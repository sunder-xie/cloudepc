<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>汽配管家－验证页面</title>
    <link rel="shortcut icon" href="/img/logo.png">
    <link href="/epc-dev/resources/common/count-down/coming-soon.css" rel="stylesheet" type="text/css">
    <link href="/epc-dev/resources/captcha/captcha.css" rel="stylesheet" type="text/css">

    <style>

    </style>
</head>

<body>
    <input type="hidden" id="ttlTime" value="${time}">
    <input type="hidden" id="overNum" value="${overNum}">
    <input type="hidden" id="refuseFtl" value="${refuseFtl}">
    <div class="logo">
        <a href="http://www.tqmall.com/">
            <img width="80" src="/img/logo.png" alt="汽配管家">
            <span>汽配管家</span>
        </a>
    </div>

    <div class="container">
        <div class="page">

            <div class="title">
            <#if overNum == -2 >
                <h1>正在跳转首页，请稍后....<span>:)</span></h1>
            <#elseif overNum == -1>
                <h1>您的操作过于频繁，已被永久封禁，请联系
                    <a href="mailto:tech-data@tqmall?subject=【配件管家解封锁申请-${ip}】">
                        管理员
                    </a>
                    进行解封<span>:(</span>
                </h1>
                <br>
                <h1>
                    <span style="float: left;margin-left: 35px;">解封申请邮箱:tech-data@tqmall.com</span>
                    <br>
                    <span style="float: left;margin-left: 35px;">您的IP地址:${ip}</span>
                </h1>
            <#else>
                <h1>您的操作过于频繁，请耐心等待，倒计时结束后方可继续访问<span>:(</span></h1>
            </#if>
            </div>
            <div class="">
                <#--<h2>倒计时</h2>-->
                <div id="defaultCountdown">
                </div>
            </div>

            <#--<div class="right">-->
                <#--<#if overNum != 1>-->
                    <#--<h2>请耐心等待</h2>-->
                    <#--<#else >-->
                        <#--<h2>验证码</h2>-->

                <#--</#if>-->
            <#--</div>-->
            <#--<button id="test">测试异步</button>  -->

        </div>

    </div>

    <#--<div class="footer"> <a href="mailto:tech-data@tqmall.com">   淘汽档口   ©2013-2016</a> </div>-->


    <script src="/epc-dev/resources/common/other/jquery.min.js" type="text/javascript"></script>
    <script src="/epc-dev/resources/common/count-down/jquery.countdown.js" type="text/javascript"></script>
    <script src="/epc-dev/resources/common/count-down/jquery.countdown-zh-CN.js" type="text/javascript"></script>
    <#--<script src="/epc-dev/resources/captcha/forbidSpider.js" type="text/javascript"></script>-->
    <script src="/epc-dev/resources/captcha/captcha.js" type="text/javascript"></script>

    <script type="application/javascript">
    $(function(){
       $("#test").click(function(){
           $.post("/epc/test/test",{id:2}, function (data, status, xhr) {
               console.log(data);
               var refuseSpider=xhr.getResponseHeader("refuseSpider");
               if(refuseSpider == "true"){
                   window.location.href = xhr.getResponseHeader("refuseFtl");
               }
           })
       });


    });
</script>
</body>
</html>
