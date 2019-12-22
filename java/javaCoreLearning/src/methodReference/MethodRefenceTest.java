package methodReference;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class MethodRefenceTest {

    @FunctionalInterface
    interface Greeter {
        void sayHello();
    }

    static class Person {
        private String name;

        public Person(String name) {
            this.name = name;
        }

        public void sayHi(String s) {
            System.out.println("Hello, my name's " + name + ": " + s);
        }
    }

    public static void main(String[] args) {
        
    }
}
