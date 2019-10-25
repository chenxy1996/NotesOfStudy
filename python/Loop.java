class Loop {
    public static final int ca = 7;
    public static int cb = 17;
    
    public String s = "a";

    public int spin() {
        String a = "a";
        String b = "b";
        String c = "c";
        String abc = "a" + "b" + "c";

        int i;
        for (i = 5; i < 10; i++) {
            ;
        }
        return i;
    }

    public static void main(String[] args) {
        int i = 7;
        Loop aLoop = new Loop();
        aLoop.spin();
        System.out.print(ca);
    }
}