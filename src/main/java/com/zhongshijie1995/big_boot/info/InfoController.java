package com.zhongshijie1995.big_boot.info;

import com.alibaba.fastjson2.JSONObject;
import com.zhongshijie1995.big_boot.base.util.cost.RespCost;
import com.zhongshijie1995.big_boot.base.util.protocol.RespBody;
import com.zhongshijie1995.big_boot.base.util.protocol.RespConstants;
import com.zhongshijie1995.big_boot.info.entity.InfoBuild;
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
    private InfoBuild infoBuild;

    @Resource
    private VersionInfoQuery versionInfoQuery;

    @Resource
    private RespBody respBody;

    private static final String TIME="time";
    private static final String VERSION = "version";
    private static final String DESC = "desc";
    private static final String ALL = "all";

    @RespCost
    @ApiOperation("版本号")
    @GetMapping(VERSION)
    public RespBody version() {
        // 提取版本号
        JSONObject data = new JSONObject();
        data.put(VERSION, infoBuild.getVersion());
        // 设置返回体
        respBody.setStatus(RespConstants.RESP_SUCC);
        respBody.setData(data);
        return respBody;
    }

    @RespCost
    @ApiOperation("构建时间")
    @GetMapping(TIME)
    public RespBody time() {
        // 提取构建时间
        JSONObject data = new JSONObject();
        data.put(TIME, infoBuild.getTime().toString());
        // 设置返回体
        respBody.setStatus(RespConstants.RESP_SUCC);
        respBody.setData(data);
        return respBody;
    }

    @RespCost
    @ApiOperation("版本描述")
    @GetMapping(DESC)
    public RespBody desc(String version) {
        // 提取版本描述
        JSONObject data = new JSONObject();
        // 如果没有给定版本号，则认为查询当前版本
        if (version == null) {
            version = infoBuild.getVersion();
        }
        data.put(DESC, versionInfoQuery.queryDesc(version));
        // 设置返回体
        respBody.setStatus(RespConstants.RESP_SUCC);
        respBody.setData(data);
        return respBody;
    }

    @RespCost
    @ApiOperation("全部")
    @GetMapping(ALL)
    public RespBody all() {
        // 提取全部
        JSONObject data = new JSONObject();
        data.put(VERSION, infoBuild.getVersion());
        data.put(TIME, infoBuild.getTime().toString());
        data.put(DESC, versionInfoQuery.queryDesc(data.getString(VERSION)));
        // 设置返回体
        respBody.setStatus(RespConstants.RESP_SUCC);
        respBody.setData(data);
        return respBody;
    }

}
