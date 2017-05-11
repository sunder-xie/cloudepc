package com.tqmall.data.epc.server.avid;

import com.tqmall.core.common.entity.Result;
import com.tqmall.data.epc.bean.bizBean.avid.AvidCallBO;
import com.tqmall.data.epc.bean.param.avid.*;
import com.tqmall.data.epc.biz.avid.AvidCallBiz;
import com.tqmall.data.epc.client.bean.dto.avid.AvidCallDTO;
import com.tqmall.data.epc.client.bean.dto.avid.AvidCallGoodsDTO;
import com.tqmall.data.epc.client.bean.param.avid.CancelAvidCallParam;
import com.tqmall.data.epc.client.bean.param.avid.ModifyAvidCallParam;
import com.tqmall.data.epc.client.bean.param.avid.TurnWishParam;
import com.tqmall.data.epc.client.server.avid.AvidCallService;
import com.tqmall.data.epc.common.utils.BdUtil;
import com.tqmall.data.epc.common.utils.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Calendar;
import java.util.List;

/**
 * Created by huangzhangting on 16/10/25.
 */
@Slf4j
public class AvidCallServiceImpl implements AvidCallService {
    @Autowired
    private AvidCallBiz avidCallBiz;

    @Override
    public Result<AvidCallDTO> getAvidCallById(Integer id) {
        try {
            Result<AvidCallBO> result = avidCallBiz.getAvidCallById(id);
            AvidCallBO avidCallBO = result.getData();
            if(avidCallBO != null){
                AvidCallDTO avidCallDTO = BdUtil.do2bo(avidCallBO, AvidCallDTO.class);
                avidCallDTO.setGoodsList(BdUtil.do2bo4List(avidCallBO.getGoodsList(), AvidCallGoodsDTO.class));
                return ResultUtil.successResult(avidCallDTO);
            }
            return ResultUtil.errorResult(result);
        }catch (Exception e){
            log.error("getAvidCallById error, id:"+id, e);
            return ResultUtil.handleException(e);
        }
    }

    @Override
    public Result cancelAvidCall(CancelAvidCallParam param) {
        try {
            CancelAvidCallPO cancelAvidCallPO = BdUtil.do2bo(param, CancelAvidCallPO.class);
            return avidCallBiz.cancelAvidCall(cancelAvidCallPO);
        }catch (Exception e){
            log.error("cancelAvidCall error, param:"+param, e);
            return ResultUtil.handleException(e);
        }
    }

    @Override
    public Result modifyAvidCall(ModifyAvidCallParam param) {
        try {
            ModifyAvidCallPO modifyAvidCallPO = BdUtil.do2bo(param, ModifyAvidCallPO.class);
            if(modifyAvidCallPO != null){
                List<ModifyAvidCallGoodsPO> goodsPOList =
                        BdUtil.do2bo4List(param.getGoodsParamList(), ModifyAvidCallGoodsPO.class);

                modifyAvidCallPO.setGoodsParamList(goodsPOList);
            }

            return avidCallBiz.modifyAvidCall(modifyAvidCallPO);
        }catch (Exception e){
            log.error("modifyAvidCall error, param:"+param, e);
            return ResultUtil.handleException(e);
        }
    }

    @Override
    public Result<Integer> turnWish(TurnWishParam param) {
        try {
            TurnWishPO wishPO = BdUtil.do2bo(param, TurnWishPO.class);
            if(wishPO != null){
                wishPO.setGoodsList(BdUtil.do2bo4List(param.getGoodsList(), TurnWishGoodsPO.class));
            }
            return avidCallBiz.turnWish(wishPO);

        }catch (Exception e){
            log.error("turnWish error, param:"+param, e);
            return ResultUtil.handleException(e);
        }
    }

    @Override
    public Result<Long> getAllNewDataNum() {
        try {
            return avidCallBiz.getNewDataCount(null);
        }catch (Exception e){
            log.error("getAllNewDataNum error", e);
            return ResultUtil.handleException(e);
        }
    }

    @Override
    public Result<Long> getFiveMinNewDataNum() {
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MINUTE, -5);
            return avidCallBiz.getNewDataCount(calendar.getTime());
        }catch (Exception e){
            log.error("getFiveMinNewDataNum error", e);
            return ResultUtil.handleException(e);
        }
    }
}
