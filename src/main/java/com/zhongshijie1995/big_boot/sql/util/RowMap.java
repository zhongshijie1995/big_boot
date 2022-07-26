package com.zhongshijie1995.big_boot.sql.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RowMap {

    public static final String KEYS = "keys";
    public static final String VALUES = "values";

    public static Map<String, List<Object>> getKeysAndValues(Map<String, Object> map) {
        Map<String, List<Object>> result = new HashMap<>();
        List<Object> keys = new ArrayList<>(), values = new ArrayList<>();
        map.forEach((k, v) -> {
            keys.add(k);
            values.add(v);
        });
        result.put(KEYS, keys);
        result.put(VALUES, values);
        return result;
    }
}
