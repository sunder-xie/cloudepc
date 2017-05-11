package com.tqmall.data.epc.biz.lop;

import com.tqmall.core.common.entity.PagingResult;
import com.tqmall.core.common.entity.Result;
import com.tqmall.data.epc.bean.bizBean.lop.OfferListBO;
import com.tqmall.data.epc.bean.bizBean.lop.OfferListDetailBO;
import com.tqmall.data.epc.bean.bizBean.lop.WishOrderBO;
import com.tqmall.data.epc.bean.param.lop.ChooseOfferGoodsParam;
import com.tqmall.data.epc.bean.param.lop.WishOrderQueryParam;

/**
 * Created by huangzhangting on 16/7/30.
 */
public interface OfferListBiz {
    /**
     * 选择报价商品
     * @param param
     * @return
     */
    Result chooseOfferGoods(ChooseOfferGoodsParam param);

    /**
     * 查询需求单详情
     * @param offerListId
     * @return
     */
    Result<OfferListDetailBO> getOfferListDetail(Integer offerListId);

    /**
     * 根据需求单id，供应商id，查询报价单详情
     * @param sellerId
     * @param wishListId
     * @return
     */
    Result<OfferListBO> getDetailByWishAndSeller(Integer sellerId, Integer wishListId);

    /**
     * 分页，查询订单
     * @param param
     * @return
     */
    PagingResult<WishOrderBO> paged(WishOrderQueryParam param);

    /**
     * 根据主键id，或者订单编号，查询订单；以id为主
     * @param orderId
     * @param orderSn
     * @return
     */
    Result<WishOrderBO> getOrderDetail(Integer orderId, String orderSn);

    /**
     * 确认收货
     * @param offerListSn
     * @return
     */
    Result confirmReceive(String offerListSn);
}
