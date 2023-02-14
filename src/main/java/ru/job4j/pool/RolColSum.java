package ru.job4j.pool;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RolColSum {

    private static Sums countRowsAndColumns(int[][] matrix, int rowOrColumn) {
        Sums sums = new Sums();
        int rowSum = 0;
        int columnSum = 0;
        for (int column = 0; column < matrix.length; column++) {
            rowSum += matrix[rowOrColumn][column];
        }
        sums.setRowSum(rowSum);

        for (int[] ints : matrix) {
            columnSum += ints[rowOrColumn];
        }
        sums.setColSum(columnSum);
        return sums;
    }

    public static Sums[] sum(int[][] matrix) {
        Sums[] sums = new Sums[matrix.length];
        int n = matrix.length;
        int bound = sums.length - 1;
        Map<Integer, Sums> futures = new HashMap<>();
        for (int i = 0; i < n / 2; i++) {
            futures.put(i, countRowsAndColumns(matrix, i));
            if (i < n / 2) {
                futures.put(bound - i, countRowsAndColumns(matrix, bound - i));
            }
        }
        for (var key : futures.keySet()) {
            sums[key] = futures.get(key);
        }
        return sums;
    }

    public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        Sums[] sums = new Sums[matrix.length];
        int n = matrix.length;
        int bound = sums.length - 1;
        Map<Integer, CompletableFuture<Sums>> futures = new HashMap<>();
        for (int i = 0; i < n / 2; i++) {
            futures.put(i, countAsync(matrix, i));
            if (i < n / 2) {
                futures.put(bound - i, countAsync(matrix, bound - i));
            }
        }
        for (var key : futures.keySet()) {
            sums[key] = futures.get(key).get();
        }
        return sums;
    }

    public static CompletableFuture<Sums> countAsync(int[][] data, int rowOrColumn) {
        return CompletableFuture.supplyAsync(() -> countRowsAndColumns(data, rowOrColumn));
    }
}