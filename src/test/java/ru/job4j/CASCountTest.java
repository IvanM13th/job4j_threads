package ru.job4j;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Disabled
public class CASCountTest {
    @Test
    public void whenTest() throws InterruptedException {
        CASCount casCount = new CASCount();
        Thread thr1 = new Thread(
                casCount::increment
        );
        Thread thr2 = new Thread(
                casCount::increment
        );
        thr1.start();
        thr2.start();
        thr2.join();
        thr1.join();
        Thread.sleep(500);
        int rsl = casCount.get();
        int exp = 2;
        assertThat(rsl).isEqualTo(exp);
    }
}
