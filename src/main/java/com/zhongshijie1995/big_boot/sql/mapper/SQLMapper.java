package com.zhongshijie1995.big_boot.sql.mapper;

import java.util.HashMap;
import java.util.List;

public interface SQLMapper {
    // 新增数据行
    void post(String tableName, List<Object> keys, List<Object> values);

    // 查询数据库行
    List<HashMap<String, Object>> get(String tableName);
}
