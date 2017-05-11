package com.tqmall.data.epc.biz.order;

import com.tqmall.core.common.entity.PagingResult;
import com.tqmall.core.common.entity.Result;
import com.tqmall.data.epc.bean.bizBean.order.*;
import com.tqmall.data.epc.bean.bizBean.sys.UserBO;
import com.tqmall.data.epc.bean.entity.baseConfig.EpcShippingConfigDO;
import com.tqmall.data.epc.bean.entity.order.*;
import com.tqmall.data.epc.bean.param.order.OrderListPageParam;
import com.tqmall.data.epc.bean.param.order.pay.PayCallbackPO;
import com.tqmall.data.epc.bean.param.order.pay.PaymentPO;
import com.tqmall.data.epc.biz.base.PayBiz;
import com.tqmall.data.epc.biz.base.config.ShippingConfigBiz;
import com.tqmall.data.epc.biz.chat.ChatBiz;
import com.tqmall.data.epc.biz.jindie.JinDieBiz;
import com.tqmall.data.epc.biz.shiro.ShiroBiz;
import com.tqmall.data.epc.common.bean.ConstantBean;
import com.tqmall.data.epc.common.bean.EpcError;
import com.tqmall.data.epc.common.bean.enums.OrderStatusEnums;
import com.tqmall.data.epc.common.bean.enums.SourceEnums;
import com.tqmall.data.epc.common.utils.*;
import com.tqmall.data.epc.dao.mapper.order.*;
import com.tqmall.data.epc.exterior.dubbo.base.RegionServiceExt;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by huangzhangting on 16/8/27.
 */
@Service
@Slf4j
public class OrderBizImpl implements OrderBiz {

    @Autowired
    private ShiroBiz shiroBiz;

    @Autowired
    private ShippingConfigBiz shippingConfigBiz;

    @Autowired
    private ChatBiz chatBiz;

    @Autowired
    private EpcOrderDOMapper orderDOMapper;
    @Autowired
    private EpcOrderExtendDOMapper extendDOMapper;
    @Autowired
    private EpcOrderCancelLogDOMapper orderCancelLogDOMapper;
    @Autowired
    private EpcOrderStatusLogDOMapper orderStatusLogDOMapper;
    @Autowired
    private EpcOrderGoodsDOMapper orderGoodsDOMapper;

    @Autowired
    private RegionServiceExt regionServiceExt;
    @Autowired
    private PayBiz payBiz;
    @Autowired
    private JinDieBiz jinDieBiz;

    @Override
    public Result<EpcOrderDO> getOrderByOrderSn(String orderSn) {
        EpcOrderDO searchOrderDO = new EpcOrderDO();
        searchOrderDO.setOrderSn(orderSn);
        List<EpcOrderDO> resultOrderList = orderDOMapper.selectByDO(searchOrderDO);
        if(CollectionUtils.isEmpty(resultOrderList)){
            return ResultUtil.errorResult(EpcError.NO_ORDER_ERROR);
        }
        EpcOrderDO epcOrderDO = resultOrderList.get(0);

        return ResultUtil.successResult(epcOrderDO);
    }

    @Override
    public PagingResult<OrderListShowBO> orderPaged(OrderListPageParam param) {
        if (param == null) {
            return ResultUtil.pageErrorResult(EpcError.ARG_ERROR);
        }
        Integer searchP = param.getSearchP();
        if (searchP == null || searchP < 1) {
            searchP = 1;
        }
        searchP = searchP - 1;

        String[] statusArray = null;
        String searchStatus = param.getSearchStatus();
        if (null != searchStatus) {
            searchStatus = searchStatus.replaceAll(" +", "").replace("，", ",");
            if (!"".equals(searchStatus)) {
                statusArray = searchStatus.split(",");
            }
        }

        Integer shopId = param.getShopId();
        if (null == shopId) {
            return ResultUtil.pageErrorResult(EpcError.ARG_ERROR);
        }

        int limit = ConstantBean.OFFER_PAGE_SIZE;
        // 查询获得列表
        List<EpcOrderDO> orderLimitList = orderDOMapper.selectListByParam(shopId, statusArray, searchP * limit, limit);
        int orderListSize = orderLimitList.size();
        if(orderListSize == 0){
            return ResultUtil.pageErrorResult(EpcError.NO_DATA_ERROR);
        }

        int sum = orderDOMapper.selectListCountByParam(shopId, statusArray);

        List<OrderListShowBO> showList = new ArrayList<>();
        for (int i = 0; i < orderListSize; i++) {
            EpcOrderDO orderDO = orderLimitList.get(i);
            String orderSn = orderDO.getOrderSn();
            EpcOrderGoodsDO searchDO = new EpcOrderGoodsDO();
            searchDO.setOrderSn(orderSn);
            List<EpcOrderGoodsDO> goodsList = orderGoodsDOMapper.selectByDO(searchDO);

            Integer orderStatus = orderDO.getOrderStatus();
            OrderListShowBO showBO = new OrderListShowBO();
            showBO.setCreateTime(DateUtil.dateToString(orderDO.getGmtCreate(), DateUtil.yyyy_MM_dd_HH_mm_ss));
            showBO.setOrderGoodsList(goodsList);
            showBO.setOrderSn(orderSn);
            showBO.setOrderStatus(orderStatus);
            //showBO.setOrderStatusName(OrderStatusEnums.codeName(orderStatus));
            showBO.setPayName(orderDO.getPayName());

            showBO.setOrderAmount(orderDO.getOrderAmount());
            showList.add(showBO);
        }

        return PagingResult.wrapSuccessfulResult(showList, sum);
    }

