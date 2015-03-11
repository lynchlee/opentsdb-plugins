package com.easemob.tsdb.thrift.rpc;

import com.easemob.tsdb.thrift.rpc.service.TSDBDelegate;

import java.util.HashMap;
import java.util.Map;

/**
 * @author stliu <stliu@apache.org>
 * @date 3/11/15
 */
public class MockConfig implements TSDBDelegate.ConfigDelegate, Constants {
    private final Map<String, String> map = new HashMap<>();
    public MockConfig(){
        map.put(PLUGIN_THRIFT_HOST, "127.0.0.1");
        map.put(PLUGIN_KAFKA_GROUP_ID, this.getClass().getName());
        map.put(PLUGIN_KAFKA_ZOOKEEPER_HOST, "127.0.0.1:2181");
        map.put(PLUGIN_KAFKA_PLAIN_TEXT_METRICS_TOPIC, "plain");
        map.put(PLUGIN_KAFKA_PLAIN_TEXT_METRICS_TOPIC_PARTITIONS, "10");


    }
    @Override
    public Map<String, String> getMap() {
        return map;
    }
}
