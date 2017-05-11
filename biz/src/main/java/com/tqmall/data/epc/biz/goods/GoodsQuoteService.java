package com.tqmall.data.epc.biz.goods;

import com.tqmall.core.common.entity.Result;
import com.tqmall.data.epc.bean.bizBean.goods.QuoteGoodsBO;

import java.util.List;

/**
 * Created by huangzhangting on 16/3/25.
 */
public interface GoodsQuoteService {
    /**
     *
     * 查询当前城市站报价信息
     *
     * @param carId 车款id：level 6 级别id
     * @param oeNum oe码
     * @param qualityId 品质枚举
     * @return
     */
    Result<List<QuoteGoodsBO>> queryGoodsQuote(Integer carId, String oeNum, Integer qualityId);

}
