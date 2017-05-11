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

    <!-- build:css ../resources/part/pay/payment.css -->
    <link href="/epc-dev/resources/part/pay/payment.css" rel="stylesheet" type="text/css">
    <!-- endbuild -->

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
        .border-none{
            border:none!important;
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
            height:25px;
            line-height: 25px;
            color: #02aadb;
            text-align: left;
            padding-left: 15px;
            border-bottom:1px solid #d6e5ec;
            background-color: #f1f1f1;
        }
        .content-div{
            margin:0 auto;
            overflow: hidden;
            padding: 30px 0;
            border-bottom: 1px solid #d6e5ec;
        }
        .content-div-text{
            /*padding-left: 12%;*/
        }
        .content-div-set{
            padding: 30px 170px;
        }
        .content-names{
            float: left;
        }
        .content-form{
            float: left;
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
            border: 1px solid #d6e5ec;
        }
        .goods-item {
            line-height: 40px;
            overflow: hidden;
            margin-bottom: 0;
            -webkit-padding-start: 0px;
        }
        .goods-item-set{
            margin: 20px 0px 0 0px!important;
            background-color: #F3FBFD;
            border: 1px solid #d6e5ec;
            padding-left: 30px!important;
        }
        .goods-item-ext{
            padding: 10px 0;
            line-height: 22px;
            margin-left: 20px!important;
        }

        .goods-item li{
            float: left;
        }
        .bottom-div{
            width: 100%;
            overflow: hidden;
        }
        .foot{
            height:47px;
            line-height: 48px;
            border-bottom:1px solid #d6e5ec;
        }
        .foot span{
            float: right;
            font-weight: bolder;
        }
        .ok-btn {
            width: 140px;
            height: 46px;
            line-height: 45px;
            font-size: 16px;
            font-weight: lighter;
            text-align: center;
            margin-left: 25px;
            color: #fff;
            background-color: #ff9900;
            border: 0;
            float: right;
        }
        .border-set, .payment-selected, .shipping-selected{
            border:1px solid #ff9900 !important;
        }
        .border-top-set{
            border-top:1px solid #d6e5ec !important;
        }
        .content-form-img{
            margin: 6px 10px 6px 0px;
            border: 1px solid white;
            cursor: pointer;
        }
        .content-form-shipping{
            width: 177px;
            height: 60px;
            line-height: 59px;
            margin-right: 12px;
            font-size: 18px;
            float: left;
            border: 1px solid #E0E0E0;
        }

        /* hzt增加 */
        .content-table{
            width: 100%;
        }
        .content-table td{
            padding: 5px 0;
        }
        .content-table td:nth-child(1){text-align: right;width:240px;}
        .content-table td:nth-child(2){text-align: left;width: auto;}
        .content-table td:nth-child(3){text-align: left;width: 240px;}
        .content-table input{
            /*width: 200px;*/
            padding: 5px;
        }
        .content-table div{
            text-align: left;
            padding-left: 5px;
        }
        .purchase-step{
            background: url(/img/order_pay_steps_2.png) no-repeat;
        }
        .seller-name {
            margin-left: 20px;
            padding: 3px 8px 3px 26px;
            background: url('/img/lop/im_icon.png') no-repeat 5px 3px #02aadb;
            color: #fff;
            cursor: pointer;
            border-radius: 3px;
        }
        .seller-info{
            height: 50px;
            margin:0 15px;
            line-height: 50px;
            text-align: left;
            border: 1px solid #d6e5ec;
            border-top:none;
        }
        .seller-info-ext{
            padding-left: 30px;
            height: 60px;
            line-height: 60px;
        }
        .remark-btn{
            height: 28px;
            width: 90%;
            padding-left: 5px;
            border-radius: 2px!important;
        }
        .goods-info-box{
            width: 100%;
            height: 80px;
            margin: 0 0 0 10px;
            border-right: 1px solid #ddd;
            position: relative;
        }

        .goods-info-img{
            width: 80px;
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
        .goods-oe{
            float: left;
            height: 22px;
            color: #B2CCE2;
            line-height: 22px;
        }
        dd li{
            height:80px;
            line-height: 80px;
        }
        .content-div-left{
            text-align: left;
            padding-left: 40px;
        }
        .content-div-left span{
            line-height: 20px;;
        }
        .content-div-left input{
            margin-left: 20px;;
        }
        .address-set{
            line-height: 27px;
            cursor: pointer;
        }
        .address-set:hover{
            background-color: rgba(250,235,215,0.45);
        }
        .address-background-set{
            background-color: antiquewhite;
        }
        #addressList span{
            font-size: 13px;
        }
        .triangle_border_up{
            width:0;
            height:0;
            border-width:0 30px 30px;
            border-style:solid;
            border-color:transparent transparent #333;/*透明 透明  灰*/
            margin:40px auto;
            position:relative;
        }
        .input-width{
            width: 350px;
        }

    </style>
