package com.tqmall.data.epc.bean.entity.order;

import com.tqmall.data.epc.common.utils.upload.ImgUtil;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class EpcOrderGoodsDO {
    private Integer id;

    private String isDeleted;

    private Integer creator;

    private Date gmtCreate;

    private Integer modifier;

    private Date gmtModified;

    //订单 id
    private Integer orderId;
    //订单编号
    private String orderSn;
    //db_goods 自增 id
    private Integer goodsId;
    //商品编号
    private String goodsSn;
    //商品名称
    private String goodsName;
    //商品规格型号
    private String goodsFormat;
    //商品图片
    private String goodsImg;
    //商品计量单位
    private String measureUnit;
    //商品品质
    private String goodsQuality;
    //品牌名称
    private String brandName;
    //标准零件名称
    private String partName;
    //oe 码
    private String oeNumber;
    //商品购买数量
    private Integer goodsNumber;
    //商品原价
    private BigDecimal goodsPrice;
    //商品实际单价
    private BigDecimal soldPrice;
    //商品实际总金额
    private BigDecimal soldPriceAmount;
    //适用车型别称--云配使用
    private String carAlias;

    public String getGoodsImg(){
        if(goodsImg==null){
            return null;
        }
        return ImgUtil.getFullPath(goodsImg);
    }
}