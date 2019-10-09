package generic.pair1;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.function.Function;
import java.util.function.IntConsumer;
import java.util.function.IntFunction;

import interfaceTest.Comparable;
import myReflection.MemberGetter;
import inheritance.*;

public class PairTest {
    public static void main(String[] args) {

    }
}

class ArrayAlg {
    public static <T extends Comparable> Pair<T> minmax(T[] a) {
        if (a == null || a.length == 0) return null;
        T min = a[0];
        T max = a[0];

        for (int i = 0; i < a.length; i++) {
            if (min.compareTo(a[i]) > 0) min = a[i];
            if (max.compareTo(a[i]) < 0) max = a[i];
        }

        return new Pair<T>(min, max);
    }

    @SafeVarargs
    public static <T> void addAll(Collection<T> coll, T... ts) {
        for (T t : ts) {
            coll.add(t);
        }
    }

    public static <T extends Comparable> T[] minmax1(IntFunction<T[]> constr, T... a)  {
        if (a == null || a.length == 0) return null;
        T min = a[0];
        T max = a[0];

        System.out.println(min.getClass().getName());
        System.out.println(max.getClass().getName());

        for (int i = 0; i < a.length; i++) {
            if (min.compareTo(a[i]) > 0) min = a[i];
            if (max.compareTo(a[i]) < 0) max = a[i];
        }

        T[] ret = constr.apply(2);
        ret[0] = min;
        ret[1] = max;

        return ret;
    }

    public static void printBuddies(Pair<? extends Employee> p) {
        Employee first = p.getFirst();
        Employee second = p.getSecond();
        System.out.println(first.getName() + ", " + second.getName());
    }
}
