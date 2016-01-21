package com.easemob.tsdb.kafka.plugin;

import com.easemob.tsdb.thrift.models.TSData;
import com.easemob.tsdb.thrift.rpc.service.TSDBDelegate;
import kafka.consumer.KafkaStream;
import kafka.message.MessageAndMetadata;
import org.apache.thrift.TDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;

import static com.easemob.tsdb.utils.TSDataUtils.*;

/**
 * Description for Class KafkaTSDataTopicConsumer
 *
 * @author Lynch Lee<Lynch.lee9527@gmail.com>
 * @version 2016-01-14.
 */
public class KafkaTSDataConsumer extends AbstractKafkaConsumer {

    private static final Logger logger = LoggerFactory.getLogger(KafkaTSDataConsumer.class);

    /**
     * 按照设计, 每个topic一个线程, 线程内跑一个任务, 任务内的业务逻辑内容由KafkaPlaintextConsumer或者KafkaTSDataConsumer来指定.
     * 此计数器应该针对单个任务
     */
    private final AtomicLong tsdataMetricsCounter = new AtomicLong();
    private final TDeserializer deserializer = new TDeserializer();


    public KafkaTSDataConsumer(String tsdataTopic, TSDBDelegate tsdb, boolean skipTSDB,
                               Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap) {
        super(tsdb, tsdataTopic, skipTSDB, consumerMap);
    }


    public void persistMetrics(KafkaStream<byte[], byte[]> kafkaStreams) {
        for (MessageAndMetadata<byte[], byte[]> message : kafkaStreams) {

            TSData tsData = new TSData();

            try {
                deserializer.deserialize(tsData, message.message());

                if (logger.isDebugEnabled()) {
                    logger.debug("persisting TSData metrics : {}, queue size is {}", tsData);
                }

                if (skipTSDB) {
                    if (logger.isDebugEnabled()) {
                        logger.debug("skipping persist {} to OpenTSDB", tsData);
                    }
                } else {

                    if (isInvalidMetrics(tsData.getName())) {
                        logger.warn("invalid format of metrics name {}", tsData.getName());
                        return;
                    }

                    metricsService.putTSData(tsData);
                    tsdataMetricsCounter.incrementAndGet();
                }

                if (logger.isDebugEnabled()) {
                    logger.debug("persisting TSData metrics to queue done");
                }
            } catch (Exception e) {
                logger.error("Failed to put tsdata metrics {}", tsData, e);
            }
        }
    }

}
