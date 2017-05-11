package com.tqmall.data.epc.exterior.dubbo.grace;

import com.tqmall.core.common.entity.Result;
import com.tqmall.data.epc.common.bean.EpcError;
import com.tqmall.data.epc.common.utils.EPCUtil;
import com.tqmall.data.epc.common.utils.ResultUtil;
import com.tqmall.part.dubbo.lop.SupplyTaxService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by huangzhangting on 16/8/31.
 */
@Service
@Slf4j
public class SellerTaxExtImpl implements SellerTaxExt {
    @Autowired
    private SupplyTaxService supplyTaxService;


    @Override
    public Result<BigDecimal> getTaxRate(Integer orgId) {
        Result<BigDecimal> result = getOriginalTaxRate(orgId);
        if(result.isSuccess()) {
            return ResultUtil.successResult(EPCUtil.getRate(result.getData()));
        }
        return result;
    }

    @Override
    public Result<Map<Integer, BigDecimal>> getSomeTaxRate(List<Integer> orgIdList) {
        if(CollectionUtils.isEmpty(orgIdList)){
            return ResultUtil.errorResult(EpcError.ARG_ERROR);
        }
        Map<Integer, BigDecimal> map = new HashMap<>();
        for(Integer id : orgIdList){
            Result<BigDecimal> result = getTaxRate(id);
            if(!result.isSuccess()){
                return ResultUtil.errorResult(result);
            }
            map.put(id, result.getData());
        }
        return ResultUtil.successResult(map);
    }

    @Override
    public Result<BigDecimal> getOriginalTaxRate(Integer orgId) {
        if(orgId==null || orgId<1){
            return ResultUtil.errorResult(EpcError.ARG_ERROR);
        }

        BigDecimal result = supplyTaxService.getTaxRate(orgId.longValue());

        if (result == null || result.compareTo(BigDecimal.ZERO) < 0) {
            return ResultUtil.errorResult("", "税率查询失败");
        }

        return ResultUtil.successResult(result);
    }







    // TODO 临时使用
    @Override
    public Result<BigDecimal> getTaxRateTemp(Integer orgId) {
        return ResultUtil.successResult(EPCUtil.getRate(new BigDecimal(6)));
    }

    @Override
    public Result<BigDecimal> getOriginalTaxRateTemp(Integer orgId) {
        return ResultUtil.successResult(new BigDecimal(6));
    }

}
