package ru.job4j.pool;

import ru.job4j.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

public class ThreadPool {
    private final List<Thread> threads = new LinkedList<>();
    private final SimpleBlockingQueue<Runnable> tasks;

    public ThreadPool(int noOfTasks) {
        tasks = new SimpleBlockingQueue<>(noOfTasks);
        int noOfThreads = Runtime.getRuntime().availableProcessors();
        for (int i = 0; i < noOfThreads; i++) {
            Thread thread = new Thread(() -> {
                while (!Thread.currentThread().isInterrupted()) {
                    try {
                        tasks.poll().run();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            });
            threads.add(thread);
        }
        for (var runnable : threads) {
            new Thread(runnable).start();
        }
    }

    public synchronized void work(Runnable job) throws InterruptedException {
        tasks.offer(job);
    }

    public synchronized void shutdown() {
        for (var runnable : threads) {
            runnable.interrupt();
        }
    }
}