    //TODO 事物控制
    @Transactional
    @Override
    public Result cancelOrder(String orderSn, String cancelReason) {
        //检查入参合法性
        if (StringUtils.isEmpty(orderSn) || StringUtils.isEmpty(cancelReason)) {
            return ResultUtil.errorResult(EpcError.ARG_ERROR);
        }

        //判断用户是否登录
        UserBO user = shiroBiz.getCurrentUser();
        if (user == null) {
            return ResultUtil.errorResult(EpcError.NOT_LOGIN_ERROR);
        }

        //组装订单查询参数, 这里是订单编号
        EpcOrderDO paramDo = new EpcOrderDO();
        paramDo.setOrderSn(orderSn);
        paramDo.setShopId(user.getShopId());

        //根据订单编号获取订单
        List<EpcOrderDO> epcOrderDOs = orderDOMapper.selectByDO(paramDo);

        //该编号的订单不存在
        if (CollectionUtils.isEmpty(epcOrderDOs)) {
            log.info("the order is not in mysql.orderSn:{} ,shopId:{}",orderSn,user.getShopId());
            return ResultUtil.errorResult(EpcError.NO_ORDER_ERROR);
        }

        //结果列表第一个元素, 就是该订单
        EpcOrderDO epcOrderDO = epcOrderDOs.get(0);

        //只有订单状态为0(待付款)的订单才能取消
        if (!OrderStatusEnums.NOT_PAY.getCode().equals(epcOrderDO.getOrderStatus())) {
            log.info("can't confirmSettle,need 0.orderSn:{} ,orderStatus:{}");
            return ResultUtil.errorResult(EpcError.ORDER_STATUS_ERROR);
        }

        Integer orderId = epcOrderDO.getId();
        Date date = new Date();

        //更新订单状态为已取消
        EpcOrderDO orderDO = new EpcOrderDO();
        orderDO.setId(orderId);
        orderDO.setModifier(user.getAccountId());
        orderDO.setGmtModified(date);
        orderDO.setOrderStatus(OrderStatusEnums.CANCEL.getCode());
        orderDOMapper.updateByPrimaryKeySelective(orderDO);

        //插入订单取消记录
        EpcOrderCancelLogDO orderCancelLogDO = new EpcOrderCancelLogDO();
        orderCancelLogDO.setOperatorName(user.getRealName());
        orderCancelLogDO.setOperateTime(date);
        orderCancelLogDO.setOrderId(orderId);
        orderCancelLogDO.setOrderSn(orderSn);
        orderCancelLogDO.setCancelReason(cancelReason);
        orderCancelLogDOMapper.insert(orderCancelLogDO);

        //插入订单状态变化记录
        EpcOrderStatusLogDO orderStatusLogDO = new EpcOrderStatusLogDO();
        orderStatusLogDO.setOperatorName(user.getRealName());
        orderStatusLogDO.setOperateTime(date);
        orderStatusLogDO.setOrderId(orderId);
        orderStatusLogDO.setOrderSn(orderSn);
        orderStatusLogDO.setOrderStatus(OrderStatusEnums.CANCEL.getCode());
        orderStatusLogDO.setOrderStatusDesc(OrderStatusEnums.CANCEL.getName());
        orderStatusLogDOMapper.insertSelective(orderStatusLogDO);

        return Result.wrapSuccessfulResult(true);
    }

    //====================== START LYJ ======================//
    @Override
    public Result<List<OrderGoodsBO>> getGoodsByOrderSn(String orderSn) {
        //判断入参合法性
        if (StringUtils.isEmpty(orderSn)) {
            return ResultUtil.errorResult(EpcError.ARG_ERROR);
        }

        //构造入参对象
        EpcOrderGoodsDO epcOrderGoodsDO = new EpcOrderGoodsDO();
        epcOrderGoodsDO.setOrderSn(orderSn);

        List<EpcOrderGoodsDO> result = orderGoodsDOMapper.selectByDO(epcOrderGoodsDO);
        if (result.isEmpty()) {
            return ResultUtil.errorResult(EpcError.NO_DATA_ERROR);
        }

        return ResultUtil.successResult4List(result, OrderGoodsBO.class);
    }

    @Override
    public Result<OrderDetailBO> getOrderDetailByShop(String orderSn, Integer shopId) {
        //检查入参合法性
        if (StringUtils.isEmpty(orderSn) || shopId == null || shopId < 0) {
            return ResultUtil.errorResult(EpcError.ARG_ERROR);
        }

        //构造查询条件
        EpcOrderDO paramDo = new EpcOrderDO();
        paramDo.setOrderSn(orderSn);
        paramDo.setShopId(shopId);

        List<EpcOrderDO> epcOrderDOs = orderDOMapper.selectByDO(paramDo);
        if (CollectionUtils.isEmpty(epcOrderDOs)) {
            return ResultUtil.errorResult(EpcError.NO_ORDER_ERROR);
        } else {
            OrderDetailBO orderDetailBO = orderDetailDO2BO(epcOrderDOs.get(0));
            return Result.wrapSuccessfulResult(orderDetailBO);
        }
    }

    @Override
    public Result<OrderDetailBO> getOrderDetailBySeller(String orderSn, Integer sellerId) {
        //检查入参合法性
        if (StringUtils.isEmpty(orderSn) || sellerId == null || sellerId < 0) {
            log.info("arg_error=== orderSn:{},sellerId:{}",orderSn,sellerId);
            return ResultUtil.errorResult(EpcError.ARG_ERROR);
        }

        //构造查询条件
        EpcOrderDO paramDo = new EpcOrderDO();
        paramDo.setOrderSn(orderSn);
        paramDo.setSellerId(sellerId);

        List<EpcOrderDO> epcOrderDOs = orderDOMapper.selectByDO(paramDo);
        if (CollectionUtils.isEmpty(epcOrderDOs)) {
            return ResultUtil.errorResult(EpcError.NO_ORDER_ERROR);
        } else {
            OrderDetailBO orderDetailBO = orderDetailDO2BO(epcOrderDOs.get(0));
            return Result.wrapSuccessfulResult(orderDetailBO);
        }
    }

