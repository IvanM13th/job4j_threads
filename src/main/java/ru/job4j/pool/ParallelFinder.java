package ru.job4j.pool;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallelFinder extends RecursiveTask<Integer> {

    private final int[] array;
    private final int from;
    private final int to;
    private final int goal;

    public ParallelFinder(int[] array, int from, int to, int goal) {
        this.array = array;
        this.from = from;
        this.to = to;
        this.goal = goal;
    }

    @Override
    protected Integer compute() {
        if (array.length <= 2) {
            for (var n : array) {
                if (n == goal) {
                    return n;
                }
            }
            return -1;
        }

        int mid = (from + to) / 2;

        ParallelFinder leftSearch = new ParallelFinder(array, from, mid, goal);
        ParallelFinder rightSearch = new ParallelFinder(array, mid + 1, to, goal);

        leftSearch.fork();
        rightSearch.fork();

        return leftSearch.join() + rightSearch.join();
    }


    public int find(int[] array) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        return forkJoinPool.invoke(new ParallelFinder(array, 0, array.length - 1, 1));
    }

    public static void main(String[] args) {
        int[] array = new int[]{19, 7, 1231, 11, 54, 44, 12312, 12, 324, 11, 2323, 5436, 12, 744, 1234};
        ParallelFinder parallelFinder = new ParallelFinder(array, 0, array.length - 1, 1);
        System.out.println(parallelFinder.find(array));

    }

}

