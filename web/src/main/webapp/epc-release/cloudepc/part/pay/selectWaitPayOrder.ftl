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

        .purchase-title{
            overflow: hidden;
        }
        .purchase-title-left{
            font-size: 15px;
        }
        .purchase-title-right{
            color: #02aadb;
            font-weight:lighter;
            cursor: pointer;
            font-size: 15px;
        }
        .order-pay-tips{
            overflow: hidden;
            padding-left: 20px;
            padding-right: 20px;
        }

        .order-list{
            padding: 5px 20px 25px;
        }
        .order-info{
            overflow: hidden;
            padding: 20px 0 15px;
        }
        .order-info span{
            margin-right: 25px;
        }
        .strong{
            color: #ff7800;
        }
        em{
            font-style: normal;
            padding: 0 5px;
        }

        .goods-list{
            overflow: hidden;
            background-color: #f5f8fb;
            padding: 15px;
        }
        .goods-detail{
            margin-left: 15px;
            margin-right: 15px;
        }
        .goods-detail > div{
            padding-top: 2px;
            padding-bottom: 2px;
        }
        .goods-img{
            height: 80px;
            width: 80px;
            margin-bottom: 5px;
            cursor: pointer;
            border: 1px solid #ccc;
        }
        .go-pay-bt{
            background: #ff7800;
            color: #fff;
            font-size: 15px;
            text-decoration: none;
            border-radius: 6px;
            padding: 5px 25px;
        }
        .has-pay-img{
            width: 25px;
            height: 25px;
        }
        .has-pay-text{
            font-size: 15px;
        }

        .seller-im-icon{
            margin-left: -15px;
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

    <div class="purchase-title">
        <div class="purchase-title-left fl">选择待支付订单</div>
        <div class="purchase-title-right fr" onclick="location.reload()">[ 刷新 ]</div>
    </div>

    <div class="order-select-payment">
        <div class="order-pay-tips ta-l">
            <span class="fl">您选购的商品将由 ${orderCount} 个商家进行配送，系统已自动将商品拆分成 ${orderCount} 个订单，请逐一对订单进行支付</span>
            <span class="fr">原订单总金额：<em class="strong">${totalAmount?string.currency}</em></span>
        </div>

        <div class="order-list">

        <#list orderList as order>
            <div class="order-info">
                <div class="fl">
                    <span>子订单 ${order_index+1}</span>
                    <span>订单号: ${order.orderSn}</span>
                    <span>子订单金额：<em class="strong">${order.orderAmount?string.currency}</em></span>
                    <span>
                        <i class="seller-company-img"></i>
                        ${order.sellerCompanyName}
                    </span>
                    <span class="seller-im-icon seller-chat" data-orgid="${order.sellerId}">和我联系</span>
                </div>
                <div class="fr">
                    <#if order.payStatus==0>
                        <a href="/part/pay/selectPayment?orderSn=${order.orderSn}" class="go-pay-bt">去支付</a>
                    <#else>
                        <img class="has-pay-img" src="/img/lop/center_success.png">
                        <strong class="has-pay-text">已支付</strong>
                    </#if>
                </div>
            </div>
            <div class="goods-list">
                <#list order.orderGoodsBOs as goods>
                    <div class="fl goods-detail">
                        <div>
                            <img src="${goods.goodsImg}" class="goods-img" onerror="javascript:this.src='/img/no-pic-big.png';this.onerror=null;" >
                        </div>
                        <div>${goods.partName}</div>
                        <div>${goods.soldPrice?string.currency} * ${goods.goodsNumber}</div>
                    </div>
                </#list>

            </div>

        </#list>
        </div>
    </div>

</div>

<#include "/epc-release/cloudepc/common/pagePart/bottom.ftl"/>
<#include "/epc-release/cloudepc/common/pagePart/popup.ftl"/>
<#include "/epc-release/cloudepc/common/resource/js.ftl"/>

</body>

<script type="text/javascript">

    $(function(){
        //添加图片放大功能
        ImgUtil.init($('.goods-img'), ['click']);
        //聊天
        $(".seller-chat").unbind("click").click(function(){
            var to_sys_id = $(this).data("orgid");
            TqmallChat.openChatWindow(to_sys_id,"yunpei",undefined,WishChatMap.bottom_url,WishChatMap.bottom_url_btn_name,WishChatMap.guide_url,WishChatMap.guide_content);
        });
    });

</script>

</html>
