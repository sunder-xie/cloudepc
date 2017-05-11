package com.tqmall.data.epc.bean.bizBean.order;

import com.tqmall.data.epc.bean.entity.order.EpcOrderGoodsDO;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by zxg on 16/8/29.
 * 09:23 筛选购买订单 list bo 类
 * no bug,以后改代码的哥们，祝你好运~！！
 */
@Data
public class OrderListShowBO {
    //创建时间
    private String createTime;

    //订单号
    private String orderSn;

    //订单状态
    private Integer orderStatus;
    private String orderStatusName;

    //支付名称：支付宝 网银
    private String payName;

    //含有的商品列表
    private List<EpcOrderGoodsDO> orderGoodsList;

    //================ START LYJ ================//
    //订单金额
    private BigDecimal orderAmount;
    //================ END LYJ ================//

}
