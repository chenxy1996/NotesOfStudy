class Student {
    constructor(name) {
        this.name = name;
    }

    sayName() {
        return this.name;
    }
}

const aStudent = new Student("chenxiangyu");
const bStudent = new Student("chenxiangyu");

const raw = "chen";
const aString = new String(raw);
const bString = new String(raw);

console.log(
    aString === bString,

    typeof raw,
    typeof aString,

    Object.is(aString, bString),
    Object.is(aStudent, bStudent),

    raw,
    aString,
    
    aStudent
);