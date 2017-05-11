<#-- 报价中需求单列表模板 -->
<script type="text/html" id="waitWishTemplate">
    {{each waitList as waitObj idx}}
    <div class="wish-right-content-form">
        <div class="form-one">
            {{if waitObj.status == "XDBJ"}}
            <i class="mark mark-dbj"></i>
            {{else}}
            <i class="mark mark-ybj"></i>
            {{/if}}
            <div class="form-one-div">
                {{if waitObj.refer == '管家急呼'}}
                <img src="/img/lop/avid_call_tag_1.png" style="margin-right: 15px;">
                {{/if}}
                <span>下单时间&nbsp;:&nbsp;{{waitObj.createTime}}</span>
                <span>需求单号&nbsp;:&nbsp;{{waitObj.wishListSn}}</span>
                <span class="cancel-wish-btn" onclick="cancelWish({{waitObj.wishListId}})">取消需求单</span>
            </div>
            <table class="wish-table">
                <tbody>
                <tr>
                    <td>
                        <i class="td-first-fix"></i>
                        填单人&nbsp;:&nbsp;{{waitObj.wishListMaker}}
                    </td>
                    <td>填单人电话&nbsp;:&nbsp;{{waitObj.wishListMakerTel}}</td>
                    <td>
                        是否带票&nbsp;:&nbsp;{{waitObj.receipt | receiptHelper}}
                    </td>
                </tr>
                <tr>
                    <td>
                        <i class="td-first-fix"></i>
                        车架号/VIN码&nbsp;:&nbsp;{{waitObj.vin}}
                    </td>
                    <td>车型信息&nbsp;:&nbsp;{{waitObj.carInfo}}</td>
                    <td>车辆备注&nbsp;:&nbsp;{{waitObj.carMemo | emptyHelper}}</td>
                </tr>
                <tr>
                    <td style="width: 100%;">
                        <i class="td-first-fix"></i>
                        订单备注&nbsp;:&nbsp;{{waitObj.wishMemo | emptyHelper}}
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
        <div class="form-two">
            <b class="wish-color-blue">我的需求</b>
        </div>
        <div class="form-three">
            <table style="width: 100%;">
                <thead>
                <tr class="form-three-thread">
                    <th style="width: 15%;">配件名称</th>
                    <th style="width: 11%;">OE码</th>
                    <th style="width: 10%;">首选品质</th>
                    <th style="width: 10%;">备选品质</th>
                    <th style="width: 7%;">数量</th>
                    <th style="width: 7%;">单位</th>
                    <th style="width: 15%;">配件备注</th>
                    <th style="width: 25%;">商品图片</th>
                </tr>
                </thead>
                <tbody>
                {{each waitObj.goodsList as goods index}}
                <tr style="border-bottom: 1px solid #bfcfd9;">
                    <td style="width: 15%;">{{goods.requireGoodsName}}</td>
                    <td style="width: 11%;">{{goods.requireOeNum}}</td>
                    <td style="width: 10%;">{{goods.requireGoodsQualityType}}</td>
                    <td style="width: 10%;">{{goods.requireGoodsQualityTypeReserve}}</td>
                    <td style="width: 7%;">{{goods.requireGoodsNumber}}</td>
                    <td style="width: 7%;">{{goods.requireGoodsMeasureUnit}}</td>
                    <td style="width: 15%;">{{goods.requireGoodsMemo}}</td>
                    <td style="width: 25%;">
                        <ul style="list-style-type: none;">
                            {{each goods.requireGoodsImages as image i}}
                            <li style="margin: 8px;">
                                <div>
                                    <img src="{{image}}" class="form-three-image" onerror="javascript:this.src='/img/no-pic-big.png';this.onerror=null;">
                                </div>
                            </li>
                            {{/each}}
                        </ul>
                    </td>
                </tr>
                {{/each}}
                </tbody>
            </table>
        </div>
        <div class="form-four" >
            <b class="wish-color-blue">供应商报价</b>
        </div>
        <!--报价商列表-->
        <div class="competitor-info" id="{{waitObj.wishListId}}" data-sn="{{waitObj.wishListSn}}" data-vin="{{waitObj.vin}}" data-car="{{waitObj.carInfo}}">
        </div>
        <!--报价信息-->
        <div class="offer-info-{{waitObj.wishListId}}"></div>
    </div>
    {{/each}}
