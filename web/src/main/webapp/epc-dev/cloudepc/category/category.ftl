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
    <style type="text/css">

        #firstList ul, #secondList ul, #thirdList-down ul {
            padding-left: 0px;
            list-style-type: none;
        }

        .firstList-backColor, #firstList ul li:hover {
            background-color: #149AD3;
        }

        #firstList ul li:hover, #choiceCat li:hover {
            color: #02AADB;

        }

        .secondList-backColor {
            color: #02AADB;
            border-top: 1px solid #02AADB;
            border-bottom: 1px solid #02AADB;
        }


        #firstList {
            height: 100%;
            width: 7%;
            float: left;
            background-color: #252C3B;
        }

        #firstList span {
            color: #C0C2C4;
        }

        #firstList li {
            cursor: pointer;
            text-align: center;
            color: #6E7073;
            height: 70px;
            padding-top: 24px;
        }

        #secondList {
            border-top: 1px solid #D6E5EC;
            background-color: #F5F9FC;
            height: 100%;
            width: 10%;
            float: left;
            font-size: 12px;
            color: #747A7D;
            padding-right: 4px;
        }

        #secondList li {
            height: 29px;
            text-align: center;
            padding-top: 7px;
            cursor: pointer;
        }

        .car-info-middle-div {
            border: 1px solid #D6E5EC;
            background-color: #EEF3F6;
            height: 100%;
            width: 40%;
            float: left;
            overflow: auto;
        }

        #carInfoSearchParam {
            height: 40px;
            width: 100%;
            padding-top: 14px;
            padding-left: 16px;
        }

        #carInfoSearchParam span {
            color: #747A7D;
            font-size: 12px;
        }

        .car-info-right-div {
            border-top: 1px solid #D6E5EC;
            background-color: #e6edf1;
            height: 100%;
            width: 43%;
            float: left;
            overflow: auto;
        }

        #describe, #suitableModel {
            background-color: #D2DDE6;
        }

        .change-backColor {
            background-color: #E6EDF1 !important;
            color: #02aadb !important;

        }

        .car-model-list span:hover {
            color: red;
        }

        #change {
            height: 51px;
            width: 100%;
            margin-top: -1px;
            font-size: 18px;
            cursor: pointer;
        }

        #change span {
            height: 41px;
            width: 50%;
            text-align: center;
            padding-top: 15px;
            color: #747A7D;
        }

        #describe {
            float: left;
        }

        #onDescribe {
            color: #747A7D;
            padding-left: 15px;
            padding-right: 15px;
        }

        #suitableModel {
            float: right;
        }

        #onSuitableModel p {
            padding-left: 10px;
            margin-top: 10px;
            color: #6E7073;
        }


        #carInfoHeader {
            margin-top: 21.5px;
            /*margin-left: 15px;*/
            color: #898c8f;
            float: left;
        }

        #thirdList-up {
            border-top: 1px solid #D6E5EC;
            border-bottom: 1px solid #D6E5EC;
            padding: 12px 0px;
            height: 32px;
        }

        #thirdList-up ul {
            list-style-type: none;
            font-size: 14px;
            color: #6E7073;
        }

        .thirdList-up-li {
            cursor: pointer;
            float: left;
            margin-left: -24px;
            margin-right: 34px;
            margin-top: -2px;
            font-size: 12px;
        }

        #thirdList-down {
            border-bottom: 1px solid #D6E5EC;
            padding: 5px 10px;
            width: 100%;
            overflow: hidden;
            color: #747A7D;
        }

        #choiceCat li {
            border: 1px solid #bfcfd9;
            float: left;
            margin: 3px 5px;
            padding: 1px 3px;
            border-radius: 2px;
            cursor: pointer;
        }


        .font-decoration {
            color: orangered;
            text-decoration: underline;
            float: right;
        }
        .vinSearch-noData {
            display: none;
            color: #FE8A71;
            border-radius: 4px;
            height: 40px;
            width: 65%;
            text-align: center;
            padding-top: 8px;
            margin-top: 70px;
            font-size: 14px;
            background-color: #EEF3F6;
            border: 2px solid #D6E5EC;
        }
        .height-25{
            height:25px;
        }


        /* 配件列表样式 */
        .goods-list{
            padding: 10px;
            /*padding: 10px 10px 0 10px;*/
            /*height: 575px;*/
            /*overflow: auto;*/
        }

        .goods-table{
            width: 100%;
            border: 1px solid #D6E5EC;
        }
        .goods-table th{
            background-color: #DAE4EA;
            height: 30px;
        }
        .goods-table td{
            height: 30px;
            cursor: pointer;
        }

        .show-goods-detail{
            text-decoration: underline !important;
            color: #FB9985 !important;
        }

    </style>
