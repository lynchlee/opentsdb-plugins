package com.easemob.tsdb.kafka.plugin;

import com.easemob.tsdb.thrift.rpc.AbstractTSDBRpcPlugin;
import com.stumbleupon.async.Deferred;
import net.opentsdb.core.TSDB;
import net.opentsdb.stats.StatsCollector;

/**
 * @author stliu <stliu@apache.org>
 * @date 3/11/15
 */
public class KafkaConfumerRPCPlugin  extends AbstractTSDBRpcPlugin {
    @Override
    public void initialize(TSDB tsdb) {

    }

    @Override
    public Deferred<Object> shutdown() {
        return null;
    }


    @Override
    public void collectStats(StatsCollector statsCollector) {

    }
}
