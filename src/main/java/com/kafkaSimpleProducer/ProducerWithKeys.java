package com.kafkaSimpleProducer;

import java.util.Properties;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProducerWithKeys {

	public static void main(String[] args) {

		String bootstrapServer = "localhost:9092";

		Logger logger = LoggerFactory.getLogger(ProducerWithKeys.class);

		// Create producer properties
		Properties props = new Properties();
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

		// Create producer client
		KafkaProducer<String, String> producer = new KafkaProducer<>(props);

		// Send data
		for (int i = 0; i < 20; i++) {

			String topic = "fst_topic";
			String key = "id-" + Integer.toString(i);
			String value = "Message # " + Integer.toString(i);

			ProducerRecord<String, String> producerRecord = new ProducerRecord<>(topic, key, value);
			producer.send(producerRecord, new Callback() {
				@Override
				public void onCompletion(RecordMetadata metadata, Exception exception) {
					// Execute every time record is successfully sent or Exception is thrown
					if (null == exception) {
						logger.info("\nReceived new metadata.." + "\nTopic: " + metadata.topic() + "\nPartition: "
								+ metadata.partition() + "\nOffset: " + metadata.offset() + "\nTimestamp: "
								+ metadata.timestamp());
					} else {
						logger.error("Error while producing...", exception);
					}
				}
			});
		}

		// Flush data
		producer.flush();

		// Close producer
		producer.close();
	}

}