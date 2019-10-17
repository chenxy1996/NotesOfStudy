package concurrency;

public class Test extends Super {
    int three = (int) Math.PI;

    @Override
    void printThree() {
        System.out.println(three);
    }

    public static void main(String[] args) {
        Super t = new Test();
        t.printThree();
    }
}
