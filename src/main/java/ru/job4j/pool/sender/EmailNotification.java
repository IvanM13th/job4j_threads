package ru.job4j.pool.sender;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EmailNotification {
    private final ExecutorService pool;
    private final List<User> users;

    public EmailNotification(ExecutorService pool, List<User> users) {
        this.pool = pool;
        this.users = users;
    }

    public void emailTo(User user) {
        String name = user.getUsername();
        String mail = user.getEmail();
        pool.submit(() -> {
            String subject = String.format("Notification %s to email %s",
                    name,
                    mail);
            String body = String.format("Add a new event to %s",
                    name);
            send(subject, body, mail);
            System.out.println("Message sent to " + name + " via email " + mail);
        });
    }

    public void send(String subject, String body, String email) {

    }

    public void close() {
        pool.shutdown();
    }

    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        List<User> users = new ArrayList<>();
        users.add(new User("Ivan", "123@321.ru"));
        users.add(new User("Sergei", "456@321.ru"));
        users.add(new User("Anna", "789@321.ru"));

        EmailNotification emailNotification = new EmailNotification(pool, users);
        for (var user : emailNotification.users) {
            emailNotification.emailTo(user);
        }
    }
}
