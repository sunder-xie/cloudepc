package com.tqmall.data.epc.biz.base;

import com.tqmall.core.common.entity.Result;
import com.tqmall.data.epc.bean.bizBean.goods.GoodsQualityBO;

import java.util.List;

/**
 * Created by huangzhangting on 16/9/6.
 */
public interface CommonBiz {
    /**
     * 获取商品品质
     * @return
     */
    Result<List<GoodsQualityBO>> getGoodsQuality();

    /**
     * 添加页面引导记录
     * @param pageUriId
     * @return
     */
    Result addPageGuideRecord(Integer pageUriId);

    /**
     * 设置当前用户历史页面访问记录（目前仅在登录时调用）
     */
    void setUserPvHisRecord(String mobile);

    /**
     * 刷新session缓存，方便测试
     */
    void refreshSessionCache();

}
