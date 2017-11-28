package cn.kenshinn.tsdb.kafka.plugin;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author stliu <stliu@apache.org>
 * @date 3/11/15
 */
public class KafkaConsuemrTest {
    public static void main(String[] args) {
        new KafkaConsuemrTest().build();
    }

    public void build(){
        Map<String, Integer> topicCountMap = new HashMap<>();
        topicCountMap.put("tsdb", 10);
        final ConsumerConnector consumer = Consumer.createJavaConsumerConnector(getKafkaConsumerConfig());

        final Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap = consumer.createMessageStreams(topicCountMap);
        List<KafkaStream<byte[], byte[]>> list =    consumerMap.get("tsdb");

        ExecutorService executorService = Executors.newFixedThreadPool(list.size());
        for(KafkaStream<byte[], byte[]> stream : list){
           executorService.submit(() -> {
               ConsumerIterator<byte[], byte[]> it = stream.iterator();
               while (it.hasNext())
                   System.out.println(new String(it.next().message()));
           });
        }
    }

    ConsumerConfig getKafkaConsumerConfig() {
        Properties props = new Properties();
        props.put("zookeeper.connect", "127.0.0.1:2181");
        props.put("group.id", this.getClass().getName()+System.currentTimeMillis());
        return new ConsumerConfig(props);
    }

}
