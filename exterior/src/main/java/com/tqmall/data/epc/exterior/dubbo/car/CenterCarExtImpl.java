package com.tqmall.data.epc.exterior.dubbo.car;

import com.google.common.collect.Lists;
import com.tqmall.athena.client.center.car.CenterCarService;
import com.tqmall.athena.domain.result.center.car.CenterCarDTO;
import com.tqmall.core.common.entity.Result;
import com.tqmall.data.epc.common.utils.upload.ImgUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by huangzhangting on 16/2/2.
 */
@Slf4j
@Service
public class CenterCarExtImpl implements CenterCarExt {
    @Autowired
    private CenterCarService centerCarService;

    @Override
    public List<CenterCarDTO> getCenterCarByPid(Integer pid) {
        if(pid==null || pid<0) {
            log.info("request athena, illegal parameter, pid:"+pid);
            return Lists.newArrayList();
        }

        Result<List<CenterCarDTO>> result = centerCarService.getCenterCarByPid(pid);
        if(result.isSuccess()){
            List<CenterCarDTO> list = result.getData();
            if(pid==0){ //品牌级别
                for(CenterCarDTO car : list){
                    car.setLogo(ImgUtil.httpToHttps(car.getLogo()));
                }
            }
            return list;
        }

        log.info("get car list from athena failed, pid:{}, result:{}", pid, result);

        return Lists.newArrayList();
    }

    @Override
    public List<CenterCarDTO> getCenterCarByLevel(Integer level) {
        if(level==null || level<1){
            log.info("request athena, illegal parameter, level:"+level);
            return Lists.newArrayList();
        }

        Result<List<CenterCarDTO>> result = centerCarService.getCenterCarByLevel(level);
        if(result.isSuccess()){
            List<CenterCarDTO> list = result.getData();
            if(level==1){ //品牌级别
                for(CenterCarDTO car : list){
                    car.setLogo(ImgUtil.httpToHttps(car.getLogo()));
                }
            }
            return list;
        }

        log.info("get car list from athena failed, level:{}, result:{}", level, result);

        return Lists.newArrayList();
    }

    @Override
    public List<CenterCarDTO> getCarListByModelId(Integer modelId) {
        if(modelId==null || modelId<1){
            log.info("request athena, illegal parameter, modelId:"+modelId);
            return Lists.newArrayList();
        }

        Result<List<CenterCarDTO>> result = centerCarService.getCarListByModelId(modelId);
        if(result.isSuccess()){
            return result.getData();
        }

        log.info("get car list from athena failed, modelId:{}, result:{}", modelId, result);

        return Lists.newArrayList();
    }

    @Override
    public CenterCarDTO getCenterCarById(Integer id) {
        if(id==null || id<1){
            log.info("request athena, illegal parameter, id:"+id);
            return null;
        }
        Result<CenterCarDTO> result = centerCarService.getCenterCarById(id);
        if(result.isSuccess()){
            return result.getData();
        }

        log.info("get car from athena failed, id:{}, result:{}", id, result);

        return null;
    }
}
