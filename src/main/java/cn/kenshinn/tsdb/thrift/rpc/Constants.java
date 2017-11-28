package cn.kenshinn.tsdb.thrift.rpc;

/**
 * @author stliu <stliu@apache.org>
 * @date 3/11/15
 */
public interface Constants {
    String PLUGIN_PERFIX = "em.";
    String PLUGIN_KAFKA_PERFIX = PLUGIN_PERFIX + "kafka.";
    String PLUGIN_THRIFT_PERFIX = PLUGIN_PERFIX + "thrift.";

    String PLUGIN_THRIFT_HOST = PLUGIN_THRIFT_PERFIX + "host";
    String PLUGIN_THRIFT_HOST_DEFAULT = "0.0.0.0";

    String PLUGIN_THRIFT_PORT = PLUGIN_THRIFT_PERFIX + "port";
    int PLUGIN_THRIFT_PORT_DEFAULT = 9999;


    String PLUGIN_THRIFT_WORKERS = PLUGIN_THRIFT_PERFIX + "workers";
    int PLUGIN_THRIFT_WORKERS_DEFAULT = 20;

    String PLUGIN_THRIFT_DISRUPTOR_ACCEPTORS = PLUGIN_THRIFT_PERFIX + "disruptor.acceptors";
    int PLUGIN_THRIFT_DISRUPTOR_ACCEPTORS_DEFAULT = 40;


    String PLUGIN_THRIFT_DISRUPTOR_SELECTORS = PLUGIN_THRIFT_PERFIX + "disruptor.selectors";
    int PLUGIN_THRIFT_DISRUPTOR_SELECTORS_DEFAULT = 80;

    //指定要消费的topic, 可以指定多个, 用逗号分割, 例如 topic1,topic2,topic3
    String PLUGIN_KAFKA_METRICS_TOPICS = PLUGIN_KAFKA_PERFIX + "topics";

    String PLUGIN_KAFKA_TOPICN_NAME = PLUGIN_KAFKA_PERFIX + "topicN.name";
    String PLUGIN_KAFKA_TOPICN_DATATYPE = PLUGIN_KAFKA_PERFIX + "topicN.datatype";
    String PLUGIN_KAFKA_TOPICN_SKIPWRITE = PLUGIN_KAFKA_PERFIX + "topicN.skipWrite";
    String PLUGIN_KAFKA_TOPICN_ZOOKEEPER_CONNECT = PLUGIN_KAFKA_PERFIX + "topicN.zookeeper.connect";
    String PLUGIN_KAFKA_TOPICN_GROUP_ID = PLUGIN_KAFKA_PERFIX + "topicN.group.id";

    // TODO:  will be removed
    //这个topic用来接收纯文本的metrics数据
    String PLUGIN_KAFKA_PLAIN_TEXT_METRICS_TOPIC = PLUGIN_KAFKA_PERFIX + "text.topic";
    //这个topic的partitions的个数
    String PLUGIN_KAFKA_PLAIN_TEXT_METRICS_TOPIC_PARTITIONS = PLUGIN_KAFKA_PLAIN_TEXT_METRICS_TOPIC + ".partitions";

    //这个topic用来接收TsData类型的thrift序列化之后的数据
    String PLUGIN_KAFKA_TSDATA_METRICS_TOPIC = PLUGIN_KAFKA_PERFIX + "tsdata.topic";
    //这个topic的partitions的个数
    String PLUGIN_KAFKA_TSDATA_METRICS_TOPIC_PARTITIONS = PLUGIN_KAFKA_TSDATA_METRICS_TOPIC + ".partitions";
    String PLUGIN_KAFKA_ZOOKEEPER_HOST = PLUGIN_KAFKA_PERFIX + "zookeeper.connect";
    String OPENTSDB_ZOOKEEPER_HOST="tsd.storage.hbase.zk_quorum";
    String PLUGIN_KAFKA_GROUP_ID = PLUGIN_KAFKA_PERFIX + "group.id";
}
