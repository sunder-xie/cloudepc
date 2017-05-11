package com.tqmall.data.epc.dao.mapper.order;

import com.tqmall.data.epc.bean.entity.order.EpcOrderCancelLogDO;

public interface EpcOrderCancelLogDOMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(EpcOrderCancelLogDO record);

    int insertSelective(EpcOrderCancelLogDO record);

    EpcOrderCancelLogDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(EpcOrderCancelLogDO record);

    int updateByPrimaryKey(EpcOrderCancelLogDO record);

    //====================== START LYJ ======================//
    //根据订单编号获取取消原因数据
    EpcOrderCancelLogDO selectByOrderSn(String orderSn);
    //====================== END LYJ ======================//
}