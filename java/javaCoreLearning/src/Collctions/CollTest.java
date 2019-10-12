package Collctions;

import java.util.*;

public class CollTest {
    public static void main(String[] args) {
        ArrayList<Integer> a = new ArrayList<>();
        a.add(null);
        a.add(3);
        System.out.println(a.contains(null));
    }
}

class Outer {
    private int num = 10;

    private class Inner implements A{
        @Override
        public int getNum() {
            return num;
        }
    }

    public Inner bridge() {
        return new Inner();
    }
}

interface A {
    public int getNum();
}
