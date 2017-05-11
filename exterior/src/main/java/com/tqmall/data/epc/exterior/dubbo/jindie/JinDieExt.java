package com.tqmall.data.epc.exterior.dubbo.jindie;

import com.tqmall.redarrow.client.param.*;

import java.util.List;

/**
 * Created by zxg on 16/9/2.
 * 15:30
 * no bug,以后改代码的哥们，祝你好运~！！
 */
public interface JinDieExt {
    //是否存在客户,null-表示接口出错
    Boolean isExistCustomer(String fNumber);
    //保存客户
    Boolean saveCustomer(String name,String fNumber,String phone,String address,String province,String city);

    //是否存在商品
    Boolean isExistGoods(String goodsSn);
    //批量保存商品
    Boolean saveGoods(List<TicItemRemoteParam> goodsList);

    //保存销售单
    Boolean saveSalesOrder(SEOrderRemoteParam seOrderRemoteParam);

    //保存采购单，返回采购订单号
    String savePurchaseOrder(PurchaseBillRemoteParam purchaseBillRemoteParam);

    //保存入库单，返回入库单号
    String saveInWareOrder(PurchaseInWareBillRemoteParam inWareBillRemoteParam);

    //保存发票，返回发票单号
    String saveInvoice(PurchaseInvoiceRemoteParam invoiceRemoteParam);

    //保存出库单，返回出库单号
    String saveOutWareOrder(StockBillRemoteParam outWareParam);
}
