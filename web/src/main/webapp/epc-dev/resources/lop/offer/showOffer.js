/**
 * 我的报价单 js
 * Created by lyj on 16/8/8.
 */

template.helper('orderStatusHelper', function (str) {
    if(str==undefined || str==null || str==''){
        return '';
    }
    var result = '';
    switch (str){
        case 'XYQX':
        case 'BYQX': result = '已取消'; break;
        case 'BDFK': result = '待付款'; break;
        case 'BYFK': result = '待发货'; break;
        case 'BBFFH': result = '部分发货'; break;
        case 'BYFH': result = '全部发货'; break;
        case 'BYQS':
        case 'BYJS': result = '已签收'; break;
        case 'JYGB':
        case 'TKWCDJS': result = '交易关闭'; break;
        case 'BYTH': result = '已退货'; break;
        case 'BYTK': result = '已退款'; break;
        default: break
    }
    return result;
});

var SHOW_OFFER = function () {
    var _body = $('body');
    var _defaultStatus = 'BDFK';
    var _pageIndex = 1;
    var _currentTab = 1;

    var initDom = function () {
        showOffer(1);

        //页面处于激活页面
        $('#myOffer').addClass('active');

        $('.wish-search-button').click(function () {
            send(1);
        });
        $('.wish-search-input').bind('keypress', function (event) {
            if (event.keyCode == 13) {
                $('.wish-search-button').trigger('click');
            }
        });
        $('.wish-show-all').click(function () {
            $('.wish-search-input').val('');
            $('.wish-search-button').trigger('click');
        });

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

        var searchParam = getSearchParam(curr);
        var start = searchParam['status'];

        JqAjax.get('/wish/order/list', function (result) {
            if (result.success) {
                var data = {
                    offerItems: result.list,
                    displayFlag: 0
                };

                if (start == 'BYQX,XYQX,BYJS,BYQS,JYGB,TKWCDJS') {
                    data['displayFlag'] = 1;
                }

                $('#offerItems').html(template('offerItemTemplate', data));
                pageInit(curr, result.total);
            } else {
                $('#offerItems').html(template('noDataTemplate', {msg: '您还没有相关的订单'}));
                $('.qxy_page').empty();
            }

            //添加图片放大功能
            ImgUtil.init($('.goods-img'), ['click']);

            JqUI.unblockUI(_body);

        }, searchParam, undefined);
    };

    //组装参数
    var getSearchParam = function (page) {
        //设置状态
        var status;
        switch (_currentTab) {
            case 1:
                //待付款
                status = 'BDFK';
                break;
            case 2:
                //待付款
                status = 'BYFK';
                break;
            case 3:
                //已发货
                status = 'BYFH,BBFFH';
                //status = 'BYFH';
                break;
            case 4:
                //已完结
                status = 'BYQX,XYQX,BYJS,BYQS,JYGB,TKWCDJS';
                //status = 'BYQX,XYQX,BYQS';
                //status = 'BYQX';
                break;
            default:
                status = _defaultStatus;
                break;
        }

        var param = {
            p: page,
            status: status
        };
        var val = $('.wish-search-input').val().trim();
        if ('' != val) {
            param['keyword'] = val;
        }

        return param;
    };

    //tab切换
    var showOffer = function (type, el) {
        //设置当前tab
        _currentTab = type;

        //修改选中标签
        if (el != undefined) {
            $('.offer-select-type li').removeClass('current');
            $(el).addClass('current');
        }

        send(1);
    };

    var cancelOffer = function (wishId, offerId) {
        CANCEL_WISH.alertCancelWish(wishId, offerId);
        CANCEL_WISH.callback = function () {
            send(_pageIndex);
        }
    };

    var confirmReceive = function (offerListSn) {

        JqUI.loading(_body);

        JqAjax.get('/wish/confirmReceive', function (result) {
            if (result.success) {
                LayerUtil.msgFun('您已确认收货成功', function(){
                    send(_pageIndex);
                });
            } else {
                LayerUtil.msg('非常抱歉，确认收货失败');
            }

            JqUI.unblockUI(_body);

        }, {offerListSn: offerListSn});
    };

    var offerDetail = function (displayFlag,orderId) {
        var url = '/wish/offer/detail?orderId=' + orderId;
        window.open(url, '_blank');
    };

    return {
        init: function () {
            initDom();
        },
        showOffer: function (type, el) {
            showOffer(type, el);
        },
        cancelOffer: function (wishId, offerId) {
            cancelOffer(wishId, offerId);
        },
        confirmReceive: function (offerListSn) {
            if (offerListSn == undefined || offerListSn == null) {
                return;
            }
            confirmReceive(offerListSn);
        },
        offerDetail: function (displayFlag,orderId) {
            offerDetail(displayFlag,orderId);
        }
    }
}();

SHOW_OFFER.init();