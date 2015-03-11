package com.easemob.tsdb.thrift.rpc;


/**
 * @author stliu <stliu@apache.org>
 * @date 3/11/15
 */
public class TestServer {
    public static void main(String[] args) {
        MockTSDB tsdb = new MockTSDB();
        ThriftTSDBRpcPlugin rpcServer;
        rpcServer = new ThriftTSDBRpcPlugin();
        rpcServer.internalInitialize(tsdb);
    }
}
