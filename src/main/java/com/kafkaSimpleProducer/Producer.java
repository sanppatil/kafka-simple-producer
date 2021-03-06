package com.kafkaSimpleProducer;

import java.util.Properties;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;

public class Producer {

	public static void main(String[] args) {

		String bootstrapServer = "localhost:9092";
		
		// Create producer properties
		Properties props = new Properties();
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

		// Create producer client
		KafkaProducer<String, String> producer = new KafkaProducer<>(props);
		
		// Send data
		for (int i = 0; i < 100; i++) {
			ProducerRecord<String, String> producerRecord = new ProducerRecord<>("first-topic", Integer.toString(i));
			producer.send(producerRecord);
		}
		
		// Flush data
		producer.flush();
		
		// Close producer
		producer.close();
	}

}