package ru.job4j.concurrent;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DeadLockSimple {
    public static void main(String[] args) throws InterruptedException {
        Runners runners = new Runners();
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                runners.firstThread();
            }
        });
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                runners.secondThread();
            }
        });
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        runners.finishedThread();
    }
}

class Runners {
    private Account account1 = new Account();
    private Account account2 = new Account();
    private Lock lock1 = new ReentrantLock();
    private Lock lock2 = new ReentrantLock();

    private void takeLocks(Lock lock1, Lock lock2) {
        boolean firstLockTaken = false;
        boolean secondLockTaken = false;

        while (true) {
            try {
                firstLockTaken = lock1.tryLock();
                secondLockTaken = lock2.tryLock();
            } finally {
                if (firstLockTaken && secondLockTaken) {
                    return;
                }
                if (firstLockTaken) {
                    lock1.unlock();
                }
                if (secondLockTaken) {
                    lock2.unlock();
                }
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public void firstThread() {
        Random random = new Random();

        for (int i = 0; i < 10000; i++) {
           takeLocks(lock1, lock2);
            try {
                Account.transfer(account1, account2, random.nextInt(100));
            } finally {
                lock1.unlock();
                lock2.unlock();
            }
        }
    }

    public void secondThread() {
        Random random = new Random();
        for (int i = 0; i < 10000; i++) {
            takeLocks(lock2, lock1);
            try {
                Account.transfer(account2, account1, random.nextInt(100));
            } finally {
                lock1.unlock();
                lock2.unlock();
            }
        }
    }

    public void finishedThread() {
        System.out.println(account1.getBalance());
        System.out.println(account2.getBalance());
        System.out.println("Total balance = " + (account1.getBalance() + account2.getBalance()));

    }

}

class Account {

    private int balance = 10000;

    public void deposite(int ammount) {
        balance += ammount;
    }

    public void withdrow(int ammount) {
        balance -= ammount;
    }

    public int getBalance() {
        return balance;
    }

    public static void transfer(Account acc1, Account acc2, int ammount) {
        acc1.withdrow(ammount);
        acc2.deposite(ammount);

    }
}
