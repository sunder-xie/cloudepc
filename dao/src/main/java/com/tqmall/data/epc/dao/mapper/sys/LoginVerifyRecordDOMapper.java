package com.tqmall.data.epc.dao.mapper.sys;

import com.tqmall.data.epc.bean.entity.sys.LoginVerifyRecordDO;

import java.util.List;

public interface LoginVerifyRecordDOMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(LoginVerifyRecordDO record);

    int insertSelective(LoginVerifyRecordDO record);

    LoginVerifyRecordDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(LoginVerifyRecordDO record);

    int updateByPrimaryKey(LoginVerifyRecordDO record);

    List<LoginVerifyRecordDO> getNewVerifyByMobile(String mobile);
}