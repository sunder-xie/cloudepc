package com.tqmall.data.epc.dao.mapper.order;

import com.tqmall.data.epc.bean.entity.order.EpcOrderGoodsSnapshotDO;

public interface EpcOrderGoodsSnapshotDOMapper {

    int insertSelective(EpcOrderGoodsSnapshotDO record);

    EpcOrderGoodsSnapshotDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(EpcOrderGoodsSnapshotDO record);

}