package com.tqmall.data.epc.biz.jindie;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import com.tqmall.core.common.entity.Result;
import com.tqmall.data.epc.bean.bizBean.base.RegionBO;
import com.tqmall.data.epc.bean.bizBean.order.OrderGoodsBO;
import com.tqmall.data.epc.bean.bizBean.shop.ShopBO;
import com.tqmall.data.epc.bean.bizBean.sys.UserBO;
import com.tqmall.data.epc.bean.entity.jindie.EpcJinDieTaskDO;
import com.tqmall.data.epc.bean.entity.order.EpcOrderDO;
import com.tqmall.data.epc.biz.order.OrderBiz;
import com.tqmall.data.epc.biz.shiro.ShiroBiz;
import com.tqmall.data.epc.common.bean.enums.OrderStatusEnums;
import com.tqmall.data.epc.common.utils.DateUtil;
import com.tqmall.data.epc.dao.mapper.jindie.EpcJinDieTaskDOMapper;
import com.tqmall.data.epc.exterior.dubbo.base.RegionServiceExt;
import com.tqmall.data.epc.exterior.dubbo.jindie.JinDieExt;
import com.tqmall.data.epc.exterior.dubbo.stall.SellerServiceExt;
import com.tqmall.redarrow.client.param.*;
import com.tqmall.tqmallstall.domain.result.seller.SellerDTO;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by zxg on 16/9/1.
 * 16:36
 * no bug,以后改代码的哥们，祝你好运~！！
 */
@Service
@Slf4j
public class JinDieBizImpl implements JinDieBiz {

    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;

    @Autowired
    private ShiroBiz shiroBiz;

    @Autowired
    private OrderBiz orderBiz;

    @Autowired
    private JinDieExt jinDieExt;
    @Autowired
    private SellerServiceExt sellerServiceExt;
    @Autowired
    private RegionServiceExt regionServiceExt;

    @Autowired
    private EpcJinDieTaskDOMapper jinDieTaskDOMapper;

    @Override
    public List<EpcJinDieTaskDO> getListByResultStatus(Integer resultStatus) {
        if(null == resultStatus || resultStatus < 0){
            return Lists.newArrayList();
        }

        EpcJinDieTaskDO searchDO = new EpcJinDieTaskDO();
        searchDO.setResultStatus(resultStatus);
        return jinDieTaskDOMapper.selectByDO(searchDO);
    }

    @Override
    public Boolean insertDO(Integer orderId, String orderSn, Integer orgId) {
        if(orderId == null || StringUtils.isEmpty(orderSn) || orgId == null){
            log.info("args is wrong. orderId:{},orderSn:{},ogrId:{}",orderId,orderSn,orderId);
            return false;
        }
        UserBO userBO = shiroBiz.getCurrentUser();
        if(null == userBO){
            log.info("session is over.orderSn:{},ogrId:{}",orderSn,orgId);
            return false;
        }
        ShopBO shopBO = userBO.getShopBO();
        if(null == shopBO){
            log.info("shopBO is null.orderSn:{},ogrId:{}",orderSn,orgId);
            return false;
        }

        Result<EpcOrderDO> result = orderBiz.getOrderByOrderSn(orderSn);
        if(!result.isSuccess()){
            log.info("orderSn not in mysql.orderSn:{}",orderSn);
            return false;
        }
        EpcOrderDO epcOrderDO = result.getData();

        //判断是否是 这个 seller
        Integer oldSellerId = epcOrderDO.getSellerId();
        if(null == oldSellerId || !orgId.equals(oldSellerId)){
            log.info("orderSn:{},sellerId:{} oldSellerId:{} == not equal",orderSn,orgId,oldSellerId);
            return false;
        }
        //如果状态为非0:未付款 1:已取消 2:已付款 3:已发货, 只有 4:已签收 5:已结算 状态的可以结算
        Integer nowOrderStatus = epcOrderDO.getOrderStatus();
        if(!nowOrderStatus.equals(OrderStatusEnums.HAVE_RECEIVE.getCode()) && !nowOrderStatus.equals(OrderStatusEnums.HAVE_SETTLE.getCode())){
            log.info("orderSn:{} orderStatus:{} ==can't confirmSettle,need 4/5",orderSn,nowOrderStatus);
            return false;
        }


        String shopNumber = shopBO.getClientNum();
        //调用接口获得供应商编码 erp_sn
        SellerDTO sellerDTO = sellerServiceExt.getSellerByOrgId(orgId);
        if(null == sellerDTO){
            log.info("orgId:{} ==can't get SellerDTO from stall",orgId);
            return false;
        }
        String sellerNumber = sellerDTO.getErpSn();
//        String sellerNumber = "";

        EpcJinDieTaskDO jinDieTaskDO = new EpcJinDieTaskDO();
        jinDieTaskDO.setOrderId(orderId);
        jinDieTaskDO.setOrderSn(orderSn);
        jinDieTaskDO.setShopNumber(shopNumber);
        jinDieTaskDO.setSellerNumber(sellerNumber);
        jinDieTaskDOMapper.insertSelective(jinDieTaskDO);

        // 防止 task 干扰，此时不进金蝶开个多线程执行进金蝶
//        ToJinDieProcess toJinDieProcess = new ToJinDieProcess(jinDieTaskDO);
//        taskExecutor.execute(toJinDieProcess);
        return true;
    }


