package ru.job4j.concurrent;

import java.util.Scanner;

public class VolatileSimple {
    public static void main(String[] args) {
        MyThread myThread = new MyThread();
        myThread.start();
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        myThread.shutdown();
    }
}

class MyThread extends Thread {
    private volatile boolean running = true;
    public void run() {
        while (running) {
            System.out.println("Hello");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void shutdown() {
        this.running = false;
    }
}
