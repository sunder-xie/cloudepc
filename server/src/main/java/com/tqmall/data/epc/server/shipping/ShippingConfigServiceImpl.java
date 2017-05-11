package com.tqmall.data.epc.server.shipping;

import com.tqmall.core.common.entity.Result;
import com.tqmall.data.epc.bean.entity.baseConfig.EpcShippingConfigDO;
import com.tqmall.data.epc.biz.base.config.ShippingConfigBiz;
import com.tqmall.data.epc.client.bean.dto.shipping.ShippingConfigDTO;
import com.tqmall.data.epc.client.server.shipping.ShippingConfigService;
import com.tqmall.data.epc.common.bean.EpcError;
import com.tqmall.data.epc.common.utils.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by huangzhangting on 16/8/30.
 */
@Slf4j
public class ShippingConfigServiceImpl implements ShippingConfigService {
    @Autowired
    private ShippingConfigBiz shippingConfigBiz;

    @Override
    public Result<List<ShippingConfigDTO>> getAll() {
        try{
            List<EpcShippingConfigDO> list = shippingConfigBiz.getAll();
            if(list.isEmpty()){
                return ResultUtil.errorResult(EpcError.NO_DATA_ERROR);
            }
            return ResultUtil.successResult4List(list, ShippingConfigDTO.class);
        }catch (Exception e){
            log.error("get all shipping config error", e);
            return ResultUtil.handleException(e);
        }
    }
}
