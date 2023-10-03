package hlf.kafkaerrorhandling;

import hlf.kafkaerrorhandling.eventhandlers.IdCapturingWidgetEventHandler;
import hlf.kafkaerrorhandling.eventhandlers.WidgetEventHandler;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;

@SpringBootTest
@EmbeddedKafka(
		topics = {"widgetEvent"}
)
class KafkaErrorHandlingApplicationTests {

	@Autowired
	private IdCapturingWidgetEventHandler widgetEventHandler;

	@Test
	void contextLoads() throws Exception {
		Thread.sleep(10000L);

		System.out.println("Summarized results: ");
		for (long k = 0; k < MessageProducer.MESSAGE_COUNT; k++) {
			Integer v = widgetEventHandler.getIds().get(k);
			if ( v == null) {
				System.out.println(String.format("id %d was never successfully consumed", k));
			}
			else  {
				System.out.println(String.format("id %d was consumed %d times", k, v));
			}
		}

		Assertions.assertThat(widgetEventHandler.getIds())
				.hasSize(MessageProducer.MESSAGE_COUNT)
				.as("All Messages should get consumed successfully");
	}
}
