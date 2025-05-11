package com.example;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class PrinterManager {

    private final ReentrantLock printerLock = new ReentrantLock();
    private final Condition readyCondition = printerLock.newCondition();
    private boolean isPrinterReady = false;

    public void makePrinterReady() {

        printerLock.lock();

        try {

            isPrinterReady = true;

            // Signal one waiting thread that the printer is ready
            readyCondition.signal();

        } finally {
            printerLock.unlock();
        }
    }

    public void printDocument(String document) {

        printerLock.lock();
        try {

            // Wait until the printer is ready
            while (!isPrinterReady) {

                System.out.println(Thread.currentThread().getName() + " waiting for printer to be ready.");
                if (!readyCondition.await(5000, TimeUnit.MILLISECONDS)) {
                    System.out.println(
                            Thread.currentThread().getName() + " could not print. " +
                            "Timeout while waiting for the printer to be ready");
                    return;
                }
            }

            // Printer is ready. Proceed to print the document
            System.out.println(Thread.currentThread().getName() + " is printing: " + document);

            // Simulate printing time
            Thread.sleep(1000);
            isPrinterReady = false;

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            printerLock.unlock();
        }
    }

    public static void main(String[] args) {

        PrinterManager printerManager = new PrinterManager();

        Thread worker1 = new Thread(() -> printerManager.printDocument("Document 1"), "Worker-1");
        Thread worker2 = new Thread(() -> printerManager.printDocument("Document 2"), "Worker-2");
        Thread worker3 = new Thread(() -> printerManager.printDocument("Document 3"), "Worker-3");

        worker1.start();
        worker2.start();
        worker3.start();

        new Thread(() -> {
            try {
                // Simulate some delay
                Thread.sleep(2000);
                printerManager.makePrinterReady();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
    }
}
