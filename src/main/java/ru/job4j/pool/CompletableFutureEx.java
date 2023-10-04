package ru.job4j.pool;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class CompletableFutureEx {

    /**
     * Есть простая задача, которую выполняет кто-то
     * @throws InterruptedException
     */
    private static void iWork() throws InterruptedException {
        int count = 0;
        while (count < 10) {
            System.out.println("I work");
            TimeUnit.SECONDS.sleep(1);
            count++;
        }
    }

    /**
     * Этот кто-то не может сейчас выполнить другую задачу,
     * поэтому ее будет выполнять кто-то другой.
     * Для этого создается объект CompletableFuture
     * и вызывается метод supplyAsync либо runAsync.
     * @return
     */
    public static CompletableFuture<Void> goToTrash() {
        return CompletableFuture.runAsync(
                () -> {
                    System.out.println("Son: Im going to send some garbage to trash");
                    try {
                        TimeUnit.SECONDS.sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Son: Im back home!");
                }
        );
    }

    /**
     * мы работаем, а тем временем наш сын выбрасывает мусор
     * @throws Exception
     */
    public static void  runAsyncExample() throws Exception {
        CompletableFuture<Void> gtt = goToTrash();
        iWork();
    }

    public static CompletableFuture<String> buyProduct(String product) {
        return CompletableFuture.supplyAsync(
                () -> {
                    System.out.println("Son: Im going to the shop");
                    try {
                        TimeUnit.SECONDS.sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Son: I bought some " + product);
                    return product;
                }
        );
    }

    /**
     * мы рабюотаем, а сын в это время идет за молоком
     * @throws Exception
     */
    public static void supplyAsyncExample() throws Exception {
        CompletableFuture<String> bm = buyProduct("Milk");
        iWork();
        System.out.println("Received: " + bm.get());
    }

    public static void main(String[] args) throws Exception {
        thenCombineExample();
    }

    /**
     * методы колбэки.
     * Callback - метод - метод, который будет вызван после выполнения асинхронной задачи.
     */

    /**
     * Метод thenRun() ничего не возвращает, а позволяет выполнить задачу типа Runnable
     * после выполнения асинхронной задачи.
     * @throws Exception
     */
    public static void thenRunExample() throws Exception {
        /**
         * сначала асинхронная задача
         */
        CompletableFuture<Void> gtt = goToTrash();
        /**
         * потом вызываем thenRun() у этой задачи.
         */
        gtt.thenRun(
                () -> {
                    int count = 0;
                    while (count < 3) {
                        System.out.println("Son: Im washing my hands");
                        try {
                            TimeUnit.MICROSECONDS.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        count++;
                    }
                    System.out.println("Son: Mu hands are clean now!");
                }
        );
        iWork();
    }

    /**
     * thenAccept();
     * метод позволяет воспользоваться результатом выполнения асинхорнной задачи
     * и выполнить какое-то действие.
     * @throws Exception
     */
    public static void thenAcceptExample() throws Exception {
        CompletableFuture<String> bm = buyProduct("Молоко");
        bm.thenAccept((product) -> System.out.println("Сын: Я убрал " + product + " в холодильник "));
        iWork();
        System.out.println("Куплено: " + bm.get());
    }

    /**
     * применяет Function к результату выполнения асинхронной задачи
     * @throws Exception
     */
    public static void thenApplyExample() throws Exception {
        CompletableFuture<String> bm = buyProduct("Молоко")
                .thenApply((product) -> "Сын: я налил тебе в кружку " + product + ". Держи.");
        iWork();
        System.out.println(bm.get());
    }

    public static void thenComposeExample() throws Exception {
        CompletableFuture<String> result = goToTrash().thenCompose(a -> buyProduct("Milk"));
        result.get();
    }

    public static void thenCombineExample() throws Exception {
        CompletableFuture<String> result = buyProduct("Молоко")
                .thenCombine(buyProduct("Хлеб"), (r1, r2) -> "Куплены " + r1 + " и " + r2);
        iWork();
        System.out.println(result.get());
    }
}
