class Loop {
    public static final int a = 7;

    public int spin() {
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
        System.out.print(a);
    }
}