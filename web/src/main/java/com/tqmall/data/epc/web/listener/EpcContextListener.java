package com.tqmall.data.epc.web.listener;

import com.tqmall.data.epc.bean.entity.sys.EpcPageUriDO;
import com.tqmall.data.epc.biz.util.UserUtil;
import com.tqmall.data.epc.dao.mapper.sys.EpcPageUriDOMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.List;

/**
 * Created by huangzhangting on 16/11/16.
 */
@Slf4j
public class EpcContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        log.info("epc 启动了！O(∩_∩)O哈哈~~");

        ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(sce.getServletContext());
        Object object = context.getBean("epcPageUriDOMapper");
        if(object != null){
            EpcPageUriDOMapper uriDOMapper = (EpcPageUriDOMapper)object;
            List<EpcPageUriDO> uriDOList = uriDOMapper.selectNeedGuideUris();
            if(!uriDOList.isEmpty()){
                for(EpcPageUriDO uriDO : uriDOList){
                    UserUtil.NEED_GUIDE_URI_MAP.put(uriDO.getPageUri(), uriDO.getId());
                }
                log.info("需要引导的页面uri map:{}", UserUtil.NEED_GUIDE_URI_MAP);
            }
        }else{
            log.info("获取 EpcPageUriDOMapper 实例失败");
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        log.info("epc 死了！o(╯□╰)o~~");

    }
}
