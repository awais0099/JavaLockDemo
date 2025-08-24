package com.example;

import java.util.concurrent.locks.ReentrantLock;

public class CounterWithLock {
    private int count = 0;
    private final ReentrantLock lock = new ReentrantLock();

    public void increment() {
        lock.lock(); // Blocks until lock is acquired
        try {
            count++;
        } finally {
            lock.unlock(); // Always release in finally
        }
    }

    public int getCount() {
        return count;
    }

    public static void main(String[] args) throws InterruptedException {
        CounterWithLock counter = new CounterWithLock();
        System.out.println("Initial count: " + counter.getCount());

        Runnable task = () -> {
            for (int i = 0; i < 1000; i++) {
                counter.increment();
            }
        };

        Thread t1 = new Thread(task);
        Thread t2 = new Thread(task);

        t1.start();
        t2.start();
        t1.join();
        t2.join();

        System.out.println("Final count: " + counter.getCount()); // Should be 2000
    }
}