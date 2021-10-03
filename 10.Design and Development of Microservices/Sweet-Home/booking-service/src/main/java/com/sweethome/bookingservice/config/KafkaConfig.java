package com.sweethome.bookingservice.config;

import java.io.IOException;


/**
 * Kafka async messaging interface
 */
public interface KafkaConfig {

    void publish(String topic, String key, String value) throws IOException;
}
