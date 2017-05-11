<html>
<head>
    <meta charset="utf-8"/>
    <title>汽配管家</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta content="width=device-width, initial-scale=1.0" name="viewport"/>
    <meta http-equiv="Content-type" content="text/html; charset=utf-8">

    <#include "/epc-dev/cloudepc/common/resource/css.ftl"/>

    <!-- build:css ../resources/lop/pay/payment.css -->
    <link href="/epc-dev/resources/lop/pay/payment.css" rel="stylesheet" type="text/css">
    <!-- endbuild -->

    <style type="text/css">

        .content{
            border: 1px solid ;
            height: 380px;
        }
        .content-detail{
            margin: 0 auto;
            width: 280px;
            padding-top: 90px;
        }
        .btn-div button{
            border: 1px solid transparent;
            cursor: pointer;
            border-radius: 5px;
            width: 120px;
            color: #FFF;
            padding: 10px;
            margin-top: 10px;
            font-size: 14px;
        }
        .repay-btn{
            background: #FE7A00;
            margin-right: 20px;
        }
        .show-detail-btn{
            background: #5db6e6;
        }
        .msg-div{
            padding: 15px;
        }
        .msg-div img{
            margin-right: 25px;
        }
        .important-msg{
            color: #333;
            font-size: 20px;
            padding-top: 5px;
        }
        .reason{
            color: #666;
            font-size: 14px;
        }


    </style>

</head>
<body>
<#include "/epc-dev/cloudepc/common/pagePart/header.ftl">

    <div class="content-wrapper w1190">
        <div class="pc-header cl">
            <div class="fl">
                <div class="fl gj-logo" onclick="location.href='/home'"></div>
                <div class="fl purchase-tip">支付失败</div>
            </div>
            <div class="fr purchase-step"></div>
        </div>
        <h4 class="ta-l purchase-title">提交订单</h4>
        <div class="content">
            <div class="content-detail cl">
                <div class="msg-div cl">
                    <div class="fl">
                        <img src="/img/lop/pay_failed.png" width="52" height="64">
                    </div>
                    <div class="fl ta-l">
                        <p class="important-msg">抱歉，支付失败...</p>
                        <p class="reason">请检查网络环境是否正常</p>
                    </div>
                </div>
                <div class="btn-div">
                    <button type="button" class="repay-btn" onclick="location.href='/wish/pay/selectPayment?orderSn=${orderSn}'">重新支付</button>
                    <button type="button" class="show-detail-btn" onclick="location.href='/wish/offer/detail?orderSn=${orderSn}'">查看订单详情</button>
                </div>
            </div>
        </div>

    </div>

<#include "/epc-dev/cloudepc/common/pagePart/bottom.ftl"/>
<#include "/epc-dev/cloudepc/common/pagePart/popup.ftl"/>
<#include "/epc-dev/cloudepc/common/resource/js.ftl"/>

</body>

</html>
