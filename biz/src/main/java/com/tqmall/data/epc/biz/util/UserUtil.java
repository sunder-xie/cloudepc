package com.tqmall.data.epc.biz.util;

import com.tqmall.core.common.entity.Result;
import com.tqmall.data.epc.bean.bizBean.shop.ShopBO;
import com.tqmall.data.epc.bean.bizBean.sys.UserBO;
import com.tqmall.data.epc.common.bean.ConstantBean;
import com.tqmall.data.epc.common.utils.ResultUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by huangzhangting on 16/8/13.
 */
public class UserUtil {
    /**
     * 需要引导的页面uri
     * key：uri  例如：/avidCall
     * value：数据库主键id
     */
    public static final Map<String, Integer> NEED_GUIDE_URI_MAP = new HashMap<>();

    /**
     * 用户访问页面记录key
     */
    public static final String USER_PV_RECORD_KEY = "user_pv_record";


    /**
     * 校验门店认证状态
     * @param shop
     * @return
     */
    public static Result<String> checkShopVerifyStatus(ShopBO shop){
        if(shop==null){
            return ResultUtil.errorResult("", "抱歉，您尚未成为汽配管家会员，请先登录认证");
        }
        Integer verify = shop.getVerifyStatus();
        if(verify==null || verify== ConstantBean.SHOP_VERIFY_STATUS_INIT){
            return ResultUtil.errorResult("", "抱歉，您尚未成为汽配管家会员，请至左上角点击 未认证 按钮");
        }
        if(verify==ConstantBean.SHOP_VERIFY_STATUS_ING){
            return ResultUtil.errorResult("", "抱歉，您的会员申请正在审核中，暂时无法使用该功能");
        }
        if(verify==ConstantBean.SHOP_VERIFY_STATUS_FAILED){
            return ResultUtil.errorResult("", "抱歉，您的会员申请未通过审核，暂时无法使用该功能，请至左上角点击 认证失败 按钮");
        }

        return ResultUtil.successResult("");
    }

    public static boolean isVerifySuccess(UserBO userBO){
        ShopBO shop = userBO.getShopBO();
        Result<String> result = checkShopVerifyStatus(shop);
        return result.isSuccess();
    }
    public static String encryptOe(String oe){
        if(oe==null){
            return "";
        }
        oe = oe.trim();
        int len = oe.length();
        if(len==0){
            return "";
        }
        String str = "******";
        if(len<3){
            return oe + str;
        }
        if(len<9){
            return oe.substring(0, 2) + str;
        }

        return oe.substring(0, 2) + str + oe.substring(8);
    }

    /** code比较特殊，是给前端判断使用的，不要乱改 */
    public static String getTimeOutJsonStr(){
        return "{\"data\":null,\"success\":false,\"code\":\"99999999\",\"message\":\"请先登录\"}";
    }
    public static String getUnVerifySuccessJsonStr(String str){
        return "{\"data\":null,\"success\":false,\"code\":\"99999995\",\"message\":\""+str+"\"}";
    }

}