    @Override
    public Result<OrderBO> getOrderBySnAndShop(String orderSn, Integer shopId) {
        //检查入参合法性
        if (StringUtils.isEmpty(orderSn) || shopId == null || shopId < 0) {
            return ResultUtil.errorResult(EpcError.ARG_ERROR);
        }

        EpcOrderDO paramDo = new EpcOrderDO();
        paramDo.setOrderSn(orderSn);
        paramDo.setShopId(shopId);

        List<EpcOrderDO> epcOrderDOs = orderDOMapper.selectByDO(paramDo);
        if (CollectionUtils.isEmpty(epcOrderDOs)) {
            return ResultUtil.errorResult(EpcError.NO_ORDER_ERROR);
        } else {
            EpcOrderDO epcOrderDO = epcOrderDOs.get(0);

            //原始订单对象转为业务对象
            OrderBO orderBO = BdUtil.do2bo(epcOrderDO, OrderBO.class);

            return Result.wrapSuccessfulResult(orderBO);
        }
    }

    @Override
    public Result<OrderBO> getOrderBySn(String orderSn) {
        if(StringUtils.isEmpty(orderSn)){
            return ResultUtil.errorResult(EpcError.ARG_ERROR);
        }
        EpcOrderDO param = new EpcOrderDO();
        param.setOrderSn(orderSn);

        List<EpcOrderDO> list = orderDOMapper.selectByDO(param);
        if(list.isEmpty()){
            return ResultUtil.errorResult(EpcError.NO_ORDER_ERROR);
        }

        OrderBO detailBO = BdUtil.do2bo(list.get(0), OrderBO.class);
        if(detailBO==null){
            return ResultUtil.errorResult(EpcError.UN_KNOW_EXCEPTION);
        }

        return ResultUtil.successResult(detailBO);
    }

    //记得加上事物
    //// TODO: 16/8/31
    @Transactional
    @Override
    public Result confirmReceive(String orderSn) {
        //检查入参合法性
        if (StringUtils.isEmpty(orderSn)) {
            return ResultUtil.errorResult(EpcError.ARG_ERROR);
        }

        //判断用户是否登录
        UserBO user = shiroBiz.getCurrentUser();
        if (user == null) {
            return ResultUtil.errorResult(EpcError.NOT_LOGIN_ERROR);
        }

        //组装订单查询参数, 这里是订单编号
        EpcOrderDO paramDo = new EpcOrderDO();
        paramDo.setOrderSn(orderSn);
        paramDo.setShopId(user.getShopId());

        //根据订单编号获取订单
        List<EpcOrderDO> epcOrderDOs = orderDOMapper.selectByDO(paramDo);

        //该编号的订单不存在
        if (CollectionUtils.isEmpty(epcOrderDOs)) {
            return ResultUtil.errorResult("", "该订单不存在");
        }

        //结果列表第一个元素, 就是该订单
        EpcOrderDO epcOrderDO = epcOrderDOs.get(0);

        //只有订单状态为3(已发货)的订单才能执行确认收货
        if (!OrderStatusEnums.HAVE_SHIPMENTS.getCode().equals(epcOrderDO.getOrderStatus())) {
            return ResultUtil.errorResult("", "该状态的订单不可以确认收货!");
        }

        Integer orderId = epcOrderDO.getId();
        Date date = new Date();

        //更新订单状态为已签收
        EpcOrderDO orderDO = new EpcOrderDO();
        orderDO.setId(orderId);
        orderDO.setModifier(user.getAccountId());
        orderDO.setGmtModified(date);
        orderDO.setOrderStatus(OrderStatusEnums.HAVE_RECEIVE.getCode());
        orderDO.setSignTime(date);
        orderDO.setNeedSettleAmount(epcOrderDO.getOrderAmount());
        orderDOMapper.updateByPrimaryKeySelective(orderDO);

        //插入订单状态变化记录
        EpcOrderStatusLogDO orderStatusLogDO = new EpcOrderStatusLogDO();
        orderStatusLogDO.setOperatorName(user.getRealName());
        orderStatusLogDO.setOperateTime(date);
        orderStatusLogDO.setOrderId(orderId);
        orderStatusLogDO.setOrderSn(orderSn);
        orderStatusLogDO.setOrderStatus(OrderStatusEnums.HAVE_RECEIVE.getCode());
        orderStatusLogDO.setOrderStatusDesc(OrderStatusEnums.HAVE_RECEIVE.getName());
        orderStatusLogDOMapper.insertSelective(orderStatusLogDO);

        //进金蝶临时表
        jinDieBiz.insertDO(orderId, orderSn, epcOrderDO.getSellerId());

        //通知 已签收
        chatBiz.haveReceiveNotify(epcOrderDO.getShopId(), epcOrderDO.getSellerId(), orderSn);

        return Result.wrapSuccessfulResult(true);
    }

