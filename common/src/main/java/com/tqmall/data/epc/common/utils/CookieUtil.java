package com.tqmall.data.epc.common.utils;

import com.tqmall.data.epc.common.bean.ConstantBean;
import org.apache.shiro.codec.Hex;
import org.apache.shiro.crypto.AesCipherService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Key;

/**
 * Created by huangzhangting on 16/7/15.
 */
public class CookieUtil {
    private static final String LOGIN_INFO_CK_NAME = "user_login_info_ck";
    private static final String LOGIN_INFO_KEY_CK_NAME = "user_login_info_key_ck";
    private static final String REGION_INFO_CK_NAME = "user_region_info_ck";

    public static void setUserLoginInfo(HttpServletResponse response, String userName){
        AesCipherService aesCipherService = new AesCipherService();
        //设置key长度
        aesCipherService.setKeySize(128);
        //生成key
        Key key = aesCipherService.generateNewKey();
        byte[] keys = key.getEncoded();
        //加密
        String text = aesCipherService.encrypt(userName.getBytes(), keys).toHex();

        Cookie cookie = new Cookie(LOGIN_INFO_CK_NAME, text);
        cookie.setMaxAge(3600*24*30);
        cookie.setPath("/");
        response.addCookie(cookie);

        String keyStr = EPCUtil.bytesToStr(keys);
        cookie = new Cookie(LOGIN_INFO_KEY_CK_NAME, keyStr);
        cookie.setMaxAge(3600*24*30);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    public static void delUserLoginInfo(HttpServletResponse response){
        Cookie cookie = new Cookie(LOGIN_INFO_CK_NAME, null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    public static String getUserLoginInfo(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        if(cookies!=null){
            String text = null;
            String key = null;
            for(Cookie cookie : cookies){
                if(LOGIN_INFO_CK_NAME.equals(cookie.getName())){
                    text = cookie.getValue();
                }
                if(LOGIN_INFO_KEY_CK_NAME.equals(cookie.getName())){
                    key = cookie.getValue();
                }
            }
            if(text!=null && key!=null){
                AesCipherService aesCipherService = new AesCipherService();
                byte[] keys = EPCUtil.strToBytes(key);
                //解密
                String userName = new String(aesCipherService.decrypt(Hex.decode(text), keys).getBytes());
                return userName;
            }
        }
        return null;
    }

    public static void setRegionInfo(HttpServletResponse response, Integer cityId){
        Cookie cookie = new Cookie(REGION_INFO_CK_NAME, cityId+"");
        cookie.setMaxAge(3600*24*365);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    public static Integer getRegionInfo(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        if(cookies!=null){
            for(Cookie cookie : cookies){
                if(REGION_INFO_CK_NAME.equals(cookie.getName())){
                    return Integer.valueOf(cookie.getValue());
                }
            }
        }
        return ConstantBean.DEFAULT_CITY_ID;
    }

}
