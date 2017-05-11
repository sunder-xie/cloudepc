<#-- 我的购物订单列表模板 -->
<script type="text/html" id="orderItemTemplate">
    {{each orderItems as orderItem idxOffer}}
    <div>
        <table border="1" style="width: 100%">
            <thead>
            <tr class="offer-base-info">
                <th colspan="7">
                    <span>{{orderItem.createTime}}</span>
                    <span>订单号 : {{orderItem.orderSn}}</span>
                </th>
            </tr>
            </thead>

            <tbody>
            {{each orderItem.orderGoodsList as goods idxGoods}}
            <tr class="offer-goods-item">
                <td width="35%">
                    <div class="goods-img-div">
                        <img src="{{goods.goodsImg}}" class="goods-img" onerror="javascript:this.src='/img/no-pic-big.png';this.onerror=null;">
                    </div>
                    <div class="goods-detail">
                        <div class="goods-name">{{goods.partName}}</div>
                        <div class="oe-num">
                            OE码：{{goods.oeNumber}}
                            <button class="adaptive-car my-order-adaptive-car" onclick="ADAPTIVE_CAR.alertAdaptiveCarByOeNum('{{goods.oeNumber}}')">
                                适配车型
                            </button>
                        </div>
                        <div>
                            品质：
                            {{if goods.brandName == ''}}
                            {{goods.goodsQuality}}
                            {{else}}
                            品牌（{{goods.brandName}}）
                            {{/if}}
                        </div>
                    </div>
                </td>
                <td width="10%">¥{{goods.goodsPrice | amountFormatHelper}}</td>
                <td width="10%">{{goods.goodsNumber}}</td>
                <td width="10%">
                    <!--退货中的链接-->
                </td>

                {{if idxGoods==0}}
                <td width="10%" rowspan="{{orderItem.orderGoodsList.length}}">
                    ¥{{orderItem.orderAmount | amountFormatHelper}}
                    <br>
                    {{orderItem.payName}}
                </td>
                <td width="10%" rowspan="{{orderItem.orderGoodsList.length}}">
                    <span style="color: #FE7A00;">
                            {{orderItem.orderStatus | partOrderStatusHelper}}
                    </span>
                    <br>
                    <br>
                    <span style="cursor: pointer;text-decoration:underline;" onclick="SHOW_ORDER.orderDetail('{{orderItem.orderSn}}')">订单详情</span>
                </td>
                <td width="15%" rowspan="{{orderItem.orderGoodsList.length}}">
                    {{if orderItem.orderStatus == 3}}
                    <div class="offer-btn" style="background: #00AADD" onclick="SHOW_ORDER.confirmReceive('{{orderItem.orderSn}}')">确认收货</div>
                    {{/if}}

                    {{if orderItem.orderStatus == 0}}
                    <div class="offer-btn" onclick="location.href='/part/pay/selectPayment?orderSn={{orderItem.orderSn}}'">立即付款</div>
                    <div onclick="SHOW_ORDER.cancelOrder('{{orderItem.orderSn}}')" style="cursor: pointer;text-decoration:underline;">
                        取消订单
                    </div>
                    {{/if}}

                    <br>

                </td>
                {{/if}}

            </tr>
            {{/each}}
            </tbody>
        </table>
    </div>
    {{/each}}
</script>


<script type="text/html" id="noDataTemplate">
    <div style="padding-top: 120px;padding-bottom: 230px;font-size: 15px;color: #000000;">{{msg}}</div>
</script>

<!--收货地址列表-->
<script type="text/html" id="addressListTemplate">
    <table class="content-table" id="addressList">
        <tbody>
        <tr>
            <td><b class="text-red">*&nbsp;</b>门店名称&nbsp;：</td>
            <td>
                <input type="text" class="input-width" readonly="readonly" name="supplier_name" id="supplier_name" value="${CURRENT_USER.shopBO.companyName}">
            </td>
        </tr>
        {{each dataList as address idx}}
        <tr>
            <td>{{if idx == 0}}<b class="text-red">*&nbsp;</b>收货地址&nbsp;：{{/if}}</td>
            <td>
                <div class="address-set">
                    <input type="radio" name="address" id="{{address.id}}" {{if idx == 0}}checked="checked"{{/if}}style="margin-right: 10px;margin-left: 5px;">
                    <span>{{address.provinceName}}&nbsp;&nbsp;{{address.cityName}}&nbsp;&nbsp;{{address.districtName}}&nbsp;&nbsp;
                        {{address.streetName}}&nbsp;&nbsp;&nbsp;&nbsp;{{address.address}}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;({{address.contactsName}}&nbsp;收)&nbsp;{{address.mobile}}</span>
                </div>
            </td>
            {{if idx == 0 && address.isDefault==1}}
            <td>
                <span style="margin-left: 20px;color: #02aadb;">默认地址</span>
            </td>
            {{/if}}
        </tr>
        {{/each}}
        </tbody>
    </table>
</script>