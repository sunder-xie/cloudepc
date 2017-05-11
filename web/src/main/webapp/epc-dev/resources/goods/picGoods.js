/**
 * Created by huangzhangting on 16/7/6.
 */
var _body = $('body');
var _carId = $('#carId').val();
var _picNum = $('#picNum').val();
var _oeNumber;
var _partName;

template.helper('numberFormatHelper',function(number){
    return EPC.parseMoney(number);
});

$(function(){
    JqUI.loading(_body);

    JqAjax.get('/goods/getGcDetailByPicNumAndCar', function(result){
        if(result.success){
            $('#goodsList').html(template('picGoodsTemplate', result));

            selectGoods($('.goods-tr-style:first'));

            $('.goods-tr-style').click(function(){
                selectGoods(this);
            });

        }

        JqUI.unblockUI(_body);
    },{picNum: _picNum, carId: _carId});

    //查看原图功能
    ImgUtil.init($('#bigPicture'));

    //初始化下拉框信息
    initQualitySelect();

    //设置城市站回调方法
    SetCityCallback.callback = function(){
        getOfferInfo();
    };

});

function selectGoods(el){
    $('.selected-style').removeClass('selected-style');
    $(el).addClass('selected-style');
    var goodsId = $(el).attr('data-id');
    _oeNumber = $(el).attr('data-title');
    _partName = $(el).attr('data-name');

    JqUI.loading(_body);

    JqAjax.get('/autoParts/getGoodsDetail', function(result){
        if (result.success) {
            $('#description').html(template('descriptionTemplate', result.data));

            var epcPicList = result.data.epcPicList;
            if(epcPicList==null || epcPicList==undefined || epcPicList.length==0){
                epcPicList = ['/img/no-pic-big.png'];
            }
            $('#bigPicture').attr('src', epcPicList[0]);

        }

        JqUI.unblockUI(_body);

    }, {goodsId: goodsId, carId: _carId});

    //查询报价信息
    getOfferInfo();
}

//获取配件所有厂商报价信息
function getOfferInfo(){
    var qualityId = $('#qualitySelect').val();

    Shopping.getOfferInfo(_carId, _oeNumber, qualityId, _partName);
}

//初始化下拉框
function initQualitySelect(){
    JqAjax.get('/common/getGoodsQuality',function(result){
        if(result.success){
            $('#chooseQuality').html(template('chooseQualityTemplate',{dataList:result.data}));
        }
    });
}

//配件品质下拉框事件
function offeringFilter(el){
    var qualityId = el.value;
    Shopping.getOfferInfo(_carId, _oeNumber, qualityId, _partName);
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

    //输入验证:非数字,正整数,数字位数小于10
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
