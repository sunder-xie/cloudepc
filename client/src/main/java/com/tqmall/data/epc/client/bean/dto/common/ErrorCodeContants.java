package com.tqmall.data.epc.client.bean.dto.common;

/**
 * Created by zxg on 16/9/7.
 * 11:51
 * no bug,以后改代码的哥们，祝你好运~！！
 */
public class ErrorCodeContants {
    private static final String EPC_CODE = "7012";

    /*用户*/
    //来源错误
    public static final String OPERATOR_SOURCE_ERROR = EPC_CODE+"1000";

    /*订单相关*/
    //订单不存在数据库
    public static final String NO_ORDER_ERROR = EPC_CODE+"2000";
    //订单状态不合法
    public static final String ORDER_STATUS_ERROR = EPC_CODE+"2001";
    //供应商跟订单没对上
    public static final String  ORDER_SELLER_ERROR = EPC_CODE+"2002";
    //订单中无商品
    public static final String ORDER_GOODS_ERROR = EPC_CODE+"2003";

    /*金额相关*/
    //金额错误，例如 结算金额比实际要大，商品金额为空或者0等等
    public static final String  AMOUNT_ERROR = EPC_CODE+"3000";

    /*其他*/
    //发货方式不存在
    public static final String  SHIPPING_ERROR = EPC_CODE+"4000";


}
