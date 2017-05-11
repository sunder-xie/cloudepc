package com.tqmall.data.epc.dao.mapper.cart;

import com.tqmall.data.epc.bean.entity.cart.EpcCartDO;

import java.util.List;

public interface EpcCartDOMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(EpcCartDO record);

    EpcCartDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(EpcCartDO record);


    EpcCartDO selectCartGoods(EpcCartDO record);

    List<EpcCartDO> selectCartGoodsList(EpcCartDO record);

    Integer countCartGoodsAmount(EpcCartDO record);

    List<Integer> selectGoodsIds(EpcCartDO record);
}