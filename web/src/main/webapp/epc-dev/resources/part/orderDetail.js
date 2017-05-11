/**
 * 订单详情js
 * Created by lyj on 16/8/26.
 */

var ORDER_DETAIL = function () {
    var _body = $('body');

    var init = function () {
        //添加图片放大功能
        ImgUtil.init($('.goods-img'), ['click']);
    };

    var confirmReceive = function (orderSn) {
        EPC.confirmNewFuc("你确定要确认收货吗?",function () {
            JqUI.blockUI(_body);

            JqAjax.post('/buy/order/confirmReceive', function (result) {
                JqUI.unblockUI(_body);

                if (result.success) {
                    LayerUtil.msgFun('您已确认收货成功', function () {
                        window.location.reload();
                    });
                } else {
                    LayerUtil.msg('非常抱歉，确认收货失败');
                }

            }, {orderSn: orderSn});
        });
    };

    var cancelOrder = function (orderSn) {
        CANCEL_ORDER.alertCancelOrder(orderSn);
        CANCEL_ORDER.callback = function () {
            window.location.reload();
        }
    };

    return {
        init: function () {
            init();
        },
        confirmReceive: function (orderSn) {
            if (orderSn == undefined || orderSn == null) {
                return;
            }

            confirmReceive(orderSn);
        },
        cancelOrder: function(orderSn){
            if (orderSn == undefined || orderSn == null) {
                return;
            }

            cancelOrder(orderSn);
        }
    }
}();

ORDER_DETAIL.init();
