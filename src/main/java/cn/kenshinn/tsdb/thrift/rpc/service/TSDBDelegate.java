package cn.kenshinn.tsdb.thrift.rpc.service;

import com.stumbleupon.async.Deferred;

import java.util.Map;

/**
 * @author stliu <stliu@apache.org>
 * @date 3/11/15
 */
public interface TSDBDelegate {
    Deferred<Object> addPoint(String name, long timestamp, double value, Map<String, String> tags);

    ConfigDelegate getConfig();

    public static interface ConfigDelegate {
        Map<String, String> getMap();
    }
}
