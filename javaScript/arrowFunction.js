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

let createNewPerson = (name, age) => (
    {
        name,
        age
    }
)

let aPerson = createNewPerson("chen", 23);
let bPerson = createNewPerson("lele", 10);

console.log(
    aPerson,
    bPerson
)

