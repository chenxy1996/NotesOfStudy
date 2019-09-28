package interfaceTest;

import java.util.Arrays;
import java.util.Comparator;

public class LengthComparator implements Comparator<String> {

    @Override
    public int compare(String o1, String o2) {
        return Integer.compare(o1.length(), o2.length());
    }

    public static void main(String[] args) {
        LengthComparator a = new LengthComparator();
        String[] strings = {"lele", "chenxaingyu", "ahuang", "xiaobai"};
        Arrays.sort(strings, a);
        System.out.println(Arrays.toString(strings));
    }
}
