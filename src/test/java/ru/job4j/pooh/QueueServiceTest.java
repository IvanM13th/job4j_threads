package ru.job4j.pooh;

import org.junit.Test;
import org.junit.jupiter.api.Disabled;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@Disabled
public class QueueServiceTest {

    @Test
    @Disabled
    public void whenPostThenGetQueue() {
        QueueService queueService = new QueueService();
        String paramForPostMethod = "temperature=18";
        queueService.process(
                new Req("POST", "queue", "weather", paramForPostMethod)
        );
        Resp result = queueService.process(
                new Req("GET", "queue", "weather", null)
        );
        assertThat(result.text(), is("temperature=18"));
    }
}