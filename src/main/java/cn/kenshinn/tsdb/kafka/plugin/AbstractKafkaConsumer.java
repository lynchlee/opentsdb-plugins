package cn.kenshinn.tsdb.kafka.plugin;

import cn.kenshinn.tsdb.thrift.rpc.service.TSDBDelegate;
import cn.kenshinn.tsdb.thrift.rpc.service.ThriftMetricsService;
import kafka.consumer.KafkaStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

/**
 * Description for Class AbstractKafkaConsumer
 *
 * @author Lynch Lee<Lynch.lee9527@gmail.com>
 * @version 2016-01-16.
 */
public abstract class AbstractKafkaConsumer implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(AbstractKafkaConsumer.class);


    protected final ThriftMetricsService metricsService;
    protected String topic;
    protected boolean skipTSDB;
    protected Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap;

    public AbstractKafkaConsumer(TSDBDelegate tsdb, String topic, boolean skipTSDB,
                                 Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap) {
        this.topic = topic;
        this.metricsService = new ThriftMetricsService(tsdb);
        this.skipTSDB = skipTSDB;
        this.consumerMap = consumerMap;
    }


    @Override
    public void run() {
        try {
            startMetricsConsumer(topic, consumerMap);
        } catch (Exception e) {
            logger.error("streaming exception", e);
        }
    }


    private void startMetricsConsumer(String metricsTopic, Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap) {
        List<KafkaStream<byte[], byte[]>> metricsStream = consumerMap.get(metricsTopic);
        ExecutorService executorService = Executors.newFixedThreadPool(metricsStream.size());

        metricsStream.forEach(new Consumer<KafkaStream<byte[], byte[]>>() {
            @Override
            public void accept(KafkaStream<byte[], byte[]> stream) {
                executorService.submit(() -> persistMetrics(stream));
            }
        });
    }


    protected abstract void persistMetrics(KafkaStream<byte[], byte[]> kafkaStreams);

}
