package ru.job4j.pool;

import java.util.Arrays;
import java.util.concurrent.RecursiveTask;

public class ValueSumCounter extends RecursiveTask<Integer> {

    private int[] array;

    public ValueSumCounter(int[] array) {
        this.array = array;
    }

    @Override
    protected Integer compute() {
        if (array.length <= 2) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return Arrays.stream(array).sum();
        }
        ValueSumCounter firstHalfArrayValueSumCounter = new ValueSumCounter(Arrays.copyOfRange(array, 0, array.length / 2));
        ValueSumCounter secondHalfArrayValueSumCounter = new ValueSumCounter(Arrays.copyOfRange(array, array.length / 2, array.length));
        firstHalfArrayValueSumCounter.fork();
        secondHalfArrayValueSumCounter.fork();
        return firstHalfArrayValueSumCounter.join() + secondHalfArrayValueSumCounter.join();
    }
}

