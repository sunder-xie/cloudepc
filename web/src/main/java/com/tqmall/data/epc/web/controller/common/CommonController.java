package com.tqmall.data.epc.web.controller.common;

import com.tqmall.core.common.entity.Result;
import com.tqmall.data.epc.bean.bizBean.goods.GoodsQualityBO;
import com.tqmall.data.epc.biz.base.CommonBiz;
import com.tqmall.data.epc.common.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by huangzhangting on 16/9/6.
 */
@Controller
@RequestMapping("common")
public class CommonController {
    @Autowired
    private CommonBiz commonBiz;


    /**
     * 获取配件配置
     *
     * @return
     */
    @RequestMapping(value = "getGoodsQuality")
    @ResponseBody
    public Result<List<GoodsQualityBO>> getGoodsQuality() {
        return commonBiz.getGoodsQuality();
    }

    /**
     * 添加页面引导记录
     * @param pageUriId
     * @return
     */
    @RequestMapping(value = "addPageGuideRecord")
    @ResponseBody
    public Result addPageGuideRecord(Integer pageUriId){
        return commonBiz.addPageGuideRecord(pageUriId);
    }




    /**
     * 刷新session缓存，方便测试
     * @return
     */
    @RequestMapping(value = "refreshSessionCache")
    @ResponseBody
    public Result refreshSessionCache(){
        commonBiz.refreshSessionCache();
        return ResultUtil.successResult("session缓存刷新成功！");
    }

}
