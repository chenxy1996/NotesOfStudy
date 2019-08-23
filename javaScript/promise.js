"use strict"

// let rejected;

// process.on("rejectionHandled", function(promise) {
//     console.log(rejected === promise);              // true
// });

// rejected = Promise.reject(new Error("Explosion!"));

// wait to add the rejection handler
// setTimeout(function() {
//     rejected.catch(function(value) {
//         console.log(value.message);     // "Explosion!"
//     });
// }, 1000);

const newObj = {
    name: "chen",
    age: 23,
    school: {
        name: "WHU"
    }
}

let school = newObj.school;
school = "HNU";

const newObj1 = {...newObj, sex: "male"};

console.log(newObj1);


