package com.sweethome.notificationservice;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import java.util.Set;


/**
 * Kafka message consumer & notifier
 */
public class MessageNotificationConsumer {

    private static final String MESSAGE_TOPIC = "message";

    public static void main(String[] args) {
        //Consumer Properties
        Properties props = new Properties();
        props.setProperty("bootstrap.servers", "ec2-user@ec2-35-169-87-94.compute-1.amazonaws.com:9092");
        props.setProperty("group.id", "test");
        props.setProperty("enable.auto.commit", "true");
        props.setProperty("auto.commit.interval.ms", "1000");
        props.setProperty("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.setProperty("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Collections.singletonList(MESSAGE_TOPIC));

        //Prints the topic subscription list
        Set<String> subscribedTopics = consumer.subscription();

        System.out.print("Listening to messages from Topic: ");
        subscribedTopics.stream().forEach(System.out::println);

        // Reading messages in a infinite loop, until service is stopped.
        try {
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
                for (ConsumerRecord<String, String> record : records)
                    System.out.printf("%nTopic = %s, offset = %d, key = %s%n value = %s%n"
                            , record.topic(), record.offset(), record.key(), record.value());
            }
        } finally {
            consumer.close();
        }
    }
}
