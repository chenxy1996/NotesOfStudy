// Code clip 1:
for (var i = 0; i < 3; i++) {
    console.log("for1: " + i);
    (function(num) {
        setTimeout(function () {
            console.log("in1: " + num);
        }, 0)
    })(i);
}

(function() {
    for (var i = 0; i < 3; i++) {
        console.log("for2: " + i);
        setTimeout(function () {
            console.log("in2: " + i);
        }, 0)
    }
})();

(function() {
    for (let i = 0; i < 3; i++) {
        console.log("for3: " + i);
        setTimeout(function () {
            console.log("in3: " + i);
        }, 0)
    }
})();
