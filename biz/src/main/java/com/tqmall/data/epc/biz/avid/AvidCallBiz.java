package com.tqmall.data.epc.biz.avid;

import com.tqmall.core.common.entity.Result;
import com.tqmall.data.epc.bean.bizBean.avid.AvidCallBO;
import com.tqmall.data.epc.bean.param.avid.CancelAvidCallPO;
import com.tqmall.data.epc.bean.param.avid.ModifyAvidCallPO;
import com.tqmall.data.epc.bean.param.avid.TurnWishPO;

import java.util.Date;

/**
 * Created by huangzhangting on 16/10/25.
 */
public interface AvidCallBiz {
    /**
     * 创建管家急呼
     * @param carSeriesId 车系id，level 2级别
     * @return
     */
    Result<Integer> createAvidCall(Integer carSeriesId);

    /**
     * 根据主键id查询急呼数据
     * @param id
     * @return
     */
    Result<AvidCallBO> getAvidCallById(Integer id);

    /**
     * 取消急呼
     * @param param
     * @return
     */
    Result cancelAvidCall(CancelAvidCallPO param);

    /**
     * 修改急呼数据
     * @param param
     * @return
     */
    Result modifyAvidCall(ModifyAvidCallPO param);

    /**
     * 转需求单
     * @param param
     * @return 返回需求单id
     */
    Result<Integer> turnWish(TurnWishPO param);

    /**
     * 查询未跟进的记录数
     * @return
     */
    Result<Long> getNewDataCount(Date time);

}
