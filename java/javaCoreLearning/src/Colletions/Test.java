package Colletions;

import java.util.*;
import java.util.function.Consumer;

public class Test {
    public static void main(String[] args) {
        List<String> al = new ArrayList<>();
        ListIterator<String> iter = al.listIterator();

        iter.add("lele");
        System.out.println(iter.previous());
        iter.add("chen");
        iter.add("xiaobai");
        System.out.println(al);

        String[] array = al.toArray(new String[0]);
        System.out.println(Arrays.toString(array));
    }
}
