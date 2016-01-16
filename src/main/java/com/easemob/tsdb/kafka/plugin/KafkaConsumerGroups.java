//package com.easemob.tsdb.kafka.plugin;
//
//import com.easemob.tsdb.thrift.models.TSData;
//import com.easemob.tsdb.thrift.rpc.Constants;
//import com.easemob.tsdb.thrift.rpc.service.TSDBDelegate;
//import com.easemob.tsdb.thrift.rpc.service.ThriftMetricsService;
//import kafka.consumer.Consumer;
//import kafka.consumer.ConsumerConfig;
//import kafka.consumer.KafkaStream;
//import kafka.javaapi.consumer.ConsumerConnector;
//import kafka.message.MessageAndMetadata;
//import org.apache.commons.lang3.StringUtils;
//import org.apache.thrift.TDeserializer;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.io.Closeable;
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Properties;
//
///**
// * @author stliu <stliu@apache.org>
// * @date 3/11/15
// */
//public class KafkaConsumerGroups implements Closeable, Runnable, Constants {
//    private final static Logger logger = LoggerFactory.getLogger(KafkaConsumerGroups.class);
//    private final Map<String, String> configuration;
//    private ConsumerConnector consumer;
//    private final String topic;
//    private final TDeserializer deserializer = new TDeserializer();
//    private final ThriftMetricsService metricsService;
//    private final ConsumerConfig consumerConfig;
//    private final boolean skipTSDB;
//
//    public KafkaConsumerGroups(TSDBDelegate tsdb) {
//        this.configuration = tsdb.getConfig().getMap();
//        this.topic = configuration.get(PLUGIN_KAFKA_TSDATA_METRICS_TOPIC);
//        this.metricsService = new ThriftMetricsService(tsdb);
//
//        this.skipTSDB =Boolean.valueOf( configuration.get("em.skip"));
//        logger.info("skip opentsdb is enabled: {} ", skipTSDB);
//
//        this.consumerConfig = getKafkaConsumerConfig(skipTSDB);
//    }
//
//
//    ConsumerConfig getKafkaConsumerConfig(boolean skipTSDB) {
//        Properties props = new Properties();
//        configuration.keySet().stream().filter(key -> key.startsWith(PLUGIN_KAFKA_PERFIX)).forEach(key -> {
//            String subKey = key.substring(PLUGIN_KAFKA_PERFIX.length());
//            props.setProperty(subKey, configuration.get(key));
//        });
//        String zkHost = configuration.get(PLUGIN_KAFKA_ZOOKEEPER_HOST);
//        if (StringUtils.isBlank(zkHost)) {
//            throw new IllegalArgumentException("required property is missing: " + PLUGIN_KAFKA_ZOOKEEPER_HOST);
//        }
//        /*if (zkHost == null || zkHost.isEmpty()) {
//            logger.warn("no configuration {}  provided, using {} from OpenTSDB", PLUGIN_KAFKA_ZOOKEEPER_HOST, OPENTSDB_ZOOKEEPER_HOST);
//            zkHost = configuration.get(OPENTSDB_ZOOKEEPER_HOST);
//        }*/
//        String groupId = configuration.get(PLUGIN_KAFKA_GROUP_ID);
//        if (StringUtils.isBlank(groupId)) {
//            throw new IllegalArgumentException("required property is missing: " + PLUGIN_KAFKA_GROUP_ID);
//        }
//        props.put("zookeeper.connect", zkHost);
//        if (skipTSDB) {
//            groupId = groupId + "-skip";
//        }
//        props.put("group.id", groupId);
//        return new ConsumerConfig(props);
//    }
//
//    @Override
//    public void close() throws IOException {
//        if (consumer != null) {
//            logger.info("Kafka Consumer Group is shutting down");
//            consumer.shutdown();
//        }
//    }
//
//    @Override
//    public void run() {
//        while (true) {
//            try {
//                startStreaming();
//            } catch (Exception e) {
//                logger.error("streaming exception", e);
//                try {
//                    close();
//                } catch (IOException e1) {
//                    logger.error("closing exception", e1);
//                }
//            }
//        }
//    }
//
//    private void startStreaming() {
//        consumer = Consumer.createJavaConsumerConnector(consumerConfig);
//        Map<String, Integer> topicCountMap = new HashMap<>();
//        topicCountMap.put(topic, 1);
//        Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap = consumer.createMessageStreams(topicCountMap);
//        KafkaStream<byte[], byte[]> stream = consumerMap.get(topic).get(0);
//        for (MessageAndMetadata<byte[], byte[]> messageAndMetadata : stream) {
//
//            TSData tsData = new TSData();
//
//            try {
//                deserializer.deserialize(tsData, messageAndMetadata.message());
//                logger.debug("persisting TSData metrics : {}, queue size is {}", tsData);
//                if (skipTSDB) {
//                    logger.debug("skipping persist {} to OpenTSDB", tsData);
//                } else {
//
//                    if (isInvalidMetrics(tsData.getName())) {
//                        logger.warn("invalid format of metrics name {}", tsData.getName());
//                        continue;
//                    }
//
//                    metricsService.putTSData(tsData);
//                }
//                logger.debug("persisting TSData metrics to queue done");
//            } catch (Exception e) {
//                logger.error("Failed to put tsdata metrics {}", tsData);
//            }
//        }
//    }
//
//    private boolean isInvalidMetrics(String metrics) {
//        return metrics.contains("#") || metrics.contains(":");
//    }
//
//}
