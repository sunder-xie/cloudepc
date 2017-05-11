package com.tqmall.data.epc.biz.goods;

import com.tqmall.athena.domain.result.center.car.CenterCarDTO;
import com.tqmall.athena.domain.result.center.goods.CenterGoodsCarDTO;
import com.tqmall.athena.domain.result.center.goods.CenterGoodsCarDetailDTO;
import com.tqmall.athena.domain.result.center.goods.CenterGoodsDTO;
import com.tqmall.core.common.entity.Result;
import com.tqmall.data.epc.bean.bizBean.car.CarBO;
import com.tqmall.data.epc.bean.bizBean.car.CenterCarBO;
import com.tqmall.data.epc.bean.bizBean.goods.CenterGoodsBO;
import com.tqmall.data.epc.bean.bizBean.goods.CenterGoodsDetailBO;
import com.tqmall.data.epc.biz.shiro.ShiroBiz;
import com.tqmall.data.epc.biz.util.UserUtil;
import com.tqmall.data.epc.common.bean.EpcError;
import com.tqmall.data.epc.common.redis.RedisClientTemplate;
import com.tqmall.data.epc.common.redis.RedisKeyBean;
import com.tqmall.data.epc.common.utils.JsonUtil;
import com.tqmall.data.epc.common.utils.ResultUtil;
import com.tqmall.data.epc.exterior.dubbo.car.CenterCarExt;
import com.tqmall.data.epc.exterior.dubbo.goods.CenterGoodsExt;
import com.tqmall.data.epc.exterior.dubbo.search.SearchExt;
import com.tqmall.search.dubbo.client.cloudepc.param.CloudEpcQueryRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * Created by huangzhangting on 16/2/17.
 */
@Service
public class CenterGoodsBizImpl implements CenterGoodsBiz {
    @Autowired
    private RedisClientTemplate redisClient;

    @Autowired
    private CenterGoodsExt centerGoodsExt;
    @Autowired
    private CenterCarExt centerCarExt;
    @Autowired
    private SearchExt searchExt;
    @Autowired
    private ShiroBiz shiroBiz;

    @Override
    public List<CenterGoodsBO> getListByCarIdCatId(Integer carId, Integer cateId) {
        if (carId == null || cateId == null || carId<1 || cateId<1) {
            return new ArrayList<>();
        }

        String key = String.format(RedisKeyBean.CENTER_GOODS_CAR_CATE_KEY, carId, cateId);
        String redisStr = redisClient.get(key);
        if(redisClient.isNone(redisStr)){
            return new ArrayList<>();
        }
        if(redisStr!=null){
            List<CenterGoodsBO> list = JsonUtil.jsonToList(redisStr, CenterGoodsBO.class);
            if(!UserUtil.isVerifySuccess(shiroBiz.getCurrentUser())) {
                for (CenterGoodsBO goodsBO : list) {
                    goodsBO.setOeNumber(UserUtil.encryptOe(goodsBO.getOeNumber()));
                }
            }
            return list;
        }

        List<CenterGoodsBO> resultList = new ArrayList<>();

        List<CenterGoodsCarDTO> goodsCarList = centerGoodsExt.getGoodsCarByGoodsCar(null, carId);
        List<CenterGoodsDTO> goodsList = centerGoodsExt.getGoodsByCatId(cateId);

        for (CenterGoodsCarDTO centerGoodsCarDTO : goodsCarList) {
            Integer goodsId = centerGoodsCarDTO.getGoodsId();

            for (CenterGoodsDTO centerGoodsDTO : goodsList) {
                if (goodsId.equals(centerGoodsDTO.getId())) {
                    CenterGoodsBO centerGoodsBO = new CenterGoodsBO();
                    centerGoodsBO.setGoodsId(goodsId);
                    centerGoodsBO.setOeNumber(centerGoodsDTO.getOeNumber());
                    centerGoodsBO.setPartName(centerGoodsDTO.getPartName());

//                    CenterGoodsCarDetailDTO detailDTO = centerGoodsExt.getGoodsCarDetailByGoodsCar(goodsId, carId);
//                    if(detailDTO!=null){
//                        centerGoodsBO.setEpcPicNum(detailDTO.getEpcPicNum());
//                        centerGoodsBO.setEpcIndex(detailDTO.getEpcIndex());
//                        centerGoodsBO.setRemarks(detailDTO.getGoodsCarRemarks());
//                        centerGoodsBO.setAmountUsed(detailDTO.getApplicationAmount());
//                        centerGoodsBO.setEpcPicList(detailDTO.getEpcPicList());
//
//                    }

                    resultList.add(centerGoodsBO);
                }
            }
        }

        if(resultList.isEmpty()){
            redisClient.setNone(key);
        }else{
            redisClient.lazySet(key, resultList, RedisKeyBean.RREDIS_EXP_DAY);

            if(!UserUtil.isVerifySuccess(shiroBiz.getCurrentUser())) {
                for (CenterGoodsBO goodsBO : resultList) {
                    goodsBO.setOeNumber(UserUtil.encryptOe(goodsBO.getOeNumber()));
                }
            }
        }

        return resultList;
    }

//    @Override
//    public CenterGoodsBO getGoodsForOeSearch(String oeNumber) {
//        CenterGoodsDTO goodsDTO = centerGoodsExt.getGoodsByOeNumber(oeNumber);
//        if(goodsDTO==null)
//            return null;
//
//        CenterGoodsBO centerGoodsBO = BdUtil.do2bo(goodsDTO, CenterGoodsBO.class);
//        centerGoodsBO.setGoodsId(goodsDTO.getId());
//
//        return centerGoodsBO;
//    }


