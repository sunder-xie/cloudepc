package com.tqmall.data.epc.exterior.dubbo.jindie;

import com.tqmall.core.common.entity.Result;
import com.tqmall.data.epc.common.utils.JsonUtil;
import com.tqmall.redarrow.client.param.*;
import com.tqmall.redarrow.client.provider.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zxg on 16/9/2.
 * 15:40
 * no bug,以后改代码的哥们，祝你好运~！！
 */
@Slf4j
@Service
public class JinDieExtImpl implements JinDieExt {

    @Autowired
    private TOrganizationRemoteService torganizationRemoteService;
    @Autowired
    private TICItemRemoteService ticItemRemoteService;
    @Autowired
    private SEOrderRemoteService seOrderRemoteService;
    @Autowired
    private POOrderRemoteService poOrderRemoteService;
    @Autowired
    private StockBillRemoteService stockBillRemoteService;
    @Autowired
    private InvoiceBillRemoteService invoiceBillRemoteService;

    @Override
    public Boolean isExistCustomer(String fNumber) {
        if(StringUtils.isEmpty(fNumber)){
            log.info("fNumber is empty");
            return false;
        }
        log.info("start check fNumber isExist:{}",fNumber);
        Result<Boolean> isExistResult = torganizationRemoteService.checkIsExistClientByNumber(fNumber);
        log.info("check fNumber result:{}", JsonUtil.objectToJson(isExistResult));

        if(isExistResult.isSuccess()){
            return isExistResult.getData();
        }

        return false;
    }

    @Override
    public Boolean saveCustomer(String name, String fNumber, String phone, String address, String province, String city) {

        ClientRemoteParam clientRemoteParam = new ClientRemoteParam();
        clientRemoteParam.setFname(name);
        clientRemoteParam.setFnumber(fNumber);
        clientRemoteParam.setFphone(phone);
        clientRemoteParam.setFaddress(address);
        clientRemoteParam.setFprovince(province);
        clientRemoteParam.setFcity(city);

        log.info("save customer:{}",JsonUtil.objectToJson(clientRemoteParam));
        Result<Boolean> result = torganizationRemoteService.saveClient(clientRemoteParam);
        log.info("save customer result:{}",JsonUtil.objectToJson(result));

        if(result.isSuccess()){
            return result.getData();
        }
        return false;
    }

    @Override
    public Boolean isExistGoods(String goodsSn) {
        if(StringUtils.isEmpty(goodsSn)){
            log.info("goodsSn is empty");
            return false;
        }

        log.info("start check GoodsSn isExist:{}",goodsSn);
        Result<Boolean> isExistResult = ticItemRemoteService.checkGoodsIsExist(goodsSn);
        log.info("check GoodsSn result:{}", JsonUtil.objectToJson(isExistResult));

        if(isExistResult.isSuccess()){
            return isExistResult.getData();
        }

        return false;
    }

    @Override
    public Boolean saveGoods(List<TicItemRemoteParam> goodsList) {
        if(goodsList.isEmpty()){
            return true;
        }

        List<String> snList = new ArrayList<>();
        for(TicItemRemoteParam ticItemRemoteParam:goodsList){
            snList.add(ticItemRemoteParam.getGoodsSn());
        }
        log.info("save goods:{}",JsonUtil.objectToJson(snList));

        Result<Boolean> result = ticItemRemoteService.saveTICItemByBath(goodsList);
        log.info("save goods result:{}",JsonUtil.objectToJson(result));

        if(result.isSuccess()){
            return result.getData();
        }
        return false;
    }

    @Override
    public Boolean saveSalesOrder(SEOrderRemoteParam seOrderRemoteParam) {
        log.info("save sales order:{}",JsonUtil.objectToJson(seOrderRemoteParam));

        Result<Boolean> result = seOrderRemoteService.saveSEOrder(seOrderRemoteParam);
        log.info("save sales order result:{}",JsonUtil.objectToJson(result));

        if(result.isSuccess()){
            return result.getData();
        }
        return false;
    }

    @Override
    public String savePurchaseOrder(PurchaseBillRemoteParam purchaseBillRemoteParam) {

        log.info("save purchase order:{}",JsonUtil.objectToJson(purchaseBillRemoteParam));
        Result<String> result = poOrderRemoteService.savePurchaseBill(purchaseBillRemoteParam);
        log.info("save purchase order result:{}",JsonUtil.objectToJson(result));

        if(result.isSuccess()){
            return result.getData();
        }
        return null;
    }

    @Override
    public String saveInWareOrder(PurchaseInWareBillRemoteParam inWareBillRemoteParam) {
        log.info("save inWare order:{}",JsonUtil.objectToJson(inWareBillRemoteParam));
        Result<String> result = stockBillRemoteService.savePurchaseInWareBill(inWareBillRemoteParam);
        log.info("save inWare order result:{}",JsonUtil.objectToJson(result));

        if(result.isSuccess()){
            return result.getData();
        }
        return null;
    }

    @Override
    public String saveInvoice(PurchaseInvoiceRemoteParam invoiceRemoteParam) {
        log.info("save invoice:{}",JsonUtil.objectToJson(invoiceRemoteParam));

        Result<String> result = invoiceBillRemoteService.saveInvoiceManager(invoiceRemoteParam);
        log.info("save invoice result:{}",JsonUtil.objectToJson(result));

        if(result.isSuccess()){
            return result.getData();
        }
        return null;
    }

    @Override
    public String saveOutWareOrder(StockBillRemoteParam outWareParam) {
        log.info("save  outWare order:{}",JsonUtil.objectToJson(outWareParam));

        Result<String> result = stockBillRemoteService.impBlueBill(outWareParam);
        log.info("save outWare order result:{}",JsonUtil.objectToJson(result));

        if(result.isSuccess()){
            return result.getData();
        }
        return null;
    }
}
