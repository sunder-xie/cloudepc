package com.tqmall.data.epc.bean.bizBean.lop;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class OfferListGoodsBO {

    private Integer id;

    private Integer offerListId;

    private Integer wishListId;

    private Integer groupId;

    private Integer isUserSelectPay;

    private Integer goodsId;

    private String goodsName;

    private String goodsOe;

    private Integer goodsQualityType;

    private String goodsBrand;

    private Integer goodsBrandId;

    private Integer goodsNumber;

    private String goodsMeasureUnit;

    private BigDecimal goodsPrice;

    private BigDecimal goodsPriceAmount;
    /**
     * 采购价格
     */
    private BigDecimal purchasePrice;

    private String shippingStatus;

    private String shippingType;

    private String shippingCompany;

    private String shippingNo;

    private String shippingImage;

    private Date shippingTime;

    /**
     * 车型相关信息的编号翻译成文字
     */
    private String brandName;

    private String engineName;

    private String modelName;

    private String yearName;
    /**
     * 用于web页面显示，带http：前缀
     */
    private String httpShippingImage;

    private String goodsQualityTypeStr;

    private String goodsPriceStr;

    private String goodsPriceAmountStr;

    private OfferListGoodsRefundBO offerListGoodsRefundDTO;

}
