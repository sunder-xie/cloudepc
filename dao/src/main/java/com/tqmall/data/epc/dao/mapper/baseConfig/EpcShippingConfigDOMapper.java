package com.tqmall.data.epc.dao.mapper.baseConfig;

import com.tqmall.data.epc.bean.entity.baseConfig.EpcShippingConfigDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EpcShippingConfigDOMapper {

    int deleteByPrimaryKey(Integer id);

    int insertSelective(EpcShippingConfigDO record);

    EpcShippingConfigDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(EpcShippingConfigDO record);

    /*========================*/

    //if shippingName == null,则 select 全部
    List<EpcShippingConfigDO> selectAll(@Param(value = "shippingName") String shippingName);
}