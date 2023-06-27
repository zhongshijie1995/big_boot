package com.zhongshijie1995.big_boot.base.util.protocol;

import com.alibaba.fastjson2.JSONObject;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class RespBody {
    private String respCode;
    private String respDesc;
    private JSONObject data;

    public void setStatus(String respCodeDesc) {
        String[] parts = respCodeDesc.split(RespConstants.SPLIT);
        this.respCode = parts[0];
        this.respDesc = parts[1];
    }
}
