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
        .brand-first-word {
            height: 50px;
            padding-left: 30px;
            background: #f5f9fc;
            border-bottom: 1px solid #d6e5ec;
        }

        .brand-first-word > li {
            padding-right: 30px;
            margin-top: 15px;
            float: left;
            list-style-type: none;
        }

        .brand-first-word > a {
            text-decoration: none;
            color: #747A7D;
        }
        .car-brand td{
            padding-right: 40px;
        }
        .car-brand th img {
            /*display: block;*/
            /*margin: 0 auto -8px;*/
            width: 120px;
            height: auto;
            padding: 10px;
        }
        .car-list span {
            border: 1px solid #EEF3F6;
            padding: 2px;
            font-size: 12px;
            color: #747A7D;
            cursor: pointer;
            display: inline-block;
        }

        .car-list span:hover {
            border: 1px solid #02aadb;
            color: #02aadb;
            border-radius: 2px;
            background-color: #B5EDFD;
            padding: 2px;
        }
        .car-type-body {
            background-color: #EEF3F6;
            padding-left: 30px;
        }
        .width-heigth-25{
            width:25px;
            height:25px;
        }


    </style>
</head>
<body>
    <#include "/epc-dev/cloudepc/common/pagePart/top.ftl">

<div class="main-content">
    <div id="firstWord-li" class="brand-first-word"></div>
    <div id="carTypeBody" class="car-type-body"></div>
</div>

    <#include "/epc-dev/cloudepc/common/pagePart/bottom.ftl">
    <#include "/epc-dev/cloudepc/common/pagePart/popup.ftl"/>
    <#include "/epc-dev/cloudepc/common/resource/js.ftl"/>
    <#include "/epc-dev/cloudepc/template/template.ftl"/>

</body>
<!-- END BODY -->
<!-- build:js ../resources/car/carModel.js -->
<script src="/epc-dev/resources/common/other/template.js" type="text/javascript"></script>
<script src="/epc-dev/resources/common/epc/pageTop.js" type="text/javascript"></script>
<script src="/epc-dev/resources/car/carModel.js" type="text/javascript"></script>
<!-- endbuild -->
</html>

</#escape>
