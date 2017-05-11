package com.tqmall.data.epc.biz.lop;

import com.tqmall.core.common.entity.Result;

import java.util.List;

/**
 * Created by huangzhangting on 16/8/1.
 */
public interface CateBiz {
    /**
     * 根据关键词搜索电商三级类目名称
     * @param keyword
     * @return
     */
    Result<List<String>> searchCateName(String keyword);
}
