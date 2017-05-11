<#escape x as x?html>
<!DOCTYPE html>

<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en">
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
    <meta charset="utf-8"/>
    <title>汽配管家</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta content="width=device-width, initial-scale=1.0" name="viewport"/>
    <meta http-equiv="Content-type" content="text/html; charset=utf-8">

    <#include "/epc-dev/cloudepc/common/resource/css.ftl"/>

    <style type="text/css">
        .content {
            height: 610px;
        }

        .car-filter-header {
            height: 50px;
            padding-left: 30px;
            background: #f5f9fc;
            border-bottom: 1px solid #d6e5ec;
            text-align: left;
            padding-top: 15px;
        }
    </style>
    <style type="text/css">

        #carStyle li, #carYear li, #carPower li, #car-choice li{
            list-style-type: none;
            float: left;
            margin-top: 11px;
        }
        #choiceCarCompare span {
            font-size: 12px;
            color: #777;
            border: 1px solid #bfcfd9;
            border-radius: 2px;
        }

        #choiceCarCompare span:hover {
            background-color: #B5EDFD;
            border: 1px solid #02AADB;
            color: #02aadb;
            font-size: 12px;
            border-radius: 2px;
        }
        .car-list-li span {
            font-size: 12px;
            padding: 3px;
        }

        .choiceCar-li {
            margin-top: 20px !important;
            margin-right: 8px !important;
            margin-left: 8px;
        }

        .choice-li {
            background-color: #B5EDFD;
            border: 1px solid #02AADB;
            color: #02aadb;
            font-size: 12px;
            border-radius: 2px;
        }

        .div-display-none {
            display: none;
        }

        .div-display-block {
            display: block;
        }
        .table-car-list:hover {
            border: 1px solid #149AD1;
            background-color: #A7E9FE;
        }

        #car-type-select {
            border: 1px solid #D6E5EC;
            border-top: none;
            color: #8CA5BB;
            width: 10.5%;
            height: 40px;
            text-align: center;
            padding-top: 13px;
            float: left;
        }

        .car-param-select {
            background-color: #EEF3F6;
            border-bottom: 1px solid #D6E5EC;
            width: 89.5%;
            height: 40px;
            float: right;
        }

        .car-list-li {
            cursor: pointer;
            margin-right: 36px;
        }

        #car-compare {
            color: #6E7073;
            height: 560px;
        }

        #carCompareInfoHeader {
            background-color: #DDE9EE;
            border: 1px solid #D6E5EC;
            border-right: none;
            padding-top: 10px;
            height: 35px;
            width: 100%;
        }

        #carTypeList {
            background-color: #DDE9EE;
            border-right: 1px solid #D6E5EC;
            width: 40%;
            height: 530px;
            padding-left: 20px;
            padding-top: 2px;
            float: left;
        }

        #CarParamList {
            width: 60%;
            height: 530px;
            background-color: #DDE9EE;
            float: right;
        }

        #car-choice-left {
            background-color: #e6edf1;
            border-top: none;
            color: #7F7C7C;
            height: 430px;
            text-align: center;
            padding-top: 8px;
            float: left;
        }

        #car-choice-right {
            background-color: #e6edf1;
            width: 89.56%;
            float: right;
            padding-bottom: 20px;
        }

        #compare {
            border-radius: 2px;
            height: 30px;
            width: 98px;
            margin-top: 8px;
            background-color: #169bc9;
        }

        .car-type-list-table {
            cursor: pointer;
            border: 1px solid #D6E5EC;
            margin: 0 auto;
        }

        #carTypeList th {
            background-color: #D2DDE6;
            height: 30px;
        }

    </style>
</head>
<body>
    <#include "/epc-dev/cloudepc/common/pagePart/top.ftl">

<input type="hidden" id="seriesId" value="${seriesId}">

<div id="car-type-info" class="main-content">
    <div id="carTypeInfoHeader" class="car-filter-header"></div>
    <div id="carStyle"></div>
    <div id="carYear"></div>
    <div id="carPower"></div>
    <div id="car-choice"></div>
</div>

    <#include "/epc-dev/cloudepc/common/pagePart/bottom.ftl">
    <#include "/epc-dev/cloudepc/common/pagePart/popup.ftl"/>
    <#include "/epc-dev/cloudepc/common/resource/js.ftl"/>
    <#include "/epc-dev/cloudepc/template/template.ftl"/>

</body>
<!-- END BODY -->
<!-- build:js ../resources/car/carFilter.js -->
<script src="/epc-dev/resources/common/other/template.js" type="text/javascript"></script>
<script src="/epc-dev/resources/common/epc/pageTop.js" type="text/javascript"></script>
<script src="/epc-dev/resources/car/carFilter.js" type="text/javascript"></script>
<!-- endbuild -->
</html>

</#escape>
