package com.tqmall.data.epc.biz.car;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.tqmall.athena.domain.result.carcategory.CarCategoryDTO;
import com.tqmall.athena.domain.result.carcategory.CarDTO;
import com.tqmall.athena.domain.result.carcategory.CarListDTO;
import com.tqmall.data.epc.bean.bizBean.car.CarBO;
import com.tqmall.data.epc.bean.bizBean.car.CarInfoBO;
import com.tqmall.data.epc.bean.bizBean.car.CenterCarBO;
import com.tqmall.data.epc.common.bean.ConstantBean;
import com.tqmall.data.epc.common.bean.EpcError;
import com.tqmall.data.epc.common.redis.RedisClientTemplate;
import com.tqmall.data.epc.common.redis.RedisKeyBean;
import com.tqmall.data.epc.common.utils.*;
import com.tqmall.data.epc.common.utils.upload.ImgUtil;
import com.tqmall.data.epc.exterior.dubbo.car.CarCategoryExt;
import com.tqmall.data.epc.exterior.dubbo.car.CarVinExt;
import com.tqmall.core.common.entity.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by huangzhangting on 16/2/1.
 */
@Slf4j
@Service
public class CarCategoryBizImpl implements CarCategoryBiz {
    @Value("${vin.search.switch}")
    private String vinSearchSwitch;

    @Autowired
    private CarCategoryExt carCategoryExt;
    @Autowired
    private CarVinExt carVinExt;
    @Autowired
    private RedisClientTemplate redisClient;

    @Override
    public String getOnlineCarNameById(Integer id) {
        CarCategoryDTO carCategoryDTO = carCategoryExt.getCarById(id);
        if(null == carCategoryDTO){
            return null;
        }

        StringBuilder sb = new StringBuilder(40);
        Integer carLevel = carCategoryDTO.getLevel();
        if(carLevel > 1) sb.append(carCategoryDTO.getBrand()).append("-");
        if(carLevel > 2) sb.append(carCategoryDTO.getSeries()).append("-");
        if(carLevel > 3) sb.append(carCategoryDTO.getModel()).append("-");
        if(carLevel > 4 && carLevel != 6) sb.append(carCategoryDTO.getPower()).append("-");
        if(carLevel > 5) sb.append(carCategoryDTO.getYear()).append("-");
        sb.append(carCategoryDTO.getName());

        return sb.toString();
    }

    @Override
    public Result<List<CarBO>> getOnlineIdByVin(HttpServletRequest request, String vin) {
        if(request==null || !EPCUtil.isVin(vin)){
            return ResultUtil.errorResult(EpcError.ARG_ERROR);
        }
        vin = vin.trim().toUpperCase();

        String day = DateUtil.dateToString(new Date(), DateUtil.yyyyMMdd);
        String dayKey = String.format(RedisKeyBean.VIN_SEARCH_COUNT_DAY_KEY, day);
        String redisStr = redisClient.get(dayKey);
        int dayCount = redisStr==null?0:Integer.parseInt(redisStr);
        //超出日上线
        if(ConstantBean.VIN_SEARCH_SWITCH_ON.equals(vinSearchSwitch) && dayCount>=ConstantBean.VIN_SEARCH_DAY_LIMIT){
            return ResultUtil.errorResult("", "很抱歉，已达到vin码查询次数日上限：单日 "
                    + ConstantBean.VIN_SEARCH_DAY_LIMIT + " 次，请明日再来查询。");
        }

        String ip = EPCUtil.getIpAddress(request);
        String ipStr = ip.replace(":", "_");//.replace(".", "_");
        String dayIpKey = String.format(RedisKeyBean.VIN_SEARCH_COUNT_DAY_IP_KEY, day, ipStr);
        redisStr = redisClient.get(dayIpKey);
        int dayIpCount = redisStr==null?0:Integer.parseInt(redisStr);
        //超出单个ip日上线
        if(ConstantBean.VIN_SEARCH_SWITCH_ON.equals(vinSearchSwitch) && dayIpCount>=ConstantBean.VIN_SEARCH_DAY_IP_LIMIT){
            return ResultUtil.errorResult("", "很抱歉，您已查询超过 "
                    + ConstantBean.VIN_SEARCH_DAY_IP_LIMIT + " 次，请明日再来查询。");
        }

        String key = String.format(RedisKeyBean.VIN_SEARCH_KEY, vin);
        redisStr = redisClient.get(key);
        if(redisClient.isNone(redisStr)){
            return ResultUtil.errorResult("", "很抱歉，未查询到您提交的车辆信息。");
        }

        List<CarBO> list;
        if(redisStr!=null){
            list = JsonUtil.jsonToList(redisStr, CarBO.class);
            recordVinSearchCount(dayKey, dayIpKey, dayCount, dayIpCount);
            return ResultUtil.successResult(list);
        }

        List<CarDTO> carDTOList = carVinExt.getCarListByVin(vin);
        if(carDTOList.isEmpty()){
            redisClient.setNone(key, RedisKeyBean.RREDIS_EXP_FIVE_MINUTE);
            recordVinSearchCount(dayKey, dayIpKey, dayCount, dayIpCount);
            return ResultUtil.errorResult("", "很抱歉，未查询到您提交的车辆信息。");
        }
        list = BdUtil.do2bo4List(carDTOList, CarBO.class);

        redisClient.lazySet(key, list, RedisKeyBean.RREDIS_EXP_DAY);
        recordVinSearchCount(dayKey, dayIpKey, dayCount, dayIpCount);

        return ResultUtil.successResult(list);
    }

