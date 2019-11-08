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

### Hardware Memory Model

The Magical Sword that software engineers who are setting out to fight Dragons are given, is not quite a sword. Rather, what the hardware guys have given us are the Rules As Written. They describe which values a processor can observe given the instructions this (or some other) processor has executed. What we could classify as Spells would be the Memory Barriers. For the MESI example of ours, they would be the following:

- **Store Memory Barrier** (a.k.a. ST, SMB, smp_wmb) is the instruction that tells the processor to apply all the **stores** that are already in the **store buffer**, before it applies any that come after this instruction
- **Load Memory Barrier** (a.k.a. LD, RMB, smp_rmb) is the instruction that tells the processor to apply all the **invalidates** that are already in the **invalidate queue**, before executing any loads

So, these two Spells can prevent the two situations which we have come across earlier. We should use it:

```java
void executedOnCpu0() {
    value = 10;
    storeMemoryBarrier(); // Mighty Spell!
    finished = true;
}
void executedOnCpu1() {
    while(!finished);
    loadMemoryBarrier(); // I am a Wizard!
    assert value == 10;
}
```

***上面的 storeMemoryBarrier 和 loadMemoryBarrier 都没有这个本地方法的***

Yay! We are now safe. Time to write some high-performance *and* correct concurrent code!

Oh, wait. It doesn’t even compile, says something about missing methods. What a mess.

## Write Once @ Run Anywhere

All those cache coherency protocols, memory barriers, dropped caches and whatnot seem to be awfully platform-specific things. Java Developers should not care for those at all. Java Memory Model has no notion of reordering, after all. 

