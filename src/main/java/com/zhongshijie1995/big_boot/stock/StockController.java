package com.zhongshijie1995.big_boot.stock;

import com.zhongshijie1995.big_boot.base.util.cost.CostReport;
import com.zhongshijie1995.big_boot.stock.api.QtStockAPI;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

@Slf4j
@Api(tags = "股票")
@RestController
@RequestMapping("stock")
public class StockController {

    @Resource
    private QtStockAPI qtStockAPI;

    @CostReport
    @ApiOperation("实时查询")
    @GetMapping("realtime")
    public Map<String, String> realtime(String cod) throws Exception {
        return qtStockAPI.realtime(cod);
    }

}