    @Override
    public Result setPayment(String orderSn, Integer payId) {
        //检查入参合法性
        if (StringUtils.isEmpty(orderSn) || payId == null || payId < 0) {
            return ResultUtil.errorResult(EpcError.ARG_ERROR);
        }

        //判断用户是否登录
        UserBO user = shiroBiz.getCurrentUser();
        if (user == null) {
            return ResultUtil.errorResult(EpcError.NOT_LOGIN_ERROR);
        }

        //组装订单查询参数, 这里是订单编号
        EpcOrderDO paramDo = new EpcOrderDO();
        paramDo.setOrderSn(orderSn);
        paramDo.setShopId(user.getShopId());

        //根据订单编号获取订单
        List<EpcOrderDO> epcOrderDOs = orderDOMapper.selectByDO(paramDo);

        //该编号的订单不存在
        if (CollectionUtils.isEmpty(epcOrderDOs)) {
            return ResultUtil.errorResult("", "该订单不存在");
        }

        //结果列表第一个元素, 就是该订单
        EpcOrderDO epcOrderDO = epcOrderDOs.get(0);

        //只有订单状态为0(未付款)的订单才能执行立即支付
        if (!OrderStatusEnums.NOT_PAY.getCode().equals(epcOrderDO.getOrderStatus())) {
            return ResultUtil.errorResult("", "该状态的订单不可以立即支付");
        }

        Integer orderId = epcOrderDO.getId();
        Date date = new Date();

        //更新订单
        EpcOrderDO orderDO = new EpcOrderDO();
        orderDO.setId(orderId);
        orderDO.setModifier(user.getAccountId());
        orderDO.setGmtModified(date);
        orderDO.setPayId(payId);
        String payName = payBiz.getPayName(payId);
        orderDO.setPayName(payName);
        orderDOMapper.updateByPrimaryKeySelective(orderDO);

        return Result.wrapSuccessfulResult(true);
    }

    @Override
    public Result<List<OrderDetailBO>> getOrderDetailList(List<String> orderSns, Integer shopId) {
        //检查入参合法性
        if (CollectionUtils.isEmpty(orderSns) || shopId == null || shopId < 0) {
            return ResultUtil.errorResult(EpcError.ARG_ERROR);
        }

        //最终返回的数据列表
        List<OrderDetailBO> orderDetailBOs = new ArrayList<>();

        for (String orderSn : orderSns) {
            EpcOrderDO paramDo = new EpcOrderDO();
            paramDo.setOrderSn(orderSn);
            paramDo.setShopId(shopId);

            List<EpcOrderDO> epcOrderDOs = orderDOMapper.selectByDO(paramDo);
            if (!CollectionUtils.isEmpty(epcOrderDOs)) {
                OrderDetailBO orderDetailBO = orderDetailDO2BO(epcOrderDOs.get(0));
                orderDetailBOs.add(orderDetailBO);
            }
        }

        if (orderDetailBOs.size() == 0) {
            return ResultUtil.errorResult(EpcError.NO_DATA_ERROR);
        }

        return Result.wrapSuccessfulResult(orderDetailBOs);

    }

    @Override
    public Result<List<OrderDetailBO>> getSiblingOrderDetailList(String orderSn, Integer shopId) {
        //检查入参合法性
        if (StringUtils.isEmpty(orderSn) || shopId == null || shopId < 0) {
            return ResultUtil.errorResult(EpcError.ARG_ERROR);
        }

        EpcOrderDO paramDo = new EpcOrderDO();
        paramDo.setOrderSn(orderSn);
        paramDo.setShopId(shopId);

        List<EpcOrderDO> epcOrderDOs = orderDOMapper.selectByDO(paramDo);
        if (CollectionUtils.isEmpty(epcOrderDOs)) {
            return ResultUtil.errorResult(EpcError.NO_ORDER_ERROR);
        }

        //最终返回的数据列表
        List<OrderDetailBO> orderDetailBOs = new ArrayList<>();

        /** 不存在拆单，直接返回，原单 */
        String parentOrderSn = epcOrderDOs.get(0).getParentOrderSn();
        if(StringUtils.isEmpty(parentOrderSn)){
            OrderDetailBO orderDetailBO = orderDetailDO2BO(epcOrderDOs.get(0));
            orderDetailBOs.add(orderDetailBO);
            return ResultUtil.successResult(orderDetailBOs);
        }

        /** 发生拆单，则全部返回 */
        EpcOrderDO tempParamDo = new EpcOrderDO();
        tempParamDo.setShopId(shopId);
        tempParamDo.setParentOrderSn(parentOrderSn);

        List<EpcOrderDO> epcOrderDOsByParentOrder = orderDOMapper.selectByDO(tempParamDo);
        for (EpcOrderDO epcOrderDO : epcOrderDOsByParentOrder) {
            OrderDetailBO orderDetailBO = orderDetailDO2BO(epcOrderDO);
            orderDetailBOs.add(orderDetailBO);
        }

        /** 未支付的放前面 */
        Collections.sort(orderDetailBOs, new Comparator<OrderDetailBO>() {
            @Override
            public int compare(OrderDetailBO o1, OrderDetailBO o2) {
                return o1.getPayStatus().compareTo(o2.getPayStatus());
            }
        });

        return ResultUtil.successResult(orderDetailBOs);
    }

