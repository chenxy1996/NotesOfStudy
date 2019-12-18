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
        System.out.println(al);
        iter.remove();
    }
}
