package hlf.kafkaerrorhandling.eventhandlers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.kafka.clients.consumer.ConsumerRecord;

public class DefaultWidgetEventHandler implements WidgetEventHandler {
    Gson gson = new GsonBuilder().create();

    @Override
    public void handle(ConsumerRecord<Long, String> record) {
        var message = String.format("%d: %s", record.key(), record.value());
        System.out.println(message);
    }
}
