package cn.kenshinn.tsdb.thrift.rpc;

import cn.kenshinn.tsdb.thrift.rpc.service.TSDBDelegate;
import com.stumbleupon.async.Deferred;

import java.util.Map;

/**
 * @author stliu <stliu@apache.org>
 * @date 3/11/15
 */
public class MockTSDB implements TSDBDelegate {

    @Override
    public Deferred<Object> addPoint(String name, long timestamp, double value, Map<String, String> tags) {
        System.out.println(String.format("add point %s %s %s %s", name, timestamp, value, tags));
        return new Deferred<>();
    }

    @Override
    public ConfigDelegate getConfig() {
        return new MockConfig();
    }
}
