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

    <!-- build:css ../resources/lop/offer/offerDetail.css -->
    <link href="/epc-dev/resources/lop/offer/offerDetail.css" rel="stylesheet" type="text/css">
    <!-- endbuild -->

    <style type="text/css">
        .refresh {
            margin-right: 10px;
        }

        .order-detail-adaptive-car {
            float: right;
            margin-left: 100px;
        }

        .part-title {
            font-size: 14px;
            color: black;
        }

        .confirm-receive-btn {
            width: 90px;
            height: 25px;
            margin-top: 25px;
            margin-right: 20px;
            color: white;
            line-height: 20px;
            border: none;
            border-radius: 2px;
            background: #00AADD
        }

    </style>

</head>
<body>

    <#include "/epc-dev/cloudepc/common/pagePart/topWish.ftl">

<div id="mainPage" class="main-div w1190">
    <div class="div-title">
        <span class="float-left">订单详情</span>

        <span class="float-right">
            <a class="refresh" onclick="location.reload()">刷新</a>
            <a href="/buy/order/myOrderPage">[ 返回 ]</a>
        </span>
    </div>

    <#if orderInfo.orderStatus == 0 >
        <div class="div-status">
            <div class="div-status-left">
                <p class="part-title">订单状态为 : 已拍下商品，等待您的付款</p>
                <p>请及时进行支付</p><#--，如果在<span class="div-total-price-color"> 24小时 </span>以内未支付订单将会被取消-->
            </div>
            <div class="div-status-right">
                <button type="button" class="pay-btn" onclick="window.location.href='/part/pay/selectPayment?orderSn=${orderInfo.orderSn}'">立即支付</button>
                <button type="button" class="cancel-btn" onclick="ORDER_DETAIL.cancelOrder('${orderInfo.orderSn}')">取消订单</button>
            </div>
        </div>
    </#if>

    <#--已取消不需要显示这一块-->

    <#if orderInfo.orderStatus == 2>
        <div class="div-status">
            <div class="div-status-left">
                <p class="part-title">订单状态为 : 等待商家发货</p>
                <p>
                    请耐心等待商家发货，如果在
                    <span class="div-total-price-color"> 24小时 </span>
                    以内或者约定时间内未发货，请及时联系供应商或汽配管家客服
                    <span class="div-total-price-color"> 400-9937-288 </span>
                </p>
            </div>
        </div>
    </#if>

    <#if orderInfo.orderStatus == 3>
        <div class="div-status">
            <div class="div-status-left">
                <p class="part-title">订单状态为 : 商家已发货</p>
                <p style="margin-bottom: 6px!important;">商品已在路上，请耐心等待</p>
                <p>
                    温馨提示 : 收到货，请当场检查验收.若未收到货请及时联系商家或汽配管家客服
                    <span class="div-total-price-color"> 400-9937-288 </span>
                </p>
            </div>
            <div class="div-status-right">
                <button type="button" class="confirm-receive-btn" onclick="ORDER_DETAIL.confirmReceive('${orderInfo.orderSn}')">确认收货</button>
            </div>
        </div>
    </#if>

    <#if orderInfo.orderStatus == 4 || orderInfo.orderStatus == 5>
        <div class="div-status">
            <div class="div-status-left">
                <p class="part-title">订单状态为 : 您的商品已签收</p>
                <p>如果商品有问题请及时联系商家或汽配管家客服
                    <span class="div-total-price-color"> 400-9937-288 </span>
                </p>
            </div>

        </div>
    </#if>

    <div class="div-info">
        <div class="div-info-box">
            <table class="wish-table">
                <tbody>
                <tr>
                    <td>订单信息</td>
                    <td>订单状态&nbsp;:&nbsp;
                        <#switch orderInfo.orderStatus>
                            <#case 0>待付款<#break>
                            <#case 1>已取消<#break>
                            <#case 2>待发货<#break>
                            <#case 3>已发货<#break>
                            <#case 4>已签收<#break>
                            <#case 5>已签收<#break>
                            <#default>
                        </#switch>
                    </td>
                    <td>订单号&nbsp;:&nbsp;${orderInfo.orderSn}</td>
                    <td>订单时间&nbsp;:&nbsp;${orderInfo.gmtCreate?string('yyyy-MM-dd HH:mm:ss')}</td>
                </tr>
                <tr>
                    <td>&nbsp;</td>
                    <td>是否带票&nbsp;:&nbsp;${orderInfo.invTypeName}</td>
                    <td>支付方式&nbsp;:&nbsp;${orderInfo.payName}
                        <img src="/img/lop/center_guarantee.png"><span style="color: #ff7800;"> 淘汽担保支付</span>
                    </td>
                    <td>配送信息&nbsp;:&nbsp;${orderInfo.shippingName}</td>
                </tr>
                <tr>
                    <td>&nbsp;</td>
                    <td>商家信息&nbsp;:&nbsp;${orderInfo.sellerCompanyName}</td>
                    <td>商家电话&nbsp;:&nbsp;${orderInfo.sellerTelephone}</td>
                    <td>&nbsp;</td>
                </tr>
                </tbody>
            </table>
        </div>
        <div class="div-info-box" <#if orderInfo.orderExtendBO??><#else> style="border-bottom: none"</#if>>
            <table class="wish-table">
                <tbody>
                <tr>
                    <td>我的信息</td>
                    <td>
                        门店名称&nbsp;:&nbsp;${orderInfo.companyName}
                    </td>
                    <td>
                        联系人&nbsp;:&nbsp;${orderInfo.consignee}
                    </td>
                    <td>
                        联系电话&nbsp;:&nbsp;${orderInfo.mobile}
                    </td>
                </tr>
                <tr>
                    <td>&nbsp;</td>
                    <td>
                        收货地址&nbsp;:&nbsp;
                        ${orderInfo.provinceName}${orderInfo.cityName}${orderInfo.districtName}${orderInfo.streetName}${orderInfo.address}
                    </td>
                    <td>
                        订单备注&nbsp;:&nbsp;
                        ${orderInfo.orderNote}
                    </td>
                    <td>&nbsp;</td>
                </tr>
                </tbody>
            </table>
        </div>
        <#if orderInfo.orderExtendBO??>
        <div class="div-info-box" style="border-bottom: none">
            <table class="wish-table">
                <tbody>
                <tr>
                    <td>发货信息</td>
                    <td>物流公司&nbsp;:&nbsp;${orderInfo.orderExtendBO.shippingCompany}</td>
                    <td>物流单号&nbsp;:&nbsp;${orderInfo.orderExtendBO.shippingNo}</td>
                    <td></td>
                </tr>
                <tr>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                </tr>
                </tbody>
            </table>
        </div>
        </#if>
    </div>
    <div>
        <table class="goods-table">
            <thead>
            <tr class="goods-table-tr">
                <td style="width: 55%;" class="text-align-left"><span class="goods-info-td">商品信息</span></td>
                <td style="width: 15%;">数量</td>
                <td style="width: 15%;">单价</td>
                <td style="width: 15%;">商品状态</td>
            </tr>
            </thead>
            <tbody>
                <#list orderInfo.orderGoodsBOs as goods>
                <tr>
                    <td>
                        <div class="goods-img-div">
                            <img class="goods-img" src="${goods.goodsImg}" onerror="javascript:this.src='/img/no-pic-big.png';this.onerror=null;">
                        </div>

                        <div class="goods-detail">
                            <div class="goods-name">${goods.partName}</div>

                            <div class="oe-num">
                                OE码：${goods.oeNumber}
                                <button class="adaptive-car order-detail-adaptive-car" onclick="ADAPTIVE_CAR.alertAdaptiveCarByOeNum('${goods.oeNumber}')">
                                    适配车型
                                </button>
                            </div>

                            <div>
                                品质：
                                <#if goods.brandName == ''>
                                    ${goods.goodsQuality}
                                <#else>
                                    品牌（${goods.brandName}）
                                </#if>
                            </div>
                        </div>
                    </td>
                    <td>${goods.goodsNumber}</td>
                    <td>${goods.soldPrice?string.currency}</td>
                    <td></td>
                </tr>
                </#list>
            </tbody>
        </table>
    </div>
    <div class="div-total">
        <div class="div-total-right">
            商品总价&nbsp;:&nbsp;
            <strong class="div-total-price-color">&nbsp;${orderInfo.goodsAmount?string.currency}元</strong>
        </div>
    </div>
</div>

    <#include "/epc-dev/cloudepc/common/pagePart/bottom.ftl">
    <#include "/epc-dev/cloudepc/common/pagePart/popup.ftl"/>
    <#include "/epc-dev/cloudepc/common/resource/js.ftl"/>
    <#include "/epc-dev/cloudepc/part/cancelOrder.ftl"/>
    <#include "/epc-dev/cloudepc/part/adaptiveCar.ftl"/>

</body>

<!-- build:js ../resources/part/orderDetail.js -->
<script src="/epc-dev/resources/common/other/template.js" type="text/javascript"></script>
<script src="/epc-dev/resources/common/epc/pageTop.js" type="text/javascript"></script>
<script src="/epc-dev/resources/part/cancelOrder.js" type="text/javascript"></script>
<script src="/epc-dev/resources/part/adaptiveCar.js" type="text/javascript"></script>
<script src="/epc-dev/resources/part/orderDetail.js" type="text/javascript"></script>
<!-- endbuild -->

</html>

</#escape>