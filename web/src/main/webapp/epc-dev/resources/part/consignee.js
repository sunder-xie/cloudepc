/**
 * Created by zhouheng on 16/8/30.
 */

var _body = $('body');
var _addressList;
var _buyNow;

$(function(){

    //立即购买
    if($('#buyNow').val()!=''){
        _buyNow = 1;
    }

    //获取地址信息列表
    _addressList = JSON.parse($('#addressList').val());
    //添加图片放大功能
    ImgUtil.init($('.goods-info-img-set'), ['click']);
    //计算总金额
    var total = 0.00;
    var domList = $('.singleTotal');
    for(var i=0;i<domList.length;i++){
        total = total + parseFloat(domList[i].innerText);
    }
    $('#total').text(EPC.parseMoney(total));

    //im通讯
    $(".seller-chat").unbind("click").click(function(){
        var to_sys_id = $(this).data("orgid");
        TqmallChat.openChatWindow(to_sys_id,"yunpei",undefined,WishChatMap.bottom_url,WishChatMap.bottom_url_btn_name,WishChatMap.guide_url,WishChatMap.guide_content);
    });
});

// //获取收货地址信息列表
// function getUserAddressList(){
//     JqUI.loading(_address);
//     JqAjax.get('/user/getUserAddressList',function(result){
//         if(result.success){
//             $('#addressList').html(template('addressListTemplate',{dataList:result.data}));
//             _addressList = result.data;
//         }else{
//             EPC.alertFuc("获取地址列表失败!");
//         }
//         JqUI.unblockUI(_address);
//     });
// }

//选择发货地址
function chooseAddress(el){
    var domList = $(el).parents("tbody").find("input[type='radio']");
    $.each(domList,function(index,dom){
        $(dom).prop('checked',false);
    });
    $(el).children("input").prop("checked",true);
    $(el).parents("tbody").find("div").removeClass('address-background-set');
    $(el).addClass('address-background-set');
}

//支付方式选择
function choosePayType(el){
    $('.payment-selected').removeClass('payment-selected');
    $(el).addClass('payment-selected');
}
//配送方式选择
function chooseShippingType(el){
    $('.shipping-selected').removeClass('shipping-selected');
    $(el).addClass('shipping-selected');
}
//确认购买
function confirm(){
    if(_addressList == null || _addressList == undefined){
        EPC.alertFuc("暂无可用收货地址，无法提交订单，请联系400-9937-288");
        return false;
    }
    
    JqUI.loading(_body);

    var post = getSellerList();
    if (typeof post == "boolean") {
        JqUI.unblockUI(_body);
        return false;
    }

    JqAjax.postJson('/buy/order/submitOrder',function(result){

        if(result.success){

            var orderSnList = result.data;
            if(orderSnList.length > 1){
                window.location.href = '/part/pay/selectWaitPayOrder?orderSn='+orderSnList[0];
                return;
            }

            var payCode = $('.payment-selected').data('code');
            if(payCode==undefined){
                return;
            }
            switch(payCode){
                case 'alipay':
                    window.location.href = '/part/pay/aliPay?orderSn='+orderSnList[0];
                    break;
                case 'lianpay':
                    window.location.href = '/part/pay/selectUnionPay?orderSn='+orderSnList[0];
                    break;
                default:
            }

        }else{
            LayerUtil.msgFun(result.message, function(){
                location.reload();
            });
        }

        JqUI.unblockUI(_body);

    }, JSON.stringify(post));
}
//构建请求对象
function getSellerList(){
    //var idStr = $('#idStr').val();
    var list = JSON.parse($('#sellerList').val());
    if(list==null){
        LayerUtil.msg('商品信息异常，请重新核对');
        return false;
    }
    var sellerList = [];
    for(var i=0;i<list.length;i++){
        var goodsList = [];
        var orderNote = $('#order-note-'+list[i].sellerId).val();
        var goodsSize = list[i].goodsList.length;
        for(var j=0; j<goodsSize; j++){
            var cacheGoods = list[i].goodsList[j];
            var goods = {
                goodsId: cacheGoods.goodsId,
                goodsSn: cacheGoods.goodsSn,
                partName: cacheGoods.partName,
                oeNumber: cacheGoods.oeNumber,
                goodsNumber: cacheGoods.goodsNumber,
                goodsPrice: cacheGoods.goodsPrice
            };
            goodsList.push(goods);
        }
        var seller = {
            sellerId:list[i].sellerId,
            sellerTelephone:list[i].sellerTelephone,
            sellerCompanyName:list[i].sellerCompanyName,
            orderNote:orderNote,
            goodsParamList:goodsList
        };
        sellerList.push(seller);
    }
    var post = checkUserAddress();
    if (typeof post == "boolean") {
        return false;
    }
    post['sellerList'] =sellerList;
    return post;
}
//构建请求对象
function checkUserAddress() {
    var consignee ;
    var mobile;  
    var province;
    var city ;
    var district;
    var street ;
    var address;
    var id = $("input[name='address']:checked").attr('id');

    $.each(_addressList,function(index,addressObj){
        if(id == addressObj.id){
            consignee = addressObj.contactsName;
            mobile = addressObj.mobile;
            province = addressObj.provinceId;
            city = addressObj.cityId;
            district = addressObj.districtId;
            street = addressObj.streetId;
            address = addressObj.address;
            return false;
        }
    });

    var companyName = $('#supplier_name').val().trim();
    var shippingObj = $('.shipping-selected');
    var paymentObj = $('.payment-selected');
    var invTypeObj = $("input[name='receipt']:checked");

    var post = {
        companyName:companyName,
        consignee:consignee,
        mobile:mobile,
        province:province,
        city:city,
        district:district,
        street:street,
        address:address,
        shippingId: shippingObj.data('value'),
        shippingName: shippingObj.data('name'),
        payId: paymentObj.data('value'),
        payName: paymentObj.data('name'),
        invType: invTypeObj.data('id'),
        //invTypeName: invTypeObj.data('name'),
        sellerList:undefined
    };

    if(_buyNow != undefined){
        post['buyNow'] = 1;
    }

    return post;
}

