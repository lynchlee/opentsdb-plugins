package cn.kenshinn.tsdb.kafka.plugin;

import cn.kenshinn.tsdb.thrift.rpc.AbstractTSDBRpcPlugin;
import cn.kenshinn.tsdb.thrift.rpc.TSDBWrapper;
import com.stumbleupon.async.Deferred;
import net.opentsdb.core.TSDB;
import net.opentsdb.stats.StatsCollector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author stliu <stliu@apache.org>
 * @date 3/11/15
 */
@SuppressWarnings("UnusedDeclaration")
public class KafkaConsumerRPCPlugin extends AbstractTSDBRpcPlugin {
    private final static Logger logger = LoggerFactory.getLogger(KafkaConsumerRPCPlugin.class);

//    private static volatile KafkaConsumerGroups kafkaConsumerGroups;
//    private final ExecutorService executorService = Executors.newCachedThreadPool();

    private static volatile KafkaConsumerGroupsMultiTopic kafkaConsumerGroupsMultiTopic;

    @Override
    public void initialize(TSDB tsdb) {
        kafkaConsumerGroupsMultiTopic = new KafkaConsumerGroupsMultiTopic(new TSDBWrapper(tsdb));
        kafkaConsumerGroupsMultiTopic.start();

//        kafkaConsumerGroups = new KafkaConsumerGroups(new TSDBWrapper(tsdb));
//        executorService.submit(kafkaConsumerGroups);
    }


    @Override
    public Deferred<Object> shutdown() {
        Deferred<Object> deferred = new Deferred<>();
        try {
            kafkaConsumerGroupsMultiTopic.close();
//            kafkaConsumerGroups.close();
//            executorService.shutdown();
        } catch (Exception e) {
            logger.error("Failed to stop kafka consumer group", e);
        }
        return deferred;
    }


    @Override
    public void collectStats(StatsCollector statsCollector) {
    }
}
