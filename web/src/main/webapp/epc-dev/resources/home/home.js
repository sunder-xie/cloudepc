var size;
var list1;
var list2;

//H5会话缓存
var storage = window.sessionStorage;
var key = 'autoParts_crumbs';

$(document).ready(function () {
    init_storage();

    $('#website_message').modal('show');

    var input = $('#search_input');
    input.focus();

    //搜索框回车键
    input.bind('keypress', function (event) {
        if (event.keyCode == 13) {
            $('#searchBtn').trigger('click');
        }
    });

/*
    JqAjax.get('/epc/car/carBrandForFilter', function (result) {
        if (result.success) {
            var data = result.data;
            $.each(data, function (key, values) {
                size += values.length;
                $.each(values, function (i, el) {
                    if (list1 === void 0) {
                        list1 = [];
                    }
                    if (list2 === void 0) {
                        list2 = [];
                    }
                    if (list1.length < 6) {
                        list1.push(el);
                    } else {
                        list2.push(el);
                    }
                });
            });
            $('#brandLogoList1').html(template('brandLogoListTemplate', {dataList: list1}));


        }
    });
*/

    changePlaceHolder(0);
});

function init_storage(){
    var crumbsSession = JSON.parse(storage.getItem(key));
    if (crumbsSession == undefined || crumbsSession == null) {
        crumbsSession = {};
        storage.setItem(key, JSON.stringify(crumbsSession));
    }
}

function changePlaceHolder(type, obj) {
    var searchInput = $('#search_input');
    searchInput.val("");
    searchInput.focus();

    TipUtil.destroyTip(searchInput);

    if(type == 0){
        //取消输入框的onkeyup事件
        searchInput.unbind('keyup');

        searchInput.attr('placeholder', _keywordSearchPlaceholder);
        searchInput.removeAttr('maxlength');
        $('#keywordButton').css('opacity', '0.7');
        $('#oeButton').css('opacity', '0.4');
        $('#vinButton').css('opacity', '0.4');

        $('#searchBtn').unbind("click").click(function () {
            var keywords = searchInput.val().trim();
            if ('' == keywords) {
                TipUtil.tooltipFun(searchInput, _keywordSearchTip, 'bottom');
                return false;
            }
            window.location.href = '/autoParts/goods/keyword?q=' + keywords;

        });

    }else if (type == 1) {
        //重设输入框的onkeyup事件
        searchInput.unbind('keyup').on('keyup', function(event){
            if (event.keyCode != 37 && event.keyCode != 38 && event.keyCode != 39 && event.keyCode != 40) {
                $(this).val($(this).val().replace(/[\u4E00-\u9FA5]/g,''));
            }
        });

        searchInput.attr('placeholder', _oeSearchPlaceholder);
        searchInput.removeAttr('maxlength');
        $('#keywordButton').css('opacity', '0.4');
        $('#oeButton').css('opacity', '0.7');
        $('#vinButton').css('opacity', '0.4');

        //跳转oe码查询
        $('#searchBtn').unbind("click").click(function () {
            var oeNumberTrim = searchInput.val().trim();
            if ('' == oeNumberTrim) {
                TipUtil.tooltipFun(searchInput, _oeSearchTip, 'bottom');
                return false;
            }

            window.location.href = '/autoParts/goods/oe?oem=' + oeNumberTrim;

        });
    } else {
        //重设输入框的onkeyup事件
        searchInput.unbind('keyup').on('keyup', function(event){
            if (event.keyCode != 37 && event.keyCode != 38 && event.keyCode != 39 && event.keyCode != 40) {
                $(this).val($(this).val().replace(/[\u4E00-\u9FA5]/g,''));
            }
        });

        searchInput.attr('placeholder', _vinSearchPlaceholder);
        searchInput.attr('maxlength', '17');
        $('#keywordButton').css('opacity', '0.4');
        $('#oeButton').css('opacity', '0.4');
        $('#vinButton').css('opacity', '0.7');

        //跳转vin码查询
        $('#searchBtn').unbind("click").click(function () {
            var vinNumber = searchInput.val().trim();
            if (!EPC.isVin(vinNumber)) {
                TipUtil.tooltipFun(searchInput, _vinSearchTip, 'bottom');
                return false;
            }
            JqAjax.post('/epc/car/getOnlineCarByVin', getVinResult, {
                'vin': vinNumber
            });
        });
    }
    $(obj).css("color", "#02aadb");
    $(obj).css("font-size", "14px");

}

function getVinResult(result) {
    var vinNumber = $('#search_input').val().trim();
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

function hideWebsiteMessage() {
    $('#websiteMessage').css('display', 'none');
}
