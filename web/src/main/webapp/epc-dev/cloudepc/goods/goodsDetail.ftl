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
            border-top: 1px solid #d6e5ec;
        }

        .info-div span {
            padding-left: 10px;
            color: #fe8a71;
            padding-right: 15px;
        }

        .part-body-left {
            width: 30%;
            height: 100%;
            padding-left: 15px;
            /*padding-right: 15px;*/
            background: #f5f9fc;
            float: left;
            overflow: auto;
        }

        .part-body-middle {
            width: 45%;
            height: 100%;
            padding: 10px;
            background: #eef3f6;
            float: left;
        }

        .part-body-right {
            width: 25%;
            height: 100%;
            /*padding-left: 15px;*/
            /*padding-right: 15px;*/
            background: #e6edf1;
            float: left;
        }

        .part-title {
            height: 40px;
            padding-top: 11px;
            padding-left: 15px;
            font-size: 14px;
            text-align: left;
        }

        .detail-table td:nth-child(1){width: 60%;}
        .detail-table td:nth-child(2){width: 40%;}


        /* 报价信息相关样式 */
        .offering-info-content{
            height: 635px;
            overflow: auto;
        }
        #offeringTable{
            padding: 10px;
        }
        #offeringTable span{
            color: #8ca5bb;
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
    <#include "/epc-dev/cloudepc/common/pagePart/top.ftl">

<input type="hidden" id="goodsId" value="${goodsId}">
<input type="hidden" id="modelId" value="${modelId}">
<input type="hidden" id="from" value="${from}">

<div id="car-oe-content" class="content-height content">

    <#--<div id="partHeader" class="info-div">-->
        <#--配件OE码-->
        <#--<span>${goods.oeNumber}</span>-->
        <#--配件名称-->
        <#--<span>${goods.partName}</span>-->
        <#--位置编号-->
        <#--<span id="epcIndex"></span>-->
    <#--</div>-->

    <div id="partBody-div" class="part-body-left">
        <div class="part-title">适配车型</div>
        <div id="partLeft"></div>
        <div class="part-title">适配车款</div>
        <div id="partLeftDown"></div>
    </div>

    <div id="partMiddle" class="part-body-middle">
        <table class="detail-table">
            <tbody id="goodsDetail">
            <tr>
                <td>配件OE码<span id="oeNumber" data-title="${goods.oeNumber}">${goods.oeNumber}</span></td>
                <td>配件名称<span id="partName" data-title="${goods.partName}">${goods.partName}</span></td>
            </tr>
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
            <a id="showPicGoods" href="#">查看本图相关配件</a>
        </div>
        <div>
            <img id="bigPicture" title="点击查看原图" class="big-picture"
                 src="/img/no-pic-big.png" onerror="javascript:this.src='/img/no-pic-big.png';this.onerror=null;">
        </div>
    </div>

    <div id="partRight" class="part-body-right">
        <div class="part-title">
            报价信息
        </div>
        <div class="row buy-condition">
            <div class="part-quality-filter">筛选:</div>
            <div id="chooseQuality"></div>
        </div>
        <div id="offerInfoContent" class="offering-info-content"></div>
    </div>
</div>

    <#include "/epc-dev/cloudepc/common/pagePart/bottom.ftl">
    <#include "/epc-dev/cloudepc/common/pagePart/popup.ftl"/>
    <#include "/epc-dev/cloudepc/common/resource/js.ftl"/>
    <#include "/epc-dev/cloudepc/template/template.ftl"/>

</body>

<!-- build:js ../resources/goods/goodsDetail.js -->
<script src="/epc-dev/resources/common/other/template.js" type="text/javascript"></script>
<script src="/epc-dev/resources/common/epc/pageTop.js" type="text/javascript"></script>
<script src="/epc-dev/resources/common/epc/shopping/shopping.js" type="text/javascript"></script>
<script src="/epc-dev/resources/goods/goodsDetail.js" type="text/javascript"></script>
<!-- endbuild -->
</html>

</#escape>
