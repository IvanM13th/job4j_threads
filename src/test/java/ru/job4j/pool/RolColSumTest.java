package ru.job4j.pool;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;


import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.assertThat;


public class RolColSumTest {

    static int N = 5000;
    static int[][] MATRIX = new int[N][N];

    @BeforeAll
    public static void fillTheArray() {
        for (int[] ints : MATRIX) {
            Arrays.fill(ints, 1);
        }
    }

    @Test
    public void whenNoAsync() {
        Sums[] sums = RolColSum.sum(MATRIX);
        assertThat(sums[1].getRowSum()).isEqualTo(5000);
        assertThat(sums[1].getColSum()).isEqualTo(5000);
    }

    @Test
    public void whenAsync() throws ExecutionException, InterruptedException {
        Sums[] sums = RolColSum.asyncSum(MATRIX);
        assertThat(sums[1].getRowSum()).isEqualTo(5000);
        assertThat(sums[1].getColSum()).isEqualTo(5000);
    }


    @Disabled
    @Test
    public void whenCompareTime() throws ExecutionException, InterruptedException {
        LocalDateTime startNoAsync = LocalDateTime.now();
        RolColSum.sum(MATRIX);
        LocalDateTime endTimeNoAsync = LocalDateTime.now();
        long noAsync = Duration.between(startNoAsync, endTimeNoAsync).toMillis();

        LocalDateTime startAsync = LocalDateTime.now();
        RolColSum.asyncSum(MATRIX);
        LocalDateTime endTimeAsync = LocalDateTime.now();
        long async = Duration.between(startAsync, endTimeAsync).toMillis();

        assertThat(async).isLessThan(noAsync);
    }
}
