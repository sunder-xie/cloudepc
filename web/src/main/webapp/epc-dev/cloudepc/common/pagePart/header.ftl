<!-- 必须引入 IM 拦截框样式 -->
<link href="${monkDomain}/monk-release/resources/chat/chat-min.css?v=${.now?string('yyyy-MM-dd')}" rel="stylesheet"
      type="text/css"/>

<style type="text/css">
    .header-style {
        height: 35px;
        background: #f2f2f2;

    }

    .header-style b {
        margin: 0 10px;
    }

    .header-left {
        float: left;
        padding-top: 10px;

    }

    .header-right {
        float: right;
        padding-top: 10px;

    }

    .header-login-color {
        color: #EA6343 !important;
    }

    .header-logout {
        margin-left: 20px;
    }

    .cart-tip-color {
        color: #ff7800;
    }

    .header-content {
        margin: 0 auto;
    }
</style>

<div class="header-style">
    <div class="w1190 header-content">
        <div class="header-left">

        <#if CURRENT_USER==null>
            <a class="header-login header-login-color" href="/user/loginPage">您好，请登录</a>
            <a class="header-logout hidden" href="#">退出</a>
        <#else>
            <a class="header-login" href="#">欢迎您，${CURRENT_USER.hiddenMobile}</a>


            <#if CURRENT_USER.shopBO.verifyStatus == 0>
                <a href="#" class="not_verify" style="color: red">未认证</a>
            <#elseif CURRENT_USER.shopBO.verifyStatus == -1>
                <a href="#" class="not_verify" style="color: red" >认证失败</a>
            <#elseif CURRENT_USER.shopBO.verifyStatus == 1>
                <a href="#"  style="color: red" >认证中</a>
            <#elseif CURRENT_USER.shopBO.verifyStatus == 2>
                <a href="#" style="color: red">已认证</a>
            <#else >
                <a href="#" style="color: red">未知状态?</a>
            </#if>
            <a class="header-logout" href="#">退出</a>
        </#if>

            <div class="dropdown dropdown-div">
                <button class="btn btn-default btn-block dropdown-toggle" style="background:white;" type="button"
                        id="dropdownCity" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    <span class="city-select" id="citySelect">${CURRENT_CITY_NAME}</span>
                    <span class="caret city-select-down"></span>
                </button>
                <div id="dropdownCityMenu" class="dropdown-menu dropdown-city" aria-labelledby="dropdownCity">
                <#--
                    <p>
                        <span style="color:#333;cursor:pointer;" onclick="selectCity(this);">
                            <input type="hidden" value="0">
                            选择城市
                        </span>
                    </p>
                    -->
                </div>
            </div>

        </div>
        <div class="header-right">
        <#if GO_HOME != null>
            <a href="/home">汽配管家首页</a>
            <b>|</b>
        </#if>
            <#--
            <a href="javascript:CityUtil.checkAvidCall()">管家急呼</a>
            <b>|</b>
            -->
            <a href="javascript:CityUtil.checkCreateWish()">发布需求</a>
            <b>|</b>
            <a href="/home/query">查询配件</a>
            <b>|</b>
            <a href="/shopping/shoppingCart" target="_self">
                <i class="fa fa-shopping-cart shopping-cart-style"></i>
                购物车
                <i class="cart-tip-color" id="cartTip"></i>
            </a>
            <b>|</b>
            <a href="/wish/myWishPage">我的需求单</a>
            <b>|</b>
            <a href="/wish/myOfferPage">个人中心</a>
            <b>|</b>
            <a href="/help/introduction" target="_blank">帮助中心</a>
        </div>
    </div>
</div>

<#-- 查看原图 -->
<div id="originalImgModal" class="modal" tabindex="-1" data-width="90%">
    <div class="modal-body">
        <img id="globalOriginalImg">
    </div>
</div>

<script src="/static/js/jquery.min.js" type="text/javascript"></script>

<!-- build:js ../resources/header/header.js -->
<script src="/epc-dev/resources/common/epc/ajax.js" type="text/javascript"></script>
<script src="/epc-dev/resources/common/epc/city/city.js" type="text/javascript"></script>
<!-- endbuild -->

<script type="text/javascript">
    //登录成功标识
    var IsLogin = 0;

    //门店认证通过标识
    var IsVerifySuccessShop = 0;

    var CURRENT_SHOP_ID = 0;

    <#if CURRENT_USER != null>
    IsLogin = 1;
        <#if CURRENT_USER.shopBO != null && CURRENT_USER.shopBO.verifyStatus == 2>
        IsVerifySuccessShop = 1;
        CURRENT_SHOP_ID = ${CURRENT_USER.shopId};
        </#if>
    </#if>

    <#if CURRENT_CITY_ID_KEY != null>
    CurrentCityId = ${CURRENT_CITY_ID_KEY};
    </#if>

    //查询城市站
    CityUtil.getAllCity();

    //跳转到认证页面
    $(".not_verify").click(function(){
        window.location.href= '/verifyShop/verifyIndex';
    });
</script>