package com.zhongshijie1995.big_boot.info;

import com.zhongshijie1995.big_boot.util.cost.CostReport;
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
    private InfoProperties infoProperties;

    @CostReport
    @ApiOperation("版本号")
    @GetMapping("version")
    public String version() {
        return infoProperties.getVersion();
    }

    @CostReport
    @ApiOperation("构建时间")
    @GetMapping("time")
    public String time() {
        return infoProperties.getTime().toString();
    }

    @CostReport
    @ApiOperation("全部")
    @GetMapping("all")
    public String all() {
        return infoProperties.toString();
    }

}
