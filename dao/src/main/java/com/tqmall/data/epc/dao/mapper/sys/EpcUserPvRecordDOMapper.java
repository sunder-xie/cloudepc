package com.tqmall.data.epc.dao.mapper.sys;

import com.tqmall.data.epc.bean.entity.sys.EpcUserPvRecordDO;

import java.util.List;

public interface EpcUserPvRecordDOMapper {

    int insertSelective(EpcUserPvRecordDO record);

    EpcUserPvRecordDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(EpcUserPvRecordDO record);

    List<Integer> selectUriIdsByMobile(String mobile);
}