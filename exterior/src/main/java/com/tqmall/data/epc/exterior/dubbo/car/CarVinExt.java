package com.tqmall.data.epc.exterior.dubbo.car;

import com.tqmall.athena.client.car.CarVinService;
import com.tqmall.athena.domain.result.carcategory.CarDTO;
import com.tqmall.core.common.entity.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangzhangting on 16/6/16.
 */
@Slf4j
@Component
public class CarVinExt {
    @Autowired
    private CarVinService carVinService;

    public List<CarDTO> getCarListByVin(String vin){
        Result<List<CarDTO>> result = carVinService.getCarListByVin(vin);
        if(result.isSuccess()){
            return result.getData();
        }
        log.info("get car list from athena failed, vin:{}, result:{}", vin, result);
        return new ArrayList<>();
    }
}
