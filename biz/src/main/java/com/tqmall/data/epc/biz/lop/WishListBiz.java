package com.tqmall.data.epc.biz.lop;

import com.tqmall.core.common.entity.PagingResult;
import com.tqmall.core.common.entity.Result;
import com.tqmall.data.epc.bean.bizBean.lop.WishListBO;
import com.tqmall.data.epc.bean.bizBean.lop.WishListImResultBO;
import com.tqmall.data.epc.bean.bizBean.lop.WishListStatusBO;
import com.tqmall.data.epc.bean.bizBean.lop.WishListVO;
import com.tqmall.data.epc.bean.param.lop.WishListCancelParam;
import com.tqmall.data.epc.bean.param.lop.WishListRequestParam;
import com.tqmall.data.epc.bean.param.lop.WishOrderCreateParam;

/**
 * Created by huangzhangting on 16/7/30.
 */
public interface WishListBiz {
    /**
     * 根据状态，分页查询需求单
     * @param p 从1开始
     * @param status 多个状态，逗号隔开
     * @return
     */
    PagingResult<WishListVO> getPageByStatus(Integer p, String status);

    /**
     * 根据关键词，从所搜获得需求单编号集合，再调用stall接口，查询需求单
     * @param p 从1开始
     * @param status 多个状态，逗号隔开
     * @param keyword
     * @return
     */
    PagingResult<WishListVO> getPageFromSearch(Integer p, String status, String keyword);

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
    Result create(WishListRequestParam param);

    /**
     * 创建订单
     * @param param
     * @return
     */
    Result createOrder(WishOrderCreateParam param);

    /**
     * 根据需求单号，查询竞价信息
     * @param wishListId
     * @return
     */
    Result getCompetitorInfoByWishId(Integer wishListId);

    /**
     * 根据主键id，查询需求单
     * @param id
     * @return
     */
    Result<WishListBO> getWishListById(Integer id);

    /**
     * im需求单页面，数据查询
     * @param wishListSn
     * @param sellerId
     * @return
     */
    Result<WishListImResultBO> groupedWishInfo(String wishListSn, Integer sellerId);

    /**
     * 获取已报价需求单数量
     * @return
     */
    Result<WishListStatusBO> getYbjWishListNum();
}
