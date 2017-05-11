package com.tqmall.data.epc.test.goods;

import com.tqmall.data.epc.common.utils.JsonUtil;
import com.tqmall.data.epc.exterior.dubbo.stall.StallGoodsExt;
import com.tqmall.data.epc.test.DubboTest;
import com.tqmall.tqmallstall.domain.result.goods.GoodsMiniDTO;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangzhangting on 16/9/1.
 */
public class GoodsTest extends DubboTest {
    @Autowired
    private StallGoodsExt stallGoodsExt;

    @Test
    public void test(){
        List<Integer> goodsIds = new ArrayList<>();
        goodsIds.add(395088);

        List<GoodsMiniDTO> list = stallGoodsExt.getGoodsMiniByIds(goodsIds);
        System.out.println(JsonUtil.objectToJson(list));
    }

}
