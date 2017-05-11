<#--取消购物订单-->
<style type="text/css">
    .aco-title {
        font-size: 16px;
        text-align: left;
        color: #000000;
    }

    .aco-reason {
        padding-left: 104px;
        list-style-type: none;
        text-align: left;
    }

    .aco-reason li {
        margin-bottom: 20px;
    }

    .aco-reason li span {
        font-size: 14px;
    }

    .aco-operation {
        height: 60px;
    }

    .aco-btn {
        width: 140px;
        height: 40px;
        font-size: 16px;
        line-height: 40px;
        background-color: #B0B0B0;
        margin-top: 10px;
        float: left;
    }

    .aco-save {
        margin-left: 120px;
        margin-right: 10px;
        background: #FE7A00;
    }
</style>

<div id="alert_cancel_order" class="modal fade modal-scroll" tabindex="-1" data-keyboard="false" aria-hidden="true" data-width="600px" data-backdrop="static">
    <div class="modal-section">
        <div class="modal-body" style="overflow-x: hidden;">
            <div class="aco-title">选择取消原因</div>
            <hr/>
            <div class="aco-reason">
                <li><input type="checkbox" title="觉得商品价格高">&nbsp;&nbsp;<span>觉得商品价格高</span></li>
                <li><input type="checkbox" title="觉得运费高">&nbsp;&nbsp;<span>与商家客服沟通没有得到回应</span></li>
                <li><input type="checkbox" title="嫌发货时间太长">&nbsp;&nbsp;<span>下错订单</span></li>
                <li><input type="checkbox" title="商家迟迟未报价">&nbsp;&nbsp;<span>我不想要了</span></li>
                <li><input type="checkbox" title="其他">&nbsp;&nbsp;<span>其他</span>&nbsp;&nbsp;
                    <input type="text" id="acoOtherReason" style="width: 300px;">
                </li>
            </div>

            <div class="aco-operation">
                <div class="operation-btn aco-btn aco-save" id="acoSave">保存</div>
                <div class="operation-btn aco-btn" onclick="CANCEL_ORDER.closeAlert('#alert_cancel_order')">取消</div>
            </div>
        </div>
    </div>
</div>
