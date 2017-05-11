/**
 * Created by zhouheng on 16/8/1.
 */
template.helper('feedbackGoodsHelper',function(value){
    if($.isEmptyObject(value)){
        return '';
    }
    return template('feedbackGoodsTemplate', {data: value});
});
template.helper('emptyHelper',function(str){
    if(str == null || str == ""){
        return '无';
    }
    return str;
});
template.helper('receiptHelper', function(receipt){
    if(receipt==null || receipt==undefined){
        return '';
    }
    var txt = '';
    switch (receipt){
        case 0: txt = '不带票'; break;
        case 1: txt = '普通发票'; break;
        default: txt = '增值发票';
    }
    return txt;
});

var _wishLists = [];
var _num = 1;
var _pageIndex = 1;
var _body = $('body');

$(function () {
    //默认启动加载报价中,1报价中,2已完成
    changeView(1);
    //页面处于激活页面
    $('#myWish').addClass('active');

    $('.wish-search-button').click(function(){
        send(1);
    });
    $('.wish-search-input').bind('keypress', function (event) {
        if (event.keyCode == 13) {
            $('.wish-search-button').trigger('click');
        }
    });
    $('.wish-show-all').click(function(){
        $('.wish-search-input').val('');
        $('.wish-search-button').trigger('click');
    });
});

//分页
var pageInit = function (curr,total) {
    JqPage.init({
        //总记录数
        itemSize: total,
        //每页记录数
        pageSize: 5,
        //当前页
        current: curr || 1,
        //点击分页后的回调
        backFn: function (p) {
            send(p);
        }
    });
};
var send = function (curr) {
    _pageIndex = curr;

    if(_num == 1){
        loadWaitWishData(curr);
    }else{
        loadFinishedWishData(curr);
    }
};

var competitorInfoIntervalId;
//切换视角.报价中.已完成
function changeView(num, el){
    if(el !== undefined){
        $('.wish-right-top li').removeClass('current');
        $(el).addClass('current');
    }

    if(competitorInfoIntervalId !== undefined){
        window.clearInterval(competitorInfoIntervalId);
    }

    _num = num;
    if(num == 1){//报价中
        loadWaitWishData(1);
    }else{
        loadFinishedWishData(1);
    }

}

function getSearchParam(page){
    var param = {
        pageIndex: page
    };
    var val = $('.wish-search-input').val().trim();
    if('' != val){
        param['keyword'] = val;
    }

    return param;
}

//初始化报价中需求单数据,分页
function loadWaitWishData(pageIndex){
    offerInfoMap = {};

    JqUI.loading(_body);

    JqAjax.get('/wish/wait',function(result){

        if(result.success){
            _wishLists = result.list;
            $('#wishListContent').html(template('waitWishTemplate',{waitList: _wishLists}));
            competitorInfo();

            competitorInfoIntervalId = window.setInterval(competitorInfo, 60000);
            pageInit(pageIndex, result.total);

        }else{
            $('#wishListContent').html(template('noWishTemplate', {msg: '您还没有相关的需求单'}));
            $('.qxy_page').empty();
        }

        //添加图片放大功能
        ImgUtil.init($('.form-three-image'), ['click']);

        JqUI.unblockUI(_body);

    }, getSearchParam(pageIndex));

}
//初始化已完成需求单数据,分页
function loadFinishedWishData(pageIndex){

    JqUI.loading(_body);

    JqAjax.get('/wish/complete',function(result){
        if(result.success){
            var dataList = result.list;
            $('#wishListContent').html(template('finishedWishTemplate',{dataList: dataList}));

            pageInit(pageIndex, result.total);

            initImIcon('#wishListContent');
        }else{
            $('#wishListContent').html(template('noWishTemplate', {msg: '您还没有相关的需求单'}));
            $('.qxy_page').empty();
        }

        //添加图片放大功能
        ImgUtil.init($('.form-three-image2'), ['click']);

        JqUI.unblockUI(_body);

    }, getSearchParam(pageIndex));
}

/* 绑定im按钮 */
function initImIcon(domId) {
    $(domId).on('mouseover', function () {
        TipUtil.addTip($(domId).find('.im-icon'), '点我聊天', 'right');
    });

    $('.im-icon').unbind().on('click', function () {
        var el = $(this);
        var toSysId = el.data("seller-id");
        var sn = el.data('sn');
        var vin = el.data('vin');
        var carType = el.data('car');

        var top_url = "/monkChat/requestList?orderSn="+sn+"&vin="+vin+"&carType="+carType;
        TqmallChat.openChatWindow(toSysId,"lop",top_url,WishChatMap.bottom_url,WishChatMap.bottom_url_btn_name,WishChatMap.guide_url,WishChatMap.guide_content);
    });
}

var offerInfoMap = {};

