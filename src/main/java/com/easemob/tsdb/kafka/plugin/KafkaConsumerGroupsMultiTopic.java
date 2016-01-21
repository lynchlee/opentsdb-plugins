package com.easemob.tsdb.kafka.plugin;

import com.easemob.tsdb.thrift.rpc.Constants;
import com.easemob.tsdb.thrift.rpc.service.TSDBDelegate;
import com.easemob.tsdb.utils.ConfigUtils;
import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Description for Class KafkaConsumerGroups1
 *
 * @author Lynch Lee<Lynch.lee9527@gmail.com>
 * @version 2016-01-13.
 */
public class KafkaConsumerGroupsMultiTopic implements Closeable, Constants {

    private final static Logger logger = LoggerFactory.getLogger(KafkaConsumerGroupsMultiTopic.class);

//    private final static int PROCESSOR_COUNT = Runtime.getRuntime().availableProcessors();
    private final TSDBDelegate tsdb;
    private final Map<String, String> configuration;
    private ExecutorService executor ;

    public KafkaConsumerGroupsMultiTopic(TSDBDelegate tsdb) {
        this.tsdb = tsdb;
        this.configuration = tsdb.getConfig().getMap();
    }


    public void start() {
        String[] consumedTopics = getConsumedTopics();

        executor = Executors.newFixedThreadPool(consumedTopics.length);

        for (String topic : consumedTopics) {
            checkConfigRequired(topic);

            String topicSkipwriteConfig = getRealConfigOption(PLUGIN_KAFKA_TOPICN_SKIPWRITE, topic);
            Boolean skipwrite = ConfigUtils.getBooleanValue(configuration, topicSkipwriteConfig, false);

            ConsumerConfig kafkaConsumerConfig = getKafkaConsumerConfig(topic, skipwrite);

            final ConsumerConnector consumer = Consumer.createJavaConsumerConnector(kafkaConsumerConfig);

            String topicNameConfig = getRealConfigOption(PLUGIN_KAFKA_TOPICN_NAME, topic);
            String topicName = configuration.get(topicNameConfig);

            Map<String, Integer> topicCountMap = new HashMap<>();
            topicCountMap.put(topicName, 1);
            final Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap = consumer.createMessageStreams(topicCountMap);

            String topicDatatypeConfig = getRealConfigOption(PLUGIN_KAFKA_TOPICN_DATATYPE, topic);
            String topicDatatype = configuration.get(topicDatatypeConfig);
            switch (topicDatatype) {
                case "tsdata":
                    executor.submit(new KafkaTSDataConsumer(topicName, tsdb, skipwrite, consumerMap));
                    logger.info("Kafka consumer for topic:{} started", topic);
                    break;
                case "plaintext":
                    executor.submit(new KafkaPlaintextConsumer(topicName, tsdb, skipwrite, consumerMap));
                    logger.info("Kafka consumer for topic:{} started", topic);
                    break;
                default:
                    logger.warn("not kafka consumer found for your topic data type, please check this config:{}," +
                                    "and value of it must be tsdata or plaintext", PLUGIN_KAFKA_TOPICN_DATATYPE);
            }
        }
    }


    private String[] getConsumedTopics() {
        String consumedTopics = configuration.get(PLUGIN_KAFKA_METRICS_TOPICS);
        if (StringUtils.isBlank(consumedTopics)) {
            logger.info("No kafka topic provided, skipping");
            throw new IllegalArgumentException("required property is missing: " + PLUGIN_KAFKA_METRICS_TOPICS);
        }

        return consumedTopics.split(",");
    }


    private Properties loadKafkaConfigProps(String topic) {
        Properties props = new Properties();
        configuration.keySet().stream().filter(key -> key.startsWith(PLUGIN_KAFKA_PERFIX + topic)).forEach(key -> {
            String subKey = key.substring((PLUGIN_KAFKA_PERFIX + topic + 1).length());
            String subV = configuration.get(key);
            logger.info("loading kafka plugin config for topic[{}] k:{}, v:{}", topic, subKey, subV);

            props.setProperty(subKey, subV);
        });

        return props;
    }


    private void checkConfigRequired(String topic) {
        String topicNameConfig = getRealConfigOption(PLUGIN_KAFKA_TOPICN_NAME, topic);
        String topicName = configuration.get(topicNameConfig);
        if (StringUtils.isBlank(topicName)) {
            throw new IllegalArgumentException("required property is missing: " + topicNameConfig);
        }

        String topicDatatypeConfig = getRealConfigOption(PLUGIN_KAFKA_TOPICN_DATATYPE, topic);
        String topicDatatype = configuration.get(topicDatatypeConfig);
        if (StringUtils.isBlank(topicDatatype)) {
            throw new IllegalArgumentException("required property is missing: " + topicDatatypeConfig);
        }

        String topicSkipwriteConfig = getRealConfigOption(PLUGIN_KAFKA_TOPICN_SKIPWRITE, topic);
        String topicSkipwrite = configuration.get(topicSkipwriteConfig);
        if (StringUtils.isBlank(topicSkipwrite)) {
            throw new IllegalArgumentException("required property is missing: " + topicSkipwriteConfig);
        }
    }

    private ConsumerConfig getKafkaConsumerConfig(String topic, boolean topicSkipwrite) {
        Properties props = loadKafkaConfigProps(topic);

        String topicNZkHostConfig = getRealConfigOption(PLUGIN_KAFKA_TOPICN_ZOOKEEPER_CONNECT, topic);
        String zkHost = configuration.get(topicNZkHostConfig);
        if (StringUtils.isBlank(zkHost)) {
            throw new IllegalArgumentException("required property is missing: " + topicNZkHostConfig);
        }
        props.put("zookeeper.connect", zkHost);

        String groupIdConfig = getRealConfigOption(PLUGIN_KAFKA_TOPICN_GROUP_ID, topic);
        String groupId = configuration.get(groupIdConfig);
        if (StringUtils.isBlank(groupId)) {
            throw new IllegalArgumentException("required property is missing: " + groupIdConfig);
        }
        if (topicSkipwrite) {
            groupId = groupId + "-" + topic + "-skip";
        }
        props.put("group.id", groupId);

        return new ConsumerConfig(props);
    }


    private String getRealConfigOption(String configTemplate, String topicName) {
        if (StringUtils.isBlank(configTemplate)) {
            return "";
        }

        return configTemplate.replace("topicN", topicName);
    }

    @Override
    public void close() throws IOException {
        if (executor != null && !executor.isShutdown()) {
            logger.info("Kafka Consumer Group is shutting down");
            executor.shutdown();
        }
    }

}
