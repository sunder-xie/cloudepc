package com.tqmall.data.epc.client.server.shipping;

import com.tqmall.core.common.entity.Result;
import com.tqmall.data.epc.client.bean.dto.shipping.ShippingConfigDTO;

import java.util.List;

/**
 * Created by huangzhangting on 16/8/30.
 */
public interface ShippingConfigService {
    /**
     * 获取所有发货方式
     * @return
     */
    Result<List<ShippingConfigDTO>> getAll();
}