/* 获取报价供应商信息 */
function competitorInfo() {
    var len = _wishLists.length;
    for(var i=0;i<len;i++){
        $.get('/wish/competitorInfo', {wishListId:_wishLists[i].wishListId},
            function(result){
                if(result.success){
                    var dataList = result.data;
                    var wishId = dataList[0].wishListId;
                    var domId = '#' + wishId;

                    var size = dataList.length;
                    var wishSellerKey;
                    for(var j=0; j<size; j++){
                        var key = dataList[j].wishListId+'-'+dataList[j].sellerId;
                        if(offerInfoMap[key] !== undefined){
                            wishSellerKey = key;
                            break;
                        }
                    }

                    var div = $(domId);
                    var obj = {
                        sn: div.data('sn'),
                        vin: div.data('vin'),
                        car: div.data('car'),
                        dataList: result.data
                    };

                    $(domId).html(template('competitorInfoTemplate', obj));
                    if(wishSellerKey !== undefined){
                        $('.offer-btn-'+wishSellerKey).trigger("click");
                    }

                    initImIcon(domId);
                }else{
                    $('#'+result.data).html(template('noCompetitorTemplate'));
                }
        });
    }
}

//查看报价信息
function getOfferInfo(el){
    var parent = $(el).parent();
    parent.addClass('cur');
    parent.siblings('.mask').addClass('show');
    $(el).next().removeClass('hidden');

    var total = 0.00;
    var wishListId = $(el).attr('data-wishListId');
    var sellerId = $(el).attr('data-sellerId');

    offerInfoMap[wishListId+'-'+sellerId] = 1;

    console.log(offerInfoMap);

    //JqUI.loading(_body);
    JqAjax.get('/wish/offer/goods',function(result){
        if(result.success){
            var goodsMap = {};
            var data = result.data.offerListGoodsList;
            //gruopId分组
            $.each(data, function (i, el) {
                var list = goodsMap[el.groupId];
                if (list === void 0) {
                    list = [];
                    goodsMap[el.groupId] = list;
                }
                list.push(el);
            });
            //初始化总金额total
            var offerId = 0;
            $.each(goodsMap,function(key,value){
                total = total + value[0].goodsPriceAmount;
                offerId = value[0].offerListId;
            });
            var param = {
                dataMap:goodsMap,
                offerListMemo:result.data.offerListMemo,
                total: total.toFixed(2),
                offerId : offerId,
                wishListId:wishListId
            };

            $('.offer-info-'+wishListId).html(template('offerInfoTemplate', param));
        }

        //JqUI.unblockUI(_body);

    }, {sellerId:sellerId, wishListId:wishListId});
}

//关闭报价信息
function closeCompetitor(el){
    var parent = $(el).parent();
    parent.removeClass('cur');
    parent.siblings('.mask').removeClass('show');
    $(el).addClass('hidden');

    var wishId = $(el).data('wish-id');
    $('.offer-info-'+wishId).empty();
    var sellerId = $(el).data('seller-id');
    delete offerInfoMap[wishId+'-'+sellerId];

}


//选中购买商品
function checkedGoods(el,key){
    if($(el).prop('checked')==true){
        $(el).parents("dd").find("input[name='checkedGoods']").prop('checked',false);
        $(el).prop('checked',true);
    }
    var total = 0.00;
    var domList = $(el).parents("dl").find("input[type='checkBox']:checked").parents("ul").find("li[name='singleTotal']");
    for(var i=0;i<domList.length;i++){
        var singleTotal = domList[i].innerText;
        total = total + parseFloat(singleTotal);
    }
    if(total == 0){
        total = "0.00";
    }
    $(el).parents("div").find("span[name='total']").text(total);
}


//取消需求单
function cancelWish(wishId){
    CANCEL_WISH.callback = function(){
        send(_pageIndex);
    };
    CANCEL_WISH.alertCancelWish(wishId);
}
//重新发起需求单
function reCreateWish(el){
    var wishId = $(el).data('wish-id');
    window.location.href = '/wish/createWishPage?wishListId='+wishId;
}
//确认报价单页面
function confirmWish(el){
    if($(el).parent().find("span[name='total']")[0].innerText == "0.00"){
        EPC.alertFuc('请选择要确认购买的商品!');
        return;
    }
    var offerListGoodsIds = [];
    var offerId = $(el).data('offer-id');
    var wishListId = $(el).data('wish-id');
    var domList = $('#'+wishListId).next().find("input[type='checkBox']:checked");
    domList.each(function (item, obj) {
        offerListGoodsIds.push($(obj).data('goods-id'));
    });
    var param = {
        wishListId:wishListId,
        offerListId:offerId,
        offerListGoodsIds:offerListGoodsIds.join(",")
    };

    JqUI.loading(_body);

    JqAjax.post('/wish/chooseOfferGoods', function (result) {
        if(result.success){
            window.location.href = '/wish/consignee?offerId='+offerId;
        }else{
            EPC.alertFuc(result.message);
        }

        JqUI.unblockUI(_body);

    }, param);
}
