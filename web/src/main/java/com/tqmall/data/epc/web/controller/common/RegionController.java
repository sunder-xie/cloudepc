package com.tqmall.data.epc.web.controller.common;

import com.tqmall.data.epc.bean.bizBean.ZTree;
import com.tqmall.data.epc.bean.bizBean.base.RegionBO;
import com.tqmall.data.epc.exterior.dubbo.base.RegionServiceExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by huangzhangting on 16/7/5.
 */
@Controller
@RequestMapping("region")
public class RegionController {
    @Autowired
    private RegionServiceExt regionServiceExt;


    @RequestMapping("allCity")
    @ResponseBody
    public List<ZTree> getAllCity(){
        return regionServiceExt.cityZTreeList();
    }

    @RequestMapping("subRegions")
    @ResponseBody
    public List<RegionBO> subRegions(Integer pid){
        return regionServiceExt.subRegions(pid);
    }
}
