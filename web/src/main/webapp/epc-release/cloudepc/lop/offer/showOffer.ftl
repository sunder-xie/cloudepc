<#escape x as x?html>
<!DOCTYPE html>

<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en" xmlns="http://www.w3.org/1999/html">
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
    <meta charset="utf-8"/>
    <title>汽配管家</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta content="width=device-width, initial-scale=1.0" name="viewport"/>
    <meta http-equiv="Content-type" content="text/html; charset=utf-8">

    <#include "/epc-release/cloudepc/common/resource/css.ftl"/>

    <link rel="stylesheet" href="/epc-release/resources/lop/offer/showOffer-ad9f1c8635.css">

</head>
<body>
    <#include "/epc-release/cloudepc/common/pagePart/topWish.ftl">

<div class="content w1190">
    <#include "/epc-release/cloudepc/lop/wish/left.ftl">

    <div class="main">
        <div class="offer-select-type">
            <ul>
                <li class="current" onclick="SHOW_OFFER.showOffer(1, this)">待付款</li>
                <li onclick="SHOW_OFFER.showOffer(2, this)">待发货</li>
                <li onclick="SHOW_OFFER.showOffer(3, this)">已发货</li>
                <li onclick="SHOW_OFFER.showOffer(4, this)">已完结</li>
            </ul>

            <div class="wish-show-all">
                显示全部
            </div>

            <div class="wish-search-button">
                搜索
            </div>

            <div class="wish-search-div">
                <input class="wish-search-input" placeholder="输入车型、车品牌">
            </div>
        </div>

        <div class="offer-head">
            <table style="width: 100%;">
                <thead>
                <tr>
                    <th width="35%">商品信息</th>
                    <th width="10%">单价(元)</th>
                    <th width="10%">数量</th>
                    <th width="10%">商品操作</th>
                    <th width="10%">订单金额(元)</th>
                    <th width="10%">订单状态</th>
                    <th width="15%">操作</th>
                </tr>
                </thead>
            </table>
        </div>

        <div id="offerItems"></div>

        <div class="qxy_page"></div>

    </div>
</div>

    <#include "/epc-release/cloudepc/common/pagePart/bottom.ftl">
    <#include "/epc-release/cloudepc/common/pagePart/popup.ftl"/>
    <#include "/epc-release/cloudepc/common/resource/js.ftl"/>
    <#include "/epc-release/cloudepc/template/lopTemplate.ftl"/>
    <#include "/epc-release/cloudepc/lop/wish/cancelWish.ftl"/>

</body>

<script src="/epc-release/resources/lop/showOffer-f5aa381ddf.js"></script>

</html>

</#escape>
