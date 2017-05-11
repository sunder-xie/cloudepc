package com.tqmall.data.epc.web.controller.order;

import com.tqmall.core.common.entity.Result;
import com.tqmall.data.epc.bean.bizBean.order.OrderBO;
import com.tqmall.data.epc.bean.bizBean.order.OrderDetailBO;
import com.tqmall.data.epc.bean.bizBean.order.fee.OrderOLPaymentBO;
import com.tqmall.data.epc.bean.bizBean.pay.BankCardBO;
import com.tqmall.data.epc.bean.bizBean.pay.UserBankCardBO;
import com.tqmall.data.epc.bean.bizBean.sys.UserBO;
import com.tqmall.data.epc.bean.param.order.pay.UnionPayFormParam;
import com.tqmall.data.epc.biz.base.PayBiz;
import com.tqmall.data.epc.biz.order.OrderBiz;
import com.tqmall.data.epc.common.bean.ConstantBean;
import com.tqmall.data.epc.common.utils.ResultUtil;
import com.tqmall.data.epc.web.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lyj on 16/9/1.
 * 开开心心写代码-lyj!
 */
@Slf4j
@Controller
@RequestMapping("part/pay")
public class PartPayController extends BaseController {

    @Autowired
    private OrderBiz orderBiz;

    @Autowired
    private PayBiz payBiz;


    /**
     * 我的购物订单页面
     * @return
     */
    public String myOrderPage(){
        return "redirect:/buy/order";
    }

    /**
     * 选择支付方式
     * @param orderSn
     * @return
     */
    @RequestMapping("selectPayment")
    public Object selectPayment(String orderSn) {
        UserBO userBO = shiroBiz.getCurrentUser();
        Integer shopId = userBO.getShopId();
        Result<OrderBO> result = orderBiz.getOrderBySnAndShop(orderSn, shopId);
        if (!result.isSuccess()) {
            log.info("getOrderBySnAndShop failed, orderSn:{}, shopId:{}", orderSn, shopId);
            return myOrderPage();
        }

        ModelAndView modelAndView = new ModelAndView(webVersion + "/cloudepc/part/pay/selectPayment");
        modelAndView.addObject("orderInfo", result.getData());
        List<OrderOLPaymentBO> paymentBOList = payBiz.getLocalPayment();
        modelAndView.addObject("paymentList", paymentBOList);

        return modelAndView;
    }

    /**
     * 设置支付方式
     *
     * @param orderSn
     * @param payId
     * @return
     */
    @RequestMapping("setPayment")
    @ResponseBody
    public Result setPayment(String orderSn, Integer payId) {
        return orderBiz.setPayment(orderSn, payId);
    }


    /**
     * 选择订单待支付页面
     *
     * @param orderSn
     * @return
     */
    @RequestMapping("selectWaitPayOrder")
    public Object selectWaitPayOrder(String orderSn) {
        Integer shopId = shiroBiz.getCurrentUser().getShopId();
        Result<List<OrderDetailBO>> result = orderBiz.getSiblingOrderDetailList(orderSn, shopId);
        if (result.isSuccess()) {
            List<OrderDetailBO> resultList = result.getData();
            int size = resultList.size();
            if (size==1){
                OrderDetailBO orderDetailBO = resultList.get(0);
                if(ConstantBean.YES_STATUS.equals(orderDetailBO.getPayStatus())){
                    return orderSuccess(orderDetailBO.getOrderSn());
                }
                return selectPayment(orderDetailBO.getOrderSn());
            }

            List<String> noPaySnList = new ArrayList<>(); //未支付订单
            BigDecimal amount = BigDecimal.ZERO;
            for(OrderDetailBO detailBO : resultList){
                amount = amount.add(detailBO.getOrderAmount());
                if(ConstantBean.NO_STATUS.equals(detailBO.getPayStatus())){
                    noPaySnList.add(detailBO.getOrderSn());
                }
            }
            if(noPaySnList.size()==1){
                return selectPayment(noPaySnList.get(0));
            }

            ModelAndView modelAndView = new ModelAndView(webVersion + "/cloudepc/part/pay/selectWaitPayOrder");
            modelAndView.addObject("totalAmount", amount);
            modelAndView.addObject("orderList", resultList);
            modelAndView.addObject("orderCount", size);

            return modelAndView;
        }

        return myOrderPage();
    }


    /**
     * 支付宝支付
     *
     * @param orderSn
     * @return
     */
    @RequestMapping("aliPay")
    public Object aliPay(String orderSn) {
        Result<String>  result = payBiz.getAliPayParam4SelectOrder(orderSn);
        if(!result.isSuccess()){
            log.info("getAliPayParam4SelectOrder failed, message:{}", result.getMessage());
            return myOrderPage();
        }
        ModelAndView view = new ModelAndView(webVersion + "/cloudepc/pay/goPay");
        view.addObject("payHtml", result.getData());
        return view;
    }