    @Override
    public Result<Integer> getNoPayOrderCount(String orderSn, Integer shopId) {
        //检查入参合法性
        if (StringUtils.isEmpty(orderSn) || shopId == null || shopId < 0) {
            return ResultUtil.errorResult(EpcError.ARG_ERROR);
        }

        EpcOrderDO paramDo = new EpcOrderDO();
        paramDo.setOrderSn(orderSn);
        paramDo.setShopId(shopId);

        List<EpcOrderDO> epcOrderDOs = orderDOMapper.selectByDO(paramDo);
        if (CollectionUtils.isEmpty(epcOrderDOs)) {
            return ResultUtil.errorResult(EpcError.NO_ORDER_ERROR);
        }

        String parentOrderSn = epcOrderDOs.get(0).getParentOrderSn();
        if(StringUtils.isEmpty(parentOrderSn)){
            return ResultUtil.errorResult(EpcError.NO_DATA_ERROR);
        }

        EpcOrderDO tempParamDo = new EpcOrderDO();
        tempParamDo.setShopId(shopId);
        tempParamDo.setParentOrderSn(parentOrderSn);

        List<EpcOrderDO> epcOrderDOsByParentOrder = orderDOMapper.selectByDO(tempParamDo);
        if (CollectionUtils.isEmpty(epcOrderDOsByParentOrder)) {
            return ResultUtil.errorResult(EpcError.NO_ORDER_ERROR);
        }

        int count = 0;
        for (EpcOrderDO epcOrderDO : epcOrderDOsByParentOrder) {
            if (ConstantBean.NO_STATUS.equals(epcOrderDO.getPayStatus())) {
                count++;
            }
        }
        if(count==0){
            return ResultUtil.errorResult(EpcError.NO_DATA_ERROR);
        }
        return ResultUtil.successResult(count);
    }

    @Override
    public Result<Integer> getNoPaySiblingOrderCount(String parentOrderSn, Integer shopId) {
        if(StringUtils.isEmpty(parentOrderSn) || shopId==null || shopId<1){
            return ResultUtil.errorResult(EpcError.ARG_ERROR);
        }

        EpcOrderDO paramDo = new EpcOrderDO();
        paramDo.setShopId(shopId);
        paramDo.setParentOrderSn(parentOrderSn);

        List<EpcOrderDO> epcOrderDOsByParentOrder = orderDOMapper.selectByDO(paramDo);
        if (epcOrderDOsByParentOrder.isEmpty()) {
            return ResultUtil.errorResult(EpcError.NO_ORDER_ERROR);
        }

        int count = 0;
        for (EpcOrderDO epcOrderDO : epcOrderDOsByParentOrder) {
            if (ConstantBean.NO_STATUS.equals(epcOrderDO.getPayStatus())) {
                count++;
            }
        }
        if(count==0){
            return ResultUtil.errorResult(EpcError.NO_DATA_ERROR);
        }
        return ResultUtil.successResult(count);
    }

    @Override
    public Result<List<EpcOrderDO>> getNeedAutoSignOrder(String startTime, String endTime) {
        if (StringUtils.isEmpty(startTime) || StringUtils.isEmpty(endTime)){
            return ResultUtil.errorResult(EpcError.ARG_ERROR);
        }

        List<EpcOrderDO> epcOrderDOs = orderDOMapper.selectNeedAutoSignOrder(startTime, endTime);
        if (epcOrderDOs.isEmpty()) {
            return ResultUtil.errorResult(EpcError.NO_DATA_ERROR);
        }

        return ResultUtil.successResult(epcOrderDOs);
    }


    /**
     * 获取订单详情
     * @param epcOrderDO
     * @return
     */
    private OrderDetailBO orderDetailDO2BO(EpcOrderDO epcOrderDO) {
        String orderSn = epcOrderDO.getOrderSn();

        //获取地址ID
        Integer provinceId = epcOrderDO.getProvince();
        Integer cityId = epcOrderDO.getCity();
        Integer districtId = epcOrderDO.getDistrict();
        Integer streetId = epcOrderDO.getStreet();

        //根据地址ID组成的列表, 一次性获取map
        List<Integer> regionIds = new ArrayList<>();
        regionIds.add(provinceId);
        regionIds.add(cityId);
        regionIds.add(districtId);
        regionIds.add(streetId);
        Map<Integer, String> regionNameMap = regionServiceExt.getRegionNameMap(regionIds);

        //原始订单对象转为业务对象
        OrderDetailBO orderDetailBO = BdUtil.do2bo(epcOrderDO, OrderDetailBO.class);

        //根据地址ID, 生成地址信息字符串
        String name = regionNameMap.get(provinceId);
        orderDetailBO.setProvinceName(name == null ? "" : name);
        name = regionNameMap.get(cityId);
        orderDetailBO.setCityName(name == null ? "" : name);
        name = regionNameMap.get(districtId);
        orderDetailBO.setDistrictName(name == null ? "" : name);
        name = regionNameMap.get(streetId);
        orderDetailBO.setStreetName(name == null ? "" : name);

        //获取订单商品列表
        EpcOrderGoodsDO epcOrderGoodsDO = new EpcOrderGoodsDO();
        epcOrderGoodsDO.setOrderSn(orderSn);
        List<EpcOrderGoodsDO> epcOrderGoodsDOs = orderGoodsDOMapper.selectByDO(epcOrderGoodsDO);

        List<OrderGoodsBO> orderGoodsBOs = null;
        if (!CollectionUtils.isEmpty(epcOrderGoodsDOs)) {
            orderGoodsBOs = BdUtil.do2bo4List(epcOrderGoodsDOs, OrderGoodsBO.class);
        }
        orderDetailBO.setOrderGoodsBOs(orderGoodsBOs);

        //表示已经取消了的订单(那么就要获取取消原因了)
        if (OrderStatusEnums.CANCEL.getCode().equals(orderDetailBO.getOrderStatus())) {
            EpcOrderCancelLogDO epcOrderCancelLogDO = orderCancelLogDOMapper.selectByOrderSn(orderSn);
            OrderCancelLogBO orderCancelLogBO = BdUtil.do2bo(epcOrderCancelLogDO, OrderCancelLogBO.class);
            orderDetailBO.setOrderCancelLogBO(orderCancelLogBO);
        }

        //表示已发货、已签收、已结算的订单(那么就要获取发货信息了)
        if (OrderStatusEnums.HAVE_SHIPMENTS.getCode().equals(orderDetailBO.getOrderStatus())
                || OrderStatusEnums.HAVE_RECEIVE.getCode().equals(orderDetailBO.getOrderStatus())
                || OrderStatusEnums.HAVE_SETTLE.getCode().equals(orderDetailBO.getOrderStatus())) {
            EpcOrderExtendDO epcOrderExtendDO = extendDOMapper.selectByOrderSn(orderSn);
            OrderExtendBO orderExtendBO = BdUtil.do2bo(epcOrderExtendDO, OrderExtendBO.class);
            orderDetailBO.setOrderExtendBO(orderExtendBO);
        }

        return orderDetailBO;
    }

