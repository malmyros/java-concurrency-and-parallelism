package com.example;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class ParallelFibonacci extends RecursiveAction {

    private static final int THRESHOLD = 10;

    private final long n;

    public ParallelFibonacci(long n) {
        this.n = n;
    }

    @Override
    protected void compute() {

        if (n <= THRESHOLD) {

            // Compute Fibonacci number sequentially
            int fib = fibonacci(n);
            System.out.println("Fibonacci (" + n + ") = " + fib);
        } else {

            ParallelFibonacci leftTask = new ParallelFibonacci(n - 1);
            ParallelFibonacci rightTask = new ParallelFibonacci(n - 2);

            leftTask.fork();
            rightTask.fork();

            leftTask.join();
            rightTask.join();
        }
    }

    private int fibonacci(long n) {
        if (n <= 1) {
            return (int) n;
        } else {
            return fibonacci(n - 1) + fibonacci(n - 2);
        }
    }

    public static void main(String[] args) {

        long n = 40;
        try (ForkJoinPool pool = new ForkJoinPool()) {
            ParallelFibonacci task = new ParallelFibonacci(n);
            pool.invoke(task);
        }
    }
}
