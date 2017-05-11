package com.tqmall.data.epc.web.controller.goods;

import com.tqmall.data.epc.bean.bizBean.goods.QuoteGoodsBO;
import com.tqmall.data.epc.biz.goods.GoodsQuoteService;
import com.tqmall.data.epc.web.BaseController;
import com.tqmall.core.common.entity.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


/**
 * Created by lyj on 16/2/17.
 */
@Controller
@RequestMapping("goodsDetail")
@Slf4j
public class GoodsDetailController extends BaseController {
    @Autowired
    private GoodsQuoteService goodsQuoteService;


    /**
     * 查询报价信息
     * @param carId 车款id，level 6 级别id
     * @param oeNum oe码
     * @param qualityId 品质
     * @return
     */
    @RequestMapping("getGoodsQuote")
    @ResponseBody
    public Result<List<QuoteGoodsBO>> getGoodsQuote(Integer carId, String oeNum, Integer qualityId){
        return goodsQuoteService.queryGoodsQuote(carId, oeNum, qualityId);
    }

}
