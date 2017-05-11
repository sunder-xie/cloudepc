package com.tqmall.data.epc.exterior.dubbo.uc;

import com.tqmall.core.common.entity.Result;
import com.tqmall.data.epc.common.utils.JsonUtil;
import com.tqmall.ucenter.object.result.shop.ShopDTO;
import com.tqmall.ucenter.object.result.shop.ShopInfoDTO;
import com.tqmall.ucenter.service.shop.RpcShopInfoService;
import com.tqmall.ucenter.service.shop.RpcShopService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by huangzhangting on 16/7/19.
 */
@Service
@Slf4j
public class ShopInfoServiceExtImpl implements ShopInfoServiceExt {
    @Autowired
    private RpcShopInfoService rpcShopInfoService;
    @Autowired
    private RpcShopService rpcShopService;


    @Override
    public ShopInfoDTO getShopInfoByShopId(String appName, Integer shopId) {
        log.info("get shop info, appName:{}, shopId:{}", appName, shopId);

        Result<ShopInfoDTO> result = rpcShopInfoService.getShopInfoByShopId(appName, shopId);
        if (result.isSuccess()){
            return result.getData();
        }

        log.info("get shop info, result:{}", JsonUtil.objectToJson(result));
        return null;
    }

    @Override
    public ShopInfoDTO getOrCreateShopInfoByMobile(String appName, String mobile) {
        log.info("get or create shop info by mobile, appName:{}, mobile:{}", appName, mobile);

        Result<ShopInfoDTO> result = rpcShopInfoService.getOrCreateShopInfoByMobile(appName, mobile);
        if(result.isSuccess()){
            return result.getData();
        }

        log.info("get or create shop info by mobile, result:{}", JsonUtil.objectToJson(result));
        return null;
    }

    @Override
    public ShopDTO getShopByShopId(String appName, Integer shopId) {
        log.info("get shop, appName:{}, shopId:{}", appName, shopId);

        Result<ShopDTO> result = rpcShopService.getShopById(appName, shopId);
        if(result.isSuccess()){
            return result.getData();
        }

        log.info("get shop, result:{}", JsonUtil.objectToJson(result));
        return null;
    }
}
