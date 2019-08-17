let fs = require("fs");

function *procdeuceIterator() {
    yield fs.readFile("./test.txt", "utf-8", function(err, contents) {
        if (err) {
            console.log(err);
        } else {
            console.log(contents);
        }
    })

    yield console.log("The inner content has been shown above.")
} 


function run(iteratorfunc) {
    let testQueue = iteratorfunc();

    let nextProc = testQueue.next();

    function step() {
        if (!nextProc.done) {
            nextProc = testQueue.next();
            step();
        }
    }

    step();
}

run(procdeuceIterator);
