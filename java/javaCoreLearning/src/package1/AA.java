package package1;

public class AA {
    public int x = 10;
    public static final int y = 11;


    public static void main(String[] args) {
        AA obj = new BB();
        System.out.println(obj.x);
        System.out.println(obj.y);
    }
}

class BB extends AA {
    public int x = 11;
    public static final int y = 12;
}
