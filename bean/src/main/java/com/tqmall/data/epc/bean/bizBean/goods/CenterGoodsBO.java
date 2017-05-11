package com.tqmall.data.epc.bean.bizBean.goods;

import lombok.Data;

import java.util.List;

/**
 * Created by huangzhangting on 16/2/17.
 * 14:47
 * 商品
 */
@Data
public class CenterGoodsBO {
    private Integer goodsId;//商品库 center_goods 主键id

    private String oeNumber;

    private String partName;

    private Object customAttr; //自定义属性

    private String epcPic;//安装配图

    private String epcIndex;//配件在图上的位置号

    private Integer thirdCatId;

    private String remarks;//备注

    private String epcPicNum; //图片编号
    private List<String> epcPicList;

    private Integer amountUsed; //用量

}
