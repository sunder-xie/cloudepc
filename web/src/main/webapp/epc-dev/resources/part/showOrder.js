/**
 * 订单js
 * Created by lyj on 16/8/22.
 */


template.helper('partOrderStatusHelper', function (status) {
    if(status==undefined || status==null){
        return '';
    }

    var result = '';
    switch (status){
        case 0: result = '待付款'; break;
        case 1: result = '已取消'; break;
        case 2: result = '待发货'; break;
        case 3: result = '已发货'; break;
        case 4:
        case 5: result = '已签收'; break;
        default: break
    }

    return result;
});

template.helper('amountFormatHelper',function(number){
    return EPC.parseMoney(number);
});

var SHOW_ORDER = function () {
    var _body = $('body');
    var _defaultStatus = '0';
    var _pageIndex = 1;
    var _currentTab = 1;

    var initDom = function () {
        showOrder(1);

        //页面处于激活页面
        $('#myOrder').addClass('active');
    };

    //分页组件
    var pageInit = function (curr, total) {
        JqPage.init({
            //总记录数
            itemSize: total,
            //每页记录数
            pageSize: 10,
            //当前页
            current: curr || 1,
            //点击分页后的回调
            backFn: function (p) {
                send(p);
            }
        });
    };

    //发送请求
    var send = function (curr) {
        _pageIndex = curr;

        JqUI.loading(_body);

        var param = getParam(curr);

        JqAjax.get('/buy/order/list', function (result) {
            if (result.success) {
                EPC.parseMoney()
                $('#orderItems').html(template('orderItemTemplate', {orderItems: result.list}));
                pageInit(curr, result.total);
            } else {
                $('#orderItems').html(template('noDataTemplate', {msg: '您还没有相关的订单'}));
                $('.qxy_page').empty();
            }

            //添加图片放大功能
            ImgUtil.init($('.goods-img'), ['click']);

            JqUI.unblockUI(_body);

        }, param, undefined);
    };

    //组装参数
    var getParam = function (page) {
        //设置状态
        var searchStatus;
        switch (_currentTab) {
            case 1:
                //待付款
                searchStatus = '0';
                break;
            case 2:
                //待发货
                searchStatus = '2';
                break;
            case 3:
                //已发货
                searchStatus = '3';
                break;
            case 4:
                //已完结
                searchStatus = '1,4,5';
                break;
            default:
                searchStatus = _defaultStatus;
                break;
        }

        return {
            searchP: page,
            searchStatus: searchStatus
        };
    };

    //tab切换
    var showOrder = function (type, el) {
        //设置当前tab
        _currentTab = type;

        //修改选中标签
        if (el != undefined) {
            $('.offer-select-type li').removeClass('current');
            $(el).addClass('current');
        }

        send(1);
    };

    var cancelOrder = function (orderSn) {
        CANCEL_ORDER.alertCancelOrder(orderSn);
        CANCEL_ORDER.callback = function () {
            send(_pageIndex);
        }
    };

    var confirmReceive = function (orderSn) {
        EPC.confirmNewFuc("你确定要确认收货吗?",function () {
            JqUI.loading(_body);

            JqAjax.post('/buy/order/confirmReceive', function (result) {
                JqUI.unblockUI(_body);

                if (result.success) {
                    LayerUtil.msgFun('您已确认收货成功', function () {
                        send(_pageIndex);
                    });
                } else {
                    LayerUtil.msg('非常抱歉，确认收货失败');
                }

            }, {orderSn: orderSn});
        });
    };

    var orderDetail = function (orderSn) {
        var url = '/buy/order/orderDetail?orderSn=' + orderSn;
        window.open(url, '_blank');
    };

    return {
        init: function () {
            initDom();
        },
        showOrder: function (type, el) {
            showOrder(type, el);
        },
        cancelOrder: function (orderSn) {
            cancelOrder(orderSn);
        },
        confirmReceive: function (orderSn) {
            if (orderSn == undefined || orderSn == null) {
                return;
            }
            confirmReceive(orderSn);
        },
        orderDetail: function (orderSn) {
            orderDetail(orderSn);
        }
    }
}();

SHOW_ORDER.init();