    @Override
    public CenterGoodsBO getCenterGoodsById(Integer goodsId) {
        CenterGoodsBO centerGoodsBO = new CenterGoodsBO();

        CenterGoodsDTO goodsDTO = centerGoodsExt.getGoodsByPrimaryId(goodsId);
        if(goodsDTO==null){
            return centerGoodsBO;
        }

        centerGoodsBO.setGoodsId(goodsDTO.getId());
        centerGoodsBO.setPartName(goodsDTO.getPartName());

        if(UserUtil.isVerifySuccess(shiroBiz.getCurrentUser())) {
            centerGoodsBO.setOeNumber(goodsDTO.getOeNumber());
        }else{
            centerGoodsBO.setOeNumber(UserUtil.encryptOe(goodsDTO.getOeNumber()));
        }

        return centerGoodsBO;
    }

    @Override
    public Map<String, Object> getCarModelForGoods(Integer goodsId) {
        String key = String.format(RedisKeyBean.CAR_MODEL_GID_KEY, goodsId);
        String redisStr = redisClient.get(key);
        if(redisStr != null) {
            return JsonUtil.jsonToObject(redisStr, Map.class);
        }

        List<CenterGoodsCarDTO> goodsCarDTOList = centerGoodsExt.getGoodsCarByGoodsCar(goodsId, null);
        if(goodsCarDTOList.isEmpty()){
            return null;
        }

        //查询品牌信息
        List<CenterCarDTO> brandList = centerCarExt.getCenterCarByLevel(1);

        Map<String, Map<String, List<CarBO>>> brandMap = new TreeMap<>();
        Map<String, String> brandLogoMap = new HashMap<>();
        Set<Integer> modelIdSet = new HashSet<>();
        for(CenterGoodsCarDTO goodsCar : goodsCarDTOList){
            //组装车型map
            if(modelIdSet.add(goodsCar.getModelId())){
                CenterCarDTO carModel = centerCarExt.getCenterCarById(goodsCar.getModelId());
                if(carModel==null)
                    continue;

                Map<String, List<CarBO>> companyMap = brandMap.get(carModel.getBrand());
                if(companyMap==null){
                    companyMap = new TreeMap<>();
                    brandMap.put(carModel.getBrand(), companyMap);

                    for(CenterCarDTO brand : brandList){
                        if(carModel.getBrand().equals(brand.getName())){
                            brandLogoMap.put(carModel.getBrand(), brand.getLogo());
                            break;
                        }
                    }
                }

                String company = carModel.getCompany();
                if("进口".equals(carModel.getImportInfo())){
                    company = company + "（进口）";
                }
                List<CarBO> carBOList = companyMap.get(company);
                if(carBOList==null){
                    carBOList = new ArrayList<>();
                    companyMap.put(company, carBOList);
                }
                carBOList.add(new CarBO(carModel.getId(), carModel.getName()));
            }

        }

        //车型排序
        for(Map.Entry<String, Map<String, List<CarBO>>> brandEt : brandMap.entrySet()){
            for(Map.Entry<String, List<CarBO>> companyEt : brandEt.getValue().entrySet()){
                Collections.sort(companyEt.getValue(), new Comparator<CarBO>() {
                    @Override
                    public int compare(CarBO o1, CarBO o2) {
                        return o1.getCarName().compareTo(o2.getCarName());
                    }
                });
            }
        }

        Map<String, Object> map = new HashMap<>();
        map.put("brandMap", brandMap);
        map.put("brandLogoMap", brandLogoMap);

        redisClient.lazySet(key, map, RedisKeyBean.RREDIS_EXP_DAY);

        return map;
    }

