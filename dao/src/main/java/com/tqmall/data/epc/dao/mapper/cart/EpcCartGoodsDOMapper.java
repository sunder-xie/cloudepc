package com.tqmall.data.epc.dao.mapper.cart;

import com.tqmall.data.epc.bean.entity.cart.EpcCartGoodsDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EpcCartGoodsDOMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(EpcCartGoodsDO record);

    EpcCartGoodsDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(EpcCartGoodsDO record);


    EpcCartGoodsDO selectByCartGoods(@Param("cartId")Integer cartId, @Param("goodsId")Integer goodsId);

    List<EpcCartGoodsDO> selectListByCart(Integer cartId);

    int countCartGoodsAmount(Integer cartId);
}