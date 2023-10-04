package ru.job4j.pool;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class MatrixDiagonalSum {
    public static int[] asyncSum(int[][] matrix) throws Exception {
        //находим длину матрицы
        int n = matrix.length;
        //создаем массив для подсчет сумма и взоврата
        int[] sums = new int[2 * n];
        //создаем мапу для хранения чего-то
        Map<Integer, CompletableFuture<Integer>> futures = new HashMap<>();
        //добавляем в мапу числа главной диагоналои
        futures.put(0, getMainDiagonalTask(matrix, 0, n - 1, 0));
        for (int k = 1; k <= n; k++) {
            futures.put(k, getTask(matrix, 0, k - 1, k - 1));
            if (k < n) {
                futures.put(2 * n - k, getTask(matrix, n - k, n - 1, n -  1));
            }
        }
        for (var key : futures.keySet()) {
            sums[key] = futures.get(key).get();
        }
        return sums;
    }

    /**
     * метод для расчеты сумм диагоналей
     * supplyAsync - возвращает результат
     *
     * @param data     передаем матрицу
     * @param startRow начальный ряд
     * @param endRow   конечный ряд
     * @param startCol начальная колонка
     * @return
     */
    public static CompletableFuture<Integer> getTask(int[][] data, int startRow, int endRow, int startCol) {
        return CompletableFuture.supplyAsync(() -> {
            int sum = 0;
            int col = startCol;
            for (int i = startRow; i <= endRow; i++) {
                sum += data[i][col];
                col--;
            }
            return sum;
        });
    }

    public static CompletableFuture<Integer> getMainDiagonalTask(int[][] data, int startRow, int endRow, int startCol) {
        return CompletableFuture.supplyAsync(() -> {
            int sum = 0;
            int col = startCol;
            for (int i = startRow; i <= endRow; i++) {
                sum += data[i][col];
                col++;
            }
            return sum;
        });
    }

    public static void main(String[] args) throws Exception {
        int[][] matrix = new int[3][3];
        matrix[0][0] = 1;
        matrix[0][1] = 2;
        matrix[0][2] = 3;
        matrix[1][0] = 4;
        matrix[1][1] = 5;
        matrix[1][2] = 6;
        matrix[2][0] = 7;
        matrix[2][1] = 8;
        matrix[2][2] = 9;
        
        System.out.println(Arrays.toString(asyncSum(matrix)));

    }
}
