package com.tqmall.data.epc.exterior.dubbo.car;

import com.google.common.collect.Lists;
import com.tqmall.athena.client.center.car.CenterCarCatService;
import com.tqmall.athena.domain.result.center.car.CenterCarCatDTO;
import com.tqmall.athena.domain.util.ShareConstants;
import com.tqmall.core.common.entity.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zxg on 16/2/3.
 * 15:12
 */
@Service
@Slf4j
public class CenterCarCatExtImpl implements CenterCarCatExt {

    @Autowired
    private CenterCarCatService centerCarCatService;

    @Override
    public List<CenterCarCatDTO> getListByCarIdC(Integer carId) {
        if(carId == null || carId < 0){
            log.info("request athena, illegal parameter, carId:"+carId);
            return Lists.newArrayList();
        }
        Result<List<CenterCarCatDTO>> result = centerCarCatService.getListByCarIdVehicle(carId, ShareConstants.PASSENGER_CAR);

        if(result.isSuccess()) {
            return result.getData();
        }

        log.info("get car cate list from athena failed, carId:{}, result:{}", carId, result);
        return Lists.newArrayList();
    }
}
