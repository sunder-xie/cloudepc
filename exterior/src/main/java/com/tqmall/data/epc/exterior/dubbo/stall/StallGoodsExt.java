package com.tqmall.data.epc.exterior.dubbo.stall;

import com.tqmall.core.common.entity.PagingResult;
import com.tqmall.tqmallstall.domain.result.goods.GoodsMiniDTO;

import java.util.List;

/**
 * Created by huangzhangting on 16/7/4.
 */
public interface StallGoodsExt {

    List<GoodsMiniDTO> getGoodsMiniByIds(List<Integer> goodsIdList);

    PagingResult<GoodsMiniDTO> queryGoodsByCondition(String oeNum, Integer qualityId, List<Integer> orgIdList,
                                                     Integer pageIndex, Integer pageSize);

    List<GoodsMiniDTO> queryAllGoods(String oeNum, Integer qualityId, List<Integer> orgIdList);

}
