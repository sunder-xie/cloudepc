package com.tqmall.data.epc.biz.goods;

import com.tqmall.athena.domain.result.center.car.CenterCarDTO;
import com.tqmall.core.common.entity.Result;
import com.tqmall.data.epc.bean.bizBean.goods.QuoteGoodsBO;
import com.tqmall.data.epc.bean.bizBean.shop.ShopBO;
import com.tqmall.data.epc.bean.bizBean.sys.UserBO;
import com.tqmall.data.epc.biz.shiro.ShiroBiz;
import com.tqmall.data.epc.common.bean.ConstantBean;
import com.tqmall.data.epc.common.bean.EpcError;
import com.tqmall.data.epc.common.bean.enums.UserType;
import com.tqmall.data.epc.common.utils.BdUtil;
import com.tqmall.data.epc.common.utils.ResultUtil;
import com.tqmall.data.epc.exterior.dubbo.car.CenterCarExt;
import com.tqmall.data.epc.exterior.dubbo.stall.SellerServiceExt;
import com.tqmall.data.epc.exterior.dubbo.stall.StallGoodsExt;
import com.tqmall.tqmallstall.domain.result.goods.GoodsMiniDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangzhangting on 16/3/25.
 */
@Service
public class GoodsQuoteServiceImpl implements GoodsQuoteService {
    @Autowired
    private ShiroBiz shiroBiz;
    @Autowired
    private SellerServiceExt sellerServiceExt;
    @Autowired
    private StallGoodsExt stallGoodsExt;
    @Autowired
    private CenterCarExt centerCarExt;


    @Override
    public Result<List<QuoteGoodsBO>> queryGoodsQuote(Integer carId, String oeNum, Integer qualityId) {
        if(carId==null || carId<1 || StringUtils.isEmpty(oeNum)){
            return ResultUtil.errorResult(EpcError.ARG_ERROR);
        }
        //查询车款（level 6）
        CenterCarDTO carDTO = centerCarExt.getCenterCarById(carId);
        if(carDTO==null){
            return ResultUtil.errorResult(EpcError.ARG_ERROR);
        }
        //查询车型（level 3）
        carDTO = centerCarExt.getCenterCarById(carDTO.getModelId());
        if(carDTO==null){
            return ResultUtil.errorResult(EpcError.ARG_ERROR);
        }

        List<Integer> orgIds = sellerServiceExt.getSuitableOrgId(shiroBiz.getUserCity(), carDTO.getPid());
        if(orgIds.isEmpty()){
            return ResultUtil.errorResult(EpcError.NO_DATA_ERROR);
        }
        List<GoodsMiniDTO> goodsMiniDTOs = stallGoodsExt.queryAllGoods(oeNum, qualityId, orgIds);
        if(goodsMiniDTOs.isEmpty()){
            return ResultUtil.errorResult(EpcError.NO_DATA_ERROR);
        }

        return ResultUtil.successResult(handleGoodsPrice(goodsMiniDTOs));
    }

    /** 根据用户类型处理商品价格 */
    private List<QuoteGoodsBO> handleGoodsPrice(List<GoodsMiniDTO> goodsMiniDTOs){
        UserBO user = shiroBiz.getCurrentUser();
        ShopBO shopBO = user.getShopBO();
        List<QuoteGoodsBO> list = new ArrayList<>();
        //未认证门店
        if(shopBO==null || shopBO.getVerifyStatus()==null
                || ConstantBean.SHOP_VERIFY_STATUS_SUCCESS!=shopBO.getVerifyStatus()){

            for(GoodsMiniDTO goodsMiniDTO : goodsMiniDTOs){
                QuoteGoodsBO goodsBO = BdUtil.do2bo(goodsMiniDTO, QuoteGoodsBO.class);
                goodsBO.setPrice(null);
                goodsBO.setHiddenCompanyName(hideCompanyName(goodsBO.getCompanyName()));
                list.add(goodsBO);
            }
            return list;
        }

        //云修门店
        if(UserType.YUN_XIU.getCode().equals(user.getUserTypeCode())){
            for(GoodsMiniDTO goodsMiniDTO : goodsMiniDTOs){
                QuoteGoodsBO goodsBO = BdUtil.do2bo(goodsMiniDTO, QuoteGoodsBO.class);

                BigDecimal price = goodsMiniDTO.getYunXiuPrice();
                if(price!=null && price.compareTo(BigDecimal.ZERO)>0){
                    goodsBO.setPrice(price);
                }
                goodsBO.setHiddenCompanyName(hideCompanyName(goodsBO.getCompanyName()));
                list.add(goodsBO);
            }
        }else{ //普通门店
            for(GoodsMiniDTO goodsMiniDTO : goodsMiniDTOs){
                QuoteGoodsBO goodsBO = BdUtil.do2bo(goodsMiniDTO, QuoteGoodsBO.class);
                goodsBO.setHiddenCompanyName(hideCompanyName(goodsBO.getCompanyName()));
                list.add(goodsBO);
            }
        }
        return list;
    }

    //隐藏供应商名称
    private String hideCompanyName(String name){
        if(name==null){
            return "全车件***公司";
        }
        name = name.trim();
        int len = name.length();
        if(len==0){
            return "全车件***公司";
        }
        if(len<4){
            return name + "***";
        }
        if(len<7){
            return name.substring(0, 3) + "***";
        }

        return name.substring(0, 3) + "***" + name.substring(6);
    }

}
