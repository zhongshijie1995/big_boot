package com.zhongshijie1995.big_boot.info;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zhongshijie1995.big_boot.base.util.cost.CostReport;
import com.zhongshijie1995.big_boot.info.entity.Info;
import com.zhongshijie1995.big_boot.info.mapper.VersionInfo;
import com.zhongshijie1995.big_boot.info.mapper.VersionInfoMapper;
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
    private Info info;

    @Resource
    private VersionInfoMapper versionInfoMapper;

    @CostReport
    @ApiOperation("版本号")
    @GetMapping("version")
    public String version() {
        return info.getVersion();
    }

    @CostReport
    @ApiOperation("构建时间")
    @GetMapping("time")
    public String time() {
        return info.getTime().toString();
    }

    @CostReport
    @ApiOperation("全部")
    @GetMapping("all")
    public String all() {
        info.setDes(desc());
        return JSON.toJSONString(info);
    }

    @CostReport
    @ApiOperation("版本描述")
    @GetMapping("desc")
    public String desc() {
        String version = info.getVersion();
        LambdaQueryWrapper<VersionInfo> versionInfoLambdaQueryWrapper = new LambdaQueryWrapper<>();
        versionInfoLambdaQueryWrapper.eq(VersionInfo::getVersion, version);
        return versionInfoMapper.selectOne(versionInfoLambdaQueryWrapper).getDesc();
    }

}
