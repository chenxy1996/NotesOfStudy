参考：

[Know thy reference](http://perfectionkills.com/know-thy-reference/)

---

道格拉斯·克拉克福德：

*The `this` parameter is very important in object oriented programming, and its value is* **determined by the invocation pattern**. There are **four patterns of invocation** *in JavaScript: the* **method invocation** *pattern, the* **function invocation** *pattern, the* **constructor invocation** *pattern, and the* **apply invocation** *pattern. The patterns differ in how the bonus parameter this is initialized.*

`this` ：
***1) The keyword "this" refers to whatever is left of the dot at call-time.***
***2) If there's nothing to the left of the dot, then "this" is the root scope (e.g. Window).***
***3) A few functions change the behavior of "this"—bind, call and apply***
***4) The keyword "new" binds this to the object just created***

For a better understanding of `this`, one should understand concept of **==references, and their base values==.**

---

[TOC]

### Reference Specification Type

<u>References are only a ==**mechanism**==, [used to describe certain behaviors in ECMAScript](https://es5.github.io/#x8.7).</u> 

They're not really "visible" to the outside world. They are vital for engine implementors, and users of the language don't need to know about them.

Except when understanding them brings a whole new level of clarity.

### Theory

<u>ECMAScript defines Reference as a "resolved name binding". It's an abstract entity that consists of three components — **base, name, and strict flag.** The first 2 are what's important for us at the moment.</u>

<u>==There are 2 cases when Reference is created: in the process of **Identifier resolution** and during **property access**.==</u> In other words, `foo` creates a Reference and `foo.bar` (or `foo['bar']`) creates a Reference. Neither literals — `1`, `"foo"`, `/x/`, `{ }`, `[ 1,2,3 ]`, etc., nor function expressions — `(function(){})` — create references.

| Example                 | Reference? | Notes                                                  |
| ----------------------- | ---------- | ------------------------------------------------------ |
| "foo"                   | No         |                                                        |
| 123                     | No         |                                                        |
| /x/                     | No         |                                                        |
| ({})                    | No         |                                                        |
| (function(){})          | No         |                                                        |
| foo                     | Yes        | Could be unresolved reference if `foo` is not defined  |
| foo.bar                 | Yes        | Property reference                                     |
| (123).toString          | Yes        | Property reference                                     |
| (function(){}).toString | Yes        | Property reference                                     |
| (1,foo.bar)             | No         | Already evaluated, BUT see grouping operator exception |
| (f = foo.bar)           | No         | Already evaluated, BUT see grouping operator exception |
| (foo)                   | Yes        | Grouping operator does not evaluate reference          |
| (foo.bar)               | Yes        | Ditto with property reference                          |

<u>Every time a Reference is created, its components — "base", "name", "strict" — are set to some values. The strict flag is easy — it's there to denote if code is in strict mode or not. The "name" component is set to identifier or property name that's being resolved, and the base is set to either property object or environment record.</u>

It might help to think of References as **plain JS objects with a null [[Prototype]]** (i.e. with no "prototype chain"), containing only "base", "name", and "strict" properties; this is how we can illustrate them below:

When Identifier `foo` is resolved, a Reference is created like so:

```js
foo.bar;

// creates (in abstract code)

var Reference = {
  base: foo,
  name: "bar",
  strict: false
};
```



