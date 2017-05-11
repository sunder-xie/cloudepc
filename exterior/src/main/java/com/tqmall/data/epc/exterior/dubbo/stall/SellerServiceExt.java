package com.tqmall.data.epc.exterior.dubbo.stall;

import com.tqmall.tqmallstall.domain.result.seller.SellerDTO;

import java.util.List;

/**
 * Created by huangzhangting on 16/9/1.
 */
public interface SellerServiceExt {

    /**
     * 根据城市站 + 车系id（level 2 级别id），查询合适的供应商组织id
     * @param cityId
     * @param carSeriesId
     * @return
     */
    List<Integer> getSuitableOrgId(Integer cityId, Integer carSeriesId);

    /**
     * 根据组织id，查询供应商信息
     * @param orgId
     * @return
     */
    SellerDTO getSellerByOrgId(Integer orgId);

}
