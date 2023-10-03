kafka-error-handling
---
This is a demo project to test out spring's error handling options for a kafka consumer 

Spring Boot 3 provides kafka consumer options for error handling and batching. This repo has a spring-boot app
with a single test to publish to and consume from a kafka topic. You can play with some of the options, run the test
and watch what happens. 

Run the test in KafkaErrorHandlingApplicationTests. It uses an embedded kafka broker for easy testing.

## What should I try? 

Some of the options you might want to experiment with are: 

- batch consuming of records vs single message consumers
- batch sizes
- @KafkaListener's errorHandler property vs the errorHandler configured in the KafkaListenerContainerFactory
- Retry Backoff's
- Recovery strategies

## Where are those settings? 

### batch message consumer
Batch consuming options can be changed in the MessageConsumer class. The @KafkaListener annotation has a 'batch' property.
If you set batch=true, you should have a method signature that accepts a list of records. 

### Batch size 
You can set the batch size in the application.properties file with 
the **spring.kafka.consumer.max-poll-records** property

### @KafkaListener's errorHandler
The name of the bean can be set in @KafkaListener annotation of the MessageConsumer class. The errorHandler bean needs
to be defined in the sprint context, which is created in the KafkaErrorHandlingApplication class. 

### KafkaListenerContainerFactory
KafkaListenerContainerFactory error handling is set up with Beans defined in the KafkaErrorHandlingApplication class,
and the KafkaListenerContainerFactory is set in the @KafkaListener annotation's containerFactory property in MessageConsumer

### Backoff and recovery strategies
Are set up as part of the KafkaListenerContainerFactory, see the bean definitions in KafkaErrorHandlingApplication