</script>

<#-- 报价供应商列表模板 -->
<script type="text/html" id="competitorInfoTemplate">
    <ul class="competitor-info-status">
        {{each dataList as seller index}}
        <li>
            <p>
                {{seller.sellerName}}
                <i class="im-icon" data-seller-id="{{seller.sellerId}}" data-sn="{{sn}}" data-vin="{{vin}}" data-car="{{car}}"></i>
            </p>
            <p>电话:{{seller.sellerTel}}</p>
            {{if seller.statusStr == "已报价"}}
            <span class="competitor-info-wait  competitor-backColor offer-btn-{{seller.wishListId}}-{{seller.sellerId}}" data-wishListId="{{seller.wishListId}}" data-sellerId="{{seller.sellerId}}" onclick="getOfferInfo(this)">{{seller.statusStr}}</span>
            <a href="javascript:" class="competitor-model-close btn competitor-model-close-a  hidden " data-wish-id="{{seller.wishListId}}" data-seller-id="{{seller.sellerId}}" onclick="closeCompetitor(this)">
                <i class="fa fa-times"></i>
            </a>
            {{else}}
            <span class="competitor-info-wait">{{seller.statusStr}}</span>
            {{/if}}
        </li>
        {{/each}}
        <li class="mask"></li>
    </ul>
</script>

<script type="text/html" id="noCompetitorTemplate">
    <ul class="competitor-info-status">
        <p style="text-align: center;line-height: 65px;">该车型暂无供应商报价，请拨打全车件热线 400-9937-288 进行询价</p>
    </ul>
</script>

<#-- 报价中需求单，报价商品模板 -->
<script type="text/html" id="offerInfoTemplate">
    <div style="padding: 5px 15px;">
        <dl class="goods-list">
            <dt>
                <ul class="goods-item dt-line-height">
                    <li style="width: 5%;">&nbsp;</li>
                    <li style="width: 18%;text-align: left;">商品名称</li>
                    <li style="width: 13%;">OE码</li>
                    <li style="width: 13%;">品质类型</li>
                    <li style="width: 17%;">品牌</li>
                    <li style="width: 10%;">单位</li>
                    <li style="width: 10%;">数量</li>
                    <li style="width: 14%;">单价(含运费)</li>
                </ul>
            </dt>
            {{each dataMap as goodsList key}}
            <dd id="{{key}}">
                {{each goodsList as goods idx}}
                <ul class="goods-item" style="padding: 11px 0px 7px 0px;line-height: 19px;">
                    <li style="width: 5%;">
                        {{if idx == 0}}
                        <input type="checkbox" name="checkedGoods" data-goods-id="{{goods.id}}" checked="checked" onclick="checkedGoods(this,{{key}})">
                        {{else}}
                        <input type="checkbox" name="checkedGoods" data-goods-id="{{goods.id}}" onclick="checkedGoods(this,{{key}})">
                        {{/if}}
                    </li>
                    <li style="width: 18%;text-align: left;">{{goods.goodsName}}</li>
                    <li style="width: 13%;">{{goods.goodsOe}}</li>
                    <li style="width: 13%;">{{goods.goodsQualityTypeStr}}</li>
                    <li style="width: 17%;">&nbsp;{{goods.goodsBrand}}</li>
                    <li style="width: 10%;">{{goods.goodsMeasureUnit}}</li>
                    <li style="width: 10%;" name="goodsNumber">{{goods.goodsNumber}}</li>
                    <li style="width: 14%;color: #ff7800;" name="goodsPrice">￥{{goods.goodsPrice}}</li>
                    <li name="singleTotal" class="hidden">{{goods.goodsPriceAmount}}</li>
                </ul>
                {{/each}}
            </dd>
            {{/each}}
        </dl>
        <div class="foot-memo">备注&nbsp;:&nbsp;{{offerListMemo | emptyHelper}}</div>
        <div class="detail-foot">
            <p class="d-left">总价&nbsp;:&nbsp;<strong>￥<span name="total">{{total}}</span></strong></p>
            <button class="ok-btn" onclick="confirmWish(this)" data-wish-id="{{wishListId}}" data-offer-id="{{offerId}}">确认购买</button>
        </div>
    </div>
