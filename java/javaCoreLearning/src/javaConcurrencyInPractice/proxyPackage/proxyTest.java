package javaConcurrencyInPractice.proxyPackage;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

public class proxyTest {
    /*
    接口
     */
    interface Animal {
        void getSpecies();
    }

    interface Shout {
        void toShout();
    }

    static class Dog implements Animal, Shout{
        @Override
        public void getSpecies() {
            System.out.println("This is a dog!");
        }

        @Override
        public void toShout() {
            System.out.println("wang!");
        }
    }

    static class Cat implements Animal {
        @Override
        public void getSpecies() {
            System.out.println("This is a cat!");
        }
    }

    static void saySpecies(Animal animal) {
        animal.getSpecies();
    }

    public static void main(String[] args) {
        Dog aDog = new Dog();

        InvocationHandler handler = (Object proxy, Method method, Object[] arguments) -> {
            try {
                System.out.println("handler enter!");
                return method.invoke(aDog, arguments);
            } finally {
                System.out.println("handler out!");
            }
        };

        Animal dog = (Animal) Proxy.newProxyInstance(aDog.getClass().getClassLoader(), aDog.getClass().getInterfaces(), handler);
        dog.getSpecies();
        ((Shout) dog).toShout();
        System.out.println(Dog.class.getClassLoader());
        System.out.println(Arrays.toString(aDog.getClass().getInterfaces()));
    }
}
