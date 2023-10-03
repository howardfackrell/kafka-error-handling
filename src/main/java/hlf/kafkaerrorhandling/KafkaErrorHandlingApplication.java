package hlf.kafkaerrorhandling;

import hlf.kafkaerrorhandling.eventhandlers.DefaultWidgetEventHandler;
import hlf.kafkaerrorhandling.eventhandlers.FlakyWidgetEventHandler;
import hlf.kafkaerrorhandling.eventhandlers.IdCapturingWidgetEventHandler;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.kafka.ConcurrentKafkaListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.listener.ConsumerRecordRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.listener.KafkaListenerErrorHandler;
import org.springframework.kafka.listener.ListenerExecutionFailedException;
import org.springframework.messaging.Message;
import org.springframework.util.backoff.FixedBackOff;

import static java.util.List.of;

@SpringBootApplication
@EnableKafka
public class KafkaErrorHandlingApplication {

	public static void main(String[] args) {
		SpringApplication.run(KafkaErrorHandlingApplication.class, args);
	}

	@Bean
	public NewTopic widgetEventTopic() {
		return TopicBuilder.name("widgetEvent")
				.build();
	}

	@Bean
	public KafkaListenerErrorHandler widgetEventErrorHandler() {
		return new KafkaListenerErrorHandler() {
			@Override
			public Object handleError(Message<?> message, ListenerExecutionFailedException exception) {
				System.out.println("oops. " + message.getPayload().toString());
				return null;
			}
		};
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory widgetListenerContainerFactory(
			ConcurrentKafkaListenerContainerFactoryConfigurer configurer,
			ConsumerFactory<Object, Object> kafkaConsumerFactory
	) {
		ConcurrentKafkaListenerContainerFactory<Object, Object> listenerFactory = new ConcurrentKafkaListenerContainerFactory<>();
		configurer.configure(listenerFactory, kafkaConsumerFactory);
		var recoverer = new ConsumerRecordRecoverer() {
			@Override
			public void accept(ConsumerRecord<?, ?> consumerRecord, Exception e) {
				System.out.println("RECOVERING... " + e.getMessage());
			}
		};

		var errorHandler = new DefaultErrorHandler(recoverer, new FixedBackOff(200l, 3));
		listenerFactory.setCommonErrorHandler(errorHandler);
		return listenerFactory;
	}

	@Bean
	public IdCapturingWidgetEventHandler widgetEventHandler() {
		var def = new DefaultWidgetEventHandler();
		var flaky = new FlakyWidgetEventHandler(def, 10);
		return new IdCapturingWidgetEventHandler(flaky);
	}
}
