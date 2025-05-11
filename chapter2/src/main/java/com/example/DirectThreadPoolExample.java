package com.example;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class DirectThreadPoolExample {

    public static void main(String[] args) {

        int corePoolSize = 2;

        // Make this 4, 10, 12, 14, and finally observe the output
        // When the pool size is small the executor will throw an RejectedExecutionException
        // because the executor is saturated (i.e there no space in the queue)

        // RejectedExecutionException:
        // Exception thrown by an Executor when a task cannot be accepted for execution.

        // The program will also hang because it fails to catch the exception to terminate
        // the ThreadPoolExecutor gracefully.
        int maximumPoolSize = 4;

        long keepAliveTime = 5000;
        TimeUnit timeUnit = TimeUnit.MILLISECONDS;
        ArrayBlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(10);

        int taskCount = 15;
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                corePoolSize,
                maximumPoolSize,
                keepAliveTime,
                timeUnit,
                workQueue);

        IntStream.range(0, taskCount)
                .forEach(i -> executor.execute(() -> System.out.printf(
                        "Task %d executed. " +
                                "Pool size = %d. " +
                                "Queue size = %d.%n",
                        i,
                        executor.getPoolSize(),
                        executor.getQueue().size())));

        executor.shutdown();
        executor.close();
    }
}
