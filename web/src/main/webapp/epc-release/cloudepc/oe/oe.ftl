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
        .goods-table tbody {
            display: block;
            overflow: auto;
            max-height: 500px;
        }

        .goods-table thead, .goods-table tbody tr {
            display: table;
            width: 100%;
            table-layout: fixed;
        }
        .goods-tr-style{
            cursor: pointer;
        }

        .goods-table thead th {
            color: #777;
            background-color: #DAE4EA;
            font-size: 13px;
        }

        .goods-table tbody td {
            color: #777;
            border-right: 1px solid #D6E5EC;
            border-bottom: 1px solid #D6E5EC;
        }

        .goods-table th, .goods-table td {
            padding: 7px 5px;
            text-align: center;
        }

        .table-style-1 {
        }

        .table-style-1 th:nth-child(1) {
            width: 8%;
        }

        .table-style-1 th:nth-child(2) {
            width: 25%;
        }

        .table-style-1 th:nth-child(3) {
            width: 25%;
        }

        .table-style-1 th:nth-child(4) {
            width: 30%;
        }

        .table-style-1 th:nth-child(5) {
            width: 12%;
        }

        .table-style-1 td:nth-child(1) {
            width: 8%;
        }

        .table-style-1 td:nth-child(2) {
            width: 25%;
        }

        .table-style-1 td:nth-child(3) {
            width: 25%;
        }

        .table-style-1 td:nth-child(4) {
            width: 30%;
        }

        .table-style-1 td:nth-child(5) {
            width: 12%;
        }

        .content {
            height: 580px;
            background-color: #EEF3F6;
        }

        .goods-filter{
            width: 13%;
            float: left;
        }
        .goods-content{
            width: 87%;
            float: left;
            height: 100%;
        }

        .content-left {
            border: 1px solid #D6E5EC;
            border-bottom: none;
            width: 65%;
            height: 100%;
            float: left;
        }

        .content-right {
            width: 35%;
            height: 100%;
            float: left;
            border-right: 1px solid #D6E5EC;
            border-bottom: none;
            overflow: auto;
        }

        .pagination-div {
            width: 100%;
            height: 30px;
            padding-left: 25px;
        }

        .content-right-title {
            background-color: #DAE4EA;
            color: #6E7073;
            width: 100%;
            height: 41px;
            padding-top: 12px;
            padding-left: 0;
        }

        .content-right-title > span {
            font-size: 13px;
            color: #777;
            text-align: center;
        }

        .content-right-content {
            padding: 5px;
        }

        .filter-select{
            width: 100%;
        }
        .goods-filter div{
            padding: 5px 10px;
        }
        .filter-title{
            text-align: left;
            font-size: 14px;
            margin-top: 10px;
            color: #777;
        }

        /* select2 调整 */
        .select2{
            width: 100% !important;
        }
        .select2-selection__rendered{
            line-height: 24px !important;
            color: #8ca5bb !important;
            text-align: left;
        }

        .show-goods-detail{
            text-decoration: underline !important;
            color: #FB9985 !important;
        }

    </style>

</head>
<body>
    <#include "/epc-release/cloudepc/common/pagePart/top.ftl">

<input type="hidden" id="searchValue" value="${searchValue}">
<input type="hidden" id="searchType" value="${searchType}">

<div id="content" class="content ">

    <div id="goods-filter" class="goods-filter hidden">
        <div class="filter-title">车型筛选</div>
        <div>
            <select id="carBrandSelect" class="filter-select">
                <option value="-1">选择品牌</option>
            </select>
        </div>
        <div>
            <select id="carModelSelect" class="filter-select">
                <option value="-1">选择车型</option>
            </select>
        </div>

        <div class="filter-title">分类筛选</div>
        <div>
            <select id="cateSelect" class="filter-select">
                <option value="-1">选择分类</option>
            </select>
        </div>
    </div>

    <div id="goods-content" class="goods-content hidden">
        <div id="content-left" class="content-left">
            <table class="goods-table table-style-1">
                <thead>
                <tr class="height-40">
                    <th>序号</th>
                    <th>OE码</th>
                    <th>配件名称</th>
                    <th>备注</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody id="SearchResult">

                </tbody>
            </table>

            <!-- 分页 -->
            <div style="text-align: center; padding: 10px;">
                <div class="pagination">
                    <a href="#" class="first" data-action="first" >首页</a>
                    <a href="#" class="previous" data-action="previous" >上一页</a>
                    <input type="text" readonly="readonly" data-max-page="40"/>
                    <a href="#" class="next" data-action="next" >下一页</a>
                    <a href="#" class="last" data-action="last">末页</a>
                </div>
            </div>

        </div>

        <div id="content-right" class="content-right">
            <div class="content-right-title">
                <span>请选择适配车型</span>
            </div>
            <div id="CarModel" class="content-right-content"></div>
        </div>

    </div>
</div>

    <#include "/epc-release/cloudepc/common/pagePart/bottom.ftl">
    <#include "/epc-release/cloudepc/common/pagePart/popup.ftl"/>
    <#include "/epc-release/cloudepc/common/resource/js.ftl"/>
    <#include "/epc-release/cloudepc/template/template.ftl"/>

</body>
<!-- END BODY -->
<script src="/epc-release/resources/oe/oe-46be0b12fa.js"></script>
</html>

</#escape>
