let fs = require("fs");

// function readFile(filename) {
//     return function(callback) {
//         fs.readFile(filename, "utf-8", callback);
//     };
// }

// function run(taskDef) {

//     // create the iterator, make available elsewhere
//     let task = taskDef();

//     // start the task
//     let result = task.next();

//     // recursive function to keep calling next()
//     function step() {

//         // if there's more to do
//         if (!result.done) {
//             if (typeof result.value === "function") {
//                 result.value(function(err, data) {
//                     if (err) {
//                         result = task.throw(err);
//                         return;
//                     }
//                     console.log(data);
//                     result = task.next(data);
//                     step();
//                 });
//             } else {
//                 result = task.next(result.value);
//                 step();
//             }
//         }
//     }

//     // start the process
//     step();

// }

// run(function*() {
//     let contents = yield readFile("test.txt");
//     console.log("hehe");
//     console.log("Done");
// });

function run(taskDef) {
    let task = taskDef();

    let result = task.next();

    function step() {
        if (!result.done) {
            if ((typeof result.value) === "function") {
                result.value(function(err, data) {
                    if (err) {
                        console.log(err);
                    } else {
                        console.log(data);
                        result = task.next()
                        step();
                    }
                })
            } else {
                result = task.next();
                step();
            }
        }
    }

    step();
}

function readFile(path) {
    return function(callback) {
        fs.readFile(path, "utf-8", callback);
    }
}

run(function*(){
    yield readFile("test.txt");

    yield console.log("2");
})

function asyncTask(taskDescription, callback) {
    console.log("before " + taskDescription);

    setTimeout(function() {
        console.log("this is an asynct task: " + taskDescription);
        callback && callback();
    }, Math.random() * 1000);
    console.log("after " + taskDescription);
}

asyncTask("task a", function() {
    asyncTask("task b", function() {
        asyncTask("task c");
    });
});