package cn.kenshinn.tsdb.kafka.plugin;

import cn.kenshinn.tsdb.thrift.models.TSData;
import cn.kenshinn.tsdb.thrift.rpc.service.TSDBDelegate;
import kafka.consumer.KafkaStream;
import kafka.message.MessageAndMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import static cn.kenshinn.tsdb.utils.TSDataUtils.*;

/**
 * Description for Class KafkaTSDataConsumer
 * Metrics schema(just only one space between fragements):
 * name         tags                   timestamp  value
 * sys.cpu.user host=webserver01,cpu=0 1356998400 1
 *
 * @author Lynch Lee<Lynch.lee9527@gmail.com>
 * @version 2016-01-14.
 */
public class KafkaPlaintextConsumer extends AbstractKafkaConsumer {

    private static final Logger logger = LoggerFactory.getLogger(KafkaPlaintextConsumer.class);

    /**
     * 按照设计, 每个topic一个线程, 线程内跑一个任务, 任务内的业务逻辑内容由KafkaPlaintextConsumer或者KafkaTSDataConsumer来指定.
     * 此计数器应该针对单个任务
     */
    private final AtomicLong plainTextMetricsCounter = new AtomicLong();


    public KafkaPlaintextConsumer(String plainTextTopic, TSDBDelegate tsdb, boolean skipTSDB,
                                  Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap) {
        super(tsdb, plainTextTopic, skipTSDB, consumerMap);
    }

    protected void persistMetrics(KafkaStream<byte[], byte[]> stream) {
        for (MessageAndMetadata<byte[], byte[]> message : stream) {
            String metrics = null;

            try {
                metrics = new String(message.message());

                if (logger.isDebugEnabled()) {
                    logger.debug("persisting plain text metrics : {}, queue size is {}", metrics);
                }
                if (skipTSDB) {
                    if (logger.isDebugEnabled()) {
                        logger.debug("skipping persist {} to OpenTSDB", metrics);
                    }
                } else {
                    TSData tsData = parseString2TSData(metrics);

                    if (isValidMetricAndTags(tsData.getName(), tsData.getTags())) {
                        return;
                    }

                    metricsService.putString(metrics);
                    plainTextMetricsCounter.incrementAndGet();
                }

                if (logger.isDebugEnabled()) {
                    logger.debug("persisting plain text metrics to queue done");
                }
            } catch (Exception e) {
                logger.error("Failed to put plain text metrics {}", metrics);
            }
        }
    }
}
