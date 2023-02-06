package ru.job4j.thread.threadlocal;

public class SecondThread implements  Runnable {
    @Override
    public void run() {
        ThreadLocalDemo.tl.set("Thread #2");
        System.out.println(ThreadLocalDemo.tl.get());
    }
}
