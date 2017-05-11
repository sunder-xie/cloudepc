/**
 * 创建需求单
 * Created by lyj on 16/8/1.
 */

var _body = $('body');

var CREATE_WISH = function () {
    //var KEY_CAR_INFO = 'key_car_info';
    var ALL_CAR_INFO = {};//存所有的车辆信息
    var PERSON_CAR_INFO = {};
    var GOODS_QUALITIES = {};

    var VIN_LENGTH = 17;
    var CAR_NAME = 'carName';
    var GOODS_ITEM_NUM = 1;
    var TIP_PLACE = 'bottom';

    var BRAND_SELECT_DEFAULT = '请选择车辆品牌';
    var SERIES_SELECT_DEFAULT = '请选择车辆车系';
    var MODEL_SELECT_DEFAULT = '请选择车辆车型';
    var CAR_NAME_SELECT_DEFAULT = '请选择排量年款';
    var QUALITY_SELECT_DEFAULT = '请选择品质';

    //初始化vinCodeLimit
    var initVinCodeLimit = function () {
        var vinCode = $.trim($('#vinCode').val());
        var length = VIN_LENGTH - vinCode.length;

        if (length < 0) {
            $('#vinCodeLimit').text(0);
        } else {
            $('#vinCodeLimit').text(length);
        }
        $('#vinCode').val(vinCode);
    };

    //重置car相关下拉框, 只适合seriesSelect、modelSelect、carNameSelect
    var resetCarSelect = function (dom, defaultText, selectedVal, textAttr) {
        var pid = $(dom).parent().parent().prev().find('select').val();

        if (selectedVal == undefined) {
            selectedVal = 0;
        }

        JqAjax.get('/epc/car/carInfoByPid', function (result) {
            var index = 0;

            if (result.success) {
                index = selectedVal;

                //清空项目
                $(dom).empty();
                LOP.default_select(dom, defaultText);

                //添加项目
                LOP.append_option(dom, result.data, undefined, textAttr);

                if (textAttr == CAR_NAME) {
                    ALL_CAR_INFO = result.data;
                    setPersonCarInfo(index);
                }
            }

            $(dom).val(index);
            $(dom).select2();

        }, {pid: pid}, undefined, false);
    };

    var init_data = function () {
        //获取配件品质
        JqAjax.get('/common/getGoodsQuality', function (result) {
            if (result.success) {
                GOODS_QUALITIES = result.data;
            }
        }, undefined, undefined, false);
    };

    //初始化页面dom元素
    var init_dom = function () {
        //初始化carCategory相关select
        LOP.default_select('#brandSelect', BRAND_SELECT_DEFAULT);
        LOP.default_select('#seriesSelect', SERIES_SELECT_DEFAULT);
        LOP.default_select('#modelSelect', MODEL_SELECT_DEFAULT);
        LOP.default_select('#carNameSelect', CAR_NAME_SELECT_DEFAULT);

        //品牌select填充数据
        JqAjax.get('/epc/car/carInfoByPid', function (result) {
            if (result.success) {
                LOP.append_option('#brandSelect', result.data);
            }
        }, {pid: 0}, undefined, false);

        //发票开具类型select增加select2样式
        $('#receiptType').select2();

        //*********************** 下面是重新发起需求单的初始化 ***********************//
        var reCreateFlag = $('#reCreateFlag').val();
        if (reCreateFlag == 'true') {
            initVinCodeLimit();

            $('#brandSelect').val($('#reCreateBrand').val());
            $('#brandSelect').select2();

            resetCarSelect('#seriesSelect', SERIES_SELECT_DEFAULT, $('#reCreateSeries').val());
            resetCarSelect('#modelSelect', MODEL_SELECT_DEFAULT, $('#reCreateModel').val());
            resetCarSelect('#carNameSelect', CAR_NAME_SELECT_DEFAULT, undefined, CAR_NAME);
            var carNameId;
            var reCreateYear = $('#reCreateYear').val();
            for (var i = 0; i < ALL_CAR_INFO.length; i++) {
                if (reCreateYear == ALL_CAR_INFO[i]['yearId']) {

                    carNameId = ALL_CAR_INFO[i]['id'];
                    PERSON_CAR_INFO = ALL_CAR_INFO[i];
                    break;
                }
            }
            $('#carNameSelect').val(carNameId);
            $('#carNameSelect').select2();

            $('#receiptType').val($('#reIsReceiptPrice').val());
            $('#receiptType').select2();

            var reGoodsList = JSON.parse($('#reGoodsList').val());
            for (var j = 0; j < reGoodsList.length; j++) {
                addGoodsItemByData(reGoodsList[j]);
            }

        } else {
            //初始化goodsItem, 初始添加一个配件
            addGoodsItem();
        }

    };

    //初始化dom事件
    var init_event = function () {
        $('#vinCode').on('keyup', function () {
            initVinCodeLimit();
        });

        $('#vinCode').on('blur', function () {
            var vinCode = $.trim($(this).val());

            if (vinCode == '') {
                return false;
            }
            vinCode = vinCode.toUpperCase();
            if (!EPC.isVin(vinCode)) {
                TipUtil.tooltipFun($(this), '请输入正确的17位vin码！', TIP_PLACE);
                return false;
            }

            JqAjax.post('/epc/car/getOnlineCarByVin', function (result) {
                if (result.success) {
                    if (result.data.length > 0) {
                        var car = result.data.shift();
                        var brandSelectNum = $('#brandSelect').find('option').length;

                        $('#brandSelect').val(brandSelectNum == 1 ? 0 : car.brandId);
                        $('#brandSelect').select2();
                        resetCarSelect('#seriesSelect', SERIES_SELECT_DEFAULT, car.seriesId);
                        resetCarSelect('#modelSelect', MODEL_SELECT_DEFAULT, car.modelId);
                        resetCarSelect('#carNameSelect', CAR_NAME_SELECT_DEFAULT, car.id, CAR_NAME);
                    }
                } else {
                    TipUtil.tooltipFun($('#vinCode'), result.message, TIP_PLACE);
                }

            }, {vin: vinCode});
        });

        $('#brandSelect').change(function () {
            resetCarSelect('#seriesSelect', SERIES_SELECT_DEFAULT);
        });

        $('#seriesSelect').change(function () {
            resetCarSelect('#modelSelect', MODEL_SELECT_DEFAULT);
        });

        $('#modelSelect').change(function () {
            resetCarSelect('#carNameSelect', CAR_NAME_SELECT_DEFAULT, undefined, CAR_NAME);
        });

        $('#carNameSelect').change(function () {
            var index = $('#carNameSelect').val();
            setPersonCarInfo(index);

        });
    };

    var setPersonCarInfo = function (index) {
        for (var i = 0; i < ALL_CAR_INFO.length; i++) {
            if (index == ALL_CAR_INFO[i].id) {

                PERSON_CAR_INFO = ALL_CAR_INFO[i];
                break;
            }
        }
    };

    //删除图片
    var deleteImg = function (url) {
        //前台删除
        $('#show').attr('src', '');

        //后台删除
        JqAjax.post('/upload/delete', function (result) {}, {url: url});
    };

    //添加配件
    var addGoodsItem = function () {
        //追加一个配件
        $('#goodsItem').append(template('goodsItemTemplate', {goodsItemNum: GOODS_ITEM_NUM}));

        var selects = $('#goodsItem' + GOODS_ITEM_NUM).find('select');
        //上传配置
        var config = {
            browse_button: 'uploadBtn' + GOODS_ITEM_NUM,
            preview_dom: 'preview' + GOODS_ITEM_NUM,
            goodsImages_dom: 'goodsImages' + GOODS_ITEM_NUM,
            goodsName_dom: 'goodsName' + GOODS_ITEM_NUM
        };
        //定义上传事件
        var callBackFunc = {
            FilesAdded: function (up, files) {
                up.start();
            },
            FileUploaded: function (up, files, result, config) {
                var response = result.response;
                var obj = JSON.parse(response);

                //上传失败
                if (!obj.success) {
                    TipUtil.tooltipFun($('#' + config['browse_button']), '图片上传失败!', TIP_PLACE);
                    return;
                }

                //如果之前有上传的图片, 现在只是更换一张图片, 则后台删除之前的图片
                var oldImg = $('#' + config['goodsImages_dom']).val();
                if (oldImg != undefined && oldImg != null && oldImg != '') {
                    JqAjax.post('/upload/delete', function (result) {}, {url: oldImg});
                }

                $('#' + config['goodsImages_dom']).val(obj.data);
                $('#' + config['preview_dom']).attr('src', _ossImgHost+obj.data);

                //添加图片放大功能
                ImgUtil.init($('#' + config['preview_dom']), ['click']);

                up.refresh();
            },
            UploadProgress: function (up, file) {
            },
            Error: function (up, err) {
                var message;
                var code = err.code;

                if (code == '-600') {
                    message = '上传失败, 图片太大了!';
                } else if (code == '-601') {
                    message = '上传失败, 文件类型错误!';
                } else if (code == '-700') {
                    message = '上传失败, 图片格式错误!';
                } else {
                    message = '图片太大或类型错误, 请更换上传图片!';
                }

                console.error(err.code + '  ' + err.message);
                TipUtil.tooltipFun($('#' + config['browse_button']), message, TIP_PLACE);
            }
        };

        //上传配置初始化
        UPLOAD.upload(config, callBackFunc);

        //初始化品质select
        for (var i = 0; i < selects.length; i++) {
            LOP.default_select(selects[i], QUALITY_SELECT_DEFAULT);
            LOP.append_option(selects[i], GOODS_QUALITIES);
        }

        //商品名称添加联想
        $('#' + config['goodsName_dom']).on('keyup', function () {
            if ($(this).val().trim().length > 0) {
                JqAjax.get('/wish/cate/suggest', function (result) {
                    $('#' + config['goodsName_dom']).autocomplete({
                        source: result.data
                    });
                }, {keyword: $(this).val().trim()});
            }
        });

        //商品数量增加输入校验
        $('#goodsItem' + GOODS_ITEM_NUM).find('input[name=goodsNumber]').unbind('keyup').on('keyup', function (event) {
            if (event.keyCode != 37 && event.keyCode != 38 && event.keyCode != 39 && event.keyCode != 40) {
                $(this).val($(this).val().replace(/[^0-9]/g, ''));

                if ($(this).val() == '') {
                    $(this).val(1);
                }

                var num = Number($(this).val().trim());
                $(this).val(num);
            }
        });

        //goodsItemNum 加1, 用以区别
        GOODS_ITEM_NUM += 1;
    };

    //添加配件
    var addGoodsItemByData = function (reGoods) {
        var oldNum = GOODS_ITEM_NUM;
        addGoodsItem();

        //填充数据
        var qualitySelect = $('#goodsItem' + oldNum).find('select');//品质的select
        var qualityTypeArr = reGoods.qualityTypeStr.split(',');

        $('#goodsItem' + oldNum).find('#goodsName' + oldNum).val(reGoods.goodsName);
        $('#goodsItem' + oldNum).find('input[name="goodsOe"]').val(reGoods.goodsOe);
        $('#goodsItem' + oldNum).find('input[name="goodsNumber"]').val(reGoods.goodsNumber);
        $('#goodsItem' + oldNum).find('input[name="goodsMeasureUnit"]').val(reGoods.goodsMeasureUnit);
        $(qualitySelect[0]).val(qualityTypeArr[0]);
        $(qualitySelect[1]).val(qualityTypeArr[1] == null ? 0 : qualityTypeArr[1]);
        $(qualitySelect[0]).select2();
        $(qualitySelect[1]).select2();
        $('#goodsItem' + oldNum).find('input[name="goodsMemo"]').val(reGoods.goodsMemo);
        $('#goodsItem' + oldNum).find('img[id="preview' + oldNum + '"]').attr('src', _ossImgHost+reGoods.goodsImages);
        $('#goodsItem' + oldNum).find('input[id="goodsImages' + oldNum + '"]').val(reGoods.goodsImages);
    };

    //删除配件
    var deleteGoodsItem = function (dom, imgId) {
        var productInfoRows = $('#goodsItem').find('tr');

        if (productInfoRows.length <= 1) {
            TipUtil.tooltipFun(dom, '至少有一个商品', TIP_PLACE);
        } else {
            var url = $('#'+imgId).val();

            $(dom).parent().remove();

            //如果有上传的图片, 则后台删除该图片
            if(url!=''){
                JqAjax.post('/upload/delete', function (result) {}, {url: url});
            }
        }
    };

    //组装单个配件json
    var getGoodsItem = function (productInfoRowDom) {
        var productInfoRow = $(productInfoRowDom);
        //品质的select
        var qualitySelect = productInfoRow.find('select');

        var getId = function () {
            var goodsId = productInfoRow.find('input[name="goodsId"]').val();
            if (goodsId == undefined) {
                goodsId = 0;
            }
            return goodsId;
        };
        var getGoodsName = function () {
            var goodsNameObj = productInfoRow.find('input[name="goodsName"]');
            var goodsName = $.trim(goodsNameObj.val());
            if (goodsName.length == 0) {
                goodsNameObj.focus();
                throw "配件名称不能为空!";
            }
            return goodsName;
        };
        var getGoodsQualityType = function () {
            var qualityIds = [];
            var firstQualityType = $(qualitySelect[0]).val();
            var secondQualityType = $(qualitySelect[1]).val();

            if (firstQualityType == 0) {
                throw "至少选择一个品质要求!";
            }
            if (firstQualityType == secondQualityType) {
                throw "备选品质不能和首选一样!";
            }

            qualityIds.push(firstQualityType);
            if (secondQualityType > 0) {
                qualityIds.push(secondQualityType);
            }
            return qualityIds.join(',');
        };
        var getGoodOe = function () {
            return productInfoRow.find('input[name="goodsOe"]').val();
        };
        var getGoodNumber = function () {
            var goodsNumber = productInfoRow.find('input[name="goodsNumber"]').val();
            if (isNaN(parseInt(goodsNumber)) || goodsNumber < 0) {
                throw "请填写正确的购买数量!";
            }
            return goodsNumber;
        };
        var getGoodsMeasureUnit = function () {
            return productInfoRow.find('input[name="goodsMeasureUnit"]').val();
        };
        var getGoodsImages = function () {
            return productInfoRow.find('input[name="goodsImages"]').val();
        };
        var getGoodsMemo = function () {
            return productInfoRow.find('input[name="goodsMemo"]').val();
        };

        var goodsNumber = getGoodNumber();
        if (goodsNumber == 0) {
            throw "购买数量不能为0!";
        }
        var goodsParam = {
            id: getId(),
            goodsName: getGoodsName(),
            qualityTypeStr: getGoodsQualityType(),
            goodsOe: getGoodOe(),
            goodsNumber: goodsNumber,
            goodsMeasureUnit: getGoodsMeasureUnit(),
            goodsImages: getGoodsImages(),
            goodsMemo: getGoodsMemo()
        };

        return goodsParam;
    };

    //组装提交到后台的对象
    var getWish = function () {
        var getId = function () {
            return $('#wishId').val();
        };
        var getVin = function () {
            var vinCode = $.trim($('#vinCode').val());
            if (vinCode.length == 0) {
                throw "请填写车架号/VIN码";
            }
            return vinCode;
        };
        var isDeckVehicle = function () {
            return 0;
        };
        var isModifiedVehicle = function () {
            var modifiedVehicle = $('input[id="isModifiedVehicle"]:checked');
            if (modifiedVehicle.val() == undefined) {
                return 0;
            }
            return 1;
        };
        var isReceiptPrice = function () {
            return $("#receiptType").val();
        };
        var getBrand = function () {
            var brandId = $('#brandSelect').val();
            if (brandId == 0) {
                throw "请选择车辆品牌!";
            }
            return brandId;
        };
        var getSeries = function () {
            var seriesId = $('#seriesSelect').val();
            if (seriesId == 0) {
                throw "请选择车辆系列!";
            }
            return seriesId;
        };
        var getModel = function () {
            var modelId = $('#modelSelect').val();
            if (modelId == 0) {
                throw "请选择车辆类型!";
            }
            return modelId;
        };
        var getEngine = function () {
            var car = PERSON_CAR_INFO;
            var carNameId = $('#carNameSelect').val();
            if (carNameId == 0) {
                throw "没有找到排量或年款!";
            }
            return car.powerId;
        };
        var getYear = function () {
            var car = PERSON_CAR_INFO;
            var carNameId = $('#carNameSelect').val();
            if (carNameId == 0) {
                throw "没有找到排量或年款!";
            }
            return car.yearId;
        };
        var getCarModel = function () {
            var car = PERSON_CAR_INFO;
            var carNameId = $('#carNameSelect').val();
            if (carNameId == 0) {
                throw "请选择排量、年款!";
            }
            return car.id;
        };
        var getCompanyName = function () {
            var companyName = $.trim($('#userCompanyName').val());
            if (companyName.length == 0) {
                throw "请填写门店名称";
            }
            return companyName;
        };
        var getTelephone = function () {
            var mobile = $.trim($('#userMobile').val());
            if (!/^1\d{10}$/.test(mobile)) {
                throw "联系电话不正确,例子：138XXXXXXXX";
            }
            return mobile;
        };
        var getWishListMaker = function () {
            var billRealName = $.trim($('#billRealName').val());
            if (billRealName.length == 0) {
                throw "请填写填单人姓名";
            }
            return billRealName;
        };
        var getWishListMakerTel = function () {
            var billMobile = $.trim($('#billMobile').val());
            if (!/^1\d{10}$/.test(billMobile)) {
                throw "请填写填单人手机号";
            }
            return billMobile;
        };
        var getWishListMemo = function () {
            return $('#wishMemo').val();
        };
        var getWishListGoodsList = function () {
            var goodsList = [];
            var productInfoRows = $('#goodsItem').find('tr');
            for (var i = 0; i < productInfoRows.length; i++) {
                var goodsItem = getGoodsItem(productInfoRows[i]);
                if (goodsItem != null) {
                    goodsList.push(goodsItem);
                }
            }
            return goodsList;
        };
        var wishQueryParam = {
            id: getId(),
            vin: getVin(),
            isDeckVehicle: isDeckVehicle(),
            isModifiedVehicle: isModifiedVehicle(),
            isReceiptPrice: isReceiptPrice(),
            brand: getBrand(),
            series: getSeries(),
            model: getModel(),
            engine: getEngine(),
            year: getYear(),
            carModel: getCarModel(),
            companyName: getCompanyName(),
            telephone: getTelephone(),
            wishListMemo: getWishListMemo(),
            wishListMaker: getWishListMaker(),
            wishListMakerTel: getWishListMakerTel(),
            goodsList: getWishListGoodsList(),
            token: $('#token').val()
        };
        return wishQueryParam;
    };

    //创建需求单
    var createWish = function () {
        var requestParam;
        try {
            requestParam = JSON.stringify(getWish());
        } catch (message) {
            EPC.alertFucWithTime(1500, message);
            return false;
        }

        JqUI.loading(_body);

        JqAjax.post('/wish/create', function(result){
            if (result.success) {
                CREATE_WISH.alertSuccessWish();
            } else {
                if(result.data==null){
                    EPC.alertFuc(result.message);
                }else{
                    var div = '<div style="text-align: left;font-size: 14px;">' + _unOpenCityMsg + '</div>';
                    EPC.alertFuc(div);
                }
            }

            JqUI.unblockUI(_body);

        }, requestParam, undefined, 'application/json');

        return false;
    };

    return {
        init: function () {
            init_data();
            init_dom();
            init_event();
        },
        deleteImg: deleteImg,
        addGoodsItem: addGoodsItem,
        deleteGoodsItem: function (dom, url) {
            deleteGoodsItem(dom, url);
        },
        createWish: function () {
            createWish();
        },
        alertSuccessWish: function () {
            $('#alert_success_wish').modal('show');
        }
    }
}();

//初始化
CREATE_WISH.init();