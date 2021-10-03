package com.sweethome.bookingservice.config;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Properties;


/**
 * Implementation of Kafka config service to sent payment success async message.
 */
@Component
public class KafkaConfigImpl implements KafkaConfig {
    @Override
    public void publish(String topic, String key, String value) throws IOException {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "ec2-user@ec2-35-169-87-94.compute-1.amazonaws.com:9092");
        properties.put("acks", "all");
        properties.put("retries", 0);
        properties.put("linger.ms", 0);
        properties.put("partitioner.class", "org.apache.kafka.clients.producer.internals.DefaultPartitioner");
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("request.timeout.ms", 30000);
        properties.put("timeout.ms", 30000);
        properties.put("max.in.flight.requests.per.connection", 5);
        properties.put("retry.backoff.ms", 5);

        //Instantiate Producer Object
        Producer<String, String> producer = new KafkaProducer<>(properties);

        System.out.println(producer.metrics());
        //Send Single message
        producer.send(new ProducerRecord<String, String>(topic, key, value));

        producer.close();
    }
}
