package com.tqmall.data.epc.common.bean;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import java.math.BigDecimal;
import java.util.*;

/**
 * 常量bean
 * Created by huangzhangting on 16/2/23.
 */
public class ConstantBean {
    public static final String APPLICATION_NAME = "TQMALLPORTAL";

    //档口网站设备id，支付接口需要用到
    public static final String WEB_DEVICE_ID = "tqmall_web";

    /*=======通用=============*/
    //还未完成的通用状态
    public static final Integer NO_STATUS = 0;
    //已完成的通用状态
    public static final Integer YES_STATUS = 1;

    /*=======START vin============*/
    //单日vin码查询次数上线
    public static final int VIN_SEARCH_DAY_LIMIT = 500;
    //单个ip 单日vin码查询次数上线
    public static final int VIN_SEARCH_DAY_IP_LIMIT = 20;

    //vin码搜索限制次数开关
    public static final String VIN_SEARCH_SWITCH_ON = "on";

    /*=======end vin============*/


    /*=======START 验证码============*/
    /**
     * 验证码开关
     */
    public static final String VERIFY_ON = "on"; //开启验证码
    /**
     * 规定时间内，请求验证码次数限制
     */
    public static final int REQUEST_VERIFY_CODE_LIMIT = 5;

    /**
     * 验证码状态
     */
    public static final byte VERIFY_STATUS_INIT = 0;
    public static final byte VERIFY_STATUS_SUCCESS = 1;
    public static final byte VERIFY_STATUS_FAILED = 2;
    /*======= end 验证码============*/


    /**
     * 当前用户
     */
    public static final String USER_KEY = "CURRENT_USER";

    /**
     * 门店正常状态
     */
    public static final int SHOP_STATUS_OK = 0;

    //门店认证通过
    public static final int SHOP_VERIFY_STATUS_SUCCESS = 2;
    //门店认证失败
    public static final int SHOP_VERIFY_STATUS_FAILED = -1;
    //门店未认证
    public static final int SHOP_VERIFY_STATUS_INIT = 0;
    //门店认证中
    public static final int SHOP_VERIFY_STATUS_ING = 1;


    /*=======START 防爬虫============*/
    //每分钟单位ip请求的上限次数，超过此上限，则封锁
    public static final Integer REQUEST_MINUTE_LIMIT = 10;
    //封锁后，再叠加此限制数，则ip的封锁数＋＋
    public static final Integer REQUEST_MINUTE_ADD_LIMIT = 1;
    //暂时封锁的ip次数上限，超过此上限，则永久黑名单
    public static final Integer IP_WAITING_NUM_LIMIT = 3;
    //每个封锁数要等待的单位时间:10 min
    public static final Integer WAITING_EVE_TIME = 10*60;
    /*=======END 防爬虫============*/


    /**
     *  用户上一次请求，用于登录成功后，跳转到指定页面
     * */
    public static final String LAST_URI = "LAST_URI";

    //默认城市站
    public static final int DEFAULT_CITY_ID = 383;
    public static final String CURRENT_CITY_ID_KEY = "CURRENT_CITY_ID";
    public static final String CURRENT_CITY_NAME_KEY = "CURRENT_CITY_NAME";


    //需求单列表条数
    public static final int WISH_PAGE_SIZE = 5;
    //报价单列表条数
    public static final int OFFER_PAGE_SIZE = 10;
    //是否能发布需求单标识
    public static final String CAN_CREATE_WISH = "CAN_CREATE_WISH";
    //门店未认证成功
    public static final String UN_AUTH_SHOP_KEY = "un_auth_success";

    //校验码key
    public static final String CHECK_CODE_KEY = "CHECK_CODE_KEY";

    //增值税，税率
    public static final BigDecimal ADDED_TAX_RATE = BigDecimal.valueOf(17);

    //云修门店标签
    public static final List<String> YUN_XIU_TAG_KEYS = new ArrayList<String>(){{
        add("FRANCHISEE_TYPE_A");
        add("FRANCHISEE_TYPE_B");
        add("FRANCHISEE_TYPE_C");
        add("SYC_FRANCHISEE_TYPE_A");
        add("SYC_FRANCHISEE_TYPE_B");
        add("SYC_FRANCHISEE_TYPE_C");
    }};

}
