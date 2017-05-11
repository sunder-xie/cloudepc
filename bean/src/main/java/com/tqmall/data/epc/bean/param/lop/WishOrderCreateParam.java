package com.tqmall.data.epc.bean.param.lop;

import com.tqmall.data.epc.bean.param.BaseQueryParam;
import lombok.Data;

/**
 * 需求单，提交订单，参数对象
 * Created by huangzhangting on 16/7/30.
 */
@Data
public class WishOrderCreateParam extends BaseQueryParam{

    private String companyName;

    private Integer province;

    private Integer city;

    private Integer district;

    private Integer street;

    private String address;

    private String mobile;

    private String telephone;

    private String consignee;

    private Integer wishId;

    private Integer offerId;

    private Integer payId;

    private Integer shippingId;

    private String saleTel;

    private String offerGoodsIds;
}
