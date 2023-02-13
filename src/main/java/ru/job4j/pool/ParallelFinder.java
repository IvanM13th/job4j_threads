package ru.job4j.pool;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallelFinder<T> extends RecursiveTask<Integer> {

    private final T[] array;
    private final int from;
    private final int to;
    private final T goal;

    public ParallelFinder(T[] array, int from, int to, T goal) {
        this.array = array;
        this.from = from;
        this.to = to;
        this.goal = goal;
    }

    @Override
    protected Integer compute() {
        if (to - from <= 10) {
            return findIndex();
        }
        int mid = (from + to) / 2;
        ParallelFinder<T> leftSearch = new ParallelFinder<>(array, from, mid, goal);
        ParallelFinder<T> rightSearch = new ParallelFinder<>(array, mid + 1, to, goal);
        leftSearch.fork();
        rightSearch.fork();
        return Math.max(leftSearch.join(), rightSearch.join());
    }

    public static <T> Integer find(T[] array, T goal) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        return forkJoinPool.invoke(new ParallelFinder<>(array, 0, array.length - 1, goal));
    }

    private int findIndex() {
        int rsl = -1;
        for (int i = from; i <= to; i++) {
            if (goal.equals(array[i])) {
                rsl = i;
                break;
            }
        }
        return rsl;
    }
}