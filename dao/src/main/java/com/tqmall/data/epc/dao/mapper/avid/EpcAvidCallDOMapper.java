package com.tqmall.data.epc.dao.mapper.avid;

import com.tqmall.data.epc.bean.entity.avid.EpcAvidCallDO;

import java.util.Date;

public interface EpcAvidCallDOMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(EpcAvidCallDO record);

    EpcAvidCallDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(EpcAvidCallDO record);

    long getNewDataCount(Date time);
}