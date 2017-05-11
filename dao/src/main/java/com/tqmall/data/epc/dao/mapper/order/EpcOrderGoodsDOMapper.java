package com.tqmall.data.epc.dao.mapper.order;

import com.tqmall.data.epc.bean.entity.order.EpcOrderGoodsDO;

import java.util.List;

public interface EpcOrderGoodsDOMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(EpcOrderGoodsDO record);

    EpcOrderGoodsDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(EpcOrderGoodsDO record);

    /*=========BY ZXG==============*/
    //根据 对象自身元素 全量匹配检索 list
    List<EpcOrderGoodsDO> selectByDO(EpcOrderGoodsDO searchDO);

}