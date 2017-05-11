package com.tqmall.data.epc.exterior.dubbo.base;

import com.tqmall.core.common.entity.Result;
import com.tqmall.data.epc.bean.bizBean.order.fee.OrderOLPaymentBO;
import com.tqmall.data.epc.bean.bizBean.pay.BankCardBO;
import com.tqmall.data.epc.bean.bizBean.pay.UserBankCardBO;
import com.tqmall.data.epc.bean.param.lop.WishOrderPayParam;
import com.tqmall.data.epc.bean.param.lop.WishOrderUnionPayParam;
import com.tqmall.data.epc.bean.param.order.pay.AliPayFormParam;
import com.tqmall.data.epc.bean.param.order.pay.UnionPayFormParam;

import java.util.List;
import java.util.Map;

/**
 * 外部支付服务
 * Created by huangzhangting on 16/7/31.
 */
public interface PayServiceExt {
    /**
     * 获取支付方式
     * @param userType
     * @param deviceType
     * @param userId
     * @return
     */
    List<OrderOLPaymentBO> getOnlinePayment(String userType,String deviceType,Integer userId);

    /**
     * 设置支付方式
     * @param userId
     * @param orderId
     * @param paymentId
     * @return
     */
    Result setPayment4WishOrder(Integer userId, Integer orderId, Byte paymentId);

    /**
     * 获取支付宝参数
     * @param payParam
     * @return
     */
    Result<String> getAliPayParam4WishOrder(WishOrderPayParam payParam);

    /**
     * 验证支付宝返回值
     * @param requestMap
     * @return
     */
    Result<String> verifyAliReturn4WishOrder(String appName, Map<String, String[]> requestMap);

    /**
     * 查询用户绑定的银行卡
     * @param appName
     * @param userId
     * @return
     */
    List<UserBankCardBO> getUserBindCardList(String appName, Integer userId);

    /**
     * 获取支持的银行卡
     * @return
     */
    List<BankCardBO> getSupportCardList(String appName);

    /**
     * 根据银行卡号，查询银行卡信息
     * @param appName
     * @param cardNo
     * @return
     */
    BankCardBO getBankCard(String appName, String cardNo);

    /**
     * 获取连连支付参数
     * @param payParam
     * @return
     */
    Result<String> getUnionPayParam4WishOrder(WishOrderUnionPayParam payParam);

    /**
     * 验证连连支付，返回结果
     * @param appName
     * @param requestMap
     * @return
     */
    Result<String> verifyUnionReturn4WishOrder(String appName, Map<String, String[]> requestMap);



    //=============== 筛选订单相关 ===============

    /**
     * 获取支付宝支付参数
     * @param param
     * @return
     */
    Result<String> getAliPayParam4SelectOrder(AliPayFormParam param);

    /**
     * 获取连连支付参数
     * @param param
     * @return
     */
    Result<String> getUnionPayParam4SelectOrder(UnionPayFormParam param);

    /**
     * 验证支付宝返回值
     * @param requestMap
     * @return
     */
    Result<String> verifyAliReturn4SelectOrder(String appName, Map<String, String[]> requestMap);

    /**
     * 验证连连支付，返回结果
     * @param appName
     * @param requestMap
     * @return
     */
    Result<String> verifyUnionReturn4SelectOrder(String appName, Map<String, String[]> requestMap);

}
