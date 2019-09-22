[TOC]

# Introduction

***最重要的可以看 Access Control 这一节***

## Definition

<u>**Names** are used to refer to entities declared in a program.</u>

## Declared entity

<u>A declared entity ([§6.1](https://docs.oracle.com/javase/specs/jls/se8/html/jls-6.html#jls-6.1)) is a package, class type (normal or enum), interface type (normal or annotation type), member (class, interface, field, or method) of a reference type, type parameter (of a class, interface, method or constructor), parameter (to a method, constructor, or exception handler), or local variable.</u>

## Classification

**A *simple name* is a single identifier.**

**A *qualified name* consists of a name, a "`.`" token, and an identifier.**

### Simple Names

**consisting of a single identifier.**

### Qualified Names

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

*Method names should be verbs or verb phrases, in mixed case, with the first letter lowercase and the first letter of any subsequent words capitalized. Here are some additional specific conventions for method names:*

- Methods to get and set an attribute that might be thought of as a variable *V* should be named `getV` and `setV`. An example is the methods `getPriority` and `setPriority` of class `Thread`.
- A method that returns the length of something should be named `length`, as in class `String`.
- A method that tests a boolean condition *V* about an object should be named `isV`. An example is the method `isInterrupted` of class `Thread`.
- A method that converts its object to a particular format *F* should be named `toF`. Examples are the method `toString` of class `Object` and the methods `toLocaleString` and `toGMTString` of class `java.util.Date`.

### Field Names

Names of fields that are not `final` should be in mixed case with a lowercase first letter and the first letters of subsequent words capitalized. Note that well-designed classes have very few `public` or `protected` fields, except for fields that are constants (`static` `final` fields).

Fields should have names that are nouns, noun phrases, or abbreviations for nouns.

### Constant Names

*The names of constants in interface types should be, and* `final` *variables of class types may conventionally be, a sequence of one or more words, acronyms, or abbreviations, all uppercase, with components separated by underscore "*`_`*" characters. Constant names should be descriptive and not unnecessarily abbreviated. Conventionally they may be any appropriate part of speech.*

*A group of constants that represent alternative values of a set, or, less frequently, masking bits in an integer value, are sometimes usefully specified with a common acronym as a name prefix.*

### Local Variable and Parameter Names

(注意：原则上来说应该遵循变量，不管是不是局部变量都应该有具体的含义，要让人看懂)

**<u>*Local variable and parameter names should be short, yet meaningful. They are often short sequences of lowercase letters that are not words, such as:*</u>**

- Acronyms, that is the first letter of a series of words, as in `cp` for a variable holding a reference to a `ColoredPoint`
- Abbreviations, as in `buf` holding a pointer to a buffer of some kind
- Mnemonic terms, organized in some way to aid memory and understanding, typically by using a set of local variables with conventional names patterned after the names of parameters to widely used classes. For example:
  - `in` and `out`, whenever some kind of input and output are involved, patterned after the fields of `System`
  - `off` and `len`, whenever an offset and length are involved, patterned after the parameters to the `read` and `write` methods of the interfaces `DataInput` and `DataOutput` of `java.io`

*One-character local variable or parameter names should be avoided, except for temporary and looping variables, or where a variable holds an undistinguished value of a type. Conventional one-character names are:*

- `b` for a `byte`
- `c` for a `char`
- `d` for a `double`
- `e` for an `Exception`
- `f` for a `float`
- `i`, `j`, and `k` for `int`s
- `l` for a `long`
- `o` for an `Object`
- `s` for a `String`
- `v` for an arbitrary value of some type

*Local variable or parameter names that consist of only two or three lowercase letters should not conflict with the initial country codes and domain names that are the first component of unique package names.*

# Names and Identifiers

A *name* is used to refer to an entity declared in a program.

There are two forms of names: simple names and qualified names.

A *simple name* is a single identifier.

A *qualified name* consists of a name, a "`.`" token, and an identifier.

**Not all identifiers in a program are a part of a name. Identifiers are also used in the following situations:**

- In declarations ([§6.1](https://docs.oracle.com/javase/specs/jls/se8/html/jls-6.html#jls-6.1)), where an identifier may occur to specify the name by which the declared entity will be known.

- As labels in labeled statements ([§14.7](https://docs.oracle.com/javase/specs/jls/se8/html/jls-14.html#jls-14.7)) and in `break` and `continue` statements ([§14.15](https://docs.oracle.com/javase/specs/jls/se8/html/jls-14.html#jls-14.15), [§14.16](https://docs.oracle.com/javase/specs/jls/se8/html/jls-14.html#jls-14.16)) that refer to statement labels.

  The identifiers used in labeled statements and their associated `break` and `continue` statements are completely separate from those used in declarations.

- In field access expressions ([§15.11](https://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.11)), where an identifier occurs after a "`.`" token to indicate a member of the object denoted by the expression before the "`.`" token, or the object denoted by the `super` or *TypeName*`.``super` before the "`.`" token.

- In some method invocation expressions ([§15.12](https://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.12)), wherever an identifier occurs after a "`.`" token and before a "`(`" token to indicate a method to be invoked for the object denoted by the expression before the "`.`" token, or the type denoted by the *TypeName* before the "`.`" token, or the object denoted by the `super` or *TypeName*`.``super` before the "`.`" token.

- In some method reference expressions ([§15.13](https://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.13)), wherever an identifier occurs after a "`::`" token to indicate a method of the object denoted by the expression before the "`::`" token, or the type denoted by the *TypeName* before the "`::`" token, or the object denoted by the `super` or *TypeName*`.``super` before the "`::`" token.

- In qualified class instance creation expressions ([§15.9](https://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.9)), where an identifier occurs to the right of the `new` token to indicate a type that is a member of the compile-time type of the expression preceding the `new` token.

- In element-value pairs of annotations ([§9.7.1](https://docs.oracle.com/javase/specs/jls/se8/html/jls-9.html#jls-9.7.1)), to denote an element of the corresponding annotation type.

```java
class Test {
    public static void main(String[] args) {
        Class c = System.out.getClass();
        System.out.println(c.toString().length() +
                           args[0].length() + args.length);
    }
}
```

*the identifiers* `Test`*,* `main`*, and the first occurrences of* `args` *and* `c` *are not names. Rather, they are identifiers used in declarations to specify the names of the declared entities. The names* `String`*,* `Class`*,* `System.out.getClass`*,* `System.out.println`*,* `c.toString`*,* `args`*, and* `args.length` *appear in the example.*

*The occurrence of* `length` *in* `args.length` *is a name because* `args.length` *is a qualified name (*[§6.5.6.2](https://docs.oracle.com/javase/specs/jls/se8/html/jls-6.html#jls-6.5.6.2)*) and not a field access expression (*[§15.11](https://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.11)*). A field access expression, as well as a method invocation expression, a method reference expression, and a qualified class instance creation expression, uses an identifier rather than a name to denote the member of interest. Thus, the occurrence of* `length` *in* `args[0].length()` *is* not *a name, but rather an identifier appearing in a method invocation expression.*

*One might wonder why these kinds of expression use an identifier rather than a simple name, which is after all just an identifier. The reason is that a simple expression name is defined in terms of the lexical environment; that is, a simple expression name must be in the scope of a variable declaration (*[§6.5.6.1](https://docs.oracle.com/javase/specs/jls/se8/html/jls-6.html#jls-6.5.6.1)*). On the other hand, field access, qualified method invocation, method references, and qualified class instance creation all refer to members whose names are not in the lexical environment. By definition, such names are bound only in the context provided by the* Primary *of the field access expression, method invocation expression, method reference expression, or class instance creation expression; or by the* `super` *of the field access expression, method invocation expression, or method reference expression; and so on. Thus, we denote such members with identifiers rather than simple names.*

## Scope of a Declaration

**The *scope* of a declaration is the region of the program within which the entity declared by the declaration can be referred to using a simple name, provided it is visible。**

## Shadowing and Obscuring

**A local variable ([§14.4](https://docs.oracle.com/javase/specs/jls/se8/html/jls-14.html#jls-14.4)), formal parameter ([§8.4.1](https://docs.oracle.com/javase/specs/jls/se8/html/jls-8.html#jls-8.4.1), [§15.27.1](https://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.27.1)), exception parameter ([§14.20](https://docs.oracle.com/javase/specs/jls/se8/html/jls-14.html#jls-14.20)), and local class ([§14.3](https://docs.oracle.com/javase/specs/jls/se8/html/jls-14.html#jls-14.3)) can only be referred to using a simple name, not a qualified name ([§6.2](https://docs.oracle.com/javase/specs/jls/se8/html/jls-6.html#jls-6.2)).**

### Shadowing

**A declaration d is said to be *visible at point p in a program* if the scope of d includes `p`, and d is not shadowed by any other declaration at `p`.**

 ### Obscuring

## Determining the Meaning of a Name

###  Syntactic Classification of a Name According to Context

### Reclassification of Contextually Ambiguous Names

### Meaning of Package Names

### Meaning of Expression Names

#### Simple Expression Names

If an expression name consists of a single *Identifier*, then there must be exactly one declaration denoting either a local variable, parameter, or field visible ([§6.4.1](https://docs.oracle.com/javase/specs/jls/se8/html/jls-6.html#jls-6.4.1)) at the point at which the *Identifier* occurs. Otherwise, a compile-time error occurs.

If the declaration denotes an instance variable ([§8.3](https://docs.oracle.com/javase/specs/jls/se8/html/jls-8.html#jls-8.3)), the expression name must appear within the declaration of an instance method ([§8.4](https://docs.oracle.com/javase/specs/jls/se8/html/jls-8.html#jls-8.4)), constructor ([§8.8](https://docs.oracle.com/javase/specs/jls/se8/html/jls-8.html#jls-8.8)), instance initializer ([§8.6](https://docs.oracle.com/javase/specs/jls/se8/html/jls-8.html#jls-8.6)), or instance variable initializer ([§8.3.2](https://docs.oracle.com/javase/specs/jls/se8/html/jls-8.html#jls-8.3.2)). If the expression name appears within a `static` method ([§8.4.3.2](https://docs.oracle.com/javase/specs/jls/se8/html/jls-8.html#jls-8.4.3.2)), static initializer ([§8.7](https://docs.oracle.com/javase/specs/jls/se8/html/jls-8.html#jls-8.7)), or initializer for a `static` variable ([§8.3.2](https://docs.oracle.com/javase/specs/jls/se8/html/jls-8.html#jls-8.3.2), [§12.4.2](https://docs.oracle.com/javase/specs/jls/se8/html/jls-12.html#jls-12.4.2)), then a compile-time error occurs.

#### Qualified Expression Names

# Access Control

