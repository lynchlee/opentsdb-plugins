package com.easemob.tsdb;

import com.easemob.tsdb.thrift.models.TSData;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.message.MessageAndMetadata;
import org.apache.thrift.TDeserializer;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @author stliu at apache.org
 * @since 5/30/15
 */
public class StandaloneConsumer {

    private static final Logger logger = LoggerFactory.getLogger(StandaloneConsumer.class);
    public static void main(String[] args) {
        Consumer consumer = new Consumer("tsdb");
        consumer.start();
    }
    public static class Consumer extends Thread {
        private final ConsumerConnector consumer;
        private final String topic;
        private final TDeserializer deserializer= new TDeserializer();

        public Consumer(String topic) {
            consumer = kafka.consumer.Consumer.createJavaConsumerConnector(
                    createConsumerConfig());
            this.topic = topic;
        }

        private ConsumerConfig createConsumerConfig() {
            Properties props = new Properties();
            props.put("zookeeper.connect", "192.168.100.61:2181");
            props.put("group.id", "test1");
            props.put("zookeeper.session.timeout.ms", "4000");
            props.put("zookeeper.sync.time.ms", "200");
            props.put("auto.commit.interval.ms", "1000");

            return new ConsumerConfig(props);

        }

        public void run() {
            Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
            topicCountMap.put(topic, 1);
            Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap = consumer.createMessageStreams(topicCountMap);
            KafkaStream<byte[], byte[]> stream = consumerMap.get(topic).get(0);
            for (MessageAndMetadata<byte[], byte[]> messageAndMetadata : stream) {

                TSData tsData = new TSData();

                try {
                    deserializer.deserialize(tsData, messageAndMetadata.message());
                    logger.debug("persisting TSData metrics : {}", tsData);
                } catch (TException e) {
                    logger.error("Failed to put tsdata metrics {}", tsData);
                }
            }
        }
    }
}