    // 更新错误原因
    @Override
    public Boolean updateTaskFail(Integer primaryId, String failReason){
        EpcJinDieTaskDO upJinDieDO = new EpcJinDieTaskDO();
        upJinDieDO.setId(primaryId);
        upJinDieDO.setFailReason(failReason);
        jinDieTaskDOMapper.updateByPrimaryKeySelective(upJinDieDO);
        return true;
    }

    @Override
    public Boolean toJinDieReport(EpcJinDieTaskDO jinDieTaskDO) {
        Integer jinDiePrimaryId = jinDieTaskDO.getId();
        if(null == jinDiePrimaryId){
            return false;
        }
        Integer nowTaskStatus = jinDieTaskDO.getTaskStatus();
        Integer resultTaskStatus = jinDieTaskDO.getResultStatus();
        //成功或失败的 不进行进金蝶任务，只有 new 的可以进金蝶
        if(!resultTaskStatus.equals(EpcJinDieTaskDO.RESULT_STATUS_NEW)){
            return false;
        }
        //获得原始数据值
        String orderSn = jinDieTaskDO.getOrderSn();
        Result<EpcOrderDO> epcOrderDOResult = orderBiz.getOrderByOrderSn(orderSn);
        if(!epcOrderDOResult.isSuccess()){
            log.info("orderSn not in epc_order mysql.orderSn:{}",orderSn);
            return false;
        }
        EpcOrderDO epcOrderDO= epcOrderDOResult.getData();

        Result<List<OrderGoodsBO>> orderGoodsBOResult = orderBiz.getGoodsByOrderSn(orderSn);
        if(!orderGoodsBOResult.isSuccess()){
            log.info("orderSn not in epc_order_goods mysql.orderSn:{}",orderSn);
            return false;
        }
        List<OrderGoodsBO> orderGoodsBOList = orderGoodsBOResult.getData();
        //更新次数
        EpcJinDieTaskDO upJinDieDO = new EpcJinDieTaskDO();
        upJinDieDO.setId(jinDiePrimaryId);
        upJinDieDO.setHandleTimes(jinDieTaskDO.getHandleTimes() + 1);
        jinDieTaskDOMapper.updateByPrimaryKeySelective(upJinDieDO);
        // 1.客户+商品
        if(nowTaskStatus.equals(EpcJinDieTaskDO.TSK_STATUS_NEW)){
            nowTaskStatus = ReadyAboutGoodsAndCustomers(jinDieTaskDO, epcOrderDO, orderGoodsBOList);
        }
        // 2.销售单
        if(nowTaskStatus.equals(EpcJinDieTaskDO.TSK_STATUS_DATA)){
            nowTaskStatus = CreateSaleOrder(jinDieTaskDO, epcOrderDO, orderGoodsBOList);
        }
        // 3.采购单
        if(nowTaskStatus.equals(EpcJinDieTaskDO.TSK_STATUS_SALE)){
            nowTaskStatus = CreatePurchaseOrder(jinDieTaskDO, epcOrderDO, orderGoodsBOList);
        }
        // 4.入库单
        if(nowTaskStatus.equals(EpcJinDieTaskDO.TSK_STATUS_PURCHASE)){
            nowTaskStatus = CreateInWareOrder(jinDieTaskDO, epcOrderDO, orderGoodsBOList);
        }
        // 5.发票
        if(nowTaskStatus.equals(EpcJinDieTaskDO.TSK_STATUS_IN_WARE)){
            nowTaskStatus = CreateInvoice(jinDieTaskDO, epcOrderDO, orderGoodsBOList);
        }
        // 6.出库单,大于入库单或者发票，即可出库
        if(nowTaskStatus.equals(EpcJinDieTaskDO.TSK_STATUS_INVOICE) ){
            nowTaskStatus = CreateOutWareOrder(jinDieTaskDO, epcOrderDO, orderGoodsBOList);
        }

        if(nowTaskStatus.equals(EpcJinDieTaskDO.TSK_STATUS_FAIL)){
            return false;
        }
        return true;
    }


