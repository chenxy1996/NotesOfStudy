package concurrency;

public class Super {
    Super() {
        printThree();
    }

    void printThree() {
        System.out.println("three");
    }

    public static void main(String[] args) {
        System.out.println("hello, world!");
    }
}
