<#escape x as x?html>
<!DOCTYPE html>

<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en" xmlns="http://www.w3.org/1999/html">
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
    <meta charset="UTF-8">
    <title>汽配管家，把你的全车件需求告诉我！guanjia.tqmall.com</title>
    <meta name="keywords" content=“汽配管家,汽配商城,汽配,汽车配件,全车件,车型件,事故件,外观件,汽车配件价格,汽车配件批发,定损,配件">
    <meta name="description" content="汽配管家是一个为第三方汽配供应商以及汽修门店提供的汽车配件交易平台。采用的是汽修门店发布需求单，供应商回复报价的形式进行交易磋商，最终在汽配管家的保障下完成交易。">

    <#include "/epc-release/cloudepc/common/resource/css.ftl"/>

    <style type="text/css">

        h3{
            font-size: 18px;
            color: #333;
            text-align: left;
            margin: auto;
        }
        strong{
            color: #d10000;
        }
        .content {
            background: url('${ossImage}/img/epc/login_bg_not.jpg') no-repeat;
            background-size: 100% 100%;
            height: 580px;
        }

        .logo {
            background: url("/img/logo/logo_01.png") no-repeat;
            width: 238px;
            height: 82px;
            float: left;
            margin-top: 170px;
            cursor: pointer;
        }

        .logo-img {
            margin-top: 220px;
            margin-left: 350px;
        }

        .login-form {
            position: relative;
            float: right;
            width: 360px;
            /* padding-bottom: 100px; */
            /* margin-left: 800px; */
            padding: 25px 35px 45px 35px;
            background: #FFFFFF;
        }

        .login-form-phone {
            width: 290px;
            height: 40px;
            overflow: hidden;
        }

        .phone-img {
            margin-top: 8px;
        }

        .clear-img {
            margin-top: 10px;
            cursor: pointer;
        }

        .in-phone {
            width: 207px;
            height: 100%;
            padding-left: 10px;
            border-bottom: 1px solid #ededed;
            border-left: 1px solid #ededed;
            border-top: 1px solid #ededed;
            border-right: 0;
            float: left;
        }

        .left_div {
            width: 40px;
            height: 100%;
            float: left;
            border: 1px solid #ededed;
        }

        .login-form-code {
            width: 290px;
            height: 40px;
            margin-top: 25px;
            overflow: hidden;
        }


        .code-img {
            margin-top: 10px;
        }

        .in-code {
            width: 127px;
            height: 100%;
            padding-left: 10px;
            border: 1px solid #ededed;
            float: left;
        }

        .get-code {
            width: 110px!important;
            height: 40px;
            font-size: 14px;
            color: #00ABDC;
            line-height: 40px;
            margin-left: 10px!important;
            border: none;
            border-radius: 5px;
            background: #EBF7FE;
            float: left;
            cursor: pointer;
        }

        .login-form-btn{
            width: 290px;
            height: 42px;
            font-size: 16px;
            color: #FFFFFF;
            line-height: 42px;
            margin-top: 30px;
            border-radius: 3px;
            background: #00AADD;
            overflow: hidden;
            cursor: pointer;
        }


        @media (min-width: 900px){
            .content-detail{
                /*width: 840px;*/
                width: 1000px;
                margin: 0 auto;
                padding-top: 80px;
            }
        }
        @media (max-width: 899px){
            .content-detail{
                width: 840px;
                margin: 0 auto;
                padding-top: 80px;
            }
        }
        .check-code-img{
            width: 115px;
            height: 40px;
            cursor: pointer;
        }

        .fa{
            font-size: 20px;
            color: #666;
        }
        .fa-mobile{
            font-size: 30px;
        }
        .fa-lock{
            font-size: 25px;
        }

        .login-title{
            font-size: 22px;
            color: #333;
        }
        .title-tips{
            font-size: 14px;
            color: #888;
            padding: 10px 0 20px;
        }

        .certificate {
            position: absolute;
            right: -5px;
            top: 15px;
            width: 111px;
            height: 46px;
            background: url(/img/cert/cert-btn.png) no-repeat;
        }
        .login-cert-wrap{
            position: absolute;
            top: 72px;
            right: -10px;
            padding: 30px;
            width: 355px;
            border: 1px solid #ccc;
            border-radius: 3px;
            background: #fff;
        }
        .login-cert-wrap .arrow {
            position: absolute;
            background: url(/img/cert/arrow-ico.jpg) no-repeat 350px 0;
            height: 12px;
            width: 415px;
            top: -12px;
            left: -77px;
        }
        .login-cert-wrap .cert-tips {
            text-align: left;
            font-size: 14px;
            color: #888;
            margin-top: 5px;
        }
        .login-cert-wrap .cert-fast{
            display: block;
            margin: 50px auto 0;
            width: 200px;
            height: 50px;
        }

    </style>

