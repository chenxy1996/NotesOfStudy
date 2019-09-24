package inheritance;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;

public class ReflectionTest {
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        int[] nums = {1, 2, 3};
        int[] nums1 = new int[10];
        System.out.println(Arrays.toString(nums1));
        int[] nums2 = Arrays.copyOf(nums, 10);
        System.out.println(Arrays.toString(nums2));
    }
}
