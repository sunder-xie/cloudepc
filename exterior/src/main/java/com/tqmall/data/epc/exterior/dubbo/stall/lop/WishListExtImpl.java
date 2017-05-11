package com.tqmall.data.epc.exterior.dubbo.stall.lop;

import com.tqmall.core.common.entity.PagingResult;
import com.tqmall.core.common.entity.Result;
import com.tqmall.data.epc.bean.bizBean.lop.*;
import com.tqmall.data.epc.bean.param.lop.WishListCancelParam;
import com.tqmall.data.epc.bean.param.lop.WishListParam;
import com.tqmall.data.epc.bean.param.lop.WishListRequestParam;
import com.tqmall.data.epc.bean.param.lop.WishOrderCreateParam;
import com.tqmall.data.epc.common.bean.EpcError;
import com.tqmall.data.epc.common.utils.BdUtil;
import com.tqmall.data.epc.common.utils.JsonUtil;
import com.tqmall.data.epc.common.utils.ResultUtil;
import com.tqmall.tqmallstall.domain.param.openplatform.OfferAddressModifyRequest;
import com.tqmall.tqmallstall.domain.param.openplatform.WishListCancelRequest;
import com.tqmall.tqmallstall.domain.param.openplatform.WishListPageRequest;
import com.tqmall.tqmallstall.domain.param.openplatform.WishListRequest;
import com.tqmall.tqmallstall.domain.result.openplatform.*;
import com.tqmall.tqmallstall.service.openplatform.WishListService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by huangzhangting on 16/7/30.
 */
@Service
@Slf4j
public class WishListExtImpl implements WishListExt{
    @Autowired
    private WishListService wishListService;


    @Override
    public PagingResult<WishListVO> paged(WishListParam param) {
        log.info("request wish list, param:{}", JsonUtil.objectToJson(param));

        WishListPageRequest request = BdUtil.do2bo(param, WishListPageRequest.class);
        PagingResult<WishListViewDTO> result = wishListService.getWishList4Paging(request);

        List<WishListVO> list = new ArrayList<>();
        List<WishListViewDTO> dtoList = result.getList();
        if (result.isSuccess() && !CollectionUtils.isEmpty(dtoList)) {
            for (WishListViewDTO item : dtoList) {
                WishListVO listItem = BdUtil.do2bo(item, WishListVO.class);
                listItem.setGoodsList(BdUtil.do2bo4List(item.getGoodsList(), WishListGoodsVO.class));
                listItem.setSellerList(BdUtil.do2bo4List(item.getSellerList(), SellerBO.class));
                if (item.getFeedbackGoodsList() != null) {
                    Map<String, List<OfferGoodsVO>> feedbackGoodsMap = new HashMap<>();
                    for (Integer groupId : item.getFeedbackGoodsList().keySet()) {
                        feedbackGoodsMap.put(groupId.toString(),
                                BdUtil.do2bo4List(item.getFeedbackGoodsList().get(groupId), OfferGoodsVO.class));
                    }
                    listItem.setFeedbackGoodsList(feedbackGoodsMap);
                }
                list.add(listItem);
            }
            return PagingResult.wrapSuccessfulResult(list, result.getTotal());
        }

        log.info("request wish list, result:{}", JsonUtil.objectToJson(result));
        return ResultUtil.pageErrorResult(EpcError.NO_DATA_ERROR);
    }

    @Override
    public Result cancel(WishListCancelParam param) {
        log.info("cancel wish list, param:{}", JsonUtil.objectToJson(param));

        WishListCancelRequest request = BdUtil.do2bo(param, WishListCancelRequest.class);
        request.setId(param.getWishId());
        Result result = wishListService.cancel(request);

        log.info("cancel wish list, result:{}", JsonUtil.objectToJson(result));
        return result;
    }

    @Override
    public Result<Integer> create(WishListRequestParam param) {
        log.info("create wish list, param:{}", JsonUtil.objectToJson(param));

        WishListRequest request = BdUtil.do2bo(param, WishListRequest.class);
        request.setGoodsList(BdUtil.do2bo4List(param.getGoodsList(), WishListGoodsDTO.class));
        Result<Integer> result = wishListService.create(request);

        log.info("create wish list, result:{}", JsonUtil.objectToJson(result));
        return result;
    }

