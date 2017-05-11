package com.tqmall.data.epc.biz.lop;

import com.tqmall.core.common.entity.Result;
import com.tqmall.data.epc.bean.entity.wish.WishListCityConfDO;
import com.tqmall.data.epc.common.bean.EpcError;
import com.tqmall.data.epc.common.redis.RedisClientTemplate;
import com.tqmall.data.epc.common.redis.RedisKeyBean;
import com.tqmall.data.epc.common.utils.JsonUtil;
import com.tqmall.data.epc.common.utils.ResultUtil;
import com.tqmall.data.epc.dao.mapper.wish.WishListCityConfDOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by huangzhangting on 16/8/11.
 */
@Service
public class WishListCityBizImpl implements WishListCityBiz {
    @Autowired
    private RedisClientTemplate redisClient;
    @Autowired
    private WishListCityConfDOMapper cityConfDOMapper;


    @Override
    public Result checkCity(Integer cityId) {
        if(cityId==null || cityId<1){
            return ResultUtil.errorResult(EpcError.ARG_ERROR);
        }
        String key = String.format(RedisKeyBean.WISH_LIST_CITY_KEY, cityId);
        String redisStr = redisClient.get(key);
        if(redisClient.isNone(redisStr)){
            return ResultUtil.errorResult(EpcError.UN_OPEN_CITY_ERROR);
        }

        if(redisStr != null){
            return ResultUtil.successResult(redisStr);
        }

        Integer id = cityConfDOMapper.checkCity(cityId);
        if(id == null){
            redisClient.setNone(key);
            return ResultUtil.errorResult(EpcError.UN_OPEN_CITY_ERROR);
        }

        redisClient.setStringWithTime(key, id+"", RedisKeyBean.RREDIS_EXP_DAY);
        return ResultUtil.successResult(id);
    }

    @Override
    public String getCityNames() {
        String redisStr = redisClient.get(RedisKeyBean.WISH_LIST_CITY_NAMES_KEY);
        if(redisStr != null){
            return redisStr;
        }

        //暂时没用到，所以先不缓存城市站对象
//        redisStr = redisClient.get(RedisKeyBean.WISH_LIST_CITIES_KEY);
//        if(redisStr != null){
//            List<WishListCityConfDO> list = JsonUtil.jsonToList(redisStr, WishListCityConfDO.class);
//            if(!list.isEmpty()){
//                StringBuilder sb = new StringBuilder(80);
//                for(WishListCityConfDO cityConfDO : list){
//                    sb.append(", ").append(cityConfDO.getCityName());
//                }
//                sb.deleteCharAt(0);
//                String str = sb.toString();
//                redisClient.setStringWithTime(RedisKeyBean.WISH_LIST_CITY_NAMES_KEY, str, RedisKeyBean.RREDIS_EXP_DAY);
//                return str;
//            }
//        }

        List<WishListCityConfDO> confDOList = cityConfDOMapper.getAllCity();
        if(confDOList.isEmpty()){
            return "";
        }

        Collections.sort(confDOList, new Comparator<WishListCityConfDO>() {
            @Override
            public int compare(WishListCityConfDO o1, WishListCityConfDO o2) {
                return o1.getCityName().compareTo(o2.getCityName());
            }
        });

        StringBuilder names = new StringBuilder(80);
        for(WishListCityConfDO cityConfDO : confDOList){
            names.append(", ").append(cityConfDO.getCityName());
        }
        names.deleteCharAt(0);
        String str = names.toString();

        redisClient.setStringWithTime(RedisKeyBean.WISH_LIST_CITY_NAMES_KEY, str, RedisKeyBean.RREDIS_EXP_DAY);

        return str;
    }
}
