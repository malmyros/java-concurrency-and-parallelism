package com.example;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class KitchenSimulator {

    public static void main(String[] args) {

        try (ExecutorService kitchen = Executors.newFixedThreadPool(3)) {

            String dishToPrepare = "Spaghetti Bolognese";
            String cuisineToSearch = "Italian";
            String menuToUpdate = "Today's Specials";

            kitchen.submit(() -> prepareDish(dishToPrepare));
            kitchen.submit(() -> searchRecipes(cuisineToSearch));
            kitchen.submit(() -> prepareMenu(menuToUpdate));
        }
    }

    private static void prepareDish(String dish) {
        System.out.println("Preparing " + dish);
        try {
            Thread.sleep(600);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private static void searchRecipes(String cuisine) {
        System.out.println("Searching for " + cuisine + " recipes");
        try {
            Thread.sleep(600);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private static void prepareMenu(String menu) {
        System.out.println("Preparing " + menu + " menu");
        try {
            Thread.sleep(600);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
