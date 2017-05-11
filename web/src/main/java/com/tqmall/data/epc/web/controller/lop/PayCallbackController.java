package com.tqmall.data.epc.web.controller.lop;

import com.tqmall.core.common.entity.Result;
import com.tqmall.data.epc.bean.bizBean.order.OrderBO;
import com.tqmall.data.epc.biz.base.PayBiz;
import com.tqmall.data.epc.biz.order.OrderBiz;
import com.tqmall.data.epc.common.bean.enums.OrderStatusEnums;
import com.tqmall.data.epc.web.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * 第三方支付回调
 * Created by huangzhangting on 16/7/31.
 */
@Slf4j
@Controller
@RequestMapping("payCallback")
public class PayCallbackController extends BaseController{
    @Autowired
    private PayBiz payBiz;
    @Autowired
    private OrderBiz orderBiz;


    //获取订单编号
    private String getOrderSn(String[] tradeNos){
        String orderSn= "";
        for (String str : tradeNos) {
            orderSn += str;
        }
        String[] split = orderSn.split("-");
        if (split.length>1){
            orderSn = split[0];
        }
        return orderSn;
    }

    private String aliPay4WishUrl(Map<String, String[]> requestParams){
        Result<String> result = payBiz.verifyAliReturn4WishOrder(requestParams);
        if(result.isSuccess() && !"fail".equals(result.getData())){
            return "redirect:/wish/order/success?orderSn=";
        }
        return null;
    }

    //支付宝同步回调
    @RequestMapping(value = "aliPay4Wish")
    public String aliPay4Wish() {
        Map<String, String[]> requestParams = request.getParameterMap();
        String orderSn = getOrderSn(requestParams.get("out_trade_no"));
        String url = aliPay4WishUrl(requestParams);
        if(url != null){
            return url + orderSn;
        }

        String[] tradeStatus = requestParams.get("trade_status");
        if(tradeStatus!=null && tradeStatus.length>0){
            //支付宝支付成功，再次验证订单状态是否修改
            if("TRADE_SUCCESS".equals(tradeStatus[0]) || "TRADE_FINISHED".equals(tradeStatus[0])){
                url = aliPay4WishUrl(requestParams);
                if(url != null){
                    return url + orderSn;
                }

                //等待页面
                return "redirect:/wish/order/waiting?orderSn="+orderSn;
            }
        }

        //失败页面
        return "redirect:/wish/order/failed?orderSn="+orderSn;
    }


    private String unionPay4WishUrl(Map<String, String[]> requestParams){
        Result<String> result = payBiz.verifyUnionReturn4WishOrder(requestParams);
        if(result.isSuccess() && !"fail".equals(result.getData())){
            return "redirect:/wish/order/success?orderSn=";
        }
        return null;
    }

    //连连支付同步回调
    @RequestMapping(value = "unionPay4Wish")
    public String unionPay4Wish() {
        Map<String, String[]> requestParams = request.getParameterMap();
        String orderSn = getOrderSn(requestParams.get("no_order"));
        String url = unionPay4WishUrl(requestParams);
        if(url != null){
            return url + orderSn;
        }

        String[] tradeStatus = requestParams.get("result_pay");
        if(tradeStatus!=null && tradeStatus.length>0){
            //连连支付成功，再次验证订单状态是否修改
            if("SUCCESS".equals(tradeStatus[0])){
                url = unionPay4WishUrl(requestParams);
                if(url != null){
                    return url + orderSn;
                }

                //等待页面
                return "redirect:/wish/order/waiting?orderSn="+orderSn;
            }
        }

        //失败页面
        return "redirect:/wish/order/failed?orderSn="+orderSn;
    }



    /** TODO ================== 筛选订单相关 ================== */
    //等待页面
    private String selectOrderWaitUrl(String orderSn){
        return "redirect:/part/pay/waiting?orderSn="+orderSn;
    }
    //失败页面
    private String selectOrderFailedUrl(String orderSn){
        return "redirect:/part/pay/failed?orderSn="+orderSn;
    }
    //成功页面
    private String selectOrderSuccessUrl(String orderSn){
        Result<OrderBO> result = orderBiz.getOrderBySn(orderSn);
        if(result.isSuccess()){
            if(OrderStatusEnums.HAVE_PAY.getCode().equals(result.getData().getOrderStatus())) {
                return "redirect:/part/pay/success?orderSn="+orderSn;
            }
        }
        return null;
    }


    private String aliPay4SelectOrderUrl(Map<String, String[]> requestParams){
        Result<String> result = payBiz.verifyAliReturn4SelectOrder(requestParams);
        if(result.isSuccess() && !"fail".equals(result.getData())){
            return "redirect:/part/pay/success?orderSn=";
        }
        return null;
    }

    //支付宝同步回调
    @RequestMapping(value = "aliPay4SelectOrder")
    public String aliPay4SelectOrder() {
        Map<String, String[]> requestParams = request.getParameterMap();
        String orderSn = getOrderSn(requestParams.get("out_trade_no"));
        String url = aliPay4SelectOrderUrl(requestParams);
        if(url != null){
            return url + orderSn;
        }

        String[] tradeStatus = requestParams.get("trade_status");
        if(tradeStatus!=null && tradeStatus.length>0){
            //支付宝支付成功，再次验证订单状态是否修改
            if("TRADE_SUCCESS".equals(tradeStatus[0]) || "TRADE_FINISHED".equals(tradeStatus[0])){
                url = aliPay4SelectOrderUrl(requestParams);
                if(url != null){
                    return url + orderSn;
                }

                //等待页面
                return selectOrderWaitUrl(orderSn);
            }
        }

        //失败页面
        return selectOrderFailedUrl(orderSn);
    }


    private String unionPay4SelectOrderUrl(Map<String, String[]> requestParams){
        Result<String> result = payBiz.verifyUnionReturn4SelectOrder(requestParams);
        if(result.isSuccess() && !"fail".equals(result.getData())){
            return "redirect:/part/pay/success?orderSn=";
        }
        return null;
    }

    //连连支付同步回调
    @RequestMapping(value = "unionPay4SelectOrder")
    public String unionPay4SelectOrder() {
        Map<String, String[]> requestParams = request.getParameterMap();
        String orderSn = getOrderSn(requestParams.get("no_order"));
        String url = unionPay4SelectOrderUrl(requestParams);
        if(url != null){
            return url + orderSn;
        }

        String[] tradeStatus = requestParams.get("result_pay");
        if(tradeStatus!=null && tradeStatus.length>0){
            //连连支付成功，再次验证订单状态是否修改
            if("SUCCESS".equals(tradeStatus[0])){
                url = unionPay4SelectOrderUrl(requestParams);
                if(url != null){
                    return url + orderSn;
                }

                //等待页面
                return selectOrderWaitUrl(orderSn);
            }
        }

        //失败页面
        return selectOrderFailedUrl(orderSn);
    }

}
