package com.zhongshijie1995.big_boot.info.mapper;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class VersionInfo {
    @TableId
    private Integer ids;
    private String version;
    private String desc;
}
