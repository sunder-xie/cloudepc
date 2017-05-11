/**
 * 适配车型相关js
 * Created by lyj on 16/8/26.
 */

var ADAPTIVE_CAR = function () {

    var alertAdaptiveCar = function (goodsId) {
        //动画效果
        JqUI.loading($('#alert_adaptive_car'));

        JqAjax.get('/shopping/getCarForGoods', function (result) {
            if (result.success) {
                $('#carForGoods').html(template('carForGoodsTemplate', {dataList: result.data}));
            } else {
                $('#carForGoods').html(template('noCarForGoodsTemplate'));
            }
            JqUI.unblockUI($('#alert_adaptive_car'));

        }, {goodsId: goodsId});
    };

    var alertAdaptiveCarByOeNum = function (oeNum) {
        //动画效果
        JqUI.loading($('#alert_adaptive_car'));

        JqAjax.get('/shopping/getCarForGoodsByOeNum', function (result) {
            if (result.success) {
                $('#carForGoods').html(template('carForGoodsTemplate', {dataList: result.data}));
            } else {
                $('#carForGoods').html(template('noCarForGoodsTemplate'));
            }
            JqUI.unblockUI($('#alert_adaptive_car'));

        }, {oeNum: oeNum});
    };

    return {
        alertAdaptiveCar: function (goodsId) {
            alertAdaptiveCar(goodsId);

            $('#alert_adaptive_car').modal('show');
        },
        alertAdaptiveCarByOeNum: function (oeNum){
            alertAdaptiveCarByOeNum(oeNum);

            $('#alert_adaptive_car').modal('show');
        }
    }
}();
