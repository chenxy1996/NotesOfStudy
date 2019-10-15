package Colletions;

import netscape.javascript.JSObject;

import java.lang.reflect.Array;
import java.util.*;

public class GoldenRetriver extends Dog {
    public GoldenRetriver(String name, int age) {
        super(name, age);
    }

    public static void main(String[] args) {
        String[] s = {"chen", "lele"};
        HashSet<String> stringSet = new HashSet<>(Arrays.asList(s));
        String[] ns = stringSet.toArray(new String[0]);
        System.out.println(Arrays.toString(ns));
    }
}
