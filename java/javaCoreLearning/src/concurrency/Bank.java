package concurrency;

/*
A lock object can have one or more asscociated condition objects. You obtain a condition
object with the newCondition method. It is customary to give each condition object a name
that evokes the condition that it represents. For example, here we set up a condition
object to represent the "sufficient funds" condition.

There is an essential difference between a thread that is waiting to acquire a lock and
a thread that has called await. Once a thread calls the await method, it enters a wait
set for that condition. The thread is not made runnable when the lock is available.
Instead, it stays deactivated until another thread has called the signalAll method on
the same condition.
 */

import java.util.Collections;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Bank {
    private static int MAX_ACCOUNT_NUMBER =10;
    private double[] accounts;
    private static double MAX_AMOUNT = 100;
    private static int DELAY = 10;

//    private Lock bankLock;
//    private Condition sufficientFunds;

    public Bank() {
        accounts = new double[MAX_ACCOUNT_NUMBER];

        for (int i = 0; i < MAX_ACCOUNT_NUMBER; i++) {
            accounts[i] = MAX_AMOUNT;
        }

//        bankLock = new ReentrantLock();
//        sufficientFunds = bankLock.newCondition();
    }

    public int size() {
        return MAX_ACCOUNT_NUMBER;
    }

    public double getTotalBalance() {
        double sum = 0;
        for (double account : accounts) {
            sum += account;
        }
        return sum;
    }

    public double getBalance(int from) {
        return accounts[from];
    }

    /**
     * Transfer money from one account to another.
     * @param from the account to transfer from
     * @param to the account to transfer to
     * @param amount the amount to transfer
     */
    public synchronized void transfer(int from, int to, double amount) throws InterruptedException {
        while (accounts[from] < amount) {
            wait();
        }
        System.out.print(Thread.currentThread());
        accounts[from] -= amount;
        System.out.printf(" %10.2f from %d to %d", amount, from, to);
        accounts[to] += amount;
        System.out.printf(" Total Balance: %10.2f%n", getTotalBalance());

        // check for the state of the lock condition.
        notifyAll();
    }

    public static void main(String[] args) {
        System.out.println("111");
        Bank bank = new Bank();
        System.out.println("222");
        for (int i = 0; i < MAX_ACCOUNT_NUMBER; i++) {
            int fromAccount = i;

            Runnable r = () -> {
                try {
                    while (true) {
                        int toAccount = (int) (bank.size() * Math.random());
                        double amount = MAX_AMOUNT * Math.random();
                        if (bank.getBalance(fromAccount) >= amount) {
                            bank.transfer(fromAccount, toAccount, amount);
                        }
                        Thread.sleep(DELAY);
                    }
                } catch (InterruptedException e) {
                    System.out.println(e.getCause());
                }
            };

            Thread t = new Thread(r);
            t.start();
        }
    }
}
