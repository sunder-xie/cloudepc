package com.tqmall.data.epc.exterior.dubbo.goods;

import com.tqmall.athena.domain.result.center.goods.CenterGoodsCarDTO;
import com.tqmall.athena.domain.result.center.goods.CenterGoodsCarDetailDTO;
import com.tqmall.athena.domain.result.center.goods.CenterGoodsCarSubjoinDTO;
import com.tqmall.athena.domain.result.center.goods.CenterGoodsDTO;

import java.util.List;

/**
 * Created by huangzhangting on 16/2/17.
 * 14:40
 * 商品中心商品
 */
public interface CenterGoodsExt {
    /*=========goods===============*/

    CenterGoodsDTO getGoodsByPrimaryId(Integer goodsId);

    CenterGoodsDTO getGoodsByOeNumber(String oeNumber);

    List<CenterGoodsDTO> getGoodsByCatId(Integer catId);

    List<CenterGoodsDTO> getGoodsByPicNumAndCar(String picNum, Integer carId);


    /*=========goods_car===============*/

    /**
     * 根据 goods 和 car 查询数据
     * @param goodsId 可为null
     * @param carId 可为null
     * @return list
     */
    List<CenterGoodsCarDTO> getGoodsCarByGoodsCar(Integer goodsId, Integer carId);

    CenterGoodsCarDetailDTO getGoodsCarDetailByGoodsCar(Integer goodsId, Integer carId);

    CenterGoodsCarSubjoinDTO getSubjoinByGoodsModel(Integer goodsId, Integer modelId);

    List<CenterGoodsCarDetailDTO> getGcDetailByPicNumAndCar(String picNum, Integer carId);

}
