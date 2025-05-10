package com.example;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MicroserviceExample {

    private static final int NUM_THREADS = 10;

    public static void main(String[] args) {

        try (ExecutorService executorService = Executors.newFixedThreadPool(NUM_THREADS)) {

            for (int i = 0; i < 10; i++) {

                executorService.submit(() -> {

                    // Simulate processing time
                    try {
                        Thread.sleep(600);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }

                    System.out.println("Request processed by " + Thread.currentThread().getName());
                });
            }
        }
    }
}
