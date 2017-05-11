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
            /*max-height: 500px;*/
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

        .table-style-1 th:nth-child(1) {  width: 40%;  }
        .table-style-1 th:nth-child(2) {  width: 40%;  }
        .table-style-1 th:nth-child(3) {  width: 20%;  }

        .table-style-1 td:nth-child(1) {  width: 40%;  }
        .table-style-1 td:nth-child(2) {  width: 40%;  }
        .table-style-1 td:nth-child(3) {  width: 20%;  }


        .content-left {
            border-left: 1px solid #D6E5EC;
            width: 30%;
            height: 100%;
            float: left;
            overflow: auto;
        }

        .content-right {
            width: 45%;
            height: 100%;
            float: left;
            border-left: 1px solid #D6E5EC;
            border-right: 1px solid #D6E5EC;
        }

        .quote-div{
            width: 25%;
            height: 100%;
            float: left;
            border-right: 1px solid #D6E5EC;
        }

        .content-title {
            background-color: #DAE4EA;
            color: #6E7073;
            width: 100%;
            height: 41px;
            padding-top: 12px;
            padding-left: 0;
        }

        .content-title > span {
            font-size: 13px;
            color: #777;
            text-align: center;
        }

        .content-right-content {
            padding: 10px;
        }

        .car-type{
            position: absolute;
            text-align: left;
            padding: 10px;
            margin-top: -43px;
            font-size: 13px;
        }
        .car-type span{
            color: #FE8A71;
            margin-left: 5px;
            margin-right: 20px;
            font-size: 13px;
        }

        .content{
            background-color: #EEF3F6;
            /*padding-top: 45px;*/
            padding: 45px 5px 0 5px;
        }

        .big-picture-div{
            margin-top: 10px;
        }

        .detail-table{
            color: #747A7D;
        }
        .detail-table td:nth-child(1){width: 60%;}
        .detail-table td:nth-child(2){width: 40%;}


        /* 报价信息相关样式 */
        .offering-info-content{
            height: 590px;
            overflow: auto;
        }
        #offeringTable{
            padding: 10px;
        }
        #offeringTable td:nth-child(1){width: 45%;}
        #offeringTable td:nth-child(2){width: 55%;}


        .count{
            float: left;
            height: 25px;
            line-height: 25px;
            margin-right: 10px;
        }

        .countBox {
            width: 112px;
            overflow: hidden;
            height: 25px;
            line-height:25px;
            border: 1px solid #E6E5E5;
            border-radius: 3px;
            /*z-index: 9999;*/
            /*position: relative;*/
        }
        .countCon1, .countCon2 {
            width: 25%;
            overflow: hidden;
            border: none;
            text-align: center;
            float: left;
            background-color: #EEF3F6;
            font-size: 20px;
            color: #000000;
            outline: none;
            cursor: pointer;
            line-height: 18px;
        }
        .countCon1{
            font-size: 28px;
        }

        .countCon3 {
            width: 50%;
            overflow: hidden;
            float: left;
            text-align: center;
            border-left: 1px solid #E6E5E5;
            border-right: 1px solid #E6E5E5;
            border-top: none;
            border-bottom: none;
            font-size: 14px;
            display: block;
        }

    </style>

</head>
<body>
    <#include "/epc-release/cloudepc/common/pagePart/top.ftl">

<input type="hidden" id="searchValue" value="${searchValue}">
<input type="hidden" id="searchType" value="${searchType}">

<input type="hidden" id="picNum" value="${picNum}">
<input type="hidden" id="carId" value="${carId}">

<div id="content" class="content-height content">

    <div class="car-type">
        车型
        <span>${carName}</span>

        配图编号
        <span>${picNum}</span>
    </div>

    <div id="content-left" class="content-left">
        <table class="goods-table table-style-1">
            <thead>
            <tr class="height-40">
                <th>OE码</th>
                <th>配件名称</th>
                <th>位置编号</th>
            </tr>
            </thead>
            <tbody id="goodsList">

            </tbody>
        </table>

    </div>

    <div id="content-right" class="content-right">
        <div class="content-title">
            <span>辅助描述</span>
        </div>
        <div id="" class="content-right-content">
            <table class="detail-table">
                <tbody id="description">
                <tr>
                    <td>配件备注</td>
                    <td>配件用量</td>
                </tr>
                <tr>
                    <td>配图编号</td>
                    <td>位置编号</td>
                </tr>
                </tbody>
            </table>
            <div class="big-picture-div">
                <img id="bigPicture" title="点击查看原图" class="big-picture"
                     src="/img/no-pic-big.png" onerror="javascript:this.src='/img/no-pic-big.png';this.onerror=null;">
            </div>
        </div>
    </div>

    <div id="" class="quote-div">
        <div class="content-title" style="text-align: left;">
            <span>报价信息</span>
        </div>

        <div class="row buy-condition">
            <div class="part-quality-filter">筛选:</div>
            <div id="chooseQuality"></div>
        </div>

        <div id="offerInfoContent" class="offering-info-content"></div>

    </div>

</div>

    <#include "/epc-release/cloudepc/common/pagePart/bottom.ftl">
    <#include "/epc-release/cloudepc/common/pagePart/popup.ftl"/>
    <#include "/epc-release/cloudepc/common/resource/js.ftl"/>
    <#include "/epc-release/cloudepc/template/template.ftl"/>

</body>

<script src="/epc-release/resources/goods/picGoods-55f6a74559.js"></script>
</html>

</#escape>
