package com.easemob.tsdb.kafka.plugin;

import com.easemob.tsdb.thrift.models.TSData;
import com.easemob.tsdb.thrift.rpc.Constants;
import com.easemob.tsdb.thrift.rpc.service.TSDBDelegate;
import com.easemob.tsdb.thrift.rpc.service.ThriftMetricsService;
import com.easemob.tsdb.utils.ConfigUtils;
import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.message.MessageAndMetadata;
import org.apache.thrift.TDeserializer;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author stliu <stliu@apache.org>
 * @date 3/11/15
 */
public class KafkaConsumerGroups implements Closeable, Constants {
    private final static Logger logger = LoggerFactory.getLogger(KafkaConsumerGroups.class);
    private final static int PROCESSOR_COUNT = Runtime.getRuntime().availableProcessors();
    private final TSDBDelegate tsdb;
    private final Map<String, String> configuration;
    private ExecutorService executor;

    private static final AtomicLong plainTextMetricsCounter = new AtomicLong();
    private static final AtomicLong tsdataMetricsCounter = new AtomicLong();

    public KafkaConsumerGroups(TSDBDelegate tsdb) {
        this.tsdb = tsdb;
        this.configuration = tsdb.getConfig().getMap();
    }

    public void start() {
        Map<String, Integer> topicCountMap = new HashMap<String, Integer>();

        String plainTextTopic = configuration.get(PLUGIN_KAFKA_PLAIN_TEXT_METRICS_TOPIC);
        int plainTextTopicPartition = ConfigUtils.getIntValue(configuration, PLUGIN_KAFKA_PLAIN_TEXT_METRICS_TOPIC_PARTITIONS, PROCESSOR_COUNT);
        if (plainTextTopic != null && !plainTextTopic.isEmpty()) {
            topicCountMap.put(plainTextTopic, plainTextTopicPartition);
            logger.info("Consume plain text metrics from topic " + plainTextTopic + " with partition size " + plainTextTopicPartition);
        }
        String tsdataTopic = configuration.get(PLUGIN_KAFKA_TSDATA_METRICS_TOPIC);
        int tsdataTopicPartition = ConfigUtils.getIntValue(configuration, PLUGIN_KAFKA_TSDATA_METRICS_TOPIC_PARTITIONS, PROCESSOR_COUNT);

        if (tsdataTopic != null && !tsdataTopic.isEmpty()) {
            topicCountMap.put(tsdataTopic, tsdataTopicPartition);
            logger.info("Consume tsdata metrics from topic " + tsdataTopic + " with partition size " + tsdataTopicPartition);
        }
        if (topicCountMap.isEmpty()) {
            logger.info("No kafka topic provided, skipping");
            return;
        }
        executor = Executors.newFixedThreadPool(plainTextTopicPartition + tsdataTopicPartition);

        final ConsumerConnector consumer = Consumer.createJavaConsumerConnector(getKafkaConsumerConfig());

        final Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap = consumer.createMessageStreams(topicCountMap);


        startPlainTextMetricsConsumer(plainTextTopic, consumerMap);
        startTsdataMetricsConsumer(tsdataTopic, consumerMap);

        logger.info("Kafka consumer groups started");

    }

    private void startTsdataMetricsConsumer(String tsdataTopic, Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap) {
        List<KafkaStream<byte[], byte[]>> tsdataMetricStream = consumerMap.get(tsdataTopic);

        if (tsdataMetricStream != null) {
            tsdataMetricStream.forEach(new java.util.function.Consumer<KafkaStream<byte[], byte[]>>() {
                @Override
                public void accept(KafkaStream<byte[], byte[]> stream) {
                    executor.submit(new TsdataMetricsConsumer(stream, tsdb));
                }
            });
        }
    }

    private void startPlainTextMetricsConsumer(String plainTextTopic, Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap) {
        List<KafkaStream<byte[], byte[]>> plainTextMetricStream = consumerMap.get(plainTextTopic);

        if (plainTextMetricStream != null) {
            plainTextMetricStream.forEach(new java.util.function.Consumer<KafkaStream<byte[], byte[]>>() {
                @Override
                public void accept(KafkaStream<byte[], byte[]> stream) {
                    executor.submit(new PlainMetricsConsumer(stream, tsdb));
                }
            });
        }
    }

    public static class PlainMetricsConsumer implements Runnable {
        protected final KafkaStream<byte[], byte[]> kafkaStream;
        protected final ThriftMetricsService metricsService;

        public PlainMetricsConsumer(KafkaStream<byte[], byte[]> stream, TSDBDelegate tsdb) {
            this.kafkaStream = stream;
            this.metricsService = new ThriftMetricsService(tsdb);
        }

        public void run() {
            ConsumerIterator<byte[], byte[]> it = kafkaStream.iterator();
            while (it.hasNext())
                persistMetrics(it.next());
            System.out.println("-------------");
        }

        protected void persistMetrics(MessageAndMetadata<byte[], byte[]> message) {
            String metrics = null;
            try {
                metrics = new String(message.message());
                logger.debug("persisting metrics : "+metrics);
                metricsService.putString(metrics);
                plainTextMetricsCounter.incrementAndGet();
            } catch (TException e) {
                logger.error("Failed to put string metrics: " + metrics);
            }
        }
    }


    public static class TsdataMetricsConsumer extends PlainMetricsConsumer {
        private final TDeserializer deserializer= new TDeserializer();

        public TsdataMetricsConsumer(KafkaStream<byte[], byte[]> stream, TSDBDelegate tsdb) {
            super(stream, tsdb);
        }
        @Override
        protected void persistMetrics(MessageAndMetadata<byte[], byte[]> message) {
            TSData tsData = new TSData();

            try {
                deserializer.deserialize(tsData, message.message());
                metricsService.putTSData(tsData);
                tsdataMetricsCounter.incrementAndGet();
            } catch (TException e) {
                logger.error("Failed to put tsdata metrics " + tsData);
            }
        }
    }

    ConsumerConfig getKafkaConsumerConfig() {


        Properties props = new Properties();
        configuration.keySet().stream().filter(key -> key.startsWith(PLUGIN_KAFKA_PERFIX)).forEach(key -> {
            String subKey = key.substring(PLUGIN_KAFKA_PERFIX.length());
            props.setProperty(subKey, configuration.get(key));
        });
        String zkHost = configuration.get(PLUGIN_KAFKA_ZOOKEEPER_HOST);
        if (zkHost == null || zkHost.isEmpty()) {
            logger.warn("no configuration " + PLUGIN_KAFKA_ZOOKEEPER_HOST + " provided, using " + OPENTSDB_ZOOKEEPER_HOST + "from OpenTSDB");
            zkHost = configuration.get(OPENTSDB_ZOOKEEPER_HOST);
        }
        String groupId = configuration.get(PLUGIN_KAFKA_GROUP_ID);
        if (zkHost == null || groupId == null) {
            throw new IllegalArgumentException("required property is missing: " + PLUGIN_KAFKA_ZOOKEEPER_HOST + "," + PLUGIN_KAFKA_GROUP_ID);
        }
        props.put("zookeeper.connect", zkHost);
        props.put("group.id", groupId);
        return new ConsumerConfig(props);
    }

    @Override
    public void close() throws IOException {
        if (executor != null && !executor.isShutdown()) {
            logger.info("Kafka Consumer Group is shutting down");
            executor.shutdown();
        }
    }
}
