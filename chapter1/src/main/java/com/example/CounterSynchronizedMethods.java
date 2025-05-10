package com.example;

public class CounterSynchronizedMethods {

    private static class SynchronizedCounter {

        private int count = 0;

        public synchronized void increment() {
            this.count++;
        }

        public synchronized int getCount() {
            return this.count;
        }
    }

    private static class CounterThread extends Thread {

        private final SynchronizedCounter synchronizedCounter;

        public CounterThread(SynchronizedCounter synchronizedCounter) {
            this.synchronizedCounter = synchronizedCounter;
        }

        @Override
        public void run() {
            for (int i = 0; i < 1000; i++) {
                synchronizedCounter.increment();
            }

            System.out.println("Thread " + Thread.currentThread().getName() + " finished processing");
        }
    }

    private static void printThreadState(Thread thread) {
        System.out.println("Thread " + thread.getName() + " state is " + thread.getState());
    }

    public static void main(String[] args) {

        SynchronizedCounter synchronizedCounter = new SynchronizedCounter();
        CounterThread counterThread1 = new CounterThread(synchronizedCounter);
        CounterThread counterThread2 = new CounterThread(synchronizedCounter);

        System.out.println("Created threads 1 and 2");
        printThreadState(counterThread1);
        printThreadState(counterThread2);

        System.out.println("Starting threads 1 and 2");
        counterThread1.start();
        counterThread2.start();
        printThreadState(counterThread1);
        printThreadState(counterThread2);


        try {

            // Wait for the threads to terminate / die
            System.out.println("Joining threads 1 and 2");
            counterThread1.join();
            counterThread2.join();

            printThreadState(counterThread1);
            printThreadState(counterThread2);
            System.out.println("Final count: " + synchronizedCounter.getCount());

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

    }
}
