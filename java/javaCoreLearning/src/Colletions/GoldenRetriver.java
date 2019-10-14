package Colletions;

public class GoldenRetriver extends Dog {
    public GoldenRetriver(String name, int age) {
        super(name, age);
    }

    public static void main(String[] args) {
        Dog dog1 = new GoldenRetriver("lele", 1);
        System.out.println(dog1.score);
    }
}
