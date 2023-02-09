package ru.job4j;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicReference;


@ThreadSafe
public class CASCount {
    private final AtomicReference<Integer> count = new AtomicReference<>();

    public void increment() {
        Integer current = count.get();
        if (current == null) {
            count.set(0);
            current = 0;
        }
        int next;
        do {
            next = current + 1;
            count.compareAndSet(current, next);
            current = next;
            next = current + 1;
        } while (!count.compareAndSet(current, next));
    }

    public int get() {
        Integer rsl = count.get();
        if (rsl == null) {
            throw new UnsupportedOperationException("Count is not impl.");
        }
        return rsl;
    }
}