[TOC]

# Java 中 `protected` 对包内及包外访问属性、方法过程的具体影响的深入探究

*（题目也可以为：Java 中子类访问不到超类的 `protected` 属性或方法）*

## *references:*

[Controlling Access to Members of a Class](https://docs.oracle.com/javase/tutorial/java/javaOO/accesscontrol.html)

[Chapter 6. Names 6.6.2.1](https://docs.oracle.com/javase/specs/jls/se8/html/jls-6.html)

[为什么子类中不能访问另一个包中父类中的protected方法？](https://blog.csdn.net/dawn_after_dark/article/details/74453915)

[再谈包访问权限 子类为何不能使用父类protected方法](https://blog.csdn.net/noteless/article/details/82599324)



## 前言

一切要从那张经典的表说起：

**Access Levels**

| **Modifier** | **Class** | **Package** | **Subclass** | **World** |
| ------------ | --------- | ----------- | ------------ | --------- |
| public       | Y         | Y           | Y            | Y         |
| protected    | Y         | Y           | Y            | N         |
| no modifier  | Y         | Y           | N            | N         |
| private      | Y         | N           | N            | N         |

该表定义了四个不同的访问权限修饰符对访问类中的成员 (class members) 的权限影响，注意第四列的 `SubClass` 是指定义在不同包下的子类， 第三列 `Package` 是指定义在同一包下的所有子类。

**其中最迷惑的是 `protected`**. 往往在很多相关文章或专业书籍中被简单解释：<u>该类成员可以在同个包下或者不同包下的子类中可以被访问到</u>。成员不包括构造方法。

## 问题

受此影响，为了验证 `protected` 的作用往往会写出以下的代码：

```java
package AccessTest.package1;

public class SupClass {
	protected String motto = "This is a Protected field";
}
```

```java
package AccessTest.package2;
import AccessTest.package1.SupClass;

public class SubClass extends SupClass{
	public static void main(String... args) {
		SupClass a = new SupClass();
		System.out.println(a.motto); //The field SupClass.motto is not visible
	}
}
```

**在 `Access.package2` 中， 显示编译错误，提示信息：`The field SupClass.motto is not visible`.**  这可能和我们之前的理解不太一样，原本以为既然 `protected` 保护字段能在不同包下的子类中可以访问，那么上述代码应该是可以编译通过且能执行的，因为 `SubClass` 是 `SupClass`  的子类。

我们又写了下段代码，在 `SupClass` 的同个包类创建一个新的 `SupClass` 的子类：

```java
package AccessTest.package1;

public class SubClass1 extends SupClass {
	public static void main(String[] args) {
		SupClass a = new SupClass();
		System.out.println(a.motto); // 编译通过且能执行
	}
}
```

此时上述代码编译通过且能执行。

问题就是为什么会出现此种现象。

## 理解

先说结论：

1. `protected` 可以理解为对同个包下的所有类可见（**既能通过实例化一个超类对象访问又能通过继承访问**），不同包下的类要想访问只能通过**继承**的方式。
2. 同个包下所有类能通过实例化一个超类对象访问 `protected` 成员的原因是**它们在同一包中。**

下面给出具体的说明和解释。

## 结论1

### 不同包下的类要想访问 `protected` 成员只能通过**继承**的方式。

之前给出的代码例子中，在不同包中直接通过创建父类对象来访问这个 `protected` 成员是不可行的。**通过继承的方式是指：创建子类对象实现访问这个 `protected` 成员。**