</head>
<body>
    <#include "/epc-release/cloudepc/common/pagePart/header.ftl">

    <#-- 重要标志，不能随意改动 -->
    <input type="hidden" id="isLoginPage" value="1">

<div class="content">
    <div class="content-detail">
        <div class="logo" onclick="location.href='/home'"></div>

        <div class="login-form">
            <h3>
                <p class="login-title">登录</p>
                <p class="title-tips">全站无需注册，手机号即可登录</p>
            </h3>

            <#--认证入口-->
            <a class="certificate " href="#" id="cert-btn"></a>
            <div class="login-cert-wrap " style="display: none;" >
                <div class="arrow"></div>
                <h3>门店认证入口</h3>
                <div class="cert-tips">认证成功，立享<strong>发布需求单</strong>新功能</div>
                <input type="button"  class="btn btn-danger cert-fast" value="立即认证" >
            </div>

            <#if message!=null>
                <div class="login-error-message">${message}</div>
            </#if>
            <div class="login-form-phone">
                <#-- 为了autocomplete属性生效而加的 form 标签，不加全局，是为了避免回车事件监听冲突 -->
                <form action="javascript:void(0)" style="height: 100%;">
                    <div class="left_div" style="border-right: none;padding-top: 12px;">
                        <i class="fa fa-mobile"></i>
                    </div>
                    <input type="text" id="userNameInput" autocomplete="on" value="${userName}" placeholder="手机号" class="in-phone">
                    <div class="left_div" style="border-left: none;">
                        <img src="/img/lop/login_del_not.png" class="clear-img" id="clearPhone">
                    </div>
                </form>
            </div>
            <div class="login-form-code">
                <div class="left_div" style="border-right: none;padding-top: 12px;">
                    <i class="fa fa-cog"></i>
                </div>
                <input type="text" id="checkCode" placeholder="校验码" class="in-code">
                <img id="checkCodeImg" class="check-code-img" src="/verifyCode/getVerifyCode?v=${.now?time}" data-url="/verifyCode/getVerifyCode" >
            </div>
            <div class="login-form-code">
                <div class="left_div" style="border-right: none;padding-top: 12px;">
                    <i class="fa fa-lock"></i>
                </div>
                <input type="text" id="verifyCodeInput" value="${verifyCode}" placeholder="验证码" class="in-code">
                <button id="getCode" class="get-code">
                    获取验证码
                </button>
            </div>
            <div class="login-form-btn" id="loginButton">
                登&nbsp;&nbsp;&nbsp;录
            </div>
        </div>

    </div>

</div>

    <#include "/epc-release/cloudepc/common/pagePart/bottom.ftl">
    <#include "/epc-release/cloudepc/common/pagePart/popup.ftl"/>
    <#include "/epc-release/cloudepc/common/resource/js.ftl"/>

</body>

<script src="/epc-release/resources/login/loginNew-fe07a1dcc9.js"></script>

<script type="text/javascript">
    $(function(){
        LOGIN_NEW.init();
    });
</script>
</html>

</#escape>
