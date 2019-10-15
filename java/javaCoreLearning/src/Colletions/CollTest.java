package Colletions;

import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Path;
import java.util.*;

public class CollTest implements A{
    public static void main(String[] args) {
        int[] rawTypes = (int[]) Array.newInstance(int.class, 5);
        for (int i = 0; i < 5; i++) {
            rawTypes[i] = i;
        }
        System.out.println(Arrays.toString(rawTypes));
    }

    @Override
    public int getNum() {
        return 0;
    }


}

interface A {
    int getNum();
}
