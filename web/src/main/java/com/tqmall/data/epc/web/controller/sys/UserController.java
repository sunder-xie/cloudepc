package com.tqmall.data.epc.web.controller.sys;

import com.tqmall.core.common.entity.Result;
import com.tqmall.data.epc.bean.bizBean.shop.AddressBO;
import com.tqmall.data.epc.bean.bizBean.sys.UserBO;
import com.tqmall.data.epc.biz.base.SmsBiz;
import com.tqmall.data.epc.biz.lop.WishListCityBiz;
import com.tqmall.data.epc.biz.sys.UserBiz;
import com.tqmall.data.epc.biz.util.UserUtil;
import com.tqmall.data.epc.common.bean.ConstantBean;
import com.tqmall.data.epc.common.bean.EpcError;
import com.tqmall.data.epc.common.utils.CookieUtil;
import com.tqmall.data.epc.common.utils.ResultUtil;
import com.tqmall.data.epc.web.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by huangzhangting on 16/6/21.
 */

@Controller
@RequestMapping("user")
@Slf4j
public class UserController extends BaseController {
    @Value("${verify.switch}")
    private String verifySwitch;
    @Autowired
    private SmsBiz smsBiz;
    @Autowired
    private UserBiz userBiz;
    @Autowired
    private WishListCityBiz wishListCityBiz;


    /**
     * TODO 门店认证页面
     *
     * @return
     */
    @RequestMapping("shopAuthPage")
    public String shopAuthPage() {
        return webVersion + "/cloudepc/login/test";
    }

    /**
     * 登陆页面
     *
     * @return
     */
    @RequestMapping("loginPage")
    public ModelAndView loginPage() {
        //登陆过，未退出
        UserBO user = shiroBiz.getCurrentUser();
        if (user != null) {
            return new ModelAndView(webVersion + "/cloudepc/home/index");
        }

        ModelAndView view = new ModelAndView(webVersion + "/cloudepc/login/loginNew");
        setBreadcrumb("LOGIN_PAGE");
        return view;
    }

    @RequestMapping("login")
    public Object login(String userName, String verifyCode) {
        ModelAndView view = new ModelAndView(webVersion + "/cloudepc/login/loginNew");
        Result<UserBO> result = doLogin(userName, verifyCode);
        if (result.isSuccess()) {
            Object obj = shiroBiz.getSessionValue(ConstantBean.LAST_URI);
            userBiz.resetVerifyStatus();

            if (obj != null) {
                String uri = (String) obj;
                return "redirect:" + uri;
            }

            view.setViewName(webVersion + "/cloudepc/home/index");
        }

        view.addObject("userName", userName);
        view.addObject("verifyCode", verifyCode);
        view.addObject("message", result.getMessage());

        return view;
    }

    /**
     * 异步登陆
     *
     * @param userName
     * @param verifyCode
     * @return
     */
    @RequestMapping(value = "doLogin", method = RequestMethod.POST)
    @ResponseBody
    public Result<UserBO> doLogin(String userName, String verifyCode) {
        //登陆过，未退出
        UserBO user = shiroBiz.getCurrentUser();
        if (user != null) {
            return ResultUtil.successResult(user);
        }
        Result<UserBO> result = userBiz.login(userName, verifyCode);
        if (result.isSuccess()) {
            //往cookie写登录信息
            CookieUtil.setUserLoginInfo(response, userName);
        }

        return result;
    }

    @RequestMapping("logout")
    public String logout() {
        userBiz.logout(response);

        //必须重定向，否则会报异常
        return "redirect:/user/loginPage";
    }

