package com.tqmall.data.epc.dao.mapper.order;

import com.tqmall.data.epc.bean.entity.order.EpcOrderStatusLogDO;

public interface EpcOrderStatusLogDOMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(EpcOrderStatusLogDO record);

    EpcOrderStatusLogDO selectByPrimaryKey(Integer id);

}