package com.tqmall.data.epc.biz.base;

import com.tqmall.data.epc.bean.entity.sys.LoginVerifyRecordDO;
import com.tqmall.data.epc.common.bean.ConstantBean;
import com.tqmall.data.epc.common.redis.RedisClientTemplate;
import com.tqmall.data.epc.common.redis.RedisKeyBean;
import com.tqmall.data.epc.dao.mapper.sys.LoginVerifyRecordDOMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Created by huangzhangting on 16/7/13.
 */
@Slf4j
@Service
public class VerifyBizImpl implements VerifyBiz{
    @Autowired
    private RedisClientTemplate redisClient;
    @Autowired
    private LoginVerifyRecordDOMapper loginVerifyRecordDOMapper;


    @Override
    public void addVerifyRecord(String mobile, String code) {
        //入参，调用方已验证，所以不再判断了
        LoginVerifyRecordDO recordDO = new LoginVerifyRecordDO();
        recordDO.setVerifyMobile(mobile);
        recordDO.setVerifyCode(code);
        recordDO.setVerifyStatus(ConstantBean.VERIFY_STATUS_INIT);
        recordDO.setGmtCreate(new Date());
        loginVerifyRecordDOMapper.insertSelective(recordDO);
    }

    //校验验证码
    private boolean check(String mobile, String code, LoginVerifyRecordDO record){
        if(code.equals(record.getVerifyCode())){
            record.setVerifyTime(new Date());
            record.setGmtModified(record.getVerifyTime());
            record.setVerifyStatus(ConstantBean.VERIFY_STATUS_SUCCESS);
            loginVerifyRecordDOMapper.updateByPrimaryKeySelective(record);

            String key = String.format(RedisKeyBean.VERIFY_CODE_KEY, mobile);
            redisClient.delKey(key);
            return true;
        }
        return false;
    }
    //判断验证码是否超时
    private boolean codeIsTimeOut(String mobile){
        String key = String.format(RedisKeyBean.VERIFY_CODE_KEY, mobile);
        String vc = redisClient.get(key);
        if(vc==null){
            return true;
        }
        return false;
    }


    @Override
    public boolean verifying(String mobile, String code) {
        if(codeIsTimeOut(mobile)){
            return false;
        }

        //入参，调用方已验证，所以不再判断了
        List<LoginVerifyRecordDO> recordList = loginVerifyRecordDOMapper.getNewVerifyByMobile(mobile);
        if(recordList.isEmpty()){
            return false;
        }

        int size = recordList.size();
        if(size==1){
            return check(mobile, code, recordList.get(0));
        }

        //根据主键id降序
        Collections.sort(recordList, new Comparator<LoginVerifyRecordDO>() {
            @Override
            public int compare(LoginVerifyRecordDO o1, LoginVerifyRecordDO o2) {
                return o2.getId().compareTo(o1.getId());
            }
        });

        boolean flag = check(mobile, code, recordList.get(0));
        for(int i=1; i<size; i++){
            LoginVerifyRecordDO record = recordList.get(i);
            record.setVerifyTime(new Date());
            record.setGmtModified(record.getVerifyTime());
            record.setVerifyStatus(ConstantBean.VERIFY_STATUS_FAILED);
            loginVerifyRecordDOMapper.updateByPrimaryKeySelective(record);
        }
        return flag;
    }

}
