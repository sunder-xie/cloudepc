package com.tqmall.data.epc.exterior.dubbo.stall.lop;

import com.tqmall.core.common.entity.PagingResult;
import com.tqmall.core.common.entity.Result;
import com.tqmall.data.epc.bean.bizBean.lop.CompetitorInfoBO;
import com.tqmall.data.epc.bean.bizBean.lop.WishListBO;
import com.tqmall.data.epc.bean.bizBean.lop.WishListStatusBO;
import com.tqmall.data.epc.bean.bizBean.lop.WishListVO;
import com.tqmall.data.epc.bean.param.lop.WishListCancelParam;
import com.tqmall.data.epc.bean.param.lop.WishListParam;
import com.tqmall.data.epc.bean.param.lop.WishListRequestParam;
import com.tqmall.data.epc.bean.param.lop.WishOrderCreateParam;

import java.util.List;
import java.util.Map;

/**
 * Created by huangzhangting on 16/7/30.
 */
public interface WishListExt {
    /**
     * 分页查询需求单列表
     * @param param
     * @return
     */
    PagingResult<WishListVO> paged(WishListParam param);

    /**
     * 取消需求单
     * @param param
     * @return
     */
    Result cancel(WishListCancelParam param);

    /**
     * 创建需求单
     * @param param
     * @return
     */
    Result<Integer> create(WishListRequestParam param);

    /**
     * 创建订单
     * @param param
     * @return
     */
    Result createOrder(WishOrderCreateParam param);

    /**
     * 查询报价信息
     * @param wishListIdList
     * @param sellerId
     * @param status
     * @return
     */
    Map<Integer, List<CompetitorInfoBO>> getCompetitorInfo(List<Integer> wishListIdList, Integer sellerId, String status);

    /**
     * 根据主键id，用户id，查询需求单
     * @param id
     * @param userId
     * @return
     */
    WishListBO getWishListById(Integer id, Integer userId);

    /**
     * 根据供应商id，需求单编号查询需求单
     * @param sellerId
     * @param wishListSn
     * @return
     */
    List<WishListBO> getSuitableWishList(Integer sellerId, List<String> wishListSn);

    /**
     * 查询已报价需求单数量
     * @param userId
     * @return
     */
    WishListStatusBO getYbjWishListNum(Integer userId);
}
