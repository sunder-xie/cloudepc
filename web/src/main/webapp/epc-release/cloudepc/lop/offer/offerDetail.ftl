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

    <link rel="stylesheet" href="/epc-release/resources/lop/offer/offerDetail-9f2f57218f.css">

</head>
<body>

    <#include "/epc-release/cloudepc/common/pagePart/topWish.ftl">

    <div id="mainPage" class="main-div w1190">
        <div class="div-title">
            <span class="float-left">订单详情</span>
            <span class="float-right">
                <a href="/wish/myOfferPage">[ 返回 ]</a>
            </span>
        </div>
        <#if orderInfo.orderStatus == 'BDFK'>
            <div class="div-status">
                <div class="div-status-left">
                    <p style="font-size: 14px;color: black;margin-bottom: 6px!important;">订单状态为 : 已拍下商品，等待您的付款</p>
                    <p>请及时进行支付，如果在<span class="div-total-price-color"> 24小时 </span>以内未支付订单将会被取消</p>
                </div>
                <div class="div-status-right">
                    <button type="button" class="pay-btn" onclick="window.location.href='/wish/pay/selectPayment?orderSn=${orderInfo.offerListSn}'">立即支付</button>
                    <button type="button" class="cancel-btn" data-order-id="${orderInfo.id}" data-wish-id="${orderInfo.wishListId}">取消订单</button>
                </div>
            </div>
        </#if>
        <#if orderInfo.orderStatus == 'BYFK'>
            <div class="div-status">
                <div class="div-status-left">
                    <p style="font-size: 14px;color: black;margin-bottom: 6px!important;">订单状态为 : 等待商家发货</p>
                    <p>请耐心等待商家发货，如果在<span class="div-total-price-color"> 24小时 </span>以内或者约定时间内，请及时联系供应商或汽配管家客服
                        <span class="div-total-price-color"> 400-9937-288 </span>
                    </p>
                </div>
            </div>
        </#if>
    <#if orderInfo.orderStatus == 'BYFH'>
        <div class="div-status">
            <div class="div-status-left">
                <p style="font-size: 14px;color: black;margin-bottom: 6px!important;">订单状态为 : 商家已发货</p>
                <p style="margin-bottom: 6px!important;">商品已在路上，请耐心等待</p>
                <p>温馨提示 : 收到货，请当场检查验收.若未收到货请及时联系商家或汽配管家客服
                    <span class="div-total-price-color"> 400-9937-288 </span>
                </p>
            </div>
        </div>
    </#if>
    <#if orderInfo.orderStatus == 'BYQS'>
        <div class="div-status">
            <div class="div-status-left">
                <p style="font-size: 14px;color: black;margin-bottom: 6px!important;">订单状态为 : 您的商品已签收</p>
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
                            <#--<#if displayFlag == 1>-->
                                <#switch orderInfo.orderStatus>
                                    <#case "BYQX">已取消<#break>
                                    <#case "XYQX">已取消<#break>
                                    <#case "BDFK">待付款<#break>
                                    <#case "BYFK">待发货<#break>
                                    <#case "BBFFH">部分发货<#break>
                                    <#case "BYFH">全部发货<#break>
                                    <#case "BYQS">已签收<#break>
                                    <#case "BYJS">已签收<#break>
                                    <#case "BYTH">已退货<#break>
                                    <#case "BYTK">已退款<#break>
                                    <#case "JYGB">交易关闭<#break>
                                    <#case "TKWCDJS">交易关闭<#break>
                                    <#default>
                                </#switch>
                            <#--<#else>-->
                            <#--${orderInfo.orderStatusName}-->
                            <#--</#if>-->
                        </td>
                        <td>订单号&nbsp;:&nbsp;${orderInfo.offerListSn}</td>
                        <td>订单时间&nbsp;:&nbsp;${orderInfo.createTime}</td>
                    </tr>
                    <tr>
                        <td>&nbsp;</td>
                        <td>是否带票&nbsp;:&nbsp;
                            <#switch orderInfo.receipt>
                                <#case "1">普通发票<#break>
                                <#case "2">增值发票<#break>
                                <#default>不带票
                            </#switch>
                        </td>
                        <td>支付方式&nbsp;:&nbsp;${orderInfo.payName}
                            <img src="/img/lop/center_guarantee.png"><span style="color: #ff7800;"> 淘汽担保支付</span>
                        </td>
                        <td>商家信息&nbsp;:&nbsp;${orderInfo.sellerName}</td>
                    </tr>
                    <tr>
                        <td>&nbsp;</td>
                        <td>商家电话&nbsp;:&nbsp;${orderInfo.sellerTel}</td>
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                    </tr>
                    </tbody>
                </table>
            </div>
            <div class="div-info-box">
                <table class="wish-table">
                    <tbody>
                    <tr>
                        <td>我的信息</td>
                        <td>门店名称&nbsp;:&nbsp;${orderInfo.companyName}</td>
                        <td>联系人&nbsp;:&nbsp;${orderInfo.consignee}</td>
                        <td>联系电话&nbsp;:&nbsp;${orderInfo.telephone}</td>
                    </tr>
                    <tr>
                        <td>&nbsp;</td>
                        <td>收货地址&nbsp;:&nbsp;${orderInfo.address}</td>
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                    </tr>
                    </tbody>
                </table>
            </div>
            <div class="div-info-box" style="border-bottom: none;">
                <table class="wish-table" style="border-bottom: none;">
                    <tbody>
                    <tr>
                        <td>我的信息</td>
                        <td>车架号/VIN码&nbsp;:&nbsp;${orderInfo.vin}</td>
                        <td>车型&nbsp;:&nbsp;${orderInfo.carModelName}</td>
                        <td>车辆备注&nbsp;:&nbsp;${orderInfo.isModifiedVehicle}</td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
        <div>
            <table class="goods-table">
                <thead>
                <tr class="goods-table-tr">
                    <td style="width: 35%;" class="text-align-left"><span class="goods-info-td">商品信息</span></td>
                    <td style="width: 20%;">零件号</td>
                    <td style="width: 15%;">数量</td>
                    <td style="width: 15%;">单价</td>
                    <td style="width: 15%;">商品状态</td>
                </tr>
                </thead>
                <tbody>
                    <#list orderInfo.offerListGoodsList as goods>
                    <tr>
                        <td>
                            <div class="goods-img-div">
                                <img class="goods-img" src="${goods.shippingImage}" onerror="javascript:this.src='/img/no-pic-big.png';this.onerror=null;">
                            </div>
                            <div class="goods-detail">
                                <div class="goods-name">${goods.goodsName}</div>
                                <div>
                                    <#if goods.goodsBrandId == -1>
                                        品质：${goods.goodsQualityTypeStr}
                                    <#else>
                                        品牌：${goods.goodsBrand}
                                    </#if>
                                </div>
                            </div>
                        </td>
                        <td>
                            ${goods.goodsOe}
                        </td>
                        <td>
                            ${goods.goodsNumber}
                        </td>
                        <td>
                            ￥${goods.goodsPrice}
                        </td>
                        <td>
                            ${goods.offerGoodsStatus}
                        </td>
                    </tr>
                    </#list>
                </tbody>
            </table>
        </div>
        <div class="div-total">
            <#if orderInfo.orderStatus == 'BYFK'>
            <div class="div-total-right">
                实付款&nbsp;:&nbsp;<strong class="div-total-price-color">¥&nbsp;${orderInfo.paidOfferAmount}元</strong>
            </div>
            </#if>
            <div class="div-total-right">
                商品总价&nbsp;:&nbsp;
                <strong class="div-total-price-color">¥&nbsp;${orderInfo.offerAmount}元</strong>
            </div>
        </div>
    </div>

    <#include "/epc-release/cloudepc/common/pagePart/bottom.ftl">
    <#include "/epc-release/cloudepc/common/pagePart/popup.ftl"/>
    <#include "/epc-release/cloudepc/common/resource/js.ftl"/>
    <#include "/epc-release/cloudepc/lop/wish/cancelWish.ftl"/>

</body>
<!-- END BODY -->
<script src="/epc-release/resources/lop/offerDetail-9048bfe348.js"></script>

</html>

</#escape>
