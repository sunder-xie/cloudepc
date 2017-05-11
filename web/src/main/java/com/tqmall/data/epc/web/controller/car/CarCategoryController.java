package com.tqmall.data.epc.web.controller.car;

import com.tqmall.data.epc.bean.bizBean.car.CarBO;
import com.tqmall.data.epc.bean.bizBean.car.CarInfoBO;
import com.tqmall.data.epc.bean.bizBean.car.CenterCarBO;
import com.tqmall.data.epc.biz.car.CarCategoryBiz;
import com.tqmall.data.epc.biz.car.CenterCarBiz;
import com.tqmall.data.epc.common.utils.ResultUtil;
import com.tqmall.data.epc.web.BaseController;
import com.tqmall.core.common.entity.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


/**
 * Created by huangzhangting on 16/1/27.
 */
@Slf4j
@Controller
@RequestMapping("epc/car")
public class CarCategoryController extends BaseController {
    @Autowired
    private CenterCarBiz centerCarBiz;
    @Autowired
    private CarCategoryBiz carCategoryBiz;


    @RequestMapping("carListForFilter")
    @ResponseBody
    public Result carListForFilter(Integer modelId){
        return ResultUtil.successResult(centerCarBiz.getCarListForFilter(modelId));
    }

    @RequestMapping("carModelForFilter")
    @ResponseBody
    public Result carModelForFilter(Integer seriesId){
        return ResultUtil.successResult(centerCarBiz.getModelForFilter(seriesId));
    }

    @RequestMapping("carBrandForFilter")
    @ResponseBody
    public Result carBrandForFilter(){
        return ResultUtil.successResult(centerCarBiz.getBrandForFilter());
    }

    /**
     * 根据vin码查询车型信息
     * @param vin
     * @return
     */
    @RequestMapping(value = "getOnlineCarByVin",method = RequestMethod.POST)
    @ResponseBody
    public Result<List<CarBO>> getOnlineCarByVin(String vin){
        return carCategoryBiz.getOnlineIdByVin(request, vin);
    }

    /**
     * 根据pid，查询车型数据，四级结构
     * @param pid
     * @return
     */
    @RequestMapping("carInfoByPid")
    @ResponseBody
    public Result<List<CarInfoBO>> getCarInfoByPid(Integer pid){
        return carCategoryBiz.getCarInfoByPid(pid);
    }

    /**
     * 查询全部品牌
     * @return
     */
    @RequestMapping("getCarBrands")
    @ResponseBody
    public Result<List<CenterCarBO>> getCarBrands(){
        return ResultUtil.successResult(centerCarBiz.getCarBrands());
    }


    /**
     * 管家急呼车系筛选
     * @return
     */
    @RequestMapping("allCarBrandSeries")
    @ResponseBody
    public Result allCarBrandSeries(){
        return ResultUtil.successResult(carCategoryBiz.getBrandForFilter());
    }

}
