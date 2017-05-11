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
        .order-success-content{
            border: 1px solid ;
        }
        .content-flag-div{
            padding-top: 50px;
            padding-left: 50px;
            padding-right: 50px;
        }
        .content-msg-div{
            width: 520px;
            padding-top: 50px;
        }
        .content-msg-div h2{
            margin-top: 0;
            margin-bottom: 15px;
            color: #666;
            font-size: 25px;
        }
        .content-detail-div{
            margin-top: 30px;
        }
        .content-detail-div a{
            text-decoration: underline;
            color: #5fb5ef;
        }
        .order-info-div{
            padding-right: 40px;
            border-right: 1px solid;
        }
        .strong{
            color: #ff7800;
        }
        .learn-more-div{
            padding-left: 40px;
        }
        .learn-more-div img{
            margin-right: 10px;
        }
        .order-sn{
            color: #5fb5ef;
        }

        .go-pay-bt{
            background: #ff7800;
            color: #fff !important;
            font-size: 15px;
            text-decoration: none !important;
            border-radius: 6px;
            padding: 5px 25px;
        }

    </style>

</head>
<body>
<#include "/epc-release/cloudepc/common/pagePart/header.ftl">

    <div class="content-wrapper w1190">
        <div class="pc-header cl">
            <div class="fl">
                <div class="fl gj-logo" onclick="location.href='/home'"></div>
                <div class="fl purchase-tip">支付成功</div>
            </div>
            <div class="fr purchase-step"></div>
        </div>
        <h4 class="ta-l purchase-title">提交订单</h4>
        <div class="order-success-content cl">
            <div class="fl content-flag-div">
                <img src="/img/lop/center_success.png">
            </div>
            <div class="fl content-msg-div">
                <div class="ta-l">
                    <h2>恭喜您，支付成功！</h2>
                    <p>温馨提示：商家发货后15天，系统将自动确认收货，如果没有收到商品，</p>
                    <p>请及时联系商家或者汽配管家客服！</p>
                </div>
                <div class="content-detail-div">
                    <div class="fl ta-l order-info-div">
                        <p>订单号：<span class="order-sn">${orderInfo.orderSn}</span></p>
                        <p>订单总金额：<span class="strong">¥ ${orderInfo.orderAmount}</span></p>
                        <p>支付金额：<span class="strong">¥ ${orderInfo.orderAmount}</span></p>
                        <#if noPaySiblingOrderCount==null>
                        <p>页面在 <span class="strong return-time">15秒</span> 后自动返回到 <a href="/buy/order/myOrderPage">我的购物订单</a></p>
                        <p>您还可以 <a href="/home">返回首页</a></p>
                        </#if>
                    </div>
                    <div class="fl ta-l learn-more-div">
                        <#if noPaySiblingOrderCount==null>
                            <p>我想去了解</p>
                            <p>
                                <a href="/help/introduction">
                                    <img src="/img/lop/center_guarantee.png">淘汽担保支付
                                </a>
                            </p>
                            <p>
                                <a href="/help/introduction">
                                    <img src="/img/lop/center_underwrite.png">淘汽承保
                                </a>
                            </p>
                        <#else>
                            <p>
                                您还有 <span class="strong">${noPaySiblingOrderCount}笔</span> 子订单未支付
                            </p>
                            <p style="padding: 10px 0;">
                                <a class="go-pay-bt" href="/part/pay/selectWaitPayOrder?orderSn=${orderInfo.orderSn}">继续支付</a>
                            </p>
                            <p>
                                页面在 <span class="strong return-time">10秒</span> 后自动返回到 <a href="/part/pay/selectWaitPayOrder?orderSn=${orderInfo.orderSn}">待支付订单</a>
                            </p>
                        </#if>
                    </div>
                </div>
            </div>
            <div class="fl">
                <img src="${ossImage}/img/epc/center_sketch.jpg" width="450">
            </div>
        </div>

    </div>

<#include "/epc-release/cloudepc/common/pagePart/bottom.ftl"/>
<#include "/epc-release/cloudepc/common/pagePart/popup.ftl"/>
<#include "/epc-release/cloudepc/common/resource/js.ftl"/>

</body>
    <script type="text/javascript">

        var intervalId; //定时器id
        var count = 15; //倒计时，秒
        var curCount; //当前剩余秒数
        var url = '/buy/order/myOrderPage';

        <#if noPaySiblingOrderCount != null>
        url = '/part/pay/selectWaitPayOrder?orderSn=${orderInfo.orderSn}';
        count = 10;
        </#if>

        $(function(){
            curCount = count;
            intervalId = window.setInterval(setRemainTime, 1000);
        });

        function setRemainTime() {
            if (curCount == 0) {
                window.location.href = url;
            } else {
                curCount--;
                $('.return-time').text(curCount + '秒');
            }
        }

    </script>
</html>
