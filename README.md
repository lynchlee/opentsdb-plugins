[![Build Status](https://travis-ci.org/lynchlee/opentsdb-plugins.svg?branch=master)](https://travis-ci.org/lynchlee/opentsdb-plugins)

This project provides two [OpenTSDB(v2.1.3)](http://opentsdb.net) *rpc* plugins.

### The Thrift Plugin

This is just a simple Thrift server that will be start when OpenTSDB starts.

By default, it binds to _0.0.0.0_, which is configable var property `em.thrift.host` and listen port is `9999`,
 which also can be changed using property `em.thrift.port`

The thrift IDL used here can be found at _src/main/thrift/metrics.thrift_ which will
 provided very detailed view of how to use this thrift service.

As defined in the IDL, this server accepts two types of metrics data, simple text or *Tsdata* which is a predefined
thrift struct.

The plain text type data is just like the OpenTSDB's telent service, so one can write a thrift client and put same data
that used in the telnet service, it uses same metrics syntax.


### The Kafka Plugin

While, this is somehow misusing the OpenTSDB's RPC mechanism, since it is not a *RPC* plugin at all.

What this plugin does is simple start a kafka consumer and fetch data from kafka and then write it to OpenTSDB.

This kafka consumer reads one more topics, you can shot data only type of plain text or tsdata's binary into every topic.

Note: the topic name property has no default value, so if there is no such property provided, then this plugin will not fetch
any data from kafka but just quite.

We can get you topics via this configured property `em.kafka.topics`, you must split them by `,`, they are just index,
real topic name will be provode by the property named `em.kafka.topicN.name`
e.g: `em.kafka.topics=topic1,topic2,...,topicN`

For each topic there are some properties that should be defined in the configuration file:
(e.g: topic1)

1. `em.kafka.topic1.name`

   topic names of kafka

2. `em.kafka.topic1.datatype`

   the data type of your data shot into this topic, only two value for you : `plaintext` for plain text value and `tsdata`
   for tsdata's binary.
   Metrics schemas:
     - plaintext (just only one space between fragements):
       * (name        tags                   timestamp  value)
       * sys.cpu.user host=webserver01,cpu=0 1356998400 1
     - tsdata:
       * TSData(name:sys.cpu.user, value:6.0, timestamp:1356998400, tags:{host=webserver01, cpu=0})

3. `em.kafka.topic1.zookeeper.connect`

    The zk host which will be used for the kafka consumer to connect to.

4. `em.kafka.topic1.group.id`

    The client id used to identify kafka consumer group.

### How to build

        ./gradlew clean build assembleMainDist

The artifact will be build and save to *build/distributions/*

**NOTE: this requires Thrift 0.9.3**

### Install

Copy the build artifact and copy the jars to the OpenTSDB's lib dir, please note some dependencies of this project maybe already
exist in the OpenTSDB's distribution, so just skip such jars and only copy non-exist ones.

Then modify OpenTSDB's conf file and add properties required by this plugin,
all properties can be found in the class cn.kenshinn.tsdb.thrift.rpc.Constants

To enable the rpc plugins provided by this project, please make sure such line exist in the Opentsdb's conf

    tsd.rpc.plugins=cn.kenshinn.thrift.tsdb.rpc.ThriftTSDBRpcPlugin,cn.kenshinn.tsdb.kafka.plugin.KafkaConsumerRPCPlugin
