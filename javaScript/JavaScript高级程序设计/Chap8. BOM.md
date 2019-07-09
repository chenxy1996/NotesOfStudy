[TOC]

# The Browser Object Model

## THE WINDOW OBJECT

The window object serves a dual purpose in browsers, acting as the JavaScript interface to the
browser window and the ECMAScript Global object. 

### The Global Scope

Despite global variables becoming properties of the window object, there is a slight difference between defining a global variable and defi ning a property directly on window: **global variables cannot be removed using the delete operator, while properties defined directly on window can**.

```js
var age = 29;
window.color = “red”;
//throws an error in IE < 9, returns false in all other browsers
delete window.age;
//throws an error in IE < 9, returns true in all other browsers
delete window.color; //returns true
alert(window.age); //29
alert(window.color); //undefined
```

**attempting to access an undeclared variable throws an error, but it is possible to check for the existence of a potentially undeclared variable by looking on the window `object`**. For example:

```js
//this throws an error because oldValue is undeclared
var newValue = oldValue;
//this doesn’t throw an error, because it’s a property lookup
//newValue is set to undefined
var newValue = window.oldValue;
```

### Window Relationships and Frames

### Window Position

### Window Size

Chrome

```js
window.innerHeight;
window.innerWidth;
window.outerHeight;
winodw.outerWidth;
```

### Navigating and Opening Windows

#### Popping Up Windows

#### Security Restrictions

#### Pop-up Blockers

### Intervals and TimeOuts

JavaScript execution in a browser is **single-threaded**, but does allow for the scheduling of code to run at specific points in time through the use of **timeouts** and **intervals**.

You set the timeout using the `window`'s `setTimeout()` method, which accepts two arguments: the
code to execute and the number of time (in milliseconds) to wait before attempting to execute the code.

```js
//avoid!
setTimeout(“alert(‘Hello world!’) “, 1000);
//preferred
setTimeout(function() {
	alert(“Hello world!”);
}, 1000);
```

JavaScript is single-threaded and, as such, can execute only one piece of code at a time. To manage execution, t<u>here is a **queue** of JavaScript tasks to execute. The tasks are executed in the order in which they were added to the queue. The second argument of setTimeout() tells the JavaScript engine to add this task onto the queue after a set number of milliseconds. If the queue is empty, then that code is executed immediately; if the queue is not empty, the code must wait its turn.</u>

When `setTimeout()` is called, it returns a numeric ID for the timeout. The timeout ID is a unique identifier for the scheduled code that can be used to cancel the timeout. <u>To cancel a pending timeout, use the `clearTimeout()` method and pass in the timeout ID, as in the following example:</u>

```js
//set the timeout
var timeoutId = setTimeout(function() {
    alert("Hello World!");
});

//nevermind - cnacel it
clearTimeout(timeoutId);
```

**Note:** *All code executed by a timeout runs in the global scope, so ==the value of `this` inside of the function will always point to `window` when running in notstrict mode and `undefined` when running in strict mode.==*

```js
var stu = {
    name: "chenxiangyu",
   	sayName: function() {
        alert(this.name);
    }
}

var name = "lele";

setTimeout(stu.sayName, 3000); // lele
setTimeout(stu.sayName.bind(stu), 5000); // chenxiangyu
```

Intervals work in the same way as timeouts except that they execute the code repeatedly at specific time intervals until the interval is canceled or the page is unloaded.

The `setInterval()` method lets you set up intervals, and it accepts the same arguments as `setTimeout()`: the code to execute (string or function) and the milliseconds to wait between executions. Here’s an example:

```js
//avoid!
setInterval(“alert(‘Hello world!’) “, 10000);
//preferred
setInterval(function() {
	alert(“Hello world!”);
}, 10000);
```

The setInterval() method also returns an interval ID that can be used to cancel the interval at some point in the future. The clearInterval() method can be used with this ID to cancel all pending intervals. This ability is more important for intervals than timeouts since, if left unchecked, they continue to execute until the page is unloaded. Here is a common example of interval usage:

```js
var num = 0;
var max = 10;
var intervalId = null;
function incrementNumber() {
    num++;
    //if the max has been reached, cancel all pending executions
    if (num == max) {
        clearInterval(intervalId);
        alert(”Done”);
    }
}
intervalId = setInterval(incrementNumber, 500);
```

The pattern above can also be implemented using timeouts, as shown here:

```js
var num = 0;
var max = 10;
function incrementNumber() {
    num++;
    if (num < max) {
        setTimeout(incrementNumber, 500);
    } else {
        alert("Done");
    }
}

setTimeout(incrementNumber, 500);
```

True intervals are rarely used in production environments because the time between the end of one interval and the beginning of the next is not necessarily guaranteed, and some intervals may be skipped. Using timeouts, as in the preceding example, ensures that can’t happen. Generally speaking, **it’s best to avoid intervals.**

### System Dialogs

```js
alert();
confirm();
prompt();
```

## THE LOCATION OBJECT

One of the most useful BOM object is **`location`**, which <u>provides information about the document that is currently loaded in the window, </u> as well as general navigation functionality.

The `location` object is unique <u>in that it is a property of both `window` and `document`; both `window.location` and `document.location` point to the same object.</u>

Not only does `location` know about the current loaded document, but <u>it also parse the URL into discrete segments that can be accessed via a series of properties.</u>

### Query String Arguments

```js
function getQueryStringArgs() {
    // get query string without the initial ?
    var qs = (location.search.length > 0 ? location.search.substring(1) : ""),

        // object to hold data
        args = {};

        // get individual items
        items = qs.length ? qs.split("&") : [],
        item = null,
        name = null,
        value = null,
        
        // used in for loop
        i = 0,
        len = items.length

    // assgin each item onto the args object
    for (i = 0; i < len; i++) {
        item = items[i].split("=");
        name = decodeURIComponent(item[0]);
        value = decodeURIComponent(item[1]);

        if (name.length) {
            args[name] = value;
        }
    }

    return args;
}

function getQueryStringArgs() {
    let qs = location.search.length > 0 ? location.search.substring(1) : "";
    let items = qs.length > 0 ? qs.split('&') : [];
    let args = {};

    for (let i = 0; i < items.length; i++) {
        let item = items[i].split('=');
        let name = decodeURIComponent(item[0]);
        let value = decodeURIComponent(item[1]);

        if (name) {
            args[name] = value;
        }
    }

    return args;
}
```

### Manipulating the Location

## THE NAVIGATOR OBJECT

### Detecting Plug-ins

### Registering Handlers

## THE SCREEN OBJECT

## THE HISTORY OBJECT













