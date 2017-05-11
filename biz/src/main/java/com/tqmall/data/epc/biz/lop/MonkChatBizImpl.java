package com.tqmall.data.epc.biz.lop;

import com.tqmall.core.common.entity.Result;
import com.tqmall.data.epc.bean.bizBean.lop.WishListBO;
import com.tqmall.data.epc.bean.param.lop.ImWishSnParam;
import com.tqmall.data.epc.biz.shiro.ShiroBiz;
import com.tqmall.data.epc.common.bean.EpcError;
import com.tqmall.data.epc.common.redis.RedisClientTemplate;
import com.tqmall.data.epc.common.redis.RedisKeyBean;
import com.tqmall.data.epc.common.utils.DateUtil;
import com.tqmall.data.epc.common.utils.JsonUtil;
import com.tqmall.data.epc.common.utils.ResultUtil;
import com.tqmall.data.epc.exterior.dubbo.stall.lop.WishListExt;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by huangzhangting on 16/8/11.
 */
@Slf4j
@Service
public class MonkChatBizImpl implements MonkChatBiz {
    @Autowired
    private RedisClientTemplate redisClient;
    @Autowired
    private ShiroBiz shiroBiz;
    @Autowired
    private WishListExt wishListExt;


    private String cacheWishSnKey(Integer shopId, Integer chatObjectId){
        String date = DateUtil.dateToString(new Date(), DateUtil.yyyyMMdd);
        return String.format(RedisKeyBean.WISH_SN_CACHE_DAY_KEY, shopId, chatObjectId, date);
    }

    @Override
    public Result<List<String>> cacheWishSn(ImWishSnParam param) {
        if (param==null){
            return ResultUtil.errorResult(EpcError.ARG_ERROR);
        }
        List<String> snList = param.getSnList();
        Integer chatObjectId = param.getChatObjectId();
        if(CollectionUtils.isEmpty(snList) || chatObjectId==null || chatObjectId<1){
            return ResultUtil.errorResult(EpcError.ARG_ERROR);
        }

        Integer shopId = shiroBiz.getCurrentUser().getShopId();

        log.info("to cache wish sn, shopId:{}, chatObjectId:{}, wishSnList:{}", shopId, chatObjectId, snList.toString());

        String key = cacheWishSnKey(shopId, chatObjectId);
        String redisStr = redisClient.get(key);
        List<String> cacheSnList;
        if(redisStr==null){
            cacheSnList = new ArrayList<>();
        }else{
            cacheSnList = JsonUtil.jsonToList(redisStr, String.class);
        }

        //需求单号去重
        List<String> newSnList = new ArrayList<>();
        for(String sn : snList){
            if(cacheSnList.contains(sn) || newSnList.contains(sn)){
                continue;
            }
            newSnList.add(sn);
        }
        if(newSnList.isEmpty()){
            return ResultUtil.errorResult(EpcError.ARG_ERROR);
        }

        //校验需求单
        List<WishListBO> wishListBOList = wishListExt.getSuitableWishList(chatObjectId, newSnList);
        if(wishListBOList.isEmpty()){
            return ResultUtil.errorResult(EpcError.ARG_ERROR);
        }
        newSnList = new ArrayList<>();
        Integer userId = shiroBiz.getUserId();
        for(WishListBO wishListBO : wishListBOList){
            if(wishListBO.getUserId().equals(userId)){
                newSnList.add(wishListBO.getWishListSn());
            }
        }
        if(newSnList.isEmpty()){
            return ResultUtil.errorResult(EpcError.ARG_ERROR);
        }

        cacheSnList.addAll(newSnList);
        redisClient.lazySet(key, cacheSnList, RedisKeyBean.RREDIS_EXP_DAY);
        log.info("cache wish sn success, shopId:{}, chatObjectId:{}, newSnList:{}", shopId, chatObjectId, newSnList.toString());

        return ResultUtil.successResult(newSnList);
    }

    @Override
    public Result<List<String>> getCacheWishSn(Integer chatObjectId) {
        if(chatObjectId==null || chatObjectId<1){
            return ResultUtil.errorResult(EpcError.ARG_ERROR);
        }
        String key = cacheWishSnKey(shiroBiz.getCurrentUser().getShopId(), chatObjectId);
        String redisStr = redisClient.get(key);
        if(redisStr==null){
            return ResultUtil.errorResult(EpcError.NO_DATA_ERROR);
        }
        List<String> list = JsonUtil.jsonToList(redisStr, String.class);
        return ResultUtil.successResult(list);
    }
}
