package hlf.kafkaerrorhandling.eventhandlers;

import org.apache.kafka.clients.consumer.ConsumerRecord;

public interface WidgetEventHandler {
    public void handle(ConsumerRecord<Long, String> record);
}
