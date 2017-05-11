package com.tqmall.data.epc.bean.bizBean.lop;

import lombok.Data;

import java.util.List;

/**
 * Created by huangzhangting on 16/8/12.
 */
@Data
public class WishListImResultBO {

    private WishListVO wishListVO;

    private List<OfferListGoodsBO> goodsBOList;

    //附加信息
    private List<OfferListGoodsBO> suppleGoodsList;

    //当前商家名称
    private String sellerName;

    //报价商家数量
    private Integer sellerNum;
    //当前商家对该订单的状态
    private String status;
}
