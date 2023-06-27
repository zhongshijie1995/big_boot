package com.zhongshijie1995.big_boot.stock.calc;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zhongshijie1995.big_boot.base.util.cost.RespCost;
import com.zhongshijie1995.big_boot.base.util.protocol.RespBody;
import com.zhongshijie1995.big_boot.base.util.protocol.RespConstants;
import com.zhongshijie1995.big_boot.base.util.sql.EntityWithUuidService;
import com.zhongshijie1995.big_boot.stock.calc.dao.StockCalcAcctMapper;
import com.zhongshijie1995.big_boot.stock.calc.dao.StockCalcFundMapper;
import com.zhongshijie1995.big_boot.stock.calc.dao.StockCalcTradeMapper;
import com.zhongshijie1995.big_boot.stock.calc.entity.StockCalcAcct;
import com.zhongshijie1995.big_boot.stock.calc.entity.StockCalcFund;
import com.zhongshijie1995.big_boot.stock.calc.entity.StockCalcTrade;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@Api(tags = "股票")
@RestController
@RequestMapping("stock/calc")
public class StockCalcController {

    @Resource
    private RespBody respBody;

    @Resource
    private StockCalcAcctMapper stockCalcAcctMapper;

    @Resource
    private StockCalcFundMapper stockCalcFundMapper;

    @Resource
    private StockCalcTradeMapper stockCalcTradeMapper;

    @Resource
    private EntityWithUuidService entityWithUuidService;

    @RespCost
    @ApiOperation("账户")
    @PostMapping("acct")
    public RespBody acct(@RequestBody StockCalcAcct stockCalcAcct) {
        JSONObject data = JSONObject.from(entityWithUuidService.save(stockCalcAcctMapper, stockCalcAcct));
        respBody.setData(data);
        respBody.setStatus(RespConstants.RESP_SUCC);
        return respBody;
    }

    @RespCost
    @ApiOperation("资金")
    @PostMapping("fund")
    public RespBody fund(@RequestBody StockCalcFund stockCalcFund) {
        JSONObject data = JSONObject.from(entityWithUuidService.save(stockCalcFundMapper, stockCalcFund));
        respBody.setData(data);
        respBody.setStatus(RespConstants.RESP_SUCC);
        return respBody;
    }

    @RespCost
    @ApiOperation("交易")
    @PostMapping("trade")
    public RespBody trade(@RequestBody StockCalcTrade stockCalcTrade) {
        JSONObject data = JSONObject.from(entityWithUuidService.save(stockCalcTradeMapper, stockCalcTrade));
        respBody.setData(data);
        respBody.setStatus(RespConstants.RESP_SUCC);
        return respBody;
    }

    public RespBody calc() {
        // 查询待计算列表
        LambdaQueryWrapper<StockCalcTrade> stockCalcTradeLambdaQueryWrapper = new LambdaQueryWrapper<>();

        stockCalcTradeMapper.selectList(stockCalcTradeLambdaQueryWrapper);
        return respBody;
    }
}