</head>
<body>
    <#include "/epc-dev/cloudepc/common/pagePart/top.ftl">

<input type="hidden" id="vinNumber" value="${vinNumber}">
<input type="hidden" id="carId" value="${carId}">
<input type="hidden" id="carName" value="${carName}">

<div id="content" class="content-height" style="display: none;">
    <div id="firstList" ></div>
    <div id="secondList"></div>
    <div class="car-info-middle-div">
        <div id="carInfoSearchParam">
            <span style="float: left;">查询条件&nbsp;&nbsp;&nbsp;<span style="color: #FE8A71;">${carName}</span></span>
        </div>
        <div id="thirdList-firstWord"></div>
        <div id="thirdList"></div>
        <div id="oeList" class="goods-list"></div>
    </div>

    <div class="car-info-right-div">
        <div id="change">
            <span onclick="onChange(this)" id="describe">辅助描述</span>
            <span onclick="onChange(this)" id="suitableModel">适配车型</span>
        </div>
        <div id="onDescribe">
            <table class="detail-table">
                <tbody id="remarks">
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
            <div id="pictureList" class="picture-list">
                <img class="small-pic picture-border" src="/img/no-pic-big.png">
            </div>
            <div class="pic-goods-div">
                <a id="showPicGoods" href="#" >查看本图相关配件</a>
            </div>
            <div>
                <img id="bigPicture" title="点击查看原图" class="big-picture"
                     src="/img/no-pic-big.png" onerror="javascript:this.src='/img/no-pic-big.png';this.onerror=null;">
            </div>
        </div>
        <div id="onSuitableModel" style="display: none;margin: 0px 16px;">
            <div id="CarModel"></div>
        </div>

    </div>
</div>
<div id="vinSearch-noData" class="content-height" style="width: 100%;display: none; border-top: 1px solid #D6E5EC;background-color: #EEF3F6;">
    <div style=" height: 100px;width: 33%;padding: 10px;margin: 0 auto;">
        <span>查询条件&nbsp;&nbsp;&nbsp;<span style="color: #FE8A71;">${carName}</span></span>
        <p style="margin-top: 17px;font-size: 16px;background-color: #f2dede;color: #a94442;padding: 10px;border-radius: 5px;">该车型数据暂未开放</p>
    </div>
</div>

    <#include "/epc-dev/cloudepc/common/pagePart/bottom.ftl">
    <#include "/epc-dev/cloudepc/common/pagePart/popup.ftl"/>
    <#include "/epc-dev/cloudepc/common/resource/js.ftl"/>
    <#include "/epc-dev/cloudepc/template/template.ftl"/>

</body>
<!-- END BODY -->
<!-- build:js ../resources/category/category.js -->
<script src="/epc-dev/resources/common/other/template.js" type="text/javascript"></script>
<script src="/epc-dev/resources/common/epc/pageTop.js" type="text/javascript"></script>
<script src="/epc-dev/resources/category/category.js" type="text/javascript"></script>
<!-- endbuild -->
</html>

</#escape>
