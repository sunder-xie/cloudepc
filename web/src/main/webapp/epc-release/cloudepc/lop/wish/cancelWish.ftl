<#--取消需求单-->
<style type="text/css">
    .acw-title {
        font-size: 16px;
        text-align: left;
        color: #000000;
    }

    .acw-reason {
        padding-left: 104px;
        list-style-type: none;
        text-align: left;
    }

    .acw-reason li {
        margin-bottom: 20px;
    }

    .acw-reason li span {
        font-size: 14px;
    }

    .acw-operation {
        height: 60px;
    }

    .acw-btn {
        width: 140px;
        height: 40px;
        font-size: 16px;
        line-height: 40px;
        background-color: #B0B0B0;
        margin-top: 10px;
        float: left;
    }

    .acw-save {
        margin-left: 120px;
        margin-right: 10px;
        background: #FE7A00;
    }
</style>

<div id="alert_cancel_wish" class="modal fade modal-scroll" tabindex="-1" data-keyboard="false" aria-hidden="true" data-width="600px" data-backdrop="static">
    <div class="modal-section">
        <div class="modal-body" style="overflow-x: hidden;">
            <div class="acw-title">选择取消原因</div>
            <hr/>
            <div class="acw-reason">
                <li><input type="checkbox" title="觉得商品价格高">&nbsp;&nbsp;<span>觉得商品价格高</span></li>
                <li><input type="checkbox" title="觉得运费高">&nbsp;&nbsp;<span>觉得运费高</span></li>
                <li><input type="checkbox" title="嫌发货时间太长">&nbsp;&nbsp;<span>嫌发货时间太长</span></li>
                <li><input type="checkbox" title="商家迟迟未报价">&nbsp;&nbsp;<span>商家迟迟未报价</span></li>
                <li><input type="checkbox" title="其他">&nbsp;&nbsp;<span>其他</span>&nbsp;&nbsp;
                    <input type="text" id="acwOtherReason" style="width: 300px;">
                </li>
            </div>

            <div class="acw-operation">
                <div class="operation-btn acw-btn acw-save" id="acwSave">保存</div>
                <div class="operation-btn acw-btn" onclick="CANCEL_WISH.closeAlert('#alert_cancel_wish')">取消</div>
            </div>
        </div>
    </div>
</div>
