package methodReference;

import java.util.concurrent.Callable;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

public class InterfaceReferenceTest {
    interface Greeting {
        void sayHello(Object obj);
    }

    static class Dog implements Greeting {
        @Override
        public void sayHello(Object o) {
            System.out.println("wang! " + o);
        }
    }

    static class Cat implements Greeting {
        @Override
        public void sayHello(Object o) {
            System.out.println("miao! " + o);
        }
    }

    public static void main(String[] args) {
        Cat aCat = new Cat();
        Dog aDog = new Dog();
        BiConsumer<Greeting, Object> binaryFunction = Greeting::sayHello;
        binaryFunction.accept(aCat, "chen");
        binaryFunction.accept(aDog, "chen");
    }
}
