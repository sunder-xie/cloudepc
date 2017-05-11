package com.tqmall.data.epc.exterior.dubbo.stall;

import com.tqmall.core.common.entity.Result;
import com.tqmall.data.epc.common.utils.JsonUtil;
import com.tqmall.tqmallstall.domain.result.seller.SellerDTO;
import com.tqmall.tqmallstall.service.openplatform.RpcSellerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangzhangting on 16/9/1.
 */
@Service
@Slf4j
public class SellerServiceExtImpl implements SellerServiceExt {
    @Autowired
    private RpcSellerService rpcSellerService;

    @Override
    public List<Integer> getSuitableOrgId(Integer cityId, Integer carSeriesId) {

        log.info("get org id by condition, cityId:{}, carSeriesId:{}", cityId, carSeriesId);

        Result<List<Integer>> result = rpcSellerService.getOrgIdListByCondition(cityId, carSeriesId);
        if(result!=null && result.isSuccess() && !CollectionUtils.isEmpty(result.getData())){
            return result.getData();
        }

        log.info("get org id by condition, failed, result:{}", JsonUtil.objectToJson(result));
        return new ArrayList<>();
    }

    @Override
    public SellerDTO getSellerByOrgId(Integer orgId) {
        log.info("get seller by org id, orgId:{}", orgId);

        Result<SellerDTO> result = rpcSellerService.getSellerByOrgId(orgId);
        if(result!=null && result.isSuccess()){
            return result.getData();
        }

        log.info("get seller by org id, failed, result:{}", JsonUtil.objectToJson(result));
        return null;
    }
}
