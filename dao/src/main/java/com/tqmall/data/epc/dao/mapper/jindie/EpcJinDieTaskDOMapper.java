package com.tqmall.data.epc.dao.mapper.jindie;

import com.tqmall.data.epc.bean.entity.jindie.EpcJinDieTaskDO;

import java.util.List;

public interface EpcJinDieTaskDOMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(EpcJinDieTaskDO record);

    EpcJinDieTaskDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(EpcJinDieTaskDO record);

    /*======================zxg=======================*/
    List<EpcJinDieTaskDO> selectByDO(EpcJinDieTaskDO record);
}