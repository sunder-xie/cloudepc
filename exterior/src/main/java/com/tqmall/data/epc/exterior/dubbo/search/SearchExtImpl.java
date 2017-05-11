package com.tqmall.data.epc.exterior.dubbo.search;

import com.tqmall.data.epc.bean.bizBean.lop.WishSnSearchResultBO;
import com.tqmall.data.epc.common.bean.ConstantBean;
import com.tqmall.data.epc.common.bean.EpcError;
import com.tqmall.data.epc.common.utils.JsonUtil;
import com.tqmall.data.epc.common.utils.ResultUtil;
import com.tqmall.search.common.data.PageableRequest;
import com.tqmall.search.dubbo.client.cloudepc.param.CloudEpcQueryRequest;
import com.tqmall.search.dubbo.client.cloudepc.param.WishListQueryRequest;
import com.tqmall.search.dubbo.client.cloudepc.result.CenterGoodsDTO;
import com.tqmall.search.dubbo.client.cloudepc.service.CloudEpcService;
import com.tqmall.core.common.entity.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

/**
 * Created by huangzhangting on 16/6/28.
 */
@Slf4j
@Service
public class SearchExtImpl implements SearchExt {
    private static final Integer MAX_PAGE_SIZE = 500;

    @Autowired
    private CloudEpcService searchEpcService;


    private PageableRequest getPageReq(Integer pageIndex, Integer pageSize, int defaultSize){
        if(pageIndex==null || pageIndex<1){
            pageIndex=1;
        }
        if(pageSize==null || pageSize<1){
            pageSize = defaultSize>0?defaultSize:10;
        }else if(pageSize>MAX_PAGE_SIZE){
            pageSize = MAX_PAGE_SIZE;
        }
        return new PageableRequest(pageIndex-1, pageSize);
    }

    @Override
    public Result<CenterGoodsDTO> goodsSearch(CloudEpcQueryRequest queryRequest, Integer pageIndex, Integer pageSize) {

        log.info("getCenterGoods from search, param:{}", JsonUtil.objectToJson(queryRequest));

        PageableRequest pageableRequest = getPageReq(pageIndex, pageSize, 10);

        com.tqmall.search.common.result.Result searchResult =
                searchEpcService.getCenterGoods(queryRequest, pageableRequest);
        if(searchResult.isSuccess()){
            CenterGoodsDTO centerGoodsDTO = (CenterGoodsDTO)searchResult.getData();
            if(!CollectionUtils.isEmpty(centerGoodsDTO.getGoodsInfo())) {
                return ResultUtil.successResult(centerGoodsDTO);
            }
        }

        log.info("getCenterGoods from search failed, result:{}", JsonUtil.objectToJson(searchResult));
        return ResultUtil.errorResult(EpcError.UN_KNOW_EXCEPTION);
    }

    @Override
    public WishSnSearchResultBO wishListSnSearch(Integer userId, String status, String keyword, Integer pageIndex, Integer pageSize) {
        PageableRequest pageableRequest = getPageReq(pageIndex, pageSize, ConstantBean.WISH_PAGE_SIZE);
        WishListQueryRequest queryRequest = new WishListQueryRequest();
        queryRequest.setQ(keyword);
        queryRequest.setUserId(userId);
        queryRequest.setStatus(status);

        com.tqmall.search.common.result.Result<Page<String>> searchResult =
                searchEpcService.queryWishListSn(queryRequest, pageableRequest);

        if(searchResult.isSuccess()){
            Page<String> page = searchResult.getData();
            if(!CollectionUtils.isEmpty(page.getContent())) {
                WishSnSearchResultBO resultBO = new WishSnSearchResultBO();
                resultBO.setSnList(page.getContent());
                resultBO.setTotal((int) page.getTotalElements());
                return resultBO;
            }
        }

        return null;
    }

    @Override
    public WishSnSearchResultBO offerListSnSearch(Integer userId, String status, String payStatus,
                                                  String keyword, Integer pageIndex, Integer pageSize) {
        PageableRequest pageableRequest = getPageReq(pageIndex, pageSize, ConstantBean.OFFER_PAGE_SIZE);
        WishListQueryRequest queryRequest = new WishListQueryRequest();
        queryRequest.setQ(keyword);
        queryRequest.setUserId(userId);
        queryRequest.setStatus(status);
        if(!StringUtils.isEmpty(payStatus)){
            queryRequest.setPayStatus(payStatus);
        }

        com.tqmall.search.common.result.Result<Page<String>> searchResult =
                searchEpcService.queryOfferListSn(queryRequest, pageableRequest);

        if(searchResult.isSuccess()){
            Page<String> page = searchResult.getData();
            if(!CollectionUtils.isEmpty(page.getContent())) {
                WishSnSearchResultBO resultBO = new WishSnSearchResultBO();
                resultBO.setSnList(page.getContent());
                resultBO.setTotal((int) page.getTotalElements());
                return resultBO;
            }
        }

        return null;
    }
}
