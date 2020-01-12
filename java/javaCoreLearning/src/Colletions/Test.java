package Colletions;

import interfaceTest.Interface;
import org.w3c.dom.ls.LSOutput;

import java.util.*;
import java.util.function.Consumer;

public class Test {
    public static void main(String[] args) {
        Set<Integer>  set = new TreeSet<>();
        set.add(3);
        set.add(7);
        set.add(1);
        set.add(8);
        set.add(2);
        set.add(9);
        Integer[] array = set.toArray(Integer[]::new);
        System.out.println(Arrays.toString(array));
//        List<Integer> list = new ArrayList<>(set);
    }
}
