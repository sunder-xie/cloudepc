package com.tqmall.data.epc.web.controller.verifyShop;

import com.tqmall.core.common.entity.Result;
import com.tqmall.data.epc.bean.bizBean.shop.ShopVerityBO;
import com.tqmall.data.epc.bean.bizBean.sys.UserBO;
import com.tqmall.data.epc.biz.sys.UserBiz;
import com.tqmall.data.epc.biz.verifyShop.VerifyShopBiz;
import com.tqmall.data.epc.common.bean.enums.ShopVerifyStatus;
import com.tqmall.data.epc.common.utils.CookieUtil;
import com.tqmall.data.epc.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by zxg on 16/10/14.
 * 09:52
 * no bug,以后改代码的哥们，祝你好运~！！
 */

@Controller
@RequestMapping("verifyShop")
public class VerifyShopController extends BaseController {

    @Autowired
    private UserBiz userBiz;
    @Autowired
    private VerifyShopBiz verifyShopBiz;

    //认证页面,如果有用户信息就放，第二步，有就第一步
    @RequestMapping(value = "verifyIndex")
    public ModelAndView verifyIndex() {
        ModelAndView modelAndView = new ModelAndView(webVersion + "/cloudepc/verifyShop/verifyIndex");

        Integer nowStep = 1;
        UserBO userBO = shiroBiz.getCurrentUser();
        if (userBO != null) {
            Integer verifyStatus = userBO.getShopBO().getVerifyStatus();
            modelAndView.addObject("verifyStatus", verifyStatus); //测试的时候注释掉
            //认证中，不进行后续处理
            if (verifyStatus.equals(ShopVerifyStatus.SUCCESS_STATUS.getCode()) || verifyStatus.equals(ShopVerifyStatus.ING_STATUS.getCode())) {
                //成功的 直接到页面上进行跳转
                return modelAndView;
            }


            Integer userId = userBO.getUid();
            modelAndView.addObject("phone", userBO.getMobile());



            //未认证的和认证失败的
            nowStep = 2;
            ShopVerityBO shopVerityBO = null;
            if (verifyStatus.equals(ShopVerifyStatus.INIT_STATUS.getCode())) {
                shopVerityBO = verifyShopBiz.getPreVerifyInfo(userId);
            } else if (verifyStatus.equals(ShopVerifyStatus.FAILED_STATUS.getCode())) {
                shopVerityBO = verifyShopBiz.getVerifyInfo(userBO.getMobile());
            }
//            else if (verifyStatus.equals(ShopVerifyStatus.ING_STATUS.getCode())) {
//                //认证中的数据 ，只能修改 img
//                nowStep = 3;
//                shopVerityBO = verifyShopBiz.getVerifyInfo(userBO.getMobile());
//            }
            //结果放到view中
            if (shopVerityBO != null) {
                modelAndView.addObject("shopVerityBO", shopVerityBO);
            }
        }
        modelAndView.addObject("nowStep", nowStep);
        return modelAndView;
    }

    @RequestMapping(value = "verifyPhone", method = RequestMethod.POST)
    @ResponseBody
    public Result<String> verifyPhone(String userName, String verifyCode) {
        UserBO user = shiroBiz.getCurrentUser();
        if (user != null) {
            userBiz.logout(response);
        }

        Result<UserBO> userBOResult = userBiz.login(userName, verifyCode);
        if (userBOResult.isSuccess()) {
            return Result.wrapSuccessfulResult("success");
        }

        return Result.wrapErrorResult(userBOResult.getCode(), userBOResult.getMessage());
    }

    @RequestMapping(value = "startVerify", method = RequestMethod.POST)
    @ResponseBody
    public Result<String> startVerify(ShopVerityBO shopVerityBO) {
        UserBO userBO = shiroBiz.getCurrentUser();
        if (userBO == null) {
            return Result.wrapErrorResult("001", "用户已经登出，请刷新重试");
        }
        Integer verifyStatus = userBO.getShopBO().getVerifyStatus();
        Integer userId = userBO.getUid();

        Boolean result = false;
        if (verifyStatus.equals(ShopVerifyStatus.INIT_STATUS.getCode()) || verifyStatus.equals(ShopVerifyStatus.FAILED_STATUS.getCode())) {
            //未认证或认证失败 申请认证
            shopVerityBO.setUid(userId);
            result = verifyShopBiz.register(shopVerityBO);
        } else if (verifyStatus.equals(ShopVerifyStatus.ING_STATUS.getCode())) {
            //正在认证中 更新认证图片
            result = verifyShopBiz.updateImage(shopVerityBO.getImgs(), userId);
        }

        if (result) {
            //重置认证状态
//            userBiz.resetVerifyStatus();
            return Result.wrapSuccessfulResult("success");
        }

        return Result.wrapErrorResult("002", "认证操作失败，请联系客服或稍后重试");
    }

}
