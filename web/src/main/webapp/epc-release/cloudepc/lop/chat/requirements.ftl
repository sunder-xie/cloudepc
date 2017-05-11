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

    <style type="text/css">
        body {
            font-size: 12px;
            color: #000000;
            height: 320px;
            overflow: hidden;
            min-width: auto;
        }

        div {
            text-align: left;
        }

        span {
            color: #567494;
        }

        .ordinary-style{
            color: #567494 !important;
        }

        .orange-style {
            color: #ff9400 !important;
        }

        /* 整个内容宽度 */
        .content-div {
            /*padding: 10px;*/
            background-color: #f6fafe;
            width: 700px;
            height: 100%;
        }

        /* 弹窗 */
        .modal {
            top: auto !important;
            overflow-y: hidden !important;
            width: 700px;
        }

        .nav-tabs {
            border-bottom: none;
        }

        .nav-tabs > li.active > a, .nav-tabs > li.active > a:hover, .nav-tabs > li.active > a:focus {
            border: none;
        }

        .nav > li > a {
            padding: 3px 15px;
        }

        .tab-content {
            margin-bottom: 10px;
            padding: 5px 5px 5px 5px;
            overflow: auto;
            background-color: #fff;
            max-height: 55px;
        }

        .order-num-span {
            display: inline-block;
            margin: 2px 14px;
            cursor: pointer;
        }

        .order-num-span:hover {
            color: #c00;
        }

        .order-num-span.choose {
            color: #ff9400;
        }

        .title {
            font-weight: bold;
            text-align: center;
            font-size: 15px;
        }

        .detail-table td {
            padding: 2px 0;
        }
        .detail-table td:nth-child(1){width: 100%;}

        .goods-table {
            margin-top: 5px;
        }

        .goods-table tbody {
            display: block;
            overflow: auto;
            max-height: 126px; /* 页面报价信息高度 */
        }

        /* 补充信息列表高度 */
        .more-info-table tbody {
            max-height: 248px !important;
        }

        /* 报价页面品质列表高度 */
        .modal-body > .goods-table > #qualityTb {
            max-height: 92px;
        }

        .goods-table thead, tbody tr {
            display: table;
            width: 100%;
            table-layout: fixed;
        }

        .goods-table thead th {
            color: #537293;
            background-color: #dfebf6;
        }

        .goods-table tbody td {
            color: #9bb0c7;
        }

        .goods-table th, .goods-table td {
            padding: 5px 5px;
            text-align: center;
        }

        .goods-table td {
            border: solid 1px #ecf2fa;
            overflow: hidden;
            text-overflow: ellipsis;
            white-space: nowrap;
        }

        /* 弹窗 */
        /*.modal-full{*/
        /*width: 96%;*/
        /*}*/
        .modal-body {
            padding: 0 5px;
        }

        .modal-close {
            position: absolute;
            top: 1px;
            right: 8px;
            text-align: center;
            border-radius: 16px;
            z-index: 9999;
            border: none;
            padding-top: 0;
            font-weight: bold;
            font-size: 18px;
        }

        .modal-section {
            margin: 16px 0 0 0;
            background-color: #ffffff;
            border: 1px solid rgba(0, 0, 0, 0.2);
            border-radius: 6px;
            -webkit-box-shadow: 0 3px 9px rgba(0, 0, 0, 0.5);
            box-shadow: 0 3px 9px rgba(0, 0, 0, 0.5);
            background-clip: padding-box;
        }

        .table-style-1 {
            /*width: 690px;*/
        }

        .table-style-1 th:nth-child(1) {
            width: 20%;
        }

        .table-style-1 th:nth-child(2) {
            width: 14%;
        }

        .table-style-1 th:nth-child(3) {
            width: 14%;
        }

        .table-style-1 th:nth-child(4) {
            width: 14%;
        }

        .table-style-1 th:nth-child(5) {
            width: 10%;
        }

        .table-style-1 th:nth-child(6) {
            width: 13%;
        }

        .table-style-1 th:nth-child(7) {
            width: 15%;
        }

        .table-style-1 td:nth-child(1) {
            width: 20%;
        }

        .table-style-1 td:nth-child(2) {
            width: 14%;
        }

        .table-style-1 td:nth-child(3) {
            width: 14%;
        }

        .table-style-1 td:nth-child(4) {
            width: 14%;
        }

        .table-style-1 td:nth-child(5) {
            width: 10%;
        }

        .table-style-1 td:nth-child(6) {
            width: 13%;
        }

        .table-style-1 td:nth-child(7) {
            width: 15%;
        }

        /* 没有滚动条 15%; 有滚动条 13%; */

        .table-style-2 {
            /*width: 690px;*/
        }

        .table-style-2 th:nth-child(1) {
            width: 20%;
        }

        .table-style-2 th:nth-child(2) {
            width: 20%;
        }

        .table-style-2 th:nth-child(3) {
            width: 15%;
        }

        .table-style-2 th:nth-child(4) {
            width: 15%;
        }

        .table-style-2 th:nth-child(5) {
            width: 15%;
        }

        .table-style-2 th:nth-child(6) {
            width: 15%;
        }

        .table-style-2 td:nth-child(1) {
            width: 20%;
        }

        .table-style-2 td:nth-child(2) {
            width: 20%;
        }

        .table-style-2 td:nth-child(3) {
            width: 15%;
        }

        .table-style-2 td:nth-child(4) {
            width: 15%;
        }

        .table-style-2 td:nth-child(5) {
            width: 15%;
        }

        .table-style-2 td:nth-child(6) {
            width: 15%;
        }

        /* 没有滚动条 15%; 有滚动条 12%; */

        .table-style-2 select, .table-style-2 input {
            width: 100%;
            text-align: center;
        }

        .btn-pink {
            color: #fff !important;
            background-color: #dd808f;
            border-color: #D96B7D;
        }

        .btn-pink:hover {
            background-color: #D96B7D;
        }

        .btn {
            margin: 0 10px;
        }

        .btn-sm {
            padding: 2px 15px;
        }

        .row {
            margin: 0;
        }

        .add-quote:hover, .modify-quote:hover {
            cursor: pointer;
            text-decoration: underline;
        }

        .name-input {
            width: 350px;
        }

        .oe-input {
            width: 200px;
        }

        .name-input, .oe-input {
            height: 25px;
            color: #567494;
            padding: 0 4px;
        }

        .small-img {
            border: 1px solid #c9c9c9;
            width: 15px;
            height: 15px;
            cursor: pointer;
        }

        .msg-style {
            color: #B3ABAB;
            padding-bottom: 10px;
            text-align: center;
            font-size: 15px;
            font-weight: bold;
        }

        /* select2 调整 */
        .select2 {
            width: 100% !important;
        }

        .select2-selection__arrow {
            height: 20px !important;
            width: 14px !important;
        }

        .select2-selection__rendered {
            line-height: 22px !important;
            color: #567494 !important;
        }

        .select2-selection--single {
            height: 22px !important;
        }

        .select2-results__options {
            max-height: 150px !important;
        }

        .nav-tabs > li.refresh {
            float: right;
            margin: 5px 15px;
            color: #428bca;
            cursor: pointer;
        }

        .goods-table tbody td.hasPrice {
            cursor: pointer;
            color: #ff9400;
            text-decoration: underline;
        }

        .noTxt {
            text-align: center;
            margin: 10px 0;
        }

        .img-click {
            cursor: pointer;
            display: block;
            margin: 0 auto;
        }

        #mylist {
            display: none;
        }
    </style>