    @Transactional
    @Override
    public Result autoConfirmReceive(String orderSn) {
        //检查入参合法性
        if (StringUtils.isEmpty(orderSn)) {
            return ResultUtil.errorResult(EpcError.ARG_ERROR);
        }

        //组装订单查询参数, 这里是订单编号
        EpcOrderDO paramDo = new EpcOrderDO();
        paramDo.setOrderSn(orderSn);

        //根据订单编号获取订单
        List<EpcOrderDO> epcOrderDOs = orderDOMapper.selectByDO(paramDo);

        //该编号的订单不存在
        if (CollectionUtils.isEmpty(epcOrderDOs)) {
            return ResultUtil.errorResult(EpcError.NO_DATA_ERROR);
        }

        //结果列表第一个元素, 就是该订单
        EpcOrderDO epcOrderDO = epcOrderDOs.get(0);

        //只有订单状态为3(已发货)的订单才能执行确认收货
        if (!OrderStatusEnums.HAVE_SHIPMENTS.getCode().equals(epcOrderDO.getOrderStatus())) {
            return ResultUtil.errorResult("", "该状态不能执行自动签收");
        }

        Integer orderId = epcOrderDO.getId();
        Date date = new Date();

        //更新订单状态为已签收
        EpcOrderDO orderDO = new EpcOrderDO();
        orderDO.setId(orderId);
        orderDO.setModifier(0);
        orderDO.setGmtModified(date);
        orderDO.setOrderStatus(OrderStatusEnums.HAVE_RECEIVE.getCode());
        orderDO.setSignTime(epcOrderDO.getAutoSignTime());
        orderDO.setNeedSettleAmount(epcOrderDO.getOrderAmount());
        orderDOMapper.updateByPrimaryKeySelective(orderDO);

        //插入订单状态变化记录
        EpcOrderStatusLogDO orderStatusLogDO = new EpcOrderStatusLogDO();
        orderStatusLogDO.setOperatorName("epc");
        orderStatusLogDO.setOperateTime(date);
        orderStatusLogDO.setOrderId(orderId);
        orderStatusLogDO.setOrderSn(orderSn);
        orderStatusLogDO.setOrderStatus(OrderStatusEnums.HAVE_RECEIVE.getCode());
        orderStatusLogDO.setOrderStatusDesc(OrderStatusEnums.HAVE_RECEIVE.getName());
        orderStatusLogDOMapper.insertSelective(orderStatusLogDO);

        //进金蝶临时表
        jinDieBiz.insertDO(orderId, orderSn, epcOrderDO.getSellerId());

        return ResultUtil.successResult(1);
    }

    //====================== END LYJ ======================//

    @Transactional
    @Override
    public Result confirmSettle(String orderSn, BigDecimal hasSettleAmount, String operator, Integer operatorSource) {
        //判断参数
        if(StringUtils.isEmpty(orderSn) ||StringUtils.isEmpty(operator) || operatorSource == null ||hasSettleAmount == null || BigDecimal.ZERO.compareTo(hasSettleAmount) > -1){
            log.info("arg error--orderSn:{},hasSettleAmount:{},operator:{},operatorSource:{}",orderSn,hasSettleAmount,operator,operatorSource);
            return ResultUtil.errorResult(EpcError.ARG_ERROR);
        }
        //来源非定义的三个
        if(!SourceEnums.isInEnums(operatorSource)){
            log.info("operatorSource error--orderSn:{},operatorSource:{}",orderSn,operatorSource);
            return ResultUtil.errorResult(EpcError.OPERATOR_SOURCE_ERROR);
        }

        //根据订单编号获取订单
        Result<EpcOrderDO> epcOrderDOResult = getOrderByOrderSn(orderSn);
        //该编号的订单不存在
        if (!epcOrderDOResult.isSuccess()) {
            log.info("from sql,EpcOrderDO is null.orderSn:{}",orderSn);
            return epcOrderDOResult;
        }
        EpcOrderDO epcOrderDO = epcOrderDOResult.getData();

        //结算金额<=需要结算的金额
        BigDecimal needSettleAmount = epcOrderDO.getNeedSettleAmount();
        if(needSettleAmount.compareTo(hasSettleAmount)==-1){
            log.info("needSettleAmount:{} < hasSettleAmount:{}",needSettleAmount,hasSettleAmount);
            return ResultUtil.errorResult(EpcError.AMOUNT_ERROR);
        }
        //如果状态为非4 只有 4:已签收 状态的可以结算
        Integer nowOrderStatus = epcOrderDO.getOrderStatus();
        if(!nowOrderStatus.equals(OrderStatusEnums.HAVE_RECEIVE.getCode())){
            log.info("can't confirmSettle,need 4.orderSn:{} orderStatus:{} ",orderSn,nowOrderStatus);
            return ResultUtil.errorResult(EpcError.ORDER_STATUS_ERROR);
        }
        //设置订单状态为结算金额状态
        EpcOrderDO updateDO = new EpcOrderDO();
        updateDO.setId(epcOrderDO.getId());
        updateDO.setOrderStatus(OrderStatusEnums.HAVE_SETTLE.getCode());
        updateDO.setSettleStatus(ConstantBean.YES_STATUS);
        updateDO.setHasSettleAmount(hasSettleAmount);
        updateDO.setSettleTime(new Date());

        orderDOMapper.updateByPrimaryKeySelective(updateDO);

        //记录状态更改日志
        EpcOrderStatusLogDO statusLogDO = new EpcOrderStatusLogDO();
        statusLogDO.setOperatorName(operator);
        statusLogDO.setOperatorSource(operatorSource);
        statusLogDO.setOrderId(epcOrderDO.getId());
        statusLogDO.setOrderSn(epcOrderDO.getOrderSn());
        statusLogDO.setOrderStatus(OrderStatusEnums.HAVE_SETTLE.getCode());
        statusLogDO.setOrderStatusDesc(OrderStatusEnums.HAVE_SETTLE.getName());

        orderStatusLogDOMapper.insertSelective(statusLogDO);

        return ResultUtil.successResult(orderSn);
    }

