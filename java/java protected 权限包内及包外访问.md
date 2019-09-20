[TOC]

# Java 中 `protected` 对包内及包外访问属性、方法过程的具体影响

*（题目也可以为：Java 中子类访问不到超类的 `protected` 属性或方法）*



## 前言

一切要从那张经典的表说起：

**Access Levels**

| **Modifier** | **Class** | **Package** | **Subclass** | **World** |
| ------------ | --------- | ----------- | ------------ | --------- |
| public       | Y         | Y           | Y            | Y         |
| protected    | Y         | Y           | Y            | N         |
| no modifier  | Y         | Y           | N            | N         |
| private      | Y         | N           | N            | N         |

该表定义了四个不同的访问权限修饰符对访问类中的成员 (class members) 的权限影响。

**其中最迷惑的是 `protected`**. 往往在很多相关文章或专业书籍中被简单解释：<u>该类成员可以在同个包下或者不同包下的子类中可以被访问到</u>。

## 问题

受此影响，为了验证 `protected` 的作用而写出以下的代码：

```java
package class
```

