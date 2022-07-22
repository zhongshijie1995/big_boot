package com.zhongshijie1995.big_boot.sql;

import com.zhongshijie1995.big_boot.sql.mapper.TableMapper;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@Api(tags = "结构化数据库")
@RestController
@RequestMapping("sql")
public class TableController {

    @Resource
    private TableMapper tableMapper;

    @GetMapping("getTableDetails")
    public List<HashMap<String, Object>> getTableDetails() {
        return tableMapper.queryTableDetails("ops_tips", "1=1");
    }
}
