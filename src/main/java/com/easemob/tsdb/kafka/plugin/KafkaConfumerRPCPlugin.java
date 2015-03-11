package com.easemob.tsdb.kafka.plugin;

import com.easemob.tsdb.thrift.rpc.AbstractTSDBRpcPlugin;
import com.easemob.tsdb.thrift.rpc.TSDBWrapper;
import com.stumbleupon.async.Deferred;
import net.opentsdb.core.TSDB;
import net.opentsdb.stats.StatsCollector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @author stliu <stliu@apache.org>
 * @date 3/11/15
 */
@SuppressWarnings("UnusedDeclaration")
public class KafkaConfumerRPCPlugin extends AbstractTSDBRpcPlugin {
    private final static Logger logger = LoggerFactory.getLogger(KafkaConfumerRPCPlugin.class);
    private static volatile KafkaConsumerGroups kafkaConsumerGroups;

    @Override
    public void initialize(TSDB tsdb) {
        kafkaConsumerGroups = new KafkaConsumerGroups(new TSDBWrapper(tsdb));
        kafkaConsumerGroups.start();
    }


    @Override
    public Deferred<Object> shutdown() {
        Deferred<Object> deferred = new Deferred<>();
        try {
            kafkaConsumerGroups.close();
        } catch (IOException e) {
            logger.error("Failed to stop kafka consumer group", e);
        }
        return deferred;
    }


    @Override
    public void collectStats(StatsCollector statsCollector) {
//TODO
    }
}
