package ru.job4j.pool;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ParallelFinderTest {
    @Test
    public void whenFindInteger() {
        Integer[] array = new Integer[]{1, 3, 5, 74, 674, 12, 55, 12312, 43534, 123, 112, 645, 1231};
        int goal = 1231;
        ParallelFinder<Integer> parallelFinder = new ParallelFinder<>(array, 0, array.length - 1, goal);
        Integer exp = 12;
        Integer rsl = parallelFinder.find(array, goal);
        assertThat(rsl).isEqualTo(exp);
    }

    @Test
    public void whenFindString() {
        String[] array = new String[]{
                "sweat", "dreams", "are", "made", "of", "this",
                "who", "am", "i", "to", "disagree",
                "travel", "the world", "and", "the", "seven", "seas",
                "everybody's", "looking for", " something"};
        String goal = "the world";
        ParallelFinder<String> parallelFinder = new ParallelFinder<>(array, 0, array.length - 1, goal);
        Integer exp = 12;
        assertThat(parallelFinder.find(array, goal)).isEqualTo(exp);
    }

    @Test
    public void whenSmallArray() {
        Long[] array = new Long[]{123456789000L, 123456798111111L, 12313213548796L};
        long goal = 123456789000L;
        ParallelFinder<Long> parallelFinder = new ParallelFinder<>(array, 0, array.length - 1, goal);
        Integer exp = 0;
        assertThat(parallelFinder.find(array, goal)).isEqualTo(exp);
    }

    @Test
    public void whenItemNotFound() {
        Integer[] array = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14};
        Integer goal = 15;
        ParallelFinder<Integer> parallelFinder = new ParallelFinder<>(array, 0, array.length - 1, goal);
        Integer exp = -1;
        assertThat(parallelFinder.find(array, goal)).isEqualTo(exp);
    }
}
