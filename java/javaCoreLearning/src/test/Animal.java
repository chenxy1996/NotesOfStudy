package test;

public class Animal implements Greeting{
    public static int nums = 10;
    public String name = "Class of Animal";

    public Animal() {
        System.out.println(this.sayHello());
    }

    @Override
    public String sayHello() {return "Hello, I'm an anmial!";}

    public static String getSpecies() {return "Animal";}
}
