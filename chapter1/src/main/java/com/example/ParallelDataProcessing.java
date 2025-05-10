package com.example;

import java.util.ArrayList;
import java.util.List;

public class ParallelDataProcessing {

    public static void main(String[] args) {

        List<Integer> data = generateData(10_000_000);

        // Sequential processing
        long start = System.currentTimeMillis();

        int sum = data.stream()
                .mapToInt(Integer::intValue)
                .sum();

        long end = System.currentTimeMillis();
        System.out.println("Sequential sum " + sum + " time " + (end - start) + " ms");

        // Parallel processing
        // Sequential processing
        start = System.currentTimeMillis();

        sum = data.parallelStream()
                .mapToInt(Integer::intValue)
                .sum();

        end = System.currentTimeMillis();
        System.out.println("Parallel sum " + sum + " time " + (end - start) + " ms");
    }

    private static List<Integer> generateData(int size) {

        // If possible always initialise the array with the size
        // to ensure that the elements are stored in a continuous
        // memory location, which means the elements are placed next
        // to each other in memory

        // Remember:
        // L1 -> Level 1 Instruction Cache | Level 0 Data Cache (Per Core)
        // L2 -> Level 2 Cache (Per Core)
        // L3 -> LLC (Last Level Cache) Shared access across CPU cores
        List<Integer> data = new ArrayList<>(size);

        for (int i = 0; i < size; i++) {
            data.add(i);
        }
        return data;
    }
}
