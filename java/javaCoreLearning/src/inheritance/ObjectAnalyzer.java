package inheritance;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class ObjectAnalyzer {
    public static void main(String[] args) throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Employee[] staff = new Employee[2];
        staff[0] = new Employee("chen", 90000, 2021, 9, 1);
        staff[1] = new Employee("lele", 100000, 2022, 9, 1);
        Field name = Person.class.getDeclaredField("name");
        name.setAccessible(true);
        System.out.println(name.get(staff[0]));
    }

    public static Object googCopy(Object a, int newLength) {
        Class cls = a.getClass();
        // 判断 a 是不是 array
        if (!(cls.isArray())) {
            return null;
        }
        Class componentType = cls.getComponentType();
        // 这里因为 a 的 type 是 Object 不是数组，所以只能用
        // Array.getLength(a) 这个静态方法，不能使用 a.length
        int length = Array.getLength(a);
        Object newArray = Array.newInstance(componentType, length);
        System.arraycopy(a, 0, newArray, 0, Math.min(length, newLength));
        return newArray;
    }
}
