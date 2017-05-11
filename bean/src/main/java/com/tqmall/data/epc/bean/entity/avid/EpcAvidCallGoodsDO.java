package com.tqmall.data.epc.bean.entity.avid;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class EpcAvidCallGoodsDO {
    private Integer id;

    private String isDeleted;

    private Integer creator;

    private Date gmtCreate;

    private Integer modifier;

    private Date gmtModified;

    private String modifierName;

    private Integer avidCallId;

    private String goodsName;

    private String goodsOe;

    private Integer goodsNumber;

    private String goodsUnit;

    private Integer goodsQualityId;

    private Integer goodsBrandId;

    private String goodsRemark;

    private BigDecimal goodsPrice;

    private Integer backQualityId;

    //处理非必填属性，批量插入时需要用到
    public void handleNotEssentialProperty(){
        if(this.goodsOe==null){
            this.goodsOe = "";
        }
        if(this.goodsUnit==null){
            this.goodsUnit = "";
        }
        if(this.backQualityId==null){
            this.backQualityId = 0;
        }
        if(this.goodsRemark==null){
            this.goodsRemark = "";
        }
        if(this.goodsName==null){
            this.goodsName = "";
        }
        if(this.goodsQualityId==null){
            this.goodsQualityId = 0;
        }

    }

}