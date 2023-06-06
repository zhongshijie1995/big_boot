package com.zhongshijie1995.big_boot.info.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zhongshijie1995.big_boot.info.mapper.VersionInfo;
import com.zhongshijie1995.big_boot.info.mapper.VersionInfoMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class VersionInfoQuery {

    @Resource
    private VersionInfoMapper versionInfoMapper;

    public String queryDesc(String version) {
        LambdaQueryWrapper<VersionInfo> versionInfoLambdaQueryWrapper = new LambdaQueryWrapper<>();
        versionInfoLambdaQueryWrapper.eq(VersionInfo::getVersion, version);
        return versionInfoMapper.selectOne(versionInfoLambdaQueryWrapper).getDesc();
    }
}
