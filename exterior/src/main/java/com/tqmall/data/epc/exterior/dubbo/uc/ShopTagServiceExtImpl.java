package com.tqmall.data.epc.exterior.dubbo.uc;

import com.tqmall.core.common.entity.Result;
import com.tqmall.data.epc.common.utils.JsonUtil;
import com.tqmall.ucenter.object.result.shoptag.ShopTagDTO;
import com.tqmall.ucenter.service.shoptag.RpcShopTagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangzhangting on 16/9/2.
 */
@Service
@Slf4j
public class ShopTagServiceExtImpl implements ShopTagServiceExt{
    @Autowired
    private RpcShopTagService shopTagService;

    @Override
    public List<ShopTagDTO> getTagsByShopId(String appName, Integer shopId) {
        log.info("getTagsByShopId, appName:{}, shopId:{}", appName, shopId);

        Result<List<ShopTagDTO>> result = shopTagService.getTagsByShopId(appName, shopId);
        if(result.isSuccess() && !CollectionUtils.isEmpty(result.getData())){
            return result.getData();
        }

        log.info("getTagsByShopId failed, result:{}", JsonUtil.objectToJson(result));
        return new ArrayList<>();
    }
}
