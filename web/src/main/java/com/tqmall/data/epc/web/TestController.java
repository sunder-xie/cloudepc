package com.tqmall.data.epc.web;

import com.tqmall.core.common.entity.Result;
import com.tqmall.data.epc.bean.param.order.pay.UnionPayFormParam;
import com.tqmall.data.epc.biz.base.PayBiz;
import com.tqmall.data.epc.biz.order.OrderBiz;
import com.tqmall.data.epc.biz.shiro.ShiroBiz;
import com.tqmall.data.epc.client.server.order.OrderService;
import com.tqmall.data.epc.common.utils.ResultUtil;
import com.tqmall.data.epc.exterior.dubbo.grace.SellerTaxExt;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by huangzhangting on 16/1/27.
 */
@Controller
@RequestMapping("epc/test")
@Slf4j
public class TestController {

    @Value(value = "${web.version}")
    private String webVersion;

    @Autowired
    private ShiroBiz shiroBiz;
    @Autowired
    private OrderBiz orderBiz;

    @Autowired
    private OrderService orderService;

    @Autowired
    private SellerTaxExt sellerTaxExt;

    @Autowired
    private PayBiz payBiz;


    @RequestMapping("test")
    @ResponseBody
    public Result test(Integer id) {
        id.toString();
        log.info("epc test:{}", "234");

        return ResultUtil.successResult(id);
    }

    @RequestMapping("testCart")
    @ResponseBody
    public Result testCart() {
//        UserBO user = shiroBiz.getCurrentUser();
//        if(user==null){
//            return ResultUtil.errorResult("", "请先登录");
//        }
//        CartParam param = new CartParam();
//        param.setCityId(383);
//        param.setUid(user.getUid());
//        param.setSessionId(shiroBiz.getSessionId());
//
//        return ResultUtil.successResult(cartServiceExt.getCartGoods(param));

        return ResultUtil.successResult(shiroBiz.getSession());
    }

    @RequestMapping("testOrderDetail")
    @ResponseBody
    public Result testOrderDetail(String orderSn, Integer shopId) {
        return ResultUtil.successResult(orderBiz.getOrderDetailByShop(orderSn, shopId));
    }

    @RequestMapping("testOrderService1")
    @ResponseBody
    public Result testOrderService1(String orderSn) {
        return ResultUtil.successResult(orderService.getGoodsByOrderSn(orderSn));
    }

    @RequestMapping("testOrderService2")
    @ResponseBody
    public Result testOrderService2(String orderSn, Integer sellerId) {
        return orderService.getDetailByOrderSn(orderSn, sellerId);
    }

    @RequestMapping("sellerTaxExt")
    @ResponseBody
    public Result sellerTaxExt(Integer orgId) {
        return ResultUtil.successResult(sellerTaxExt.getTaxRate(orgId));
    }


    @RequestMapping("testAliPayParam")
    @ResponseBody
    public Result<String> testAliPayParam(String orderSn){
        return payBiz.getAliPayParam4SelectOrder(orderSn);
    }

    @RequestMapping("testUnionPayParam")
    @ResponseBody
    public Result<String> testUnionPayParam(String orderSn){
        UnionPayFormParam formParam = new UnionPayFormParam();
        formParam.setSn(orderSn);
        formParam.setNoAgree("2016041884542625");
        formParam.setCardNo("622588******5488");
        formParam.setIdNo("330327******1222");
        formParam.setAcctName("杨林周");

        return payBiz.getUnionPayParam4SelectOrder(formParam);
    }


    @RequestMapping(value="selectWaitPayFirst")
    public ModelAndView selectWaitPayFirst(){
        ModelAndView modelAndView = new ModelAndView(webVersion+"/cloudepc/part/pay/selectWaitPay");
        return modelAndView;
    }


}
