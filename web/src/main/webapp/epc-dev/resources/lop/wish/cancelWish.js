/**
 * 取消需求单相关js
 * Created by lyj on 16/8/4.
 */

var CANCEL_WISH = function () {

    /**
     * 关闭弹框(冗余LOP的代码, 即可以完全不加载LOP)
     * @param dom
     */
    var closeAlert = function (dom) {
        if ($(dom) != undefined && $(dom) != null) {
            $(dom).modal('hide');
        }
    };

    //==============业务代码 start==============

    //弹出框
    var DOM = $('#alert_cancel_wish');

    /**
     * 弹出取消需求单弹框
     */
    var alertCancelWish = function (wishId, offerId) {
        //做这个判断是因为有可能页面没添加"取消需求单弹出框"代码
        if (DOM != undefined) {
            //弹出前初始化弹框
            initAlertCancelWish(wishId, offerId);

            //展示
            DOM.modal('show');
        }
    };

    /**
     * 初始化"取消需求单弹出框"
     */
    var initAlertCancelWish = function (wishId, offerId) {
        //获取所有checkbox
        var reasonCheckboxes = DOM.find('input[type=checkbox]');

        //清空输入框内容
        $('#acwOtherReason').val('');

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
        DOM.find('div[id=acwSave]').unbind().on('click', function () {

            toCancelWish(wishId, offerId);
        });
    };

    var toCancelWish = function (wishId, offerId) {
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
            reason = DOM.find('input[id=acwOtherReason]').val().trim();

            if (reason == '') {
                EPC.alertFucWithTime(1500, '请填写具体原因!');

                return;
            }
        }

        if (confirm('您确定要取消吗？') == true) {
            cancelWish(wishId, offerId, reason);
        }

    };

    /**
     * 取消需求单
     */
    var cancelWish = function (wishId, offerId, reason) {
        //包装参数, ajax提交
        var requestParam = {
            wishId: wishId,
            offerId: offerId,
            reason: reason
        };

        //提交
        $.ajax({
            type: "POST",
            url: '/wish/cancel',
            data: JSON.stringify(requestParam),
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            beforeSend: function (request) {
                var timestamp = new Date().getTime();
                request.setRequestHeader("timestamp", timestamp);
            },
            success: function (result) {
                if (result.success) {
                    CANCEL_WISH.closeAlert(DOM);
                    EPC.alertFuc('取消成功!');

                    //执行回调方法
                    if ($.isFunction(CANCEL_WISH.callback)) {
                        CANCEL_WISH.callback();
                    }
                } else {
                    EPC.alertFuc(result.message);
                }
            },
            error: function (result) {
            }
        });

    };
    //==============业务代码 end==============

    return {
        closeAlert: function (dom) {
            closeAlert(dom);
        },
        alertCancelWish: function (wishId, offerId) {
            if (wishId == null || wishId == undefined) {
                return false;
            }
            alertCancelWish(wishId, offerId);
        },
        callback: undefined
    }
}();