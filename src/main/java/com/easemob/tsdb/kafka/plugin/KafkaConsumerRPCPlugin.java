package com.easemob.tsdb.kafka.plugin;

import com.easemob.tsdb.thrift.models.TSData;
import com.easemob.tsdb.thrift.rpc.AbstractTSDBRpcPlugin;
import com.easemob.tsdb.thrift.rpc.TSDBWrapper;
import com.stumbleupon.async.Deferred;
import net.opentsdb.core.TSDB;
import net.opentsdb.stats.StatsCollector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.*;

/**
 * @author stliu <stliu@apache.org>
 * @date 3/11/15
 */
@SuppressWarnings("UnusedDeclaration")
public class KafkaConsumerRPCPlugin extends AbstractTSDBRpcPlugin {
    private final static Logger logger = LoggerFactory.getLogger(KafkaConsumerRPCPlugin.class);
    private static volatile KafkaConsumerGroups kafkaConsumerGroups;
    private final ExecutorService executorService = Executors.newCachedThreadPool();
    @Override
    public void initialize(TSDB tsdb) {
        kafkaConsumerGroups = new KafkaConsumerGroups(new TSDBWrapper(tsdb));
        executorService.submit(kafkaConsumerGroups);
    }



    @Override
    public Deferred<Object> shutdown() {
        Deferred<Object> deferred = new Deferred<>();
        try {
            kafkaConsumerGroups.close();
            executorService.shutdown();
        } catch (Exception e) {
            logger.error("Failed to stop kafka consumer group", e);
        }
        return deferred;
    }


    @Override
    public void collectStats(StatsCollector statsCollector) {
//TODO
    }
}
