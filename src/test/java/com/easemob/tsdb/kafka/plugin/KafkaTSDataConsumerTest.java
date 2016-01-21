package com.easemob.tsdb.kafka.plugin;


import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.message.MessageAndMetadata;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Description for Class KafkaTSDataConsumerTests
 *
 * @author Lynch Lee<Lynch.lee9527@gmail.com>
 * @version 2016-01-14.
 */
public class KafkaTSDataConsumerTest {

    public static void main(String[] args) {
        new KafkaTSDataConsumerTest().build();
    }

    public void build() {
        String topic = "tsdb";

        Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
        topicCountMap.put(topic, 10);
        final ConsumerConnector consumer = Consumer.createJavaConsumerConnector(getKafkaConsumerConfig());

        final Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap = consumer.createMessageStreams(topicCountMap);

        KafkaTSDataConsumer  kafkaTSDataConsumer = new KafkaTSDataConsumer(topic, null, false, consumerMap);
        kafkaTSDataConsumer.run();
    }

    ConsumerConfig getKafkaConsumerConfig() {
        Properties props = new Properties();
        props.put("zookeeper.connect", "127.0.0.1:2181");
        props.put("group.id", this.getClass().getName() + System.currentTimeMillis());
        return new ConsumerConfig(props);
    }

}
