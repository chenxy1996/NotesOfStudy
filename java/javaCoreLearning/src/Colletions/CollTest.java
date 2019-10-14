package Colletions;

import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

public class CollTest {
    public static void main(String[] args) {
        Integer[] nums = new Integer[] {1, 2, 3};
        List<Integer> numsList = Arrays.asList(nums);
        System.out.println(numsList.getClass().getName());
    }
}

interface A {
    public int getNum();
}
