package com.easemob.tsdb.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * @author stliu <stliu@apache.org>
 * @date 3/11/15
 */
public class ConfigUtils {
    private static final Logger logger = LoggerFactory.getLogger(ConfigUtils.class);

    public static int getIntValue(Map<String, String> map, String propertyName, int defaultValue) {
        try {
            if (map.containsKey(propertyName)) {
                String value = map.get(propertyName);
                return Integer.valueOf(value);
            } else {
                return defaultValue;
            }
        } catch (Exception e) {
            logger.warn("Failed to get integer property value of " + propertyName + " using default one instead " + defaultValue);
            return defaultValue;
        }
    }
}