    @Override
    public Map<String, Object> getCarForGoods(Integer goodsId) {
        String key = String.format(RedisKeyBean.CAR_GID_KEY, goodsId);
        String redisStr = redisClient.get(key);
        if(redisStr != null) {
            return JsonUtil.jsonToObject(redisStr, Map.class);
        }

        List<CenterGoodsCarDTO> goodsCarDTOList = centerGoodsExt.getGoodsCarByGoodsCar(goodsId, null);
        if(goodsCarDTOList.isEmpty()){
            return null;
        }

        //查询品牌信息
        List<CenterCarDTO> brandList = centerCarExt.getCenterCarByLevel(1);

        Map<String, Map<String, List<CarBO>>> brandMap = new TreeMap<>();
        Map<String, String> brandLogoMap = new HashMap<>();
        Set<Integer> modelIdSet = new HashSet<>();
        Set<Integer> carIdSet = new HashSet<>();
        Map<Integer, List<CenterCarBO>> carListMap = new HashMap<>();
        for(CenterGoodsCarDTO goodsCar : goodsCarDTOList){
            //组装车型map
            if(modelIdSet.add(goodsCar.getModelId())){
                CenterCarDTO carModel = centerCarExt.getCenterCarById(goodsCar.getModelId());
                if(carModel==null)
                    continue;

                Map<String, List<CarBO>> companyMap = brandMap.get(carModel.getBrand());
                if(companyMap==null){
                    companyMap = new TreeMap<>();
                    brandMap.put(carModel.getBrand(), companyMap);

                    for(CenterCarDTO brand : brandList){
                        if(carModel.getBrand().equals(brand.getName())){
                            brandLogoMap.put(carModel.getBrand(), brand.getLogo());
                            break;
                        }
                    }
                }

                String company = carModel.getCompany();
                if("进口".equals(carModel.getImportInfo())){
                    company = company + "（进口）";
                }
                List<CarBO> carBOList = companyMap.get(company);
                if(carBOList==null){
                    carBOList = new ArrayList<>();
                    companyMap.put(company, carBOList);
                }
                carBOList.add(new CarBO(carModel.getId(), carModel.getName()));
            }

            //组装车款map
            if(carIdSet.add(goodsCar.getCarId())){
                List<CenterCarBO> centerCarBOList = carListMap.get(goodsCar.getModelId());
                if(centerCarBOList==null){
                    centerCarBOList = new ArrayList<>();
                    carListMap.put(goodsCar.getModelId(), centerCarBOList);
                }
                CenterCarDTO car = centerCarExt.getCenterCarById(goodsCar.getCarId());
                if(car==null){
                    continue;
                }
                CenterCarBO centerCarBO = new CenterCarBO();
                centerCarBO.setId(goodsCar.getCarId());
                centerCarBO.setPower(car.getPower());
                centerCarBO.setYear(car.getYear());
                centerCarBO.setName(car.getYear()+"款 "+car.getName());
                centerCarBOList.add(centerCarBO);
            }
        }

        Map<String, Object> map = new HashMap<>();
        map.put("brandMap", brandMap);
        map.put("brandLogoMap", brandLogoMap);
        map.put("carListMap", carListMap);

        redisClient.lazySet(key, map, RedisKeyBean.RREDIS_EXP_DAY);

        return map;
    }

