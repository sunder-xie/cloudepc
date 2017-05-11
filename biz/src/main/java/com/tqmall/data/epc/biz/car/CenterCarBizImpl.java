package com.tqmall.data.epc.biz.car;


import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.tqmall.athena.domain.result.center.car.CenterCarDTO;
import com.tqmall.data.epc.bean.bizBean.car.CenterCarBO;
import com.tqmall.data.epc.common.redis.RedisClientTemplate;
import com.tqmall.data.epc.common.redis.RedisKeyBean;
import com.tqmall.data.epc.common.utils.BdUtil;
import com.tqmall.data.epc.common.utils.JsonUtil;
import com.tqmall.data.epc.exterior.dubbo.car.CenterCarExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by huangzhangting on 16/2/2.
 */
@Service
public class CenterCarBizImpl implements CenterCarBiz {
    @Autowired
    private CenterCarExt centerCarExt;

    @Autowired
    private RedisClientTemplate redisClient;


    @Override
    public List<CenterCarBO> getCarListForFilter(Integer modelId) {
        String key = String.format(RedisKeyBean.SEARCH_CARS_MODEL_ID_KEY, modelId);
        String redisStr = redisClient.get(key);
        if(redisStr != null)
            return JsonUtil.jsonToList(redisStr, CenterCarBO.class);

        List<CenterCarDTO> centerCarDTOList = centerCarExt.getCarListByModelId(modelId);
        if(centerCarDTOList.isEmpty()){
            return Lists.newArrayList();
        }

        List<CenterCarBO> carBOList = BdUtil.do2bo4List(centerCarDTOList, CenterCarBO.class);
        for(CenterCarBO carBO : carBOList){
            carBO.setName(carBO.getYear() + "款 " + carBO.getName());
        }
        Collections.sort(carBOList, new Comparator<CenterCarBO>() {
            @Override
            public int compare(CenterCarBO o1, CenterCarBO o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });

        redisClient.lazySet(key, carBOList, RedisKeyBean.RREDIS_EXP_DAY);

        return carBOList;
    }

    @Override
    public List<CenterCarBO> getModelForFilter(Integer seriesId) {
        List<CenterCarDTO> modelDTOList = centerCarExt.getCenterCarByPid(seriesId);
        if(modelDTOList.isEmpty()){
            return Lists.newArrayList();
        }

        List<CenterCarBO> carModelList = BdUtil.do2bo4List(modelDTOList, CenterCarBO.class);
        if(carModelList.size()==1){
            CenterCarBO carModel = carModelList.get(0);
            carModel.setCustomAttr(getCarListForFilter(carModel.getId()));
        }else{
            Collections.sort(carModelList, new Comparator<CenterCarBO>() {
                @Override
                public int compare(CenterCarBO o1, CenterCarBO o2) {
                    return o1.getName().compareTo(o2.getName());
                }
            });
        }
        return carModelList;
    }

    //处理品牌
    private Map<String, List<CenterCarBO>> handleCarBrand(List<CenterCarDTO> brandDTOList){
        Collections.sort(brandDTOList, new Comparator<CenterCarDTO>() {
            @Override
            public int compare(CenterCarDTO o1, CenterCarDTO o2) {
                int firstWordFlag = o1.getFirstWord().compareTo(o2.getFirstWord());
                if(firstWordFlag==0){
                    return o1.getName().compareTo(o2.getName());
                }
                return firstWordFlag;
            }
        });

        Map<String, List<CenterCarBO>> firstWordMap = new TreeMap<>();

        List<CenterCarDTO> seriesDTOList = centerCarExt.getCenterCarByLevel(2);

        for(CenterCarDTO carBrand : brandDTOList){
            CenterCarBO centerCarBO = BdUtil.do2bo(carBrand, CenterCarBO.class);

            List<CenterCarBO> carBrandList = firstWordMap.get(carBrand.getFirstWord());
            if(carBrandList==null){
                carBrandList = new ArrayList<>();
                firstWordMap.put(carBrand.getFirstWord(), carBrandList);
            }
            carBrandList.add(centerCarBO);

            //处理车系
            centerCarBO.setCustomAttr(handleCarSeries(seriesDTOList, carBrand.getId()));
        }

        return firstWordMap;
    }

    //处理车系
    private Map<String, List<CenterCarBO>> handleCarSeries(List<CenterCarDTO> centerCarDTOList, Integer brandId){
        List<CenterCarDTO> seriesDTOList = Lists.newArrayList();
        for(CenterCarDTO carDTO : centerCarDTOList){
            if(carDTO.getPid().equals(brandId)) {
                seriesDTOList.add(carDTO);
            }
        }

        Collections.sort(seriesDTOList, new Comparator<CenterCarDTO>() {
            @Override
            public int compare(CenterCarDTO o1, CenterCarDTO o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });

        Map<String, List<CenterCarBO>> companyMap = new HashMap<>();
        for(CenterCarDTO carSeries : seriesDTOList){
            String company = carSeries.getCompany();
            if("进口".equals(carSeries.getImportInfo())){
                company = company + "（进口）";
            }
            List<CenterCarBO> carSeriesList = companyMap.get(company);
            if(carSeriesList==null){
                carSeriesList = new ArrayList<>();
                companyMap.put(company, carSeriesList);
            }
            carSeriesList.add(BdUtil.do2bo(carSeries, CenterCarBO.class));
        }

        return companyMap;
    }

    @Override
    public Map<String, List<CenterCarBO>> getBrandForFilter() {
        String redisStr = redisClient.get(RedisKeyBean.SEARCH_CAR_BRAND_KEY);
        if(redisStr != null){
            return JsonUtil.jsonToObject(redisStr, Map.class);
        }

        List<CenterCarDTO> centerCarDTOList = centerCarExt.getCenterCarByLevel(1);
        if(centerCarDTOList.isEmpty()){
            return Maps.newHashMap();
        }

        Map<String, List<CenterCarBO>> map = handleCarBrand(centerCarDTOList);
        redisClient.lazySet(RedisKeyBean.SEARCH_CAR_BRAND_KEY, map, RedisKeyBean.RREDIS_EXP_DAY);
        return map;
    }

    @Override
    public List<CenterCarBO> getCarBrands() {
        String redisStr = redisClient.get(RedisKeyBean.ALL_CAR_BRANDS);
        if(redisStr != null){
            return JsonUtil.jsonToList(redisStr, CenterCarBO.class);
        }
        List<CenterCarDTO> centerCarDTOList = centerCarExt.getCenterCarByLevel(1);
        if(centerCarDTOList.isEmpty()){
            return new ArrayList<>();
        }
        List<CenterCarBO> list = BdUtil.do2bo4List(centerCarDTOList, CenterCarBO.class);
        Collections.sort(list, new Comparator<CenterCarBO>() {
            @Override
            public int compare(CenterCarBO o1, CenterCarBO o2) {
//                String str1 = o1.getFirstWord()+o1.getName();
//                String str2 = o2.getFirstWord()+o2.getName();
//                return str1.compareTo(str2);
                return o1.getFirstWord().compareTo(o2.getFirstWord());
            }
        });
        redisClient.lazySet(RedisKeyBean.ALL_CAR_BRANDS, list, RedisKeyBean.RREDIS_EXP_DAY);
        return list;
    }

}
