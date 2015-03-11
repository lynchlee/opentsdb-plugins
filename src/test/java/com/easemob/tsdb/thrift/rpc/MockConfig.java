package com.easemob.tsdb.thrift.rpc;

import com.easemob.tsdb.thrift.rpc.service.TSDBDelegate;

import java.util.HashMap;
import java.util.Map;

/**
 * @author stliu <stliu@apache.org>
 * @date 3/11/15
 */
public class MockConfig implements TSDBDelegate.ConfigDelegate, Constants {
    private final Map<String, String> map = new HashMap<>();
    public MockConfig(){
        map.put(PLUGIN_THRIFT_HOST, "127.0.0.1");


    }
    @Override
    public Map<String, String> getMap() {
        return map;
    }
}
