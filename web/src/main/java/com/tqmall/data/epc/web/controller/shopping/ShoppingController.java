package com.tqmall.data.epc.web.controller.shopping;

import com.tqmall.data.epc.bean.bizBean.order.fee.OrderOLPaymentBO;
import com.tqmall.data.epc.bean.bizBean.shop.AddressBO;
import com.tqmall.data.epc.bean.entity.baseConfig.EpcShippingConfigDO;
import com.tqmall.data.epc.bean.entity.cart.EpcCartDO;
import com.tqmall.data.epc.bean.entity.cart.EpcCartVO;
import com.tqmall.data.epc.bean.param.cart.CheckGoodsParam;
import com.tqmall.data.epc.bean.param.cart.PurchaseParam;
import com.tqmall.data.epc.biz.base.PayBiz;
import com.tqmall.data.epc.biz.base.config.ShippingConfigBiz;
import com.tqmall.data.epc.biz.cart.CartBiz;
import com.tqmall.data.epc.biz.sys.UserBiz;
import com.tqmall.data.epc.common.bean.ConstantBean;
import com.tqmall.data.epc.common.bean.EpcError;
import com.tqmall.data.epc.common.utils.EPCUtil;
import com.tqmall.data.epc.common.utils.JsonUtil;
import com.tqmall.data.epc.common.utils.ResultUtil;
import com.tqmall.data.epc.exterior.dubbo.grace.SellerTaxExt;
import com.tqmall.data.epc.web.BaseController;
import com.tqmall.core.common.entity.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * Created by huangzhangting on 16/7/11.
 */
@Controller
@RequestMapping("shopping")
@Slf4j
public class ShoppingController extends BaseController{
    @Autowired
    private CartBiz cartBiz;
    @Autowired
    private PayBiz payBiz;
    @Autowired
    private UserBiz userBiz;
    @Autowired
    private ShippingConfigBiz shippingConfigBiz;
    @Autowired
    private SellerTaxExt sellerTaxExt;

    /**
     * 进入购物车页面
     * @return
     */
    @RequestMapping(value="shoppingCart")
    public ModelAndView shoppingCartModel(){
        ModelAndView modelAndView = new ModelAndView(webVersion+"/cloudepc/shopping/shoppingCartInfo");
        //modelAndView.addObject("GO_HOME", "GO_HOME");
        modelAndView.addObject("HIDE_BREADCRUMB", "hide");
        modelAndView.addObject("TOP_MSG", "购物车");
        return modelAndView;
    }

    /**
     * 购物车进入核对订单信息页面
     * @param idStr
     * @return
     */
    @RequestMapping(value="consignee")
    public ModelAndView consignee(String idStr){
        ModelAndView modelAndView = new ModelAndView(webVersion+"/cloudepc/part/consignee");
        //获取默认地址list
        Result<List<AddressBO>> listResult = userBiz.getUserAddressList();
        if(listResult.isSuccess()) {
            modelAndView.addObject("addressList", listResult.getData());
        }
        modelAndView.addObject("addressJson",JsonUtil.objectToJson(listResult.getData()));
        //获取所有配送方式
        List<EpcShippingConfigDO> shippingConfigList = shippingConfigBiz.getAll();
        modelAndView.addObject("shippingConfigList",shippingConfigList);
        // 获取支付方式
        List<OrderOLPaymentBO> paymentBOList = payBiz.getLocalPayment();
        modelAndView.addObject("paymentList", paymentBOList);
        //获取待支付商品信息
        Result result = cartBiz.getGoodsListByIds(idStr);
        //modelAndView.addObject("idStr",idStr);
        modelAndView.addObject("result",result);
        modelAndView.addObject("sellerList",JsonUtil.objectToJson(result.getData()));
        return modelAndView;
    }

    /**
     * 立即购买
     * */
    @RequestMapping(value="purchaseImmediately")
    public ModelAndView purchaseImmediately(Integer goodsId,String oeNumber,Integer goodsNumber,
                                            Integer sellerId,String partName,String sellerName,String sellerTel){
        ModelAndView modelAndView = new ModelAndView(webVersion+"/cloudepc/part/consignee");
        //获取默认地址list
        Result<List<AddressBO>> listResult = userBiz.getUserAddressList();
        if(listResult.isSuccess()){
            modelAndView.addObject("addressList",listResult.getData());
        }
        modelAndView.addObject("addressJson",JsonUtil.objectToJson(listResult.getData()));
        //获取所有配送方式
        List<EpcShippingConfigDO> shippingConfigList = shippingConfigBiz.getAll();
        modelAndView.addObject("shippingConfigList",shippingConfigList);
        // 获取支付方式
        List<OrderOLPaymentBO> paymentBOList = payBiz.getLocalPayment();
        modelAndView.addObject("paymentList", paymentBOList);
        //获取待支付商品信息
        PurchaseParam purchaseParam = new PurchaseParam();
        purchaseParam.setGoodsId(goodsId);
        purchaseParam.setOeNumber(oeNumber);
        purchaseParam.setGoodsNumber(goodsNumber);
        purchaseParam.setPartName(partName);
        purchaseParam.setSellerId(sellerId);
        purchaseParam.setSellerName(sellerName);
        purchaseParam.setSellerTel(sellerTel);
        Result<List<EpcCartVO>> result = cartBiz.getGoodsInfoList(purchaseParam);
        //modelAndView.addObject("idStr",String.valueOf(purchaseParam.getGoodsId()));
        modelAndView.addObject("result",result);
        modelAndView.addObject("sellerList",JsonUtil.objectToJson(result.getData()));

        modelAndView.addObject("buyNow", "buyNow");

        return modelAndView;
    }

