package com.kafka.sample;

import java.time.Duration;
import java.util.Collections;
import java.util.Map;
import java.util.Properties;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.TopicPartition;

public class AtMostOnceConsumer {

    private static String topicName = "at-most-once-demo";

    public static void main(String[] args) {
        Properties prop = new Properties();
        prop.put(ConsumerConfig.CLIENT_ID_CONFIG, "AtMostOnceConsumer");
        prop.put(ConsumerConfig.GROUP_ID_CONFIG, "AtMostOnceConsumerGroup");
        prop.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka1:9092,kafka2:9092,kafka3:9092");
        prop.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "False");
        prop.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "30000");
        prop.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, IntegerDeserializer.class.getName());
        prop.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        prop.put(ConsumerConfig.ISOLATION_LEVEL_CONFIG, "read_committed");
        prop.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        prop.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, "1000");

        try (KafkaConsumer<Integer, String> consumer = new KafkaConsumer<>(prop);) {

            consumer.subscribe(Collections.singletonList(topicName));

            System.out.println("polling...");
            while (true) {
                ConsumerRecords<Integer, String> records = consumer.poll(Duration.ofSeconds(10));

                if (records.count() > 0) {
                    System.out.println("record count: " + String.valueOf(records.count()));

                    consumer.commitSync();
                    System.out.println("committed");

                    for (ConsumerRecord<Integer, String> record : records) {

                        System.out.println(String.format("key:%d, value:%s", record.key(), record.value()));
                        System.out.println("processing data...");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("close");
        }
    }
}