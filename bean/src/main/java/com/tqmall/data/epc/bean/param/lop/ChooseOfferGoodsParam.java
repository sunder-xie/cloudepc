package com.tqmall.data.epc.bean.param.lop;

import com.tqmall.data.epc.bean.param.BaseQueryParam;
import lombok.Data;

/**
 * Created by huangzhangting on 16/7/30.
 */
@Data
public class ChooseOfferGoodsParam extends BaseQueryParam{
    private Integer offerListId;

    private String offerListGoodsIds;

    private Integer wishListId;
}
