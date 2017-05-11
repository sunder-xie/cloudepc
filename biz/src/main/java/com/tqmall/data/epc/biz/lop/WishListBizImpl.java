package com.tqmall.data.epc.biz.lop;

import com.tqmall.core.common.entity.PagingResult;
import com.tqmall.core.common.entity.Result;
import com.tqmall.data.epc.bean.bizBean.lop.*;
import com.tqmall.data.epc.bean.param.lop.WishListCancelParam;
import com.tqmall.data.epc.bean.param.lop.WishListParam;
import com.tqmall.data.epc.bean.param.lop.WishListRequestParam;
import com.tqmall.data.epc.bean.param.lop.WishOrderCreateParam;
import com.tqmall.data.epc.biz.car.CarCategoryBiz;
import com.tqmall.data.epc.biz.chat.ChatBiz;
import com.tqmall.data.epc.biz.shiro.ShiroBiz;
import com.tqmall.data.epc.common.bean.ConstantBean;
import com.tqmall.data.epc.common.bean.EpcError;
import com.tqmall.data.epc.common.utils.ResultUtil;
import com.tqmall.data.epc.exterior.dubbo.search.SearchExt;
import com.tqmall.data.epc.exterior.dubbo.stall.lop.OfferListExt;
import com.tqmall.data.epc.exterior.dubbo.stall.lop.WishListExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by huangzhangting on 16/7/30.
 */
@Service
public class WishListBizImpl implements WishListBiz {
    @Autowired
    private ShiroBiz shiroBiz;

    @Autowired
    private WishListExt wishListExt;
    @Autowired
    private SearchExt searchExt;
    @Autowired
    private OfferListExt offerListExt;
    @Autowired
    private CarCategoryBiz carCategoryBiz;
    @Autowired
    private WishListCityBiz wishListCityBiz;
    @Autowired
    private ChatBiz chatBiz;


    @Override
    public PagingResult<WishListVO> getPageByStatus(Integer p, String status) {
        if(p==null || p<1){
            p = 1;
        }
        p = p - 1;

        WishListParam param = new WishListParam();
        param.setUid(shiroBiz.getUserId());
        param.setLimit(ConstantBean.WISH_PAGE_SIZE);
        param.setStart(p * param.getLimit());
        param.setStatus(status);
        return wishListExt.paged(param);
    }

    @Override
    public PagingResult<WishListVO> getPageFromSearch(Integer p, String status, String keyword) {
        Integer userId = shiroBiz.getUserId();
        WishSnSearchResultBO resultBO =
                searchExt.wishListSnSearch(userId, status, keyword, p, ConstantBean.WISH_PAGE_SIZE);
        if(resultBO==null){
            return ResultUtil.pageErrorResult(EpcError.NO_DATA_ERROR);
        }

        List<WishListVO> list = new ArrayList<>();
        WishListParam param = new WishListParam();
        param.setUid(userId);
        param.setLimit(1);
        param.setStart(0);
        for(String sn : resultBO.getSnList()) {
            param.setWishListSn(sn);
            PagingResult<WishListVO> pagingResult = wishListExt.paged(param);
            if(pagingResult.isSuccess()) {
                list.addAll(pagingResult.getList());
            }
        }
        return PagingResult.wrapSuccessfulResult(list, resultBO.getTotal());
    }

    @Override
    public Result cancel(WishListCancelParam param) {
        if(param==null){
            return ResultUtil.errorResult(EpcError.ARG_ERROR);
        }
        if(param.getWishId()==null){
            return ResultUtil.errorResult(EpcError.ARG_ERROR);
        }
        if(StringUtils.isEmpty(param.getReason())){
            return ResultUtil.errorResult(EpcError.ARG_ERROR);
        }

        param.setUid(shiroBiz.getUserId());

        return wishListExt.cancel(param);
    }

    @Override
    public Result create(WishListRequestParam param) {
        if(param==null){
            return ResultUtil.errorResult(EpcError.ARG_ERROR);
        }
        List<WishListGoodsBO> goodsList = param.getGoodsList();
        if(CollectionUtils.isEmpty(goodsList)){
            return ResultUtil.errorResult(EpcError.ARG_ERROR);
        }
        for (WishListGoodsBO goods : goodsList) {
            if (goods.getGoodsNumber() == null || goods.getGoodsNumber() <= 0) {
                return ResultUtil.errorResult(EpcError.ARG_ERROR);
            }
        }

        Integer cityId = shiroBiz.getUserCity();
        Result result = wishListCityBiz.checkCity(cityId);
        if(!result.isSuccess()){
//            return ResultUtil.errorResult(EpcError.UN_OPEN_CITY_ERROR, wishListCityBiz.getCityNames());
            return ResultUtil.errorResult(EpcError.UN_OPEN_CITY_ERROR, 1);
        }

        param.setUid(shiroBiz.getUserId());
        param.setCityId(cityId);
        param.setRefer("epc|" + param.getVersion());

        Result createResult = wishListExt.create(param);
        //创建成功，发送im给到 淘汽客服
        if(createResult.isSuccess()){
            String companyName = param.getCompanyName();
            Integer yearId = param.getYear();
            String carName = carCategoryBiz.getOnlineCarNameById(yearId);
            String cityName = shiroBiz.getUserCityName();
            chatBiz.newWishOrderNotify(companyName,carName,cityName);
        }

        return createResult;
    }

