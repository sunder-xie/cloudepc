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

    <link rel="stylesheet" href="/epc-release/resources/lop/pay/payment-97d3e10533.css">

    <style type="text/css">
        .overflow-hidden{
            overflow: hidden;
        }
        .top{
            height: 110px;
            margin: 0px 200px;
        }
        .top ul,.content ul{
            list-style-type: none;
            -webkit-padding-start: 0px;
            margin:0px 10px;
            height: 100%;
        }
        .top-two{
            text-align: left;
            height: 40px;
            line-height: 40px;
            font-size: 14px;
            color: black;
        }
        .top-left{
            line-height: 55px;
            float: left;
            margin:10px 0px;
        }
        .top-middle{
            float: left;
            border-left: 1px solid #d6e5ec;
            height: 30px;
            line-height: 30px;
            margin-top: 23px;
            margin-left: 10px;
            padding-left: 10px;
        }
        .top-right{
            margin-top: 15px;
            float: right;
            width: 50%;
        }
        .top-right li{
            height: 100%;
            width: 25%;
            float:left;
        }
        .top-model-close {
            top: 7px;
            right: 6px;
            height: 17px;
            width: 17px;
            font-size: 13px;
            text-align: center;
            padding: 0;
            border-radius: 16px !important;
            z-index: 1200;
        }
        .top-model-close-a {
            position: static!important;
            color: #FFF;
            background-color: #02aadb;
            margin-left: 5px;
            margin-top: -18px;
        }
        .top-model-background-color{
            background-color: #C6D8E8;
        }
        .text-red{
            color: red;
        }
        .content{
            border:1px solid #d6e5ec;
            /*margin: 0px 200px 25px 200px;*/
            overflow: hidden;
            border-bottom: none;
        }
        .content label{
            font-weight: 100;
            font-size: 12px;
            margin:3px 0px;
        }
        .content input{
            border:1px solid #DDDDDD;
            border-radius: 1px;
        }
        .content-nav{
            width: 100%;
            height:25px;
            line-height: 25px;
            color: #02aadb;
            text-align: left;
            padding-left: 15px;
            border-bottom:1px solid #d6e5ec;
            background-color: #f1f1f1;
        }
        .content-div{
            width: 100%;
            overflow: hidden;
            padding:10px 0px;
            border-bottom: 1px solid #d6e5ec;
        }
        .content-names{
            width:25%;
            float: left;
        }
        .content-form{
            width:75%;
            float: right;
            overflow: hidden;
            text-align: left;
        }
        .name-div{
            overflow: hidden;
            padding:8px 0px;
        }
        .pay-div{
            overflow: hidden;
            padding:25px 0px;
        }
        .name-div input{
            width:150px;
            padding-left: 5px;
        }
        .width-set{
            width: 490px !important;
        }
        .name-div select{
            width:110px;
        }
        .name-div-left{
            float: left;
        }
        .name-div-right{
            float: right;
        }
        .content-form img{
            float: left;
        }

        .goods-list dt {
            height: 40px;
            font-weight: lighter;
            margin:0px 15px;
        }
        .goods-list dd {
            margin: 0px 15px;
            background-color: #F3FBFD;
            border: 1px solid #d6e5ec;
        }
        .goods-item {
            line-height: 40px;
            overflow: hidden;
            margin-bottom: 0;
            -webkit-padding-start: 0px;
        }

        .goods-item li{
            float: left;
        }
        .bottom-div{
            width: 100%;
            overflow: hidden;
        }
        .foot{
            height:35px;
            line-height: 35px;
            border-top:1px solid #d6e5ec;
            border-bottom:1px solid #d6e5ec;
        }
        .foot span{
            float: right;
            font-weight: bolder;
        }
        .ok-btn {
            width: 120px;
            height: 34px;
            line-height: 30px;
            font-size: 15px;
            font-weight: lighter;
            text-align: center;
            margin-left: 25px;
            color: #fff;
            background-color: #ff7800;
            border: 0;
            float: right;
        }
        .border-set{
            border:1px solid #ff7800!important;
        }
        .content-form-img{
            margin: 6px 10px 6px 0px;
            border: 1px solid white;
            cursor: pointer;
        }


        /* hzt增加 */
        .content-table{
            margin-left: 200px;
        }
        .content-table td{
            padding: 5px 0;
        }
        .content-table td:nth-child(1){text-align: right}
        .content-table td:nth-child(2){text-align: left}
        .content-table input{
            width: 200px;
            padding: 5px;
        }
        .content-table select{
            width: 120px;
        }

        .purchase-step{
            background: url(/img/lop/center_steps_2.png) no-repeat;
        }

    </style>
</head>
<body>
    <#include "/epc-release/cloudepc/common/pagePart/header.ftl"/>

