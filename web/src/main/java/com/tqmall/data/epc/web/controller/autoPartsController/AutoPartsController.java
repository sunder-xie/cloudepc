package com.tqmall.data.epc.web.controller.autoPartsController;

import com.tqmall.athena.domain.result.center.goods.CenterGoodsCarSubjoinDTO;
import com.tqmall.core.common.entity.Result;
import com.tqmall.data.epc.bean.bizBean.car.CarBO;
import com.tqmall.data.epc.bean.bizBean.goods.CenterGoodsBO;
import com.tqmall.data.epc.bean.bizBean.goods.CenterGoodsDetailBO;
import com.tqmall.data.epc.biz.car.CarCategoryBiz;
import com.tqmall.data.epc.biz.goods.CenterGoodsBiz;
import com.tqmall.data.epc.common.bean.EpcError;
import com.tqmall.data.epc.common.utils.ResultUtil;
import com.tqmall.data.epc.exterior.dubbo.goods.CenterGoodsExt;
import com.tqmall.data.epc.web.BaseController;
import com.tqmall.search.dubbo.client.cloudepc.result.CenterGoodsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("autoParts")
public class AutoPartsController extends BaseController {

    @Autowired
    private CarCategoryBiz carCategoryBiz;
    @Autowired
    private CenterGoodsBiz centerGoodsBiz;
    @Autowired
    private CenterGoodsExt centerGoodsExt;


    //品牌页面
    @RequestMapping(value = "car/carModel")
    public ModelAndView carModel() {
        ModelAndView modelAndView = new ModelAndView(webVersion + "/cloudepc/car/carModel");

        setBreadcrumb("CAR_MODEL");

        return modelAndView;
    }

    //车型筛选页面
    @RequestMapping(value = "car/carFilter")
    public ModelAndView carFilter(Integer seriesId) {
        ModelAndView modelAndView = new ModelAndView(webVersion + "/cloudepc/car/carFilter");
        modelAndView.addObject("seriesId", seriesId);

        putIntoSession("SERIES_ID", seriesId);

        setBreadcrumb("CAR_FILTER");

        return modelAndView;
    }

    //分类页面
    @RequestMapping(value = "category/category")
    public ModelAndView category(Integer carId, String vinNumber, Integer seriesId) {
        ModelAndView modelAndView = new ModelAndView(webVersion + "/cloudepc/category/category");
        //根据carId获得其全称
        String carName = carCategoryBiz.getOnlineCarNameById(carId);
        if (!StringUtils.isEmpty(vinNumber)) {
            modelAndView.addObject("searchType", "vin");
            modelAndView.addObject("vinNumber", vinNumber);

            putIntoSession("SERIES_ID", seriesId);
            putIntoSession("VIN_NO", vinNumber);
        }
        modelAndView.addObject("carName", carName);
        modelAndView.addObject("carId", carId);

        putIntoSession("CAR_ID", carId);

        setBreadcrumb("CATEGORY_FILTER");

        return modelAndView;
    }

    //配件详情
    @RequestMapping(value = "goods/goodsDetail")
    public ModelAndView goodsDetail(Integer goodsId, String modelId, String from, String st) {
        CenterGoodsBO centerGoodsBO = centerGoodsBiz.getCenterGoodsById(goodsId);
        ModelAndView modelAndView = new ModelAndView(webVersion + "/cloudepc/goods/goodsDetail");
        modelAndView.addObject("goods", centerGoodsBO);
        modelAndView.addObject("goodsId", goodsId);
        modelAndView.addObject("modelId", modelId);
        modelAndView.addObject("from", from);
        modelAndView.addObject("searchType", st);

        setBreadcrumb(from);

        return modelAndView;
    }

    //oe查询结果界面
    @RequestMapping(value = "goods/oe")
    public ModelAndView oe(String oem) {
        ModelAndView modelAndView = new ModelAndView(webVersion + "/cloudepc/oe/oe");
        modelAndView.addObject("searchType", "oe");
        modelAndView.addObject("searchValue", oem);

        putIntoSession("SEARCH_VALUE", oem);
        setBreadcrumb("GOODS");

        return modelAndView;
    }

