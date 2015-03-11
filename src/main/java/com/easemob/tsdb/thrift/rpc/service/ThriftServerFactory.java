package com.easemob.tsdb.thrift.rpc.service;

import com.easemob.tsdb.thrift.models.ThriftTsdbRpcService;
import com.easemob.tsdb.thrift.rpc.Constants;
import com.easemob.tsdb.utils.ConfigUtils;
import com.thinkaurelius.thrift.Message;
import com.thinkaurelius.thrift.TDisruptorServer;
import com.thinkaurelius.thrift.util.TBinaryProtocol;
import org.apache.thrift.TProcessorFactory;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TTransportException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.Map;

/**
 * @author stliu <stliu@apache.org>
 * @date 3/11/15
 */
public class ThriftServerFactory implements Constants {
    private static final Logger logger = LoggerFactory.getLogger(ThriftServerFactory.class);
    private final String thriftHost;
    private final int thriftPort;
    private final int workers;
    private final int acceptors;
    private final int selectors;
    private final TProcessorFactory processorFactory;
    private final InetSocketAddress address;
    private final TNonblockingServerSocket socket;


    public ThriftServerFactory(TSDBDelegate tsdb) throws TTransportException {
        Map<String, String> map = tsdb.getConfig().getMap();
        this.thriftHost = map.getOrDefault(PLUGIN_THRIFT_HOST, PLUGIN_THRIFT_HOST_DEFAULT);
        this.thriftPort = ConfigUtils.getIntValue(map, PLUGIN_THRIFT_PORT, PLUGIN_THRIFT_PORT_DEFAULT);
        this.address = new InetSocketAddress(thriftHost, thriftPort);
        this.socket = new TNonblockingServerSocket(address);
        logger.info("Thrift RPC Server is running on {}:{}", thriftHost, thriftPort);
        this.workers = ConfigUtils.getIntValue(map, PLUGIN_THRIFT_WORKERS, PLUGIN_THRIFT_WORKERS_DEFAULT);
        this.acceptors = ConfigUtils.getIntValue(map, PLUGIN_THRIFT_DISRUPTOR_ACCEPTORS, PLUGIN_THRIFT_DISRUPTOR_ACCEPTORS_DEFAULT);
        this.selectors = ConfigUtils.getIntValue(map, PLUGIN_THRIFT_DISRUPTOR_SELECTORS, PLUGIN_THRIFT_DISRUPTOR_SELECTORS_DEFAULT);
        this.processorFactory = new TProcessorFactory(new ThriftTsdbRpcService.Processor<>(new ThriftMetricsService(tsdb)));

    }

    public CustomTDisruptorServer disruptorServerProvider() throws TTransportException {
        final TBinaryProtocol.Factory protocol = new TBinaryProtocol.Factory();

        TDisruptorServer.Args args = new TDisruptorServer.Args(socket)
                .inputTransportFactory(new TFramedTransport.Factory())
                .outputTransportFactory(new TFramedTransport.Factory())
                .inputProtocolFactory(protocol)
                .outputProtocolFactory(protocol)
                .processorFactory(processorFactory)
                .useHeapBasedAllocation(true)
                .numAcceptors(acceptors)
                .numSelectors(selectors)
                .numWorkersPerSelector(workers)
                .alwaysReallocateBuffers(true);
        return new CustomTDisruptorServer(args);
    }


    public static class CustomTDisruptorServer extends TDisruptorServer {
        public CustomTDisruptorServer(Args args) {
            super(args);
        }

        @Override
        protected void beforeInvoke(Message message) {
        }

    }

}
