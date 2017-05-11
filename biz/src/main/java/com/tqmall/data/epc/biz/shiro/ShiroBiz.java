package com.tqmall.data.epc.biz.shiro;

import com.tqmall.data.epc.bean.bizBean.sys.UserBO;
import org.apache.shiro.session.Session;

/**
 * Created by huangzhangting on 16/7/13.
 */
public interface ShiroBiz {
    Session getSession();

    String getSessionId();
    
    void putIntoSession(String key, Object value);

    Object removeFromSession(String key);

    Object getSessionValue(String key);

    void setCurrentUser(UserBO user);

    UserBO getCurrentUser();

    void removeCurrentUser();

    Integer getUserId();

    void setUserCity(Integer cityId);

    Integer getUserCity();

    String getUserCityName();

    /**
     * 往session写pageUriId
     * @param pageUriId
     */
    boolean setPageGuideRecord(Integer pageUriId);

    /**
     * 根据uri从session中获取pageUriId
     * @param uri
     * @return
     */
    Integer getPageUriId(String uri);
}
