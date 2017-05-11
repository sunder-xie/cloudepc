package com.tqmall.data.epc.biz.lop;

import com.tqmall.core.common.entity.Result;
import com.tqmall.data.epc.bean.bizBean.lop.CateBO;
import com.tqmall.data.epc.common.bean.EpcError;
import com.tqmall.data.epc.common.utils.ResultUtil;
import com.tqmall.data.epc.exterior.dubbo.stall.lop.CateServiceExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangzhangting on 16/8/1.
 */
@Service
public class CateBizImpl implements CateBiz {
    @Autowired
    private CateServiceExt cateServiceExt;

    @Override
    public Result<List<String>> searchCateName(String keyword) {
        if(StringUtils.isEmpty(keyword)){
            return ResultUtil.errorResult(EpcError.ARG_ERROR);
        }
        List<CateBO> list = cateServiceExt.searchByKeyword(keyword);
        if(list.isEmpty()){
            return ResultUtil.errorResult(EpcError.NO_DATA_ERROR);
        }
        List<String> nameList = new ArrayList<>();
        for(CateBO cateBO : list){
            if(keyword.equals(cateBO.getWord())){
                continue;
            }
            nameList.add(cateBO.getWord());
        }

        return ResultUtil.successResult(nameList);
    }
}