    //oe查询结果界面
    @RequestMapping(value = "goods/keyword")
    public ModelAndView keyword(String q) {
        ModelAndView modelAndView = new ModelAndView(webVersion + "/cloudepc/oe/oe");
        modelAndView.addObject("searchType", "keyword");
        modelAndView.addObject("searchValue", q);

        putIntoSession("SEARCH_VALUE", q);
        setBreadcrumb("GOODS");

        return modelAndView;
    }

    @RequestMapping(value = "getGoodsDetail")
    @ResponseBody
    public Result getGoodsDetail(Integer goodsId, Integer carId) {
        CenterGoodsDetailBO centerGoodsDetailBO = centerGoodsBiz.getGoodsDetailByGoodsIdCarId(goodsId, carId);
        if (centerGoodsDetailBO == null) {
            return ResultUtil.errorResult(EpcError.ARG_ERROR);
        }
        return ResultUtil.successResult(centerGoodsDetailBO);
    }


    // ========== hzt 添加 ==========
    @RequestMapping("carModelForGoods")
    @ResponseBody
    public Result carModelForGoods(Integer goodsId) {
        Map<String, Object> map = centerGoodsBiz.getCarModelForGoods(goodsId);
        if (map == null) {
            return ResultUtil.errorResult(EpcError.ARG_ERROR);
        }

        return ResultUtil.successResult(map);
    }

    @RequestMapping("carForGoods")
    @ResponseBody
    public Result carForGoods(Integer goodsId) {
        Map<String, Object> map = centerGoodsBiz.getCarForGoods(goodsId);
        if (map == null) {
            return ResultUtil.errorResult(EpcError.ARG_ERROR);
        }

        return ResultUtil.successResult(map);
    }

    @RequestMapping("getSubjoinByGoodsModel")
    @ResponseBody
    public Result getSubjoinByGoodsModel(Integer goodsId, Integer modelId) {
        CenterGoodsCarSubjoinDTO subjoinDTO = centerGoodsExt.getSubjoinByGoodsModel(goodsId, modelId);
        if (subjoinDTO == null) {
            return ResultUtil.errorResult(EpcError.ARG_ERROR);
        }
        return ResultUtil.successResult(subjoinDTO);
    }

    @RequestMapping("goodsSearch")
    @ResponseBody
    public Result<CenterGoodsDTO> goodsSearch(String keyword, String oeNumber, Integer modelId, Integer cateId,
                              Integer pageIndex, Integer pageSize) {

        return centerGoodsBiz.goodsSearch(keyword, oeNumber, modelId, cateId, pageIndex, pageSize);
    }

    //根据goodsId查询适配车型，以及第一个车型对应的备注信息
    @RequestMapping("modelAndSubjoinForGoods")
    @ResponseBody
    public Map modelAndSubjoinForGoods(Integer goodsId) {

        Map<String, Object> carMap = centerGoodsBiz.getCarModelForGoods(goodsId);
        if (carMap == null) {
            return null;
        }

        Map<String, Map<String, List<Object>>> brandMap = (Map) carMap.get("brandMap");
        Integer modelId = null;
        String modelName = "";

        loop1:
        for (Map.Entry<String, Map<String, List<Object>>> brandEt : brandMap.entrySet()) {
            for (Map.Entry<String, List<Object>> companyEt : brandEt.getValue().entrySet()) {
                Object obj = companyEt.getValue().get(0);
                if (obj instanceof CarBO) {
                    CarBO carBO = (CarBO) obj;
                    modelId = carBO.getId();
                    modelName = carBO.getCarName();
                } else {
                    Map map = (Map) obj;
                    modelId = (Integer) map.get("id");
                    modelName = map.get("carName").toString();
                }
                break loop1;
            }
        }

        CenterGoodsCarSubjoinDTO subjoinDTO = centerGoodsExt.getSubjoinByGoodsModel(goodsId, modelId);

        Map<String, Object> result = new HashMap<>();
        result.put("carMap", carMap);
        result.put("modelName", modelName);
        result.put("modelId", modelId);
        result.put("modelSubjoin", subjoinDTO);
        result.put("goodsId", goodsId);

        return result;
    }

}
