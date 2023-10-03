package hlf.kafkaerrorhandling.eventhandlers;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.util.Map;
import java.util.TreeMap;

public class IdCapturingWidgetEventHandler implements WidgetEventHandler {

    private Map<Long, Integer> ids = new TreeMap<>();

    private WidgetEventHandler delegate;

    public IdCapturingWidgetEventHandler(WidgetEventHandler delegate) {
        this.delegate = delegate;
    }

    @Override
    public void handle(ConsumerRecord<Long, String> record) {
        delegate.handle(record);
        ids.compute(record.key(), (k,v) -> v == null ? 1 : v+1);
    }

    public Map<Long, Integer> getIds() {
        return ids;
    }
}
