package com.tqmall.data.epc.test.shipping;

import com.tqmall.core.common.entity.Result;
import com.tqmall.data.epc.client.bean.dto.shipping.ShippingConfigDTO;
import com.tqmall.data.epc.client.server.shipping.ShippingConfigService;
import com.tqmall.data.epc.common.utils.JsonUtil;
import com.tqmall.data.epc.test.DubboTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by huangzhangting on 16/8/30.
 */

public class ShippingTest extends DubboTest{
    @Autowired
    private ShippingConfigService shippingConfigService;

    @Test
    public void test(){
        Result<List<ShippingConfigDTO>> result = shippingConfigService.getAll();
        System.out.println(JsonUtil.objectToJson(result));
    }


}
