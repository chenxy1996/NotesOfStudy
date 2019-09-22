[TOC]

# Introduction
## Definition

<u>**Names** are used to refer to entities declared in a program.</u>

## Declared entity

<u>A declared entity ([§6.1](https://docs.oracle.com/javase/specs/jls/se8/html/jls-6.html#jls-6.1)) is a package, class type (normal or enum), interface type (normal or annotation type), member (class, interface, field, or method) of a reference type, type parameter (of a class, interface, method or constructor), parameter (to a method, constructor, or exception handler), or local variable.</u>

## Classification

### Simple Name

**consisting of a single identifier.**

### Qualified Name

**consisting of a sequence of identifiers separated by "`.`" tokens** 

A qualified name `N.x` may be used to refer to a *member* of a package or reference type, where `N` is a simple or qualified name and `x` is an identifier. If `N` names a package, then `x` is a member of that package, which is either a class or interface type or a subpackage. If `N` names a reference type or a variable of a reference type, then `x` names a member of that type, which is either a class, an interface, a field, or a method.

## Scope

Every declaration that introduces a name has a *scope* ([§6.3](https://docs.oracle.com/javase/specs/jls/se8/html/jls-6.html#jls-6.3)), which is the part of the program text within which the declared entity can be referred to by a simple name.

## Access Control

**Access is a different concept from scope. Access specifies the part of the program text within which the declared entity can be referred to by a qualified name.**

# Declaration

**A *declaration* introduces an entity into a program and includes an identifier ([§3.8](https://docs.oracle.com/javase/specs/jls/se8/html/jls-3.html#jls-3.8)) that can be used in a name to refer to this entity.**

## including

*... Detailed description will be seen in raw chapter.*

## Naming Conventions

### Package Names

*You form a unique package name by first having (or belonging to an organization that has) an Internet domain name, such as* `oracle.com`*. You then reverse this name, component by component, to obtain, in this example,* `com.oracle`*, and use this as a prefix for your package names, using a convention developed within your organization to further administer package names. Such a convention might specify that certain package name components be division, department, project, machine, or login names.*

***Names of packages intended only for local use should have <u>a first identifier that begins with a lowercase letter</u>, but that first identifier specifically should not be the identifier* `java`*; package names that start with the identifier* `java` *are reserved for packages of the Java SE platform.***

### Class and Interface Type Names

*Names of class types should be descriptive nouns or noun phrases, not overly long, in mixed case with the first letter of each word capitalized.*

*Likewise, names of interface types should be short and descriptive, not overly long, in mixed case with the first letter of each word capitalized.**The name may be a descriptive noun or noun phrase, which is appropriate when an interface is used as if it were an abstract superclass, such as interfaces* `java.io.DataInput` *and* `java.io.DataOutput`*; or it may be an adjective describing a behavior, as for the interfaces* `Runnable` *and* `Cloneable`*.*

### Type Variable Names

*Type variable names should be pithy (**single character if possible**) yet evocative, and **should not include lower case letters.** This makes it easy to distinguish type parameters from ordinary classes and interfaces.*

<u>*Container types should use the name* `E` *for their element type. Maps should use* `K` *for the type of their keys and* `V` *for the type of their values. The name* `X` *should be used for arbitrary exception types. We use* `T` *for type, whenever there is not anything more specific about the type to distinguish it. (This is often the case in generic methods.)*</u>

<u>*If there are multiple type parameters that denote arbitrary types, one should use letters that neighbor* `T` *in the alphabet, such as* `S`*. Alternately, it is acceptable to use numeric subscripts (e.g.,* `T1`*,* `T2`*) to distinguish among the different type variables. In such cases, all the variables with the same prefix should be subscripted.*</u>

*If a generic method appears inside a generic class, it is a good idea to avoid using the same names for the type parameters of the method and class, to avoid confusion. The same applies to nested generic classes.*

```java
public class HashSet<E> extends AbstractSet<E> { ... }
public class HashMap<K,V> extends AbstractMap<K,V> { ... }
public class ThreadLocal<T> { ... }
public interface Functor<T, X extends Throwable> {
    T eval() throws X;
}
```

### Method Names

