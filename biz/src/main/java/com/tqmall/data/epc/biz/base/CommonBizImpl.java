package com.tqmall.data.epc.biz.base;

import com.tqmall.core.common.entity.Result;
import com.tqmall.data.epc.bean.bizBean.goods.GoodsQualityBO;
import com.tqmall.data.epc.bean.bizBean.sys.UserBO;
import com.tqmall.data.epc.bean.entity.sys.EpcUserPvRecordDO;
import com.tqmall.data.epc.biz.shiro.ShiroBiz;
import com.tqmall.data.epc.biz.util.UserUtil;
import com.tqmall.data.epc.common.bean.EpcError;
import com.tqmall.data.epc.common.utils.ResultUtil;
import com.tqmall.data.epc.dao.mapper.sys.EpcUserPvRecordDOMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by huangzhangting on 16/9/6.
 */
@Slf4j
@Service
public class CommonBizImpl implements CommonBiz {
    @Autowired
    private ShiroBiz shiroBiz;
    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;
    @Autowired
    private EpcUserPvRecordDOMapper userPvRecordDOMapper;


    @Override
    public Result<List<GoodsQualityBO>> getGoodsQuality() {
        List<GoodsQualityBO> list = new ArrayList<>();
        GoodsQualityBO qualityBO = new GoodsQualityBO();
        qualityBO.setId(1);
        qualityBO.setName("正厂原厂");
        list.add(qualityBO);

        qualityBO = new GoodsQualityBO();
        qualityBO.setId(2);
        qualityBO.setName("正厂配套");
        list.add(qualityBO);

        qualityBO = new GoodsQualityBO();
        qualityBO.setId(3);
        qualityBO.setName("正厂下线");
        list.add(qualityBO);

        qualityBO = new GoodsQualityBO();
        qualityBO.setId(4);
        qualityBO.setName("全新拆车");
        list.add(qualityBO);

        qualityBO = new GoodsQualityBO();
        qualityBO.setId(5);
        qualityBO.setName("旧件拆车");
        list.add(qualityBO);

        qualityBO = new GoodsQualityBO();
        qualityBO.setId(7);
        qualityBO.setName("品牌");
        list.add(qualityBO);

        return ResultUtil.successResult(list);
    }

    @Override
    public Result addPageGuideRecord(final Integer pageUriId) {
        if(pageUriId==null || pageUriId<1){
            return ResultUtil.errorResult(EpcError.ARG_ERROR);
        }

        //设置缓存
        if(!shiroBiz.setPageGuideRecord(pageUriId)){
            return ResultUtil.errorResult("", "已经缓存过了，uriId："+pageUriId);
        }

        UserBO user = shiroBiz.getCurrentUser();
        final String mobile = user.getMobile();

        //持久化到数据库
        taskExecutor.execute(new Runnable() {
            @Override
            public void run() {
                log.info("addPageGuideRecord, mobile:{}, uriId:{}", mobile, pageUriId);
                EpcUserPvRecordDO recordDO = new EpcUserPvRecordDO();
                recordDO.setGmtCreate(new Date());
                recordDO.setUserMobile(mobile);
                recordDO.setPageUriId(pageUriId);
                userPvRecordDOMapper.insertSelective(recordDO);
            }
        });

        return ResultUtil.successResult(pageUriId);
    }

    @Override
    public void setUserPvHisRecord(String mobile) {
        //入参调用方已验证，这里无需再次校验
        List<Integer> uriIdList = userPvRecordDOMapper.selectUriIdsByMobile(mobile);
        if(uriIdList.isEmpty()){
            shiroBiz.removeFromSession(UserUtil.USER_PV_RECORD_KEY);
        }else{
            Set<Integer> set = new HashSet<>(uriIdList);
            shiroBiz.putIntoSession(UserUtil.USER_PV_RECORD_KEY, set);
            log.info("setUserPvHisRecord, mobile:{}, uriIds:{}", mobile, set.toString());
        }
    }

    @Override
    public void refreshSessionCache() {
        UserBO user = shiroBiz.getCurrentUser();
        setUserPvHisRecord(user.getMobile());


    }
}
