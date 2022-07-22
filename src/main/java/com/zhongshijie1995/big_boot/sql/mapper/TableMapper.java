package com.zhongshijie1995.big_boot.sql.mapper;

import java.util.HashMap;
import java.util.List;

public interface TableMapper {
    List<HashMap<String, Object>> queryTableDetails(String tableName, String whereCondition);
}
