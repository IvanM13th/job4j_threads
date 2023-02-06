package ru.job4j.thread.threadlocal;

public class FirstThread implements Runnable {
    @Override
    public void run() {
        ThreadLocalDemo.tl.set("Thread #1");
        System.out.println(ThreadLocalDemo.tl.get());
    }
}
