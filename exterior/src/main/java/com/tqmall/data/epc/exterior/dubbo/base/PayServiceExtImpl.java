package com.tqmall.data.epc.exterior.dubbo.base;

import com.tqmall.core.common.entity.Result;
import com.tqmall.data.epc.bean.bizBean.order.fee.OrderOLPaymentBO;
import com.tqmall.data.epc.bean.bizBean.pay.BankCardBO;
import com.tqmall.data.epc.bean.bizBean.pay.UserBankCardBO;
import com.tqmall.data.epc.bean.param.lop.WishOrderPayParam;
import com.tqmall.data.epc.bean.param.lop.WishOrderUnionPayParam;
import com.tqmall.data.epc.bean.param.order.pay.AliPayFormParam;
import com.tqmall.data.epc.bean.param.order.pay.UnionPayFormParam;
import com.tqmall.data.epc.common.utils.BdUtil;
import com.tqmall.data.epc.common.utils.JsonUtil;
import com.tqmall.finance.model.param.pay.LianPayRequest;
import com.tqmall.finance.model.param.pay.OfferListFormParam;
import com.tqmall.finance.model.param.pay.OfferListLianFormParam;
import com.tqmall.finance.model.result.user.BankCardDTO;
import com.tqmall.finance.model.result.user.UserBankCardDTO;
import com.tqmall.finance.service.pay.WebPayService;
import com.tqmall.tqmallstall.domain.param.pay.OfferListLianPayRequest;
import com.tqmall.tqmallstall.domain.param.pay.OfferListPayRequest;
import com.tqmall.tqmallstall.domain.tc.OrderOLPaymentDTO;
import com.tqmall.tqmallstall.service.PaymentService;
import com.tqmall.tqmallstall.service.pay.WebOfferListPayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by huangzhangting on 16/7/31.
 */
@Service
@Slf4j
public class PayServiceExtImpl implements PayServiceExt {
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private WebOfferListPayService webOfferListPayService;
    @Autowired
    private WebPayService webPayService;


    @Override
    public List<OrderOLPaymentBO> getOnlinePayment(String userType, String deviceType, Integer userId){
        log.info("get online payment, userType:{}, deviceType:{}, userId:{}", userType, deviceType, userId);

        Result<List<OrderOLPaymentDTO>> result = paymentService.getOnlinePayment(userType, deviceType, userId);

        log.info("get online payment, result:{}", JsonUtil.objectToJson(result));

        if(result.isSuccess()){
            return BdUtil.do2bo4List(result.getData(), OrderOLPaymentBO.class);
        }

        return new ArrayList<>();
    }

    @Override
    public Result setPayment4WishOrder(Integer userId, Integer orderId, Byte paymentId) {
        log.info("set payment, userId:{}, orderId:{}, paymentId:{}", userId, orderId, paymentId);

        Result<Void> result = paymentService.setPayment4OfferList(userId, orderId, paymentId);

        log.info("set payment, result:{}", JsonUtil.objectToJson(result));

        return result;
    }

    @Override
    public Result<String> getAliPayParam4WishOrder(WishOrderPayParam payParam) {
        log.info("get ali pay param, param:{}", JsonUtil.objectToJson(payParam));
        
        OfferListPayRequest payRequest = BdUtil.do2bo(payParam, OfferListPayRequest.class);
        Result<String> result = webOfferListPayService.getAliPayParam4OfferList(payRequest);

        log.info("get ali pay param, result:{}", JsonUtil.objectToJson(result));

        return result;
    }

    @Override
    public Result<String> verifyAliReturn4WishOrder(String appName, Map<String, String[]> requestMap) {
        log.info("verify ali return, param:{}", JsonUtil.objectToJson(requestMap));

        Result<String> result = webPayService.verifyOfferListAliReturn(appName, requestMap);

        log.info("verify ali return, result:{}", JsonUtil.objectToJson(result));

        return result;
    }

    @Override
    public List<UserBankCardBO> getUserBindCardList(String appName, Integer userId) {
        log.info("get user bind card list, appName:{}, userId:{}", appName, userId);

        Result<List<UserBankCardDTO>> result = webPayService.queryUserBindCardList(appName, userId);
        if(result.isSuccess() && !CollectionUtils.isEmpty(result.getData())){
            return BdUtil.do2bo4List(result.getData(), UserBankCardBO.class);
        }

        log.info("get user bind card list, result:{}", JsonUtil.objectToJson(result));
        return new ArrayList<>();
    }

