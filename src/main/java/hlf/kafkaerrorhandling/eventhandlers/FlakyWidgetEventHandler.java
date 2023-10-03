package hlf.kafkaerrorhandling.eventhandlers;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.util.Random;

public class FlakyWidgetEventHandler implements WidgetEventHandler {

    private final WidgetEventHandler delegate;
    private final int percentFailure;
    private static final Random random = new Random();

    public FlakyWidgetEventHandler(WidgetEventHandler delegate, int percentFailure) {
        this.percentFailure = percentFailure;
        this.delegate = delegate;
    }

    @Override
    public void handle(ConsumerRecord<Long, String> record) {
        if (fails()) {
            throw new RuntimeException("FlakyWidgetEventHandler is acting flaky");
        }
        delegate.handle(record);
    }

    private boolean fails() {
        var num = Math.abs(random.nextInt() % 100);
        return num < percentFailure;
    }
}