    @Override
    public Result createOrder(WishOrderCreateParam param) {
        log.info("create wish order, param:{}", JsonUtil.objectToJson(param));

        OfferAddressModifyRequest request = BdUtil.do2bo(param, OfferAddressModifyRequest.class);
        Result result = wishListService.createWishlistOrder(request);

        log.info("create wish order, result:{}", JsonUtil.objectToJson(result));
        return result;
    }

    @Override
    public Map<Integer, List<CompetitorInfoBO>> getCompetitorInfo(List<Integer> wishListIdList, Integer sellerId, String status) {
        log.info("get competitor info, wishListIdList:{}, sellerId:{}, status:{}", wishListIdList.toString(), sellerId, status);

        Result<Map<Integer, List<CompetitorInfoDTO>>> result = wishListService.getCompetitorInfo(wishListIdList, sellerId, status);
        Map<Integer, List<CompetitorInfoDTO>> dataMap = result.getData();
        if (result.isSuccess() && !CollectionUtils.isEmpty(dataMap)) {
            Map<Integer, List<CompetitorInfoBO>> resultMap = new HashMap<>();
            for(Map.Entry<Integer, List<CompetitorInfoDTO>> entry : dataMap.entrySet()){
                List<CompetitorInfoBO> competitorInfoBOs = BdUtil.do2bo4List(entry.getValue(), CompetitorInfoBO.class);
                resultMap.put(entry.getKey(), competitorInfoBOs);
            }
            return resultMap;
        }

        log.info("get competitor info, result:{}", JsonUtil.objectToJson(result));

        return null;
    }

    @Override
    public WishListBO getWishListById(Integer id, Integer userId) {
        log.info("get wish by id, id:{}, userId:{}", id, userId);

        Result<WishListDTO> result = wishListService.getById(id, userId);
        if(result.isSuccess() && result.getData()!=null){
            WishListBO wishListBO = BdUtil.do2bo(result.getData(), WishListBO.class);
            wishListBO.setGoodsList(BdUtil.do2bo4List(result.getData().getGoodsList(), WishListGoodsBO.class));
            return wishListBO;
        }

        log.info("get wish by id, result:{}", JsonUtil.objectToJson(result));
        return null;
    }

    @Override
    public List<WishListBO> getSuitableWishList(Integer sellerId, List<String> wishListSn) {
        log.info("get suitable wish list, sellerId:{}, wishListSn:{}", sellerId, wishListSn.toString());

        Result<List<WishListDTO>> result = wishListService.getSuitableWishList(sellerId, wishListSn);
        if(result.isSuccess() && !CollectionUtils.isEmpty(result.getData())){
            return BdUtil.do2bo4List(result.getData(), WishListBO.class);
        }

        log.info("get suitable wish list, result:{}", JsonUtil.objectToJson(result));
        return new ArrayList<>();
    }

    @Override
    public WishListStatusBO getYbjWishListNum(Integer userId) {
        log.info("get ybj wish list num, userId:{}", userId);

        Result<WishListStatDTO> result = wishListService.stat4XYBJ(userId);
        WishListStatDTO statDTO = result.getData();
        if(result.isSuccess() && statDTO != null){
            WishListStatusBO statusBO = new WishListStatusBO();
            statusBO.setYbjNumber(statDTO.getYbjNumber());

            OfferListTipMsgDTO tipMsgDTO = statDTO.getOfferListTipMsg();
            if(tipMsgDTO != null && !CollectionUtils.isEmpty(tipMsgDTO.getNewOfferListBOList())){
                statusBO.setNewYbjNumber(tipMsgDTO.getNewOfferListBOList().size());
            }else{
                statusBO.setNewYbjNumber(0);
            }

            return statusBO;
        }

        log.info("get ybj wish list num, result:{}", JsonUtil.objectToJson(result));
        return null;
    }
}