    //多线程进金蝶
    private class ToJinDieProcess implements Runnable {
        EpcJinDieTaskDO jinDieTaskDO;
        ToJinDieProcess(EpcJinDieTaskDO jinDieTaskDO){
            this.jinDieTaskDO = jinDieTaskDO;
        }
        @Override
        public void run() {
            try {
                toJinDieReport(jinDieTaskDO);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /*===进金蝶细化的流程====*/
    // 1.客户+商品
    private Integer ReadyAboutGoodsAndCustomers(EpcJinDieTaskDO jinDieTaskDO, EpcOrderDO epcOrderDO, List<OrderGoodsBO> orderGoodsBOList){
        Integer jindiePrimaryId = jinDieTaskDO.getId();
        // a.客户
        String fNumber = jinDieTaskDO.getShopNumber();
        Boolean isExistShop = jinDieExt.isExistCustomer(fNumber);
        if(!isExistShop){
            //不存在，则插入
            Integer provinceId = epcOrderDO.getProvince();
            Integer cityId = epcOrderDO.getCityId();

            RegionBO provinceBO = regionServiceExt.getRegionById(provinceId);
            if(provinceBO == null){
                log.info("pronviceId can't get 无法获得RegionBO. jindiePrimaryId:{},pronviceId:{}",jindiePrimaryId,provinceId);
                updateTaskFail(jindiePrimaryId, "pronviceId 无法获得RegionBO 对象");
                return EpcJinDieTaskDO.TSK_STATUS_FAIL;
            }
            RegionBO cityBO = regionServiceExt.getRegionById(cityId);
            if(provinceBO == null){
                log.info("cityId can't get 无法获得RegionBO. jindiePrimaryId:{},cityId:{}",jindiePrimaryId,cityId);
                updateTaskFail(jindiePrimaryId, "cityId 无法获得RegionBO 对象");
                return EpcJinDieTaskDO.TSK_STATUS_FAIL;
            }
            Boolean isSaveOk = jinDieExt.saveCustomer(epcOrderDO.getCompanyName(),jinDieTaskDO.getShopNumber(),epcOrderDO.getMobile(),epcOrderDO.getAddress(),provinceBO.getRegionName(),cityBO.getRegionName());
            if(!isSaveOk){
                log.info("save Customers To jindie fail. jindiePrimaryId:{}",jindiePrimaryId);
                updateTaskFail(jindiePrimaryId, "保存 客户失败");
                return EpcJinDieTaskDO.TSK_STATUS_FAIL;
            }
        }

        // b.商品
        List<TicItemRemoteParam> needAddList = new ArrayList<>();
        for(OrderGoodsBO orderGoodsBO : orderGoodsBOList){
            String goodsSn = orderGoodsBO.getGoodsSn();
            Boolean isExistGoods = jinDieExt.isExistGoods(goodsSn);
            if(!isExistGoods){
                TicItemRemoteParam remoteParam = new TicItemRemoteParam();
                remoteParam.setGoodsSn(goodsSn);
                remoteParam.setGoodsName(orderGoodsBO.getGoodsName());
                remoteParam.setGoodsUnit(orderGoodsBO.getMeasureUnit());
                remoteParam.setGoodsFormat(orderGoodsBO.getOeNumber());
                needAddList.add(remoteParam);
            }
        }
        Boolean isSaveGoodsOk = jinDieExt.saveGoods(needAddList);
        if(!isSaveGoodsOk){
            log.info("save Goods To jindie fail. jindiePrimaryId:{}",jindiePrimaryId);
            updateTaskFail(jindiePrimaryId, "保存 商品列表失败");
            return EpcJinDieTaskDO.TSK_STATUS_FAIL;
        }

        //更新 task 状态
        EpcJinDieTaskDO upJinDieDO = new EpcJinDieTaskDO();
        upJinDieDO.setId(jindiePrimaryId);
        upJinDieDO.setTaskStatus(EpcJinDieTaskDO.TSK_STATUS_DATA);
        jinDieTaskDOMapper.updateByPrimaryKeySelective(upJinDieDO);

        return EpcJinDieTaskDO.TSK_STATUS_DATA;
    }

    // 2.销售单
    private Integer CreateSaleOrder(EpcJinDieTaskDO jinDieTaskDO, EpcOrderDO epcOrderDO, List<OrderGoodsBO> orderGoodsBOList){
        SEOrderRemoteParam seOrderRemoteParam = new SEOrderRemoteParam();
        List<SEOrderEntryRemoteParam> remoteParamList =new LinkedList<>();
        Date orderCreateDate = epcOrderDO.getGmtCreate();
        if(orderCreateDate == null){
            Integer jindiePrimaryId = jinDieTaskDO.getId();
            log.info("save sales order To jindie fail. epc_order#gmt_create is null");
            updateTaskFail(jindiePrimaryId, "保存 销售单失败,epc_order#gmt_create为空");
            return EpcJinDieTaskDO.TSK_STATUS_FAIL;
        }

        for(OrderGoodsBO orderGoodsBO : orderGoodsBOList){
            SEOrderEntryRemoteParam remoteParam = new SEOrderEntryRemoteParam();
            remoteParam.setAdviceConsignDate(DateUtil.dateToString(new Date(),DateUtil.yyyy_MM_dd_HH_mm_ss));
            remoteParam.setAmount(orderGoodsBO.getSoldPriceAmount());
            remoteParam.setPrice(orderGoodsBO.getSoldPrice());
            remoteParam.setQty(orderGoodsBO.getGoodsNumber());
            remoteParam.setGoodsSn(orderGoodsBO.getGoodsSn());
            remoteParam.setGoodsUnit(orderGoodsBO.getMeasureUnit());
            remoteParamList.add(remoteParam);
        }

        seOrderRemoteParam.setEntries(remoteParamList);
        seOrderRemoteParam.setOrderSn(epcOrderDO.getOrderSn());
        seOrderRemoteParam.setClientNumber(jinDieTaskDO.getShopNumber());
        seOrderRemoteParam.setGmtCreate(DateUtil.dateToString(orderCreateDate, DateUtil.yyyy_MM_dd_HH_mm_ss));
        seOrderRemoteParam.setFetchAdd(epcOrderDO.getAddress());

        //保存销售单
        Boolean isSaveSalesOk = jinDieExt.saveSalesOrder(seOrderRemoteParam);
        if(!isSaveSalesOk){
            Integer jindiePrimaryId = jinDieTaskDO.getId();
            log.info("save sales order To jindie fail. jindiePrimaryId:{}",jindiePrimaryId);
            updateTaskFail(jindiePrimaryId, "保存 销售单失败");
            return EpcJinDieTaskDO.TSK_STATUS_FAIL;
        }

        //更新 task 状态
        EpcJinDieTaskDO upJinDieDO = new EpcJinDieTaskDO();
        upJinDieDO.setId(jinDieTaskDO.getId());
        upJinDieDO.setTaskStatus(EpcJinDieTaskDO.TSK_STATUS_SALE);
        jinDieTaskDOMapper.updateByPrimaryKeySelective(upJinDieDO);

        return EpcJinDieTaskDO.TSK_STATUS_SALE;
    }
    // 3.采购单
    private Integer CreatePurchaseOrder(EpcJinDieTaskDO jinDieTaskDO, EpcOrderDO epcOrderDO, List<OrderGoodsBO> orderGoodsBOList){
        PurchaseBillRemoteParam chaseBillRemoteParam = new PurchaseBillRemoteParam();
        List<PurchaseBillEntryRemoteParam> entryRemoteList = new LinkedList<>();
        Integer jindiePrimaryId = jinDieTaskDO.getId();

        Date payDate = epcOrderDO.getPayTime();
        if(payDate == null){
            log.info("save purchase order To jindie fail. epc_order#pay_time is null");
            updateTaskFail(jindiePrimaryId, "保存 采购单失败,epc_order#pay_time 为空");
            return EpcJinDieTaskDO.TSK_STATUS_FAIL;
        }

        for(OrderGoodsBO orderGoodsBO : orderGoodsBOList){
            PurchaseBillEntryRemoteParam remoteParam = new PurchaseBillEntryRemoteParam();
            remoteParam.setGoodsSn(orderGoodsBO.getGoodsSn());
            remoteParam.setGoodsUnit(orderGoodsBO.getMeasureUnit());
            remoteParam.setGoodsNumber(orderGoodsBO.getGoodsNumber());
            remoteParam.setIncludeTaxPrice(orderGoodsBO.getSoldPrice());
            remoteParam.setIncludeTaxAmount(orderGoodsBO.getSoldPriceAmount());
            remoteParam.setConvertRate(BigDecimal.ZERO);
            entryRemoteList.add(remoteParam);
        }

        chaseBillRemoteParam.setEntries(entryRemoteList);
        chaseBillRemoteParam.setCreateName(EpcJinDieTaskDO.CREATE_NAME);
        chaseBillRemoteParam.setSupplierNumber(jinDieTaskDO.getSellerNumber());
        chaseBillRemoteParam.setReceiptGoodsAddress(EpcJinDieTaskDO.PURCHASEORDER_RECEIPTGOODSADDRESS);
        chaseBillRemoteParam.setGmtCreate(payDate);
        chaseBillRemoteParam.setGmtModified(payDate);
        chaseBillRemoteParam.setAmount(epcOrderDO.getNeedSettleAmount());
        chaseBillRemoteParam.setDeliveryDate(new Date());
        chaseBillRemoteParam.setWarehouseNo(EpcJinDieTaskDO.LOP_WAREHOUSENO);

        String purchaseNo = jinDieExt.savePurchaseOrder(chaseBillRemoteParam);

        if(StringUtils.isEmpty(purchaseNo)){
            log.info("save purchase order To jindie fail. jindiePrimaryId:{}",jindiePrimaryId);
            updateTaskFail(jindiePrimaryId, "保存 采购单失败");
            return EpcJinDieTaskDO.TSK_STATUS_FAIL;
        }

        jinDieTaskDO.setPurchaseNumber(purchaseNo);
        //保存采购单
        EpcJinDieTaskDO upJinDieDO = new EpcJinDieTaskDO();
        upJinDieDO.setId(jindiePrimaryId);
        upJinDieDO.setPurchaseNumber(purchaseNo);
        upJinDieDO.setTaskStatus(EpcJinDieTaskDO.TSK_STATUS_PURCHASE);
        jinDieTaskDOMapper.updateByPrimaryKeySelective(upJinDieDO);

        return EpcJinDieTaskDO.TSK_STATUS_PURCHASE;
    }
    // 4.入库单
    private Integer CreateInWareOrder(EpcJinDieTaskDO jinDieTaskDO, EpcOrderDO epcOrderDO, List<OrderGoodsBO> orderGoodsBOList){
        PurchaseInWareBillRemoteParam inWareBillRemoteParam = new PurchaseInWareBillRemoteParam();
        List<PurchaseInWareBillEntryRemoteParam> list = new LinkedList<>();

        Integer jindiePrimaryId = jinDieTaskDO.getId();
        Date signDate = epcOrderDO.getSignTime();
        if(signDate == null){
            log.info("save in ware order To jindie fail. epc_order#sign_time is null");
            updateTaskFail(jindiePrimaryId, "保存 入库单失败,epc_order#sign_time 为空");
            return EpcJinDieTaskDO.TSK_STATUS_FAIL;
        }

        for(OrderGoodsBO orderGoodsBO:orderGoodsBOList){
            PurchaseInWareBillEntryRemoteParam entryRemoteParam = new PurchaseInWareBillEntryRemoteParam();
            entryRemoteParam.setGoodsSn(orderGoodsBO.getGoodsSn());
            entryRemoteParam.setCurrencyInWareNumber(orderGoodsBO.getGoodsNumber());
            list.add(entryRemoteParam);
        }
        inWareBillRemoteParam.setEntries(list);
        inWareBillRemoteParam.setCreateName(EpcJinDieTaskDO.CREATE_NAME);
        inWareBillRemoteParam.setAudioTime(new Date());
        inWareBillRemoteParam.setSourceBillNo(jinDieTaskDO.getPurchaseNumber());
        inWareBillRemoteParam.setGmtCreate(signDate);
        inWareBillRemoteParam.setWarehouseNo(EpcJinDieTaskDO.LOP_WAREHOUSENO);

        String inWareNo = jinDieExt.saveInWareOrder(inWareBillRemoteParam);
        if(StringUtils.isEmpty(inWareNo)){
            log.info("save in ware order To jindie fail. jindiePrimaryId:{}",jindiePrimaryId);
            updateTaskFail(jindiePrimaryId, "保存 入库单失败");
            return EpcJinDieTaskDO.TSK_STATUS_FAIL;
        }

        jinDieTaskDO.setInWareNumber(inWareNo);
        //保存入库单
        EpcJinDieTaskDO upJinDieDO = new EpcJinDieTaskDO();
        upJinDieDO.setId(jindiePrimaryId);
        upJinDieDO.setInWareNumber(inWareNo);
        upJinDieDO.setTaskStatus(EpcJinDieTaskDO.TSK_STATUS_IN_WARE);
        jinDieTaskDOMapper.updateByPrimaryKeySelective(upJinDieDO);


        return EpcJinDieTaskDO.TSK_STATUS_IN_WARE;
    }
    // 5.发票
    private Integer CreateInvoice(EpcJinDieTaskDO jinDieTaskDO, EpcOrderDO epcOrderDO, List<OrderGoodsBO> orderGoodsBOList){
        PurchaseInvoiceRemoteParam invoiceRemoteParam = new PurchaseInvoiceRemoteParam();
        List<PurchaseInvoiceEntryRemoteParam> invoiceEntryRemoteParamList=new LinkedList<>();

        Integer jindiePrimaryId = jinDieTaskDO.getId();
        Date signDate = epcOrderDO.getSignTime();
        if(signDate == null){
            log.info("save in invoice order To jindie fail. epc_order#sign_time is null");
            updateTaskFail(jindiePrimaryId, "保存 发票失败,epc_order#sign_time 为空");
            return EpcJinDieTaskDO.TSK_STATUS_FAIL;
        }

        for(OrderGoodsBO orderGoodsBO : orderGoodsBOList){
            PurchaseInvoiceEntryRemoteParam invoiceEntryRemoteParam = new PurchaseInvoiceEntryRemoteParam();
            BigDecimal soldPrice = orderGoodsBO.getSoldPrice();
            invoiceEntryRemoteParam.setGoodsSn(orderGoodsBO.getGoodsSn());
            invoiceEntryRemoteParam.setGoodsNumber(orderGoodsBO.getGoodsNumber());
            invoiceEntryRemoteParam.setIncludeTaxPrice(soldPrice);
            invoiceEntryRemoteParam.setIncludeTaxAmount(orderGoodsBO.getSoldPriceAmount());
            invoiceEntryRemoteParam.setConvertRate(BigDecimal.ZERO);
            invoiceEntryRemoteParam.setIsReverseWrite(1);
            invoiceEntryRemoteParam.setPurchaseIncludeTaxPrice(soldPrice);
            invoiceEntryRemoteParamList.add(invoiceEntryRemoteParam);
        }
        invoiceRemoteParam.setEntries(invoiceEntryRemoteParamList);

        String purchaseNo = jinDieTaskDO.getPurchaseNumber();
        String inWareNo = jinDieTaskDO.getInWareNumber();

        invoiceRemoteParam.setCreateName(EpcJinDieTaskDO.CREATE_NAME);
        invoiceRemoteParam.setGmtCreate(signDate);
        invoiceRemoteParam.setWarehouseNo(EpcJinDieTaskDO.LOP_WAREHOUSENO);
        invoiceRemoteParam.setPurchaseBillNo(purchaseNo);
        invoiceRemoteParam.setInWareBillNo(inWareNo);
        invoiceRemoteParam.setComment(purchaseNo + "/" + inWareNo);


        Integer invType = Optional.fromNullable(epcOrderDO.getInvType()).or(0);
        if(invType.compareTo(0)>0) {
            invoiceRemoteParam.setInvoiceType(1);//需求单的普通发票+增值发票 == 金蝶 增值发票
        } else {
            invoiceRemoteParam.setInvoiceType(0);//需求单的不带票 == 金蝶 普通发票
        }

        String invoiceNo = jinDieExt.saveInvoice(invoiceRemoteParam);

        if(StringUtils.isEmpty(invoiceNo)){
            log.info("save invoice order To jindie fail. jindiePrimaryId:{}",jindiePrimaryId);
            updateTaskFail(jindiePrimaryId, "保存 发票失败");
            return EpcJinDieTaskDO.TSK_STATUS_FAIL;
        }

        jinDieTaskDO.setInvoiceNumber(invoiceNo);
        //保存发票
        EpcJinDieTaskDO upJinDieDO = new EpcJinDieTaskDO();
        upJinDieDO.setId(jindiePrimaryId);
        upJinDieDO.setInvoiceNumber(invoiceNo);
        upJinDieDO.setTaskStatus(EpcJinDieTaskDO.TSK_STATUS_INVOICE);
        jinDieTaskDOMapper.updateByPrimaryKeySelective(upJinDieDO);


        return EpcJinDieTaskDO.TSK_STATUS_INVOICE;
    }

    // 6.出库单
    private Integer CreateOutWareOrder(EpcJinDieTaskDO jinDieTaskDO, EpcOrderDO epcOrderDO, List<OrderGoodsBO> orderGoodsBOList){
        StockBillRemoteParam billRemoteParam  = new StockBillRemoteParam();
        List<StockBillEntryRemoteParam> entries = new ArrayList<>();
        Integer jindiePrimaryId = jinDieTaskDO.getId();
        Date signDate = epcOrderDO.getSignTime();
        if(signDate == null){
            log.info("save out ware order To jindie fail. epc_order#sign_time is null");
            updateTaskFail(jindiePrimaryId, "保存 出库单失败,epc_order#sign_time 为空");
            return EpcJinDieTaskDO.TSK_STATUS_FAIL;
        }

        for(OrderGoodsBO orderGoodsBO:orderGoodsBOList){
            StockBillEntryRemoteParam entryRemoteParam = new StockBillEntryRemoteParam();
            entryRemoteParam.setFitemNumber(orderGoodsBO.getGoodsSn());
            entryRemoteParam.setRetrunNumber(0);
            entries.add(entryRemoteParam);
        }
        billRemoteParam.setEntries(entries);
        billRemoteParam.setCurrentUserName(EpcJinDieTaskDO.CREATE_NAME);
        billRemoteParam.setWarehouseNo(EpcJinDieTaskDO.LOP_WAREHOUSENO);
        billRemoteParam.setSaleOrderNumber(epcOrderDO.getOrderSn());
        billRemoteParam.setRedOrBlue(1);
        billRemoteParam.setClientNumber(jinDieTaskDO.getShopNumber());
        billRemoteParam.setGmtCreate(getOutStockTime(signDate));

        String outWareNo = jinDieExt.saveOutWareOrder(billRemoteParam);
        if(StringUtils.isEmpty(outWareNo)){
            log.info("save out ware order To jindie fail. jindiePrimaryId:{}",jindiePrimaryId);
            updateTaskFail(jindiePrimaryId, "保存 出库单失败");
            return EpcJinDieTaskDO.TSK_STATUS_FAIL;
        }

        jinDieTaskDO.setOutWareNumber(outWareNo);
        //保存出库单，且设定 进金蝶成功
        EpcJinDieTaskDO upJinDieDO = new EpcJinDieTaskDO();
        upJinDieDO.setId(jindiePrimaryId);
        upJinDieDO.setOutWareNumber(outWareNo);
        upJinDieDO.setTaskStatus(EpcJinDieTaskDO.TSK_STATUS_OUT_WARE);
        upJinDieDO.setResultStatus(EpcJinDieTaskDO.RESULT_STATUS_SUCCESS);
        jinDieTaskDOMapper.updateByPrimaryKeySelective(upJinDieDO);

        return EpcJinDieTaskDO.TSK_STATUS_OUT_WARE;
    }


    /**
     * 出库单时间：入库单时间+1~3天，不能隔月，必须在白天,若是最后一天 18:00 以后，则+1小时 或 +1 minute 处理
     * 8~18时直接
     */
    private Date getOutStockTime(Date date) {
        DateTime dateTime = new DateTime(date.getTime());
        Random random = new Random();
        int s = random.nextInt(3)+1 ;
        int oldMonth = dateTime.getMonthOfYear();
        DateTime newTime = dateTime.plusDays(s);
        int newMonth = newTime.getMonthOfYear();
        int day = newTime.getDayOfMonth();
        if (oldMonth != newMonth) {
            newTime = newTime.plusDays(-day);
        }
        int hour = dateTime.getHourOfDay();
        if (hour < 8) {
            newTime = newTime.plusHours(10);
        } else if (hour > 17) {
            newTime = newTime.plusHours(-10);
        }

        //暂时不做控制，入库单可以比出库单大
        // 如果 新的时间比 dateTime 时间小或者一样,即 此为最后一天 18:00 以后
//        if(!newTime.isAfter(dateTime)){
//            if(dateTime.getHourOfDay() == 23){
//                if(dateTime.getMinuteOfHour() == 59){
//                    //23:59 分，不能跨日，最后一天了
//                    newTime = dateTime;
//                }else{
//                    //23 点，+1分钟，出库
//                    newTime = dateTime.plusMinutes(1);
//                }
//            }else {
//                //最后一天，在入库时间+1个小时 出库
//                newTime = dateTime.plusHours(1);
//            }
//        }
        return newTime.toDate();
    }
}
