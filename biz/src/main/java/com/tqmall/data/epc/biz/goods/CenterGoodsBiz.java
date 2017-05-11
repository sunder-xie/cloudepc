package com.tqmall.data.epc.biz.goods;

import com.tqmall.athena.domain.result.center.goods.CenterGoodsCarDetailDTO;
import com.tqmall.core.common.entity.Result;
import com.tqmall.data.epc.bean.bizBean.goods.CenterGoodsBO;
import com.tqmall.data.epc.bean.bizBean.goods.CenterGoodsDetailBO;

import java.util.List;
import java.util.Map;

/**
 * Created by huangzhangting on 16/2/17.
 */
public interface CenterGoodsBiz {

    /**
     * 根据车型和分类筛选配件
     * @param carId online车型id
     * @param cateId 商品中心分类id
     * @return list列表
     */
    List<CenterGoodsBO> getListByCarIdCatId(Integer carId,Integer cateId);

//    CenterGoodsBO getGoodsForOeSearch(String oeNumber);

    CenterGoodsBO getCenterGoodsById(Integer goodsId);

    /**
     * 根据goodsId查询适配车型（level 3）
     * @param goodsId
     * @return
     */
    Map<String, Object> getCarModelForGoods(Integer goodsId);

    /**
     * 根据goodsId查询适配车款（level 6）
     * @param goodsId
     * @return
     */
    Map<String, Object> getCarForGoods(Integer goodsId);

    /**
     * 根据 goodsId 和 carId 获取 配件详情的数据
     * @param goodsId
     * @param carId
     * @return
     */
    CenterGoodsDetailBO getGoodsDetailByGoodsIdCarId(Integer goodsId, Integer carId);

    /**
     * 配件搜索
     * @param keyword
     * @param oeNumber
     * @param modelId
     * @param cateId
     * @param pageIndex
     * @param pageSize
     * @return
     */
    Result<com.tqmall.search.dubbo.client.cloudepc.result.CenterGoodsDTO> goodsSearch(
            String keyword, String oeNumber, Integer modelId, Integer cateId, Integer pageIndex, Integer pageSize);

    /**
     * 根据车型、图号查询配件
     * @param picNum
     * @param carId
     * @return
     */
    Result<List<CenterGoodsCarDetailDTO>> getGcDetailByPicNumAndCar(String picNum, Integer carId);

}
