package ru.job4j;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicReference;


@ThreadSafe
public class CASCount {
    private int initialNo;

    private final AtomicReference<Integer> count = new AtomicReference<>();

    public CASCount(int initialNo) {
        count.set(initialNo);
    }

    public synchronized void increment() {
        Integer current;
        int next;
        do {
            current = count.get();
            next = current + 1;
            count.compareAndSet(current, next);
            current = next;
        } while (!count.compareAndSet(current, next));
    }

    public synchronized int get() {
        return count.get();
    }
}