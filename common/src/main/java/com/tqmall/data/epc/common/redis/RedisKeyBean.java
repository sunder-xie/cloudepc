package com.tqmall.data.epc.common.redis;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by zxg on 15/9/10.
 * redis key 的管理
 */

public interface RedisKeyBean {
    /*===========变量自动为 final static*/
    //redis变量key的系统前缀
    String SYSTEM_PREFIX = "CloudEpc_";

    String NULL_OBJECT = "None";

    /*=============失效时间=======================================================================*/
    /**
     * 缓存时效 1分钟
     */
    int RREDIS_EXP_MINUTE = 60;

    int RREDIS_EXP_FIVE_MINUTE = 300;

    /**
     * 缓存时效 10分钟
     */
    int RREDIS_EXP_MINUTES = 600;

    /**
     * 缓存时效 60分钟
     */
    int RREDIS_EXP_HOURS = 3600;

    /**
     * 缓存时效 1天
     */
    int RREDIS_EXP_DAY = 3600 * 24;

    /**
     * 缓存时效 1周
     */
    int RREDIS_EXP_WEEK = 3600 * 24 * 7;

    /**
     * 缓存时效 1月
     */
    int RREDIS_EXP_MONTH = 3600 * 24 * 30 * 7;

    /*=====================自定义的各种key=========================================================================*/

    String ALL_CAR_BRANDS = SYSTEM_PREFIX + "all_car_brands";

    //全部的车品牌车系
    String ALL_CAR_BRAND_SERIES = SYSTEM_PREFIX + "all_car_brand_series";

    /*
    * 车品牌查询页面，品牌数据
    * */
    String SEARCH_CAR_BRAND_KEY = SYSTEM_PREFIX + "search_car_brands";

    /*
    * 车品牌查询页面，车型筛选弹窗，车款数据
    * */
    String SEARCH_CARS_MODEL_ID_KEY = SYSTEM_PREFIX + "search_cars_model_id_%d";

    /*
    * oe码查询页面，车型筛选页面，车型数据
    * */
    String OE_CARS_GOODS_ID_KEY = SYSTEM_PREFIX + "oe_cars_goods_id_%d";

    /*
    * 当天vin码查询次数
    * */
    String VIN_SEARCH_COUNT_DAY_KEY = SYSTEM_PREFIX + "vin_search_count_day_%s";
    /*
    * 当天同个ip vin码查询次数
    * */
    String VIN_SEARCH_COUNT_DAY_IP_KEY = SYSTEM_PREFIX + "vin_search_count_day_%s_%s";

    //vin码查询结果
    String VIN_SEARCH_KEY = SYSTEM_PREFIX + "cars_by_vin_%s";

    //配件详情
    String CENTER_GOODS_DETAIL_KEY = SYSTEM_PREFIX + "goods_detail_gid_%d_cid_%d";

    //配件报价
    String GOODS_QUOTE_LIST_KEY = SYSTEM_PREFIX + "goods_quote_gid_%d_cid_%d";

    //根据goodsId查适配车型
    String CAR_MODEL_GID_KEY = SYSTEM_PREFIX + "car_model_gid_%d";
    //根据goodsId查适配车款
    String CAR_GID_KEY = SYSTEM_PREFIX + "car_gid_%d";

    //根据类目id，车款id查配件
    String CENTER_GOODS_CAR_CATE_KEY = SYSTEM_PREFIX + "goods_cid_%d_cate_id_%d";


    //城市站
    String ALL_CITY_KEY = SYSTEM_PREFIX + "all_city";
    String REGION_LIST_PID_KEY = SYSTEM_PREFIX + "region_list_pid_%d";
    String REGION_BY_ID_KEY = SYSTEM_PREFIX + "region_by_id_%d";


    //手机验证码
    String VERIFY_CODE_KEY = SYSTEM_PREFIX + "verify_code_%s";
    //请求验证码次数统计
    String REQUEST_VERIFY_CODE_COUNT_KEY = SYSTEM_PREFIX + "verify_code_count_%s";


    //=====防爬虫=========
    //每个ip 单位时间发起的请求数，失效时间：1min
    String ALL_REQUEST_IP_KEY = SYSTEM_PREFIX + "all_request_by_ip_%s";
    //每个ip的封锁次数，失效时间：1天
    String WAITING_IP_KEY = SYSTEM_PREFIX + "waiting_time_by_ip_%s";
    // 禁止访问的黑名单列表
    String BLACKE_IP_SET_KEY = SYSTEM_PREFIX + "black_ip_set";

    //发布需求单城市站限制
    String WISH_LIST_CITIES_KEY = SYSTEM_PREFIX + "wish_list_cities";
    String WISH_LIST_CITY_NAMES_KEY = SYSTEM_PREFIX + "wish_list_city_names";
    String WISH_LIST_CITY_KEY = SYSTEM_PREFIX + "wish_list_city_%d";
    //今日聊到需求单编号
    String WISH_SN_CACHE_DAY_KEY = SYSTEM_PREFIX + "wish_sn_%d_%d_%s";

    //订单编号序列key
    String ORDER_NO_SEQ_KEY = SYSTEM_PREFIX + "order_no_seq_%s";

    //配送方式
    String ALL_SHIPPING_CONFIG_KEY = SYSTEM_PREFIX + "all_shipping_config";



    /**
     * 不能刷缓存的key，主要用于刷redis缓存接口判断使用
     * 同时方便不熟悉的人查看
     */
    Set<String> UN_FRESH_KEY_SET = new HashSet<String>(){{
        add(ORDER_NO_SEQ_KEY); //订单编号序列
        add(WISH_SN_CACHE_DAY_KEY); //今日聊到需求单编号
        add(BLACKE_IP_SET_KEY); //禁止访问的黑名单列表

    }};

}
