package com.example;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ConcurrentKitchen {

    public static void main(String[] args) {

        try (ExecutorService executorService = Executors.newFixedThreadPool(2)) {

            Future<?> choppingTask = executorService.submit(() -> {
                System.out.println("Chopping food...");
                try {
                    Thread.sleep(600);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });


            Future<?> grillingTask = executorService.submit(() -> {
                System.out.println("Grilling food...");
                try {
                    Thread.sleep(600);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });

            try {
                choppingTask.get();
                grillingTask.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
    }
}
