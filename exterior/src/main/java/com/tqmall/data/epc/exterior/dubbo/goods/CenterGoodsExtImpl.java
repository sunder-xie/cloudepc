package com.tqmall.data.epc.exterior.dubbo.goods;

import com.google.common.collect.Lists;
import com.tqmall.athena.client.center.goods.CenterGoodsService;
import com.tqmall.athena.domain.result.center.goods.CenterGoodsCarDTO;
import com.tqmall.athena.domain.result.center.goods.CenterGoodsCarDetailDTO;
import com.tqmall.athena.domain.result.center.goods.CenterGoodsCarSubjoinDTO;
import com.tqmall.athena.domain.result.center.goods.CenterGoodsDTO;
import com.tqmall.core.common.entity.Result;
import com.tqmall.data.epc.common.utils.upload.ImgUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by huangzhangting on 16/2/17.
 */
@Slf4j
@Service
public class CenterGoodsExtImpl implements CenterGoodsExt {
    @Autowired
    private CenterGoodsService centerGoodsService;

    //处理图片
    private void handleGoodsPic(CenterGoodsDTO centerGoodsDTO){
        String pic = centerGoodsDTO.getGoodsPic();
        centerGoodsDTO.setGoodsPic(ImgUtil.httpToHttps(pic));
    }

    @Override
    public CenterGoodsDTO getGoodsByPrimaryId(Integer goodsId) {
        if(goodsId==null || goodsId<1){
            log.info("request athena, illegal parameter, goodsId:{}", goodsId);
            return null;
        }

        Result<CenterGoodsDTO> result = centerGoodsService.getGoodsByPrimaryId(goodsId);
        if(result.isSuccess()){
            CenterGoodsDTO goodsDTO = result.getData();
            handleGoodsPic(goodsDTO);
            return goodsDTO;
        }

        log.info("get goods from athena failed, goodsId:{}, result:{}", goodsId, result);
        return null;
    }

    @Override
    public CenterGoodsDTO getGoodsByOeNumber(String oeNumber) {
        if(StringUtils.isEmpty(oeNumber)){
            log.info("request athena, illegal parameter, oeNumber:"+oeNumber);
            return null;
        }
        Result<CenterGoodsDTO> result = centerGoodsService.getGoodsByOeNumber(oeNumber);
        if(result.isSuccess()) {
            CenterGoodsDTO goodsDTO = result.getData();
            handleGoodsPic(goodsDTO);
            return goodsDTO;
        }

        log.info("get goods from athena failed, oeNumber:{}, result:{}", oeNumber, result);

        return null;
    }

    @Override
    public List<CenterGoodsDTO> getGoodsByCatId(Integer catId) {
        if(catId==null || catId<1){
            log.info("request athena, illegal parameter, catId:{}",catId);
            return Lists.newArrayList();
        }

        Result<List<CenterGoodsDTO>> result = centerGoodsService.getGoodsByThirdCatId(catId);
        if(result.isSuccess()) {
            List<CenterGoodsDTO> list = result.getData();
            for(CenterGoodsDTO goodsDTO : list){
                handleGoodsPic(goodsDTO);
            }
            return list;
        }

        log.info("get goods list from athena failed, catId:{}, result:{}", catId, result);
        return Lists.newArrayList();
    }

    @Override
    public List<CenterGoodsDTO> getGoodsByPicNumAndCar(String picNum, Integer carId) {
        if(StringUtils.isEmpty(picNum) || carId==null || carId<1){

            return new ArrayList<>();
        }

        Result<List<CenterGoodsDTO>> result = centerGoodsService.getGoodsByPicNumAndCar(picNum, carId);
        if(result.isSuccess()){
            List<CenterGoodsDTO> list = result.getData();
            Collections.sort(list, new Comparator<CenterGoodsDTO>() {
                @Override
                public int compare(CenterGoodsDTO o1, CenterGoodsDTO o2) {
                    return o1.getPartName().compareTo(o2.getPartName());
                }
            });
            for(CenterGoodsDTO goodsDTO : list){
                handleGoodsPic(goodsDTO);
            }
            return list;
        }

        return new ArrayList<>();
    }

    @Override
    public List<CenterGoodsCarDTO> getGoodsCarByGoodsCar(Integer goodsId, Integer carId) {
        if (goodsId == null && carId == null) {
            log.info("request athena, illegal parameter, goodsId:null, carId:null");
            return Lists.newArrayList();
        }

        Result<List<CenterGoodsCarDTO>> result = centerGoodsService.getGoodsCarByGoodsCar(goodsId, carId);
        if (result.isSuccess()) {
            return result.getData();
        }

        log.info("get goods car list from athena failed, goodsId:{}, carId:{}, result:{}", goodsId, carId, result);

        return Lists.newArrayList();
    }

    @Override
    public CenterGoodsCarDetailDTO getGoodsCarDetailByGoodsCar(Integer goodsId, Integer carId) {
        if(goodsId==null || carId == null || goodsId<1 || carId<1) {
            return null;
        }
        Result<CenterGoodsCarDetailDTO> result = centerGoodsService.getGoodsCarDetailByGoodsCar(goodsId, carId);
        if(result.isSuccess()){
            CenterGoodsCarDetailDTO detailDTO = result.getData();
            List<String> epcPicList = detailDTO.getEpcPicList();
            detailDTO.setEpcPicList(ImgUtil.batchHttpToHttps(epcPicList));
            return detailDTO;
        }

        return null;
    }

    @Override
    public CenterGoodsCarSubjoinDTO getSubjoinByGoodsModel(Integer goodsId, Integer modelId) {
        if(goodsId==null || goodsId<1 || modelId==null || modelId<1){
            return null;
        }
        Result<CenterGoodsCarSubjoinDTO> result = centerGoodsService.getSubjoinByGoodsModel(goodsId, modelId);
        if(result.isSuccess()){
            return result.getData();
        }

        return null;
    }

    @Override
    public List<CenterGoodsCarDetailDTO> getGcDetailByPicNumAndCar(String picNum, Integer carId) {
        if(StringUtils.isEmpty(picNum) || carId==null || carId<1){

            return new ArrayList<>();
        }

        Result<List<CenterGoodsCarDetailDTO>> result = centerGoodsService.getGcDetailByPicNumAndCar(picNum, carId);
        if(result.isSuccess()){
            List<CenterGoodsCarDetailDTO> list = result.getData();
            Collections.sort(list, new Comparator<CenterGoodsCarDetailDTO>() {
                @Override
                public int compare(CenterGoodsCarDetailDTO o1, CenterGoodsCarDetailDTO o2) {
                    int flag = o1.getEpcIndex().compareTo(o2.getEpcIndex());
                    if(flag == 0){
                        return o1.getPartName().compareTo(o2.getPartName());
                    }
                    return flag;
                }
            });
            for(CenterGoodsCarDetailDTO detailDTO : list){
                List<String> epcPicList = detailDTO.getEpcPicList();
                detailDTO.setEpcPicList(ImgUtil.batchHttpToHttps(epcPicList));
            }
            return list;
        }

        return new ArrayList<>();
    }
}
