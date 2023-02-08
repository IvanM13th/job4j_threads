package ru.job4j;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {
    private final int limit;

    public SimpleBlockingQueue(int limit) {
        this.limit = limit;
    }

    @GuardedBy("this")
    private Queue<T> queue = new LinkedList<>();

    public synchronized void offer(T value) throws InterruptedException {
        while (queue.size() == limit) {
            this.wait();
        }
        notifyAll();
        queue.add(value);
    }

    public synchronized T poll() throws InterruptedException {
        while (queue.isEmpty()) {
            this.wait();
        }
        notifyAll();
        return queue.poll();
    }
}