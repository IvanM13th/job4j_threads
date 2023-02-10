package ru.job4j;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class CASCountTest {
    @Test
    public void whenTwoThreads() throws InterruptedException {
        CASCount casCount = new CASCount(0);
        Thread thr1 = new Thread(
                () -> {
                    for (int i = 0; i < 10; i++) {
                        casCount.increment();
                    }
                }
        );
        Thread thr2 = new Thread(
                () -> {
                    for (int i = 0; i < 10; i++) {
                        casCount.increment();
                    }
                }
        );
        thr1.start();
        thr2.start();
        thr2.join();
        thr1.join();
        int rsl = casCount.get();
        int exp = 20;
        assertThat(rsl).isEqualTo(exp);
    }
    @Test
    public void whenFourThreads() throws InterruptedException {
        CASCount casCount = new CASCount(0);
        Thread thr1 = new Thread(
                () -> {
                    for (int i = 0; i < 3; i++) {
                        casCount.increment();
                    }
                }
        );
        Thread thr2 = new Thread(
                () -> {
                    for (int i = 0; i < 8; i++) {
                        casCount.increment();
                    }
                }
        );
        Thread thr3 = new Thread(
                () -> {
                    for (int i = 0; i < 87; i++) {
                        casCount.increment();
                    }
                }
        );
        Thread thr4 = new Thread(
                () -> {
                    for (int i = 0; i < 29; i++) {
                        casCount.increment();
                    }
                }
        );
        thr1.start();
        thr2.start();
        thr3.start();
        thr4.start();
        thr1.join();
        thr2.join();
        thr3.join();
        thr4.join();
        int rsl = casCount.get();
        int exp = 127;
        assertThat(rsl).isEqualTo(exp);
    }
}
