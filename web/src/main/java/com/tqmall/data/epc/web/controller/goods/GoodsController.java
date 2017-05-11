package com.tqmall.data.epc.web.controller.goods;

import com.tqmall.athena.domain.result.center.goods.CenterGoodsDTO;
import com.tqmall.data.epc.bean.bizBean.goods.CenterGoodsBO;
import com.tqmall.data.epc.biz.car.CarCategoryBiz;
import com.tqmall.data.epc.biz.goods.CenterGoodsBiz;
import com.tqmall.data.epc.common.bean.EpcError;
import com.tqmall.data.epc.common.utils.ResultUtil;
import com.tqmall.data.epc.exterior.dubbo.goods.CenterGoodsExt;
import com.tqmall.data.epc.web.BaseController;
import com.tqmall.core.common.entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by huangzhangting on 16/7/6.
 */
@Controller
@RequestMapping("goods")
public class GoodsController extends BaseController{
    @Autowired
    private CarCategoryBiz carCategoryBiz;
    @Autowired
    private CenterGoodsBiz centerGoodsBiz;
    @Autowired
    private CenterGoodsExt centerGoodsExt;


    @RequestMapping("getGoodsByCarCate")
    @ResponseBody
    public Result getGoodsByCarCate(Integer carId, Integer catId) {
        List<CenterGoodsBO> resultList = centerGoodsBiz.getListByCarIdCatId(carId, catId);

        if (resultList.isEmpty()) {
            return ResultUtil.errorResult(EpcError.ARG_ERROR);
        }
        Collections.sort(resultList, new Comparator<CenterGoodsBO>() {
            @Override
            public int compare(CenterGoodsBO o1, CenterGoodsBO o2) {
                return o1.getOeNumber().compareTo(o2.getOeNumber());
            }
        });
        return ResultUtil.successResult(resultList);
    }

    @RequestMapping("picGoods")
    public ModelAndView picGoods(String picNum, Integer carId, String from){
        String carName = carCategoryBiz.getOnlineCarNameById(carId);

        ModelAndView modelAndView = new ModelAndView(webVersion + "/cloudepc/goods/picGoods");
        modelAndView.addObject("picNum", picNum);
        modelAndView.addObject("carId", carId);
        modelAndView.addObject("carName", carName);

        setBreadcrumb("CATE_PIC_GOODS");

        return modelAndView;
    }

    @RequestMapping("getGoodsByPicNumAndCar")
    @ResponseBody
    public Result getGoodsByPicNumAndCar(String picNum, Integer carId){
        List<CenterGoodsDTO> list = centerGoodsExt.getGoodsByPicNumAndCar(picNum, carId);
        if(list.isEmpty()){
            return ResultUtil.errorResult(EpcError.NO_DATA_ERROR);
        }
        return ResultUtil.successResult(list);
    }

    @RequestMapping("getGcDetailByPicNumAndCar")
    @ResponseBody
    public Result getGcDetailByPicNumAndCar(String picNum, Integer carId){
        return centerGoodsBiz.getGcDetailByPicNumAndCar(picNum, carId);
    }

}
