package cn.kenshinn.tsdb.thrift.rpc;

import net.opentsdb.tsd.RpcPlugin;

/**
 * @author stliu <stliu@apache.org>
 * @date 3/11/15
 */
public abstract class AbstractTSDBRpcPlugin extends RpcPlugin {
    @Override
    public String version() {
        return Version.VERSION;
    }
}
