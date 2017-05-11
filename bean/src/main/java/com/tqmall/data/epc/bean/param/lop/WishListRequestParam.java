package com.tqmall.data.epc.bean.param.lop;

import com.tqmall.data.epc.bean.bizBean.lop.WishListGoodsBO;
import com.tqmall.data.epc.bean.param.BaseQueryParam;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Created by huangzhangting on 16/7/30.
 */
@Data
public class WishListRequestParam extends BaseQueryParam{
    private Integer id;

    private String wishListSn;

    private String status;

    private String vin;

    private Integer isDeckVehicle;

    private Integer isModifiedVehicle;

    private Integer isReceiptPrice;

    /**
     * 品牌
     */
    private Integer brand;

    /**
     * 车系
     */
    private Integer series;

    /**
     * 车型
     */
    private Integer model;

    /**
     * 排量
     */
    private Integer engine;

    /**
     * 年款
     */
    private Integer year;

    /**
     * 第六级
     */
    private Integer tqCarModel;

    private String companyName;

    private String telephone;

    private String wishListMemo;

    private Date wishStartTime;

    private Date wishEndTime;

    private String refer;

    private String wishListMaker;

    private String wishListMakerTel;
    /**
     * 客户端token
     */
    private String token;

    private List<WishListGoodsBO> goodsList;
}
