/**
 * 取消订单相关js
 * Created by lyj on 16/8/26.
 */

var CANCEL_ORDER = function () {

    /**
     * 关闭弹框
     * @param dom
     */
    var closeAlert = function (dom) {
        if ($(dom) != undefined && $(dom) != null) {
            $(dom).modal('hide');
        }
    };

    //==============业务代码 start==============

    //弹出框
    var DOM = $('#alert_cancel_order');

    /**
     * 弹出取消需求单弹框
     */
    var alertCancelOrder = function (orderSn) {
        //做这个判断是因为有可能页面没添加"取消需求单弹出框"代码
        if (DOM != undefined) {
            //弹出前初始化弹框
            initAlertCancelOrder(orderSn);

            //展示
            DOM.modal('show');
        }
    };

    /**
     * 初始化"取消需求单弹出框"
     */
    var initAlertCancelOrder = function (orderSn) {
        //获取所有checkbox
        var reasonCheckboxes = DOM.find('input[type=checkbox]');

        //清空输入框内容
        $('#acoOtherReason').val('');

        //所有的checkbox设置为不选中
        $(reasonCheckboxes).prop('checked', false);

        //默认选中第一个checkbox(即 觉得商品价格高)
        $(reasonCheckboxes[0]).prop('checked', true);

        //给所有checkbox添加互斥事件
        reasonCheckboxes.on('click', function () {
            DOM.find('input[type=checkbox]').prop('checked', false);
            $(this).prop('checked', true);
        });

        //初始化保存按钮事件(即 取消需求单)
        DOM.find('div[id=acoSave]').unbind().on('click', function () {

            toCancelOrder(orderSn);
        });
    };

    var toCancelOrder = function (orderSn) {
        var reason;
        var reasonCheckboxes = DOM.find('input[type=checkbox]');

        //参数"原因"校验
        for (var i = 0; i < reasonCheckboxes.length; i++) {
            if ($(reasonCheckboxes[i]).prop('checked') == true) {
                reason = $(reasonCheckboxes[i]).attr('title');
            }
        }

        if (reason == undefined || reason == null || reason == '') {
            EPC.alertFucWithTime(1500, '请选择取消原因!');

            return;
        }

        if (reason == '其他') {
            reason = DOM.find('input[id=acoOtherReason]').val().trim();

            if (reason == '') {
                EPC.alertFucWithTime(1500, '请填写具体原因!');

                return;
            }
        }

        if (confirm('您确定要取消吗？') == true) {
            cancelOrder(orderSn, reason);
        }

    };

    /**
     * 取消需求单
     */
    var cancelOrder = function (orderSn, reason) {
        //包装参数, ajax提交
        var requestParam = {
            orderSn: orderSn,
            cancelReason: reason
        };

        Ajax.post({
            url: '/buy/order/cancelOrder',
            data: requestParam,
            success: function (result) {
                if (result.success) {
                    CANCEL_ORDER.closeAlert(DOM);

                    LayerUtil.msgFun('订单取消成功', function () {
                        //执行回调方法
                        if ($.isFunction(CANCEL_ORDER.callback)) {
                            CANCEL_ORDER.callback();
                        }
                    });

                } else {
                    LayerUtil.msg(result.message);
                }
            }
        });

    };
    //==============业务代码 end==============

    return {
        closeAlert: function (dom) {
            closeAlert(dom);
        },
        alertCancelOrder: function (orderSn) {
            if (orderSn == null) {
                return false;
            }
            alertCancelOrder(orderSn);
        },
        callback: undefined
    }
}();