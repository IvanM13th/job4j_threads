package ru.job4j.pool;

import ru.job4j.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

public class ThreadPool {
    private final List<Thread> threads = new LinkedList<>();
    private final SimpleBlockingQueue<Runnable> tasks;

    public ThreadPool(int NoOfTasks) {
        tasks = new SimpleBlockingQueue<>(NoOfTasks);
        int noOfThreads = Runtime.getRuntime().availableProcessors();
        while (!Thread.currentThread().isInterrupted() && threads.size() < noOfThreads) {
            threads.add(new Thread(() -> {
                try {
                    tasks.poll().run();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }));
        }
        threads.forEach(Thread::start);
    }

    public synchronized void work(Runnable job) throws InterruptedException {
        tasks.offer(job);
    }

    public synchronized void shutdown() {
        threads.forEach(Thread::interrupt);
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadPool threadPool = new ThreadPool(5);
        threadPool.work(() -> System.out.println("Test text to printed in console"));
        threadPool.work(() -> System.out.println("Test text to printed in console"));
        threadPool.work(() -> System.out.println("Test text to printed in console"));
        threadPool.work(() -> System.out.println("Test text to printed in console"));
        threadPool.work(() -> System.out.println("Test text to printed in console"));
        threadPool.shutdown();
    }
}

