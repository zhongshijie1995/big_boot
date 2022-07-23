package com.zhongshijie1995.big_boot.sql;

import com.zhongshijie1995.big_boot.sql.mapper.TabMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
    private TabMapper tabMapper;

    @ApiOperation("查询数据表")
    @GetMapping("getTab")
    public List<HashMap<String, Object>> getTab(String tab, String condition) {
        return tabMapper.query(tab, condition);
    }
}
