[toc]

[原文链接]( http://blog.jamesdbloom.com/JVMInternals.html )

本文实际上是我在读这篇文章所作的注解。***黑色加粗斜体是所作注解。***

# JVM Internals

***注：原文所写的对应的 JVM 版本是7.***

![JMM](../image/JMM.png)

***注：本图中把字符串常量池 String Constant Poll  也就是 Interned Sttrings 放在了  Non Heap 中，在 JDK8 之后包括 JDK8 ，字符串常量池被放进了堆 Heap 中。具体的实现机制可以参考 [深入解析String#intern](https://tech.meituan.com/2014/03/06/in-depth-understanding-string-intern.html)。这里的内存分配示意图和 JAVA VIRTUAL MACHINE SPECIFICATION 有一些不同，比较着来看。***

 The components shown on this diagram are each explained below in two sections. [First section](http://blog.jamesdbloom.com/JVMInternals.html#threads) covers the components that are created for each thread and the [second section](http://blog.jamesdbloom.com/JVMInternals.html#shared_between_threads) covers the components that are created independently of threads. 

- Threads
  - [JVM System Threads](http://blog.jamesdbloom.com/JVMInternals.html#jvm_system_threads)
  - [Per Thread](http://blog.jamesdbloom.com/JVMInternals.html#per_thread)
  - [program Counter (PC)](http://blog.jamesdbloom.com/JVMInternals.html#program_counter)
  - [Stack](http://blog.jamesdbloom.com/JVMInternals.html#stack)
  - [Native Stack](http://blog.jamesdbloom.com/JVMInternals.html#native_stack)
  - [Stack Restrictions](http://blog.jamesdbloom.com/JVMInternals.html#stack_restrictions)
  - [Frame](http://blog.jamesdbloom.com/JVMInternals.html#frame)
  - [Local Variables Array](http://blog.jamesdbloom.com/JVMInternals.html#local_variables_array)
  - [Operand Stack](http://blog.jamesdbloom.com/JVMInternals.html#operand_stack)
  - [Dynamic Linking](http://blog.jamesdbloom.com/JVMInternals.html#dynamic_linking)
- Shared Between Threads
  - [Heap](http://blog.jamesdbloom.com/JVMInternals.html#heap)
  - [Memory Management](http://blog.jamesdbloom.com/JVMInternals.html#memory_management)
  - [Non-Heap Memory](http://blog.jamesdbloom.com/JVMInternals.html#non_heap_memory)
  - [Just In Time (JIT) Compilation](http://blog.jamesdbloom.com/JVMInternals.html#jit_compilation)
  - [Method Area](http://blog.jamesdbloom.com/JVMInternals.html#method_area)
  - [Class File Structure](http://blog.jamesdbloom.com/JVMInternals.html#class_file_structure)
  - [Classloader](http://blog.jamesdbloom.com/JVMInternals.html#classloader)
  - [Faster Class Loading](http://blog.jamesdbloom.com/JVMInternals.html#faster_class_loading)
  - [Where Is The Method Area](http://blog.jamesdbloom.com/JVMInternals.html#where_is_the_method_area)
  - [Classloader Reference](http://blog.jamesdbloom.com/JVMInternals.html#classloader_reference)
  - [Run Time Constant Pool](http://blog.jamesdbloom.com/JVMInternals.html#constant_pool)
  - [Exception Table](http://blog.jamesdbloom.com/JVMInternals.html#exception_table)
  - [Symbol Table](http://blog.jamesdbloom.com/JVMInternals.html#symbol_table)
  - [Interned Strings (String Table)](http://blog.jamesdbloom.com/JVMInternals.html#string_table)

## Thread

A thread is a thread of execution in a program. The JVM allows an application to have multiple threads of execution running concurrently. In the Hotspot JVM there is a direct mapping between a Java Thread and a native operating system Thread. <u>After preparing all of the state for a Java thread such as thread-local storage, allocation buffers, synchronization objects, stacks and the program counter, the native thread is created.</u> The native thread is reclaimed once the Java thread terminates. The operating system is therefore responsible for scheduling all threads and dispatching them to any available CPU. Once the native thread has initialized it invokes the run() method in the Java thread. When the run() method returns, uncaught exceptions are handled, then the native thread confirms if the JVM needs to be terminated as a result of the thread terminating (i.e. is it the last non-deamon thread). When the thread terminates all resources for both the native and Java thread are released. 

### JVM System Threads

 If you use jconsole or any debugger it is possible to see there are numerous threads running in the background. <u>These background threads run in addition to the main thread, which is created as part of invoking public static void main(String[])and any threads created by the main thread.,</u>  The main background system threads in the Hotspot JVM are: 

<u>VM thread</u>

This thread waits for operations to appear that require the JVM to reach a safe-point. The reason these operations have to happen on a separate thread is because they all require the JVM to be at a safe point where modifications to the heap can not occur. The type of operations performed by this thread are "stop-the-world" garbage collections, thread stack dumps, thread suspension and biased locking revocation.

<u>Periodic task thread</u>

This thread is responsible for timer events (i.e. interrupts) that are used to schedule execution of periodic operations

<u>GC threads</u>

These threads support the different types of garbage collection activities that occur in the JVM

<u>Compiler threads</u>

These threads compile byte code to native code at runtime

<u>Signal dispatcher thread</u>

This thread receives signals sent to the JVM process and handle them inside the JVM by calling the appropriate JVM methods.

### Per Thread

 Each thread of execution has the following components: 

### Program Counter (PC)

**Address of the current instruction (or opcode) unless it is native.** If the current method is native then the PC is undefined. All CPUs have a PC, typically the PC is incremented after each instruction and therefore holds the address of the next instruction to be executed. <u>The JVM uses the PC to keep track of where it is executing instructions, the PC will in fact be pointing at a memory address in the Method Area.</u> 

***注：通过 PC 实现分支、循环等以及线程上下文的切换。***

### Stack

Each thread has its own stack that <u>holds a frame for each method executing on that thread.</u> The stack is a Last In First Out (LIFO) data structure, so the currently executing method is at the top of the stack. A new frame is created and added (pushed) to the top of stack for every method invocation. The frame is removed (popped) when the method returns normally or if an uncaught exception is thrown during the method invocation. <u>The stack is not directly manipulated, except to push and pop frame objects, and therefore the frame objects may be allocated in the Heap and the memory does not need to be contiguous.</u> 

### Native Stack

Not all JVMs support native methods, however, those that do typically create a per thread native method stack. If a JVM has been implemented using a C-linkage model for Java Native Invocation (JNI) then the native stack will be a C stack. In this case the order of arguments and return value will be identical in the native stack to typical C program. <u>A native method can typically (depending on the JVM implementation) call back into the JVM and invoke a Java method. Such a native to Java invocation will occur on the stack (normal Java stack); the thread will leave the native stack and create a new frame on the stack (normal Java stack).</u> 

### Stack Restrictions

 A stack can be a dynamic or fixed size. If a thread requires a larger stack than allowed a StackOverflowError is thrown. If a thread requires a new frame and there isn’t enough memory to allocate it then an OutOfMemoryError is thrown. 

### Frame

A new frame is created and added (pushed) to the top of stack for every method invocation. The frame is removed (popped) when the method returns normally or if an uncaught exception is thrown during the method invocation. For more detail on exception handling [see the section on Exception Tables below](http://blog.jamesdbloom.com/JVMInternals.html#exception_table).

Each frame contains:

- Local variable array
- Return value
- Operand stack
- **Reference to runtime constant pool for class of the current method** (***这点很重要***)

### Local Variables Array

The array of local variables contains all the variables used during the execution of the method, <u>**including a reference to this**, all method parameters and other locally defined variables. For class methods (i.e. static methods) the method parameters start from zero, however, for instance method the zero slot is reserved for this.</u>

***本地变量表相当于一个 Array. 静态方法中其索引从零开始，实例方法中其从一开始，索引0的位置空出用来以后保存 this 。***

A local variable can be:

- boolean
- byte
- char
- long
- short
- int
- float
- double
- reference
- returnAddress ***(The values of the returnAddress type are <u>pointers</u> to the opcodes of Java Virtual Machine instructions. Of the primitive types, only the returnAddress type is not directly associated with a Java programming language type.)***

<u>All types take a single slot in the local variable array except long and double which both take two consecutive slots because these types are double width (64-bit instead of 32-bit).</u>

### Operand Stack

<u>The operand stack is used during the execution of byte code instructions in a similar way that general-purpose registers are used in a native CPU.</u> Most JVM byte code spends its time manipulating the operand stack by pushing, popping, duplicating, swapping, or executing operations that produce or consume values. Therefore, instructions that move values between the array of local variables and the operand stack are very frequent in byte code. For example, a simple variable initialization results in two byte codes that interact with the operand stack. 

```
int i;
```

Gets compiled to the following byte code:

```
 0:	iconst_0	// Push 0 to top of the operand stack
 1:	istore_1	// Pop value from top of operand stack and store as local 					// variable 1
```

For more detail explaining interactions between the local variables array, operand stack and run time constant pool [see the section on Class File Structure below](http://blog.jamesdbloom.com/JVMInternals.html#class_file_structure).

### Dynamic Linking

Each frame contains a reference to the runtime constant pool. The reference points to the constant pool for the class of the method being executed for that frame. This reference helps to support dynamic linking.

C/C++ code is typically compiled to an object file then multiple object files are linked together to product a usable artifact such as an executable or dll. <u>During the linking phase symbolic references in each object file are replaced with an actual memory address relative to the final executable.</u> **In Java this linking phase is done dynamically at runtime.**

When a Java class is **compiled**, all references ***（这里的 reference 引用要理解成 c++ 中所说的引用，也可以理解为名称 alias. 不能和 javaScript 中一样简单的理解）*** to variables and methods are stored in the class's constant pool as a symbolic reference. **A symbolic reference is a logical reference not a reference that actually points to a physical memory location.** ***(编译阶段)*** The JVM implementation can choose when to resolve symbolic references, this can happen when the class file is verified, after being loaded, called eager or static resolution, instead this can happen when the symbolic reference is used for the first time called lazy or late resolution. However the JVM has to behave as if the resolution occurred when each reference is first used and throw any resolution errors at this point. **Binding is the process of the field, method or class identified by the symbolic reference being replaced by a direct reference**, <u>this only happens once because the symbolic reference is completely replaced. If the symbolic reference refers to a class that has not yet been resolved then this class will be loaded. Each direct reference is stored as an offset against the storage structure associated with the runtime location of the variable ormethod.</u>

***注：上面这几段涉及到了类 Class 的加载过程，大体可以分为三阶段：Loading => Linking => Initiating. 其中 Linking 阶段也可以分成三个阶段：Verification => Preparation => Resolution。具体的实现细节可以参考 JAVA LANGUAGE SPECIFICATION 和 JAVA VIRTUAL MACHINE SPECIFICATION。***

## Shared Between Threads

### Heap

The Heap is used to allocate class instances and arrays at runtime. ***(这里的 class instances 包括 Class Object)*** <u>Arrays and objects can never be stored on the stack because a frame is not designed to change in size after it has been created. The frame only stores references that point to objects or arrays on the heap. Unlike primitive variables and references in the local variable array (in each frame) objects are always stored on the heap so they are not removed when a method ends. Instead objects are only removed by the garbage collector.</u> 

***上面这段话非常重要，翻译（意译）一下：堆 Heap 用来在虚拟机运行时给类的实例对象和数组分配内存空间。实例和数组无法存储在栈中，因为栈帧在创建之后其大小就已经固定了，不能改变。栈帧中存储的只是指向堆中实例对象和数组的引用。和栈帧中本地变量数组 (local variable array) 储存的的原始类型和引用类型不同，对象实例总是被储存在堆中，所以当一个方法结束时它们无法随之被清除。只能通过垃圾回收器来将其移除。***

