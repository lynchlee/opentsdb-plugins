package cn.kenshinn.tsdb.thrift.rpc.service;

import org.apache.thrift.TException;
import org.apache.thrift.async.AsyncMethodCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static cn.kenshinn.tsdb.utils.TSDataUtils.*;

/**
 * @author stliu <stliu@apache.org>
 * @date 3/11/15
 */
public class ThriftMetricsService implements ThriftTsdbRpcService.Iface, ThriftTsdbRpcService.AsyncIface {
    private static final Logger logger = LoggerFactory.getLogger(ThriftMetricsService.class);
    private final TSDBDelegate tsdb;

    public ThriftMetricsService(TSDBDelegate tsdb) {
        this.tsdb = tsdb;
    }

/*
    private static final Callback<Object, Object> LOGGING_CALLBACK = o -> {
        if (o instanceof Exception) {
            logger.error("failed to insert data point due to error ", (Exception) o);
        }else if(o !=null){
            logger.debug("tsdb add data point returned {}", o);
        }

        return null;
    };
*/

    @Override
    public void putTSData(TSData tsdata, AsyncMethodCallback resultHandler) throws TException {
        if (tsdata == null) {
            return;
        }

        try {
            tsdb.addPoint(tsdata.getName(), tsdata.getTimestamp(), tsdata.getValue(), tsdata.getTags());
            logger.info("insert TSData into TSDB. {}", tsdata);
        }catch (Exception e){
            logger.error("Failed to insert data to OpenTSDB", e);
        }
    }

    @Override
    public void putString(String metrics, AsyncMethodCallback resultHandler) throws TException {
        putTSData(parseString2TSData(metrics), resultHandler);
    }


    @Override
    public void putTSData(TSData tsdata) throws TException {
        putTSData(tsdata, null);
    }

    @Override
    public void putString(String metrics) throws TException {
        putTSData(parseString2TSData(metrics));
    }


}
