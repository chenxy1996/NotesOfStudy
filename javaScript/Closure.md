# A very interesting example:
```js
var a = 99;
function fun1() {
    var a = 3;
    function fun2() {
        if (a === 3)
            a += 7;
        else
            a += 5;
        return a;
    }
    return fun2;
}

fun3 = fun1();
fun3(); // 10;
fun3(); // 15
```