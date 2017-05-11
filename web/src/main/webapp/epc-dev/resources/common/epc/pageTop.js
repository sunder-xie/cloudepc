/**
 * Created by lyj on 16/6/20.
 */
//H5会话缓存
var storage = window.sessionStorage;
var key = 'autoParts_crumbs';

$(function () {

    var placeholder = '';
    switch (Number($('.choose-style').attr('data-type'))){
        case 1: placeholder = _oeSearchPlaceholder; break;
        case 2: placeholder = _vinSearchPlaceholder; break;
        default : placeholder = _keywordSearchPlaceholder;
    }

    $('#car-type-search-input').bind('keypress', function (event) {
        if (event.keyCode == 13) {
            $('#car-type-head-searchBtn').trigger('click');
        }
    }).attr('placeholder', placeholder);

});

function changeSearchType(type, el) {
    TipUtil.removeTip();

    var searchInput = $('#car-type-search-input');
    searchInput.val("");

    $('.choose-style').removeClass('choose-style');
    $(el).addClass('choose-style');

    switch (type){
        case 0:
            //取消输入框的onkeyup事件
            searchInput.unbind('keyup');

            searchInput.attr('placeholder', _keywordSearchPlaceholder);
            searchInput.removeAttr('maxlength');
            break;
        case 1:
            //重设输入框的onkeyup事件
            searchInput.unbind('keyup').on('keyup', function(){
                $(this).val($(this).val().replace(/[\u4E00-\u9FA5]/g,''));
            });

            searchInput.attr('placeholder', _oeSearchPlaceholder);
            searchInput.removeAttr('maxlength');
            break;
        default :
            //重设输入框的onkeyup事件
            searchInput.unbind('keyup').on('keyup', function(){
                $(this).val($(this).val().replace(/[\u4E00-\u9FA5]/g,''));
            });

            searchInput.attr('placeholder', _vinSearchPlaceholder);
            searchInput.attr('maxlength', _vinLength);
            break;
    }

    searchInput.focus();
}


function carSearch() {
    var searchInput = $('#car-type-search-input');
    TipUtil.destroyTip(searchInput);
    var searchType = Number($('.choose-style').attr('data-type'));
    var searchValue = searchInput.val().trim();

    if (searchType == 0) {
        if ('' == searchValue) {
            TipUtil.tooltipFun(searchInput, _keywordSearchTip, 'bottom');
            return false;
        }

        window.location.href = '/autoParts/goods/keyword?q=' + searchValue;

    }
    if (searchType == 1) {
        if ('' == searchValue) {
            TipUtil.tooltipFun(searchInput, _oeSearchTip, 'bottom');
            return false;
        }

        window.location.href = '/autoParts/goods/oe?oem=' + searchValue;

    }
    if (searchType == 2) {
        searchValue = searchValue.toUpperCase();
        if (!EPC.isVin(searchValue)) {
            TipUtil.tooltipFun(searchInput, _vinSearchTip, 'bottom');
            return false;
        }
        JqAjax.post('/epc/car/getOnlineCarByVin', getVinResult, {
            'vin': searchValue
        });
    }
}

function getVinResult(result) {
    var vinNumber = $('#car-type-search-input').val().trim();
    var baseUrl = '/autoParts/category/category?carId=CARID&vinNumber=' + vinNumber;
    if (result.success) {
        var carList = result.data;
        if (carList.length == 1) {
            window.location.href = baseUrl.replace("CARID", carList[0]['id'])+'&seriesId='+carList[0]['seriesId'];
        } else {
            var choose_html = template('choose_car_template', {list: carList});
            EPC.alertFuc(choose_html);

            $('.choose_car').unbind("click").click(function () {
                var carId = $(this).attr("data-id");
                var seriesId = $(this).attr("data-sid");
                window.location.href = baseUrl.replace("CARID", carId)+'&seriesId='+seriesId;
            })
        }
    } else {
        EPC.alertFuc('很抱歉，未查询到您提交的车辆信息。');
    }

    return false;
}


// //加载购物车数据
// function loadShoppingCart(){
//     //加载购物车中商品数量图标小提示
//     var num = ShoppingCart.getProductList().length;
//     $('#cartTip').text(num);
//     var cartData = ShoppingCart.getCartData();
//     if(cartData != null){
//         $('#cartContentModel2').html(template('cartTipContentTemplate',cartData));
//     }else{
//         $('#cartContentModel2').html('暂无商品');
//     }
//
// }

// //购物车鼠标划入事件
// function showCartInfo(){
//     $('#cartContentModel1').css('display','block');
//     $('#cartContentModel2').css('display','block');
//     loadShoppingCart();
// }
// //购物车鼠标划出事件
// function hideCartInfo(){
//     $('#cartContentModel1').css('display','none');
//     $('#cartContentModel2').css('display','none');
// }
//删除购物车内商品
// function removeGoods(id){
//     ShoppingCart.deleteProduct(id);
//     loadShoppingCart();
// }

/* 获取当前页面导航 */
//function getCrumbs(){
//    var crumbs = $('#crumbs-content').html();
//    var url = window.location.href.replace('http://'+window.location.host, '');
//    return crumbs.replace('#', url);
//}
