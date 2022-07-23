package com.zhongshijie1995.big_boot.sql.mapper;

import java.util.HashMap;
import java.util.List;

public interface TabMapper {
    List<HashMap<String, Object>> query(String tableName, String whereCondition);
}
