package ru.job4j.pool;

import java.util.Date;
import java.util.concurrent.ForkJoinPool;

public class Main {

    public static void main(String[] args) {
/*        int[] array = getInitArray(10000);
        ValueSumCounter counter = new ValueSumCounter(array);
        System.out.println(new Date());
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        System.out.println(forkJoinPool.invoke(counter));
        System.out.println(new Date());*/

        String test = "qweRty";
        for (int i = 0; i < test.length(); i++) {
            char ch = test.charAt(i);
            if (Character.isUpperCase(ch)) {
                System.out.println(ch);
            }
        }
    }

    public static int[] getInitArray(int capacity) {
        int[] array = new int[capacity];
        for (int i = 0; i < capacity; i++) {
            array[i] = 3;
        }
        return array;
    }
}
