/**
 * Created by lyj on 16/6/20.
 */

var body = $('body');
var carBrandSeriesResult;

$(function () {

    getCarBrandForFilter();
});

function getCarBrandForFilter(){

    JqUI.loading(body);

    JqAjax.get('/epc/car/carBrandForFilter', function (result) {
        if (result.success) {
            $('#firstWord-li').html(template('carTypeHeaderTemplate', result));
            $('#firstWord-li span:first').addClass('backColor');
            $('#carTypeBody').html(template('carTypeBodyTemplate', result));

            bindClickOnCarModel();

            carBrandSeriesResult = result;
        }

        JqUI.unblockUI(body);
    });
}

//点击字母检索
function firstWord(el) {
    $('#firstWord-li span').removeClass('backColor');
    var firstWord = $(el).attr('id');

    $(el).addClass('backColor');
    $('#carTypeBody').html(template('carTypeBodyTemplate', handleResultMap(carBrandSeriesResult, firstWord)));

    bindClickOnCarModel();
}

function handleResultMap(result, firstWord) {
    if(firstWord==-1){
        return result;
    }

    var obj = {};
    obj[firstWord] = result.data[firstWord];

    return {data: obj};
}

//选择车款，页面跳转到车辆信息页面
function bindClickOnCarModel() {
    $(".series_name_span").unbind("click").click(function () {
        var seriesId = $(this).attr('data-id');

        window.location.href = '/autoParts/car/carFilter?seriesId=' + seriesId;
    });
}
