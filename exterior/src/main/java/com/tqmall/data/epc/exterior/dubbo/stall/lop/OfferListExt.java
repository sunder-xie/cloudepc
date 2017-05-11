package com.tqmall.data.epc.exterior.dubbo.stall.lop;

import com.tqmall.core.common.entity.PagingResult;
import com.tqmall.core.common.entity.Result;
import com.tqmall.data.epc.bean.bizBean.lop.OfferListBO;
import com.tqmall.data.epc.bean.bizBean.lop.WishOrderBO;
import com.tqmall.data.epc.bean.param.lop.ChooseOfferGoodsParam;
import com.tqmall.data.epc.bean.param.lop.WishOrderQueryParam;
import com.tqmall.tqmallstall.domain.result.openplatform.OfferListDetailDTO;

/**
 * Created by huangzhangting on 16/7/30.
 */
public interface OfferListExt {
    /**
     * 选择报价商品
     * @param param
     * @return
     */
    Result chooseOfferGoods(ChooseOfferGoodsParam param);

    /**
     * 查询报价单详情
     * @param offerListId
     * @return
     */
    OfferListDetailDTO getOfferListDetail(Integer offerListId);

    /**
     * 根据需求单id，供应商id，查询报价单详情
     * @param sellerId
     * @param wishListId
     * @return
     */
    OfferListBO getDetailByWishAndSeller(Integer sellerId, Integer wishListId);

    /**
     * 分页，查询订单
     * @param param
     * @return
     */
    PagingResult<WishOrderBO> paged(WishOrderQueryParam param);

    /**
     * 查询订单详情
     * @param uid
     * @param orderId
     * @param orderSn 目前接口不支持
     * @return
     */
    WishOrderBO getOrderDetail(Integer uid, Integer orderId, String orderSn);

    /**
     * 确认收货
     * @param userId
     * @param offerListSn
     * @return
     */
    Result confirmReceive(Integer userId, String offerListSn);

}
