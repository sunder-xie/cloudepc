package com.tqmall.data.epc.bean.param.lop;

import com.tqmall.data.epc.bean.param.BaseQueryParam;
import lombok.Data;

/**
 * 需求单查询参数对象
 * Created by huangzhangting on 16/7/30.
 */
@Data
public class WishListParam extends BaseQueryParam {
    //门店名称
    private String companyName;
    //联系电话
    private String telephone;
    //需求单号
    private String wishListSn;
    //需求单状态
    private String status;

    //创建时间开始。格式：yyyy-MM-dd HH:mm:ss
    private String createStartDate;
    //创建时间结束。格式：yyyy-MM-dd HH:mm:ss
    private String createEndDate;
}
