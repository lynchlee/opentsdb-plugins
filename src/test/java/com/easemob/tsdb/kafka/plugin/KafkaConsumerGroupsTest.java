package com.easemob.tsdb.kafka.plugin;

import com.easemob.tsdb.thrift.rpc.MockTSDB;

/**
 * @author stliu <stliu@apache.org>
 * @date 3/11/15
 */
public class KafkaConsumerGroupsTest {
    public static void main(String[] args) {
        KafkaConsumerGroups kafkaConsumerGroups = new KafkaConsumerGroups(new MockTSDB());
        kafkaConsumerGroups.start();
    }
}
