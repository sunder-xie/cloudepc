package com.tqmall.data.epc.client.server.avid;

import com.tqmall.core.common.entity.Result;
import com.tqmall.data.epc.client.bean.dto.avid.AvidCallDTO;
import com.tqmall.data.epc.client.bean.param.avid.CancelAvidCallParam;
import com.tqmall.data.epc.client.bean.param.avid.ModifyAvidCallParam;
import com.tqmall.data.epc.client.bean.param.avid.TurnWishParam;

/**
 * Created by huangzhangting on 16/10/25.
 */
public interface AvidCallService {
    /**
     * 根据主键id查询急呼数据
     * @param id
     * @return
     */
    Result<AvidCallDTO> getAvidCallById(Integer id);

    /**
     * 取消急呼
     * @param param
     * @return
     */
    Result cancelAvidCall(CancelAvidCallParam param);

    /**
     * 修改急呼数据
     * @param param
     * @return
     */
    Result modifyAvidCall(ModifyAvidCallParam param);

    /**
     * 转需求单
     * @param param
     * @return 返回需求单id
     */
    Result<Integer> turnWish(TurnWishParam param);

    /**
     * 查询全部未跟进的记录数
     * @return
     */
    Result<Long> getAllNewDataNum();

    /**
     * 查询5分钟内未跟进的记录数
     * @return
     */
    Result<Long> getFiveMinNewDataNum();
}
