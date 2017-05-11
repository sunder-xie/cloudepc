package com.tqmall.data.epc.biz.car;

import com.tqmall.data.epc.bean.bizBean.car.CarBO;
import com.tqmall.core.common.entity.Result;
import com.tqmall.data.epc.bean.bizBean.car.CarInfoBO;
import com.tqmall.data.epc.bean.bizBean.car.CenterCarBO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created by huangzhangting on 16/2/1.
 */
public interface CarCategoryBiz {

    /**
     * 根据car主键获得其全称
     * @param id
     * @return
     */
    String getOnlineCarNameById(Integer id);

    /**
     * 根据vin 获得线上车型id
     * @param vin
     * @return
     */
    Result<List<CarBO>> getOnlineIdByVin(HttpServletRequest request, String vin);

    /**
     * 根据pid，查询车型数据，四级结构
     * @param pid
     * @return
     */
    Result<List<CarInfoBO>> getCarInfoByPid(Integer pid);

    /**
     * 全部车品牌筛选页面
     * @return
     */
    Map<String, List<CenterCarBO>> getBrandForFilter();

}
