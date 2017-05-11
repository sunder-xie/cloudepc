package com.tqmall.data.epc.bean.bizBean.goods;

import com.tqmall.data.epc.bean.entity.goods.GoodsQuoteDO;
import lombok.Data;

import java.util.List;

/**
 * Created by lyj on 16/2/18.
 * 配件详情
 */
@Data
public class CenterGoodsDetailBO {
    //goods 信息
    private Integer goodsId;//商品库 center_goods 主键id
    private String oeNumber;
    private String partName;

    //car 信息
    private Integer carId;
    private String name;
    private String model;
    private String series;
    private String brand;
    private String company;
    private String year;

    //goodsBase 信息
    private String firstCatName;
    private String secondCatName;
    private String thirdCatName;

    //goods X car 联合信息
    private String epcPic;//安装配图
    private String epcIndex;//配件位置
    private String epcPicNum;//图编号
    private List<String> epcPicList;
    private String remarks;
    private Integer amountUsed; //用量

    private List<GoodsQuoteDO> quoteDOList;//报价信息
}
