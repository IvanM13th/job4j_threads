package ru.job4j.pooh;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class QueueService implements Service {
    private final static String POST = "POST";

    private final ConcurrentHashMap<String, ConcurrentLinkedQueue<String>> queue = new ConcurrentHashMap<>();

    @Override
    public Resp process(Req req) {
        String topic = req.getSourceName();
        String param = req.getParam();
        String rsl;
        if (POST.equals(req.httpRequestType())) {
            queue.putIfAbsent(topic, new ConcurrentLinkedQueue<>());
            queue.get(topic).add(param);
            rsl = param;
        } else {
            rsl = queue.getOrDefault(topic, new ConcurrentLinkedQueue<>()).poll();
        }
        return new Resp(rsl, "200");
    }
}