package com.tqmall.data.epc.biz.base;

import com.tqmall.core.common.entity.Result;
import com.tqmall.data.epc.bean.bizBean.order.OrderDetailBO;
import com.tqmall.data.epc.bean.bizBean.order.OrderGoodsBO;
import com.tqmall.data.epc.bean.bizBean.order.fee.OrderOLPaymentBO;
import com.tqmall.data.epc.bean.bizBean.pay.BankCardBO;
import com.tqmall.data.epc.bean.bizBean.pay.UserBankCardBO;
import com.tqmall.data.epc.bean.param.lop.WishOrderPayParam;
import com.tqmall.data.epc.bean.param.lop.WishOrderUnionPayParam;
import com.tqmall.data.epc.bean.param.order.pay.AliPayFormParam;
import com.tqmall.data.epc.bean.param.order.pay.UnionPayFormParam;
import com.tqmall.data.epc.biz.order.OrderBiz;
import com.tqmall.data.epc.biz.shiro.ShiroBiz;
import com.tqmall.data.epc.common.bean.ConstantBean;
import com.tqmall.data.epc.common.bean.EpcError;
import com.tqmall.data.epc.common.bean.enums.OrderStatusEnums;
import com.tqmall.data.epc.common.bean.enums.PayMessage;
import com.tqmall.data.epc.common.utils.EPCUtil;
import com.tqmall.data.epc.common.utils.JsonUtil;
import com.tqmall.data.epc.common.utils.ResultUtil;
import com.tqmall.data.epc.common.utils.upload.ImgUtil;
import com.tqmall.data.epc.exterior.dubbo.base.PayServiceExt;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by huangzhangting on 16/7/31.
 */
@Slf4j
@Service
public class PayBizImpl implements PayBiz {
    @Value("${epc.host.public}")
    private String epcHostPublic;
    @Autowired
    private ShiroBiz shiroBiz;
    @Autowired
    private PayServiceExt payServiceExt;
    @Autowired
    private OrderBiz orderBiz;


    @Override
    public List<OrderOLPaymentBO> getOnlinePayment() {
        return payServiceExt.getOnlinePayment("1", "pc", shiroBiz.getUserId());
    }

    @Override
    public List<OrderOLPaymentBO> getLocalPayment() {

        List<OrderOLPaymentBO> list = new ArrayList<>();
        OrderOLPaymentBO paymentBO = new OrderOLPaymentBO();
        paymentBO.setId(4);
        paymentBO.setCode("alipay");
        paymentBO.setTitle("支付宝支付");
        paymentBO.setUrl("/img/lop/center_alipay_not.png");
        list.add(paymentBO);

        paymentBO = new OrderOLPaymentBO();
        paymentBO.setId(17);
        paymentBO.setCode("lianpay");
        paymentBO.setTitle("银行卡支付");
        paymentBO.setUrl("/img/lop/center_unionpay_not.png");
        list.add(paymentBO);

        return list;
    }

    @Override
    public String getPayName(Integer payId) {
        if(payId==null || payId<1){
            return "";
        }
        List<OrderOLPaymentBO> list = getLocalPayment();
        for(OrderOLPaymentBO paymentBO : list){
            if(payId.equals(paymentBO.getId())){
                return paymentBO.getTitle();
            }
        }
        return "";
    }

    @Override
    public Result setPayment4WishOrder(Integer orderId, Byte paymentId) {
        if(orderId==null || orderId<1 || paymentId==null || paymentId<1){
            return ResultUtil.errorResult(EpcError.ARG_ERROR);
        }
        return payServiceExt.setPayment4WishOrder(shiroBiz.getUserId(), orderId, paymentId);
    }

    @Override
    public Result<String> getAliPayParam4WishOrder(Integer orderId) {
        if(orderId==null || orderId<1){
            return ResultUtil.errorResult(EpcError.ARG_ERROR);
        }
        WishOrderPayParam payParam = new WishOrderPayParam();
        payParam.setOfferListId(orderId);
        payParam.setUid(shiroBiz.getUserId());
        payParam.setWebReturnUrl(epcHostPublic+"/payCallback/aliPay4Wish");
        payParam.setDeviceId(ConstantBean.WEB_DEVICE_ID);

        return payServiceExt.getAliPayParam4WishOrder(payParam);
    }