</script>

<#-- 已完成需求单列表模板 -->
<script type="text/html" id="finishedWishTemplate">
    {{each dataList as completeObj index}}
    <div class="wish-right-content-form">
        <div class="form-one">
            <div class="form-one-div">
                {{if completeObj.refer == '管家急呼'}}
                <img src="/img/lop/avid_call_tag_1.png" style="margin-right: 15px;">
                {{/if}}
                <span>需求单号&nbsp;:&nbsp;{{completeObj.wishListSn}}</span>
                {{if completeObj.status == "XQRBJ"}}
                <i class="mark mark-qrbj"></i>
                {{else}}
                <span class="recreate-wish-btn color-red" data-wish-id="{{completeObj.wishListId}}" onclick="reCreateWish(this)">重新发起</span>
                <i class="mark mark-yqx"></i>
                {{/if}}
            </div>
            <table class="wish-table">
                <tbody>
                {{if completeObj.status == "XQRBJ"}}
                <tr>
                    <td>
                        <i class="td-first-fix"></i>
                        商家名称&nbsp;:&nbsp;{{completeObj.sellerName}}
                        <i class="im-icon" data-seller-id="{{completeObj.sellerId}}" data-sn="{{completeObj.wishListSn}}"
                           data-vin="{{completeObj.vin}}" data-car="{{completeObj.carInfo}}"></i>
                    </td>
                    <td>联系电话&nbsp;:&nbsp;{{completeObj.sellerTel}}</td>
                    <td>咨询时间&nbsp;:&nbsp;9:00-11:30 13:30-18:00</td>
                </tr>
                <tr>
                    <td>
                        <i class="td-first-fix"></i>
                        下单时间&nbsp;:&nbsp;{{completeObj.createTime}}
                    </td>
                    <td>车架号/VIN码&nbsp;:&nbsp;{{completeObj.vin}}</td>
                    <td>车型&nbsp;:&nbsp;{{completeObj.carInfo}}</td>
                </tr>
                <tr>
                    <td>
                        <i class="td-first-fix"></i>
                        车辆备注&nbsp;:&nbsp;{{completeObj.carMemo | emptyHelper}}
                    </td>
                    <td>
                        是否带票&nbsp;:&nbsp;{{completeObj.receipt | receiptHelper}}
                    </td>
                    <td>其他备注&nbsp;:&nbsp;{{completeObj.wishMemo | emptyHelper}}</td>
                </tr>
                {{else}}
                <tr>
                    <td>
                        <i class="td-first-fix"></i>
                        门店名称&nbsp;:&nbsp;{{completeObj.companyName}}
                    </td>
                    <td>地区&nbsp;:&nbsp;{{completeObj.addressInfo}}</td>
                    <td>电话&nbsp;:&nbsp;{{completeObj.mobile}}</td>
                </tr>
                <tr>
                    <td>
                        <i class="td-first-fix"></i>
                        交单时间&nbsp;:&nbsp;{{completeObj.createTime}}
                    </td>
                    <td>车架号/VIN码&nbsp;:&nbsp;{{completeObj.vin}}</td>
                    <td>车型&nbsp;:&nbsp;{{completeObj.carInfo}}</td>
                </tr>
                <tr>
                    <td>
                        <i class="td-first-fix"></i>
                        车辆备注&nbsp;:&nbsp;{{completeObj.carMemo | emptyHelper}}
                    </td>
                    <td>
                        是否带票&nbsp;:&nbsp;{{completeObj.receipt | receiptHelper}}
                    </td>
                    <td>其他备注&nbsp;:&nbsp;{{completeObj.wishMemo | emptyHelper}}</td>
                </tr>
                {{/if}}
                </tbody>
            </table>
        </div>
        <div class="form-two">
            <div class="form-two-left"><b class="wish-color-blue">我的需求</b></div>
            <div class="form-two-right"><b class="wish-color-blue">商家报价</b></div>
        </div>
        <div class="form-three">
            <table style="width: 100%;">
                <tbody>
                    <tr>
                        <td style="width: 50%; vertical-align: top;">
                            <table style="width: 100%;border-right: 1px solid #bfcfd9;">
                                <thead>
                                <tr class="form-three-thread">
                                    <th style="width: 18%;">配件名称</th>
                                    <th style="width: 15%;">OE码</th>
                                    <th style="width: 12%;">首选品质</th>
                                    <th style="width: 12%;">备选品质</th>
                                    <th style="width: 8%;">数量</th>
                                    <th style="width: 8%;">单位</th>
                                    <th style="width: 15%;">配件备注</th>
                                    <th style="width: 12%;">商品图片</th>
                                </tr>
                                </thead>
                                <tbody>
                                {{each completeObj.goodsList as goods idx}}
                                <tr style="border-bottom: 1px solid #bfcfd9;">
                                    <td style="width: 18%;">{{goods.requireGoodsName}}</td>
                                    <td style="width: 15%;">{{goods.requireOeNum}}</td>
                                    <td style="width: 12%;">{{goods.requireGoodsQualityType}}</td>
                                    <td style="width: 12%;">{{goods.requireGoodsQualityTypeReserve}}</td>
                                    <td style="width: 8%;">{{goods.requireGoodsNumber}}</td>
                                    <td style="width: 8%;">{{goods.requireGoodsMeasureUnit}}</td>
                                    <td style="width: 15%;">{{goods.requireGoodsMemo}}</td>
                                    <td style="width: 12%;">
                                        <ul style="margin-bottom: 5px;list-style-type: none;">
                                            {{each goods.requireGoodsImages as image i}}
                                            <li style="margin: 5px;">
                                                <div>
                                                    <img src="{{image}}" class="form-three-image2" onerror="javascript:this.src='/img/no-pic-big.png';this.onerror=null;">
                                                </div>
                                            </li>
                                            {{/each}}
                                        </ul>
                                    </td>
                                </tr>
                                {{/each}}
                                </tbody>
                            </table>
                        </td>
                        <td style="width: 50%; vertical-align: top;">
                            {{#completeObj.feedbackGoodsList | feedbackGoodsHelper}}
                        </td>
                    </tr>
                <tbody>
            </table>
        </div>
        {{if completeObj.offerListMemo != "" && completeObj.offerListMemo != null}}
        <div class="form-one-div">
            <i class="im-icon" data-seller-id="{{completeObj.sellerId}}" data-sn="{{completeObj.wishListSn}}"
               data-vin="{{completeObj.vin}}" data-car="{{completeObj.carInfo}}"></i>
            <strong>商家备注 : {{completeObj.offerListMemo}}</strong>
        </div>
        {{/if}}
    </div>
    {{/each}}
</script>

<#-- 已完成需求单中，报价单商品列表模板 -->
<script type="text/html" id="feedbackGoodsTemplate">
    <table style="width: 100%;border-left: 1px solid #bfcfd9;">
        <thead>
        <tr class="form-three-thread">
            <th style="width: 25%;">配件名称</th>
            <th style="width: 16%;">OE码</th>
            <th style="width: 3%;"></th>
            <th style="width: 16%;text-align: left;">品质类型</th>
            <th style="width: 13%;">品牌</th>
            <th style="width: 10%;">数量</th>
            <th style="width: 17%;">单价(含运费)</th>
        </tr>
        </thead>
        <tbody>
        {{each data as feedBackGoodsList key}}
        <#--{{if key>0}}-->
        {{each feedBackGoodsList as feedBackGoods i}}
        <tr style="border-bottom: 1px solid #bfcfd9;height: 51px;">
            {{if i==0}}
            <td style="width: 25%;" rowspan="{{feedBackGoodsList.length}}">{{feedBackGoods.feedbackGoodsName}}</td>
            <td style="width: 16%;border-right:1px solid #bfcfd9" rowspan="{{feedBackGoodsList.length}}">{{feedBackGoods.feedbackOeNum}}</td>
            {{/if}}

            <td style="width: 3%;padding: 0 5px;"><input type="checkbox" disabled="disabled"
                {{if feedBackGoods.isUserSelectPay == '1'}}checked="checked"{{/if}}/></td>
            <td style="width: 16%;text-align: left;">{{feedBackGoods.feedbackGoodsQualityType}}</td>
            <td style="width: 13%;">{{feedBackGoods.feedbackGoodsBrand}}</td>
            <td style="width: 10%;">{{feedBackGoods.feedbackGoodsNumber}}</td>
            <td style="width: 17%;color: #ff7800;">￥{{feedBackGoods.feedbackGoodsPrice}}</td>
        </tr>
        {{/each}}
        <#--{{else}}-->
        <#--{{each feedBackGoodsList as feedBackGoods i}}-->
        <#--<tr style="border-bottom: 1px solid #bfcfd9;height: 51px;">-->
            <#--<td style="width: 26%;">{{feedBackGoods.feedbackGoodsName}}</td>-->
            <#--<td style="width: 16%;border-right:1px solid #bfcfd9">{{feedBackGoods.feedbackOeNum}}</td>-->
            <#--<td style="width: 17%;">{{feedBackGoods.feedbackGoodsQualityType}}</td>-->
            <#--<td style="width: 13%;">{{feedBackGoods.feedbackGoodsBrand}}</td>-->
            <#--<td style="width: 10%;">{{feedBackGoods.feedbackGoodsNumber}}</td>-->
            <#--<td style="width: 18%;color: #ff7800;">￥{{feedBackGoods.feedbackGoodsPrice}}</td>-->
        <#--</tr>-->
        <#--{{/each}}-->
        <#--{{/if}}-->
        {{/each}}
        </tbody>
    </table>
</script>

<script type="text/html" id="noDataTemplate">
    <div style="padding-top: 120px;padding-bottom: 230px;font-size: 15px;color: #000000;">{{msg}}</div>
</script>

<script type="text/html" id="noWishTemplate">
    <div style="padding-top: 120px;padding-bottom: 230px;font-size: 15px;color: #000000;">
        {{msg}}
        <div class="operation-div">
            <div class="create-wish-btn " onclick="CityUtil.checkCreateWish()">
                发布需求单
            </div>
        </div>
    </div>
</script>

<#-- 报价单列表模板 -->
<script type="text/html" id="offerItemTemplate">
    {{each offerItems as offerItem idxOffer}}
    <div>
        <table border="1" style="width: 100%">
            <thead>
            <tr class="offer-base-info">
                <th colspan="7">
                    <span>{{offerItem.createTime}}</span>
                    <span>订单号 : {{offerItem.offerListSn}}</span>
                    <span>车型 : {{offerItem.carModelName}}</span>
                </th>
            </tr>
            </thead>

            <tbody>
            {{each offerItem.offerListGoodsList as goods idxGoods}}
            <tr class="offer-goods-item">
                <td width="35%">
                    <div class="goods-img-div">
                        <img src="{{goods.shippingImage}}" class="goods-img" onerror="javascript:this.src='/img/no-pic-big.png';this.onerror=null;">
                    </div>
                    <div class="goods-detail">
                        <div class="goods-name">{{goods.goodsName}}</div>
                        <div class="oe-num">OE码：{{goods.goodsOe}}</div>
                        <div>
                            {{if goods.goodsBrandId==-1}}
                            品质：{{goods.goodsQualityTypeStr}}
                            {{else}}
                            品牌：{{goods.goodsBrand}}
                            {{/if}}
                        </div>
                    </div>
                </td>
                <td width="10%">¥{{goods.goodsPrice}}</td>
                <td width="10%">{{goods.goodsNumber}}</td>
                <td width="10%">
                    <!--退货中的链接-->
                    {{if goods.offerGoodsStatus != '申请退货'}}
                    {{goods.offerGoodsStatus}}
                    {{/if}}
                </td>

                {{if idxGoods==0}}
                <td width="10%" rowspan="{{offerItem.offerListGoodsList.length}}">
                    ¥{{offerItem.offerAmount}}
                    <br>
                    {{offerItem.payName}}
                </td>
                <td width="10%" rowspan="{{offerItem.offerListGoodsList.length}}">
                    <span style="color: #FE7A00;">
                        <#--{{if displayFlag == 1}}-->
                            <#--{{offerItem.orderStatus | orderStatusHelper}}-->
                        <#--{{else}}-->
                            <#--{{if offerItem.orderStatus=='XYQX'}}订单取消{{else}}{{offerItem.orderStatusName}}{{/if}}-->
                        <#--{{/if}}-->
                            {{offerItem.orderStatus | orderStatusHelper}}
                    </span>
                    <br>
                    <br>
                    <span style="cursor: pointer;text-decoration:underline;" onclick="SHOW_OFFER.offerDetail({{displayFlag }},{{offerItem.id}})">订单详情</span>
                </td>
                <td width="15%" rowspan="{{offerItem.offerListGoodsList.length}}">
                    {{if offerItem.showConfirmButton == true}}
                    <div class="offer-btn" style="background: #00AADD" onclick="SHOW_OFFER.confirmReceive('{{offerItem.offerListSn}}')">确认收货</div>
                    {{/if}}

                    {{if offerItem.orderStatus != 'XYQX'}}
                    {{if offerItem.showPayButton == true}}
                    {{if offerItem.payCode == 'lianpay' || offerItem.payCode == 'lianpay_mobile'}}
                    <div class="offer-btn" onclick="location.href='/wish/pay/selectPayment?orderSn={{offerItem.offerListSn}}'">立即付款</div>
                    {{else if offerItem.payCode == 'alipay' || offerItem.payCode == 'alipay_mobile'}}
                    <div class="offer-btn" onclick="location.href='/wish/pay/selectPayment?orderSn={{offerItem.offerListSn}}'">立即付款</div>
                    {{/if}}
                    {{/if}}
                    {{/if}}

                    <br>

                    {{if offerItem.orderStatus == 'BDFK'}}
                    <div onclick="SHOW_OFFER.cancelOffer('{{offerItem.wishListId}}', '{{offerItem.id}}')" style="cursor: pointer;text-decoration:underline;">
                        取消订单
                    </div>
                    {{/if}}

                    <!--退款相关连接-->
                <#--<a href="" target="_blank">{{offerItem.offerTKStatusName}}</a>-->
                    {{if offerItem.offerTKStatusName != '申请退款'}}
                    {{offerItem.offerTKStatusName}}
                    {{/if}}

                </td>
                {{/if}}

            </tr>
            {{/each}}
            </tbody>
        </table>
    </div>
    {{/each}}
</script>
