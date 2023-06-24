package com.zhongshijie1995.big_boot.info.dao;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class InfoVersionDesc {
    @TableId
    private Integer ids;
    private String version;
    private String desc;
}
