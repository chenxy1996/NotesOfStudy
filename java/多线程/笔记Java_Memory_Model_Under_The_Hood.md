[toc]

[原文链接]( https://gvsmirnov.ru/blog/tech/2014/02/10/jmm-under-the-hood.html )

# Java Memory Model Under The Hood

There are many sources where you can get an idea of what JMM is about, but most of them still leave you with lots of unanswered questions. How does that happens-before thing work? Does using `volatile` result in caches being dropped? Why do we even need a memory model in the first place?

This article is intended to give the readers a level of understanding which allows them to answer all of these questions. It will consist of two large parts; the first of them being a hardware-level outline of what’s happening, and the second is indulging in some digging around OpenJDK sources and experimenting. Thus, even if you’re not exactly into Java, the first part might still be of interest to you.

## 硬件相关的知识

The engineers that create hardware are working hard on optimizing their products ever further, enabling you to get more and more performance units out of your code. However, it does come at a price of counter-intuitive execution scenarios that your code may display when it is run. There are countless hardware details obscured from our view by abstractions. And abstractions tend to get [leaky](http://www.joelonsoftware.com/articles/LeakyAbstractions.html). 

### Processor Caches

A request to the main memory is an expensive operation, which can take hundreds of nanoseconds to execute, even on modern hardware. The execution time of other operations, however, has grown considerably smaller over the years, unlike the main memory access. This problem is commonly named as the [Memory Wall](http://www.eecs.ucf.edu/~lboloni/Teaching/EEL5708_2006/slides/wulf94.pdf), and the obvious workaround for this is introducing caches. To put it simply, the processor has local copies of the contents of the main memory that it frequently uses. You can read further on different cache structures [here](http://arstechnica.com/gadgets/2002/07/caching/2/), while we shall move on to the problem of keeping the cached values up to date.

Although there is apparently no problem when you have only one execution unit (referred to as **processor** from now on), things get complicated if you have more than one of those.

<u>How does processor **A** know that processor **B** has modified some value, if **A** has it cached?</u>

<u>Or, more generally, how do you ensure **cache coherency**?</u>

To maintain a consistent view on the state of the world, processors have to communicate with each other. The rules of such communication are called a **cache coherency protocol**. 

### Cache Coherency Protocols

There are numerous different protocols, which vary not only from one hardware manufacturer to another, but also constantly develop within a single vendor’s product line. In spite of all this variety, most of the protocols have lots of common aspects. Which is why we will take a closer look at **MESI**. It does not give the reader a full overview of all the protocols out there, however. There are some (e.g. [Directory Based](http://courses.cs.washington.edu/courses/cse471/00au/Lectures/luke_directories.pdf)) protocols that are absolutely different. We are not going to look into them.

In MESI, every cache entry can be in one of the following states:

- **I**nvalid: the cache does not have such entry
- **E**xclusive: the entry resides in this cache only, and has not been modified
- **M**odified: the processor has modified a value, but has not yet written it back to the main memory or sent to any other processor
- **S**hared: more that one processor has the entry in its cache

Transitions between states occur via sending certain messages that are also a part of the protocol. The exact message types are not quite relevant, so they are omitted in this article. There are many other sources which you can use to gain insight into them. [Memory Barriers: a Hardware View for Software Hackers](http://www.rdrop.com/users/paulmck/scalability/paper/whymb.2010.07.23a.pdf) is the one that I would recommend.

### MESI Optimizations And The Problems They Introduced

 Without going into details, we will say that it takes time for messages to be delivered, which introduces more latency into state switching. It is also important to understand that some state transitions require some special handling, which might stall the processor. These things lead to all sorts of scalability and performance problems. 

### **Store Buffers**

If you need to write something to a variable that is **Shared** in the cache, you have to send an **Invalidate** message to all its other holders, and wait for them to acknowledge it. The processor is going to be stalled for that duration Which is a sad thing, seeing as the time required for that is typically several orders of magnitude higher than executing a simple instruction needs. 

In real life, cache entries do not contain just a single variable. The established unit is a cache line, which usually contains more than one variable, and is typically 64 bytes in size.

It can lead to interesting side effects, e.g. [cache contention](http://openjdk.java.net/jeps/142).

<u>To avoid such a waste of time, **Store Buffers** are used. The processor places the values which it wants to write to its buffer, and goes on executing things. When all the **Invalidate Acknowledge** messages are received, the data is finally committed.</u>

One can expect a number of hidden dangers here. The easy one is that a processor may try to read some value that it has placed in the store buffer, but which has not yet been committed. The workaround is called **Store Forwarding**, which causes the loads to return the value in the store buffer, if it is present. 

The second pitfall is that there is no guarantee on the order in which the stores will leave the buffer. Consider the following piece of code: 

```java
void executedOnCpu0() {
    value = 10;
    finished = true;
}
void executedOnCpu1() {
    while(!finished);
    assert value == 10;
}
```

<u>Suppose that when the execution starts, **CPU 0** has `finished` in the **Exclusive** state, while `value` is not installed in its cache at all (i.e. is **Invalid**). In such scenario, `value` will leave the store buffer considerably later than `finished` will. It is entirely possible that **CPU 1** will then load `finished` as `true`, while `value` will not be equal to `10`.</u> 

Such changes in the observable behavior are called **reorderings**. Note that it does not necessarily mean that your instructions' places have been changed by some malicious (or well-meaning) party.

It just means that some other CPU has *observed* their results in a different order than what's written in the program.

### **Invalidate Queues (说的很好)**

Executing an invalidation is not a cheap operation as well, and it costs for the processor applying it. Moreover, it is no surprise that Store Buffers are not infinite, so the processors sometimes have to wait for **Invalidate Acknowledge** to come. These two can make performance degrade considerably. To counter this, **Invalidate Queues** have been introduced. Their contract is as follows:

- For all incoming **Invalidate** requests, **Invalidate Acknowledge** messages are immediately sent
- The **Invalidate** is not in fact applied, but placed to a special queue, to be executed when convenient
- The processor will not send any messages on the cache entry in question, until it processes the **Invalidate**

There, too, are cases when such optimization will lead to counter-intuitive results. We return to our code, and assume that **CPU 1** has `value` in the **Exclusive** state. Here’s a diagram of a possible execution: 

Concurrency is simple and easy, is it not? The problem is in steps (4) — (6). When **CPU 1** receives an **Invalidate** in (4), it queues it without processing. Then **CPU 1** gets **Read Response** in (6), while the corresponding **Read** has been sent earlier in (2). Despite this, we do not invalidate `value`, ending up with an assertion that fails. If only operation (N) has executed earlier. But alas, the damn optimization has spoiled everything! On the other hand, it grants us some significant performance boost. 

 The thing is that hardware engineers cannot know in advance when such an optimization is allowed, and when it is not. Which is why they leave the problem in our capable hands. They also give us a little something, with a note attached to it: “It’s dangerous to go alone! Take this!” 