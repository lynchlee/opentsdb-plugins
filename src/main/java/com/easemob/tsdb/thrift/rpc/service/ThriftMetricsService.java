package com.easemob.tsdb.thrift.rpc.service;

import com.easemob.tsdb.thrift.models.TSData;
import com.easemob.tsdb.thrift.models.ThriftTsdbRpcService;
import com.stumbleupon.async.Callback;
import org.apache.thrift.TException;
import org.apache.thrift.async.AsyncMethodCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

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


    private static final Callback<Object, Object> LOGGING_CALLBACK = o -> {
        if (o instanceof Exception) {
            logger.error("failed to insert data point due to error ", (Exception) o);
        }else if(o !=null){
            logger.debug("tsdb add data point returned {}", o);
        }

        return null;
    };

    @Override
    public void putTSData(TSData tsdata, AsyncMethodCallback resultHandler) throws TException {
        if (tsdata == null) {
            return;
        }

        try {
            tsdb.addPoint(tsdata.getName(), tsdata.getTimestamp(), tsdata.getValue(), tsdata.getTags())
                    .join();
        }catch (Exception e){
            logger.error("Failed to insert data to OpenTSDB", e);
        }
//                .addCallback(LOGGING_CALLBACK);
        logger.info("insert TSData into TSDB. {}", tsdata);

    }

    @Override
    public void putString(String metrics, AsyncMethodCallback resultHandler) throws TException {
        putTSData(parse(metrics), resultHandler);
    }


    @Override
    public void putTSData(TSData tsdata) throws TException {
        putTSData(tsdata, null);
    }

    @Override
    public void putString(String metrics) throws TException {
        putTSData(parse(metrics));
    }

    //sys.cpu.user host=webserver01,cpu=0  1356998400  1
    private static TSData parse(String s) {
        try {
            if (s == null || s.isEmpty()) {
                return null;
            }
            String[] fragements = s.split(" ");
            System.out.println(Arrays.toString(fragements));
            if (fragements.length < 4) {
                return null;
            }
            String name = fragements[0].trim();
            if (name.isEmpty()) {
                return null;
            }
            String tagsString = fragements[1].trim();
            if (tagsString.isEmpty()) {
                return null;
            }
            long timestamp = Long.valueOf(fragements[2].trim());
            double value = Double.valueOf(fragements[3].trim());
            Map<String, String> tags = parseTags(tagsString);
            if (tags == null || tags.isEmpty()) {
                return null;
            }
            TSData tsdata = new TSData();
            tsdata.setName(name);
            tsdata.setTags(tags);
            tsdata.setTimestamp(timestamp);
            tsdata.setValue(value);
            return tsdata;
        } catch (Exception e) {
            logger.error("Failed to parse '{}' to data point, just ignore this", e);
            return null;
        }

    }

    private static Map<String, String> parseTags(String tagsString) {
        String[] tags = tagsString.split(",");
        Map<String, String> map = new HashMap<>(tags.length);
        for (String tag : tags) {
            String[] keyValue = tag.trim().split("=");
            if (keyValue.length != 2) {
                continue;
            }
            map.put(keyValue[0], keyValue[1]);

        }
        return map;

    }
}
