package cn.kenshinn.tsdb.kafka.plugin;

import cn.kenshinn.tsdb.thrift.rpc.MockTSDB;


/**
 * Description for Class KafkaConsumerGroupsMultiTopicTest
 *
 * @author Lynch Lee<Lynch.lee9527@gmail.com>
 * @version 2016-01-14.
 */
public class KafkaConsumerGroupsMultiTopicTest {

    public static void main(String[] args) {
        KafkaConsumerGroupsMultiTopic kafkaConsumerGroups =
                new KafkaConsumerGroupsMultiTopic(new MockTSDB());
        kafkaConsumerGroups.start();
    }

}
