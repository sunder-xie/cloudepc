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

    <#include "/epc-release/cloudepc/common/resource/css.ftl"/>
    <style type="text/css">
        .brand-first-word {
            height: 50px;
            /*padding-left: 30px;*/
            /*background: #f5f9fc;*/
            /*border-bottom: 1px solid #d6e5ec;*/
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
            height: 90px;
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
        .avid-banner{
            background: url("/img/lop/avid_banner.jpg") no-repeat;
            width: 100%;
            height: 220px;

        }
        .avid-call-btn{
            background: url("/img/lop/avid_call_btn_logo.png") no-repeat;
            height: 55px;
            width: 160px;
            position: absolute;
            right: 20px;
            top: 10px;
            cursor: pointer;
            padding-top: 11px;
            padding-left: 51px;
        }
        .avid-call-msg-1{
            text-align: left;
            font-size: 13px;
            font-weight: bold;
            color: #00a9dd;
        }
        .avid-call-msg-2{
            text-align: left;
            font-size: 10px;
            color: #ccc;
            font-family: "Arial";
        }

        .tip-avid-call-btn{
            background: url("/img/lop/avid_call_btn_logo.png") no-repeat;
            height: 55px;
            width: 160px;
            cursor: pointer;
            margin-left: -10px;
            position: relative;
            padding-top: 9px;
            padding-left: 51px;
        }
        .tip-avid-call-btn > .avid-call-msg-2{
            margin-top: -5px;
        }

        .close-avid-btn{
            background: url("/img/lop/close_avid_btn_grey.png") no-repeat;
            width: 16px;
            height: 16px;
            position: absolute;
            right: 10px;
            top: 18px;
            cursor: default;
            z-index: 9;
        }
        .close-avid-btn:hover{
            background: url("/img/lop/close_avid_btn.png") no-repeat;
        }

        .avid-tip-div{
            background: #f5f9fc;
            padding-top: 15px;
            padding-left: 30px;
            text-align: left;
            font-size: 15px;
            position: relative;
        }
        .fixed{
            position: fixed !important;
            top: 0;
            width: 100%;
        }

        .series-span-selected{
            border: solid 2px red !important;
        }

        /* 特殊处理 */
        .layui-layer-tips-fix{
            z-index: 99 !important;
        }
        .layui-layer-tips-fix > .layui-layer-content{
            background: none !important;
            box-shadow: none !important;
            padding: 0 !important;
        }

        .avid_call_guide_step_1{
            background: url("/img/lop/avid_call_guide_step_1.png") no-repeat;
            height: 130px;
            width: 520px;
            position: relative;
        }
        .avid_call_guide_step_1 span{
            position: absolute;
            left: 65px;
            top: 98px;
            font-size: 15px;
            color: #FFF;
            background-color: #0BC6E4;
            border-radius: 2px;
            padding: 3px 10px;
        }

    </style>
</head>
<body>
    <#include "/epc-release/cloudepc/common/pagePart/top.ftl">

    <div class="main-content">
        <div class="avid-banner"></div>

        <div class="avid-tip-div">
            请选择要询价的车型
            <div class="avid-call-btn">
                <div class="avid-call-msg-1">我要急呼</div>
                <div class="avid-call-msg-2">Online Service</div>
            </div>
            <div id="firstWord-li" class="brand-first-word"></div>
        </div>

        <div id="carTypeBody" class="car-type-body"></div>
    </div>

<#-- 隐藏文本域 -->
<input type="hidden" id="pageUriId" value="${pageUriId}">

<#-- 模板 -->
<script type="text/html" id="avidCallBtnTemplate">
    <div class="tip-avid-call-btn">
        <div class="avid-call-msg-1">我要急呼</div>
        <div class="avid-call-msg-2">Online Service</div>
    </div>
    <b title="关闭" class="close-avid-btn"></b>
</script>

<script type="text/html" id="avidCallGuideStep1">
    <div class="avid_call_guide_step_1"><span>{{data}}</span></div>
</script>


    <#include "/epc-release/cloudepc/common/pagePart/bottom.ftl">
    <#include "/epc-release/cloudepc/common/pagePart/popup.ftl"/>
    <#include "/epc-release/cloudepc/common/resource/js.ftl"/>
    <#include "/epc-release/cloudepc/template/template.ftl"/>

</body>

<script src="/epc-release/resources/avid/avidCall-469e3f469b.js"></script>
</html>

</#escape>
