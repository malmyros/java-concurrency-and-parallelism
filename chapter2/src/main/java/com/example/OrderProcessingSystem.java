package com.example;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class OrderProcessingSystem {

    private static final ExecutorService executorService = Executors.newFixedThreadPool(10);
    private static final ConcurrentLinkedDeque<Order> orderQueue = new ConcurrentLinkedDeque<>();
    private static final CopyOnWriteArrayList<Order> processedOrders = new CopyOnWriteArrayList<>();
    private static final ConcurrentHashMap<Integer, String> orderStatus = new ConcurrentHashMap<>();
    private static final Lock paymentLock = new ReentrantLock();
    private static final Semaphore validationSemaphore = new Semaphore(5);
    private static final AtomicInteger processedCount = new AtomicInteger(0);

    static class Order {

        private final int id;

        public Order(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }
    }

    public void startProcessing() {
        while (!orderQueue.isEmpty()) {
            Order order = orderQueue.poll();
            executorService.submit(() -> processOrder(order));
        }
    }

    private void processOrder(Order order) {
        try {

            validateOrder(order);
            paymentLock.lock();

            try {
                processPayment(order);
            } finally {
                paymentLock.unlock();
            }

            shipOrder(order);
            processedOrders.add(order);
            processedCount.incrementAndGet();
            orderStatus.put(order.getId(), "Completed");

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void validateOrder(Order order) throws InterruptedException {
        validationSemaphore.acquire();
        try {
            Thread.sleep(1000);
            System.out.println("Order " + order.getId() + " validated");
        } finally {
            validationSemaphore.release();
        }
    }

    private void processPayment(Order order) {
        System.out.println("Payment processed for order " + order.getId());
    }

    private void shipOrder(Order order) {
        System.out.println("Shipped order " + order.getId());
    }

    public void placeOrder(Order order) {

        orderQueue.add(order);
        orderStatus.put(order.getId(), "Received");
        System.out.println("Order " + order.getId() + " placed");
    }

    public static void main(String[] args) {

        OrderProcessingSystem system = new OrderProcessingSystem();
        for (int i = 0; i < 20; i++) {
            system.placeOrder(new Order(i));
        }

        system.startProcessing();
        System.out.println("All orders processed");

        executorService.shutdown();
        executorService.close();
    }
}
