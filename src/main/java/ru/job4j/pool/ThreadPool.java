package ru.job4j.pool;

import ru.job4j.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

public class ThreadPool {
    private final List<Thread> threads = new LinkedList<>();
    private final SimpleBlockingQueue<Runnable> tasks;
    private boolean isStopped = false;

    public ThreadPool(int NoOfTasks) {
        tasks = new SimpleBlockingQueue<>(NoOfTasks);
        int noOfThreads = Runtime.getRuntime().availableProcessors();
        for (int i = 0; i < noOfThreads; i++) {
            threads.add(new Thread(() -> {
                try {
                    tasks.poll();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }));
        }
        threads.forEach(Thread::start);
    }

    public synchronized void work(Runnable job) throws InterruptedException {
        if (!isStopped) {
            tasks.offer(job);
        }
    }

    public synchronized void shutdown() {
        isStopped = true;
        threads.forEach(Thread::interrupt);
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadPool threadPool = new ThreadPool(5);
        threadPool.work(() -> System.out.println("Test string to be printed in console"));
        Thread.sleep(500);
        threadPool.shutdown();
    }
}

