package com.tqmall.data.epc.exterior.dubbo.stall.lop;

import com.google.common.reflect.TypeToken;
import com.tqmall.core.common.entity.PagingResult;
import com.tqmall.core.common.entity.Result;
import com.tqmall.data.epc.bean.bizBean.lop.OfferListBO;
import com.tqmall.data.epc.bean.bizBean.lop.OfferListGoodsBO;
import com.tqmall.data.epc.bean.bizBean.lop.WishOrderBO;
import com.tqmall.data.epc.bean.param.lop.ChooseOfferGoodsParam;
import com.tqmall.data.epc.bean.param.lop.WishOrderQueryParam;
import com.tqmall.data.epc.common.bean.EpcError;
import com.tqmall.data.epc.common.utils.BdUtil;
import com.tqmall.data.epc.common.utils.JsonUtil;
import com.tqmall.data.epc.common.utils.ResultUtil;
import com.tqmall.tqmallstall.domain.param.openplatform.OfferListGoodsChooseRequest;
import com.tqmall.tqmallstall.domain.param.openplatform.RpcOfferListRequestParam;
import com.tqmall.tqmallstall.domain.result.openplatform.OfferListDTO;
import com.tqmall.tqmallstall.domain.result.openplatform.OfferListDetailDTO;
import com.tqmall.tqmallstall.domain.result.openplatform.OfferOrderDTO;
import com.tqmall.tqmallstall.domain.tc.MyOfferQueryDTO;
import com.tqmall.tqmallstall.service.openplatform.OfferListService;
import com.tqmall.tqmallstall.service.order.OrderReadRpcService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * Created by huangzhangting on 16/7/30.
 */
@Service
@Slf4j
public class OfferListExtImpl implements OfferListExt {
    @Autowired
    private OfferListService offerListService;
    @Autowired
    private OrderReadRpcService orderReadRpcService;


    @Override
    public Result chooseOfferGoods(ChooseOfferGoodsParam param) {
        log.info("confirm offer, param:{}", JsonUtil.objectToJson(param));

        OfferListGoodsChooseRequest request = BdUtil.do2bo(param, OfferListGoodsChooseRequest.class);
        Result<Integer> result = offerListService.selectGoods(request);

        log.info("confirm offer, result:{}", JsonUtil.objectToJson(result));
        return result;
    }

    @Override
    public OfferListDetailDTO getOfferListDetail(Integer offerListId) {
        log.info("get offer list detail, offerListId:{}", offerListId);

        Result<OfferListDetailDTO> result = offerListService.getDetailForSelectedGoods(offerListId, 0);
        if(result.isSuccess()){
            return result.getData();
        }

        log.info("get offer list detail, result:{}", JsonUtil.objectToJson(result));
        return null;
    }

    @Override
    public OfferListBO getDetailByWishAndSeller(Integer sellerId, Integer wishListId) {
        log.info("get detail by wish and seller, sellerId:{}, wishListId:{}", sellerId, wishListId);

        Result<OfferListDTO> result = offerListService.getDetailByWishListAndSeller(sellerId, wishListId);
        if(result.isSuccess() && result.getData()!=null){
            OfferListDTO dto = result.getData();
            OfferListBO offerListBO = BdUtil.do2bo(dto, OfferListBO.class);
            offerListBO.setOfferListGoodsList(BdUtil.do2bo4List(dto.getOfferListGoodsList(), OfferListGoodsBO.class));
            return offerListBO;
        }

        log.info("get detail by wish and seller, result:{}", JsonUtil.objectToJson(result));
        return null;
    }

    @Override
    public PagingResult<WishOrderBO> paged(WishOrderQueryParam param) {
        log.info("query wish order, param:{}", JsonUtil.objectToJson(param));

        MyOfferQueryDTO queryDTO = BdUtil.do2bo(param, MyOfferQueryDTO.class);
        PagingResult<OfferOrderDTO> result = orderReadRpcService.listMyFinishOffer(queryDTO);

        if(result.isSuccess() && !CollectionUtils.isEmpty(result.getList())){
            List<WishOrderBO> list =
                    BdUtil.complexPropertiesCopy(result.getList(), new TypeToken<List<WishOrderBO>>(){}.getType());
            if(list != null){
                return PagingResult.wrapSuccessfulResult(list, result.getTotal());
            }
        }

        log.info("query wish order, result:{}", JsonUtil.objectToJson(result));
        return ResultUtil.pageErrorResult(EpcError.NO_DATA_ERROR);
    }

    @Override
    public WishOrderBO getOrderDetail(Integer uid, Integer orderId, String orderSn) {
        log.info("query wish order detail, uid:{}, orderId:{}", uid, orderId);

        RpcOfferListRequestParam param = new RpcOfferListRequestParam();
        param.setUid(uid);
        param.setId(orderId);

        Result<OfferOrderDTO> result = offerListService.getOfferOrderDetail(param);

        if(result.isSuccess()){
            return BdUtil.complexPropertiesCopy(result.getData(), new TypeToken<WishOrderBO>(){}.getType());
        }

        log.info("query wish order detail, result:{}", JsonUtil.objectToJson(result));
        return null;
    }

    @Override
    public Result confirmReceive(Integer userId, String offerListSn) {
        log.info("confirm receive, userId:{}, offerListSn:{}", userId, offerListSn);

        Result<Integer> result = offerListService.receiveByOfferListSn(offerListSn, userId);

        log.info("confirm receive, result:{}", JsonUtil.objectToJson(result));
        return result;
    }
}
