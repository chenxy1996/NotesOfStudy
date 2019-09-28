package interfaceTest;

import inheritance.Manager;
import org.jetbrains.annotations.NotNull;
import inheritance.Person;
import inheritance.Employee;

public class InterfaceTest implements Interface, Interface1{
    private String name = "chen";

    @Override
    public String getName() {
        return Interface1.super.getName();
    }

    public static void main(String[] args) throws CloneNotSupportedException {
        Manager a = new Manager("chenxiangyu", 200000, 2021, 9, 1);
        System.out.println(a);
        Manager b = (Manager) a.clone();
        System.out.println(b);
        System.out.println(a.equals(b));
    }
}
