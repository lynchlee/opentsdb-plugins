This project provides two [OpenTSDB](http://opentsdb.net) *rpc* plugins.

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

This kafka consumer reads at most two topics, the purpose is same as above, one topic for the plain text type data,
and the other is for the tsdata's binary data.

The plain text's topic name can be configured with property `em.kafka.text.topic` and it's partition number is configured
with property `em.kafka.text.topic.partition`.

The tsdata's topic name is configured with property `em.kafka.tsdata.topic` and `em.kafka.tsdata.topic.partiton`

Note: the topic name property has no default value, so if there is no such property provided, then this plugin will not fetch
any data from kafka but just quite.

The partiton property is used to caucalute the consumer thread count, by default it is the cpu cores.

There are also two properties that should be defined in the configuration file:

1. em.kafka.zookeeper.connect

    The zk host which will be used for the kafka consumer to connect to.

    If there is no such property, then the value of property `tsd.storage.hbase.zk_quorum` will be used.

2. em.kafka.client.id

    The client id used to identify kafka consumer group



### How to build

        ./gradlew clean build assembleMainDist

The artifact will be build and save to *build/distributions/*

### Install

Copy the build artifact and copy the jars to the OpenTSDB's lib dir, please note some dependencies of this project maybe already
exist in the OpenTSDB's distribution, so just skip such jars and only copy non-exist ones.

Then modify OpenTSDB's conf file and add properties required by this plugin,
all properties can be found in the class com.easemob.tsdb.thrift.rpc.Constants

To enable the rpc plugins provided by this project, please make sure such line exist in the Opentsdb's conf

    tsd.rpc.plugins=com.easemob.thrift.tsdb.rpc.ThriftTSDBRpcPlugin,com.easemob.tsdb.kafka.plugin.KafkaConsumerRPCPlugin