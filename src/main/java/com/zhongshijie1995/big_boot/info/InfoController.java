package com.zhongshijie1995.big_boot.info;

import com.alibaba.fastjson2.JSON;
import com.zhongshijie1995.big_boot.base.util.cost.CostReport;
import com.zhongshijie1995.big_boot.info.entity.InfoVersionDescReal;
import com.zhongshijie1995.big_boot.info.service.VersionInfoQuery;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(tags = "应用信息")
@RestController
@RequestMapping("info")
public class InfoController {
    @Resource
    private InfoVersionDescReal infoVersionDescReal;

    @Resource
    private VersionInfoQuery versionInfoQuery;

    @CostReport
    @ApiOperation("版本号")
    @GetMapping("version")
    public String version() {
        return infoVersionDescReal.getVersion();
    }

    @CostReport
    @ApiOperation("构建时间")
    @GetMapping("time")
    public String time() {
        return infoVersionDescReal.getTime().toString();
    }

    @CostReport
    @ApiOperation("全部")
    @GetMapping("all")
    public String all() {
        infoVersionDescReal.setDes(desc());
        return JSON.toJSONString(infoVersionDescReal);
    }

    @CostReport
    @ApiOperation("版本描述")
    @GetMapping("desc")
    public String desc() {
        String version = infoVersionDescReal.getVersion();
        return versionInfoQuery.queryDesc(version);
    }

}
