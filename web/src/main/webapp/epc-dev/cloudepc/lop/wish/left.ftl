<style type="text/css">
    .left-navigate {
        width: 12%;
        color: #333C4A;
        float: left;
    }

    .model-title {
        font-size: 16px;
    }

    .left-navigate-btn {
        margin-bottom: 10px;
        cursor: pointer;
        text-align: left;
        padding-left: 10px;
    }

    .left-navigate-btn:hover {
        color: #FE7A00;
    }

    .left-navigate-btn.active{
        color: #FE7A00;
        border-left: 2px solid #FE7A00;
    }

</style>

<div class="left-navigate">
    <div style="width: 80%">
        <div class="text-align-left">
            <span class="model-title">交易中心</span>
            <hr/>
        </div>
        <div id="myWish" class="left-navigate-btn" onclick="location.href='/wish/myWishPage'">我的需求单</div>
        <div id="myOffer" class="left-navigate-btn" onclick="location.href='/wish/myOfferPage'">我的报价订单</div>
        <div id="myOrder" class="left-navigate-btn" onclick="location.href='/buy/order/myOrderPage'">我的购物订单</div>
    </div>
</div>

