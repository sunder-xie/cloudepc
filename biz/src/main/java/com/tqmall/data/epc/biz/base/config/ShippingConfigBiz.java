package com.tqmall.data.epc.biz.base.config;


import com.tqmall.data.epc.bean.entity.baseConfig.EpcShippingConfigDO;

import java.util.List;

/**
 * Created by zxg on 16/8/30.
 * 16:46
 * no bug,以后改代码的哥们，祝你好运~！！
 * 发货方式 biz 表
 */
public interface ShippingConfigBiz {

    EpcShippingConfigDO getByPrimaryId(Integer id);

    /**
     * 查询全部的配送方式
     *
     * @return
     */
    List<EpcShippingConfigDO> getAll();
}
