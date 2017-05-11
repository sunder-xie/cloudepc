package com.tqmall.data.epc.bean.entity.jindie;

import lombok.Data;

import java.util.Date;

@Data
public class EpcJinDieTaskDO {
    public final static String CREATE_NAME="赵阿勇";
    public final static String PURCHASEORDER_RECEIPTGOODSADDRESS="浙江省杭州市余杭区文一西路998号";
    public final static String LOP_WAREHOUSENO="QG01";

    public final static Integer TSK_STATUS_FAIL = -1;
    public final static Integer TSK_STATUS_NEW = 0;
    public final static Integer TSK_STATUS_DATA = 1;
    public final static Integer TSK_STATUS_SALE = 2;
    public final static Integer TSK_STATUS_PURCHASE = 3;
    public final static Integer TSK_STATUS_IN_WARE = 4;
    public final static Integer TSK_STATUS_INVOICE = 5;
    public final static Integer TSK_STATUS_OUT_WARE = 6;

    public final static Integer RESULT_STATUS_NEW = 0;
    public final static Integer RESULT_STATUS_SUCCESS = 1;
    public final static Integer RESULT_STATUS_FAIL = 2;


    private Integer id;

    private String isDeleted;

    private Integer creator;

    private Date gmtCreate;

    private Integer modifier;

    private Date gmtModified;

    private Integer taskStatus;

    private Integer resultStatus;

    private Integer orderId;

    private String orderSn;

    private String shopNumber;

    private String sellerNumber;

    private String purchaseNumber;

    private String inWareNumber;

    private String invoiceNumber;

    private String outWareNumber;

    private Integer handleTimes;

    private String failReason;

}