</head>
<body >

<div class="content-div">
    <div>
        <ul class="nav nav-tabs">
            <li class="active title">
                <a href="#tab1" data-toggle="tab" aria-expanded="false">
                    今日聊到
                </a>
            </li>
            <li class="refresh">刷新</li>

        </ul>
        <div class="tab-content">
            <div class="tab-pane fade active in text-align-left" id="tab1">
                <#if orderSn??>
                    <#list orderSn as list>
                        <#if list_index == 0>
                            <span class="order-num-span choose" data-seller-id="${sellerId}">${list}</span>
                        <#else>
                            <span class="order-num-span" data-seller-id="${sellerId}">${list}</span>
                        </#if>
                    </#list>
                <#else>
                    <p class="noTxt">您与当前联系人的今日聊天记录中尚未涉及任何需求单号</p>
                </#if>
            </div>
        </div>
    </div>

    <div id="detailDiv">

    </div>


    <div class="modal fade" id="basicModal" tabindex="-1" style="display: none;">
        <span title="关闭窗口" class="modal-close btn btn-default" data-dismiss="modal">
            X
        </span>
        <div class="modal-section">
            <div class="modal-body">
                <div id="mylist">
                    <div class="title">我的需求：</div>
                    <table class="goods-table table-style-1">
                        <thead>
                        <tr>
                            <th>配件名称</th>
                            <th>OE码</th>
                            <th>首选品质</th>
                            <th>次选品质</th>
                            <th>数量</th>
                            <th>配件图片</th>
                        </tr>
                        </thead>
                        <tbody class="mylist">

                        </tbody>
                    </table>
                </div>
                <div id="more">

                </div>
            </div>
        </div>
    </div>

