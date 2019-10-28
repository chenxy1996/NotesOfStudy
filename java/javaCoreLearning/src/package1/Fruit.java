package package1;

class Fruit {
    static int x = 10;
    static BigWaterMelon bigWaterMelon_1 = new BigWaterMelon(x);

    int y = 20;
    BigWaterMelon bigWaterMelon_2 = new BigWaterMelon(y);

    public static void main(String[] args) {
        Fruit fruit = new Fruit();
        int x = 100;

        int z = 30;
        BigWaterMelon bigWaterMelon_3 = new BigWaterMelon(z);

        new Thread() {
            @Override
            public void run() {
                int k = 100;
                setWeight(k);
                System.out.println(x);
            }

            void setWeight(int waterMelonWeight) {
                fruit.bigWaterMelon_2.weight = waterMelonWeight;
            }
        }.start();
    }
}

class BigWaterMelon {
    public BigWaterMelon(int weight) {
        this.weight = weight;
    }

    public int weight;
}