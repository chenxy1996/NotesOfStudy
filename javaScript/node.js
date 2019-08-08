function defaultParam(a=1, b={name: "chen", age: 22}, c=[1, 2, 3]) {
    console.log(
        a,
        b,
        c,
    );

    b.age += 1;
    c.push(5);

    console.log(
        a,
        b,
        c,
    );
}

function mixArgs(first, second = "b") {
    console.log(arguments.length);
    console.log(first === arguments[0]);
    console.log(second === arguments[1]);
    first = "c";
    second = "d";
    console.log(first === arguments[0]);
    console.log(second === arguments[1]);
}


function pick(object, ...keys) {
    console.log(
        arguments.length,
        keys.length
    );
}

let newPerson = (function(name) {
    return {
        sayName: () => name
    }
})("chen");

console.log(newPerson.sayName());