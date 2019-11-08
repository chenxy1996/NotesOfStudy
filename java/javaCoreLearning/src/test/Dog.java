package test;

public class Dog extends Animal{
    public static final int nums = 20;
    public String name;


    public Dog(String name) {
        super();
        this.name = name;
    }

    @Override
    public String sayHello() {return "Hello, I'm a dog";}

    public static String getSpecies() {return "Dog";}

    public static void main(String[] args) {
        Animal aDog = new Dog("lele");
        System.out.println(aDog.name);
        System.out.println(aDog.nums);
        System.out.println(aDog.getSpecies());

//        System.out.println(aDog.getSpecies());
    }
}
