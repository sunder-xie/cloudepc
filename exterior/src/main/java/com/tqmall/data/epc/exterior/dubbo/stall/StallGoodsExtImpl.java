package com.tqmall.data.epc.exterior.dubbo.stall;

import com.tqmall.core.common.entity.PagingResult;
import com.tqmall.data.epc.common.bean.EpcError;
import com.tqmall.data.epc.common.utils.JsonUtil;
import com.tqmall.data.epc.common.utils.ResultUtil;
import com.tqmall.data.epc.common.utils.upload.ImgUtil;
import com.tqmall.tqmallstall.domain.param.minitqmall.CloudGoodsParam;
import com.tqmall.tqmallstall.domain.result.goods.GoodsMiniDTO;
import com.tqmall.tqmallstall.service.minitqmall.MiniGoodsService;
import com.tqmall.core.common.entity.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangzhangting on 16/7/4.
 */
@Service
@Slf4j
public class StallGoodsExtImpl implements StallGoodsExt {
    private static final int DEFAULT_PAGE_SIZE = 20;

    @Autowired
    private MiniGoodsService stallMiniGoodsService;


    /** 这个方法比较特殊，清空失效商品时，需要严格判断 */
    @Override
    public List<GoodsMiniDTO> getGoodsMiniByIds(List<Integer> goodsIdList) {
        if (CollectionUtils.isEmpty(goodsIdList)){
            log.info("query goods list, illegal param:{}", goodsIdList);
            return new ArrayList<>();
        }
        log.info("query goods list, param:{}", JsonUtil.objectToJson(goodsIdList));

        try {
            Result<List<GoodsMiniDTO>> result = stallMiniGoodsService.queryGoodsList(goodsIdList);

            if(!result.isSuccess()){
                log.info("query goods list, system error, result:{}", JsonUtil.objectToJson(result));
                return null;
            }

            if(!CollectionUtils.isEmpty(result.getData())){
                List<GoodsMiniDTO> list = result.getData();
                for(GoodsMiniDTO goodsMiniDTO : list){
                    goodsMiniDTO.setGoodsImg(ImgUtil.httpToHttps(goodsMiniDTO.getGoodsImg()));
                }
                return list;
            }

            log.info("query goods list, failed, result:{}", JsonUtil.objectToJson(result));
            return new ArrayList<>();
        }catch (Exception e){
            log.error("query goods list, error", e);
            return null;
        }
    }

    @Override
    public PagingResult<GoodsMiniDTO> queryGoodsByCondition(String oeNum, Integer qualityId, List<Integer> orgIdList,
                                                            Integer pageIndex, Integer pageSize) {

        if(StringUtils.isEmpty(oeNum) || CollectionUtils.isEmpty(orgIdList)){
            return ResultUtil.pageErrorResult(EpcError.ARG_ERROR);
        }

        if(pageIndex==null || pageIndex<1){
            pageIndex = 1;
        }
        if(pageSize==null || pageSize<1){
            pageSize = DEFAULT_PAGE_SIZE;
        }

        int start = (pageIndex - 1) * pageSize;

        CloudGoodsParam param = new CloudGoodsParam();
        param.setStart(start);
        param.setLimit(pageSize);
        param.setOeNum(oeNum);
        param.setOrgIdList(orgIdList);
        if(qualityId != null) {
            param.setGoodsQualityType(qualityId);
        }
        log.info("query goods by condition, param:{}", JsonUtil.objectToJson(param));

        PagingResult<GoodsMiniDTO> pagingResult = stallMiniGoodsService.queryGoodsByCondition(param);
        if(pagingResult!=null && pagingResult.isSuccess()){
            List<GoodsMiniDTO> list = pagingResult.getList();
            if(!CollectionUtils.isEmpty(list)){
                for(GoodsMiniDTO goodsMiniDTO : list){
                    goodsMiniDTO.setGoodsImg(ImgUtil.httpToHttps(goodsMiniDTO.getGoodsImg()));
                }
                return pagingResult;
            }
        }

        log.info("query goods by condition, failed, result:{}", JsonUtil.objectToJson(pagingResult));
        return ResultUtil.pageErrorResult(EpcError.NO_DATA_ERROR);
    }

    @Override
    public List<GoodsMiniDTO> queryAllGoods(String oeNum, Integer qualityId, List<Integer> orgIdList) {
        int pageSize = DEFAULT_PAGE_SIZE;
        List<GoodsMiniDTO> list = new ArrayList<>();
        PagingResult<GoodsMiniDTO> pagingResult = queryGoodsByCondition(oeNum, qualityId, orgIdList, 1, pageSize);
        if(!pagingResult.isSuccess()){
            return list;
        }
        list.addAll(pagingResult.getList());
        int total = pagingResult.getTotal();
        if(total > pageSize){
            int page = total%pageSize==0?total/pageSize:(total/pageSize+1);
            while (page > 1){
                pagingResult = queryGoodsByCondition(oeNum, qualityId, orgIdList, 1, pageSize);
                if(pagingResult.isSuccess()){
                    list.addAll(pagingResult.getList());
                }

                page--;
            }
        }

        return list;
    }
}
