package interfaceTest;

import inheritance.Manager;
import org.jetbrains.annotations.NotNull;
import inheritance.Person;
import inheritance.Employee;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.IntPredicate;


public class InterfaceTest implements Interface, Interface1{
    private String name = "chen";

    @Override
    public String getName() {
        return Interface1.super.getName();
    }

    public static void main(String[] args) throws CloneNotSupportedException {
        IntPredicate ifIntGreaterThanTree = (a) -> {
            if (a > 3) return true;
            return false;
        };

        System.out.println(ifIntGreaterThanTree.test(7));
    }
}
