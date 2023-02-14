package ru.job4j.pool;

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

    private static Sums[] generateArray(int[][] matrix) {
        Sums[] sum = new Sums[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            sum[i] = new Sums();
        }
        return sum;
    }

    public static Sums[] sum(int[][] matrix) {
        Sums[] sum = generateArray(matrix);
        int n = matrix.length;
        int bound = sum.length - 1;
        for (int i = 0; i < n / 2; i++) {
            sum[i] = countRowsAndColumns(matrix, i);
            if (i < n / 2) {
                sum[bound - i] = countRowsAndColumns(matrix, bound - i);
            }
        }
        return sum;
    }

    public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        Sums[] sum = generateArray(matrix);
        int n = matrix.length;
        int bound = sum.length - 1;
        for (int i = 0; i < n / 2; i++) {
            sum[i] = countAsync(matrix, i).get();
            if (i < n / 2) {
                sum[bound - i] = countAsync(matrix, bound - i).get();
            }
        }
        return sum;
    }

    public static CompletableFuture<Sums> countAsync(int[][] data, int rowOrColumn) {
        return CompletableFuture.supplyAsync(() -> countRowsAndColumns(data, rowOrColumn));
    }

}
