package ru.job4j.practice;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.Callable;


public class BlockingQueue<T> {
    private final Queue<T> tasks = new LinkedList<>();

    public synchronized void put(T task) {
        tasks.add(task);
        notifyAll();
    }

    public synchronized T get() throws InterruptedException {
        while (tasks.isEmpty()) {
            wait();
        }
        T rsl = tasks.poll();
        notifyAll();
        return rsl;
    }

    public static void main(String[] args) throws Exception {
        ThreadLocal<String> tl = new ThreadLocal<>();
        tl.set("123123123");

        Callable<String> rsl = () -> "this is calable";
        Thread thr3 = new Thread(() -> {
            for (int i = 1; i < 10; i++) {
                try {
                    System.out.println("This is callable number " + rsl.call());

                    System.out.println(tl.get());;
                    Thread.sleep(300);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

        });
        thr3.start();

        BlockingQueue<String> blockingQueue = new BlockingQueue<>();
        List<Thread> threads = new ArrayList<>();
        for (int i = 0 ; i < Runtime.getRuntime().availableProcessors() / 2; i++) {
            Thread thread = new Thread(() -> {
                while (!Thread.currentThread().isInterrupted()) {
                    try {
                        System.out.println(blockingQueue.get());
                        tl.set("bambucho");
                        System.out.println(tl.get() + " is called from " + Thread.currentThread().getName());
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        System.out.println("Getter is shutting down");
                        Thread.currentThread().interrupt();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            threads.add(thread);
        }
        for (var t : threads) {
            t.start();
        }
        Thread pusher = new Thread(() -> {
            for (int i = 1; i < 10000; i++) {
                blockingQueue.put("Task number " + i);
            }
        });
        pusher.start();

        Thread.sleep(10000);
        for (var t : threads) {
            t.interrupt();
        }

    }
}
