<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>验证页面</title>
    <link rel="shortcut icon" href="/img/car_32.ico">
    <link href="/epc-dev/resources/common/count-down/count-down.css" rel="stylesheet" type="text/css">

    <style>
        body {
            position: relative;
            line-height: 1.4;
            color: #737373;
            background: url(${ossImage}/img/epc/home/beijing.png) no-repeat #77ab4c;
            min-height: 700px;
        }
        .container {
            width: 90%;
            overflow: auto;
            margin: auto;
            position: absolute;
            top: 0;
            left: 0;
            bottom: 0;
            right: 0;
            /*height: 700px;*/
            /*min-width: 1000px;*/
            overflow-y: hidden;
        }
        .page{
            max-width: 90%;
            padding: 30px 20px 50px;
            border: 1px solid #b3b3b3;
            border-radius: 4px;
            margin: 0 auto;
            box-shadow: 0 1px 10px #a7a7a7, inset 0 1px 0 #fff;
            background: #fcfcfc;
        }
        .page > div{
            text-align:center

        }

    </style>
</head>

<body>
<input type="hidden" id="ttlTime" value="${time}">
<input type="hidden" id="overNum" value="${overNum}">
<input type="hidden" id="refuseFtl" value="${refuseFtl}">

    <div class="container">
        <div class="page">
            <#if overNum == -2 >
                <h1>正在跳转首页，请稍后....<span>:)</span></h1>
            <#elseif overNum == -1>
                <h1>您的操作过于频繁，已被永久封禁，请联系管理员进行解封<span>:(</span></h1>
            <#else>
                <h1>您的操作过于频繁，请耐心等待，倒计时结束后方可继续访问<span>:(</span></h1>

                <div>
                    <div id="defaultCountdown">
                    </div>
                </div>
            </#if>


            <#if overNum = 1>
                <span>验证码</span>
            </#if>
            <button id="test">测试异步</button>

        </div>

    </div>

    <script src="/epc-dev/resources/common/other/jquery.min.js" type="text/javascript"></script>
    <script src="/epc-dev/resources/common/count-down/jquery.countdown.js" type="text/javascript"></script>
    <script src="/epc-dev/resources/common/count-down/jquery.countdown-zh-CN.js" type="text/javascript"></script>

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
        var overTime = Number($("#overNum").val());
        if(overTime == -2){
            // go home
            window.location.href = "/home";
            return false
        }
        if(overTime > 0){
            //倒计时
            var nowTime = new Date().getTime();
            var ttlTime = Number($("#ttlTime").val());
            var closeDate = new Date(nowTime+ttlTime*1000);
            $('#defaultCountdown').countdown({
                until: closeDate,
                onExpiry:function(){
//                    window.history.go(-1);;
                }
            });
        }
        //更改浏览器的url
        var refuseFtl = $("#refuseFtl").val();
        var true_url = 'http://' + window.location.host+refuseFtl;

        if( window.location.href != true_url) {
            var stateObj = { foo: new Date().getTime() };
            history.pushState(stateObj, "", refuseFtl);
        }
        //高度自适应
        $(".container").height($(".page").outerHeight());

    });
</script>
</body>
</html>