<div class="content-wrapper w1190">
    <div class="pc-header cl">
        <div class="fl">
            <div class="fl gj-logo" onclick="location.href='/home'"></div>
            <div class="fl purchase-tip">结算页</div>
        </div>
        <div class="fr purchase-step"></div>
    </div>
    <h4 class="ta-l purchase-title">填写核对订单信息</h4>

    <div class="content">
        <div class="content-nav">收货人信息</div>
        <div class="content-div">
            <table class="content-table">
                <tbody>
                <tr>
                    <td><b class="text-red">*&nbsp;</b>门店名称&nbsp;：</td>
                    <td>
                        <input type="text" class="input-width" name="supplier_name" id="supplier_name" value="${CURRENT_USER.shopBO.companyName}">
                    </td>
                </tr>
                <tr>
                    <td><b class="text-red">*&nbsp;</b>联系人&nbsp;：</td>
                    <td>
                        <input type="text" class="input-width" name="wishListMaker" id="wishListMaker" value="">
                    </td>
                </tr>
                <tr>
                    <td><b class="text-red">*&nbsp;</b>联系电话&nbsp;：</td>
                    <td>
                        <input readonly="readonly" type="text" class="input-width readonly-style" name="mobile" id="mobile" value="" >
                    </td>
                </tr>
                <tr>
                    <td><b class="text-red">*&nbsp;</b>所在地区&nbsp;：</td>
                    <td>
                        <select id="province" onchange="chooseProvince(this)">
                            <option value="0">请选择省份</option>
                        </select>
                        <select id="city" onchange="chooseCity(this)">
                            <option value="0">请选择城市</option>
                        </select>
                        <select id="district" onchange="chooseDistrict(this)">
                            <option value="0">请选择区县</option>
                        </select>
                        <select id="street" >
                            <option value="0">请选择街道</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td><b class="text-red">*&nbsp;</b>街道地址&nbsp;：</td>
                    <td>
                        <input type="text" class=" width-set" name="address" id="address" value="">
                    </td>
                </tr>
                <tr>
                    <td>业务经理&nbsp;：</td>
                    <td>
                        <input placeholder="请填写手机号" type="text" id="saleTel" value="">
                    </td>
                </tr>
                </tbody>
            </table>

        </div>
        <div class="content-nav">支付及配送方式</div>
        <div class="content-div">
            <div class="content-names">
                <div class="pay-div">
                    <label class="name-div-right">
                        支付方式&nbsp;：
                    </label>
                </div>
                <div class="pay-div">
                    <label class="name-div-right">
                        配送方式&nbsp;：
                    </label>
                </div>
            </div>
            <div class="content-form">
                <div style="overflow: hidden;" id="paymentList">
                    <#list extra.paymentList as payment>
                        <img <#if payment_index == 0>src="${payment.url}" class="content-form-img border-set"<#else>src="${payment.url}"  class="content-form-img"</#if>
                             data-value="${payment.id}" data-code="${payment.code}" onclick="choosePayType(this)" javascript:this.src='/img/no-pic-big.png';this.onerror=null;">
                    </#list>
                </div>
                <div style="overflow: hidden;" id="paymentList">
                    <img src="/img/lop/center_tqmall_has.png" id="payType" data-value="${extra.shippingList[0].shippingId}" style="margin: 6px 10px 6px 0px;" javascript:this.src='/img/no-pic-big.png';this.onerror=null;">
                </div>
            </div>
        </div>
        <div class="content-nav">确认商品信息</div>
        <div class="bottom-div">
            <dl class="goods-list">
                <dt>
                <ul class="goods-item ">
                    <li style="width: 20%;">商品名称</li>
                    <li style="width: 20%;">适配车型</li>
                    <li style="width: 15%;">品质</li>
                    <li style="width: 15%;">品牌</li>
                    <li style="width: 10%;">数量</li>
                    <li style="width: 20%;">单价(含运费)</li>
                </ul>
                </dt>
                <dd>
                    <#list result.offerListDto.offerListGoodsList as goods>
                        <ul class="goods-item" style="padding: 10px 0;line-height: 22px;" name="goodsItems" data-value="${goods.id}">
                            <li style="width: 20%;">${goods.goodsName}</li>
                            <li style="width: 20%;">${result.carInfo}</li>
                            <li style="width: 15%;">${goods.goodsQualityTypeStr}</li>
                            <li style="width: 15%;">&nbsp;${goods.goodsBrand}</li>
                            <li style="width: 10%;">${goods.goodsNumber}${goods.goodsMeasureUnit}</li>
                            <li style="width: 20%;color: #ff7800;" >${goods.goodsPriceStr}</li>
                        </ul>
                    </#list>
                </dd>
            </dl>
            <div class="foot">
                <button class="ok-btn" id="${result.offerListDto.offerListSn}" data-wish-id="${result.wishListDto.id}" data-offer-id="${result.offerListDto.id}" onclick="confirm(this)">提交订单</button>
                <span>实付款&nbsp;:&nbsp;<strong style="color:#ff7800;">${result.offerListDto.offerAmountStr}元</strong></span>
            </div>
        </div>
    </div>

</div>

    <#include "/epc-release/cloudepc/common/pagePart/bottom.ftl">
    <#include "/epc-release/cloudepc/common/pagePart/popup.ftl"/>
    <#include "/epc-release/cloudepc/common/resource/js.ftl"/>

</body>
<!-- END BODY -->
<script src="/epc-release/resources/lop/pay/consignee-257c6b5036.js"></script>
</html>

</#escape>
