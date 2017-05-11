package com.tqmall.data.epc.exterior.dubbo.uc;

import com.tqmall.core.common.entity.Result;
import com.tqmall.data.epc.bean.bizBean.shop.AddressBO;
import com.tqmall.data.epc.common.utils.BdUtil;
import com.tqmall.data.epc.common.utils.JsonUtil;
import com.tqmall.ucenter.object.result.address.AddressDTO;
import com.tqmall.ucenter.service.address.RpcAddressService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangzhangting on 16/8/8.
 */
@Slf4j
@Service
public class AddressServiceExtImpl implements AddressServiceExt {
    @Autowired
    private RpcAddressService rpcAddressService;

    @Override
    public List<AddressBO> getAddressByTypeAndUserId(String appName, Integer userId, Integer userType) {
        log.info("get user address, appName:{}, userId:{}, userType:{}", appName, userId, userType);

        Result<List<AddressDTO>> result = rpcAddressService.getAddressByTypeAndUserId(appName, userId, userType);
        if(result.isSuccess() && !CollectionUtils.isEmpty(result.getData())){
            return BdUtil.do2bo4List(result.getData(), AddressBO.class);
        }

        log.info("get user address, result:{}", JsonUtil.objectToJson(result));
        return new ArrayList<>();
    }
}
