package cn.kenshinn.tsdb.thrift.rpc;

import cn.kenshinn.tsdb.thrift.rpc.service.TSDBDelegate;
import com.stumbleupon.async.Deferred;
import net.opentsdb.core.TSDB;

import java.util.Map;

/**
 * @author stliu <stliu@apache.org>
 * @date 3/11/15
 */
public class TSDBWrapper implements TSDBDelegate {
    private final TSDB tsdb;

    public TSDBWrapper(TSDB tsdb) {
        this.tsdb = tsdb;
    }


    @Override
    public Deferred<Object> addPoint(String name, long timestamp, double value, Map<String, String> tags) {
        return tsdb.addPoint(name, timestamp, value, tags);
    }

    @Override
    public ConfigDelegate getConfig() {
        return new ConfigWrapper(tsdb.getConfig().getMap());
    }

    public static class ConfigWrapper implements ConfigDelegate{
        private final Map<String, String> map;

        public ConfigWrapper(Map<String, String> map) {
            this.map = map;
        }

        @Override
        public Map<String, String> getMap() {
            return map;
        }
    }
}
