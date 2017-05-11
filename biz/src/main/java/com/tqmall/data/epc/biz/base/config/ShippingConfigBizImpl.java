package com.tqmall.data.epc.biz.base.config;

import com.tqmall.data.epc.bean.entity.baseConfig.EpcShippingConfigDO;
import com.tqmall.data.epc.common.redis.RedisClientTemplate;
import com.tqmall.data.epc.common.redis.RedisKeyBean;
import com.tqmall.data.epc.common.utils.JsonUtil;
import com.tqmall.data.epc.dao.mapper.baseConfig.EpcShippingConfigDOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by zxg on 16/8/30.
 * 16:47
 * no bug,以后改代码的哥们，祝你好运~！！
 */
@Service
public class ShippingConfigBizImpl implements ShippingConfigBiz {
    @Autowired
    private RedisClientTemplate redisClient;


    @Autowired
    private EpcShippingConfigDOMapper epcShippingConfigDOMapper;

    @Override
    public EpcShippingConfigDO getByPrimaryId(Integer id) {
        if(null == id || id < 1){
            return null;
        }
        return epcShippingConfigDOMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<EpcShippingConfigDO> getAll() {
        String key = RedisKeyBean.ALL_SHIPPING_CONFIG_KEY;
        String redisStr = redisClient.get(key);
        if(redisStr != null){
            return JsonUtil.jsonToList(redisStr, EpcShippingConfigDO.class);
        }
        List<EpcShippingConfigDO> list = epcShippingConfigDOMapper.selectAll(null);
        if(!list.isEmpty()){
            Collections.sort(list, new Comparator<EpcShippingConfigDO>() {
                @Override
                public int compare(EpcShippingConfigDO o1, EpcShippingConfigDO o2) {
                    return o1.getSortIndex().compareTo(o2.getSortIndex());
                }
            });
            redisClient.lazySet(key, list, RedisKeyBean.RREDIS_EXP_DAY);
        }
        return list;
    }
}
