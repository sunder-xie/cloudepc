/**
 * Created by zhouheng on 16/8/9.
 */
var _body = $('body');
var _province = 0;
var _city = 0;
var _district = 0;
var _street = 0;

$(function(){
    //initSelect2();

    //获取地址
    getUserAddress();

    //获取省份
    getRegionInfo(1, _province, 'province');

    //获取城市
    if(_province > 0){
        getRegionInfo(_province, _city, 'city');
    }
    //获取区县
    if(_city > 0){
        getRegionInfo(_city, _district, 'district');
    }
    //获取街道
    if(_district > 0){
        getRegionInfo(_district, _street, 'street');
    }
});

//获取用户地址
function getUserAddress(){
    Ajax.get({
        url: '/user/getDefaultAddress',
        async: false,
        success: function(result){
            if(result.success){
                var data = result.data;
                _province = data.provinceId;
                _city = data.cityId;
                _district = data.districtId;
                _street = data.streetId;

                $('#wishListMaker').val(data.contactsName);
                $('#mobile').val(data.mobile);
                $('#address').val(data.address);

            }
        }
    });
}

function initSelect2(){
    $('#province').select2();
    $('#city').select2();
    $('#district').select2();
    $('#street').select2();
}

//清除选择框
function clearSelect(idStr){
    var select = $('#'+idStr).get(0);
    if(select===undefined){
        return;
    }
    select.options.length = 1;
    $(select).val(0).select2();
}
//获取区域信息
function getRegionInfo(pid, id, idStr){
    clearSelect(idStr);

    var select = $('#'+idStr);
    JqAjax.get('/region/subRegions',function(result){
        for(var i=0;i<result.length;i++){
            var op = $("<option></option>");
            op.val(result[i].id);
            op.text(result[i].regionName);
            select.append(op);
        }
        select.val(id).select2();

    }, {pid:pid});
}
//选择省份
function chooseProvince(el){
    var province = $(el).val();
    if(province==0){
        clearSelect('city');
    }else{
        getRegionInfo(province, 0, 'city');
    }

    clearSelect('district');
    clearSelect('street');
}
//选择城市
function chooseCity(el){
    var city = $(el).val();
    if(city==0){
        clearSelect('district');
    }else{
        getRegionInfo(city, 0, 'district');
    }

    clearSelect('street');
}
//选择区域
function chooseDistrict(el){
    var district = $(el).val();
    if(district==0){
        clearSelect('street');
        return;
    }
    getRegionInfo(district, 0, 'street');
}
//支付方式选择
function choosePayType(el){
    $(el).parent().children("img").removeClass('border-set');
    $(el).addClass('border-set');
}
//确认购买
function confirm(el){
    var post = checkUserAddress(true,el);
    if (typeof post == "boolean") {
        return false;
    }

    JqUI.loading(_body);

    JqAjax.post('/wish/createOrder',function(result){
        if(result.success){
            //EPC.alertFuc("创建订单成功!");

            var payCode = $('.border-set').data('code');
            if(payCode==undefined){
                return;
            }
            switch(payCode){
                case 'alipay':
                    window.location.href = '/wish/pay/aliPay?orderId='+post.offerId;
                    break;
                case 'lianpay':
                    window.location.href = '/wish/pay/selectUnionPay?orderId='+post.offerId;
                    break;
                default:
            }

        }else{
            EPC.alertFuc("创建订单失败，"+result.message);
        }

        JqUI.unblockUI(_body);

    }, JSON.stringify(post), undefined, 'application/json');
}

function emptyId(id){
    if(id==undefined || id==null || id==0){
        return true;
    }
    return false;
}

function checkUserAddress(showTip,el) {

    var illegalCode = new Array('!', '@', '#', '\'', '\"', '$', '%', '^', '&', '_', '=', '~', '`', '{', '}', ';', ':', ',', '?', '<', '>');
    var illegalCodeRegex = new RegExp('\\' + illegalCode.join('|\\'), 'gi');

    var supplierName = $('#supplier_name').val().trim();
    var wishListMaker = $('#wishListMaker').val().trim();
    var mobile = $('#mobile').val().trim();
    var province = $('#province').val();
    var city = $('#city').val();
    var district = $('#district').val();
    var street = $('#street').val();
    var address = $('#address').val().trim();
    var shippingId = $('#payType').data('value');
    var payId = $('#paymentList .border-set').data('value');
    var wishId = $(el).data('wish-id');
    var offerId = $(el).data('offer-id');
    supplierName = supplierName.replace(illegalCodeRegex, '');
    wishListMaker = wishListMaker.replace(illegalCodeRegex, '');
    address = address.replace(illegalCodeRegex, '');

    if (supplierName.length <= 0) {
        if (showTip) {
            EPC.alertFuc("门店名称不能为空！");
            $('#supplier_name').focus();
        }
        return false;
    }
    if (wishListMaker.length <= 0) {
        if (showTip) {
            EPC.alertFuc('联系人不能为空！');
            $('#wishListMaker').focus();
        }
        return false;
    }

    if (emptyId(province)) {
        if (showTip) {
            EPC.alertFuc('请选择省份！');
            $('#province').click();
        }
        return false;
    }
    if (emptyId(city)) {
        if (showTip) {
            EPC.alertFuc('请选择城市！');
            $('#city').click();
        }
        return false;
    }
    if (emptyId(district)) {
        if (showTip) {
            EPC.alertFuc('请选择区县！');
            $('#district').click();
        }
        return false;
    }
    if (emptyId(street)) {
        if (showTip) {
            EPC.alertFuc('请选择街道！');
            $('#street').click();
        }
        return false;
    }

    if (address.length <= 0) {
        if (showTip) {
            EPC.alertFuc('请填写详细信息！');
            $('#address').focus();
        }
        return false;
    } else if (address.length < 5 || address.length > 120) {
        if (showTip) {
            EPC.alertFuc('详细地址请控制在5-120字以内！');
            $('#address').focus();
        }
        return false;
    }
    if(payId==undefined){
        if (showTip) {
            EPC.alertFuc('请选择支付方式！');
        }
        return false;
    }

    var offerGoodsIds = [];
    var domList = $("ul[name='goodsItems']");
    domList.each(function (item, obj) {
        offerGoodsIds.push($(obj).data('value'));
    });

    var post = {
        companyName:supplierName,
        province:province,
        city:city,
        district:district,
        street:street,
        address:address,
        mobile:mobile,
        telephone:mobile,
        consignee:wishListMaker,
        wishId:wishId,
        offerId:offerId,
        payId:payId,
        shippingId:shippingId,
        saleTel:$('#saleTel').val().trim(),
        offerGoodsIds:offerGoodsIds.join(',')
    };
    //  对当前页面的唯一值做判断
    return post;
}

