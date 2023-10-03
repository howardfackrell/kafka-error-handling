package hlf.kafkaerrorhandling;


import hlf.kafkaerrorhandling.eventhandlers.WidgetEventHandler;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class MessageConsumer {

    private final WidgetEventHandler widgetEventHandler;

    /**
    @KafkaListener(
            topics = {"widgetEvent"},
            groupId = "KafkaErrorHandlingMessageConsumer-e37fd1d2",
            batch = "false",
            errorHandler = "widgetEventErrorHandler",
            containerFactory = "widgetListenerContainerFactory"
    )
    public void handleWidget(ConsumerRecord<Long, String> record) {
        widgetEventHandler.handle(record);
    }
     **/

    @KafkaListener(
            topics = {"widgetEvent"},
            groupId = "KafkaErrorHandlingMessageConsumer-e37fd1d2",
            batch = "true",
//            errorHandler = "widgetEventErrorHandler",
            containerFactory = "widgetListenerContainerFactory"
    )
    public void handleWidget(List<ConsumerRecord<Long, String>> records) {
        records.forEach(r -> widgetEventHandler.handle(r));
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println("MessageConsumer has been initialized");
    }
}
