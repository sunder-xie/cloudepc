package com.tqmall.data.epc.biz.lop;

import com.google.common.reflect.TypeToken;
import com.tqmall.core.common.entity.PagingResult;
import com.tqmall.core.common.entity.Result;
import com.tqmall.data.epc.bean.bizBean.lop.OfferListBO;
import com.tqmall.data.epc.bean.bizBean.lop.OfferListDetailBO;
import com.tqmall.data.epc.bean.bizBean.lop.WishOrderBO;
import com.tqmall.data.epc.bean.bizBean.lop.WishSnSearchResultBO;
import com.tqmall.data.epc.bean.param.lop.ChooseOfferGoodsParam;
import com.tqmall.data.epc.bean.param.lop.WishOrderQueryParam;
import com.tqmall.data.epc.biz.shiro.ShiroBiz;
import com.tqmall.data.epc.common.bean.ConstantBean;
import com.tqmall.data.epc.common.bean.EpcError;
import com.tqmall.data.epc.common.utils.BdUtil;
import com.tqmall.data.epc.common.utils.ResultUtil;
import com.tqmall.data.epc.exterior.dubbo.search.SearchExt;
import com.tqmall.data.epc.exterior.dubbo.stall.lop.OfferListExt;
import com.tqmall.tqmallstall.domain.result.openplatform.OfferListDetailDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangzhangting on 16/7/30.
 */
@Slf4j
@Service
public class OfferListBizImpl implements OfferListBiz {
    @Autowired
    private ShiroBiz shiroBiz;
    @Autowired
    private OfferListExt offerListExt;
    @Autowired
    private SearchExt searchExt;


    @Override
    public Result chooseOfferGoods(ChooseOfferGoodsParam param) {
        if(param==null){
            return ResultUtil.errorResult(EpcError.ARG_ERROR);
        }
        if(param.getWishListId()==null || param.getWishListId()<1
                || param.getOfferListId()==null || param.getOfferListId()<1
                || StringUtils.isEmpty(param.getOfferListGoodsIds())){

            return ResultUtil.errorResult(EpcError.ARG_ERROR);
        }

        param.setUid(shiroBiz.getUserId());

        return offerListExt.chooseOfferGoods(param);
    }

    @Override
    public Result<OfferListDetailBO> getOfferListDetail(Integer offerListId) {
        if(offerListId==null || offerListId<1){
            return ResultUtil.errorResult(EpcError.ARG_ERROR);
        }

        OfferListDetailDTO detailDTO = offerListExt.getOfferListDetail(offerListId);
        if(detailDTO==null || detailDTO.getWishListDto()==null){
            return ResultUtil.errorResult(EpcError.ARG_ERROR);
        }

        if(!shiroBiz.getUserId().equals(detailDTO.getWishListDto().getUserId())){
            log.info("getOfferListDetail failed, offerId:{}, wishUserId:{}, currentUserId:{}",
                    offerListId, detailDTO.getWishListDto().getUserId(), shiroBiz.getUserId());

            return ResultUtil.errorResult(EpcError.ARG_ERROR);
        }

        OfferListDetailBO bo = BdUtil.complexPropertiesCopy(detailDTO, new TypeToken<OfferListDetailBO>(){}.getType());
        if(bo==null){
            return ResultUtil.errorResult(EpcError.UN_KNOW_EXCEPTION);
        }

        return ResultUtil.successResult(bo);
    }

    @Override
    public Result<OfferListBO> getDetailByWishAndSeller(Integer sellerId, Integer wishListId) {
        if(sellerId==null || sellerId<1 || wishListId==null || wishListId<1){
            return ResultUtil.errorResult(EpcError.ARG_ERROR);
        }

        OfferListBO listBO = offerListExt.getDetailByWishAndSeller(sellerId, wishListId);
        if(listBO==null){
            return ResultUtil.errorResult(EpcError.ARG_ERROR);
        }

        if(!shiroBiz.getUserId().equals(listBO.getUserId())){
            log.info("getDetailByWishAndSeller failed, offerUserId:{}, currentUserId:{}",
                    listBO.getUserId(), shiroBiz.getUserId());

            return ResultUtil.errorResult(EpcError.ARG_ERROR);
        }

        return ResultUtil.successResult(listBO);
    }

