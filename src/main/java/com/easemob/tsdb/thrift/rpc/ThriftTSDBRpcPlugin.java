package com.easemob.tsdb.thrift.rpc;

import com.easemob.tsdb.thrift.rpc.service.TSDBDelegate;
import com.easemob.tsdb.thrift.rpc.service.ThriftServerFactory;
import com.stumbleupon.async.Deferred;
import net.opentsdb.core.TSDB;
import net.opentsdb.stats.StatsCollector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 这个类是实现了OpenTSDB的 {@link net.opentsdb.tsd.RpcPlugin}的接口
 * 需要在OpenTSDB的配置文件中配置
 * <code>
 * tsd.rpc.plugins=com.easemob.thrift.tsdb.rpc.ThriftTSDBRpcPlugin
 * </code>
 * <p>
 * 需要注意的是, 这个plugin只有在OpenTSDB启动的时候才会加载,
 * 并且当配置多个plugin的时候, 类名用逗号分开, 具体的OpenTSDB配置详见
 * http://opentsdb.net/docs/build/html/user_guide/plugins.html#rpc
 *
 * @author stliu <stliu@apache.org>
 * @date 3/11/15
 */
@SuppressWarnings("UnusedDeclaration")
public class ThriftTSDBRpcPlugin extends AbstractTSDBRpcPlugin {
    private static final Logger logger = LoggerFactory.getLogger(ThriftTSDBRpcPlugin.class);
    private static volatile ThriftServerFactory.CustomTDisruptorServer server;

    @Override
    public void initialize(final TSDB tsdb) {

        internalInitialize(new TSDBWrapper(tsdb));

    }

    //for testing purpose only
    public void internalInitialize(final TSDBDelegate tsdbDelegate) {
        new Thread() {
            @Override
            public void run() {
                if (server != null && server.isServing()) {
                    logger.warn("thrift server is already started, this probably due to an internal error");
                    return;
                }


                try {
                    ThriftServerFactory factory = new ThriftServerFactory(tsdbDelegate);
                    server = factory.disruptorServerProvider();
                    server.serve();
                    logger.info("Thrift RPC Server started");
                } catch (Exception e) {
                    logger.error("Failed to start ThriftRPCServer due to Exception", e);
                    throw new IllegalArgumentException(e);
                }
            }
        }.start();
    }

    @Override
    public Deferred<Object> shutdown() {
        final Deferred<Object> deferred = new Deferred<>();
        new Thread() {
            @Override
            public void run() {
                try {
                    if (server != null && server.isServing()) {
                        logger.info(this.getClass().getName() + " is about to shutdown");
                        server.stop();
                        deferred.callback(null);
                    }
                } catch (Exception e) {
                    logger.error("Failed to stop thrift server due to Exception", e);
                    deferred.callback(e);
                }
            }
        }.start();
        return deferred;
    }

    @Override
    public void collectStats(StatsCollector statsCollector) {
        //TODO
    }
}
