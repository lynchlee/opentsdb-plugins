package com.easemob.tsdb.thrift.rpc;

import com.easemob.tsdb.thrift.models.TSData;
import com.easemob.tsdb.thrift.models.ThriftTsdbRpcService;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author stliu <stliu@apache.org>
 * @date 3/11/15
 */
public class TestClient  implements Constants{
    public static void main(String[] args) throws Exception{
        ThriftTsdbRpcService.Client client;
        TTransport transport;
        transport = new TSocket("127.0.0.1", PLUGIN_THRIFT_PORT_DEFAULT);
        System.out.println("-------");
        transport = new TFramedTransport.Factory().getTransport(transport);
        System.out.println("@##@#@#");
        transport.open();
        System.out.println("22222222");
        TProtocol protocol = new TBinaryProtocol(transport);
        client = new ThriftTsdbRpcService.Client(protocol);
        for(int i=0;i<100;i++)
        pushData(client);
        transport.close();
    }

    private static void pushData(ThriftTsdbRpcService.Client client) throws org.apache.thrift.TException {
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
}
