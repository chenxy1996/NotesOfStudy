module.exports.x = 10;

let foo = {
    x: 20, 

    bar: function() {
        this.baz = () => this.x
    },

    fiz: () => this.x
}

let newObj = {
    x: 30
};

// newObj.func = foo.bar;
// newObj.func();
// console.log(newObj.baz());

let obj1 = {
    
}