    @Transactional
    @Override
    public Result confirmShipping(ConfirmShippingBO confirmShippingBO) {
        Integer sellerId = confirmShippingBO.getSellerId();
        String orderSn = confirmShippingBO.getOrderSn();
        Integer shippingId = confirmShippingBO.getShippingId();
        String operator = confirmShippingBO.getOperator();
        Integer operatorSource = confirmShippingBO.getOperatorSource();
        //判断参数
        if(StringUtils.isEmpty(orderSn) ||StringUtils.isEmpty(operator) || operatorSource == null || sellerId == null || shippingId == null){
            log.info("arg error--orderSn:{},sellerId:{},shippingId:{},operator:{},operatorSource:{}",orderSn,sellerId,shippingId,operator,operatorSource);
            return ResultUtil.errorResult(EpcError.ARG_ERROR);
        }
        //来源非定义的三个
        if(!SourceEnums.isInEnums(operatorSource)){
            log.info("operatorSource error--orderSn:{},operatorSource:{}",orderSn,operatorSource);
            return ResultUtil.errorResult(EpcError.OPERATOR_SOURCE_ERROR);
        }
        //判断是否有此发货的信息
        EpcShippingConfigDO configDO = shippingConfigBiz.getByPrimaryId(shippingId);
        if(null == configDO){
            log.info("not have this shippingId in mysql.orderSn:{},shippingId:{}",orderSn,shippingId);
            return ResultUtil.errorResult(EpcError.SHIPPING_ERROR);
        }

        //根据订单编号获取订单
        Result<EpcOrderDO> epcOrderDOResult = getOrderByOrderSn(orderSn);
        //该编号的订单不存在
        if (!epcOrderDOResult.isSuccess()) {
            log.info("from sql,EpcOrderDO is null.orderSn:{}",orderSn);
            return epcOrderDOResult;
        }
        EpcOrderDO epcOrderDO = epcOrderDOResult.getData();

        //判断是否是 这个 seller
        Integer oldSellerId = epcOrderDO.getSellerId();
        if(null == oldSellerId || !sellerId.equals(oldSellerId)){
            log.info("orderSn:{},sellerId:{} oldSellerId:{} == not equal",orderSn,sellerId,oldSellerId);
            return ResultUtil.errorResult(EpcError.ORDER_SELLER_ERROR);
        }
        //如果状态为非2 只有 2:已付款 状态的可以结算
        Integer nowOrderStatus = epcOrderDO.getOrderStatus();
        if(!nowOrderStatus.equals(OrderStatusEnums.HAVE_PAY.getCode())){
            log.info("can't confirmSettle,need 2.orderSn:{} orderStatus:{}",orderSn,nowOrderStatus);
            return ResultUtil.errorResult(EpcError.ORDER_STATUS_ERROR);
        }
        Integer orderId = epcOrderDO.getId();
        //设置订单状态为发货状态
        Date now = new Date();
        DateTime dateTime = new DateTime(now.getTime());
        DateTime autoSignDateTime = dateTime.plusDays(15);//15天后 自动收货


        EpcOrderDO updateDO = new EpcOrderDO();
        updateDO.setId(epcOrderDO.getId());
        updateDO.setModifier(sellerId);
        updateDO.setOrderStatus(OrderStatusEnums.HAVE_SHIPMENTS.getCode());
        updateDO.setShippingStatus(ConstantBean.YES_STATUS);
        updateDO.setShippingId(shippingId);
        updateDO.setShippingName(configDO.getShippingName());
        updateDO.setSellerOrderNote(confirmShippingBO.getSellerOrderNote());
        updateDO.setShippingTime(now);
        updateDO.setAutoSignTime(autoSignDateTime.toDate());


        orderDOMapper.updateByPrimaryKeySelective(updateDO);
        //记录 发货详细内容
        String company = confirmShippingBO.getShippingCompany();
        String shippingNo = confirmShippingBO.getShippingNo();
        if(!StringUtils.isEmpty(company) || !StringUtils.isEmpty(shippingNo)) {
            EpcOrderExtendDO extendDO = new EpcOrderExtendDO();
            extendDO.setCreator(sellerId);
            extendDO.setOrderId(orderId);
            extendDO.setOrderSn(orderSn);
            extendDO.setShippingCompany(company);
            extendDO.setShippingNo(shippingNo);

            extendDOMapper.insertSelective(extendDO);
        }

        //记录状态更改日志
        EpcOrderStatusLogDO statusLogDO = new EpcOrderStatusLogDO();
        statusLogDO.setOperatorName(operator);
        statusLogDO.setOperatorSource(operatorSource);
        statusLogDO.setOrderId(orderId);
        statusLogDO.setOrderSn(orderSn);
        statusLogDO.setOrderStatus(OrderStatusEnums.HAVE_SHIPMENTS.getCode());
        statusLogDO.setOrderStatusDesc(OrderStatusEnums.HAVE_SHIPMENTS.getName());

        orderStatusLogDOMapper.insertSelective(statusLogDO);

        //通知发货啦
        chatBiz.haveShippingNotify(epcOrderDO.getShopId(),sellerId,orderSn);

        return ResultUtil.successResult(orderSn);
    }

