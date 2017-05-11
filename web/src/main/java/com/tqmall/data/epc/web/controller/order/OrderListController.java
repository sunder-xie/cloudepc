package com.tqmall.data.epc.web.controller.order;

import com.tqmall.core.common.entity.PagingResult;
import com.tqmall.core.common.entity.Result;
import com.tqmall.data.epc.bean.bizBean.order.OrderDetailBO;
import com.tqmall.data.epc.bean.bizBean.order.OrderListShowBO;
import com.tqmall.data.epc.bean.param.order.OrderInfoParam;
import com.tqmall.data.epc.bean.param.order.OrderListPageParam;
import com.tqmall.data.epc.biz.order.CreateOrderBiz;
import com.tqmall.data.epc.biz.order.OrderBiz;
import com.tqmall.data.epc.web.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by zxg on 16/8/29.
 * 09:16
 * no bug,以后改代码的哥们，祝你好运~！！
 */
@Slf4j
@Controller
@RequestMapping("buy/order")
public class OrderListController extends BaseController{

    @Autowired
    private OrderBiz orderBiz;
    @Autowired
    private CreateOrderBiz createOrderBiz;


    @RequestMapping("")
    public ModelAndView defaultPage() {
        return myOrderPage();
    }

    /**
     * 我的报价单
     *
     * @return
     */
    @RequestMapping("myOrderPage")
    public ModelAndView myOrderPage() {
        return new ModelAndView(webVersion + "/cloudepc/part/showOrder");
    }

    /**
     * 订单详情
     * @param orderSn
     * @return
     */
    @RequestMapping("orderDetail")
    public ModelAndView orderDetail(String orderSn) {
        ModelAndView view = new ModelAndView(webVersion + "/cloudepc/part/orderDetail");

        Integer shopId = shiroBiz.getCurrentUser().getShopId();
        Result<OrderDetailBO> result = orderBiz.getOrderDetailByShop(orderSn, shopId);
        if (result.isSuccess()) {
            view.addObject("orderInfo", result.getData());
        }

        return view;
    }


    @RequestMapping("list")
    @ResponseBody
    public PagingResult<OrderListShowBO> orderList(OrderListPageParam searchParam) {

        Integer shopId = shiroBiz.getCurrentUser().getShopId();
        searchParam.setShopId(shopId);

        return orderBiz.orderPaged(searchParam);
    }

    @RequestMapping(value = "cancelOrder", method = RequestMethod.POST)
    @ResponseBody
    public Result cancelOrder(String orderSn, String cancelReason) {
        return orderBiz.cancelOrder(orderSn, cancelReason);
    }

    @RequestMapping(value = "confirmReceive", method = RequestMethod.POST)
    @ResponseBody
    public Result confirmReceive(String orderSn) {
        return orderBiz.confirmReceive(orderSn);
    }

    /**
     * 提交产生订单
     * @param param
     * @return
     */
    @RequestMapping(value="submitOrder",method = RequestMethod.POST)
    @ResponseBody
    public Result submitOrder(@RequestBody OrderInfoParam param){
        return createOrderBiz.submitOrder(param);
    }
}
