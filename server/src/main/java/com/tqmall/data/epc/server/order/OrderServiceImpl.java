package com.tqmall.data.epc.server.order;

import com.tqmall.core.common.entity.Result;
import com.tqmall.data.epc.bean.bizBean.order.*;
import com.tqmall.data.epc.biz.order.OrderBiz;
import com.tqmall.data.epc.biz.order.OrderGoodsBiz;
import com.tqmall.data.epc.client.bean.dto.order.OrderCancelLogDTO;
import com.tqmall.data.epc.client.bean.dto.order.OrderDetailDTO;
import com.tqmall.data.epc.client.bean.dto.order.OrderExtendDTO;
import com.tqmall.data.epc.client.bean.dto.order.OrderGoodsDTO;
import com.tqmall.data.epc.client.bean.param.order.ConfirmSettleParam;
import com.tqmall.data.epc.client.bean.param.order.ConfirmShippingParam;
import com.tqmall.data.epc.client.bean.param.order.OrderGoodsParam;
import com.tqmall.data.epc.client.server.order.OrderService;
import com.tqmall.data.epc.common.bean.EpcError;
import com.tqmall.data.epc.common.utils.BdUtil;
import com.tqmall.data.epc.common.utils.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * Created by huangzhangting on 16/8/26.
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderBiz orderBiz;
    @Autowired
    private OrderGoodsBiz orderGoodsBiz;


    @Override
    public Result<List<OrderGoodsDTO>> getGoodsByOrderSn(String orderSn) {

        try {
            Result<List<OrderGoodsBO>> result = orderBiz.getGoodsByOrderSn(orderSn);
            if (result.isSuccess()) {
                List<OrderGoodsDTO> orderGoodsDTOs = BdUtil.do2bo4List(result.getData(), OrderGoodsDTO.class);
                return Result.wrapSuccessfulResult(orderGoodsDTOs);
            }

            return ResultUtil.errorResult(EpcError.ARG_ERROR);
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            return ResultUtil.handleException(e);
        }
    }

    @Override
    public Result<OrderDetailDTO> getDetailByOrderSn(String orderSn, Integer sellerId) {
        try {
            Result<OrderDetailBO> result = orderBiz.getOrderDetailBySeller(orderSn, sellerId);

            if (result.isSuccess()) {
                OrderDetailBO orderDetailBO = result.getData();

                OrderDetailDTO orderDetailDTO = BdUtil.do2bo(orderDetailBO, OrderDetailDTO.class);

                //订单商品
                List<OrderGoodsBO> orderGoodsBOs = orderDetailBO.getOrderGoodsBOs();
                if (!CollectionUtils.isEmpty(orderGoodsBOs)) {
                    List<OrderGoodsDTO> orderGoodsDTOs = BdUtil.do2bo4List(orderGoodsBOs, OrderGoodsDTO.class);
                    orderDetailDTO.setOrderGoodsDTOList(orderGoodsDTOs);
                }

                //取消原因
                OrderCancelLogBO orderCancelLogBO = orderDetailBO.getOrderCancelLogBO();
                if (orderCancelLogBO != null) {
                    OrderCancelLogDTO orderCancelLogDTO = BdUtil.do2bo(orderCancelLogBO, OrderCancelLogDTO.class);
                    orderDetailDTO.setOrderCancelLogDTO(orderCancelLogDTO);
                }

                //发货信息
                OrderExtendBO orderExtendBO = orderDetailBO.getOrderExtendBO();
                if (orderExtendBO != null) {
                    OrderExtendDTO orderExtendDTO = BdUtil.do2bo(orderExtendBO, OrderExtendDTO.class);
                    orderDetailDTO.setOrderExtendDTO(orderExtendDTO);
                }

                return Result.wrapSuccessfulResult(orderDetailDTO);
            }

            return ResultUtil.errorResult(result);
        } catch (Exception e) {
            log.error("getDetailByOrderSn error, orderSn:" + orderSn + ", sellerId:" + sellerId, e);
            return ResultUtil.handleException(e);
        }
    }

    @Override
    public Result confirmSettle(ConfirmSettleParam param) {
        try {
            Result result = orderBiz.confirmSettle(param.getOrderSn(), param.getHasSettleAmount(), param.getOperator(), param.getOperatorSource());
            return result;

        }catch (Exception e){
            log.error(e.getMessage(),e);
            return ResultUtil.handleException(e);
        }
    }

    @Override
    public Result confirmShipping(ConfirmShippingParam param) {

        try {
            ConfirmShippingBO shippingBO = BdUtil.do2bo(param, ConfirmShippingBO.class);
            return orderBiz.confirmShipping(shippingBO);
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            return ResultUtil.handleException(e);
        }
    }

    @Override
    public Result modifyOrderPrice(String orderSn, Integer sellerId, List<OrderGoodsParam> paramList, String operator) {

        try {
            List<OrderGoodsBO> boList = BdUtil.do2bo4List(paramList, OrderGoodsBO.class);
            return orderGoodsBiz.modifyOrderPrice(orderSn,sellerId,boList, operator);
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            return ResultUtil.handleException(e);
        }

    }

}