    /**
     * 获取验证码
     *
     * @param mobile
     * @return
     */
    @RequestMapping(value = "sendVerify", method = RequestMethod.GET)
    @ResponseBody
    public Result sendVerify(String mobile, String checkCode) {
        if (!ConstantBean.VERIFY_ON.equals(verifySwitch)) {
            return ResultUtil.errorResult("", "不需要验证码，随便填写即可登录");
        }

        Object obj = shiroBiz.getSessionValue(ConstantBean.CHECK_CODE_KEY);
        if (obj == null || !obj.toString().equalsIgnoreCase(checkCode)) {
            return ResultUtil.errorResult("", "校验码错误或失效");
        }

        com.tqmall.core.common.entity.Result result = smsBiz.sendVerify(mobile);
        if (result.isSuccess()) {
            return ResultUtil.successResult(result.getData(), "验证码已发送，请查看您的手机");
        }
        return ResultUtil.errorResult("", "非常抱歉，短信发送失败");
    }

    /**
     * 选择城市站
     *
     * @param cityId
     * @return
     */
    @RequestMapping(value = "selectCity", method = RequestMethod.POST)
    @ResponseBody
    public Result selectCity(Integer cityId) {
        if (cityId == null || cityId < 1) {
            return ResultUtil.errorResult(EpcError.ARG_ERROR);
        }
        CookieUtil.setRegionInfo(response, cityId);
        shiroBiz.setUserCity(cityId);
        return ResultUtil.successResult(1);
    }

    /**
     * 获取当前用户默认地址
     *
     * @return
     */
    @RequestMapping("getDefaultAddress")
    @ResponseBody
    public Result<AddressBO> getDefaultAddress() {
        return userBiz.getDefaultAddress();
    }

    @RequestMapping("getUserAddressList")
    @ResponseBody
    public Result<List<AddressBO>> getUserAddressList() {
        return userBiz.getUserAddressList();
    }

    /**
     * 检查门店认证状态
     *
     * @return
     */
    @RequestMapping("checkShopVerifyStatus")
    @ResponseBody
    public Result<String> checkShopVerifyStatus() {
        return userBiz.checkShopVerifyStatus();
    }

    /**
     * 校验发布需求单城市站
     * @param cityId
     * @return
     */
//    @RequestMapping("checkCityForWish")
//    @ResponseBody
//    public Result checkCityForWish(Integer cityId){
//        Result result = wishListCityBiz.checkCity(cityId);
//        if(result.isSuccess()){
//            shiroBiz.putIntoSession(ConstantBean.CAN_CREATE_WISH, 1);
//            return result;
//        }
//
//        shiroBiz.putIntoSession(ConstantBean.CAN_CREATE_WISH, null);
//        return ResultUtil.errorResult(EpcError.UN_OPEN_CITY_ERROR, wishListCityBiz.getCityNames());
//    }

    /**
     * 校验发布需求单城市站
     *
     * 注意：不能用shiro过滤器处理，目前有bug
     * 配置过滤器后，在未登录的状态下ajax请求，请求会 Provisional headers are shown
     * 暂时没有找到为什么~~~~
     * 临时使用下面的处理方式，待跟进...
     *
     * @return
     */
    @RequestMapping("checkCityForWish")
    @ResponseBody
    public Result checkCityForWish() {
        UserBO user = shiroBiz.getCurrentUser();
        if(user==null){
            return ResultUtil.errorResult("01", "请先登录"); //code是给前端判断用的，切记不要乱改
        }
        Result<String> checkResult = UserUtil.checkShopVerifyStatus(user.getShopBO());
        if(!checkResult.isSuccess()){
            return ResultUtil.errorResult("02", checkResult.getMessage()); //code是给前端判断用的，切记不要乱改
        }

        Integer cityId = shiroBiz.getUserCity();
        Result result = wishListCityBiz.checkCity(cityId);
        if (result.isSuccess()) {
            shiroBiz.putIntoSession(ConstantBean.CAN_CREATE_WISH, 1);
            return result;
        }

        shiroBiz.putIntoSession(ConstantBean.CAN_CREATE_WISH, null);
        return ResultUtil.errorResult(EpcError.UN_OPEN_CITY_ERROR);
    }

}