    @Override
    public List<BankCardBO> getSupportCardList(String appName) {
        log.info("get support card list, appName:{}", appName);

        Result<List<BankCardDTO>> result = webPayService.querySupportCardList(appName);
        if(result.isSuccess() && !CollectionUtils.isEmpty(result.getData())){
            return BdUtil.do2bo4List(result.getData(), BankCardBO.class);
        }

        log.info("get support card list, result:{}", JsonUtil.objectToJson(result));
        return new ArrayList<>();
    }

    @Override
    public BankCardBO getBankCard(String appName, String cardNo) {
        log.info("get bank card, appName:{}, cardNo:{}", appName, cardNo);

        LianPayRequest payRequest = new LianPayRequest();
        payRequest.setCardNo(cardNo);
        payRequest.setSource(appName);

        Result<BankCardDTO> result = webPayService.queryCardBin(payRequest);
        if(result.isSuccess() && result.getData()!=null){
            return BdUtil.do2bo(result.getData(), BankCardBO.class);
        }

        log.info("get bank card, result:{}", JsonUtil.objectToJson(result));
        return null;
    }

    @Override
    public Result<String> getUnionPayParam4WishOrder(WishOrderUnionPayParam payParam) {
        log.info("get lian pay param, param:{}", JsonUtil.objectToJson(payParam));

        OfferListLianPayRequest payRequest = BdUtil.do2bo(payParam, OfferListLianPayRequest.class);
        Result<String> result = webOfferListPayService.getOfferListLianPayParameter(payRequest);

        log.info("get lian pay param, result:{}", JsonUtil.objectToJson(result));

        return result;
    }

    @Override
    public Result<String> verifyUnionReturn4WishOrder(String appName, Map<String, String[]> requestMap) {
        log.info("verify union return, param:{}", JsonUtil.objectToJson(requestMap));

        Result<String> result = webPayService.verifyOfferListUnionReturn(appName, requestMap);

        log.info("verify union return, result:{}", JsonUtil.objectToJson(result));

        return result;
    }

    @Override
    public Result<String> getAliPayParam4SelectOrder(AliPayFormParam param) {
        log.info("getAliPayParam4SelectOrder, param:{}", JsonUtil.objectToJson(param));

        OfferListFormParam formParam = BdUtil.do2bo(param, OfferListFormParam.class);
        Result<String> result = webPayService.getAliPayParam4Epc(formParam);

        log.info("getAliPayParam4SelectOrder, result:{}", JsonUtil.objectToJson(result));
        return result;
    }

    @Override
    public Result<String> getUnionPayParam4SelectOrder(UnionPayFormParam param) {
        log.info("getUnionPayParam4SelectOrder, param:{}", JsonUtil.objectToJson(param));

        OfferListLianFormParam formParam = BdUtil.do2bo(param, OfferListLianFormParam.class);
        Result<String> result = webPayService.getLianPayParam4Epc(formParam);

        log.info("getUnionPayParam4SelectOrder, result:{}", JsonUtil.objectToJson(result));
        return result;
    }

    @Override
    public Result<String> verifyAliReturn4SelectOrder(String appName, Map<String, String[]> requestMap) {
        log.info("verifyAliReturn4SelectOrder, param:{}", JsonUtil.objectToJson(requestMap));

        Result<String> result = webPayService.verifyAliReturnEpc(appName, requestMap);

        log.info("verifyAliReturn4SelectOrder, result:{}", JsonUtil.objectToJson(result));

        return result;
    }

    @Override
    public Result<String> verifyUnionReturn4SelectOrder(String appName, Map<String, String[]> requestMap) {
        log.info("verifyUnionReturn4SelectOrder, param:{}", JsonUtil.objectToJson(requestMap));

        Result<String> result = webPayService.verifyLianReturnEpc(appName, requestMap);

        log.info("verifyUnionReturn4SelectOrder, result:{}", JsonUtil.objectToJson(result));

        return result;
    }

}
