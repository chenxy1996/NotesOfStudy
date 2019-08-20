"use strict"

const fs = require("fs");

function readFile(filePath) {
    return new Promise((resolve, reject) => {
        console.log("first");
        fs.readFile(filePath, {encoding: "utf-8"}, function(err, data) {
            if (err) {
                reject(err);
                return;
            }
            resolve(data);
        });
    });
}

const promise = readFile("test.txt");

promise.then((data) => {
    console.log(data);

    promise.then((data) => console.log(data));
});

console.log("hehe");
