package com.tqmall.data.epc.biz.car;

import com.tqmall.athena.domain.result.center.car.CenterCarCatDTO;
import com.tqmall.data.epc.bean.bizBean.car.CenterCarCatBO;
import com.tqmall.data.epc.exterior.dubbo.car.CenterCarCatExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zxg on 16/2/3.
 * 15:30
 */
@Service
public class CenterCarCatBizImpl implements CenterCarCatBiz {

    @Autowired
    private CenterCarCatExt centerCarCatExt;

    @Override
    public Map<CenterCarCatBO, Map<CenterCarCatBO, List<CenterCarCatBO>>> getCatByCarId(Integer carId) {
        Map<CenterCarCatBO, Map<CenterCarCatBO, List<CenterCarCatBO>>> resultMap = new HashMap<>();

        List<CenterCarCatDTO> extList = centerCarCatExt.getListByCarIdC(carId);
        for(CenterCarCatDTO centerCarCatDTO : extList){
            CenterCarCatBO firstBO = new CenterCarCatBO(centerCarCatDTO.getFirstCatId(),centerCarCatDTO.getFirstCatName(),centerCarCatDTO.getFirstCatLetter());
            CenterCarCatBO secondBO = new CenterCarCatBO(centerCarCatDTO.getSecondCatId(),centerCarCatDTO.getSecondCatName(),centerCarCatDTO.getSecondCatLetter());
            CenterCarCatBO thirdBO = new CenterCarCatBO(centerCarCatDTO.getThirdCatId(),centerCarCatDTO.getThirdCatName(),centerCarCatDTO.getThirdCatLetter());

            //三级拼接成map结构
            Map<CenterCarCatBO, List<CenterCarCatBO>> secondMap = resultMap.get(firstBO);
            if(null == secondMap){
                secondMap = new HashMap<>();
                resultMap.put(firstBO,secondMap);
            }

            List<CenterCarCatBO> thirdList = secondMap.get(secondBO);
            if(null == thirdList){
                thirdList = new ArrayList<>();
            }
            thirdList.add(thirdBO);
            secondMap.put(secondBO,thirdList);
        }
        return resultMap;
    }
}
