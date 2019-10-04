package myReflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Objects;

public class MemberGetter {
    public static StringBuilder getClassInfo(Class cls) {
        Objects.requireNonNull(cls);
        String s = Modifier.toString(cls.getModifiers()) + ' '
                 + cls.getName() + " {" + '}';
        return new StringBuilder(s);
    }

    public static StringBuilder getDeclaredFieldsInfo(Class cls) {
        StringBuilder wrapper = getClassInfo(cls);
        String s = '\n' + getFields(cls);
        return wrapper.insert(wrapper.length() - 1, s);
    }

    public static StringBuilder getDeclaredConstructorInfo(Class cls) {
        StringBuilder wrapper = getClassInfo(cls);
        String s = '\n' + getConstructors(cls);
        return wrapper.insert(wrapper.length() - 1, s);
    }

    public static StringBuilder getMembersInfo(Class cls) {
        StringBuilder wrapper = getClassInfo(cls);
        String s = '\n' + getFields(cls) + '\n' + getConstructors(cls) + '\n'
                + getMethods(cls);
        return wrapper.insert(wrapper.length() - 1, s);
    }

    public static StringBuilder getDeclaredMethodsInfo(Class cls) {
        StringBuilder wrapper = getClassInfo(cls);
        String s = '\n' + getMethods(cls);
        return wrapper.insert(wrapper.length() - 1, s);
    }

    private static String getFields(Class cls) {
        String s = "";
        Field[] fields = cls.getDeclaredFields();

        for (Field f : fields) {
            String mod = Modifier.toString(f.getModifiers());
            String type = f.getType().getName();
            String name = f.getName();
            s += "      " + mod  + ' ' + type  + ' ' + name + ";\n";
        }

        return s;
    }

    private static String getConstructors(Class cls) {
        String s = "";
        Constructor[] constructors = cls.getDeclaredConstructors();

        for (Constructor cons : constructors) {
            String mod = Modifier.toString(cons.getModifiers());
            String name = cons.getName();

            String sig = Arrays.toString(cons.getParameterTypes());
            sig = sig.replace('[', '(');
            sig = sig.replace(']', ')');

            s += "      " + mod  + ' ' + name + sig + ";\n";
        }

        return s;
    }

    private static String getMethods(Class cls) {
        String s = "";
        Method[] methods = cls.getDeclaredMethods();

        for (Method met : methods) {
            String mod = Modifier.toString(met.getModifiers());
            String name = met.getName();
            String ret = met.getReturnType().getName();

            String sig = Arrays.toString(met.getParameterTypes());
            sig = sig.replace('[', '(');
            sig = sig.replace(']', ')');

            s += "      " + mod + ' ' + ret + ' ' +  name + sig + ";\n";
        }

        return s;
    }
}
