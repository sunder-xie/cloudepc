package com.tqmall.data.epc.bean.bizBean.lop;

import com.tqmall.data.epc.common.utils.upload.ImgUtil;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单，商品对象
 * 参考自 portal # WebOfferListGoodsBO
 * Created by huangzhangting on 16/7/31.
 */
@Data
public class WishOrderGoodsBO {

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

    private String goodsPrice;

    private String goodsPriceAmount;

    private String shippingStatus;

    private String offerGoodsStatus;

    private String shippingType;

    private String shippingCompany;

    private String shippingNo;

    private String shippingImage;

    private Date shippingTime;
    /**
     * 用于web页面显示，带http：前缀
     */
    private String httpShippingImage;

    private String goodsQualityTypeStr;

    private String goodsPriceStr;

    private String goodsPriceAmountStr;

    //是否显示退货
    private Boolean isShowRefundGoods;

    public String getShippingImage() {
        return ImgUtil.getFullPath(shippingImage);
    }

    public void setGoodsPrice(BigDecimal goodsPrice) {
        if (goodsPrice != null) {
            this.goodsPrice = goodsPrice.setScale(2, BigDecimal.ROUND_HALF_UP).toEngineeringString();
        }
    }

    public void setGoodsPriceAmount(BigDecimal goodsPriceAmount) {
        if (goodsPriceAmount != null) {
            this.goodsPriceAmount = goodsPriceAmount.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
        }
    }
}
