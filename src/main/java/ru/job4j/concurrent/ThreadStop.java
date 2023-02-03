package ru.job4j.concurrent;

public class ThreadStop {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(
                () -> {
                    int count = 0;
                    /**
                     * проверка флага прерывания идет во второй нити/
                     * если после sout выставим interrupt(),
                     * в консоль будет выведено 0;
                     */
                    while (!Thread.currentThread().isInterrupted()) {
                        System.out.println(count++);
                    }
                }
        );
        thread.start();
        Thread.sleep(1000);
        /**
         * планировщик выделяет разное время для каждой нити,
         * по этой причине флаг прерывания выставялется в произвольное время
         */
        /**
         * главная нить выставляет прерывание
         */
        thread.interrupt();
    }
}