    @Override
    public CenterGoodsDetailBO getGoodsDetailByGoodsIdCarId(Integer goodsId, Integer carId) {
        if(goodsId==null || goodsId<1 || carId==null || carId<1){
            return null;
        }
        String key = String.format(RedisKeyBean.CENTER_GOODS_DETAIL_KEY, goodsId, carId);
        String redisStr = redisClient.get(key);
        CenterGoodsDetailBO centerGoodsDetailBO;
        if(redisStr != null){
            centerGoodsDetailBO = JsonUtil.jsonToObject(redisStr, CenterGoodsDetailBO.class);
//            handleGoodsQuote(centerGoodsDetailBO, goodsId, carId);
            if(centerGoodsDetailBO != null && !UserUtil.isVerifySuccess(shiroBiz.getCurrentUser())) {
                centerGoodsDetailBO.setOeNumber(UserUtil.encryptOe(centerGoodsDetailBO.getOeNumber()));
            }
            return centerGoodsDetailBO;
        }

        CenterGoodsDTO goodsDTO = centerGoodsExt.getGoodsByPrimaryId(goodsId);
        //CenterCarDTO centerCarDTO = centerCarExt.getCenterCarById(carId);
        CenterGoodsCarDetailDTO detailDTO = centerGoodsExt.getGoodsCarDetailByGoodsCar(goodsId, carId);

        centerGoodsDetailBO = new CenterGoodsDetailBO();
        centerGoodsDetailBO.setCarId(carId);
        centerGoodsDetailBO.setGoodsId(goodsId);
        if (goodsDTO != null) {
            centerGoodsDetailBO.setOeNumber(goodsDTO.getOeNumber());
            centerGoodsDetailBO.setPartName(goodsDTO.getPartName());
        }
//        if (centerCarDTO != null) {
//            centerGoodsDetailBO.setCarId(centerCarDTO.getId());
//            centerGoodsDetailBO.setBrand(centerCarDTO.getBrand());
//            centerGoodsDetailBO.setCompany(centerCarDTO.getCompany());
//            centerGoodsDetailBO.setModel(centerCarDTO.getModel());
//            centerGoodsDetailBO.setName(centerCarDTO.getName());
//            centerGoodsDetailBO.setSeries(centerCarDTO.getSeries());
//            centerGoodsDetailBO.setYear(centerCarDTO.getYear());
//        }
        if (detailDTO != null) {
            centerGoodsDetailBO.setEpcIndex(detailDTO.getEpcIndex());
            centerGoodsDetailBO.setEpcPicNum(detailDTO.getEpcPicNum());
            centerGoodsDetailBO.setRemarks(detailDTO.getGoodsCarRemarks());
            centerGoodsDetailBO.setAmountUsed(detailDTO.getApplicationAmount());

            List<String> epcPicList = detailDTO.getEpcPicList();
            if(!CollectionUtils.isEmpty(epcPicList)) {
                centerGoodsDetailBO.setEpcPic(detailDTO.getEpcPicList().get(0));
                centerGoodsDetailBO.setEpcPicList(detailDTO.getEpcPicList());
            }
        }

        redisClient.lazySet(key, centerGoodsDetailBO, RedisKeyBean.RREDIS_EXP_DAY);

        if(!UserUtil.isVerifySuccess(shiroBiz.getCurrentUser())) {
            centerGoodsDetailBO.setOeNumber(UserUtil.encryptOe(centerGoodsDetailBO.getOeNumber()));
        }

        return centerGoodsDetailBO;
    }

    @Override
    public Result<com.tqmall.search.dubbo.client.cloudepc.result.CenterGoodsDTO> goodsSearch(
            String keyword, String oeNumber, Integer modelId, Integer cateId, Integer pageIndex, Integer pageSize) {

        CloudEpcQueryRequest queryRequest = new CloudEpcQueryRequest();
        if(keyword != null){
            keyword = keyword.trim();
            if(!"".equals(keyword)){
                queryRequest.setQ(keyword);
            }
        }
        if(oeNumber != null){
            oeNumber = oeNumber.trim();
            if(!"".equals(oeNumber)){
                queryRequest.setOeNumber(oeNumber.toUpperCase().replaceAll("[^0-9A-Z]", ""));
            }
        }

        if(queryRequest.getQ()==null && queryRequest.getOeNumber()==null){
            return ResultUtil.errorResult(EpcError.ARG_ERROR);
        }

        if(StringUtils.isEmpty(keyword) && StringUtils.isEmpty(oeNumber)){
            return ResultUtil.errorResult(EpcError.ARG_ERROR);
        }

        if(modelId != null) {
            queryRequest.setModelId(modelId);
        }
        if(cateId != null){
            queryRequest.setThirdCateId(cateId);
        }

        Result<com.tqmall.search.dubbo.client.cloudepc.result.CenterGoodsDTO> result
                = searchExt.goodsSearch(queryRequest, pageIndex, pageSize);

        if(result.isSuccess() && !UserUtil.isVerifySuccess(shiroBiz.getCurrentUser())){
            //加密OE码
            List<com.tqmall.search.dubbo.client.cloudepc.result.CenterGoodsDTO.GoodsInfo> goodsInfoList
                    = result.getData().getGoodsInfo();
            for(com.tqmall.search.dubbo.client.cloudepc.result.CenterGoodsDTO.GoodsInfo info : goodsInfoList){
                info.setOeNumber(UserUtil.encryptOe(info.getOeNumber()));
            }
        }

        return result;
    }

    @Override
    public Result<List<CenterGoodsCarDetailDTO>> getGcDetailByPicNumAndCar(String picNum, Integer carId) {
        List<CenterGoodsCarDetailDTO> list = centerGoodsExt.getGcDetailByPicNumAndCar(picNum, carId);
        if(list.isEmpty()){
            return ResultUtil.errorResult(EpcError.NO_DATA_ERROR);
        }

        if(!UserUtil.isVerifySuccess(shiroBiz.getCurrentUser())) {
            for (CenterGoodsCarDetailDTO detailDTO : list) {
                detailDTO.setOeNumber(UserUtil.encryptOe(detailDTO.getOeNumber()));
            }
        }

        return ResultUtil.successResult(list);
    }

}