    @Override
    public PagingResult<WishOrderBO> paged(WishOrderQueryParam param) {
        if(param==null){
            return ResultUtil.pageErrorResult(EpcError.ARG_ERROR);
        }
        Integer p = param.getP();
        if(p==null || p<1){
            p = 1;
        }
        p = p - 1;

        //如果有关键词，则从搜索查询
        String keyword = param.getKeyword();
        if(keyword!=null){
            keyword = keyword.trim();
            if(!"".equals(keyword)){
                return pagedFromSearch(p, param.getStatus(), keyword);
            }
        }

        if("".equals(param.getStatus())){
            param.setStatus(null);
        }
        if("".equals(param.getOfferListSn())){
            param.setOfferListSn(null);
        }

        param.setLimit(ConstantBean.OFFER_PAGE_SIZE);
        param.setStart(p * param.getLimit());
        param.setUid(shiroBiz.getUserId());

        return offerListExt.paged(param);
    }

    /**
     * 从搜索查询报价单数据
     * @param p
     * @param status
     * @param keyword
     * @return
     */
    private PagingResult<WishOrderBO> pagedFromSearch(Integer p, String status, String keyword) {
        Integer userId = shiroBiz.getUserId();
        WishSnSearchResultBO resultBO =
                searchExt.offerListSnSearch(userId, status, "ZFZ,YZF", keyword, p, ConstantBean.OFFER_PAGE_SIZE);
        if(resultBO==null){
            return ResultUtil.pageErrorResult(EpcError.NO_DATA_ERROR);
        }

        List<WishOrderBO> list = new ArrayList<>();
        WishOrderQueryParam param = new WishOrderQueryParam();
        param.setUid(userId);
        param.setLimit(1);
        param.setStart(0);
        for(String sn : resultBO.getSnList()){
            param.setOfferListSn(sn);
            PagingResult<WishOrderBO> pagingResult = offerListExt.paged(param);
            if(pagingResult.isSuccess()) {
                list.addAll(pagingResult.getList());
            }
        }
        return PagingResult.wrapSuccessfulResult(list, resultBO.getTotal());
    }

    @Override
    public Result<WishOrderBO> getOrderDetail(Integer orderId, String orderSn) {
        if(orderId==null || orderId<1){
            if(StringUtils.isEmpty(orderSn)){
                return ResultUtil.errorResult(EpcError.ARG_ERROR);
            }

            WishOrderQueryParam param = new WishOrderQueryParam();
            param.setUid(shiroBiz.getUserId());
            param.setStart(0);
            param.setLimit(1);
            param.setOfferListSn(orderSn);

            PagingResult<WishOrderBO> pagingResult = offerListExt.paged(param);
            if(CollectionUtils.isEmpty(pagingResult.getList())){
                return ResultUtil.errorResult(EpcError.ARG_ERROR);
            }
            return ResultUtil.successResult(pagingResult.getList().get(0));
        }

        WishOrderBO orderBO = offerListExt.getOrderDetail(shiroBiz.getUserId(), orderId, null);
        if(orderBO==null){
            return ResultUtil.errorResult(EpcError.ARG_ERROR);
        }
        return ResultUtil.successResult(orderBO);
    }

    @Override
    public Result confirmReceive(String offerListSn) {
        if(StringUtils.isEmpty(offerListSn)){
            return ResultUtil.errorResult(EpcError.ARG_ERROR);
        }
        return offerListExt.confirmReceive(shiroBiz.getUserId(), offerListSn);
    }
}
