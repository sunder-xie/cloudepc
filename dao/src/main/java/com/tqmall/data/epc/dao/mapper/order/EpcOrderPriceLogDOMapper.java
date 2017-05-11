package com.tqmall.data.epc.dao.mapper.order;

import com.tqmall.data.epc.bean.entity.order.EpcOrderPriceLogDO;

public interface EpcOrderPriceLogDOMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(EpcOrderPriceLogDO record);

    int insertSelective(EpcOrderPriceLogDO record);

    EpcOrderPriceLogDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(EpcOrderPriceLogDO record);

    int updateByPrimaryKey(EpcOrderPriceLogDO record);
}