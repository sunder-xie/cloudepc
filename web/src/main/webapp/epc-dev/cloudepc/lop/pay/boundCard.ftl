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
        .zyy-bank-info-group {
            margin-bottom: 15px;
            text-align: left;
        }
        .zyy-bank-info-group label {
            height: 30px;
            width: 75px;
            padding-right: 10px;
            text-align: right;
            font-size: 12px;
            line-height: 30px;
        }
        .zyy-bank-info-group input{
            padding: 5px 10px;
            width: 300px;
            border: 1px solid #c6c6c6;
            border-radius: 3px;
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
            <div class="zyy-bank-wrapper ta-l">
                <h4>请选择支付的银行卡（储蓄卡）</h4>
                <div class="zyy-bank-module cl">
                <#list lianLianSupportList as vo>
                    <a class="zyy-bank-item " data-code="${vo.bankCode}" href="javascript:">
                        <img src="${vo.bankLogoWeb}" alt="${vo.bankName}" height="30" width="180" />
                    </a>
                </#list>
                </div>
                <h4>请输入银行卡信息</h4>
                <div class="zyy-bank-module ">
                    <div class="zyy-bank-info-group cl">
                        <label for="cardNo"><span class="necessary-star">*</span>银行卡号</label>
                        <input placeholder="请输入银行卡号" type="text" id="cardNo"/>
                    </div>
                    <div class="zyy-bank-info-group cl">
                        <label for="acctName"><span class="necessary-star">*</span>姓名</label>
                        <input placeholder="请输入银行预留姓名" type="text" id="acctName"/>
                    </div>
                    <div class="zyy-bank-info-group cl">
                        <label for="idNo"><span class="necessary-star">*</span>身份证</label>
                        <input placeholder="请输入银行预留身份证号" type="text" id="idNo"/>
                    </div>
                </div>
                <input type="hidden" id="orderId" value="${orderInfo.id}">
                <div class="ta-r">
                    <a href="javascript:" class="next-step confirm-pay">确认支付</a>
                </div>
            </div>
        </div>
    </div>

<#include "/epc-dev/cloudepc/common/pagePart/bottom.ftl"/>
<#include "/epc-dev/cloudepc/common/pagePart/popup.ftl"/>
<#include "/epc-dev/cloudepc/common/resource/js.ftl"/>

</body>

<!-- build:js ../resources/lop/pay/boundCard.js -->
<script src="/epc-dev/resources/lop/pay/boundCard.js" type="text/javascript"></script>
<!-- endbuild -->

</html>