    @Override
    public Result<String> verifyAliReturn4WishOrder(Map<String, String[]> requestMap) {
        if(requestMap==null){
            return ResultUtil.errorResult(EpcError.ARG_ERROR);
        }
        return payServiceExt.verifyAliReturn4WishOrder(ConstantBean.APPLICATION_NAME, requestMap);
    }

    @Override
    public List<UserBankCardBO> getUserBindCardList() {
        List<UserBankCardBO> list =
                payServiceExt.getUserBindCardList(ConstantBean.APPLICATION_NAME, shiroBiz.getUserId());

        for(UserBankCardBO cardBO : list){
            cardBO.setBankLogoWeb(ImgUtil.httpToHttps(cardBO.getBankLogoWeb()));
        }
        return list;
    }

    @Override
    public List<BankCardBO> getSupportCardList() {
        List<BankCardBO> list = payServiceExt.getSupportCardList(ConstantBean.APPLICATION_NAME);
        for(BankCardBO cardBO : list){
            cardBO.setBankLogoWeb(ImgUtil.httpToHttps(cardBO.getBankLogoWeb()));
        }
        return list;
    }

    @Override
    public Result<BankCardBO> getBankCard(String cardNo) {
        if(StringUtils.isEmpty(cardNo)){
            return ResultUtil.errorResult(EpcError.ARG_ERROR);
        }

        try {
            cardNo = EPCUtil.encryptDES(cardNo, PayMessage.DES_KEY.getMessage());
        } catch (Exception e) {
            log.error("encryptDES bank card no error, cardNo:"+cardNo, e);
            return ResultUtil.errorResult(EpcError.UN_KNOW_EXCEPTION);
        }

        BankCardBO cardBO = payServiceExt.getBankCard(ConstantBean.APPLICATION_NAME, cardNo);
        if(cardBO==null){
            return ResultUtil.errorResult(EpcError.NO_DATA_ERROR);
        }
        return ResultUtil.successResult(cardBO);
    }

    @Override
    public Result<String> getUnionPayParam4WishOrder(WishOrderUnionPayParam payParam) {
        if(payParam == null){
            return ResultUtil.errorResult(EpcError.ARG_ERROR);
        }
        try {
            payParam.setCardNo(EPCUtil.encryptDES(payParam.getCardNo(), PayMessage.DES_KEY.getMessage()));
            payParam.setAcctName(EPCUtil.encryptDES(payParam.getAcctName(), PayMessage.DES_KEY.getMessage()));
            payParam.setIdNo(EPCUtil.encryptDES(payParam.getIdNo(), PayMessage.DES_KEY.getMessage()));
        }catch (Exception e){
            log.error("encryptDES union param error, payParam:"+ JsonUtil.objectToJson(payParam), e);
            return ResultUtil.errorResult(EpcError.UN_KNOW_EXCEPTION);
        }
        payParam.setUid(shiroBiz.getUserId());
        payParam.setWebReturnUrl(epcHostPublic+"/payCallback/unionPay4Wish");
        payParam.setDeviceId(ConstantBean.WEB_DEVICE_ID);

        return payServiceExt.getUnionPayParam4WishOrder(payParam);
    }

    @Override
    public Result<String> verifyUnionReturn4WishOrder(Map<String, String[]> requestMap) {
        if(requestMap==null){
            return ResultUtil.errorResult(EpcError.ARG_ERROR);
        }
        return payServiceExt.verifyUnionReturn4WishOrder(ConstantBean.APPLICATION_NAME, requestMap);
    }

    @Override
    public Result<String> getAliPayParam4SelectOrder(String orderSn) {
        Integer shopId = shiroBiz.getCurrentUser().getShopId();
        Result<OrderDetailBO> result = orderBiz.getOrderDetailByShop(orderSn, shopId);
        if(!result.isSuccess()){
            return ResultUtil.errorResult("", "订单不存在，orderSn："+orderSn);
        }
        OrderDetailBO detailBO = result.getData();
        if(!OrderStatusEnums.NOT_PAY.getCode().equals(detailBO.getOrderStatus())){
            return ResultUtil.errorResult("", "订单状态无效，orderSn："+orderSn);
        }
        AliPayFormParam formParam = new AliPayFormParam();
        formParam.setBody(buildBody(detailBO.getOrderGoodsBOs()));
        formParam.setSubject(buildSubject(detailBO.getOrderGoodsBOs()));
        formParam.setTotalFee(detailBO.getOrderAmount());
        formParam.setSn(orderSn);
        formParam.setUid(shiroBiz.getUserId());
        formParam.setDeviceId(ConstantBean.WEB_DEVICE_ID);
        formParam.setSource(ConstantBean.APPLICATION_NAME);
        formParam.setWebReturnUrl(epcHostPublic+"/payCallback/aliPay4SelectOrder");

        return payServiceExt.getAliPayParam4SelectOrder(formParam);
    }

