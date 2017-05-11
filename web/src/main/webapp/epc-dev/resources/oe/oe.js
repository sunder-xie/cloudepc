/**
 * Created by lyj on 16/6/20.
 */

var _body = $('body');

var _goodsId = 0;
var _modelId = 0;

$(function () {

    $('.filter-select').select2();

    searchGoods(1);

    JqPagination.initPage(1, 1, null, goodsFilter);//初始化分页组件

});

var _carModelMap;
var _carModelList;
function handleCarModel(modelList){
    _carModelList = modelList;
    _carModelList.sort(function (a, b) {
        if (a.name >= b.name) {
            return 1;
        }
        return -1;
    });

    _carModelMap = {};
    $.each(modelList, function(idx, model){
        var list = _carModelMap[model.brand];
        if(list==undefined){
            list = [];
            _carModelMap[model.brand] = list;
        }
        list.push(model);
    });
}

var _searchValue = '';
var _searchType = 0;

//第一次搜索配件
function searchGoods(pageIndex){
    _searchValue = $('#searchValue').val();
    $('#car-type-search-input').val(_searchValue);

    _searchType = Number($('.choose-style').attr('data-type'));

    getGoods(pageIndex, -1, -1, -1, true);
}

function getGoods(pageIndex, modelId, cateId, carBrand, initFlag){
    var data = {pageIndex: pageIndex, pageSize: _goodsSearchPageSize};
    switch (_searchType){
        case 1: data['oeNumber'] = _searchValue; break;
        default : data['keyword'] = _searchValue; break;
    }
    if(modelId != -1){
        data['modelId'] = modelId;
    }
    if(cateId != -1){
        data['cateId'] = cateId;
    }

    JqUI.loading(_body);

    JqAjax.get('/autoParts/goodsSearch', function(result){
        //移除提示div
        $('.no-data-div').remove();

        if(result.success){
            if(initFlag){
                $('#goods-filter').removeClass('hidden');
                $('#goods-content').removeClass('hidden');
            }

            $('#content-left').removeClass('hidden');
            $('#content-right').removeClass('hidden');

            $('#SearchResult').html(template('goodsSearchTemplate', {dataList: result.data.goodsInfo}));

            choiceOe($('#SearchResult tr:first'));

            var maxPage = Math.ceil(result.data.num/_goodsSearchPageSize);
            JqPagination.updatePage(maxPage, pageIndex, null);//更新分页组件

            //处理配件
            handleGoods(result.data.goodsInfo);

            if(initFlag) {
                handleGoodsFilter(result);
            }

        }else{
            if(initFlag){
                $('#content').html(template('goodsSearchNoDataTemplate'));
            }else{
                $('#content-left').addClass('hidden');
                $('#content-right').addClass('hidden');
                $('#goods-content').append(template('goodsSearchNoDataTemplate'));
            }
        }

        JqUI.unblockUI(_body);

    }, data);

}

function handleGoodsFilter(result){
    handleCarModel(result.data.carInfo);

    $('#carBrandSelect').html(template('brandSelectTemplate', {data: _carModelMap}));
    //$('#carBrandSelect').val(carBrand);
    $('#carModelSelect').html(template('modelSelectTemplate', {dataList: _carModelList}));
    //$('#carModelSelect').val(modelId);

    var cateList = result.data.cateInfo;
    cateList.sort(function (a, b) {
        if (a.catName >= b.catName) {
            return 1;
        }
        return -1;
    });
    $('#cateSelect').html(template('cateSelectTemplate', {dataList: cateList}));
    //$('#cateSelect').val(cateId);

    bandGoodsFilterSelect();
}

//筛选框绑定事件
function bandGoodsFilterSelect(){
    $('#carBrandSelect').change(function(){
        //alert(this.value);
        var val = this.value;
        var list;
        if(val=='-1'){
            list = _carModelList;
        }else{
            list = _carModelMap[val];
            list.sort(function (a, b) {
                if (a.name >= b.name) {
                    return 1;
                }
                return -1;
            });
        }

        $('#carModelSelect').html(template('modelSelectTemplate', {dataList: list}));
        //$('#carModelSelect').select2('val', '-1');
        //无奈之举 o(╯□╰)o
        $('#select2-carModelSelect-container').text('选择车型').attr('title', '选择车型');
    });

    $('#carModelSelect').change(function(){
        goodsFilter(1);
    });

    $('#cateSelect').change(function(){
        goodsFilter(1);
    });
}

