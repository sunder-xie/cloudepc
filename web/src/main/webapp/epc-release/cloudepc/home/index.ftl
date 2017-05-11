<#escape x as x?html>
<!DOCTYPE html>

<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en" xmlns="http://www.w3.org/1999/html">
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
    <meta charset="UTF-8">
    <title>汽配管家，把你的全车件需求告诉我！guanjia.tqmall.com</title>
    <meta name="keywords" content=“汽配管家,汽配商城,汽配,汽车配件,全车件,车型件,事故件,外观件,汽车配件价格,汽车配件批发,定损,配件">
    <meta name="description" content="汽配管家是一个为第三方汽配供应商以及汽修门店提供的汽车配件交易平台。采用的是汽修门店发布需求单，供应商回复报价的形式进行交易磋商，最终在汽配管家的保障下完成交易。">

    <#include "/epc-release/cloudepc/common/resource/css.ftl"/>

    <style type="text/css">
        .content {
            background: url('${ossImage}/img/epc/login_bg_has.jpg') no-repeat;
            background-size: 100% 100%;
            height: 580px;

        }

        /*logo部分样式*/
        .logo-div{
            padding-top: 88px;
        }
        .logo {
            background: url("/img/logo/logo.png") no-repeat;
            width: 168px;
            height: 58px;
            margin: 0 auto;
        }

        /*操作部分样式*/
        .operation {
            padding-top: 70px;
        }

        .operation-btn {
            width: 270px;
            height: 140px;
            font-size: 30px;
            line-height: 140px;
            border-radius: 4px;
            border: 1px solid transparent;
            background: #000000;
            color: #FFFFFF;
            cursor: pointer;
            margin: 0 25px;

        }

        .create-wish {
            background: #00AADD;
        }

        .search-goods {
            background: #FE7A00;
            position: relative;
        }

        .un-open-mark{
            background: url(/img/home/un_open_label.png) no-repeat;
            width: 101px;
            height: 102px;
            position: absolute;
            z-index: 9;
            top: -2px;
            right: -2px;
        }

    </style>

</head>
<body>
    <#include "/epc-release/cloudepc/common/pagePart/header.ftl">

<div class="content">

    <#--<#include "/epc-release/cloudepc/common/avidCall/avidCallEntry.ftl">-->

    <div class="logo-div">
        <div class="logo"></div>
    </div>

    <div class="operation">
        <button type="button" class="operation-btn create-wish " onclick="CityUtil.checkCreateWish()">
            <img src="/img/lop/login_release.png" width="24">
            发布需求
        </button>
        <button type="button" class="operation-btn search-goods" onclick="location.href='/home/query'">
            <#--<i class="un-open-mark"></i>-->
            <img src="/img/lop/login_search.png" width="24">
            查询配件
        </button>
    </div>
</div>

    <#include "/epc-release/cloudepc/common/pagePart/bottom.ftl">
    <#include "/epc-release/cloudepc/common/pagePart/popup.ftl"/>
    <#include "/epc-release/cloudepc/common/resource/js.ftl"/>
    <#include "/epc-release/cloudepc/template/template.ftl"/>

</body>
<!-- END BODY -->
<script src="/epc-release/resources/home/index-b616e80c4e.js"></script>

<script type="text/javascript">
    <#if HOME_MSG != null>
        EPC.alertFuc('${HOME_MSG}');
    </#if>

</script>
</html>

</#escape>