</div>

<#-- 需求单详情模板 -->
<script type="text/html" id="detailTemplate">
    {{if (wishListVO != '')}}
    <div class="title">需求单详情</div>
    <div class="row">
        <div class="col-xs-9">
            <table class="detail-table">
                <tbody>
                <tr>
                    <td>
                        车架号：
                        <span class="ordinary-style">{{wishListVO['vin']}}</span>
                    </td>
                </tr>
                <tr>
                    <td>
                        车型信息：
                        <span class="ordinary-style">{{wishListVO['carInfo']}}</span>
                    </td>
                </tr>
                <tr>
                    <td>
                        需求单状态：
                        <span class="orange-style" id="orderStatus"></span>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
        <div class="col-xs-3" style="text-align: right;">
            {{if suppleGoodsList != null && suppleGoodsList.length > 0}}
            <button id="moreInfoBt" type="button" class="btn btn-sm btn-info">
                查看补充信息
            </button>
            {{/if}}
        </div>
    </div>
    <div>
        <table class="goods-table table-style-1">
            <thead>
            <tr>
                <th>配件名称</th>
                <th>OE码</th>
                <th>首选品质</th>
                <th>次选品质</th>
                <th>数量</th>
                <th>配件图片</th>
                <th>单价</th>
            </tr>
            </thead>
            <tbody>
            {{each wishListVO['goodsList'] as goods idx}}
            <tr id="{{goods.groupId}}">
                <td title="{{goods.requireGoodsName}}">{{goods.requireGoodsName}}</td>
                {{if (goods.requireOeNum && goods.requireOeNum != '')}}
                <td title="{{goods.requireOeNum}}">{{goods.requireOeNum}}</td>
                {{else}}
                <td>无</td>
                {{/if}}
                <td>{{goods.requireGoodsQualityType}}</td>
                <td>
                    {{if (goods.requireGoodsQualityTypeReserve && goods.requireGoodsQualityTypeReserve != '')}}
                    {{goods.requireGoodsQualityTypeReserve}}
                    {{else}}
                    无
                    {{/if}}
                </td>
                <td>{{goods.requireGoodsNumber}}</td>
                <td>
                    {{each goods.requireGoodsImages as img imgIdx}}
                    <img width="15" height="15" src="{{img}}" class="img-click" onerror="this.src='${ossImage}/images/no_picture.gif'"/></a>
                    {{/each}}
                </td>
                <td class="statusTxt"></td>
            </tr>
            {{/each}}
            </tbody>
        </table>
    </div>
    {{else}}
    <div id="detailDiv">暂无查询信息</div>
    {{/if}}
</script>

<#-- 补充信息模板 -->
<script type="text/html" id="moreInfoTemplate">
    <div class="title">{{text}}：</div>
    <table class="goods-table table-style-1 more-info-table" id="moreInfor">
        <thead>
        <tr>
            <th>配件名称</th>
            <th>OE码</th>
            <th>配件品质</th>
            <th>配件品牌</th>
            <th>数量</th>
            <th>单价</th>
        </tr>
        </thead>
        <tbody>
        {{each list as data idx}}
        <tr data-id="{{data.groupId}}">
            <td title="{{data.goodsName}}">{{data.goodsName}}</td>
            {{if (data.goodsOe && data.goodsOe != '')}}
            <td title="{{data.goodsOe}}">{{data.goodsOe}}</td>
            {{else}}
            <td>无</td>
            {{/if}}
            <td>{{data.goodsQualityTypeStr}}</td>
            <td>
                {{if (data.goodsBrand && data.goodsBrand !='')}}
                {{data.goodsBrand}}
                {{else}}
                无品牌
                {{/if}}
            </td>
            <td>{{data.goodsNumber}}</td>
            <td>￥{{data.goodsPrice}}</td>
        </tr>
        {{/each}}
        </tbody>
    </table>

</script>

<script type="text/html" id="msgTemplate">
    <div class="msg-style">{{msg}}</div>
</script>

    <#include "/epc-release/cloudepc/common/resource/js.ftl"/>

</body>
<!-- END BODY -->

<script src="${monkDomain}/monk-release/resources/chat/chat-iframe.js?v=${.now?string('yyyy-MM-dd')}" type="text/javascript"></script>

<script src="/epc-release/resources/lop/chat/requirements-c6a7ce6826.js"></script>

</html>

</#escape>
