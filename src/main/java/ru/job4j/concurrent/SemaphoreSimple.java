package ru.job4j.concurrent;

import java.util.concurrent.Semaphore;

public class SemaphoreSimple {
    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(3);
        try {
            semaphore.acquire();
            semaphore.acquire();
            semaphore.acquire();
            System.out.println( "All permits have been acquired");
            semaphore.acquire();
            System.out.println("Can not reach here....");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        semaphore.release();
        System.out.println(semaphore.availablePermits());
    }
}
