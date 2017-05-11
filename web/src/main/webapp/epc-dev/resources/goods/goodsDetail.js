/**
 * Created by lyj on 16/6/20.
 */

var _body = $('body');

var _carListMap;
var _carArray;
var _goodsId;
var _modelId;
var _oeNumber;
var _carId;
var _partName;

template.helper('numberFormatHelper',function(number){
    return EPC.parseMoney(number);
});

$(function () {
    init_variable();
    
    getCarByGoodsId();
    //初始化品质类型下拉框
    initQualitySelect();
    //查看原图功能
    ImgUtil.init($('#bigPicture'));

    //设置城市站回调方法
    SetCityCallback.callback = function(){
        getOfferInfo();
    };
});

function init_variable(){
    _goodsId = $('#goodsId').val();
    _modelId = $('#modelId').val();
}
//初始化品质选择下拉框
function initQualitySelect(){
    JqAjax.get('/common/getGoodsQuality',function(result){
        if(result.success){
            $('#chooseQuality').html(template('chooseQualityTemplate',{dataList:result.data}));
        }
    });
}

function getCarByGoodsId(){
    JqUI.loading(_body);

    JqAjax.get('/autoParts/carForGoods', function (result) {
        if (result.success) {
            result.data['modelId'] = _modelId;
            $('#partLeft').html(template('carModelTemplate', result.data));
            $('#selectCarModel span').css('border', '1px solid #F5F9FC');

            _carListMap = result.data.carListMap;
            selectCarModel($('.car-model-selected').get(0));
        } else {
            $('#partLeft').html('没有适配车型');
            $('#partLeftDown').html('没有适配车款');
        }

        JqUI.unblockUI(_body);

    }, {goodsId: _goodsId});
}

function selectCarModel(el) {
    $('.car-model-span').removeClass('car-model-selected');
    if(el===void 0){
        var modelSpan = $('.car-model-span:first');
        modelSpan.addClass('car-model-selected');
        _carArray = _carListMap[modelSpan.attr('data-id')];
    }else{
        $(el).addClass('car-model-selected');
        _carArray = _carListMap[$(el).attr('data-id')];
    }

    _carArray.sort(function (a, b) {
        if (a.name >= b.name) {
            return 1;
        }
        return -1;
    });

    $('#partLeftDown').html(template('carTemplate', {carList: _carArray}));

    selectCar($('.car-li:first'));
}

function selectCar(el) {
    $('.car-li').removeClass('car-model-selected');
    $(el).addClass('car-model-selected');

    _carId = $(el).attr('data-id');

    //获取配件的所有厂商报价信息
    getOfferInfo();

    JqUI.loading(_body);

    JqAjax.get('/autoParts/getGoodsDetail', function (result) {
        if (result.success) {

            $('#goodsDetail').html(template('goodsDetailTemplate', result.data));

            var epcPicList = result.data.epcPicList;
            if(epcPicList==null || epcPicList==undefined || epcPicList.length==0){
                epcPicList = ['/img/no-pic-big.png'];
            }
            $('#pictureList').html(template('pictureListTemplate', {dataList: epcPicList}));
            $('#bigPicture').attr('src', epcPicList[0]);

            var url = '/goods/picGoods?picNum='+result.data.epcPicNum+'&carId='+_carId;
            $('#showPicGoods').attr('href', url);
        }

        JqUI.unblockUI(_body);

    }, {goodsId: _goodsId, carId: _carId});

}

function changePicture(el) {
    $('.picture-border').removeClass('picture-border');
    $(el).addClass('picture-border');

    var src = $(el).attr('src');
    $('#bigPicture').attr('src', src);
}

//获取配件所有厂商报价信息
function getOfferInfo(){
    _oeNumber = $('#oeNumber').data('title');
    _partName = $('#partName').data('title');

    var qualityId = $('#qualitySelect').val();

    Shopping.getOfferInfo(_carId,_oeNumber, qualityId, _partName);
}

//配件品质下拉框事件
function offeringFilter(el){
    var qualityId = el.value;
    Shopping.getOfferInfo(_carId,_oeNumber, qualityId, _partName);
}


//商品数量调整
function changeNum(el,num){
    var value = $(el).parent().children("input[type='text']").val();
    var int_value = parseInt(value);

    if(int_value==1 && num<0){//商品数量不能小于1
        return false;
    }
    int_value = int_value + num;
    if(int_value > _goodsNumLimit){//购买数量不能大于最大容量
        LayerUtil.msg('非常抱歉，已超最大库存');
        int_value = _goodsNumLimit;
    }
    //更新商品数量
    $(el).parent().children("input[type='text']").val(int_value) ;
}

function changeInputNum(el){
    var input_value = $(el).val().trim();

    if(input_value=='' || input_value.substring(0,1) == 0){
        $(el).val(1);
        return;
    }

    //输入验证:非数字,正整数,数字位数小于4
    if(!isNaN(input_value) && (/^\d+$/.test(input_value))){
        if(input_value > _goodsNumLimit){
            LayerUtil.msg('非常抱歉，已超最大库存');
            input_value = _goodsNumLimit;
        }
        //更新购物车中商品数量
        $(el).val(input_value);
    }else{
        $(el).val(1);
    }

}
