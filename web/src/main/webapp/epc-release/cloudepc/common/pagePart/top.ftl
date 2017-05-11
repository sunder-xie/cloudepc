<style type="text/css">

    .top-first {
        height: 80px;
        /*border: 1px solid #d6e5ec;*/
        border-bottom: 1px solid #d6e5ec;
        /*border-top: 1px solid #d6e5ec;*/
    }

    .top-second {
        height: 30px;
        background: #f5f9fc;
        padding-top: 5px;
        padding-left: 10px;
    }

    .top-logo {
        width: 70px;
        height: 100%;
        border-right: 1px solid #d6e5ec;
        float: left;
    }

    .top-logo-image {
        width: 36px;
        height: 36px;
        margin-top: 22px;
    }

    .top-menu {
        height: 100%;
        float: left;
        padding: 29px 20px;
        font-size: 14px;
    }
    .top-menu a{
        margin: 0 10px;
    }

    .top-search {
        float: left;
        padding-top: 10px;
    }

    .search-type {
        -webkit-appearance: none;
        float: left;
        height: 36px;
        width: 75px;
        padding: 10px;
        margin-top: 17px;
        border: 1px solid #d6e5ec;
        border-right: none;
        border-radius: 5px 0 0 5px;
        color: #869cb1;
        font-size: 12px;
        background: white url('/img/epc-type-header/xiala2.png') no-repeat right center content-box;
    }

    .search-input {
        float: left;
        width: 300px;
        height: 37px;
        padding: 0 5px;
        /*padding-left: 16px;*/
        /*padding-right: 16px;*/
        /*margin-top: 17px;*/
        /*border: 1px solid #d6e5ec;*/
        border: 2px solid #0BC6E4;
        border-right: none;
    }

    .search-img {
        float: left;
        /*width: 36px;*/
        /*height: 36px;*/
        /*margin-top: 17px;*/
        /*border-top-right-radius: 5px;*/
        /*border-bottom-right-radius: 5px;*/
        cursor: pointer;
        padding: 8px 20px;
        /*border: 1px solid #d6e5ec;*/
        border: 2px solid #0BC6E4;
        border-left: none;
        background-color: #0BC6E4;
        color: white;
    }

    .search-img-bottom {
        margin-top: 10px;
        width: 16px;
        height: 16px;
    }

    .top-second > div {
        float: left;
        text-align: left;
    }

    /* hzt 增加 */
    /* color: #ff4200; */
    .choose-type{

    }
    .choose-type li{
        padding: 3px 8px;
    }
    .choose-type li:hover{
        cursor: pointer;
        color: #0BC6E4;
    }
    .choose-type .choose-style{
        background-color: #0BC6E4;
        color: white !important;
    }


    /* 购物车相关样式 */
    .shopping-cart-content{
        height: 37px;
        width: 100px;
        margin-top: 45px;
        margin-right: 1px;
        float: right;
    }
    .top-shopping-cart{
        border:1px solid #0BC6E4;
        border-radius: 4px;
        height: 25px;
        width: 100px;
        float: right;
    }
    .shopping-cart-text{
        margin-top: 4px;
        cursor: pointer;
    }
    .shopping-cart-tip{
        width:15px;
        height:15px;
        background-color: #ff7800;
        border-radius: 25px;
        position: absolute;
        top:73px;
        right:17px;
    }
    .shopping-cart-tip-num{
        height:17px;
        line-height:17px;
        display:block;
        color:#FFF;
        text-align:center
    }
    .shopping-cart-model{
        border: 2px solid #DBDEE1;
        border-top: 2px solid #ff7800;
        background-color: white;
        position: absolute;
        display: none;
        top: 80px;
        right:5px;
        height: 281px;
        width: 280px;
        z-index: 100;
    }
    .triangle-up {
        position: absolute;
        display: none;
        right: 76px;
        top:  73px;
        width: 0;
        height: 0;
        border-left: 6px solid transparent;
        border-right: 6px solid transparent;
        border-bottom: 7px solid #ff7800;
    }
    .cart-content-height1 td:nth-child(1){width: 50%;}
    .cart-content-height1 td:nth-child(2){width: 30%;}
    .cart-content-height1 td:nth-child(3){width: 15%;}

    .cart-content-height2 td:nth-child(1){width: 42%;}
    .cart-content-height2 td:nth-child(2){width: 58%;}

    .cart-content-height1{
        height:220px;
        overflow: auto;
        overflow-x: hidden;
        padding:0 10px;
    }
    .cart-content-height2{
        height:55px;
    }
    .cart-content-table{
        width:130px;
        margin-top: 3px;
        float: right;
    }
    .cart-table{
        width:100%;
    }

    .cart-table td{
        text-align: left;
        padding-top: 12px;
        overflow:hidden;
        text-overflow:ellipsis;
        white-space:nowrap;
        display: inline-block !important;
    }
    .view-cart-btn {
        border: none;
        color: #ffffff;
        width: 70px;
        height:30px;
        background-color: #c9c9c9;
        font-size: 13px;
        border-radius: 1px;
        float: left;
        margin:10px 15px;
    }
    .model-close-a{
        position: static!important;
        color:#FFF;
        background-color:#c9c9c9;
        margin-left: 12px;
        margin-top: -6px
    }
    .cart-table a:hover{
        background-color: #ff7800;
    }
    .del-goods{
        width: 17px;
        height: 17px;
        color: #fff !important;
        border-radius: 22px !important;
        margin-top: 1px;
        float: right;
        line-height: 1;
        font-size: 12px;
        background: #d7dde3 url(/img/epc-type-header/del-goods.png) no-repeat 50% 50%;
    }
