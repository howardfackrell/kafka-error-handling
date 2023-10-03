package hlf.kafkaerrorhandling;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessageProducer {
    private final KafkaTemplate<Long, String> kafkaTemplate;
    private static final String WIDGET_EVENT_TOPIC = "widgetEvent";
    public static final int MESSAGE_COUNT = 100;

    Gson gson = new GsonBuilder().create();

    @PostConstruct
    void publishMessages() {
        System.out.println("Publishing messages...");
        for (int i = 0 ; i < MESSAGE_COUNT; i++) {
            System.out.println("Publishing message " + i);
            var event = WidgetEvent.randomWidgetEvent(i);
            var jsonEvent = gson.toJson(event);
            kafkaTemplate.send(WIDGET_EVENT_TOPIC, Long.parseLong(event.getId()), jsonEvent.toString());
        }
    }
}