</head>
<body>
    <#include "/epc-dev/cloudepc/common/pagePart/header.ftl"/>
    <input type="hidden" id="sellerList" value='${sellerList}'/>
    <input type="hidden" id="addressList" value='${addressJson}'/>
    <#--<input type="hidden" id="idStr" value="${idStr}"/>-->
    <input type="hidden" id="buyNow" value="${buyNow}"/>

<div class="content-wrapper w1190">
    <div class="pc-header cl">
        <div class="fl">
            <div class="fl gj-logo" onclick="location.href='/home'"></div>
            <div class="fl purchase-tip">结算页</div>
        </div>
        <div class="fr purchase-step"></div>
    </div>
    <h4 class="ta-l purchase-title">核对订单信息</h4>

    <div class="content">
        <div class="content-nav">收货人信息</div>
        <div class="content-div content-div-text" >
            <table class="content-table" >
                <tbody>
                <tr>
                    <td><b class="text-red">*&nbsp;</b>门店名称&nbsp;：</td>
                    <td>
                        <input type="text" class="input-width readonly-style" readonly="readonly" name="supplier_name" id="supplier_name" value="${CURRENT_USER.shopBO.companyName}">
                    </td>
                    <td></td>
                </tr>
                <#if addressList??>
                    <#list addressList as address>
                    <tr>
                        <td><#if address_index == 0><b class="text-red">*&nbsp;</b>收货地址&nbsp;：</#if></td>
                        <td>
                            <div <#if address_index == 0>class="address-set address-background-set"<#else>class="address-set" </#if>onclick="chooseAddress(this)">
                                <input type="radio" name="address" id="${address.id}" <#if address_index == 0>checked="checked"</#if>style="margin-right: 10px;margin-left: 5px;">
                                <span>${address.provinceName}&nbsp;&nbsp;${address.cityName}&nbsp;&nbsp;${address.districtName}&nbsp;&nbsp;
                                ${address.streetName}&nbsp;&nbsp;${address.address}&nbsp;&nbsp;(${address.contactsName}&nbsp;收)&nbsp;${address.mobile}</span>
                            </div>
                        </td>
                        <td>
                            <#if address_index == 0 && address.isDefault==1>
                                <span style="margin-left: 20px;color: #02aadb;">默认地址</span>
                            </#if>
                        </td>
                    </tr>
                    </#list>
                <#else>
                    <tr>
                        <td><b class="text-red">*&nbsp;</b>收货地址&nbsp;：</td>
                        <td>
                            <span>暂无可用收货地址，请联系<i style="color: red;">400-9937-288</i></span>
                        </td>
                        <td></td>
                    </tr>
                </#if>
                </tbody>
            </table>
        </div>
        <div class="content-nav">支付及配送方式</div>
        <div class="content-div content-div-set">
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
                <!--支付方式选择-->
                <div style="overflow: hidden;" id="paymentList">
                    <#list paymentList as payment>
                    <img src="${payment.url}" <#if payment_index == 0>class="content-form-img payment-selected"<#else>class="content-form-img"</#if>
                         onclick="choosePayType(this)" data-value="${payment.id}" data-name="${payment.title}" data-code="${payment.code}" javascript:this.src='/img/no-pic-big.png';this.onerror=null;">
                    </#list>
                </div>
                <!--配送方式选择-->
                <div style="overflow: hidden;" id="shipping">
                    <#list shippingConfigList as shipping>
                    <div data-value="${shipping.id}" data-name="${shipping.shippingName}" <#if shipping_index == 0>class="content-form-shipping shipping-selected"<#else>class="content-form-shipping"</#if> onclick="chooseShippingType(this)">
                    ${shipping.shippingName}
                    </div>
                    </#list>  
                </div>
            </div>
        </div>
        <div class="content-nav">确认商品信息</div>
        <div class="bottom-div">
            <dl class="goods-list">
                <dt>
                <ul class="goods-item goods-item-set">
                    <li style="width: 50%;text-align: left;">商品名称</li>
                    <li style="width: 20%;">单价(元)</li>
                    <li style="width: 20%;">数量</li>
                    <li style="width: 10%;">金额(元)</li>
                </ul>
                </dt>
                <#if result.success == true>
                <#list result.data as seller>
                    <div class="seller-info border-none">
                        <span class="seller-company-img"></span>
                        ${seller.sellerCompanyName}
                        <span class="seller-im-icon seller-chat" data-orgid="${seller.sellerId}">和我联系</span>
                    </div>
                    <dd id="${seller.sellerId}">
                        <#list seller.goodsList as goods>
                        <ul class="goods-item goods-item-ext" <#if goods_has_next>style="border-bottom: 1px solid #d6e5ec;" </#if>data-value="XX">
                            <li style="width: 50%;text-align: left;">
                                <div class="goods-info-box" style="border-right: none;">
                                    <div class="goods-info-img">
                                        <img src="${goods.goodsImg}" class="goods-info-img-set" onerror="javascript:this.src='/img/no-pic-big.png';this.onerror=null;" title="点击查看原图" >
                                    </div>
                                    <div class="goods-info-text">
                                        <h5 style="margin-top: 5px;margin-bottom: 2px;font-size: 14px;">
                                            ${goods.partName}
                                        </h5>
                                        <div  class="goods-info-text-con">
                                            <div class="goods-oe">${goods.oeNumber}</div>
                                        </div>

                                        <div  class="goods-info-text-con" style="text-align: left;">
                                            品质&nbsp;:&nbsp;
                                        <#if goods.brandName==null || goods.brandName==''>
                                            ${goods.goodsQuality}
                                        <#else>
                                            品牌（${goods.brandName}）
                                        </#if>
                                        </div>
                                    </div>
                                </div>
                            </li>
                            <li style="width: 20%;">￥<span id="goodsPrice${goods.id}">${goods.goodsPrice?string('0.00')}</span></li>
                            <li style="width: 20%;">${goods.goodsNumber}</li>
                            <li style="width: 10%;color: #ff9900;">￥<span id="goodsPriceAmount${goods.id}" class="singleTotal">${(goods.goodsPrice*goods.goodsNumber)?string('0.00')}</span></li>
                        </ul>
                        </#list>
                    </dd>
                    <div class="seller-info seller-info-ext">
                        <span>给商家留言&nbsp;:&nbsp;</span>
                        <input id="order-note-${seller.sellerId}" class="remark-btn" placeholder="填写对本次交易的备注说明,不超过40个字" maxlength="40" type="text" value="">
                    </div>
                </#list>
                <#else>
                <div>数据异常</div>
                </#if>
            </dl>
            <div class="content-nav border-top-set">发票信息</div>
            <div class="content-div content-div-left" style="padding-left: 18px;">
                <input type="radio" name="receipt" data-id="0" data-name="不需要发票" checked="checked" onclick="noTaxRate()">&nbsp;&nbsp;&nbsp;&nbsp;不需要发票
                <input type="radio" name="receipt" data-id="1" data-name="开普通发票" onclick="getTaxRate()">&nbsp;&nbsp;&nbsp;&nbsp;开普通发票
                <input type="radio" name="receipt" data-id="2" data-name="开增值发票" onclick="addTaxPrice()">&nbsp;&nbsp;&nbsp;&nbsp;开增值税发票
            </div>
            <div class="content-nav">其他说明</div>
            <div class="content-div">
                <div class=" content-div-left" style="margin-bottom: 15px;">
                    <span>一、运费说明</span><br/>
                    <span>商品单价默认为不含运费价格，您需在收到货物时额外支付运费金额</span><br/>
                </div>
                <div class=" content-div-left">
                    <span>二、交易说明</span><br/>
                    <span>由淘汽档口平台对会员发放的卡劵，积分，红包等优惠，暂时无法在汽配管家使用，与您带来的不便，敬请谅解</span>
                </div>
            </div>
            <div class="foot">
                <button class="ok-btn" onclick="confirm()">提交订单</button>
                <span>实付款&nbsp;:&nbsp;<strong style="color:#ff9900;font-size: 16px;">￥<span id="total">0.00</span></strong></span>
            </div>
        </div>
    </div>

</div>

    <#include "/epc-dev/cloudepc/common/pagePart/bottom.ftl">
    <#include "/epc-dev/cloudepc/common/pagePart/popup.ftl"/>
    <#include "/epc-dev/cloudepc/common/resource/js.ftl"/>
    <#include "/epc-dev/cloudepc/template/partTemplate.ftl">

</body>

<!-- build:js ../resources/part/consignee.js -->
<script src="/epc-dev/resources/common/other/template.js" type="text/javascript"></script>
<script src="/epc-dev/resources/part/consignee.js" type="text/javascript"></script>
<!-- endbuild -->
</html>

</#escape>