//普通发票时获取供应商税率信息
function getTaxRate(){
    var sellerList = JSON.parse($('#sellerList').val());
    var orgIdList = [];
    $.each(sellerList,function(idx,seller){
        orgIdList.push(seller.sellerId);
    });
    var orgIdStr = orgIdList.join(",");
    JqAjax.get('/shopping/getTaxRateMap',function(result){
        if(result.success) {
            var total=0.00;
            var sellerMap = result.data;
            $.each(sellerMap, function (sellerId, taxRate) {
                $.each(sellerList, function (index, seller) {
                    if (sellerId == seller.sellerId) {
                        $.each(seller.goodsList, function (idx, goods) {
                            var price_rate = mul(goods.goodsPrice, taxRate);
                            var goodsPrice = add(goods.goodsPrice, price_rate);
                            $('#goodsPrice' + goods.id).text(EPC.parseMoney(goodsPrice));
                            var goodsTotal = mul(EPC.parseMoney(goodsPrice), goods.goodsNumber);
                            $('#goodsPriceAmount' + goods.id).text(EPC.parseMoney(goodsTotal));
                            total = total+parseFloat(EPC.parseMoney(goodsTotal));
                        })
                    }
                })
            });
            $('#total').text(EPC.parseMoney(total));
        }
    },{orgIdStr:orgIdStr});
}
//不需要发票信息
function noTaxRate(){
    var total = 0.00;
    var sellerList = JSON.parse($('#sellerList').val());
    $.each(sellerList,function(index,seller){
        $.each(seller.goodsList,function(idx,goods){
            $('#goodsPrice'+goods.id).text(EPC.parseMoney(goods.goodsPrice));
            var goodsTotal = mul(EPC.parseMoney(goods.goodsPrice),goods.goodsNumber);
            $('#goodsPriceAmount'+goods.id).text(EPC.parseMoney(goodsTotal));
            total = total+parseFloat(EPC.parseMoney(goodsTotal));
        })        
    });
    $('#total').text(EPC.parseMoney(total));
}
//增值税发票
function addTaxPrice(){
    var total = 0.00;
    var sellerList = JSON.parse($('#sellerList').val());
    JqAjax.get('/shopping/getAddTax',function(result){
        if(result.success){
            $.each(sellerList,function(index,seller) {
                $.each(seller.goodsList,function(idx,goods){
                    var price_rate = mul(goods.goodsPrice,result.data);
                    var goodsPrice = add(goods.goodsPrice,price_rate);
                    $('#goodsPrice'+goods.id).text(EPC.parseMoney(goodsPrice));
                    var goodsTotal = mul(EPC.parseMoney(goodsPrice),goods.goodsNumber);
                    $('#goodsPriceAmount'+goods.id).text(EPC.parseMoney(goodsTotal));
                    total = total+parseFloat(EPC.parseMoney(goodsTotal));
                })
            });
            $('#total').text(EPC.parseMoney(total));
        }
    });
}



//加
function add(a, b) {
    var c, d, e;
    try {
        c = a.toString().split(".")[1].length;
    } catch (f) {
        c = 0;
    }
    try {
        d = b.toString().split(".")[1].length;
    } catch (f) {
        d = 0;
    }
    return e = Math.pow(10, Math.max(c, d)), (mul(a, e) + mul(b, e)) / e;
}
//减
function sub(a, b) {
    var c, d, e;
    try {
        c = a.toString().split(".")[1].length;
    } catch (f) {
        c = 0;
    }
    try {
        d = b.toString().split(".")[1].length;
    } catch (f) {
        d = 0;
    }
    return e = Math.pow(10, Math.max(c, d)),  (mul(a, e) - mul(b, e)) / e;
}
//乘
function mul(a, b) {
    var c = 0,
        d = a.toString(),
        e = b.toString();
    try {
        c += d.split(".")[1].length;
    } catch (f) {}
    try {
        c += e.split(".")[1].length;
    } catch (f) {}
    return Number(d.replace(".", "")) * Number(e.replace(".", "")) / Math.pow(10, c);
}
//➗
function div(a, b) {
    var c, d, e = 0,
        f = 0;
    try {
        e = a.toString().split(".")[1].length;
    } catch (g) {}
    try {
        f = b.toString().split(".")[1].length;
    } catch (g) {}
    return c = Number(a.toString().replace(".", "")), d = Number(b.toString().replace(".", "")), mul(c / d, Math.pow(10, f - e));
}

