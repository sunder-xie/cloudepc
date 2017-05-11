package com.tqmall.data.epc.client.bean.dto.shipping;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by huangzhangting on 16/8/30.
 */
@Data
public class ShippingConfigDTO implements Serializable {
    private Integer id; //自增id
    private String shippingName; //发货方式名称
}
