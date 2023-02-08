package ru.job4j;

public class CountBarrier {
    private final Object monitor = this;
    private final int total;
    private int count = 0;

    public CountBarrier(int total) {
        this.total = total;
    }

    public void count() {
        synchronized (monitor) {
            while (count < 10) {
                System.out.println("Count now is " + count++ + " and total now is " + total);
                monitor.notify();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void await() {
        synchronized (monitor) {
            System.out.println("I am in await method");
            while (count < total) {
                try {
                    monitor.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            System.out.println("Await method is running now");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        CountBarrier countBarrier = new CountBarrier(4);
        Thread countThread = new Thread(
                () ->  {
                    System.out.println(Thread.currentThread().getName() + " started");
                    countBarrier.count();
                }
        );
        Thread awaitThread = new Thread(
                () ->  {
                    System.out.println(Thread.currentThread().getName() + " started");
                    countBarrier.await();
                }
        );

        countThread.start();
        awaitThread.start();
    }
}
