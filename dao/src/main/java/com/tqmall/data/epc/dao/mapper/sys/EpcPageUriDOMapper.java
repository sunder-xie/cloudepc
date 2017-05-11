package com.tqmall.data.epc.dao.mapper.sys;

import com.tqmall.data.epc.bean.entity.sys.EpcPageUriDO;

import java.util.List;

public interface EpcPageUriDOMapper {

    int insertSelective(EpcPageUriDO record);

    EpcPageUriDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(EpcPageUriDO record);

    List<EpcPageUriDO> selectNeedGuideUris();
}