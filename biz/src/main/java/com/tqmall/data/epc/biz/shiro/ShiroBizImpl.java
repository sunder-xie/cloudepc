package com.tqmall.data.epc.biz.shiro;

import com.tqmall.core.common.entity.Result;
import com.tqmall.data.epc.bean.bizBean.base.RegionBO;
import com.tqmall.data.epc.bean.bizBean.sys.UserBO;
import com.tqmall.data.epc.biz.lop.WishListCityBiz;
import com.tqmall.data.epc.biz.util.UserUtil;
import com.tqmall.data.epc.common.bean.ConstantBean;
import com.tqmall.data.epc.exterior.dubbo.base.RegionServiceExt;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by huangzhangting on 16/7/13.
 */
@Slf4j
@Service
public class ShiroBizImpl implements ShiroBiz {
    @Autowired
    private RegionServiceExt regionServiceExt;
    @Autowired
    private WishListCityBiz wishListCityBiz;


    @Override
    public Session getSession() {
        Subject subject = SecurityUtils.getSubject();
        if(subject!=null){
            return subject.getSession();
        }
        return null;
    }

    @Override
    public String getSessionId() {
        Subject subject = SecurityUtils.getSubject();
        if(subject!=null){
            return subject.getSession().getId().toString();
        }
        return null;
    }

    @Override
    public void putIntoSession(String key, Object value) {
        Subject subject = SecurityUtils.getSubject();
        if(subject!=null){
            Session session = subject.getSession();
            session.setAttribute(key, value);
        }
    }

    @Override
    public Object removeFromSession(String key) {
        try {
            Subject subject = SecurityUtils.getSubject();
            if(subject!=null) {
                Session session = subject.getSession();
                return session.removeAttribute(key);
            }
        }catch (Exception e){
            log.error("removeFromSession error, key:"+key, e);
        }

        return null;
    }

    @Override
    public Object getSessionValue(String key) {
        try {
            Subject subject = SecurityUtils.getSubject();
            if(subject!=null) {
                Session session = subject.getSession();
                return session.getAttribute(key);
            }
        }catch (Exception e){
            log.error("getSessionValue error, key:"+key, e);
        }

        return null;
    }

    @Override
    public void setCurrentUser(UserBO user) {
        putIntoSession(ConstantBean.USER_KEY, user);
    }

    @Override
    public UserBO getCurrentUser() {
        return (UserBO)getSessionValue(ConstantBean.USER_KEY);
    }

    @Override
    public void removeCurrentUser() {
        removeFromSession(ConstantBean.USER_KEY);
    }

    @Override
    public Integer getUserId() {
        return getCurrentUser().getUid();
    }

    @Override
    public void setUserCity(Integer cityId) {
        RegionBO regionBO = regionServiceExt.getRegionById(cityId);
        if(regionBO==null){
            return;
        }

        Result result = wishListCityBiz.checkCity(cityId);
        if(result.isSuccess()) {
            putIntoSession(ConstantBean.CAN_CREATE_WISH, 1);
        }else{
            putIntoSession(ConstantBean.CAN_CREATE_WISH, null);
        }

        putIntoSession(ConstantBean.CURRENT_CITY_ID_KEY, cityId);
        putIntoSession(ConstantBean.CURRENT_CITY_NAME_KEY, regionBO.getRegionName());
    }

    @Override
    public Integer getUserCity() {
        Object obj = getSessionValue(ConstantBean.CURRENT_CITY_ID_KEY);
        if(obj==null){
            return ConstantBean.DEFAULT_CITY_ID;
        }
        return (Integer)obj;
    }

    @Override
    public String getUserCityName() {
        Object obj = getSessionValue(ConstantBean.CURRENT_CITY_NAME_KEY);
        if(obj==null){
            return "session中无城市站名称";
        }
        return (String)obj;
    }

    @Override
    public boolean setPageGuideRecord(Integer pageUriId) {
        Object object = getSessionValue(UserUtil.USER_PV_RECORD_KEY);
        Set<Integer> pageUriIds;
        if(object==null){
            pageUriIds = new HashSet<>();
            putIntoSession(UserUtil.USER_PV_RECORD_KEY, pageUriIds);
        }else{
            pageUriIds = (Set)object;
        }
        return pageUriIds.add(pageUriId);
    }

    @Override
    public Integer getPageUriId(String uri) {
        /* 判断页面是否需要引导 */
        Integer id = UserUtil.NEED_GUIDE_URI_MAP.get(uri);
        if(id==null){
            return null;
        }

        /* 判断该用户是否已经访问过了 */
        Object object = getSessionValue(UserUtil.USER_PV_RECORD_KEY);
        if(object==null){
            return id;
        }

        Set<Integer> pageUriIds = (Set)object;
        if(pageUriIds.contains(id)){
            return null;
        }

        return id;
    }
}
