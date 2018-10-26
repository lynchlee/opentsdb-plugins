package cn.kenshinn.tsdb.thrift.rpc;

import cn.kenshinn.tsdb.thrift.models.TSData;
import cn.kenshinn.tsdb.thrift.models.ThriftTsdbRpcService;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author stliu <stliu@apache.org>
 * @date 3/11/15
 */

public class ThriftTSDBRpcPluginTest implements Constants {
    private static final Logger logger = LoggerFactory.getLogger(ThriftTSDBRpcPluginTest.class);
    MockTSDB tsdb = new MockTSDB();
    ThriftTSDBRpcPlugin rpcServer;
    ThriftTsdbRpcService.Client client;
    TTransport transport;

    @Before
    public void setUp() throws Exception {
        rpcServer = new ThriftTSDBRpcPlugin();
        rpcServer.internalInitialize(tsdb);
        long time = 10* 1000;
        logger.info("Waiting "+time+" ms for server startup");
        Thread.sleep(time);
        logger.info("now starting client to connect to server");

        transport = new TSocket("127.0.0.1", PLUGIN_THRIFT_PORT_DEFAULT);
        System.out.println("-------");
        transport = new TFramedTransport.Factory().getTransport(transport);
        System.out.println("@##@#@#");
        transport.open();
        System.out.println("22222222");
        TProtocol protocol = new TBinaryProtocol(transport);
        client = new ThriftTsdbRpcService.Client(protocol);
    }

    @Test
    public void testSendMetrics() throws TException {
        TSData tsData = new TSData();
        tsData.setName("m1");
        tsData.setTimestamp(new Date().getTime());
        tsData.setValue(1);
        Map<String, String> tags = new HashMap<>();
        tags.put("t1", "v1");

        tsData.setTags(tags);
        System.out.println("3333333");
        client.putTSData(tsData);
    }

    @After
    public void tearDown() throws Exception {
        transport.close();
        rpcServer.shutdown();
    }
}
