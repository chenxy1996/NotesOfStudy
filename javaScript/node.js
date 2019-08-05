var funcs = [];

// throws an error after one iteration
for (let i = 0; i < 10; i++) {
    funcs.push(function() {
        console.log(i, j);
    });
}

funcs.forEach((eachFunc) => eachFunc());