    //记录次数
    private void recordVinSearchCount(String dayKey, String dayIpKey, int dayCount, int dayIpCount){
        dayCount++;
        dayIpCount++;
        redisClient.setStringWithTime(dayKey, dayCount+"", RedisKeyBean.RREDIS_EXP_WEEK);
        redisClient.setStringWithTime(dayIpKey, dayIpCount+"", RedisKeyBean.RREDIS_EXP_WEEK);
    }

    @Override
    public Result<List<CarInfoBO>> getCarInfoByPid(Integer pid) {
        if(pid==null || pid<0){
            return ResultUtil.errorResult(EpcError.ARG_ERROR);
        }
        List<CarListDTO> list = carCategoryExt.categoryCarInfo(pid);
        if(list.isEmpty()){
            return ResultUtil.errorResult(EpcError.ARG_ERROR);
        }
        return ResultUtil.successResult4List(list, CarInfoBO.class);
    }

    @Override
    public Map<String, List<CenterCarBO>> getBrandForFilter() {
        String redisStr = redisClient.get(RedisKeyBean.ALL_CAR_BRAND_SERIES);
        if(redisStr != null){
            return JsonUtil.jsonToObject(redisStr, Map.class);
        }

        List<CarCategoryDTO> carDTOList = carCategoryExt.getByLevel(1);
        if(carDTOList.isEmpty()){
            return Maps.newHashMap();
        }

        Map<String, List<CenterCarBO>> map = handleCarBrand(carDTOList);
        redisClient.lazySet(RedisKeyBean.ALL_CAR_BRAND_SERIES, map, RedisKeyBean.RREDIS_EXP_DAY);
        return map;
    }

    //处理品牌
    private Map<String, List<CenterCarBO>> handleCarBrand(List<CarCategoryDTO> carDTOList){
        Collections.sort(carDTOList, new Comparator<CarCategoryDTO>() {
            @Override
            public int compare(CarCategoryDTO o1, CarCategoryDTO o2) {
                int firstWordFlag = o1.getFirstWord().compareTo(o2.getFirstWord());
                if (firstWordFlag == 0) {
                    return o1.getName().compareTo(o2.getName());
                }
                return firstWordFlag;
            }
        });

        Map<String, List<CenterCarBO>> firstWordMap = new TreeMap<>();

        List<CarCategoryDTO> seriesDTOList = carCategoryExt.getByLevel(2);

        for(CarCategoryDTO carBrand : carDTOList){
            CenterCarBO centerCarBO = BdUtil.do2bo(carBrand, CenterCarBO.class);
            centerCarBO.setLogo(ImgUtil.getFullPath(centerCarBO.getLogo()));

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
    private Map<String, List<CenterCarBO>> handleCarSeries(List<CarCategoryDTO> carDTOList, Integer brandId){
        List<CarCategoryDTO> seriesDTOList = Lists.newArrayList();
        for(CarCategoryDTO carDTO : carDTOList){
            if(carDTO.getPid().equals(brandId)) {
                seriesDTOList.add(carDTO);
            }
        }

        Collections.sort(seriesDTOList, new Comparator<CarCategoryDTO>() {
            @Override
            public int compare(CarCategoryDTO o1, CarCategoryDTO o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });

        Map<String, List<CenterCarBO>> companyMap = new HashMap<>();
        for(CarCategoryDTO carSeries : seriesDTOList){
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


}
