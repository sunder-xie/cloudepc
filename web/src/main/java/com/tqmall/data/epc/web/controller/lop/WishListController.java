package com.tqmall.data.epc.web.controller.lop;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tqmall.core.common.entity.PagingResult;
import com.tqmall.core.common.entity.Result;
import com.tqmall.data.epc.bean.bizBean.lop.*;
import com.tqmall.data.epc.bean.bizBean.order.fee.OrderOLPaymentBO;
import com.tqmall.data.epc.bean.param.lop.*;
import com.tqmall.data.epc.biz.base.PayBiz;
import com.tqmall.data.epc.biz.lop.CateBiz;
import com.tqmall.data.epc.biz.lop.OfferListBiz;
import com.tqmall.data.epc.biz.lop.WishListBiz;
import com.tqmall.data.epc.common.utils.EPCUtil;
import com.tqmall.data.epc.common.utils.ResultUtil;
import com.tqmall.data.epc.web.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by huangzhangting on 16/7/30.
 */
@Slf4j
@Controller
@RequestMapping("wish")
public class WishListController extends BaseController {
    @Autowired
    private WishListBiz wishListBiz;
    @Autowired
    private OfferListBiz offerListBiz;
    @Autowired
    private CateBiz cateBiz;
    @Autowired
    private PayBiz payBiz;


    /**
     * 查询报价中的需求单：待报价、已报价
     *
     * @param pageIndex
     * @return
     */
    @RequestMapping("wait")
    @ResponseBody
    public PagingResult<WishListVO> wait(Integer pageIndex, String keyword) {
        String status = "XDBJ,XYBJ";

        if (keyword != null) {
            keyword = keyword.trim();
            if (!"".equals(keyword)) {
                return wishListBiz.getPageFromSearch(pageIndex, status, keyword);
            }
        }

        return wishListBiz.getPageByStatus(pageIndex, status);
    }

    /**
     * 查询完成的需求单：已确认、需求单取消、报价单取消
     *
     * @param pageIndex
     * @return
     */
    @RequestMapping("complete")
    @ResponseBody
    public PagingResult<WishListVO> complete(Integer pageIndex, String keyword) {
        String status = "XQRBJ,XYQX,BYQX";

        if (keyword != null) {
            keyword = keyword.trim();
            if (!"".equals(keyword)) {
                return wishListBiz.getPageFromSearch(pageIndex, status, keyword);
            }
        }

        return wishListBiz.getPageByStatus(pageIndex, status);
    }

    /**
     * 取消需求单
     *
     * @param cancelParam
     * @return
     */
    @RequestMapping("cancel")
    @ResponseBody
    public Result cancel(@RequestBody WishListCancelParam cancelParam) {
        return wishListBiz.cancel(cancelParam);
    }

    /**
     * 创建需求单
     *
     * @param requestParam
     * @return
     */
    @RequestMapping("create")
    @ResponseBody
    public Result create(@RequestBody WishListRequestParam requestParam) {
        return wishListBiz.create(requestParam);
    }

    /**
     * 选择报价商品
     *
     * @param param
     * @return
     */
    @RequestMapping("chooseOfferGoods")
    @ResponseBody
    public Result chooseOfferGoods(ChooseOfferGoodsParam param) {
        Result result = offerListBiz.chooseOfferGoods(param);
        if (result.isSuccess()) {
            return ResultUtil.successResult(param.getOfferListId());
        }
        return result;
    }

    /**
     * 收货人信息页（确认购买）
     *
     * 需要先调用chooseOfferGoods，返回success后，再根据返回的offerId, 调用consignee；否则，不会关联报价商品
     *
     * @param offerId
     * @return
     */
    @RequestMapping("consignee")
    public String consignee(Integer offerId) {
        Result<OfferListDetailBO> result = offerListBiz.getOfferListDetail(offerId);
        if (!result.isSuccess()) {
            log.info("get offer list detail failed, offerId:{}", offerId);
            return "redirect:/wish";
        }

        Map<String, Object> map = new HashMap<>();

        List<OrderOLPaymentBO> paymentBOList = payBiz.getLocalPayment();
        map.put("paymentList", paymentBOList);

        List<Map<String, Object>> shippingList = new ArrayList<>();
        Map<String, Object> shipping = new HashMap<>();
        shipping.put("shippingId", 10);//配送方式
        shipping.put("shippingName", "淘汽档口");
        shipping.put("checked", 1);
        shippingList.add(shipping);
        map.put("shippingList", shippingList);

        request.setAttribute("result", result.getData());
        request.setAttribute("extra", map);

        return webVersion + "/cloudepc/lop/pay/consignee";
    }

    /**
     * 需求单，提交订单
     *
     * @param param
     * @return
     */
    @RequestMapping(value = "createOrder", method = RequestMethod.POST)
    @ResponseBody
    public Result createOrder(@RequestBody WishOrderCreateParam param) {
        return wishListBiz.createOrder(param);
    }

    /**
     * 订单查询
     *
     * @param param
     * @return
     */
    @RequestMapping("order/list")
    @ResponseBody
    public PagingResult<WishOrderBO> orderList(WishOrderQueryParam param) {
        return offerListBiz.paged(param);
    }

