package com.tqmall.data.epc.common.bean;

import com.tqmall.core.common.errorcode.ErrorCode;
import com.tqmall.core.common.errorcode.ErrorCodeBuilder;
import com.tqmall.core.common.errorcode.PlatformErrorCode;
import com.tqmall.core.common.errorcode.support.CommonError;
import com.tqmall.core.common.errorcode.support.ErrorCodeHelper;

/**
 * cloudepc项目异常定义
 * Created by huangzhangting on 16/1/26.
 */
public class EpcError extends CommonError {
    private static final String EPC_CODE = "01";

    public static final ErrorCode UN_KNOW_EXCEPTION = ErrorCodeBuilder.newError(PlatformErrorCode.DATA, EPC_CODE)
            .setError()
            .setDetailCode("0001")
            .setName("未知异常")
            .setMessageFormat("很抱歉，系统内部错误")
            .genErrorCode();

    public static final ErrorCode NO_DATA_ERROR = ErrorCodeBuilder.newError(PlatformErrorCode.DATA, EPC_CODE)
            .setError()
            .setDetailCode("0002")
            .setName("没有数据")
            .setMessageFormat("很抱歉，未找到满足条件的数据")
            .genErrorCode();

    public static final ErrorCode UN_OPEN_CITY_ERROR = ErrorCodeBuilder.newError(PlatformErrorCode.DATA, EPC_CODE)
            .setError()
            .setDetailCode("0003")
            .setName("未开放城市站")
            .setMessageFormat("很抱歉，该城市站发布需求单功能暂未开放")
            .genErrorCode();

    public static final ErrorCode NOT_LOGIN_ERROR = ErrorCodeBuilder.newError(PlatformErrorCode.DATA, EPC_CODE)
            .setError()
            .setDetailCode("0004")
            .setName("未登录")
            .setMessageFormat("请先登录")
            .genErrorCode();

    public static final ErrorCode SHOP_UN_VERIFY_ERROR = ErrorCodeBuilder.newError(PlatformErrorCode.DATA, EPC_CODE)
            .setError()
            .setDetailCode("0005")
            .setName("未认证通过")
            .setMessageFormat("未认证通过")
            .genErrorCode();


    /*用户*/
    public static final ErrorCode OPERATOR_SOURCE_ERROR = ErrorCodeBuilder.newError(PlatformErrorCode.DATA, EPC_CODE)
            .setError()
            .setDetailCode("1000")
            .setName("操作人来源无记录")
            .setMessageFormat("请先将operator_source记录到系统中")
            .genErrorCode();

    /*订单相关*/
    public static final ErrorCode NO_ORDER_ERROR = ErrorCodeBuilder.newError(PlatformErrorCode.DATA, EPC_CODE)
            .setError()
            .setDetailCode("2000")
            .setName("订单不存在")
            .setMessageFormat("订单编号不存在数据库")
            .genErrorCode();

    public static final ErrorCode ORDER_STATUS_ERROR = ErrorCodeBuilder.newError(PlatformErrorCode.DATA, EPC_CODE)
            .setError()
            .setDetailCode("2001")
            .setName("订单状态错误")
            .setMessageFormat("订单状态不符合此接口处理的订单标准")
            .genErrorCode();

    public static final ErrorCode ORDER_SELLER_ERROR = ErrorCodeBuilder.newError(PlatformErrorCode.DATA, EPC_CODE)
            .setError()
            .setDetailCode("2002")
            .setName("供应商编号错误")
            .setMessageFormat("此订单不属于此供应商")
            .genErrorCode();

    public static final ErrorCode ORDER_GOODS_ERROR = ErrorCodeBuilder.newError(PlatformErrorCode.DATA, EPC_CODE)
            .setError()
            .setDetailCode("2003")
            .setName("无此商品")
            .setMessageFormat("订单中无此商品")
            .genErrorCode();

    /*金额相关*/
    public static final ErrorCode  AMOUNT_ERROR = ErrorCodeBuilder.newError(PlatformErrorCode.DATA, EPC_CODE)
            .setError()
            .setDetailCode("3000")
            .setName("金额错误")
            .setMessageFormat("金额 为空、小于0或大于数据库设定值")
            .genErrorCode();

    /*其他*/
    public static final ErrorCode  SHIPPING_ERROR = ErrorCodeBuilder.newError(PlatformErrorCode.DATA, EPC_CODE)
            .setError()
            .setDetailCode("4000")
            .setName("发货方式 不存在")
            .setMessageFormat("发货方式不存在数据库中")
            .genErrorCode();

    //自定义参数错误
    public static ErrorCode SET_ARG_ERROR(String message){
        return ErrorCodeHelper.newCommonFatal().setDetailCode("0008").setName("参数错误").setMessageFormat(message).genErrorCode();
    }
}
