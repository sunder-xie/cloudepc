package com.tqmall.data.epc.test.avid;

import com.tqmall.core.common.entity.Result;
import com.tqmall.data.epc.client.bean.dto.avid.AvidCallDTO;
import com.tqmall.data.epc.client.bean.param.avid.CancelAvidCallParam;
import com.tqmall.data.epc.client.bean.param.avid.ModifyAvidCallGoodsParam;
import com.tqmall.data.epc.client.bean.param.avid.ModifyAvidCallParam;
import com.tqmall.data.epc.client.server.avid.AvidCallService;
import com.tqmall.data.epc.common.utils.JsonUtil;
import com.tqmall.data.epc.test.DubboTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangzhangting on 16/10/25.
 */
public class AvidCallTest extends DubboTest {
    @Autowired
    private AvidCallService avidCallService;

    @Test
    public void test(){
        Integer id = 3;
        Result<AvidCallDTO> result = avidCallService.getAvidCallById(id);
        //System.out.println(JsonUtil.objectToJson(result));

        CancelAvidCallParam param = new CancelAvidCallParam();
        param.setId(3);
        param.setCancelReason("测试看看");
        param.setOperator("java");
        Result result1 = avidCallService.cancelAvidCall(param);
        System.out.println(JsonUtil.objectToJson(result1));
    }

    @Test
    public void test_modify(){
        ModifyAvidCallParam param = new ModifyAvidCallParam();
        param.setId(4);
        param.setOperator("java");
        param.setCarVin("123456785");
        param.setOrderRemark("备注看看");

        List<ModifyAvidCallGoodsParam> list = new ArrayList<>();
        list.add(createGoodsParam("商品1rrg"));
        list.add(createGoodsParam("商品添加"));
        ModifyAvidCallGoodsParam goodsParam = createGoodsParam("商品1");
        goodsParam.setId(3);
        list.add(goodsParam);

        param.setGoodsParamList(list);

        Result result = avidCallService.modifyAvidCall(param);
        System.out.println(JsonUtil.objectToJson(result));
    }
    private ModifyAvidCallGoodsParam createGoodsParam(String goodsName){
        ModifyAvidCallGoodsParam goodsParam = new ModifyAvidCallGoodsParam();
        goodsParam.setGoodsName(goodsName);
        goodsParam.setGoodsNumber(5);
        goodsParam.setGoodsQualityId(5);
        goodsParam.setGoodsOe("oeertt555");

        return goodsParam;
    }


    @Test
    public void test_count(){
        Result<Long> result = avidCallService.getAllNewDataNum();
        System.out.println(JsonUtil.objectToJson(result));

        result = avidCallService.getFiveMinNewDataNum();
        System.out.println(JsonUtil.objectToJson(result));

    }
}
