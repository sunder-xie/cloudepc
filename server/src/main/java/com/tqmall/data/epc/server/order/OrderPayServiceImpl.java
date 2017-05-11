package com.tqmall.data.epc.server.order;

import com.tqmall.core.common.entity.Result;
import com.tqmall.data.epc.bean.bizBean.order.OrderBO;
import com.tqmall.data.epc.bean.param.order.pay.PayCallbackPO;
import com.tqmall.data.epc.bean.param.order.pay.PaymentPO;
import com.tqmall.data.epc.biz.order.OrderBiz;
import com.tqmall.data.epc.client.bean.dto.order.OrderPayDTO;
import com.tqmall.data.epc.client.bean.param.order.PayCallbackParam;
import com.tqmall.data.epc.client.server.order.OrderPayService;
import com.tqmall.data.epc.common.bean.ConstantBean;
import com.tqmall.data.epc.common.bean.EpcError;
import com.tqmall.data.epc.common.utils.BdUtil;
import com.tqmall.data.epc.common.utils.JsonUtil;
import com.tqmall.data.epc.common.utils.ResultUtil;
import com.tqmall.data.epc.exterior.dubbo.uc.ShopInfoServiceExt;
import com.tqmall.ucenter.object.result.shop.ShopDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by huangzhangting on 16/8/31.
 */
@Slf4j
public class OrderPayServiceImpl implements OrderPayService {
    @Autowired
    private OrderBiz orderBiz;
    @Autowired
    private ShopInfoServiceExt shopInfoServiceExt;


    @Override
    public Result<OrderPayDTO> payCallback(PayCallbackParam param) {
        if(param==null){
            return ResultUtil.errorResult(EpcError.ARG_ERROR);
        }

        log.info("payCallback param:{}", JsonUtil.objectToJson(param));

        try {
            PayCallbackPO payCallbackPO = BdUtil.do2bo(param, PayCallbackPO.class);
            payCallbackPO.setPayment(BdUtil.do2bo(param.getPayment(), PaymentPO.class));

            Result<OrderBO> result = orderBiz.payCallback(payCallbackPO);
            if(result.isSuccess()){
                OrderBO orderBO = result.getData();
                OrderPayDTO payDTO = new OrderPayDTO();
                payDTO.setOrderId(orderBO.getId());
                payDTO.setOrderSn(orderBO.getOrderSn());
                payDTO.setTotalFee(orderBO.getOrderAmount());
                payDTO.setPayId(orderBO.getPayId());
                return ResultUtil.successResult(payDTO);
            }
            return ResultUtil.errorResult(result);
        }catch (Exception e){
            log.error("payCallback error, param:"+JsonUtil.objectToJson(param), e);
            return ResultUtil.handleException(e);
        }
    }

    @Override
    public Result<Integer> getUserIdByOrderSn(String orderSn) {
        try {
            Result<OrderBO> result = orderBiz.getOrderBySn(orderSn);
            if(result.isSuccess()){
                ShopDTO shopDTO = shopInfoServiceExt.getShopByShopId(
                        ConstantBean.APPLICATION_NAME, result.getData().getShopId());

                if(shopDTO == null){
                    return ResultUtil.errorResult("", "根据门店id，调用uc，查询用户id，失败");
                }
                return ResultUtil.successResult(shopDTO.getUserId());
            }
            return ResultUtil.errorResult(result);
        }catch (Exception e){
            log.error("getUserIdByOrderSn error, orderSn:"+orderSn, e);
            return ResultUtil.handleException(e);
        }
    }
}
