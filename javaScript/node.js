var funcs = [];

// throws an error after one iteration
for (let i = 0; i < 10; i++) {
    funcs.push(function() {
        console.log(i);
    });
}

// funcs.forEach((eachFunc) => eachFunc());
Array.prototype.forEach.call(funcs, (eachFunc) => eachFunc());