package ru.job4j.thread;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.Duration;
import java.time.Instant;

public class Wget implements Runnable {
    private final String url;
    private final int speed;
    private final String saveDir;
    private static final int SLEEP_TIME = 1000;

    public Wget(String url, int speed, String saveDir) {
        this.url = url;
        this.speed = speed;
        this.saveDir = saveDir;
    }

    public static void main(String[] args) throws InterruptedException {
        validate(args);
        String url = args[0];
        String saveDir = getFileName(args[0]);
        int speed = Integer.parseInt(args[1]);
        Thread wget = new Thread(new Wget(url, speed, saveDir));
        wget.start();
        wget.join();
    }

    @Override
    public void run() {
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream out = new FileOutputStream(saveDir)) {
            byte[] dataBuffer = new byte[speed];
            int bytesRead;
            int downloadedData = 0;
            Instant start = Instant.now();
            while ((bytesRead = in.read(dataBuffer, 0, speed)) != -1) {
                out.write(dataBuffer, 0, bytesRead);
                downloadedData += bytesRead;
                if (downloadedData >= speed) {
                    long interval = Duration.between(start, Instant.now()).toMillis();
                    if (interval < SLEEP_TIME) {
                        Thread.sleep(1000 - interval);
                    }
                    downloadedData = 0;
                    start = Instant.now();
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void validate(String[] args) {
        if (args.length != 2) {
            throw new IllegalArgumentException();
        }
        try {
            new URL(args[0]).toURI();
        } catch (MalformedURLException | URISyntaxException e) {
            e.printStackTrace();
        }
        if (Integer.parseInt(args[1]) == 0) {
            throw new IllegalArgumentException();
        }
    }

    private static String getFileName(String file) {
        String[] n = file.split("/");
        return n[n.length - 1];
    }
}
