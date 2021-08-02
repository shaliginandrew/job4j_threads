package ru.job4j.concurrent;

public class Test {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new Runner());
        thread.start();
        System.out.println("Hello from main thread");

    }
}

class Runner implements Runnable {
    public void run() {
        for (int i = 0; i < 1000; i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Hello from MyThread" + i);
        }
    }
}