</style>

<#include "/epc-release/cloudepc/common/pagePart/header.ftl"/>
<div >

    <div class="top-first">
        <div class="top-logo">
            <img src="/img/logo.png" class="top-logo-image">
        </div>

        <div class="top-navigation">
            <div id="head-li" class="top-menu">
                <a href="/home">首页</a>
                <a href="/autoParts/car/carModel">车型</a>
            </div>
            <div class="top-search">

                <div class="choose-type">
                    <ul class="ul-style">
                        <li id="keywordButton" <#if searchType==null || searchType=='keyword'>class="choose-style"</#if>
                            data-type="0" onclick="changeSearchType(0, this)">
                            <span class="choose-type-name">关键词</span>
                        </li>
                        <li id="oeButton" <#if searchType=='oe'>class="choose-style"</#if>
                            data-type="1" onclick="changeSearchType(1, this)">
                            <span class="choose-type-name">OE码</span>
                        </li>
                        <li id="vinButton" <#if searchType=='vin'>class="choose-style"</#if>
                            data-type="2" onclick="changeSearchType(2, this)">
                            <span class="choose-type-name">VIN码</span>
                        </li>
                    </ul>
                </div>

                <input type="text" id="car-type-search-input" class="search-input" placeholder="" value="">

                <div class="help-text hidden">
                    <span class="danger" id="text_size">0</span>
                    /17
                </div>

                <div id="car-type-head-searchBtn" class="search-img" onclick="carSearch()">
                    <#--<img id="top-searchBtn" class="search-img-bottom" src="/img/home/sousuo.png">-->
                    搜索
                </div>
            </div>
        </div>
        <#--onmousemove="showCartInfo()" onmouseout="hideCartInfo()"-->
        <#--该隐藏部分为top右侧购物车-->
        <#--<div class="shopping-cart-content" onclick="shoppingCartInfo()" >-->
            <#--<div class="top-shopping-cart">-->
                <#--<div class="shopping-cart-icon">-->
                    <#--<i class="fa fa-shopping-cart shopping-cart-style"></i>-->
                <#--</div>-->
                <#--<div class="float-left shopping-cart-text">购物车&nbsp;&nbsp;&nbsp;></div>-->
                <#--<div class="shopping-cart-tip">-->
                    <#--<span id="shoppingCartTipNum" class="shopping-cart-tip-num"></span>-->
                <#--</div>-->
            <#--</div>-->
            <#--<div id="cartContentModel1" class="triangle-up"></div>-->
            <#--<div id="cartContentModel2" class="shopping-cart-model">-->
            <#--</div>-->
        <#--</div>-->
    </div>

    <div id="crumbs" class="top-second <#if HIDE_BREADCRUMB!=null> hidden </#if>">
        <div>
            <span>当前位置：</span>
        </div>
        <div id="crumbs-content">
            <a href="/home">首页</a>

            <#if BREADCRUMB=='AVID_CALL'>

                <i class="fa fa-arrow-right">&nbsp;</i>
                <a href="#">管家急呼</a>

            <#elseif BREADCRUMB=='CAR_MODEL'>

                <i class="fa fa-arrow-right">&nbsp;</i>
                <a href="#">车型</a>

            <#elseif BREADCRUMB=='CAR_FILTER'>

                <i class="fa fa-arrow-right">&nbsp;</i>
                <a href="/autoParts/car/carModel">车型</a>
                <i class="fa fa-arrow-right">&nbsp;</i>
                <a href="#">车型过滤</a>

            <#-- 类目筛选页面 -->
            <#elseif BREADCRUMB=='CATEGORY_FILTER'>

                <i class="fa fa-arrow-right">&nbsp;</i>
                <a href="/autoParts/car/carModel">车型</a>
                <#--<i class="fa fa-arrow-right">&nbsp;</i>-->
                <#--<a href="/autoParts/car/carFilter?seriesId=${SERIES_ID}">车型过滤</a>-->
                <i class="fa fa-arrow-right">&nbsp;</i>
                <a href="#">类目</a>

            <#-- 类目筛选页面，跳转到配件详情页 -->
            <#elseif BREADCRUMB=='CATEGORY_DETAIL'>

                <i class="fa fa-arrow-right">&nbsp;</i>
                <a href="/autoParts/car/carModel">车型</a>
                <#--<i class="fa fa-arrow-right">&nbsp;</i>-->
                <#--<a href="/autoParts/car/carFilter?seriesId=${SERIES_ID}">车型过滤</a>-->
                <i class="fa fa-arrow-right">&nbsp;</i>
                <a href="/autoParts/category/category?carId=${CAR_ID}&vinNumber=${VIN_NO}">类目</a>
                <i class="fa fa-arrow-right">&nbsp;</i>
                <a href="#">配件详情</a>

            <#elseif BREADCRUMB=='GOODS'>

                <i class="fa fa-arrow-right">&nbsp;</i>
                <a href="#">配件</a>

            <#-- 配件列表页面，跳转到配件详情页 -->
            <#elseif BREADCRUMB=='GOODS_DETAIL'>

                <i class="fa fa-arrow-right">&nbsp;</i>
                <#if searchType=='oe'>
                    <a href="/autoParts/goods/oe?oem=${SEARCH_VALUE}">配件</a>
                <#else>
                    <a href="/autoParts/goods/keyword?q=${SEARCH_VALUE}">配件</a>
                </#if>
                <i class="fa fa-arrow-right">&nbsp;</i>
                <a href="#">配件详情</a>

            <#-- 类目筛选页面，跳转到图片相关配件页面 -->
            <#elseif BREADCRUMB=='CATE_PIC_GOODS'>

                <i class="fa fa-arrow-right">&nbsp;</i>
                <a href="/autoParts/car/carModel">车型</a>
                <#--<i class="fa fa-arrow-right">&nbsp;</i>-->
                <#--<a href="/autoParts/car/carFilter?seriesId=${SERIES_ID}">车型过滤</a>-->
                <i class="fa fa-arrow-right">&nbsp;</i>
                <a href="/autoParts/category/category?carId=${carId}">类目</a>
                <i class="fa fa-arrow-right">&nbsp;</i>
                <a href="#">相关配件</a>

            <#-- 登录页面 -->
            <#elseif BREADCRUMB=='LOGIN_PAGE'>

                <i class="fa fa-arrow-right">&nbsp;</i>
                <a href="#">登录</a>

            <#else >

            </#if>

        </div>
    </div>

</div>