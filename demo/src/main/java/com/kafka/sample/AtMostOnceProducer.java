package com.kafka.sample;

import java.util.Properties;
import java.util.Random;

import de.huxhorn.sulky.ulid.ULID;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringSerializer;

public class AtMostOnceProducer {

    private static String topicName = "at-most-once-demo";

    public static void main(String[] args) {
        Properties prop = new Properties();
        prop.put(ProducerConfig.CLIENT_ID_CONFIG, "AtMostOnceProducer");
        prop.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka1:9092,kafka2:9092,kafka3:9092");
        prop.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, false);
        prop.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class.getName());
        prop.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        prop.put("topic.metadata.refresh.interval.ms", "5000");

        try (KafkaProducer<Integer, String> producer = new KafkaProducer<>(prop);) {

            ULID ulid = new ULID();
            Random rand = new Random();
            System.out.println("sending...");
            for (int i = 0; i < 50; i++) {

                int key = rand.nextInt(3);
                String value = "[" + ulid.nextULID() + "]Hello Sample Kafka";

                ProducerRecord<Integer, String> record = new ProducerRecord<>(topicName, key, value);

                producer.send(record, new Callback() {
                    public void onCompletion(RecordMetadata metadata, Exception exception) {
                        if (metadata != null) {
                            System.out.println(
                                    "message(" + key + ", " + value + ") sent to partition(" + metadata.partition()
                                            + "), " + "offset(" + metadata.offset() + ")");
                        } else {
                            exception.printStackTrace();
                        }
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}