package javaConcurrencyInPractice.syncCacheTest;

public class DataHandler implements Processor<Integer, String> {
    @Override
    public String process(Integer num) throws InterruptedException {
        Thread.sleep(300);
        String s = "";
        for (int i = 0; i < 10; i++) {
            s += (char) (num + 65 + i);
        }
        return s;
    }

    public static void main(String[] args) {
        System.out.println((int) 'A');
    }
}
