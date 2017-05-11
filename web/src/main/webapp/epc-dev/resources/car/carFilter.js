/**
 * Created by lyj on 16/6/20.
 */

var _company;

var _carArray;
var _powerMap;
var _yearMap;
var _powerArray;
var _nameArray;
var _tempArray;

$(function () {
    getCarModelForFilter();
});


function getCarModelForFilter() {
    var body = $('body');
    JqUI.loading(body);

    var seriesId = $('#seriesId').val();
    JqAjax.get('/epc/car/carModelForFilter', function (result) {
        var carModel = result.data[0];
        $('#carTypeInfoHeader').html(template('carInfoHeaderTemplate', carModel));
        $('#carStyle').html(template('carStyleTemplate', result));
        var firstLi = $('#changeCarModel li:first span');
        firstLi.addClass('choice-li');
        if (result.data.length == 1) {
            handleCarArray(carModel.customAttr);
        } else {
            JqUI.loading(body);
            var modelId = carModel.id;
            JqAjax.get('/epc/car/carListForFilter', function (result) {
                if (result.success) {
                    handleCarArray(result.data);
                }

                JqUI.unblockUI(body);

            }, {modelId: modelId});
        }

        JqUI.unblockUI(body);

    }, {seriesId: seriesId});
}

//处理车款数据集合，组装排量、年款过滤条件
function handleCarArray(dataArray) {
    _carArray = dataArray;
    _tempArray = dataArray;

    _powerMap = {};
    _yearMap = {};
    _nameArray = [];
    _powerArray = [];

    $.each(dataArray, function (i, el) {
        // 组装排量
        if (_powerMap[el.power] == undefined) {
            _powerMap[el.power] = {};
            _powerArray.push(el.power);
        }
        _powerMap[el.power][el.year] = 1;

        // 组装年款
        if (_yearMap[el.year] == undefined) {
            _yearMap[el.year] = {};
        }
        _yearMap[el.year][el.power] = 1;
        // 组装名称
        _nameArray.push(el);
    });
    _powerArray.sort();
    $('#carYear').html(template('carYearTemplate', {data: _yearMap}));
    $('#carPower').html(template('carPowerTemplate', {data: _powerArray}));
    $('#car-choice').html(template('carChoiceTemplate', {carList: _nameArray}));
}

//过滤车款
function carFilter() {
    var year = $('#changeCarYear .choice-li').text();
    var power = $('#changeCarPower .choice-li').text();

    if (power == "") {
        if (year == "") {
            return _carArray;
        } else {
            _tempArray = $.grep(_carArray,
                function (el, idx) {
                    return el.year == year;
                });
            return _tempArray;
        }
    } else {
        if (year == "") {
            _tempArray = $.grep(_carArray,
                function (el, idx) {
                    return el.power == power;
                });
            return _tempArray;
        } else {
            _tempArray = $.grep(_carArray,
                function (el, idx) {
                    return el.year == year && el.power == power;
                });
            return _tempArray;
        }
    }
}

//选择车款，页面跳转到分类选择页面
function carInfoSearch(el) {
    var carId = $(el).attr('id');
    $('#choiceCarCompare span').removeClass('choice-li');
    $(el).addClass('choice-li');

    window.location.href = '/autoParts/category/category?carId=' + carId;
}

//筛选条件
function changeCarModel(el) {
    $('#changeCarModel span').removeClass('choice-li');
    $(el).addClass('choice-li');
    var modelId = $(el).prop('title');
    JqAjax.get('/epc/car/carListForFilter', function (result) {
        if (result.success) {
            handleCarArray(result.data);
        }
    }, {modelId: modelId});
}

function changeCarYear(el) {
    $('#changeCarYear span').removeClass('choice-li');
    $(el).addClass('choice-li');
    var powerList = [];
    var year = $(el).text();
    $.each(_yearMap, function (year_key, power_values) {
        if (year == year_key) {
            $.each(power_values, function (power_key, value) {
                powerList.push(power_key);
            });
        }
    });
    $('#carPower').html(template('carPowerTemplate', {data: powerList}));
    $('#car-choice').html(template('carChoiceTemplate', {carList: carFilter()}));
}

function changeCarPower(el) {
    $('#changeCarPower span').removeClass('choice-li');
    $(el).addClass('choice-li');
    $('#car-choice').html(template('carChoiceTemplate', {carList: carFilter()}));
}

function choiceCarCompare(el) {
    $('#choiceCarCompare span').removeClass('choice-li');
    $(el).addClass('choice-li');
    $('#compare').addClass('choice-li');

}