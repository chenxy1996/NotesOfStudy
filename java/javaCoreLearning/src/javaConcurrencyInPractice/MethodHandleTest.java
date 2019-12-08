package javaConcurrencyInPractice;

import jdk.swing.interop.SwingInterOpUtils;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;

import static java.lang.invoke.MethodHandles.lookup;

public class MethodHandleTest {
    static class ClassA {
        public void println(String s) {
            System.out.println(s + " : Class A.");
        }
    }

    static class ClassB {
        public void println(String s) {
            System.out.println(s + " : Class B.");
        }

        static void print(String s) {
            System.out.println(s + ": static.");
        }
    }

    static ClassB chen = new ClassB();

    private static MethodHandle getPrintlnMH(Object receiver) throws NoSuchMethodException, IllegalAccessException {
        MethodType mt = MethodType.methodType(void.class, String.class);
        return lookup().findVirtual(receiver.getClass(), "println", mt).bindTo(receiver);
    }

    public static void main(String[] args) throws Throwable {
        Object obj1 = new ClassA();
        Object obj2 = new ClassB();

        getPrintlnMH(obj1).invokeExact("lele");
        getPrintlnMH(obj2).invokeExact("lele");
    }
}
