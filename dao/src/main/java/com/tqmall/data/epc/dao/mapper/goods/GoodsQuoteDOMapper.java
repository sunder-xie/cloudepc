package com.tqmall.data.epc.dao.mapper.goods;

import com.tqmall.data.epc.bean.entity.goods.GoodsQuoteDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GoodsQuoteDOMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(GoodsQuoteDO record);

    int insertSelective(GoodsQuoteDO record);

    GoodsQuoteDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(GoodsQuoteDO record);

    int updateByPrimaryKey(GoodsQuoteDO record);

    List<GoodsQuoteDO> selectByGoodsCar(@Param("goodsId")Integer goodsId, @Param("carId")Integer carId);
}