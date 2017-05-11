/**
 * <#--这个其实是 订单  这一期的头部 相关的js-->
 * Created by lyj on 16/9/5.
 */

var TOP_WISH = function () {

    var initDom = function () {
        $("#optionsSwitch").select2({
            minimumResultsForSearch: -1
        });

        changePlaceHolder(0);
    };

    var initEvent = function () {
        $('#optionsSwitch').on('change', function () {
            var optionsSwitchVal = Number($('#optionsSwitch').val().trim());
            changePlaceHolder(optionsSwitchVal);
        });

        //搜索框回车键
        $('#searchInput').bind('keypress', function (event) {
            if (event.keyCode == 13) {
                $('#searchBtn').trigger('click');
            }
        });

    };

    var changePlaceHolder = function (type) {
        var searchInput = $('#searchInput');
        searchInput.val("");
        searchInput.focus();

        TipUtil.destroyTip(searchInput);

        switch (type) {
            case 0:
                //取消输入框的onkeyup事件
                searchInput.unbind('keyup');

                searchInput.attr('placeholder', _keywordSearchPlaceholder);
                searchInput.removeAttr('maxlength');

                $('#searchBtn').unbind("click").click(function () {
                    var keywords = searchInput.val().trim();
                    if ('' == keywords) {
                        TipUtil.tooltipFun(searchInput, _keywordSearchTip, 'bottom');
                        return false;
                    }
                    window.location.href = '/autoParts/goods/keyword?q=' + keywords;
                });
                break;
            case 1:
                //重设输入框的onkeyup事件
                searchInput.unbind('keyup').on('keyup', function(event){
                    if (event.keyCode != 37 && event.keyCode != 38 && event.keyCode != 39 && event.keyCode != 40) {
                        $(this).val($(this).val().replace(/[\u4E00-\u9FA5]/g,''));
                    }
                });

                searchInput.attr('placeholder', _oeSearchPlaceholder);
                searchInput.removeAttr('maxlength');

                //跳转oe码查询
                $('#searchBtn').unbind("click").click(function () {
                    var oeNumberTrim = searchInput.val().trim();
                    if ('' == oeNumberTrim) {
                        TipUtil.tooltipFun(searchInput, _oeSearchTip, 'bottom');
                        return false;
                    }

                    window.location.href = '/autoParts/goods/oe?oem=' + oeNumberTrim;
                });
                break;
            case 2:
                //重设输入框的onkeyup事件
                searchInput.unbind('keyup').on('keyup', function(event){
                    if (event.keyCode != 37 && event.keyCode != 38 && event.keyCode != 39 && event.keyCode != 40) {
                        $(this).val($(this).val().replace(/[\u4E00-\u9FA5]/g,''));
                    }
                });

                searchInput.attr('placeholder', _vinSearchPlaceholder);
                searchInput.attr('maxlength', '17');

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
                break;
            default:
                //重设输入框的onkeyup事件
                searchInput.unbind('keyup').on('keyup', function(event){
                    if (event.keyCode != 37 && event.keyCode != 38 && event.keyCode != 39 && event.keyCode != 40) {
                        $(this).val($(this).val().replace(/[\u4E00-\u9FA5]/g,''));
                    }
                });

                TipUtil.tooltipFun($('#optionsSwitch'), '没有该选项的操作', 'bottom');
                break;
        }

    };

    var getVinResult = function (result) {
        var vinNumber = $('#searchInput').val().trim();
        var baseUrl = '/autoParts/category/category?carId=CARID&vinNumber=' + vinNumber;

        if (result.success) {
            var carList = result.data;
            if (carList.length == 1) {
                window.location.href = baseUrl.replace("CARID", carList[0]['id']) + '&seriesId=' + carList[0]['seriesId'];
            } else {
                var choose_html = template('chooseCarTemplate', {list: carList});

                EPC.alertFuc(choose_html);

                $('.choose_car').unbind("click").click(function () {
                    var carId = $(this).attr("data-id");
                    var seriesId = $(this).attr("data-sid");

                    window.location.href = baseUrl.replace("CARID", carId) + '&seriesId=' + seriesId;
                })
            }
        } else {
            EPC.alertFuc('很抱歉，未查询到您提交的车辆信息。');
        }

        return false;
    };

    return {
        init: function () {
            initDom();
            initEvent();
        }
    }
}();

TOP_WISH.init();
