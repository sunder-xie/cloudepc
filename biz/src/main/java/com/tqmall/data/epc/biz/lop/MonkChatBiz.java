package com.tqmall.data.epc.biz.lop;

import com.tqmall.core.common.entity.Result;
import com.tqmall.data.epc.bean.param.lop.ImWishSnParam;

import java.util.List;

/**
 * Created by huangzhangting on 16/8/11.
 */
public interface MonkChatBiz {
    /**
     * 缓存今日聊到的需求单号
     * @param param
     * @return
     */
    Result<List<String>> cacheWishSn(ImWishSnParam param);

    /**
     * 查询缓存的需求单号
     * @param chatObjectId
     * @return
     */
    Result<List<String>> getCacheWishSn(Integer chatObjectId);
}
