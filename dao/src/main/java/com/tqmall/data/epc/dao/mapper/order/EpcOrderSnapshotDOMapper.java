package com.tqmall.data.epc.dao.mapper.order;

import com.tqmall.data.epc.bean.entity.order.EpcOrderSnapshotDO;

public interface EpcOrderSnapshotDOMapper {

    int insertSelective(EpcOrderSnapshotDO record);

    EpcOrderSnapshotDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(EpcOrderSnapshotDO record);

}