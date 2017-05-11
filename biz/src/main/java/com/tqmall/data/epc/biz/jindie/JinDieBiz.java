package com.tqmall.data.epc.biz.jindie;

import com.tqmall.data.epc.bean.entity.jindie.EpcJinDieTaskDO;

import java.util.List;

/**
 * Created by zxg on 16/9/1.
 * 16:16
 * no bug,以后改代码的哥们，祝你好运~！！
 */
public interface JinDieBiz {

    //根据 result_status 获得 列表
    List<EpcJinDieTaskDO> getListByResultStatus(Integer resultStatus);

    /**
     * 插入数据到金蝶处理表
     * @param orderId epc_order_id 主键
     * @param orderSn epc_order_sn 订单编号
     * @param orgId   供应商 id（云配）
     * @return 是否插入成功
     */
    Boolean insertDO(Integer orderId,String orderSn,Integer orgId);

    Boolean updateTaskFail(Integer primaryId,String failReason);

    //执行进金蝶
    Boolean toJinDieReport(EpcJinDieTaskDO jinDieTaskDO);


}
