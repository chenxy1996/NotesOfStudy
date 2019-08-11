module.exports.x = 10;

let foo = {
    x: 7,

    bar: () => this.x,

    baz() {
        return () => this.x
    }
}

console.log(
    foo.bar(),
    foo.baz()()
)

