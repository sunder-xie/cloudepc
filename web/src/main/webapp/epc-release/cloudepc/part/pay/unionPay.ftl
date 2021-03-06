<html>
<head>
    <meta charset="utf-8"/>
    <title>汽配管家</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta content="width=device-width, initial-scale=1.0" name="viewport"/>
    <meta http-equiv="Content-type" content="text/html; charset=utf-8">

    <#include "/epc-release/cloudepc/common/resource/css.ftl"/>

    <link rel="stylesheet" href="/epc-release/resources/part/pay/payment-5f2bda70c7.css">

    <style type="text/css">

        .zyy-bank-item-add, .zyy-bank-item{
            position: relative;
            height: 63px;
            width: 318px;
        }
        .zyy-bank-card-type {
            position: absolute;
            right: 10px;
            top: 10px;
            padding: 5px;
            /*background: #fdcf00;*/
            font-size: 12px;
            line-height: 1;
            -webkit-border-radius: 3px;
            -moz-border-radius: 3px;
            -o-border-radius: 3px;
            border-radius: 3px;
        }

        .card-no{
            padding-left: 15px;
        }

        .zyy-bank-item-add {
            background: #e6eff5;
            font-size: 15px;
            font-weight: 700;
            line-height: 63px;
            text-align: center !important;
        }

    </style>

</head>
<body>
<#include "/epc-release/cloudepc/common/pagePart/header.ftl">

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
            <a href="/buy/order/orderDetail?orderSn=${orderInfo.orderSn}" target="_blank" class="fr order-detail">［查看订单详情］</a>
            <span>订单号：<b>${orderInfo.orderSn}</b></span>
            <span class="offer-amount">支付金额：<em class="strong">${orderInfo.orderAmount?string.currency}元</em></span>
            <p>请及时进行支付</p>
            <#--<p>请您下提交订单<em class="strong">24小时</em>内完成支付，否则订单会自动取消。</p>-->
        </div>
        <div class="zyy-bank-wrapper ta-l">
            <h4>请选择支付的银行卡（储蓄卡）</h4>

            <div class="zyy-bank-module cl ">
            <#list userBoundCardList as vo>
                <a class="zyy-bank-item" href="javascript:">
                    <img src="${vo.bankLogoWeb}" alt="${vo.bankName}" height="35" width="185" style="margin-left:-30px;" rel="${vo.bankCode}"/>
                    <div class="card-no ta-l">${vo.cardNo}</div>
                    <input type="hidden" class="agreeNo" value="${vo.noAgree}">
                    <input type="hidden" class ="cardNo" value="${vo.cardNo}">
                    <input type="hidden" class="acctName" value="${vo.acctName}">
                    <input type="hidden" class="idNo" value="${vo.idNo}">
                    <span class="zyy-bank-card-type">
                        <#if vo.cardType == 0>储蓄卡</#if>
                    </span>
                </a>
            </#list>
                <a class="zyy-bank-item zyy-bank-item-add" href="/part/pay/boundCardPage?orderSn=${orderInfo.orderSn}">
                    ＋ 添加新银行卡
                </a>
            </div>
            <input type="hidden" id="orderSn" value="${orderInfo.orderSn}">
            <div class="ta-r">
                <a href="javascript:" class="next-step confirm-pay">确认支付</a>
            </div>
        </div>
    </div>
</div>

<#include "/epc-release/cloudepc/common/pagePart/bottom.ftl"/>
<#include "/epc-release/cloudepc/common/pagePart/popup.ftl"/>
<#include "/epc-release/cloudepc/common/resource/js.ftl"/>


</body>

<script src="/epc-release/resources/part/pay/unionPay-6ed97d889f.js"></script>

</html>

