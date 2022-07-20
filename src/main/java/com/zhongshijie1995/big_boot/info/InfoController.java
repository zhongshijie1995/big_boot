package com.zhongshijie1995.big_boot.info;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("info")
public class InfoController {
    @Resource
    private InfoProperties infoProperties;

    @RequestMapping("version")
    public String version() {
        return infoProperties.getVersion();
    }

    @RequestMapping("time")
    public String time() {
        return infoProperties.getTime().toString();
    }

    @RequestMapping("all")
    public String all() {
        return infoProperties.toString();
    }

}
