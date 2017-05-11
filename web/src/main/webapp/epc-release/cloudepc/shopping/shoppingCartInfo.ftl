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
        /*body{*/
            /*padding-left:40px!important;*/
            /*padding-right: 40px!important;*/
        /*}*/
        a{
            font-weight: lighter;
        }
        .shopping-cart-title{
            border: 1px solid #dadada;
            height: 50px;
        }
        .title-ul{
            width: auto;
            height: 50px;
            float: left;
            padding-left: 0px;
        }
        .title-ul li {
            width: 160px;
            overflow: hidden;
            float: left;
            text-align: center;
            padding: 10px 0;
            line-height: 26px;
            font-size: 14px;
            color: #797979;
        }
        .title-ul li.current {
            border-bottom: 2px solid #ff9900;
        }
        .shopping-cart-body{
            /*height: 400px;*/
            margin: 30px auto 0;
        }
        .body-nav-table {
            width: 100%;
            overflow: hidden;
            background-color: white;
            border:1px solid #DDDDDD;
            line-height: 50px;
            text-align: center;
            border-collapse: inherit;
        }
        .body-content{
            /*height: 349px;*/
            /*max-height: 500px;*/
            /*overflow-y: auto;*/
            /*overflow-x: hidden*/
            overflow: hidden;
        }
        .body-content-height{
            /*height: 146px;*/
        }
        .body-content-table {
            width: 100%;
            margin: 0 auto;
            text-align: center;
            background-color: #fff;
        }
        .body-content-table td {
            padding: 10px 0;
        }
        .body-content-seller{
            width: 1180px;
            overflow: hidden;
            padding: 20px 0 12px 1px;
            /*background-color: #fff;*/
        }
        .body-content-seller span{
            float: left;
        }
        .seller-name {
            margin-left: 20px;
            padding: 2px 6px 1px 26px;
            background: url('/img/lop/im_icon.png') no-repeat 6px 3px #02aadb;
            color: #fff;
            cursor: pointer;
            border-radius: 2px;
        }
        .body-content-goods {
            width: 100%;
            margin: 0 auto;
            background-color: white;
            border:1px solid #DDDDDD;
        }
        .goods-info-box{
            width: 100%;
            height: 80px;
            margin: 0 0 0 10px;
            border-right: 1px solid #ddd;
            position: relative;
        }

        .goods-info-img{
            width: 20%;
            height: 80px;
            overflow: hidden;
            float: left;
            cursor: pointer;
            border: 1px solid #ddd;
        }
        .goods-info-img-set{
            width: 100%;height: 100%;
        }
        .goods-info-text{
            width: 80%;
            height: 80px;
            float: left;
            padding-left: 10px;
            text-align: left;
        }
        .goods-info-text-con{
            width: 100%;
            height:25px;
            line-height:25px;
            margin-top: 5px;
        }
        .goods-for-car{
            width: 90px;
            height: 24px;
            line-height: 24px;
            float: left;
            border: 1px solid #ddd;
            text-align: center;
            color: #797979;
            cursor: pointer;
            background-color: #fff;
        }
        .car-list{
            width: 661px;
            overflow: hidden;
            padding: 10px;
            border: 2px solid #ff9933;
            background-color: #fff;
            position: absolute;
            z-index: 100;
            top: 80px;
            left: 0px;
            display: none;
        }
        .car-list-con{
            width: 320px;
            overflow: hidden;
            float: left;
            color: #949494;
            margin: 0 5px;
        }
        .border{
            border-right: 1px solid #ededed;
        }
        .product-info-font{
            width: 260px;
            float: left;
            text-align: left;
            padding-left: 10px;
            padding-top: 5px;
            height:24px;
        }
        .goods-info-text-con p {
            color: #ff9900;
        }
        .countBox1 {
            width: 97px;
            overflow: hidden;
            height: 30px;
            line-height: 30px;
            border: 1px solid #E6E5E5;
            border-radius: 3px;
        }
        .countCon1, .countCon2 {
            width: 25px;
            height: 30px;
            overflow: hidden;
            border: none;
            text-align: center;
            float: left;
            background-color: #EEF3F6;
            font-size: 20px;
            color: #000000;
            outline: none;
            line-height: 26px;
            cursor: pointer;
        }
        .countCon3 {
            width: 45px;
            height: 30px;
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
        .goods-price-text {
            width: 100%;
            color: #797979;
            line-height: 24px;
        }
        .goods-price-text strong {
            font-size: 14px;
            color: #ff9900;
            /*margin: 0 3px;*/
        }
        .goods-option {
            margin: 0 auto;
            overflow: hidden;
        }
        .goods-option-nav {
            border: 1px solid #ff9900;
            color: #ff9900;
            padding: 3px 8px;
            font-weight: normal;
            cursor: pointer;
            /*margin: 0 5px 0 0;*/
            /*vertical-align: middle;*/
            display: inline-block;
            /*line-height: 20px;*/
        }
        .goods-option-nav:hover{
            background-color: rgba(0,0,0,.075);
        }
        .shopping-cart-foot {
            width: 100%;
            height: 48px;
            overflow: hidden;
            margin: 50px auto 80px;
            line-height: 48px;
            border:1px solid #DDDDDD;
            background-color: white;
        }
        .shopping-cart-foot span{
            cursor: pointer;
            float: left;
        }
        .shopping-cart-foot span:hover{
            color: #02aadb;
        }
        .shopping-cart-foot b {
            margin: 0 10px;
            float: right;
            font-weight: normal;
        }
        .foot-checkout {
            width: 160px;
            overflow: hidden;
            float: right;
            line-height: 48px;
            background-color: #ff9900;
            color: #fff;
            text-align: center;
            display: inline-block;
            vertical-align: middle;
            margin: 0 0 0 10px;
            cursor: pointer;
            font-size: 16px;
        }
        .shopping-cart-foot b strong {
            font-size: 16px;
            color: #ff9900;
        }
        .content{
            padding: 0 70px;
        }
        .cart-goods-delete{
            padding: 1px 4px 2px 4px;
            border-radius: 10px;
        }
        .cart-goods-delete:hover{
            background-color: red;
            color: white;
        }
        .goods-oe{
            float: left;
            height: 22px;
            color: #B2CCE2;
            line-height: 22px;
        }
        .goods-for-car-box{
            border:1px solid red;
            height:200px;
            width:150px;
        }

        .shopping-cart-adaptive-car {
            float: right;
            margin-right: 100px;
        }
        .no-data-logo{
            position: relative;
            height: 65px;
            width: 65px;
            margin:10px auto;
            border-radius: 31px;
            background-color: #ff7800;
        }
        .logo-set{
            position: absolute;
            color: azure;
            top:26px;
            left:13px;
            font-size: 40px;
        }


        .un-available{
            border-radius: 3px;
            color: #fff;
            background-color: #c33;
            padding: 5px 15px;
            font-size: 15px;
        }

        .mark-un-available{
            color: #fff;
            background-color: #a7a2a2;
            padding: 3px 5px;
            margin-left: 2px;
        }

    </style>
</head>
<body>
    <#include "/epc-release/cloudepc/common/pagePart/topWish.ftl">
    <div class="content">
        <div class="shopping-cart-body">
            <table width="100%" border="0" cellpadding="0" cellspacing="0" class="body-nav-table">
                <tbody>
                    <tr>
                        <td style="width:7%;text-align: left;padding-left: 20px;">
                            <input name="checkedAll" onclick="checkedAll(this)" checked="checked" type="checkbox" style="margin-right: 4px;">全选
                        </td>
                        <td style="width: 38%;text-align: left;padding-left: 100px;">商品信息</td>
                        <td style="width: 13%;">单价(元)</td>
                        <td style="width: 15%;">数量</td>
                        <td style="width: 15%;">金额(元)</td>
                        <td style="width: 12%;">操作</td>
                    </tr>
                </tbody>
            </table>
            <div class="body-content" id="shoppingCartBody"></div>
        </div>
        <div class="shopping-cart-foot" id="shoppingCartFoot">
            <span style="padding-left: 20px;">
                <input name="checkedAll" checked="checked" type="checkbox" onclick="checkedAll(this)">
                <b style="cursor: pointer;margin-left: 5px;">全选</b>
            </span>
            <span onclick="deleteProduct(-1)" style="margin-right: 10px;">删除选中商品</span>
            <span onclick="deleteUnAvailableGoods()">清空失效商品</span>

            <div class="foot-checkout" onclick="consignee()">结 算</div>
            <b>合计(不包含运费):
                <strong>￥</strong>
                <strong id="totalAmount">0.00</strong>
            </b>
            <b>已选商品&nbsp;<strong id="totalNum">0</strong>&nbsp;件</b>
        </div>

    </div>

    <#include "/epc-release/cloudepc/common/pagePart/bottom.ftl">
    <#include "/epc-release/cloudepc/common/pagePart/popup.ftl"/>
    <#include "/epc-release/cloudepc/common/resource/js.ftl"/>
    <#include "/epc-release/cloudepc/template/shoppingCartTemplate.ftl"/>
    <#include "/epc-release/cloudepc/part/adaptiveCar.ftl"/>

</body>

<script src="/epc-release/resources/shopping/shoppingCartInfo-686db81b36.js"></script>
</html>

</#escape>
