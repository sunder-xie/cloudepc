package com.tqmall.data.epc.test.order;

import com.tqmall.core.common.entity.PagingResult;
import com.tqmall.core.common.entity.Result;
import com.tqmall.data.epc.bean.bizBean.order.OrderListShowBO;
import com.tqmall.data.epc.bean.param.order.CreateOrderGoodsParam;
import com.tqmall.data.epc.bean.param.order.CreateOrderParam;
import com.tqmall.data.epc.bean.param.order.OrderListPageParam;
import com.tqmall.data.epc.biz.base.PayBiz;
import com.tqmall.data.epc.biz.order.CreateOrderBiz;
import com.tqmall.data.epc.biz.order.OrderBiz;
import com.tqmall.data.epc.common.utils.JsonUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by huangzhangting on 16/7/30.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:config/application-context.xml"})
public class OrderTest {
    @Autowired
    private OrderBiz orderBiz;
    @Autowired
    private CreateOrderBiz createOrderBiz;
    @Autowired
    private PayBiz payBiz;


    @Test
    public void testCreate() {
        List<CreateOrderGoodsParam> goodsParamList = new ArrayList<>();
        CreateOrderGoodsParam goodsParam = new CreateOrderGoodsParam();
        goodsParam.setGoodsId(1);
        goodsParam.setGoodsSn("123123");
        goodsParam.setGoodsName("java测试商品");
        goodsParam.setGoodsQuality("正厂原厂");
        goodsParam.setPartName("测试配件");
        goodsParam.setOeNumber("12345");
        goodsParam.setGoodsNumber(1);
        goodsParam.setGoodsPrice(new BigDecimal(0.01));

        goodsParamList.add(goodsParam);

        goodsParam = new CreateOrderGoodsParam();
        goodsParam.setGoodsId(2);
        goodsParam.setGoodsSn("2233445");
        goodsParam.setGoodsName("java测试商品");
        goodsParam.setGoodsQuality("正厂原厂");
        goodsParam.setPartName("测试配件");
        goodsParam.setOeNumber("test2");
        goodsParam.setGoodsNumber(1);
        goodsParam.setGoodsPrice(new BigDecimal(0.01));

        goodsParamList.add(goodsParam);

        //订单
        CreateOrderParam orderParam = new CreateOrderParam();
        orderParam.setShopId(675763);
        orderParam.setCompanyName("java测试门店");
        orderParam.setConsignee("谁呢");
        orderParam.setMobile("15158036445");

        orderParam.setProvince(1);
        orderParam.setCityId(2);
        orderParam.setDistrict(3);
        orderParam.setStreet(4);
        orderParam.setAddress("海创园1123912039");

        orderParam.setCityId(383);
        orderParam.setAccountId(11);

        orderParam.setSellerId(2);
        orderParam.setSellerTelephone("213123");
        orderParam.setSellerCompanyName("java测试供应商");
        orderParam.setWarehouseId(123);

        orderParam.setShippingId(1);
        orderParam.setShippingName("三轮");
        orderParam.setPayId(4);
        orderParam.setPayName("支付宝");
        orderParam.setInvType(1);
        orderParam.setInvTypeName("普通发票");

        orderParam.setOrderNote("java测试订单");
//        orderParam.setTaxRate(new BigDecimal(6));
//        orderParam.setShippingFee(new BigDecimal(10));

        orderParam.setGoodsParamList(goodsParamList);

        createOrderBiz.createOrder(orderParam);
    }

    @Test
    public void testGetOrderPages() {
        System.out.println("==========");
        OrderListPageParam param = new OrderListPageParam();
        param.setSearchStatus("0");
//        param.setLimit(1);
        param.setSearchP(1);
        PagingResult<OrderListShowBO> result = orderBiz.orderPaged(param);
        System.out.println(JsonUtil.objectToJson(result));
        System.out.println("==========");
    }

    @Test
    public void createSomeTestOrder() {
        for (int i = 0; i < 30; i++) {
            testCreate();
        }
    }

    @Test
    public void testPayCallback(){
        String json = "{\"oid_partner\":[\"201503031000226507\"],\"sign_type\":[\"MD5\"],\"sign\":[\"24dfa0d4a5ccc5f76c226b08973f1971\"],\"dt_order\":[\"20160908091449\"],\"no_order\":[\"Q16090883720011-17-168\"],\"oid_paybill\":[\"2016090824522645\"],\"money_order\":[\"0.01\"],\"settle_date\":[\"20160908\"],\"info_order\":[\"变速器后盖\"],\"pay_type\":[\"D\"],\"bank_code\":[\"03080000\"],\"result_pay\":[\"SUCCESS\"]}";

        Map<String, List<String>> map = JsonUtil.jsonToObject(json, Map.class);

        System.out.println(map);

        Map<String, String[]> requestMap = new HashMap<>();

        for(Map.Entry<String, List<String>> entry : map.entrySet()){
            List<String> value = entry.getValue();
            requestMap.put(entry.getKey(), value.toArray(new String[value.size()]));
        }

        System.out.println(requestMap);

        Result<String> result = payBiz.verifyUnionReturn4SelectOrder(requestMap);

        System.out.println(result);
    }
}
