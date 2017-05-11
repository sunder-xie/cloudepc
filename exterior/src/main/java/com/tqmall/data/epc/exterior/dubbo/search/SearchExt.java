package com.tqmall.data.epc.exterior.dubbo.search;

import com.tqmall.data.epc.bean.bizBean.lop.WishSnSearchResultBO;
import com.tqmall.search.dubbo.client.cloudepc.param.CloudEpcQueryRequest;
import com.tqmall.search.dubbo.client.cloudepc.result.CenterGoodsDTO;
import com.tqmall.core.common.entity.Result;

/**
 * Created by huangzhangting on 16/6/28.
 */
public interface SearchExt {

    /**
     * 配件搜索
     * @param queryRequest
     * @param pageIndex
     * @param pageSize
     * @return
     */
    Result<CenterGoodsDTO> goodsSearch(CloudEpcQueryRequest queryRequest, Integer pageIndex, Integer pageSize);

    /**
     * 需求单编号搜索
     * @param userId
     * @param status
     * @param keyword
     * @return
     */
    WishSnSearchResultBO wishListSnSearch(Integer userId, String status, String keyword, Integer pageIndex, Integer pageSize);

    /**
     * 报价单编号搜索
     * @param userId
     * @param status
     * @param keyword
     * @return
     */
    WishSnSearchResultBO offerListSnSearch(Integer userId, String status, String payStatus, String keyword,
                                           Integer pageIndex, Integer pageSize);

}
