package concurrency;


public class ConcurrencyTest {

    private static final long count = 1000000001;

    public static void main(String[] args) throws  InterruptedException {
        concureency();
        serial();
    }

    private static void concureency() throws  InterruptedException {

        long start = System.currentTimeMillis();
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                int a = 0;
                for(long i = 0; i < count; i++) {
                    a += 5;
                }

            }
        });
        thread.start();
        Thread thread1 = new Thread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                int a = 0;
                for(long i = 0; i < count; i++) {
                    a += 5;
                }

            }
        });
        thread1.start();
        int b = 0;
        for(long i = 0; i < count; i++) {
            b --;
        }
        thread.join();
        long time = System.currentTimeMillis() - start;
        System.out.println("concureency :" + time+"ms,b="+b);
    }

    private static void serial() {
        long start = System.currentTimeMillis();
        int a = 0;
        for (long i = 0; i < count; i++) {
            a += 5;
        }
        int b = 0;
        for (long i = 0; i < count; i++) {
            b--;
        }
        b = 0;
        for (long i = 0; i < count; i++) {
            b--;
        }
        long time = System.currentTimeMillis() - start;
        System.out.println("serial:" + time+"ms,b="+b+",a="+a);
    }
}