If you do not fully understand this last phrase, you should not continue reading this article. A better idea would be to go and learn some JMM instead. A good start would be this [FAQ](http://www.cs.umd.edu/~pugh/java/memoryModel/jsr-133-faq.html).

But there *are* reorderings happening on deeper levels of abstractions. Should be interesting to see how JMM maps to the hardware model. Let’s start with a simple class ([github](https://github.com/gvsmirnov/java-perv/blob/master/labs/src/main/java/ru/gvsmirnov/perv/labs/concurrency/TestSubject.java)):

```java
public class TestSubject {
 
    private volatile boolean finished;
    private int value = 0;
 
    void executedOnCpu0() {
        value = 10;
        finished = true;
    }
 
    void executedOnCpu1() {
        while(!finished);
        assert value == 10;
    }
 
}
```

If you have never before digged through the sources of OpenJDK (and even if you have, for that matter), it could be hard to find where the things that interest you lie. An easy way to narrow down the search space is getting the name of the bytecode instruction that interests you, and simply look for it. Alright, let’s do that: 

```java
$ javac TestSubject.java &amp; javap -c TestSubject
void executedOnCpu0();
  Code:
     0: aload_0          // Push this to the stack
     1: bipush        10 // Push 10 to the stack
     3: putfield      #2 // Assign 10 to the second field(value) of this
     6: aload_0          // Push this to the stack
     7: iconst_1         // Push 1 to the stack
     8: putfield      #3 // Assign 1 to the third field(finished) of this
    11: return

void executedOnCpu1();
  Code:
     0: aload_0          // Push this to the stack
     1: getfield      #3 // Load the third field of this(finished) and push it to the stack
     4: ifne          10 // If the top of the stack is not zero, go to label 10
     7: goto          0  // One more iteration of the loop
    10: getstatic     #4 // Get the static system field $assertionsDisabled:Z
    13: ifne          33 // If the assertions are disabled, go to label 33(the end)
    16: aload_0          // Push this to the stack
    17: getfield      #2 // Load the second field of this(value) and push it to the stack
    20: bipush        10 // Push 10 to the stack
    22: if_icmpeq     33 // If the top two elements of the stack are equal, go to label 33(the end)
    25: new           #5 // Create a new java/lang/AssertionError
    28: dup              // Duplicate the top of the stack
    29: invokespecial #6 // Invoke the constructor (the <init> method)
    32: athrow           // Throw what we have at the top of the stack (an AssertionError)
    33: return
```

There are two things of interest here:

1. Assertions are disabled by default, as many people tend to forget. Use `-ea` to enable them.
2. The names that we were looking for: `getfield` and `putfield`.

### Down The Rabbit Hole

As we can see, the instructions used for loading and storing are the same for both `volatile` and plain fields. So, it is a good idea to find where the compiler learns whether a field is `volatile` or not. Digging around a little, we end up in `share/vm/ci/ciField.hpp`. The method of interest is 

```java
bool is_volatile    () { return flags().is_volatile(); }
```

So, what we now are tasked with is finding the methods that handle loading and storing of fields and use investigate all the codepaths conditional on the result of invoking the method above. The Client Compiler processes them on the **Low-Level Intermediate Representation (LIR)** stage, in the file `share/vm/c1/c1_LIRGenerator.cpp`. 

###  C1 Intermediate Representation

Let’s start with the stores. The method that we are looking into is `void LIRGenerator::do_StoreField(StoreField* x)`, and resides at lines `1658:1751`. The first remarkable action that we see is 

```java
if (is_volatile && os::is_MP()) {
  __ membar_release();
}
```

Cool, a **memory barrier**! The two underscores are a macro that expand into `gen()->lir()->`, and the invoked method is defined in `share/vm/c1/c1_LIR.hpp`: 

```java
void membar_release()                          { append(new LIR_Op0(lir_membar_release)); }
```

So, what happened is that we have appended one more operation, `lir_membar_release`, to our representation. 

```java
if (is_volatile && !needs_patching) {
  volatile_field_store(value.result(), address, info);
}
```

The invoked method has platform-specific implementations. For x86 (`cpu/x86/vm/c1\_LIRGenerator\_x86.cpp`), it’s fairly simple: for 64-bit fields, we dabble in some Dark Magics to ensure write atomicity. Because the [spec says so](http://docs.oracle.com/javase/specs/jls/se7/html/jls-17.html#jls-17.7). This is a bit outdated, and may be reviewed in [Java 9](http://openjdk.java.net/jeps/188). The last thing that we want to see is one more memory barrier at the very end of the method: 

```java
if (is_volatile && os::is_MP()) {
  __ membar();
}
void membar()                                  { append(new LIR_Op0(lir_membar)); }
```

That’s it for the stores.

The loads are just a bit lower in the source code, and do not contain anything principally new. They have the same Dark Magic stuff for the atomicity of `long` and `double` fields, and add a `lir_membar_acquire` after the load is done.

### **Memory Barrier Types And Abstraction Levels**

By this time, you must be wondering what the **release** and **acquire** memory barriers are, for we have not yet introduced them. This is all because the **store** and **load** memory barriers which we have seen before are the operations in the MESI model, while we currently reside a couple of abstraction levels above it (or any other Cache Coherency Protocol). At this level, we have different terminology.

Given that we have two kinds of operations, **Load** and **Store**, we have four ordered of pairs of them: **LoadLoad**, **LoadStore**, **StoreLoad** and **StoreStore**. It is therefore very convenient to have four types of memory barriers with the same names.

If we have a **XY** memory barrier, it means that all **X** operations that come before the barrier must complete their execution before any **Y** operation after the barrier starts.

For instance, all **Store** operations before a **StoreStore** barrier must complete earlier than any **Store** operation that comes after the barrier starts. The [JSR-133 Cookbook](http://g.oswego.edu/dl/jmm/cookbook.html) is a good read on the subject.

Some people get confused and think that memory barriers take a variable as an argument, and then prohibit reorderings of the variable stores or loads across threads.

Memory barriers work within one thread only. By combining them in the right way, you can ascertain that when some thread loads the values stored by another thread, it sees a consistent picture. More generally, all the abstractions that JMM goes on about are granted by the correct combination of memory barrers.

Then there are the **Acquire** and **Release** semantics. A **write** operation that has **release** semantics requires that all the memory operations that come before it are finished before the operation itself starts its execution. The opposite is true for the **read-acquire** operations.

One can see that a **Release Memory Barrier** can be implemented as a `LoadStore|StoreStore` combination, and the **Acquire Memory Barrier** is a `LoadStore|LoadLoad`. The `StoreLoad` is what we have seen above as `lir_membar`.

...