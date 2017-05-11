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
        .payment{
            width: 176px;
            height: 62px;
            margin-right: 10px;
            margin-bottom: 10px;
            cursor: pointer;
        }
        .payment-selected{
            border: 1px solid #cd0006;
        }

    </style>

</head>
<body>
<#include "/epc-dev/cloudepc/common/pagePart/header.ftl">

<div class="content-wrapper w1190">
    <div class="pc-header cl">
        <div class="fl">
            <div class="fl gj-logo" onclick="location.href='/home'"></div>
            <div class="fl purchase-tip">支付</div>
        </div>
        <div class="fr purchase-step"></div>
    </div>
    <h4 class="ta-l purchase-title">选择支付方式</h4>
    <div class="order-select-payment">
        <div class="order-pay-tips ta-l">
            <a href="/wish/offer/detail?orderId=${orderInfo.id}" target="_blank" class="fr order-detail">［查看订单详情］</a>
            <span>订单号：<b>${orderInfo.offerListSn}</b></span>
            <span class="offer-amount">支付金额：<em class="strong">￥${orderInfo.offerAmount}元</em></span>
            <p>请您下提交订单<em class="strong">24小时</em>内完成支付，否则订单会自动取消。</p>
        </div>
        <div class="order-pay-select ta-l">
            <h4>请选择支付方式</h4>
            <div class="payment-div">
            <#list paymentList as payment>
                <img class="payment <#if orderInfo.payId == payment.id>payment-selected</#if>" src="${payment.url}"
                     data-id="${payment.id}" data-code="${payment.code}"/>

                <#--<label class="payment" >-->
                    <#--<input <#if orderInfo.payId == payment.id>checked</#if> type="radio" name="payment" value="${payment.id}" data-name="${payment.title}" data-code="${payment.code}">-->
                    <#---->
                    <#--&lt;#&ndash;<span>${payment.title}</span>&ndash;&gt;-->
                <#--</label>-->
            </#list>
            </div>
            <input type="hidden" id="orderId" value="${orderInfo.id}">
            <div class="ta-r ">
                <a href="javascript:" class="next-step">下一步</a>
            </div>
        </div>
    </div>
</div>

<#include "/epc-dev/cloudepc/common/pagePart/bottom.ftl"/>
<#include "/epc-dev/cloudepc/common/pagePart/popup.ftl"/>
<#include "/epc-dev/cloudepc/common/resource/js.ftl"/>

</body>


<!-- build:js ../resources/lop/pay/selectPayment.js -->
<script src="/epc-dev/resources/lop/pay/selectPayment.js" type="text/javascript"></script>
<!-- endbuild -->

</html>