    @Transactional
    @Override
    public Result<OrderBO> payCallback(PayCallbackPO param) {
        if(param==null){
            return ResultUtil.errorResult(EpcError.ARG_ERROR);
        }
        if(!param.isSuccess()){
            log.info("pay failed, param:{}", JsonUtil.objectToJson(param));
            return ResultUtil.errorResult("", "订单支付失败");
        }
        log.info("pay call back, param:{}", JsonUtil.objectToJson(param));
        if(StringUtils.isEmpty(param.getOrderSn()) || StringUtils.isEmpty(param.getTradeNo())){
            return ResultUtil.errorResult(EpcError.ARG_ERROR);
        }
        if(param.getTotalFee()==null || param.getTotalFee().compareTo(BigDecimal.ZERO)<=0){
            return ResultUtil.errorResult(EpcError.ARG_ERROR);
        }
        PaymentPO paymentPO = param.getPayment();
//        if(paymentPO==null || paymentPO.getPayId()==null || paymentPO.getPayId()<1){
//            return ResultUtil.errorResult(EpcError.ARG_ERROR);
//        }

        EpcOrderDO orderParam = new EpcOrderDO();
        orderParam.setOrderSn(param.getOrderSn());

        List<EpcOrderDO> orderDOList = orderDOMapper.selectByDO(orderParam);
        if(orderDOList.isEmpty()){
            String msg = "订单不存在，orderSn："+param.getOrderSn();
            log.info("pay call back, failed, msg:{}", msg);

            return ResultUtil.errorResult("", msg);
        }

        EpcOrderDO orderDO = orderDOList.get(0);

        /** 因为存在多次调用的情况，如果已经回调成功了，就直接返回数据 */
        if(!OrderStatusEnums.NOT_PAY.getCode().equals(orderDO.getOrderStatus())){
            StringBuilder msg = new StringBuilder();
            msg.append("订单状态为：").append(OrderStatusEnums.codeName(orderDO.getOrderStatus()))
                    .append("，不能执行支付，orderSn：").append(param.getOrderSn());

            log.info("pay call back, failed with status, msg:{}", msg.toString());

            /** 组装返回对象 */
            OrderBO orderBO = BdUtil.do2bo(orderDO, OrderBO.class);
            if(paymentPO!=null && paymentPO.getPayId()!=null && paymentPO.getPayId()>0) {
                orderBO.setPayId(paymentPO.getPayId());
            }

            return ResultUtil.successResult(orderBO);
        }

        if(param.getTotalFee().compareTo(orderDO.getOrderAmount()) < 0){
            StringBuilder msg = new StringBuilder();
            msg.append("支付金额：").append(param.getTotalFee())
                    .append("，小于订单金额：").append(orderDO.getOrderAmount())
                    .append("，不能执行支付，orderSn：").append(param.getOrderSn());

            log.info("pay call back, failed with fee, msg:{}", msg.toString());
            return ResultUtil.errorResult("", msg.toString());
        }

        /** 组装返回对象 */
        OrderBO orderBO = BdUtil.do2bo(orderDO, OrderBO.class);

        /** 更新订单 */
        Date date = new Date();
        EpcOrderDO modifyOrder = new EpcOrderDO();
        modifyOrder.setId(orderDO.getId());
        modifyOrder.setGmtModified(date);
        modifyOrder.setPayNo(param.getTradeNo());
        modifyOrder.setOrderStatus(OrderStatusEnums.HAVE_PAY.getCode());
        modifyOrder.setPayStatus(ConstantBean.YES_STATUS);
        modifyOrder.setPayTime(date);
        if(paymentPO!=null && paymentPO.getPayId()!=null && paymentPO.getPayId()>0){
            orderBO.setPayId(paymentPO.getPayId());
            modifyOrder.setPayId(paymentPO.getPayId());
            String payName = payBiz.getPayName(paymentPO.getPayId());
            if(!StringUtils.isEmpty(payName)){
                modifyOrder.setPayName(payName);
            }else{
                if(!StringUtils.isEmpty(paymentPO.getPayName())){
                    modifyOrder.setPayName(paymentPO.getPayName());
                }
            }
        }
        orderDOMapper.updateByPrimaryKeySelective(modifyOrder);

        /** 记录状态更改日志 */
        EpcOrderStatusLogDO statusLogDO = new EpcOrderStatusLogDO();
        statusLogDO.setOperateTime(date);
        statusLogDO.setOperatorName("finance|epc");
        statusLogDO.setOrderId(orderDO.getId());
        statusLogDO.setOrderSn(param.getOrderSn());
        statusLogDO.setOrderStatus(OrderStatusEnums.HAVE_PAY.getCode());
        statusLogDO.setOrderStatusDesc(OrderStatusEnums.HAVE_PAY.getName());
        statusLogDO.setLogContent("门店:"+orderDO.getShopId()+" 支付金额:"+param.getTotalFee());

        orderStatusLogDOMapper.insertSelective(statusLogDO);

        return ResultUtil.successResult(orderBO);
    }


}
