package com.tqmall.data.epc.exterior.dubbo.car;

import com.google.common.collect.Lists;
import com.tqmall.athena.client.car.CarCategoryService;
import com.tqmall.athena.domain.result.carcategory.CarCategoryDTO;
import com.tqmall.athena.domain.result.carcategory.CarListDTO;
import com.tqmall.core.common.entity.Result;
import com.tqmall.data.epc.common.utils.upload.ImgUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by huangzhangting on 16/2/1.
 */
@Slf4j
@Service
public class CarCategoryExtImpl implements CarCategoryExt {
    @Autowired
    private CarCategoryService carCategoryService;

    @Override
    public List<CarListDTO> categoryCarInfo(Integer pid) {
        if(pid==null || pid<0) {
            log.info("request athena, illegal parameter, pid:"+pid);
            return Lists.newArrayList();
        }

        Result<List<CarListDTO>> result = carCategoryService.categoryCarInfo(pid);
        if(result.isSuccess() && result.getData()!=null) {
            List<CarListDTO> list = result.getData();
            if(pid==0){ //品牌级别
                for(CarListDTO car : list){
                    car.setLogo(ImgUtil.httpToHttps(car.getLogo()));
                }
            }
            return list;
        }

        log.info("get car list from athena failed, pid:{}, result:{}", pid, result);
        return Lists.newArrayList();
    }

    @Override
    public CarCategoryDTO getCarById(Integer id) {
        if(id==null || id<1){
            log.info("request athena, illegal parameter, id:"+id);
            return null;
        }
        Result<CarCategoryDTO> result = carCategoryService.getCarCategoryByPrimaryId(id);
        if(result.isSuccess() && result.getData()!=null) {
            return result.getData();
        }

        log.info("get car from athena failed, id:{}, result:{}", id, result);
        return null;
    }

    @Override
    public List<CarCategoryDTO> getByLevel(Integer level) {
        if(level==null || level<1){
            log.info("request athena, illegal parameter, level:{}", level);
            return Lists.newArrayList();
        }
        Result<List<CarCategoryDTO>> listResult = carCategoryService.getByLevel(level);
        if(listResult.isSuccess()){
            return listResult.getData();
        }

        log.info("get car list by level from athena failed, level:{}, result:{}", level, listResult);
        return Lists.newArrayList();
    }
}
