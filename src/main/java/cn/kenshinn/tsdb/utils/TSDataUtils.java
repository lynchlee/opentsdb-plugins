package cn.kenshinn.tsdb.utils;

import cn.kenshinn.tsdb.thrift.models.TSData;
import net.opentsdb.core.Const;
import net.opentsdb.core.Tags;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Description for Class TSDataUtils
 *
 * @author Lynch Lee<Lynch.lee9527@gmail.com>
 * @version 2016-01-14.
 */
public class TSDataUtils {

    private static final Logger logger = LoggerFactory.getLogger(TSDataUtils.class);

    //name         tags                   timestamp  value
    //sys.cpu.user host=webserver01,cpu=0 1356998400 1
    public static TSData parseString2TSData(String s) {
        try {
            if (s == null || s.isEmpty()) {
                return null;
            }
            String[] fragements = s.split(" ");

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
            logger.error("Failed to parse '{}' to data point, just ignore this", s, e);
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


    /**
     * Validates the given metric and tags.
     *
     * @throws IllegalArgumentException if any of the arguments aren't valid.
     */
    public static boolean isValidMetricAndTags(final String metric, final Map<String, String> tags) {
        if (tags.size() <= 0) {
            logger.warn("Need at least one tag (metric=" + metric + ", tags=" + tags + ')');
            return false;
        } else if (tags.size() > Const.MAX_NUM_TAGS) {
            logger.warn("Too many tags: " + tags.size() + " maximum allowed: " + Const.MAX_NUM_TAGS + ", tags: " + tags);
            return false;
        }

        try {
            Tags.validateString("metric name", metric);
            for (final Map.Entry<String, String> tag : tags.entrySet()) {
                Tags.validateString("tag name", tag.getKey());
                Tags.validateString("tag value", tag.getValue());
            }

            return true;
        } catch (IllegalArgumentException iae) {
            System.out.println(iae.getMessage());
            logger.warn(iae.getMessage());
        }

        return false;
    }

}
