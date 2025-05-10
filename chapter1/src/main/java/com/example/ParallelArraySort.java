package com.example;

import java.util.Arrays;
import java.util.Random;

public class ParallelArraySort {

    public static void main(String[] args) {

        // Interestingly enough if all the elements in the array
        // are the same value sequential sorting is faster

        int[] array = generateRandomArray(10_000_000);

        long start = System.currentTimeMillis();
        Arrays.sort(array);
        long end = System.currentTimeMillis();
        System.out.println("Sequential sorting took " + (end - start) + " ms");

        start = System.currentTimeMillis();
        Arrays.parallelSort(array);
        end = System.currentTimeMillis();
        System.out.println("Parallel sorting took " + (end - start) + " ms");
    }

    private static int[] generateRandomArray(int size) {
        int[] array = new int[size];
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt();
        }
        return array;
    }
}
