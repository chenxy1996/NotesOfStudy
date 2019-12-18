package Colletions;

public class Person implements ParentInterface, ChildInterface {
    @Override
    public void sayName() {
        System.out.println("chen");
    }

    @Override
    public void sayAge() {
        System.out.println(22);
    }

    public static void main(String[] args) {
        ParentInterface aPerson = new Person();
        ((ChildInterface) aPerson).sayAge();
    }
}
