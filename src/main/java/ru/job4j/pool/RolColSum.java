package ru.job4j.pool;

import java.util.concurrent.CompletableFuture;

public class RolColSum {
    public static class Sums {

        private int rowSum;
        private int colSum;

        public int getRowSum() {
            return rowSum;
        }

        public void setRowSum(int rowSum) {
            this.rowSum = rowSum;
        }

        public int getColSum() {
            return colSum;
        }

        public void setColSum(int colSum) {
            this.colSum = colSum;
        }
    }

    private static void countRows(Sums[] sums, int[][] matrix) {
        for (int row = 0; row < matrix.length; row++) {
            int rowSum = 0;
            for (int j = 0; j < matrix[row].length; j++) {
                rowSum += matrix[row][j];
            }
            sums[row].setRowSum(rowSum);
        }
    }

    private static void countColumns(Sums[] sums, int[][] matrix) {
        for (int column = 0; column < matrix.length; column++) {
            int columnSum = 0;
            for (int[] ints : matrix) {
                columnSum += ints[column];
            }
            sums[column].setColSum(columnSum);
        }
    }

    private static Sums[] generateArray(int[][] matrix) {
        Sums[] sum = new Sums[matrix.length * 2];
        for (int i = 0; i < matrix.length; i++) {
            sum[i] = new Sums();
        }
        return sum;
    }


    public static Sums[] sum(int[][] matrix) {
        Sums[] sum = generateArray(matrix);
        countRows(sum, matrix);
        countColumns(sum, matrix);
        return sum;
    }

    public static Sums[] asyncSum(int[][] matrix) {
        Sums[] sum = generateArray(matrix);
        CompletableFuture<Void> obj = CompletableFuture.allOf(
                getRowsAsync(sum, matrix),
                getColumnsAsync(sum, matrix)
        );
        return sum;
    }

    public static CompletableFuture<Void> getRowsAsync(Sums[] sums, int[][] data) {
        return CompletableFuture.supplyAsync(() -> {
            countRows(sums, data);
            return null;
        });
    }

    public static CompletableFuture<Void> getColumnsAsync(Sums[] sums, int[][] data) {
        return CompletableFuture.supplyAsync(() -> {
            countColumns(sums, data);
            return null;
        });
    }
}
