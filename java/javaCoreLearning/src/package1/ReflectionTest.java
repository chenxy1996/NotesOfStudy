package package1;
import inheritance.*;

import java.lang.reflect.Field;
import java.util.Arrays;

public class ReflectionTest {
    public static void main(String[] args) throws IllegalAccessException {
        Manager manager = new Manager("chenxiangyu", 23, 2021, 9, 1);
        Class personCls = Person.class;
        System.out.println(Arrays.toString(personCls.getDeclaredFields()));
        Field nameField = personCls.getDeclaredFields()[0];
        nameField.setAccessible(true);
        System.out.println(nameField.get(manager));
        System.out.println(manager);
        nameField.set(manager, "chen and lele");
        System.out.println(manager);
        System.out.println(manager.getName());
    }
}
