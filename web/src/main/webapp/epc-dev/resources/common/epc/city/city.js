/**
 * Created by huangzhangting on 16/7/11.
 */
//默认杭州
var CurrentCityId = 383;
var ReloadPageFlag = false;

var SetCityCallback = {
    callback: undefined
};

var CityUtil = {
    /** 获取全部城市站 */
    getAllCity: function(){
        //var region = $.cookie('user_region_info_ck');

        JqAjax.get('/region/allCity', function(result){
            if(result.length>0){
                CityUtil.initDropdownCity(result);
            }
        });
    },
    /** 城市站下拉框初始化 */
    initDropdownCity: function(cityTreeData){
        var html = '';
        var children;
        var maxChildrenNum = 1;
        var maxWordsNum = 1;
        var wordsNum = 1;
        var proSize = cityTreeData.length;
        for(var i=0; i<proSize; i++){
            html += '<p><span class="province-span">' + cityTreeData[i].name + ':</span>';

            wordsNum = cityTreeData[i].name.length;
            children = cityTreeData[i].children;
            var citySize = children.length;
            for(var j=0; j<citySize; j++){
                html += '<span class="city-span" data-id="'+children[j].id+'">' + children[j].name + '</span>';

                wordsNum += children[j].name.length;

                //if(cityId==children[j].id){
                //    CurrentCityId = cityId;
                //    CityUtil.setCity(children[j].name);
                //}
            }
            html += '</p>';

            if(citySize>maxChildrenNum){
                maxChildrenNum = citySize;
            }
            if(wordsNum>maxWordsNum){
                maxWordsNum = wordsNum;
            }
        }

        //每个span左右margin各5 + font-size：14 + 补偿值：5
        var finalWidth = 10*(maxChildrenNum+1) + 14*maxWordsNum + 5;
        //每个p 标签 30 + 补偿值：5
        var finalHeight = 30*(proSize+1) + 5;

        var maxHeight = 450;
        var maxWidth = 700;
        if(finalHeight>maxHeight){
            finalHeight = maxHeight;
        }
        if(finalWidth>maxWidth){
            finalWidth = maxWidth;
        }

        $('#dropdownCityMenu').width(finalWidth).height(finalHeight).html(html);

        CityUtil.bindSelectCity();
    },
    /** 绑定选择城市事件 */
    bindSelectCity: function(){
        $('.city-span').click(function(){
            CityUtil.selectCity(this);
        });
    },
    /** 选择城市 */
    selectCity: function(el){
        CityUtil.setCity($(el).text());
        CurrentCityId = $(el).data('id');

        var body = $('body');
        JqUI.blockUI(body);

        JqAjax.post('/user/selectCity', function(result){
            if(result.success){

                //加载header 中购物车中商品数量图标小提示
                if(IsLogin===1){
                    ShoppingCart.reloadCartNum();
                }

                if($.isFunction(SetCityCallback.callback)){
                    SetCityCallback.callback();
                }

                //JqUI.loading(body);
                //
                //Ajax.post({
                //    url: '/user/checkCityForWish',
                //    data: {cityId: CurrentCityId},
                //    success: function(rst){
                //        if(rst.success){
                //            $('.create-wish').removeClass('hidden');
                //        }else{
                //            $('.create-wish').addClass('hidden');
                //            var div = '<div style="text-align: left;font-size: 14px;">发布需求单功能，目前开放城市站如下：<br><br>'+
                //                rst.data + '</div>';
                //            EPC.alertFuc(div);
                //        }
                //
                //        JqUI.unblockUI(body);
                //    }
                //});
            }

            JqUI.unblockUI(body);

        }, {cityId: CurrentCityId});
    },
    setCity: function(city){
        $('#citySelect').text(city);
    },
    checkOpenCity: function(url){
        if(url===undefined || url===null){
            url = '/wish/createWishPage';
        }
        var body = $('body');
        JqUI.blockUI(body);

        Ajax.get({
            url: '/user/checkCityForWish',
            success: function(rst){
                if(rst.success){
                    location.href = url;
                }else{
                    var code = rst.code;
                    if('01'==code){
                        var isLoginPage = $('#isLoginPage').val();
                        if(isLoginPage === undefined){
                            EPC.confirmNewFuc('非常抱歉，您登录超时了，请重新登录', function () {
                                location.href = '/user/loginPage';
                            });
                        }
                    }else if('02'==code){
                        EPC.alertFuc(rst.message);
                    }else{
                        var div = '<div style="text-align: left;font-size: 14px;">' + _unOpenCityMsg + '</div>';
                        EPC.alertFuc(div);
                    }
                }

                JqUI.unblockUI(body);
            }
        });
    },
    checkCreateWish: function(){
        CityUtil.checkOpenCity();
    },
    checkAvidCall: function(){
        CityUtil.checkOpenCity('/avidCall');
    }
};
