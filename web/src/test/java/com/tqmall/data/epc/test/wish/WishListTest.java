package com.tqmall.data.epc.test.wish;

import com.tqmall.core.common.entity.Result;
import com.tqmall.data.epc.bean.bizBean.lop.WishListGoodsBO;
import com.tqmall.data.epc.bean.param.lop.WishListRequestParam;
import com.tqmall.data.epc.common.utils.EPCUtil;
import com.tqmall.data.epc.web.controller.lop.WishListController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Created by huangzhangting on 16/7/30.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:config/application-context.xml"})
public class WishListTest {
    @Autowired
    private WishListController wishListController;

    //云修用户创建需求单
    @Test
    public void createWishOrder4Yun() {
        WishListRequestParam param = new WishListRequestParam();
        param.setVin("LE4GG8BB2EL305927");
        param.setBrand(17);
        param.setSeries(259);
        param.setModel(56029);
        param.setEngine(56030);
        param.setYear(56033);
        param.setCompanyName("陈可一二三");
        param.setTelephone("18658892140");
        param.setWishListMemo("java代码生成");
        param.setWishListMaker("陈可一二三");
        param.setWishListMakerTel("18658892140");
        param.setToken(EPCUtil.token());

        List<WishListGoodsBO> goodsList = new ArrayList<>();
        WishListGoodsBO wishListGoodsBO = new WishListGoodsBO();
        wishListGoodsBO.setGoodsName("手动变速箱油");
        wishListGoodsBO.setGoodsNumber(2);
        wishListGoodsBO.setGoodsImages("http://tqmall-image.oss-cn-hangzhou.aliyuncs.com/merchant/web/test/20160122/101453429243661.jpg");
        wishListGoodsBO.setQualityTypeStr("1,2");
        goodsList.add(wishListGoodsBO);

        param.setGoodsList(goodsList);

        Result<Integer> result = wishListController.create(param);

        assertTrue(result.isSuccess());
    }
}
