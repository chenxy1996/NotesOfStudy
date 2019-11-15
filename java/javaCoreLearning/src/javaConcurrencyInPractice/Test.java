package javaConcurrencyInPractice;

import java.util.concurrent.CountDownLatch;

public class Test extends Thread implements Runnable{
    public Object obj = new Object();

    @Override
    public void run() {
        try {
            boolean flag = true;
            while (!Thread.currentThread().isInterrupted()) {
                if (flag) {
                    System.out.println("in run() - about to sleep for 20 seconds.");
                    flag = false;
                }
                synchronized (obj) {
                    System.out.println();
                }
                Thread.sleep(20000);
            }

        } catch (InterruptedException e) {
//            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) {
        Test t = new Test();
        t.start();
        synchronized (t.obj) {
        }
    }


}
