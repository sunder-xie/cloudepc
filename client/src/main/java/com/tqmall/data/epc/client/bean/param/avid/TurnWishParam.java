package com.tqmall.data.epc.client.bean.param.avid;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by huangzhangting on 16/10/25.
 */
@Data
public class TurnWishParam implements Serializable{

    private String vin;

    private Integer isDeckVehicle;

    private Integer isModifiedVehicle;

    private Integer isReceiptPrice;

    private Integer brand;

    private Integer series;

    private Integer model;

    private Integer engine;

    private Integer year;

    private Integer tqCarModel;

    //private String companyName;

    private String telephone;

    private String wishListMemo;

    private String wishListMaker;

    private String wishListMakerTel;
    /**
     * 客户端token
     */
    private String token;

    private List<TurnWishGoodsParam> goodsList;

    //急呼数据主键id
    private Integer avidCallId;
    //操作人
    private String operator;

}
