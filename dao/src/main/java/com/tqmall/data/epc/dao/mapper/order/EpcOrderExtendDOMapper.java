package com.tqmall.data.epc.dao.mapper.order;

import com.tqmall.data.epc.bean.entity.order.EpcOrderExtendDO;

public interface EpcOrderExtendDOMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(EpcOrderExtendDO record);

    EpcOrderExtendDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(EpcOrderExtendDO record);

    //======== START LYJ =========//
    EpcOrderExtendDO selectByOrderSn(String orderSn);
    //======== END LYJ =========//
}