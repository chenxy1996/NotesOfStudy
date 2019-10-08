package generic.pair1;

public class SubPair extends Pair<String>{
    public static void main(String[] args) {
        Object a = "abcd";
        String s = (String) a;
        System.out.println(a + ", " +s);
    }
}
