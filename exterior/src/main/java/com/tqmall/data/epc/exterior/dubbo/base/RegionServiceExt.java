package com.tqmall.data.epc.exterior.dubbo.base;


import com.tqmall.data.epc.bean.bizBean.ZTree;
import com.tqmall.data.epc.bean.bizBean.base.RegionBO;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by huangzhangting on 16/7/5.
 */
public interface RegionServiceExt {

    /**
     * 获取档口已开通城市站和省份
     * 主要用于ztree组件，非 simpleData 模式
     * @return
     */
    List<ZTree> cityZTreeList();

    /**
     * 根据pid查询，子级地区
     * @param pid
     * @return
     */
    List<RegionBO> subRegions(Integer pid);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    RegionBO getRegionById(Integer id);

    /**
     * 根据id，查询地区名称
     * @param regionIds
     * @return
     */
    Map<Integer, String> getRegionNameMap(Collection<Integer> regionIds);
}