    /**
     * 开普通发票时获取供应商税率列表信息
     * @param orgIdStr
     * @return
     */
    @RequestMapping(value="getTaxRateMap",method = RequestMethod.GET)
    @ResponseBody
    public Result<Map<Integer, BigDecimal>> getTaxRateMap(String orgIdStr){
        if(StringUtils.isEmpty(orgIdStr)){
            return ResultUtil.errorResult(EpcError.ARG_ERROR);
        }
        String[] org = orgIdStr.split(",");
        List<Integer> orgIdList = new ArrayList<>();
        for(int i=0;i<org.length;i++){
            orgIdList.add(Integer.parseInt(org[i]));
        }
        return sellerTaxExt.getSomeTaxRate(orgIdList);
    }

    /**
     * 获取增值税率
     * @return
     */
    @RequestMapping(value="getAddTax",method = RequestMethod.GET)
    @ResponseBody
    public Result getAddTax(){
        BigDecimal addTaxRate = EPCUtil.getRate(ConstantBean.ADDED_TAX_RATE);
        return Result.wrapSuccessfulResult(addTaxRate);
    }

    /**
     * 加入购物车
     * @param cartGoods
     * @return
     */
    @RequestMapping(value = "joinShoppingCart", method = RequestMethod.POST)
    @ResponseBody
    public Result joinShoppingCart(@RequestBody EpcCartDO cartGoods){
        return cartBiz.addGoods(cartGoods);
    }

    /**
     *
     * @return
     * @param goodsIdStr
     */
    @RequestMapping(value="deleteGoodsByIdList")
    @ResponseBody
    public Result deleteGoodsByIdList(String goodsIdStr){
        if(StringUtils.isEmpty(goodsIdStr)){
            ResultUtil.errorResult(EpcError.ARG_ERROR);
        }
        String[] org = goodsIdStr.split(",");
        List<Integer> goodsIdList = new ArrayList<>();
        for(int i=0;i<org.length;i++){
            goodsIdList.add(Integer.parseInt(org[i]));
        }
        return cartBiz.deleteGoodsByIdList(goodsIdList);
    }

    /**
     * 删除购物车商品
     * @param goodsId
     * @return
     */
    @RequestMapping(value = "deleteCartGoods",method = RequestMethod.POST)
    @ResponseBody
    public Result deleteCartGoods(Integer goodsId){
        return cartBiz.deleteGoods(goodsId);
    }

    /**
     * 更新商品数量
     * @param goodsId
     * @param goodsNumber
     * @return
     */
    @RequestMapping(value = "modifyGoodsNumber",method = RequestMethod.POST)
    @ResponseBody
    public Result modifyGoodsNumber(Integer goodsId, Integer goodsNumber){
        return cartBiz.modifyGoodsNumber(goodsId,goodsNumber);
    }

    /**
     * 获取购物车中商品数据
     * @return
     */
    @RequestMapping(value = "getCartGoodsList",method = RequestMethod.GET)
    @ResponseBody
    public Result<List<EpcCartVO>> getCartGoodsList(){
        return cartBiz.getCartGoodsList();
    }

    /**
     * 获取购物车中商品条目数
     * @return
     */
    @RequestMapping(value = "getCartGoodsNum", method = RequestMethod.GET)
    @ResponseBody
    public Result getCartGoodsNum(){
        return cartBiz.getCartGoodsAmount();
    }

    /**
     * 获取商品适配车型
     * @param goodsId
     * @return
     */
    @RequestMapping(value="getCarForGoods",method = RequestMethod.GET)
    @ResponseBody
    public Result getCarForGoods(Integer goodsId){
        return Result.wrapSuccessfulResult(cartBiz.getCarForGoods(goodsId));
    }

    /**
     * 获取待下单商品
     * @param idStr
     * @return
     */
//    @RequestMapping(value="getGoodsList",method = RequestMethod.GET)
//    @ResponseBody
//    public Result getGoodsList(String idStr){
//        return cartBiz.getGoodsListByIds(idStr);
//    }

    /**
     * 根据OE码, 获取商品适配车型
     * @param oeNum
     * @return
     */
    @RequestMapping(value="getCarForGoodsByOeNum",method = RequestMethod.GET)
    @ResponseBody
    public Result getCarForGoodsByOeNum(String oeNum){
        return Result.wrapSuccessfulResult(cartBiz.getCarForGoodsByOeNum(oeNum));
    }

    /**
     * 清除失效商品
     * @return
     */
    @RequestMapping("deleteUnAvailableGoods")
    @ResponseBody
    public Result deleteUnAvailableGoods(){
        return cartBiz.deleteUnAvailableGoods();
    }

    /**
     * 购物车结算，检验失效商品
     * @param paramList
     * @return
     */
    @RequestMapping("checkGoodsForSettlement")
    @ResponseBody
    public Result checkGoodsForSettlement(@RequestBody List<CheckGoodsParam> paramList){
        return cartBiz.checkGoodsForSettlement(paramList);
    }

    /**
     * 立即购买，检验失效商品
     * @param param
     * @return
     */
    @RequestMapping("checkGoodsForBuyNow")
    @ResponseBody
    public Result checkGoodsForBuyNow(CheckGoodsParam param){
        return cartBiz.checkGoodsForBuyNow(param);
    }

}
