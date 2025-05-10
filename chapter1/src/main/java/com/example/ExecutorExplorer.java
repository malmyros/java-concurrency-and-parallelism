package com.example;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorExplorer {

    public static void main(String[] args) {

        try (ExecutorService executorService = Executors.newSingleThreadExecutor()) {

            for (int i = 0; i < 100; i++) {

                int taskId = i;
                executorService.submit(() -> {

                    System.out.println("Task " + taskId + " executed by " + Thread.currentThread().getName());
                    // Simulating task execution time
                    try {
                        Thread.sleep(600);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();;
                    }
                });
            }
        }
    }
}
