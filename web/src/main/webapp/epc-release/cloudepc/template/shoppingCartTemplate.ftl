<!--购物车数据模板-->
<script type="text/html" id="shoppingCartBodyTemplate">
    {{each dataList as seller idx}}
    <div class="body-content-height" id="{{seller.sellerId}}">
        <div class="body-content-seller">
            <span style="padding-left: 20px;">
                {{if seller.canSelect == true}}
                <input name="seller" value="{{seller.sellerId}}" onclick="checkedProduct(this,{{seller.sellerId}})" type="checkbox" style="margin-right: 30px">
                {{/if}}
                <label style="font-size: 13px;">
                    <span class="seller-company-img"></span>
                    {{seller.sellerCompanyName}}
                </label>
            </span>
            <span class="seller-im-icon seller-chat" data-orgid="{{seller.sellerId}}">和我联系</span>
        </div>
        {{each seller.goodsList as goods i}}
        <div class="body-content-goods" id="{{goods.goodsId}}" data-title="{{seller.sellerId}}" {{if seller.goodsList.length != (i+1) }}style="border-bottom:none;"{{/if}}>
        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="body-content-table">
            <tbody>
            <tr>
                {{if goods.available}}
                <td style="width: 5%;text-align: left;padding-left: 20px;">
                    <input name="goods" type="checkbox" data-title="{{seller.sellerId}}" onclick="checkedProduct(this,{{goods.goodsId}})" value="{{goods.id}}"
                           data-goods-id="{{goods.goodsId}}" data-part-name="{{goods.partName}}">
                </td>
                {{else}}
                <td style="width: 5%;text-align: left;vertical-align: top;">
                    <span class="mark-un-available">已失效</span>
                </td>
                {{/if}}

                <td style="width: 39%;">
                    <div class="goods-info-box" style="border-right: none;">
                        <div class="goods-info-img">
                            <img src="{{goods.goodsImg}}" class="goods-info-img-set" onerror="javascript:this.src='/img/no-pic-big.png';this.onerror=null;" title="点击查看原图" >
                        </div>
                        <div class="goods-info-text">
                            <h5 style="margin-top: 5px;margin-bottom: 2px;font-size: 14px;">
                                {{goods.partName}}
                            </h5>
                            <div  class="goods-info-text-con">
                                <div class="goods-oe">{{goods.oeNumber}}</div>
                                <button class="adaptive-car shopping-cart-adaptive-car" data-goods-id="{{goods.goodsId}}"
                                        onclick="ADAPTIVE_CAR.alertAdaptiveCarByOeNum('{{goods.oeNumber}}')">适配车型</button>
                            </div>
                            <div  class="goods-info-text-con" style="text-align: left;">
                                品质&nbsp;:&nbsp;
                                {{if goods.brandName==null || goods.brandName==''}}
                                {{goods.goodsQuality}}
                                {{else}}
                                品牌（{{goods.brandName}}）
                                {{/if}}
                            </div>
                        </div>
                    </div>
                </td>
                <td style="width: 14%;">
                    {{if goods.goodsPrice!=null}}
                    <span>￥</span>
                    <span>{{goods.goodsPrice | numberFormatHelper}}</span>
                    {{else}}
                    <span>价格失效</span>
                    {{/if}}
                </td>
                <td style="width: 15%;">
                    {{if goods.available}}
                    <div class="countBox1" style="margin:0 auto;">
                        <input type="button" class="countCon1" style="font-size: 26px;" value="-" title="-1" onclick="changeNum(this,{{goods.goodsId}},-1)">
                        <input type="text" value="{{goods.goodsNumber}}" class="countCon3" oninput="changeInputNum(this,{{goods.goodsId}})">
                        <input type="button" class="countCon2" value="+" title="+1" onclick="changeNum(this,{{goods.goodsId}},1)">
                    </div>
                    {{else}}
                    {{goods.goodsNumber}}
                    {{/if}}
                </td>
                <td style="width: 15%;">
                    {{if goods.goodsPrice!=null}}
                    <span class="goods-price-text">
                        <strong>￥</strong>
                        <strong class="total-price">{{(goods.goodsPrice*goods.goodsNumber) | numberFormatHelper}}</strong>
                    </span>
                    {{else}}
                    <span>价格失效</span>
                    {{/if}}
                </td>
                <td style="width:12%;">
                    <a href="javascript:" title="删 除" class="cart-goods-delete" onclick="deleteProduct({{goods.goodsId}})">
                        <i class="fa fa-times"></i>
                    </a>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
    {{/each}}
    </div>
    {{/each}}
</script>
<!--购物车中没有数据模板-->
<script type="text/html" id="noShoppingCartDataTemplate">
    <div style="margin: 52px 0 272px 0;">
        <div class="no-data-logo">
            <i class="fa fa-shopping-cart logo-set"></i>
        </div>
        <span style="font-size: 14px;">您还没有选购商品</span>
    </div>
</script>
