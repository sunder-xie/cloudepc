<#--订单模块适配车型-->
<style type="text/css">
    .aac-adaptive-car-items th {
        padding-top: 10px;
        padding-bottom: 10px;
    }

    .aac-adaptive-car-items td {
        padding-top: 5px;
        padding-bottom: 5px;
        border: 1px solid #ededed;
    }
</style>

<div id="alert_adaptive_car" class="modal fade modal-scroll" tabindex="-1" data-keyboard="false"
     aria-hidden="true" data-width="600px" data-height="300px" >
    <a href="#" class="model-close btn" style="color:#FFF;background-color:#c63927" data-dismiss="modal" aria-hidden="true">
        <i class="fa fa-times"></i>
    </a>
    <div class="modal-section">
        <div class="modal-body" style="overflow-x: hidden;">
            <table id="aacAdaptiveCarItems" class="aac-adaptive-car-items" style="width: 100%;">
                <thead style="background: #F2F2F2;">
                <tr>
                    <th width="25%">品牌</th>
                    <th width="25%">车型</th>
                    <th width="50%">车款</th>
                </tr>
                </thead>
                <!--填充适配车型数据模板-->
                <tbody id="carForGoods"></tbody>
            </table>
        </div>
    </div>
</div>


<!--适配车型数据模板-->
<script type="text/html" id="carForGoodsTemplate">
    {{each dataList as centerCar idx}}
    <tr>
        <td>{{centerCar.brand}}</td>
        <td>{{centerCar.model}}</td>
        <td>{{centerCar.name}}</td>
    </tr>
    {{/each}}
</script>

<!--适配车型没有数据模板-->
<script type="text/html" id="noCarForGoodsTemplate">
    <div>该商品暂无适配车型数据</div>
</script>
