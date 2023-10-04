package ru.job4j.pool;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallelMergeSort extends RecursiveTask<int[]> {

    private final int[] array;
    private final int from;
    private final int to;

    public ParallelMergeSort(int[] array, int from, int to) {
        this.array = array;
        this.from = from;
        this.to = to;
    }

    @Override
    protected int[] compute() {
        if (from == to) {
            return new int[] {array[from]};
        }
        int mid = (from + to) / 2;

        ParallelMergeSort leftSort = new ParallelMergeSort(array, from, mid);
        ParallelMergeSort rightSort = new ParallelMergeSort(array, mid + 1, to);

        leftSort.fork();
        rightSort.fork();

        int[] left = leftSort.join();
        int[] right = rightSort.join();

        return MergeSort.merge(left, right);
    }

    public static int[] sort(int[] array) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        return forkJoinPool.invoke(new ParallelMergeSort(array, 0, array.length - 1));
    }

    public static void main(String[] args) {
        int[] array = new int[] {19, 7, 1231, 11, 54, 44, 123, 325, 634, 12312, 43, 1, 12, 43634, 16, 57, 12, 99};
        int[] rsl = ParallelMergeSort.sort(array);
        Arrays.stream(rsl).forEach(System.out::println);
    }

}
