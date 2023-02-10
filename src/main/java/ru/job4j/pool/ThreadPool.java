package ru.job4j.pool;

import ru.job4j.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

public class ThreadPool {
    private final List<MyThread> threads = new LinkedList<>();
    private final SimpleBlockingQueue<Runnable> tasks;

    public ThreadPool(int NoOfTasks) {
        tasks = new SimpleBlockingQueue<>(NoOfTasks);
        int noOfThreads = Runtime.getRuntime().availableProcessors();
        for (int i = 0; i < noOfThreads; i++) {
            MyThread myThread = new MyThread(tasks);
            threads.add(myThread);
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
            runnable.stop();
        }
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

    static class MyThread implements Runnable {

        private final SimpleBlockingQueue<Runnable> tasks;
        private Thread thread = null;

        public MyThread(SimpleBlockingQueue<Runnable> tasks) {
            this.tasks = tasks;
        }

        @Override
        public void run() {
            this.thread = Thread.currentThread();
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    tasks.poll().run();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }

        public synchronized void stop() {
            this.thread.interrupt();
        }
    }
}



