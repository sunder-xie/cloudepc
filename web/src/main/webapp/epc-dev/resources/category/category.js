var _body = $('body');

$(function () {
    var vinNumber = $('#vinNumber').val();
    $('#car-type-search-input').val(vinNumber);
    JqAjax.get('/epc/category/getCatByCarId', getCatResult, {'carId': $('#carId').val()});

    //查看原图功能
    ImgUtil.init($('#bigPicture'));
});

var carId = $('#carId').val();
var _firstMap;
var _secondMap;
function getCatResult(result) {
    if (result.success) {
        $('#content').css('display', 'block');
        //$('#content').css('height', '720px');

        var firstList = result.data['firstList'];
        _firstMap = result.data['firstMap'];
        _secondMap = result.data['secondMap'];

        $('#firstList').html(template('firstListTemplate', {firstList: firstList}));
        $('#firstList li:first').addClass('firstList-backColor');
        var catId1 = firstList[0].catId;
        var _secondList = _firstMap[catId1]['boList'];
        $('#secondList').html(template('secondListTemplate', {secondList: _secondList}));
        var catId2 = _secondList[0].catId;
        var _thirdList = _secondMap[catId2];
        var catId3 = _thirdList[0].catId;
        handleFixingArray(_thirdList);
        $('#secondList li:first').addClass('secondList-backColor');

    } else {
        // $('#content').css('display','none');
        $('#vinSearch-noData').css('display', 'block');
    }
}

function firstList(el) {
    $('#firstList li').removeClass('firstList-backColor');
    $(el).addClass('firstList-backColor');
    var index = $(el).attr('id');
    var list = _firstMap[index];
    var _secondList = list['boList'];
  
    //$('#content').css('height', height + 'px');
    $('#secondList').html(template('secondListTemplate', {secondList: _secondList}));
    secondList($('#' + _secondList[0].catId));
}

function secondList(el) {
    $('#secondList li').removeClass('secondList-backColor');
    $(el).addClass('secondList-backColor');
    $('#secondList li:first').css(' border-top', 'none');
    if (el == $('#secondList li:first')) {
        $('#secondList li:first').css(' border-top', 'none');
    }
    var index = $(el).data('title');
    var _thirdList = _secondMap[index];
    handleFixingArray(_thirdList);
}
var _thirdMap;
function handleFixingArray(_dataArray) {
    _thirdMap = {};
    $.each(_dataArray, function (i, el) {
        var list = _thirdMap[el.firstLetter];
        if (list === void 0) {
            list = [];
            _thirdMap[el.firstLetter] = list;
        }
        list.push(el);
    });
    $('#thirdList-firstWord').html(template('thirdListFirstWordTemplate', {thirdMap: _thirdMap}));
    $('#thirdList-firstWord li:first').addClass('change-backColor');
    $('#thirdList').html(template('thirdListTemplate', {thirdMap: _thirdMap}));
    $('#oeTable tr:eq(1)').addClass('change-backColor');
    choiceCat($('#' + _dataArray[0].catId));
}

function choiceThirdListCat(el) {
    var firstWord = $(el).attr('id');
    $('#thirdList-firstWord li').removeClass('change-backColor');
    $(el).addClass('change-backColor');
    if (firstWord == -1) {
        $('#thirdList').html(template('thirdListTemplate', {thirdMap: _thirdMap}));
    } else {
        var localMap = $.extend(true, {}, _thirdMap);
        handleResultMap(localMap, firstWord);
        $('#thirdList').html(template('thirdListTemplate', {thirdMap: localMap}));
    }

}
function handleResultMap(map, firstWord) {
    $.each(map, function (key, value) {
        if (firstWord != key && firstWord != "") {
            delete map[key];
        }
    });
}

function choiceCat(el) {
    var catId = $(el).data('title');
    $('#choiceCat li').removeClass('thirdList-backColor');
    $(el).addClass('thirdList-backColor');

    JqUI.loading(_body);
    JqAjax.post("/goods/getGoodsByCarCate", tableDataFunc, {carId: carId, catId: catId});
}
var _epcList;
function tableDataFunc(result) {
    _epcList = [];
    if (result.success) {
        var resultDataList = result.data;
        _epcList = resultDataList[0].epcPicList;
        $('#oeList').html(template('oeListTemplate', {dataList: resultDataList}));
        choicePartOe($('.goods-table-tr:first'));
    }

    JqUI.unblockUI(_body);
}

var _goodsId;
var _oeNumber;
function choicePartOe(el) {
    $('#oeTable tr').removeClass('thirdList-backColor');
    $(el).addClass('thirdList-backColor');
    var epcPicNum;
    var obj = $('#oeTable tr');
    for (var i = 0; i < obj.length; i++) {
        if ($(obj[i]).hasClass('thirdList-backColor')) {
            _goodsId = $(obj[i]).attr('id');
            _oeNumber = $(obj[i]).attr('value');
            epcPicNum = $(obj[i]).children().eq(3).text();
        }
    }

    onChange($('#describe'));

    var contentDiv = $('.car-info-right-div');
    //JqUI.loading(contentDiv);
    JqUI.blockUI(contentDiv);
    JqAjax.get('/autoParts/getGoodsDetail', function(result){
        if (result.success) {
            $('#remarks').html(template('descriptionTemplate', result.data));

            var epcPicList = result.data.epcPicList;
            if(epcPicList==null || epcPicList==undefined || epcPicList.length==0){
                epcPicList = ['/img/no-pic-big.png'];
            }
            $('#pictureList').html(template('pictureListTemplate', {dataList: epcPicList}));
            $('#bigPicture').attr('src', epcPicList[0]);

            var url = '/goods/picGoods?picNum='+result.data.epcPicNum+'&carId='+carId;
            $('#showPicGoods').attr('href', url);
        }

        JqUI.unblockUI(contentDiv);

    }, {goodsId: _goodsId, carId: carId});

    JqAjax.get('/autoParts/carModelForGoods', function (result) {
        if (result.success) {
            $('#CarModel').html(template('carModelTemplate', result.data));
        } else {
            $('#CarModel').html('没有适配车型');
        }

    }, {goodsId: _goodsId});
}
function enterPartPage(el) {
    var modelId = $('.car-model-span:first').attr('data-id');
    selectCarModel(modelId);
}

function onChange(el) {
    $('.change-backColor').removeClass('change-backColor');
    $(el).addClass('change-backColor');
    //changePicture($('#pictureList img:first'));
    var id = $(el).attr("id");
    if (id == 'suitableModel') {
        $('#onDescribe').css('display', 'none');
        $('#onSuitableModel').css('display', 'block');
    } else {
        $('#onDescribe').css('display', 'block');
        $('#onSuitableModel').css('display', 'none');
    }
}
function selectCarModel(modelId) {
    window.location.href = '/autoParts/goods/goodsDetail?from=CATEGORY_DETAIL&goodsId=' + _goodsId + '&modelId=' + modelId;
}

function changePicture(el) {
    $('.picture-border').removeClass('picture-border');
    $(el).addClass('picture-border');

    var src = $(el).attr('src');
    $('#bigPicture').attr('src', src);
}
