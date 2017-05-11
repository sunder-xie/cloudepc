package com.tqmall.data.epc.biz.base;

import com.tqmall.core.common.entity.Result;
import com.tqmall.data.epc.bean.bizBean.order.fee.OrderOLPaymentBO;
import com.tqmall.data.epc.bean.bizBean.pay.BankCardBO;
import com.tqmall.data.epc.bean.bizBean.pay.UserBankCardBO;
import com.tqmall.data.epc.bean.param.lop.WishOrderUnionPayParam;
import com.tqmall.data.epc.bean.param.order.pay.UnionPayFormParam;

import java.util.List;
import java.util.Map;

/**
 * 支付业务
 * Created by huangzhangting on 16/7/31.
 */
public interface PayBiz {
    /**
     * 获取网站在线支付方式
     * @return
     */
    List<OrderOLPaymentBO> getOnlinePayment();

    List<OrderOLPaymentBO> getLocalPayment();

    String getPayName(Integer payId);

    /**
     * 需求订单，设置支付
     * @param orderId
     * @param paymentId
     * @return
     */
    Result setPayment4WishOrder(Integer orderId, Byte paymentId);

    /**
     * 获取支付宝参数
     * @param orderId
     * @return
     */
    Result<String> getAliPayParam4WishOrder(Integer orderId);

    /**
     * 验证支付宝，返回结果
     * @param requestMap
     * @return
     */
    Result<String> verifyAliReturn4WishOrder(Map<String, String[]> requestMap);

    /**
     * 获取用户绑定的银行卡
     * @return
     */
    List<UserBankCardBO> getUserBindCardList();

    /**
     * 获取支持的银行卡
     * @return
     */
    List<BankCardBO> getSupportCardList();

    /**
     * 根据银行卡号，查询银行卡信息
     * @param cardNo
     * @return
     */
    Result<BankCardBO> getBankCard(String cardNo);

    /**
     * 获取连连支付参数
     * @param payParam
     * @return
     */
    Result<String> getUnionPayParam4WishOrder(WishOrderUnionPayParam payParam);

    /**
     * 验证连连支付，返回结果
     * @param requestMap
     * @return
     */
    Result<String> verifyUnionReturn4WishOrder(Map<String, String[]> requestMap);


    //=============== 筛选订单相关 ===============

    /**
     * 获取支付宝支付参数
     * @param orderSn
     * @return
     */
    Result<String> getAliPayParam4SelectOrder(String orderSn);

    /**
     * 获取连连支付参数
     * @param param
     * @return
     */
    Result<String> getUnionPayParam4SelectOrder(UnionPayFormParam param);

    /**
     * 验证支付宝，返回结果
     * @param requestMap
     * @return
     */
    Result<String> verifyAliReturn4SelectOrder(Map<String, String[]> requestMap);

    /**
     * 验证连连支付，返回结果
     * @param requestMap
     * @return
     */
    Result<String> verifyUnionReturn4SelectOrder(Map<String, String[]> requestMap);

}
