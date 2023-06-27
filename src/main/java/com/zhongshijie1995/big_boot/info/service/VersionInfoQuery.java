package com.zhongshijie1995.big_boot.info.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zhongshijie1995.big_boot.info.dao.InfoVersionDescMapper;
import com.zhongshijie1995.big_boot.info.entity.InfoVersionDesc;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class VersionInfoQuery {

    @Resource
    private InfoVersionDescMapper infoVersionDescMapper;

    public String queryDesc(String version) {
        LambdaQueryWrapper<InfoVersionDesc> versionInfoLambdaQueryWrapper = new LambdaQueryWrapper<>();
        versionInfoLambdaQueryWrapper.eq(InfoVersionDesc::getVersion, version);
        return infoVersionDescMapper.selectOne(versionInfoLambdaQueryWrapper).getDesc();
    }
}