//配件二次筛选
function goodsFilter(pageIndex){
    var modelId = $('#carModelSelect').val();
    var cateId = $('#cateSelect').val();
    var carBrand = $('#carBrandSelect').val();
    //alert(modelId+'  '+cateId+'  '+carBrand);

    getGoods(pageIndex, modelId, cateId, carBrand, false);
}

//点击配件
function choiceOe(el) {
    $('.thirdList-backColor').removeClass('thirdList-backColor');
    $(el).addClass('thirdList-backColor');
    _goodsId = Number($(el).attr('data-id'));

    var goodsDetail = _goodsDetailMap[_goodsId];
    if(goodsDetail !== undefined){
        if(goodsDetail === null){
            $('#CarModel').html('没有适配车型');
        }else{
            goodsDetail.carMap['modelId'] = goodsDetail.modelId;
            $('#CarModel').html(template('carModelTemplate', goodsDetail.carMap));
            var text = '';
            if(goodsDetail.modelSubjoin !== null){
                text = goodsDetail.modelSubjoin.goodsCarRemarks;
            }

            $(el).children().find('.goods-car-remark').html(text+'（'+goodsDetail.modelName+'）');

            $('.car-model-span').mouseover(function(){
                carModelMouseOver(this);
            });
        }

        return;
    }

    JqAjax.get('/autoParts/carModelForGoods', function (result) {
        if (result.success) {
            $('#CarModel').html(template('carModelTemplate', result.data));

            $('.car-model-span').mouseover(function(){
                carModelMouseOver(this);
            });

            carModelMouseOver($('.car-model-span:first'));

        } else {
            $('#CarModel').html('没有适配车型');
        }
    }, {goodsId: _goodsId});
}


//鼠标双击配件，跳转到配件详情页
function enterOeInfo(goodsId) {
    window.location.href = '/autoParts/goods/goodsDetail?from=GOODS_DETAIL&goodsId=' + goodsId
        + '&modelId=' + _modelId+'&st='+$('#searchType').val();
}

//选择适配车型，跳转到配件详情页
function selectCarModel(el) {
    _modelId = $(el).attr('data-id');
    window.location.href = '/autoParts/goods/goodsDetail?from=GOODS_DETAIL&goodsId=' + _goodsId
        + '&modelId=' + _modelId+'&st='+$('#searchType').val();
}

// 鼠标浮动到适配车型上
var _goodsCarRemarkMap = {};
function carModelMouseOver(el){
    $('.car-model-selected').removeClass('car-model-selected');
    $(el).addClass('car-model-selected');
    _modelId = $(el).attr('data-id');
    var modelName = $(el).attr('data-name');
    var gcKey = _goodsId+'_'+_modelId;
    var subjoin = _goodsCarRemarkMap[gcKey];
    if(subjoin !== undefined){
        if(subjoin !== null){
            $('.thirdList-backColor > .goods-car-remark').html(subjoin.goodsCarRemarks+'（'+modelName+'）');
        }
        return;
    }
    //获取备注
    JqAjax.get('/autoParts/getSubjoinByGoodsModel', function(result){
        if(result.success){
            $('.thirdList-backColor > .goods-car-remark').html(result.data.goodsCarRemarks+'（'+modelName+'）');
            _goodsCarRemarkMap[gcKey] = result.data;
        }else{
            _goodsCarRemarkMap[gcKey] = null;
        }
    },{goodsId: _goodsId, modelId: _modelId});
}

var _goodsDetailMap = {};
function handleGoods(goodsList){
    var len = goodsList.length;
    for(var i=1; i<len; i++){
        var id = goodsList[i].id;

        JqAjax.get('/autoParts/modelAndSubjoinForGoods', function(result){

            _goodsDetailMap[result.goodsId] = result;

            if(result!=null && result.modelSubjoin!=null){
                $('#goodsCarRemark'+result.goodsId).html(result.modelSubjoin.goodsCarRemarks+'（'+result.modelName+'）');
            }

        }, {goodsId: id});
    }

}
