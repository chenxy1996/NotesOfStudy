package Collctions;

import java.util.ArrayList;
import java.util.Queue;

public class CollTest {
    public static void main(String[] args) {
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
