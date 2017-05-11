package com.tqmall.data.epc.web.controller.category;

import com.tqmall.data.epc.bean.bizBean.car.CenterCarCatBO;
import com.tqmall.data.epc.biz.car.CenterCarCatBiz;
import com.tqmall.data.epc.common.bean.EpcError;
import com.tqmall.data.epc.common.utils.ResultUtil;
import com.tqmall.data.epc.web.BaseController;
import com.tqmall.core.common.entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * Created by zxg on 16/2/2.
 * 11:33
 */
@Controller
@RequestMapping("epc/category")
public class CategoryController extends BaseController {

    @Autowired
    private CenterCarCatBiz centerCarCatBiz;


    //根据carId获得分类数据
    @RequestMapping(value = "getCatByCarId")
    @ResponseBody
    public Result getCatByCarId(Integer carId){
        if(carId == null || carId < 0){
            return ResultUtil.errorResult(EpcError.ARG_ERROR);
        }
        Map<String,Object> resultMap = new HashMap<>();
        Map<CenterCarCatBO, Map<CenterCarCatBO, List<CenterCarCatBO>>> catMap = centerCarCatBiz.getCatByCarId(carId);
        if(catMap.isEmpty()){
            return ResultUtil.errorResult(EpcError.ARG_ERROR);
        }
        //一级的列表
        List<CenterCarCatBO> firstList = new ArrayList<>();
        //一级id：Map<boList：二级list，letterList：二级首字母>
        Map<Integer,Map<String,Object>> firstMap = new HashMap<>();

        //二级id：list<三级>
        Map<Integer,List<CenterCarCatBO>> secondMap = new HashMap<>();
        //二级id：list<三级首字母>
        Map<Integer,List<String>> secondLetterMap = new HashMap<>();

        for(Map.Entry<CenterCarCatBO, Map<CenterCarCatBO, List<CenterCarCatBO>>> mapEntry : catMap.entrySet()){
            CenterCarCatBO firstBO = mapEntry.getKey();
            Map<CenterCarCatBO, List<CenterCarCatBO>> firstBOMap = mapEntry.getValue();

            Map<String,Object> firstValueMap = new HashMap<>();
            List<CenterCarCatBO> secondList = new ArrayList<>();
            List<String> secondLetterList = new ArrayList<>();
            Set<String> secondLetterSet = new HashSet<>();
            firstValueMap.put("boList", secondList);
            firstValueMap.put("letterList", secondLetterList);

            firstList.add(firstBO);
            for(Map.Entry<CenterCarCatBO, List<CenterCarCatBO>> firstEntry : firstBOMap.entrySet()){
                CenterCarCatBO secondBO = firstEntry.getKey();
                List<CenterCarCatBO> thirdList = firstEntry.getValue();

                List<String> thirdLetterList = new ArrayList<>();
                Set<String> thirdLetterSet = new HashSet<>();
                for(CenterCarCatBO thirdBO:thirdList){
                    thirdLetterSet.add(thirdBO.getFirstLetter());
                }
                thirdLetterList.addAll(thirdLetterSet);
                sortLetter(thirdLetterList);
                secondLetterMap.put(secondBO.getCatId(),thirdLetterList);

                sortCatBOListByLetter(thirdList);
                secondMap.put(secondBO.getCatId(),thirdList);

                //二级分类首字母,给 firstMap 使用
                String secondLetter = secondBO.getFirstLetter();
                secondLetterSet.add(secondLetter);
                //给 firstMap 使用
                secondList.add(secondBO);


            }
            sortCatBOListByLetter(secondList);
            secondLetterList.addAll(secondLetterSet);
            sortLetter(secondLetterList);
            firstMap.put(firstBO.getCatId(),firstValueMap);
        }

        sortCatBOListByLetter(firstList);

        resultMap.put("firstList", firstList);
        resultMap.put("firstMap", firstMap);
        resultMap.put("secondMap", secondMap);
        resultMap.put("secondLetterMap", secondLetterMap);

        return Result.wrapSuccessfulResult(resultMap);
    }


    private void sortCatBOListByLetter(List<CenterCarCatBO> list){

        Collections.sort(list, new Comparator<CenterCarCatBO>() {
            @Override
            public int compare(CenterCarCatBO o1, CenterCarCatBO o2) {
                return o1.getFirstLetter().compareTo(o2.getFirstLetter());
            }
        });
    }
    private void sortLetter(List<String> list){

        Collections.sort(list, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });
    }
}
