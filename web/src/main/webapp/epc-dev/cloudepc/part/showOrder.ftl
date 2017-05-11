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

    <#include "/epc-dev/cloudepc/common/resource/css.ftl"/>

    <!-- build:css ../resources/lop/offer/showOffer.css -->
    <link href="/epc-dev/resources/lop/offer/showOffer.css" rel="stylesheet" type="text/css">
    <!-- endbuild -->

    <style type="text/css">
        .my-order-adaptive-car {
            float: right;
        }

    </style>

</head>
<body>
    <#include "/epc-dev/cloudepc/common/pagePart/topWish.ftl">

<div class="content w1190">
    <#include "/epc-dev/cloudepc/lop/wish/left.ftl">

    <div class="main">
        <div class="offer-select-type">
            <ul>
                <li class="current" onclick="SHOW_ORDER.showOrder(1, this)">待付款</li>
                <li onclick="SHOW_ORDER.showOrder(2, this)">待发货</li>
                <li onclick="SHOW_ORDER.showOrder(3, this)">已发货</li>
                <li onclick="SHOW_ORDER.showOrder(4, this)">已完结</li>
            </ul>
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

        <div id="orderItems">
            <div style="padding-top: 120px;padding-bottom: 230px;font-size: 15px;color: #000000;"></div>
        </div>

        <div class="qxy_page"></div>

    </div>

</div>

    <#include "/epc-dev/cloudepc/common/pagePart/bottom.ftl">
    <#include "/epc-dev/cloudepc/common/pagePart/popup.ftl"/>
    <#include "/epc-dev/cloudepc/common/resource/js.ftl"/>

    <#include "/epc-dev/cloudepc/template/partTemplate.ftl"/>
    <#include "/epc-dev/cloudepc/part/cancelOrder.ftl"/>
    <#include "/epc-dev/cloudepc/part/adaptiveCar.ftl"/>

</body>

<!-- build:js ../resources/part/showOrder.js -->
<script src="/epc-dev/resources/common/other/template.js" type="text/javascript"></script>
<script src="/epc-dev/resources/common/epc/pageTop.js" type="text/javascript"></script>
<script src="/epc-dev/resources/common/epc/page.js" type="text/javascript"></script>
<script src="/epc-dev/resources/part/cancelOrder.js" type="text/javascript"></script>
<script src="/epc-dev/resources/part/adaptiveCar.js" type="text/javascript"></script>
<script src="/epc-dev/resources/part/showOrder.js" type="text/javascript"></script>
<!-- endbuild -->

</html>

</#escape>