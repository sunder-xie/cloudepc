package com.tqmall.data.epc.biz.cart;

import com.tqmall.core.common.entity.Result;
import com.tqmall.data.epc.bean.bizBean.car.CenterCarBO;
import com.tqmall.data.epc.bean.entity.cart.EpcCartDO;
import com.tqmall.data.epc.bean.entity.cart.EpcCartVO;
import com.tqmall.data.epc.bean.param.cart.CheckGoodsParam;
import com.tqmall.data.epc.bean.param.cart.PurchaseParam;

import java.util.List;

/**
 * 购物车相关业务
 * Created by huangzhangting on 16/8/24.
 */
public interface CartBiz {
    /**
     * 添加商品
     * @param cartGoods
     */
    Result addGoods(EpcCartDO cartGoods);

    /**
     * 删除商品
     * @param goodsId
     * @return
     */
    Result deleteGoods(Integer goodsId);

    /**
     * 批量删除购物车中商品
     * @param goodsIdList
     * @return
     */
    Result deleteGoodsByIdList(List<Integer> goodsIdList);
    /**
     * 修改商品数量
     * @param goodsId
     * @param goodsNumber
     * @return
     */
    Result modifyGoodsNumber(Integer goodsId, Integer goodsNumber);

    /**
     * 查询购物车商品列表
     * @return
     */
    Result<List<EpcCartVO>> getCartGoodsList();

    /**
     * 通过epc_cart表主键id获取商品列表
     *
     * @param idStr
     * @return
     */
    Result<List<EpcCartVO>> getGoodsListByIds(String idStr);

    /**
     * 获取立即购买商品
     * @param purchaseParam
     * @return
     */
    Result<List<EpcCartVO>> getGoodsInfoList(PurchaseParam purchaseParam);

    /**
     * 查询购物车商品总数
     * @return
     */
    Result getCartGoodsAmount();

    /**
     * 根据goodsId查询商品适配车型
     * @param goodsId
     * @return
     */
    List<CenterCarBO> getCarForGoods(Integer goodsId);

    /**
     * 根据OE码, 获取商品适配车型
     * @param oeNum
     * @return
     */
    List<CenterCarBO> getCarForGoodsByOeNum(String oeNum);

    /**
     * 清除失效商品
     * @return
     */
    Result deleteUnAvailableGoods();

    /**
     * 购物车结算，校验商品
     * @param paramList
     * @return
     */
    Result checkGoodsForSettlement(List<CheckGoodsParam> paramList);

    /**
     * 立即购买，校验商品
     * @param param
     * @return
     */
    Result checkGoodsForBuyNow(CheckGoodsParam param);

}
