package com.zhongshijie1995.big_boot.base.util.sql;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class EntityWithUuid {
    @TableId
    private String uuid;
}
