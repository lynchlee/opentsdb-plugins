package cn.kenshinn.tsdb.thrift.rpc;

import cn.kenshinn.tsdb.thrift.rpc.service.TSDBDelegate;
import net.opentsdb.utils.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author stliu <stliu@apache.org>
 * @date 3/11/15
 */
public class MockConfig implements TSDBDelegate.ConfigDelegate, Constants {

    private static final Logger logger = LoggerFactory.getLogger(MockConfig.class);

    private Map<String, String> map = new HashMap<>();

    private static final String configFilePath = "/data/apps/work/kenshinn/opentsdb/opentsdb-plugins/src/test/resources/opentsdb.conf";

    public MockConfig(){
//        map.put(PLUGIN_THRIFT_HOST, "127.0.0.1");
//        map.put(PLUGIN_KAFKA_GROUP_ID, this.getClass().getName());
//        map.put(PLUGIN_KAFKA_ZOOKEEPER_HOST, "127.0.0.1:2181");
//        map.put(PLUGIN_KAFKA_PLAIN_TEXT_METRICS_TOPIC, "plain");
//        map.put(PLUGIN_KAFKA_PLAIN_TEXT_METRICS_TOPIC_PARTITIONS, "10");
//        map.put(PLUGIN_KAFKA_PERFIX+"fetch.wait.max.ms", "10");
//        map.put(PLUGIN_KAFKA_PERFIX+"fetch.message.max.bytes", String.valueOf(1024*10));

        try {
            Config config = new Config(configFilePath);
            map = config.getMap();
        } catch (IOException e) {
            logger.error("loading config file[{}] runs into error.", configFilePath, e);
        }
    }

    @Override
    public Map<String, String> getMap() {
        return map;
    }

}