    /**
     * 选择银行卡支付
     * 如果已绑定，银行卡，则跳转到支付页面
     * 如果未绑定，银行卡，则跳转到添加银行卡页面
     * @param orderSn
     * @return
     */
    @RequestMapping("selectUnionPay")
    public Object unionPay(String orderSn){
        UserBO userBO = shiroBiz.getCurrentUser();
        Integer shopId = userBO.getShopId();
        Result<OrderBO> result = orderBiz.getOrderBySnAndShop(orderSn, shopId);
        if (!result.isSuccess()) {
            log.info("getOrderBySnAndShop failed, orderSn:{}, shopId:{}", orderSn, shopId);
            return myOrderPage();
        }

        List<UserBankCardBO> list = payBiz.getUserBindCardList();
//        if(list.isEmpty()){
//            return "redirect:/part/pay/boundCardPage?orderSn="+orderSn;
//        }

        ModelAndView view = new ModelAndView();
        view.addObject("orderInfo", result.getData());
        view.addObject("userBoundCardList", list);
        view.setViewName(webVersion+"/cloudepc/part/pay/unionPay");
        return view;
    }

    /**
     * 银行卡支付
     * @param payParam
     * @return
     */
    @RequestMapping("unionPay")
    public Object unionPay(UnionPayFormParam payParam){
        Result<String> result = payBiz.getUnionPayParam4SelectOrder(payParam);
        if(!result.isSuccess()){
            log.info("getUnionPayParam4SelectOrder failed, message:{}", result.getMessage());
            return myOrderPage();
        }

        ModelAndView view = new ModelAndView(webVersion+"/cloudepc/pay/goPay");
        view.addObject("payHtml", result.getData());

        return view;
    }

    /**
     * 绑定银行卡页面
     * @param orderSn
     * @return
     */
    @RequestMapping("boundCardPage")
    public Object boundCardPage(String orderSn){
        UserBO userBO = shiroBiz.getCurrentUser();
        Integer shopId = userBO.getShopId();
        Result<OrderBO> result = orderBiz.getOrderBySnAndShop(orderSn, shopId);
        if (!result.isSuccess()) {
            log.info("getOrderBySnAndShop failed, orderSn:{}, shopId:{}", orderSn, shopId);
            return myOrderPage();
        }

        ModelAndView view = new ModelAndView(webVersion+"/cloudepc/part/pay/boundCard");
        view.addObject("orderInfo", result.getData());
        view.addObject("lianLianSupportList", payBiz.getSupportCardList());

        return view;
    }

    /**
     * 根据银行卡号，查询银行卡账户信息
     *
     * @param cardNo
     * @return
     */
    @RequestMapping("getBankCard")
    @ResponseBody
    public Result<BankCardBO> getBankCard(String cardNo) {
        return payBiz.getBankCard(cardNo);
    }



    /**
     * 订单支付成功回调
     *
     * @param orderSn
     * @return
     */
    @RequestMapping(value = "success")
    public Object orderSuccess(String orderSn) {
        UserBO userBO = shiroBiz.getCurrentUser();
        Integer shopId = userBO.getShopId();
        Result<OrderBO> result = orderBiz.getOrderBySnAndShop(orderSn, shopId);
        if (!result.isSuccess()) {
            log.info("getOrderBySnAndShop failed, orderSn:{}, shopId:{}", orderSn, shopId);
            return myOrderPage();
        }
        OrderBO order = result.getData();
        if (!ConstantBean.YES_STATUS.equals(order.getPayStatus())) {
            log.info("order not pay, orderSn:{}", orderSn);
            return selectPayment(orderSn);
        }

        ModelAndView view = new ModelAndView(webVersion + "/cloudepc/part/pay/success");
        view.addObject("orderInfo", order);

        //检查是否有拆单，未支付的订单
        Result<Integer> countResult = orderBiz.getNoPaySiblingOrderCount(order.getParentOrderSn(), shopId);
        if(countResult.isSuccess()){
            view.addObject("noPaySiblingOrderCount", countResult.getData());
        }
        return view;
    }

    /**
     * 订单第三方支付成功，等待支付状态修改
     *
     * @param orderSn
     * @return
     */
    @RequestMapping(value = "waiting")
    public Object orderWaiting(String orderSn) {
        ModelAndView view = new ModelAndView(webVersion + "/cloudepc/part/pay/waiting");
        view.addObject("orderSn", orderSn);

        return view;
    }

    /**
     * 订单支付失败
     *
     * @param orderSn
     * @return
     */
    @RequestMapping(value = "failed")
    public Object orderFailed(String orderSn) {
        ModelAndView view = new ModelAndView(webVersion + "/cloudepc/part/pay/failed");
        view.addObject("orderSn", orderSn);

        return view;
    }

    /**
     * 验证订单支付状态
     * @param orderSn
     * @return
     */
    @RequestMapping("checkOrderPayStatus")
    @ResponseBody
    public Result checkOrderPayStatus(String orderSn){
        UserBO userBO = shiroBiz.getCurrentUser();
        Integer shopId = userBO.getShopId();
        Result<OrderBO> result = orderBiz.getOrderBySnAndShop(orderSn, shopId);
        if (result.isSuccess()) {
            if(ConstantBean.YES_STATUS.equals(result.getData().getPayStatus())){
                return ResultUtil.successResult(1);
            }
        }
        return ResultUtil.errorResult("", "");
    }

}
