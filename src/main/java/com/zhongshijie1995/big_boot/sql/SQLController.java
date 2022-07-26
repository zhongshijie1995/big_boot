package com.zhongshijie1995.big_boot.sql;

import com.zhongshijie1995.big_boot.sql.bean.RowMap;
import com.zhongshijie1995.big_boot.sql.mapper.SQLMapper;
import com.zhongshijie1995.big_boot.util.cost.CostTime;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = "结构化数据库")
@RestController
@RequestMapping("sql")
@Slf4j
public class SQLController {
    @Resource
    private SQLMapper SQLMapper;

    @ApiOperation("插入数据表")
    @PostMapping("post")
    @CostTime
    public HashMap<String, Object> post(String tableName, @RequestBody HashMap<String, Object> row) {
        Map<String, List<Object>> rowMap = RowMap.getKeysAndValues(row);
        SQLMapper.post(tableName, rowMap.get(RowMap.KEYS), rowMap.get(RowMap.VALUES));
        return row;
    }

    @ApiOperation("查询数据表")
    @GetMapping("get")
    @CostTime
    public List<HashMap<String, Object>> get(String tableName) {
        return SQLMapper.get(tableName);
    }
}
