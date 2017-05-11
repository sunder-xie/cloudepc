package com.tqmall.data.epc.dao.mapper.avid;

import com.tqmall.data.epc.bean.entity.avid.EpcAvidCallGoodsDO;

import java.util.List;

public interface EpcAvidCallGoodsDOMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(EpcAvidCallGoodsDO record);

    EpcAvidCallGoodsDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(EpcAvidCallGoodsDO record);

    List<EpcAvidCallGoodsDO> getByAvidCallId(Integer avidCallId);

    int batchInsert(List<EpcAvidCallGoodsDO> list);

    int deleteByAvidCallId(Integer avidCallId);
}