package methodReference;

import java.util.function.Consumer;

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
            System.out.println("")
        }
    }

    public static void main(String[] args) {
        Consumer<? super SupClass> cons = SubClass::saySomething;
        // cons.accept(? extends SupClass)
        // 匹配过程：
        // 1. cons.accept(new SupClass());
        // 2. cons.accept(mew SubClass());
        cons.accept(new SupClass());
        cons.accept(new SubClass());
    }
}
