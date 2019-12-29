package methodReference;

public class polymorphicTest {
    static class SupClass {
        public void saySomething() {
            System.out.println("I'm SupClass!");
        }
    }

    static class SubClass extends SupClass {
        @Override
        public void saySomething() {
            System.out.println("I'm SubClass");
        }

        public void saySomethingElse() {
            System.out.println("This is another infomation");
        }
    }

    public static void main(String[] args) {

    }
}
