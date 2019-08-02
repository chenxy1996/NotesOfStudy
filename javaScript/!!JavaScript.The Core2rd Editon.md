[TOC]

[原文](http://dmitrysoshnikov.com/ecmascript/javascript-the-core-2nd-edition/)

# Object

==**Def. 1: Object:** An *object* is a *collection of properties*, and has a *single prototype object*. The prototype may be either an object or the `null` value.==

**Note**: `Object.prototype__proto__` is `null`

A prototype of an object is referenced by the internal `[[Prototype]]` property, which to user-level code is exposed via the `__proto__` property.

For the code:

```js
let point = {
    x: 10,
    y: 20,
};
```

we have the structure with two *explicit own properties* and one *implicit* `__proto__`property, which is the reference to the prototype of `point`:

![*Figure 1*](http://dmitrysoshnikov.com/wp-content/uploads/2017/11/js-object.png)

*Figure 1. A basic object with a prototype.*



# Prototype

Every object, when is created, receives its *prototype*. **If the prototype is not set *explicitly*, objects receive *default prototype* as their *inheritance object*.**

==**Def. 2: Prototype:** A *prototype* is a delegation object used to implement *prototype-based inheritance*.==

**Note**: <u>by default objects receive `Object.prototype` as  their inheritance object.</u>

The prototype can be set *explicitly* via either the `__proto__` property, or `Object.create` method:

```js
// Base object.
let point = {
  x: 10,
  y: 20,
};
 
// Inherit from `point` object via `__proto__` property
let point3D = {
  z: 30,
  __proto__: point,
};

console.log(
  point3D.x, // 10, inherited
  point3D.y, // 20, inherited
  point3D.z  // 30, own
);

// Inherit from `point` object via `Object.create` method
//let point3D = Object.create(point);
//point3D.z = 30;

```

Any object can be used as a prototype of another object, and the prototype itself can have its own prototype. If a prototype has a not-null reference to its prototype, and so on, it is called the prototype chain.

==**Def. 3: Prototype chain:** A *prototype chain* is a ***finite*** chain of objects used to implement *inheritance* and *shared properties*.==

![Figure 2](http://dmitrysoshnikov.com/wp-content/uploads/2017/11/prototype-chain.png)

*Figure 2. A prototype chain.*



The rule is very simple: if a property is not found in the object itself, there is an attempt to *resolve* it in the prototype; in the prototype of the prototype, etc. — until the whole prototype chain is considered.

Technically this mechanism is known as ***dynamic dispatch*** or ***delegation***.

==**Def. 4: Delegation:** a mechanism used to resolve a property in the inheritance chain. The process happens at runtime, hence is also called **dynamic dispatch**.==

**Note**: in contrast with *static dispatch* when references are resolved at *compile time*, *dynamic dispatch* resolves the references at *runtime*.

And i<u>f a property eventually is not found in the prototype chain, the `undefined` value is returned:</u>

```js
// An "empty" object.
let empty = {};
 
console.log(
 
  // function, from default prototype
  empty.toString,
   
  // undefined
  empty.x,
 
);
```

As we can see, a default object is actually *never empty* — it always inherits *something* from the `Object.prototype`. ==To create a *prototype-less dictionary*, we have to explicitly set its prototype to `null`:==

```js
// Doesn't inherit from anything.
let dict = Object.create(null);
 
console.log(dict.toString); // undefined

let dict1 = {};
dict1.__proto__ = null;

console.log(dict1.toString); // undefined
```

The *dynamic dispatch* mechanism allows *full mutability* of the inheritance chain, providing an ability to change the delegation object:

```js
let protoA = {x: 10};
let protoB = {x: 20};
 
// Same as `let objectC = {__proto__: protoA};`:
let objectC = Object.create(protoA);
console.log(objectC.x); // 10
 
// Change the delegate:
Object.setPrototypeOf(objectC, protoB);
console.log(objectC.x); // 20
```

**Note**: even though the `__proto__` property is standardized today, and is easier to use for explanations, on practice prefer using API methods for prototype manipulations, such as `Object.create`, `Object.getPrototypeOf`, `Object.setPrototypeOf` , and similar on the `Reflect` module.

# Class

<u>When several objects share the same initial state and behavior, they form a classification.</u>

==**Def. 5: Class:** A *class* is a formal abstract set which specifies initial state and behavior of its objects.==

![Figure 3](http://dmitrysoshnikov.com/wp-content/uploads/2017/11/shared-prototype.png)

*Figure 3. A shared prototype.*



However, this is obviously *cumbersome*. And the class abstraction serves exactly this purpose — being a ***syntactic sugar*** <u>(i.e. a construct which *semantically does the same*, but in a much *nicer syntactic form*)</u>, it allows creating such multiple objects with the convenient pattern:

```js
class Letter {
  constructor(number) {
    this.number = number;
  }
 
  getNumber() {
    return this.number;
  }
}
 
let a = new Letter(1);
let b = new Letter(2);
// ...
let z = new Letter(26);
 
console.log(
  a.getNumber(), // 1
  b.getNumber(), // 2
  z.getNumber(), // 26
);
```

Technically a “class” is represented as a *“constructor function + prototype”* pair. Thus, a constructor function *creates objects*, and also *automatically* sets the *prototype* for its newly created instances. This prototype is stored in the `<ConstructorFunction>.prototype` property.

==**Def. 6: Constructor:** A *constructor* is a function which is used to create instances, and automatically set their prototype.==

It is possible to use a constructor function explicitly. Moreover, before the class abstraction was introduced, JS developers used to do so not having a better alternative (we can still find a lot of such legacy code allover the internets):

```js
function Letter(number) {
  this.number = number;
}
 
Letter.prototype.getNumber = function() {
  return this.number;
};
 
let a = new Letter(1);
let b = new Letter(2);
// ...
let z = new Letter(26);
 
console.log(
  a.getNumber(), // 1
  b.getNumber(), // 2
  z.getNumber(), // 26
);
```

![Figure 4](http://dmitrysoshnikov.com/wp-content/uploads/2017/11/js-constructor.png)



# Execution context

==**Def. 7: Execution context:** An *execution context* is a specification device that is used to track the runtime evaluation of the code.==

There are several types of ECMAScript code: the ***global code*, *function code*, *`eval`code*, and *module code*;** each code is evaluated in its execution context. Different code types, and their appropriate objects may affect the structure of an execution context: for example, *generator functions* save their *generator object* on the context.

```js
function recursive(flag) {
 
  // Exit condition.
  if (flag === 2) {
    return;
  }
 
  // Call recursively.
  recursive(++flag);
}
 
// Go.
recursive(0);
```

When a function is called, a *new execution context* is created, and *pushed* onto the stack — at this point it becomes *an active execution context*. When a function returns, its context is *popped* from the stack.

**A context which calls another context is called a *caller***, **And a context which is being called, accordingly, is a *callee*.**

In our example the `recursive` function plays both roles: of a callee and a caller — when calls itself recursively.\

==**Def. 8: Execution context stack:** An *execution context stack* is a LIFO structure used to maintain control flow and order of execution.==

![Figure 5](http://dmitrysoshnikov.com/wp-content/uploads/2017/11/execution-stack.png)

*Figure 5. An execution context stack.*

As we can also see, the *global context* is always at the bottom of the stack, it is created prior execution of any other context.

In general, the code of a context *runs to completion*, however as we mentioned above, some objects — such as *generators*, may violate LIFO order of the stack. A generator function may suspend its running context, and *remove* it from the stack *before completion*. Once a generator is activated again, its context is *resumed* and again is *pushed* onto the stack:

```js
function *gen() {
  yield 1;
  return 2;
}

let g = gen();

console.log(
  g.next().value, // 1
  g.next().value, // 2
);
```

The `yield` statement here returns the value to the caller, and pops the context. On the second `next` call, the *same context* is pushed again onto the stack, and is *resumed*. Such context may *outlive* the caller which creates it, hence the violation of the LIFO structure.

# Environment

Every execution context has an associated *lexical environment*.

==**Def. 9: Lexical environment:** A *lexical environment* is a structure used to define association between *identifiers* appearing in the context with their values. Each environment can have a reference to an *optional parent environment*.==

**So a environment is a *storage* of variables, functions, and classes defined in a scope.**

Technically, an environment is a *pair*, consisting of an *environment record* (an actual storage table which maps identifiers to values), and a reference to the parent (which can be `null`).

For the code:

```js
let x = 10;
let y = 20;

function foo(z) {
  let x = 100;
  return x + y + z;
}

foo(30); // 150
```

The environment structures of the *global* context, and a context of the `foo` function would looks as follows:

![Figure 6](http://dmitrysoshnikov.com/wp-content/uploads/2017/11/environment-chain.png)

*Figure 6. An environment chain.*

 **if a variable is *not found* in the *own* environment, there is an attempt to lookup it in the *parent environment*, in the parent of the parent, and so on — until the whole *environment chain* is considered.**

==**Def. 10: Identifier resolution:** the process of resolving a variable *(binding)* in an environment chain. An unresolved binding results to `ReferenceError`.==

Similarly to prototypes, the same parent environment can be shared by several child environments: for example, two global functions share the same global environment.

Environment records differ by *type*. There are **<u>object** environment records</u> and <u>**declarative** environment records.</u> On top of the declarative record there are also <u>**function** environment records</u>, and **<u>module** environment records</u>. Each type of the record has specific only to it properties. However, the generic mechanism of the identifier resolution is common across all the environments, and doesn’t depend on the type of a record.

An example of an *object environment record* can be the record of the *global environment*. Such record has also associated *binding object*, which may store some properties from the record, but not the others, and vice-versa. The binding object can also be provided as `this` value.

```js
// Legacy variables using `var`.
var x = 10;

// Modern variables using `let`.
let y = 20;

// Both are added to the environment record:
console.log(
  x, // 10
  y, // 20
);

// But only `x` is added to the "binding object".
// The binding object of the global environment
// is the global object, and equals to `this`:

console.log(
  this.x, // 10
  this.y, // undefined!
);

// Binding object can store a name which is not
// added to the environment record, since it's
// not a valid identifier:

this['not valid ID'] = 30;

console.log(
  this['not valid ID'], // 30
);
```

This is depicted on the following figure:

![Figure 7](http://dmitrysoshnikov.com/wp-content/uploads/2017/11/env-binding-object.png)

*Figure 7. A binding object.*



# Closure

Functions in ECMAScript are *first-class*. This concept is fundamental to *functional programming*, which aspects are supported in JavaScript.

==**Def. 11: First-class function:** a function which can participate as a normal data: be stored in a variable, passed as an argument, or returned as a value from another function.==

With the concept of first-class functions so called [“Funarg problem”](https://en.wikipedia.org/wiki/Funarg_problem) is related (or *“A problem of a functional argument”*). The problem arises when a function has to deal with *free variables*.

==**Def. 12: Free variable:** a variable which is *neither a parameter*, *nor a local variable* of this function.==

```js
let x = 10;
 
function foo() {
  console.log(x);
}
 
function bar(funArg) {
  let x = 20;
  funArg(); // 10, not 20!
}
 
// Pass `foo` as an argument to `bar`.
bar(foo);
```

For the function `foo` the variable `x` is free. When the `foo` function is activated (via the `funArg` parameter) — where should it resolve the `x` binding? From the *outer scope* where the function was *created*, or from the *caller scope*, from where the function is *called*? As we see, the caller, that is the `bar` function, also provides the binding for `x` — with the value `20`.

The use-case described above is known as the **downwards funarg problem**, i.e. an *ambiguity* at determining a *correct environment* of a binding: should it be an environment of the *creation time*, or environment of the *call time*?

**This is solved by an agreement of using *static scope*, that is the scope of the *==creation time==*.**

==**Def. 13: Static scope:** a language implements *static scope*, if only by looking at the source code one can determine in which environment a binding is resolved.==

In our example, the environment captured by the `foo` function, is the *global environment*:

![Figure 8](http://dmitrysoshnikov.com/wp-content/uploads/2017/11/closure.png)

*Figure 8. A closure.*

==**Def. 14: Closure:** A *closure* is a function which *captures the environment*  where it’s *defined*. Further this environment is used for *identifier resolution*.

The second sub-type of the Funarg problem is known as the **upwards funarg problem**.

Again, technically it doesn’t differ from the same exact mechanism of capturing the definition environment. **Just in this case, hadn’t we have the closure, the activation environment of `foo` *would be destroyed*. But we *captured* it, so it *cannot be deallocated*, and is preserved — to support *static scope* semantics.**

However, as we can see, the technical mechanism for the *downwards* and *upwards funarg problem* is *exactly the same* — and is the *mechanism of the static scope*.

As we mentioned above, similarly to prototypes, the same parent environment can be *shared* across *several* closures. This allows accessing and mutating the shared data:

```js
function createCounter() {
  let count = 0;
 
  return {
    increment() { count++; return count; },
    decrement() { count--; return count; },
  };
}
 
let counter = createCounter();
 
console.log(
  counter.increment(), // 1
  counter.decrement(), // 0
  counter.increment(), // 1
);
```

Since both closures, `increment` and `decrement`, are created within the scope containing the `count` variable, they *share* this *parent scope*. **That is, capturing always happens ==*“by-reference”*== — meaning the *reference* to the *whole parent environment* is stored.**

![Figure 9](http://dmitrysoshnikov.com/wp-content/uploads/2017/11/shared-environment.png)

*Figure 9. A shared environment.*

<u>Some languages may capture *by-value*, making a copy of a captured variable, and do not allow changing it in the parent scopes. However in JS, to repeat, it is always the *reference* to the parent scope.</u>

**Note**: implementations may optimize this step, and do not capture the whole environment. Capturing *only used* free-variables, they though still maintain invariant of mutable data in parent scopes.

==**So all identifiers are statically scoped.**== <u>There is however *one* value which is *dynamically scoped* in ECMAScript. It’s the value of `this`.</u>

# This

The `this` value is a special object which is dynamically and implicitly passed to the code of a context. **We can consider it as an implicit extra parameter, which we can access, but cannot mutate.**

<u>The purpose of the `this` value is to executed the same code for multiple objects.</u>

==**Def. 15: This:** an implicit *context object* accessible from a code of an execution context — in order to apply the same code for multiple objects.==

```js
class Point {
  constructor(x, y) {
    this._x = x;
    this._y = y;
  }
 
  getX() {
    return this._x;
  }
 
  getY() {
    return this._y;
  }
}
 
let p1 = new Point(1, 2);
let p2 = new Point(3, 4);
 
// Can access `getX`, and `getY` from
// both instances (they are passed as `this`).
 
console.log(
  p1.getX(), // 1
  p2.getX(), // 3
);
```



Another application of `this`, is *generic interface functions*, which can be used in *mixins* or *traits*.

In the following example, the `Movable` interface contains generic function `move`, which expects the users of this mixin to implement `_x`, and `_y` properties:

```js
// Generic Movable interface (mixin).
let Movable = {
 
  /**
   * This function is generic, and works with any
   * object, which provides `_x`, and `_y` properties,
   * regardless of the class of this object.
   */
  move(x, y) {
    this._x = x;
    this._y = y;
  },
};
 
let p1 = new Point(1, 2);
 
// Make `p1` movable.
Object.assign(p1, Movable);
 
// Can access `move` method.
p1.move(100, 200);
 
console.log(p1.getX()); // 100
```

As an alternative, a mixin can also be applied at *prototype level* instead of *per-instance* as we did in the example above.

```js
function foo() {
  return this;
}
 
let bar = {
  foo,
 
  baz() {
    return this;
  },
};
 
// `foo`
console.log(
  foo(),       // global or undefined
 
  bar.foo(),   // bar
  (bar.foo)(), // bar
 
  (bar.foo = bar.foo)(), // global
);
 
// `bar.baz`
console.log(bar.baz()); // bar
 
let savedBaz = bar.baz;
console.log(savedBaz()); // global
```

==The **arrow functions** are special in terms of `this` value: their `this` is *lexical (static)*, but *not dynamic*.== I.e. their function environment record *does not provide this value*, and **it’s taken from the *parent environment*.**

```js
var x = 10;
 
let foo = {
  x: 20,
 
  // Dynamic `this`.
  bar() {
    return this.x;
  },
 
  // Lexical `this`.
  baz: () => this.x,
 
  qux() {
    // Lexical this within the invocation.
    let arrow = () => this.x;
 
    return arrow();
  },
};
 
console.log(
  foo.bar(), // 20, from `foo`
  foo.baz(), // 10, from global
  foo.qux(), // 20, from `foo` and arrow
);
```

Like we said, in the *global context* the `this` value is the *global object* (the *binding object* of the *global environment record*). Previously there was only one global object. In current version of the spec there might be *multiple global objects* which are part of *code realms*. Let’s discuss this structure.

# Realm

Before it is evaluated, all ECMAScript code must be associated with a *realm*. Technically a realm just provides a global environment for a context.

==**Def. 16: Realm:** A *code realm* is an object which encapsulates a separate *global environment*.==

**Note**: a direct realm equivalent in browser environment is the `iframe`element, which exactly provides a custom global environment. In Node.js it is close to the sandbox of the [vm module](https://nodejs.org/api/vm.html).

Current version of the specification doesn’t provide an ability to explicitly create realms, but they can be created implicitly by the implementations. There is a [proposal](https://github.com/tc39/proposal-realms/) though to expose this API to user-code.

Logically though, each context from the stack is always associated with its realm:

![Figure 10](http://dmitrysoshnikov.com/wp-content/uploads/2017/11/context-realm.png)

*Figure 10. A context and realm association.*



```js
const vm = require('vm');
 
// First realm, and its global:
const realm1 = vm.createContext({x: 10, console});
 
// Second realm, and its global:
const realm2 = vm.createContext({x: 20, console});
 
// Code to execute:
const code = `console.log(x);`;
 
vm.runInContext(code, realm1); // 10
vm.runInContext(code, realm2); // 20
```

# Job

Some operations can be postponed, and executed as soon as there is an available spot on the execution context stack.

==**Def. 17: Job:** A *job* is an abstract operation that initiates an ECMAScript computation when *no other* ECMAScript computation is currently in progress.==

Jobs are enqueued on the **job queues**, and in current spec version there are two job queues: **ScriptJobs**, and **PromiseJobs**.

And *initial job* on the *ScriptJobs* queue is the *main entry point* to our program — initial script which is loaded and evaluated: a realm is created, a global context is created and is associated with this realm, it’s pushed onto the stack, and the global code is executed.

**Notice, the *ScriptJobs* queue manages both, *scripts* and *modules*.**

Further this context can execute *other contexts*, or enqueue *other jobs*. An example of a job which can be spawned and enqueued is a *promise*.

When there is *no running* execution context and the execution context stack is *empty*, the ECMAScript implementation removes the first *pending job* from a job queue, creates an execution context and starts its execution.

```js
// Enqueue a new promise on the PromiseJobs queue.
new Promise(resolve => setTimeout(() => resolve(10), 0))
  .then(value => console.log(value));
 
// This log is executed earlier, since it's still a
// running context, and job cannot start executing first
console.log(20);
 
// Output: 20, 10
```

The ***async functions*** can *await* for promises, so they also enqueue promise jobs:

```js
async function later() {
  return await Promise.resolve(10);
}
 
(async () => {
  let data = await later();
  console.log(data); // 10
})();
 
// Also happens earlier, since async execution
// is queued on the PromiseJobs queue.
console.log(20);
 
// Output: 20, 10
```



# Agent

The *concurrency* and *parallelism* is implemented in ECMAScript using *Agent pattern*. The Agent pattern is very close to the [Actor pattern](https://en.wikipedia.org/wiki/Actor_model) — a *lightweight process* with *message-passing* style of communication.

==**Def. 18: Agent:** An *agent* is an abstraction encapsulating execution context stack, set of job queues, and code realms.==

Implementation dependent an agent can run on the same thread, or on a separate thread. The `Worker` agent in the browser environment is an example of the *Agent*concept.

The agents are *state isolated* from each other, and can communicate by *sending messages*. Some data can be shared though between agents, for example `SharedArrayBuffer`s. Agents can also combine into *agent clusters*.

In the example below, the `index.html` calls the `agent-smith.js` worker, passing shared chunk of memory:

```js
// In the `index.html`:
 
// Shared data between this agent, and another worker.
let sharedHeap = new SharedArrayBuffer(16);
 
// Our view of the data.
let heapArray = new Int32Array(sharedHeap);
 
// Create a new agent (worker).
let agentSmith = new Worker('agent-smith.js');
 
agentSmith.onmessage = (message) => {
  // Agent sends the index of the data it modified.
  let modifiedIndex = message.data;
 
  // Check the data is modified:
  console.log(heapArray[modifiedIndex]); // 100
};
 
// Send the shared data to the agent.
agentSmith.postMessage(sharedHeap);
```

And the worker code:

```js
// agent-smith.js
 
/**
 * Receive shared array buffer in this worker.
 */
onmessage = (message) => {
  // Worker's view of the shared data.
  let heapArray = new Int32Array(message.data);
 
  let indexToModify = 1;
  heapArray[indexToModify] = 100;
 
  // Send the index as a message back.
  postMessage(indexToModify);
};
```

You can find the full code for the example above in [this gist](https://gist.github.com/DmitrySoshnikov/b75a2dbcdb60b18fd9f05b595135dc82).

(Notice, if you run this example locally, run it in Firefox, since Chrome due to security reasons doesn’t allow loading web workers from a local file)

So below is the picture of the ECMAScript runtime:

![Figure 11](http://dmitrysoshnikov.com/wp-content/uploads/2017/11/agents-1.png)

*Figure 11. ECMAScript runtime.*





