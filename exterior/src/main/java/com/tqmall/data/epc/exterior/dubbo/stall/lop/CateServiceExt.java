package com.tqmall.data.epc.exterior.dubbo.stall.lop;

import com.tqmall.data.epc.bean.bizBean.lop.CateBO;

import java.util.List;

/**
 * Created by huangzhangting on 16/8/1.
 */
public interface CateServiceExt {
    /**
     * 根据关键词搜索电商三级类目
     * @param keyword
     * @return
     */
    List<CateBO> searchByKeyword(String keyword);
}
