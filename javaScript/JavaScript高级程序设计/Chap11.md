```js
var i,
    len,
    child = element.firstChild;
while (child) {
    if (child.nodeType == 1) {
        processChild(child);
    }
    child = child.nextSibling;
}
```

```js
var i,
    len,
    child = element.firstElementChild;
while (child) {
    processChild(child);
    child = child.nextElementSibling;
}
```

