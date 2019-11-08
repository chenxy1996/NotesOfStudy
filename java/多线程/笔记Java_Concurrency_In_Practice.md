[toc]

# Java Concurrency In Practice

##  Chapter 1 - Introduction

- 并发和并行的区别。
- 线程和进程的区别。Resource utilization, Fairness, Convenience.

## Chapter 2 - Thread Safety

- 把所有的 mutable state 封装在一个类之中，用这个类自带的 intrinsic lock 来同步访问操作。这样使代码简洁，健壮，容易之后的维护。
- 当出现要在 simplicity 和 performance 之间做选择的情况，必须要二者取其一的时候，首先要考虑的是 simplicity 。若是为了性能而复杂化代码，可能会得不偿失，带来潜在的问题，并且难以维护。
- 出现 check and act 操作时候，考虑将 check 放入一个同步块中 （Synchronized block）, act 放入另一个当中。

## Chapter 3 - Sharing Objects

 