    @Override
    public Result<String> getUnionPayParam4SelectOrder(UnionPayFormParam param) {
        if(param == null){
            return ResultUtil.errorResult(EpcError.ARG_ERROR);
        }
        try {
            param.setCardNo(EPCUtil.encryptDES(param.getCardNo(), PayMessage.DES_KEY.getMessage()));
            param.setAcctName(EPCUtil.encryptDES(param.getAcctName(), PayMessage.DES_KEY.getMessage()));
            param.setIdNo(EPCUtil.encryptDES(param.getIdNo(), PayMessage.DES_KEY.getMessage()));
        }catch (Exception e){
            log.error("encryptDES union param error, payParam:"+ JsonUtil.objectToJson(param), e);
            return ResultUtil.errorResult(EpcError.UN_KNOW_EXCEPTION);
        }

        Integer shopId = shiroBiz.getCurrentUser().getShopId();
        String orderSn = param.getSn();
        Result<OrderDetailBO> result = orderBiz.getOrderDetailByShop(orderSn, shopId);
        if(!result.isSuccess()){
            return ResultUtil.errorResult("", "订单不存在，orderSn："+orderSn);
        }
        OrderDetailBO detailBO = result.getData();
        if(!OrderStatusEnums.NOT_PAY.getCode().equals(detailBO.getOrderStatus())){
            return ResultUtil.errorResult("", "订单状态无效，orderSn："+orderSn);
        }
        param.setBody(buildBody(detailBO.getOrderGoodsBOs()));
        param.setSubject(buildSubject(detailBO.getOrderGoodsBOs()));
        param.setTotalFee(detailBO.getOrderAmount());
        param.setSn(orderSn);
        param.setGmtCreate(detailBO.getGmtCreate());
        param.setUid(shiroBiz.getUserId());
        param.setDeviceId(ConstantBean.WEB_DEVICE_ID);
        param.setSource(ConstantBean.APPLICATION_NAME);
        param.setWebReturnUrl(epcHostPublic+"/payCallback/unionPay4SelectOrder");

        return payServiceExt.getUnionPayParam4SelectOrder(param);
    }

    private String buildBody(List<OrderGoodsBO> goodsBOList){
        if(CollectionUtils.isEmpty(goodsBOList)) {
            return "";
        }
        StringBuilder body = new StringBuilder();
        for(OrderGoodsBO goodsBO : goodsBOList){
            body.append("|").append(goodsBO.getPartName());
        }
        body.deleteCharAt(0);

        return body.toString();
    }
    private String buildSubject(List<OrderGoodsBO> goodsBOList){
        if(CollectionUtils.isEmpty(goodsBOList)) {
            return "";
        }
        StringBuilder subject = new StringBuilder();
        subject.append(goodsBOList.get(0).getPartName());
        if(goodsBOList.size()>1){
            subject.append("等").append(goodsBOList.size()).append("种商品");
        }

        return subject.toString();
    }

    @Override
    public Result<String> verifyAliReturn4SelectOrder(Map<String, String[]> requestMap) {
        if(requestMap==null){
            return ResultUtil.errorResult(EpcError.ARG_ERROR);
        }
        return payServiceExt.verifyAliReturn4SelectOrder(ConstantBean.APPLICATION_NAME, requestMap);
    }

    @Override
    public Result<String> verifyUnionReturn4SelectOrder(Map<String, String[]> requestMap) {
        if(requestMap==null){
            return ResultUtil.errorResult(EpcError.ARG_ERROR);
        }
        return payServiceExt.verifyUnionReturn4SelectOrder(ConstantBean.APPLICATION_NAME, requestMap);
    }

}
