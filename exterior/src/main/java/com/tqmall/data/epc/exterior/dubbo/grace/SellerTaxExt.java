package com.tqmall.data.epc.exterior.dubbo.grace;

import com.tqmall.core.common.entity.Result;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Created by huangzhangting on 16/8/31.
 */
public interface SellerTaxExt {

    /**
     * 根据组织id查询税率（税率会预先处理过 /100）
     * @param orgId
     * @return
     */
    Result<BigDecimal> getTaxRate(Integer orgId);

    /**
     * 查询多个组织的税率（税率会预先处理过 /100）
     * @param orgIdList
     * @return
     */
    Result<Map<Integer, BigDecimal>> getSomeTaxRate(List<Integer> orgIdList);

    /**
     * 根据组织id查询税率，不做任何处理
     * @param orgId
     * @return
     */
    Result<BigDecimal> getOriginalTaxRate(Integer orgId);






    //TODO 临时使用
    Result<BigDecimal> getTaxRateTemp(Integer orgId);

    Result<BigDecimal> getOriginalTaxRateTemp(Integer orgId);
}