    @Override
    public Result createOrder(WishOrderCreateParam param) {
        if(param==null){
            return ResultUtil.errorResult(EpcError.ARG_ERROR);
        }

        param.setUid(shiroBiz.getUserId());
        param.setCityId(shiroBiz.getUserCity());

        return wishListExt.createOrder(param);
    }

    @Override
    public Result getCompetitorInfoByWishId(Integer wishListId) {
        if(wishListId==null || wishListId<1){
            return ResultUtil.errorResult(EpcError.ARG_ERROR);
        }
        List<Integer> wishIds = new ArrayList<>();
        wishIds.add(wishListId);
        Map<Integer, List<CompetitorInfoBO>> map = wishListExt.getCompetitorInfo(wishIds, -1, null);
        if(map==null){
            return ResultUtil.errorResult(EpcError.NO_DATA_ERROR, wishListId);
        }
        return ResultUtil.successResult(map.get(wishListId));
    }

    @Override
    public Result<WishListBO> getWishListById(Integer id) {
        if(id==null || id<1){
            return ResultUtil.errorResult(EpcError.ARG_ERROR);
        }
        WishListBO wishListBO = wishListExt.getWishListById(id, shiroBiz.getUserId());
        if(wishListBO==null){
            return ResultUtil.errorResult(EpcError.ARG_ERROR);
        }
        return ResultUtil.successResult(wishListBO);
    }

    @Override
    public Result<WishListImResultBO> groupedWishInfo(String wishListSn, Integer sellerId) {
        if(StringUtils.isEmpty(wishListSn) || sellerId==null || sellerId<1){
            return ResultUtil.errorResult(EpcError.ARG_ERROR);
        }

        WishListParam param = new WishListParam();
        param.setUid(shiroBiz.getUserId());
        param.setLimit(1);
        param.setStart(0);
        param.setWishListSn(wishListSn);
        PagingResult<WishListVO> pagingResult = wishListExt.paged(param);
        if(pagingResult.getList().isEmpty()){
            return ResultUtil.errorResult(EpcError.NO_DATA_ERROR);
        }

        WishListImResultBO imResultBO = new WishListImResultBO();
        imResultBO.setStatus("未报价");
        WishListVO wishListVO = pagingResult.getList().get(0);
        imResultBO.setWishListVO(wishListVO);

        Integer wishId = wishListVO.getWishListId();
        OfferListBO offerListBO = offerListExt.getDetailByWishAndSeller(sellerId, wishId);
        if(offerListBO != null){
            List<OfferListGoodsBO> suppleList = new ArrayList<>();
            List<OfferListGoodsBO> goodsBOList = new ArrayList<>();
            //设置弹框信息
            for (OfferListGoodsBO offerListGoodsBO : offerListBO.getOfferListGoodsList()) {
                if (offerListGoodsBO.getGroupId() < 0) {
                    suppleList.add(offerListGoodsBO);
                } else {
                    goodsBOList.add(offerListGoodsBO);
                }
                imResultBO.setSuppleGoodsList(suppleList);
                imResultBO.setGoodsBOList(goodsBOList);
            }
            if ("XYBJ".equals(offerListBO.getStatus())) {
                imResultBO.setStatus("已报价");
            }
            if ("XQRBJ".equals(wishListVO.getStatus())) {
                //确认报价单该展现的状态
                if (offerListBO.getId() != null) {
                    if ("BJJSB".equals(offerListBO.getStatus())) {
                        imResultBO.setStatus("已下单（当前联系人竞价失败）");
                    } else {
                        imResultBO.setStatus("已下单（当前联系人竞价成功）");
                    }
                }
            }
        }
        //已被XX个商家报价
        int i = 0;
        List<SellerBO> sellerList = wishListVO.getSellerList();
        if (!CollectionUtils.isEmpty(sellerList)) {
            for (SellerBO sellerBO : sellerList) {
                OfferListBO offerListBOTemp = offerListExt.getDetailByWishAndSeller(sellerBO.getId(), wishId);
                //设置显示状态
                if (offerListBOTemp!=null && "XYBJ".equals(offerListBOTemp.getStatus())) {
                    i++;
                }
                if (sellerBO.getId().equals(sellerId)) {
                    imResultBO.setSellerName(sellerBO.getCompanyName());
                }
            }
        }
        imResultBO.setSellerNum(i);

        return ResultUtil.successResult(imResultBO);
    }

    @Override
    public Result<WishListStatusBO> getYbjWishListNum() {
        WishListStatusBO statusBO = wishListExt.getYbjWishListNum(shiroBiz.getUserId());
        if(statusBO==null){
            return ResultUtil.errorResult(EpcError.NO_DATA_ERROR);
        }
        return ResultUtil.successResult(statusBO);
    }
}