    /**
     * 订单详情页，参数二选一
     *
     * @param orderId 订单id
     * @param orderSn 订单编号
     * @return
     */
    @RequestMapping("offer/detail")
    public ModelAndView offerDetail(Integer orderId, String orderSn,Integer displayFlag) {
        ModelAndView view = new ModelAndView(webVersion + "/cloudepc/lop/offer/offerDetail");
        Result<WishOrderBO> result = offerListBiz.getOrderDetail(orderId, orderSn);
        view.addObject("orderInfo", result.getData());
        view.addObject("displayFlag",displayFlag);
        return view;
    }

    /**
     * 根据需求单id，查询竞价信息
     *
     * @param wishListId
     * @return
     */
    @RequestMapping("competitorInfo")
    @ResponseBody
    public Result getCompetitorInfoByWishId(Integer wishListId) {
        return wishListBiz.getCompetitorInfoByWishId(wishListId);
    }

    /**
     * 根据需求单id，供应商id，查询报价单商品
     *
     * @param sellerId
     * @param wishListId
     * @return
     */
    @RequestMapping("offer/goods")
    @ResponseBody
    public Result<OfferListBO> getOfferGoods(Integer sellerId, Integer wishListId) {
        return offerListBiz.getDetailByWishAndSeller(sellerId, wishListId);
    }

    /**
     * 页面联想搜索
     * 根据关键词搜索商品类目名称（当做配件名称使用）
     *
     * @param keyword
     * @return
     */
    @RequestMapping(value = "cate/suggest")
    @ResponseBody
    public Result<List<String>> suggest(String keyword) {
        return cateBiz.searchCateName(keyword);
    }

    /**
     * 确认收货
     *
     * @param offerListSn
     * @return
     */
    @RequestMapping("confirmReceive")
    @ResponseBody
    public Result confirmReceive(String offerListSn) {
        return offerListBiz.confirmReceive(offerListSn);
    }


    // ========================= start lyj =========================
    /**
     * 创建需求单页面
     *
     * @param wishListId
     * @return
     */
    @RequestMapping("createWishPage")
    public ModelAndView createWishPage(Integer wishListId) throws JsonProcessingException {
        ModelAndView view = new ModelAndView(webVersion + "/cloudepc/lop/wish/createWish");

        Result<WishListBO> result = wishListBiz.getWishListById(wishListId);
        if(result.isSuccess()){
            view.addObject("wishInfo", result.getData());

            ObjectMapper mapper = new ObjectMapper();
            String object4json = mapper.writeValueAsString(result.getData().getGoodsList());
            view.addObject("goodsList", object4json);
        }
        view.addObject("token", EPCUtil.token());

        return view;
    }

    /**
     * 我的报价单
     * @return
     */
    @RequestMapping("myOfferPage")
    public ModelAndView myOfferPage() {
        return new ModelAndView(webVersion + "/cloudepc/lop/offer/showOffer");
    }


    /**
     * 我的需求单页面
     */
    @RequestMapping(value = "myWishPage")
    public ModelAndView myWishPage() {
        return new ModelAndView(webVersion + "/cloudepc/lop/wish/showWish");
    }

    /**
     * 默认页面
     * @return
     */
    @RequestMapping("")
    public ModelAndView defaultPage(){
        return myWishPage();
    }

    /**
     * 查询已报价需求单数量
     * @return
     */
    @RequestMapping("getYbjWishListNum")
    @ResponseBody
    public Result<WishListStatusBO> getYbjWishListNum(){
        return wishListBiz.getYbjWishListNum();
    }

    /**
     * 订单支付成功回调
     *
     * @param orderSn
     * @return
     */
    @RequestMapping(value = "order/success")
    public Object orderSuccess(String orderSn) {
        Result<WishOrderBO> result = offerListBiz.getOrderDetail(null, orderSn);
        if (!result.isSuccess()) {
            log.info("get order detail failed, orderSn:{}", orderSn);
            return "redirect:/wish";
        }
        WishOrderBO order = result.getData();
        if ("ZFZ".equals(order.getPayStatus())) {
            return "redirect:/wish/pay/selectPayment?orderSn=" + orderSn;
        }
        ModelAndView view = new ModelAndView(webVersion + "/cloudepc/lop/pay/success");
        view.addObject("orderInfo", order);

        return view;
    }

    /**
     * 订单第三方支付成功，等待支付状态修改
     *
     * @param orderSn
     * @return
     */
    @RequestMapping(value = "order/waiting")
    public Object orderWaiting(String orderSn) {
        ModelAndView view = new ModelAndView(webVersion + "/cloudepc/lop/pay/waiting");
        view.addObject("orderSn", orderSn);

        return view;
    }

    /**
     * 订单支付失败
     *
     * @param orderSn
     * @return
     */
    @RequestMapping(value = "order/failed")
    public Object orderFailed(String orderSn) {
        ModelAndView view = new ModelAndView(webVersion + "/cloudepc/lop/pay/failed");
        view.addObject("orderSn", orderSn);

        return view;
    }

    /**
     * 验证订单支付状态
     * @param orderSn
     * @return
     */
    @RequestMapping("checkOrderPayStatus")
    @ResponseBody
    public Result checkOrderPayStatus(String orderSn){
        Result<WishOrderBO> result = offerListBiz.getOrderDetail(null, orderSn);
        if (result.isSuccess()) {
            if("YZF".equals(result.getData().getPayStatus())){
                return ResultUtil.successResult(1);
            }
        }
        return ResultUtil.errorResult("", "");
    }

}
