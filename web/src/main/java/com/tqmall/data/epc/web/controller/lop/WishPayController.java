package com.tqmall.data.epc.web.controller.lop;

import com.tqmall.core.common.entity.Result;
import com.tqmall.data.epc.bean.bizBean.lop.WishOrderBO;
import com.tqmall.data.epc.bean.bizBean.order.fee.OrderOLPaymentBO;
import com.tqmall.data.epc.bean.bizBean.pay.BankCardBO;
import com.tqmall.data.epc.bean.bizBean.pay.UserBankCardBO;
import com.tqmall.data.epc.bean.param.lop.WishOrderUnionPayParam;
import com.tqmall.data.epc.biz.base.PayBiz;
import com.tqmall.data.epc.biz.lop.OfferListBiz;
import com.tqmall.data.epc.web.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by huangzhangting on 16/7/31.
 */
@Slf4j
@Controller
@RequestMapping("wish/pay")
public class WishPayController extends BaseController{
    @Autowired
    private OfferListBiz offerListBiz;
    @Autowired
    private PayBiz payBiz;


    /**
     * 选择支付方式，跳转到选择页面
     * @param orderSn
     * @return
     */
    @RequestMapping("selectPayment")
    public Object selectPayment(String orderSn){
        Result<WishOrderBO> result = offerListBiz.getOrderDetail(null, orderSn);
        if(!result.isSuccess()){
            log.info("get order detail failed, orderSn:{}", orderSn);
            return "redirect:/wish";
        }
        ModelAndView view = new ModelAndView(webVersion + "/cloudepc/lop/pay/selectPayment");
        view.addObject("orderInfo", result.getData());

        List<OrderOLPaymentBO> paymentBOList = payBiz.getLocalPayment();
        view.addObject("paymentList", paymentBOList);

        return view;
    }

    /**
     * 确定支付方式
     * @param orderId
     * @param paymentId
     * @return
     */
    @RequestMapping("setPayment")
    @ResponseBody
    public Result setPayment(Integer orderId, Byte paymentId){
        return payBiz.setPayment4WishOrder(orderId, paymentId);
    }

    /**
     * 支付宝支付
     * @param orderId
     * @return
     */
    @RequestMapping("aliPay")
    public Object aliPay(Integer orderId){
        Result<WishOrderBO> orderResult = offerListBiz.getOrderDetail(orderId, null);
        if(!orderResult.isSuccess()){
            log.info("get order detail failed, orderId:{}", orderId);
            return "redirect:/wish";
        }
        if(orderResult.getData().getOfferListSn()==null){
            log.info("order sn is null, orderId:{}", orderId);
            return "redirect:/wish";
        }

        Result<String> result = payBiz.getAliPayParam4WishOrder(orderId);
        ModelAndView view = new ModelAndView(webVersion+"/cloudepc/pay/goPay");
        if(result.isSuccess()){
            view.addObject("payHtml", result.getData());
        }

        return view;
    }

    /**
     * 选择银行卡支付
     * 如果已绑定，银行卡，则跳转到支付页面
     * 如果未绑定，银行卡，则跳转到添加银行卡页面
     * @param orderId
     * @return
     */
    @RequestMapping("selectUnionPay")
    public Object unionPay(Integer orderId){
        Result<WishOrderBO> orderResult = offerListBiz.getOrderDetail(orderId, null);
        if(!orderResult.isSuccess()){
            log.info("get order detail failed, orderId:{}", orderId);
            return "redirect:/wish";
        }
        String orderSn = orderResult.getData().getOfferListSn();
        if(orderSn==null){
            log.info("order sn is null, orderId:{}", orderId);
            return "redirect:/wish";
        }

        List<UserBankCardBO> list = payBiz.getUserBindCardList();
        if(list.isEmpty()){
            return "redirect:/wish/pay/boundCardPage?orderId="+orderId;
        }

        ModelAndView view = new ModelAndView();
        view.addObject("orderInfo", orderResult.getData());
        view.addObject("userBoundCardList", list);
        view.setViewName(webVersion+"/cloudepc/lop/pay/unionPay");
        return view;
    }

    /**
     * 银行卡支付
     * @param payParam
     * @return
     */
    @RequestMapping("unionPay")
    public Object unionPay(WishOrderUnionPayParam payParam){
        Result<String> result = payBiz.getUnionPayParam4WishOrder(payParam);
        if(!result.isSuccess()){
            return "redirect:/wish";
        }

        ModelAndView view = new ModelAndView(webVersion+"/cloudepc/pay/goPay");
        view.addObject("payHtml", result.getData());

        return view;
    }

    /**
     * 绑定银行卡页面
     * @param orderId
     * @return
     */
    @RequestMapping("boundCardPage")
    public Object boundCardPage(Integer orderId){
        Result<WishOrderBO> orderResult = offerListBiz.getOrderDetail(orderId, null);
        if(!orderResult.isSuccess()){
            log.info("get order detail failed, orderId:{}", orderId);
            return "redirect:/wish";
        }
        String orderSn = orderResult.getData().getOfferListSn();
        if(orderSn==null){
            log.info("order sn is null, orderId:{}", orderId);
            return "redirect:/wish";
        }

        ModelAndView view = new ModelAndView(webVersion+"/cloudepc/lop/pay/boundCard");
        view.addObject("orderInfo", orderResult.getData());
        view.addObject("lianLianSupportList", payBiz.getSupportCardList());

        return view;
    }

    /**
     * 根据银行卡号，查询银行卡账户信息
     * @param cardNo
     * @return
     */
    @RequestMapping("getBankCard")
    @ResponseBody
    public Result<BankCardBO> getBankCard(String cardNo){
        return payBiz.getBankCard(cardNo);
    }

}
