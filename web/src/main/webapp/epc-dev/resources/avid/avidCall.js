var body = $('body');
var avidTipDiv = $('.avid-tip-div');
var avidTipDivTop = avidTipDiv.get(0).offsetTop;

var allCarBrandSeriesResult;

$(function () {

    getCarBrandForFilter();

    $('.avid-call-btn').click(function(){
        submitAvidCall();
    });

    //添加滚动条监听
    $(window).scroll(function(){
        //console.log(avidTipDivTop+'  '+$(document).scrollTop());
        if($(document).scrollTop() >= avidTipDivTop){
            avidTipDiv.addClass('fixed');
            $('#carTypeBody').css('padding-top', avidTipDiv.height()+20);
        }else{
            avidTipDiv.removeClass('fixed');
            $('#carTypeBody').css('padding-top', 0);
        }
    });

});

function getCarBrandForFilter(){

    JqUI.loading(body);

    Ajax.get({
        url: '/epc/car/allCarBrandSeries',
        success: function (result) {
            if (result.success) {
                $('#firstWord-li').html(template('carTypeHeaderTemplate', result));
                $('#firstWord-li span:first').addClass('backColor');
                $('#carTypeBody').html(template('carTypeBodyTemplate', result));

                bindClickOnCarModel();

                allCarBrandSeriesResult = result;
            }

            JqUI.unblockUI(body);

            if(result.success){
                //页面功能引导
                pageGuide();

            }
        }
    });

}

//点击字母检索
function firstWord(el) {
    $('#firstWord-li span').removeClass('backColor');
    var firstWord = $(el).attr('id');

    $(el).addClass('backColor');
    $('#carTypeBody').html(template('carTypeBodyTemplate', handleResultMap(allCarBrandSeriesResult, firstWord)));

    bindClickOnCarModel();

    body.animate({scrollTop: avidTipDivTop}, 500);

    initAvidCallBtn();
}

function handleResultMap(result, firstWord) {
    if(firstWord==-1){
        return result;
    }

    var obj = {};
    obj[firstWord] = result.data[firstWord];

    return {data: obj};
}

function initAvidCallBtn(){
    layer.closeAll('tips');
    //$('.avid-call-btn').removeClass('hidden');
}

function bindClickOnCarModel() {
    $(".series_name_span").unbind("click").click(function () {
        $('.series-span-selected').removeClass('series-span-selected');
        var $this = $(this);
        $this.addClass('series-span-selected');

        var position = $this.position();
        //console.log('position left:'+position.left+'  top:'+position.top);

        //var html = '<div class="tip-avid-call-btn"><img src="/img/lop/avid_call_btn_logo.png"><b title="关闭" class="close-avid-btn"></b></div>';
        var html = $('#avidCallBtnTemplate').html();

        var index = layer.tips(html, $this, {
            tips: [1, '#fff'],
            time: 600000
        });

        var tips = $('.layui-layer-tips');
        tips.addClass('layui-layer-tips-fix');
        tips.find('i').addClass('hidden');
        var top = tips.css('top');
        //console.log(top);
        tips.css('top', parseInt(top.replace('px', ''))+18);

        $('.avid-call-btn').addClass('hidden');

        $('.tip-avid-call-btn').click(function(){
            submitAvidCall();
        });
        $('.close-avid-btn').click(function(){
            initAvidCallBtn();
        });
    });
}

function submitAvidCall(){
    var series = $('.series-span-selected');
    if(series.length==0){
        LayerUtil.msg('请选择需要急呼的车型');
        return;
    }

    var seriesId = series.attr('data-id');

    JqUI.loading(body);

    Ajax.post({
        url: '/avidCall/create',
        data: {carSeriesId : seriesId},
        success: function(result){
            //console.log(result);
            if(result.success){
                LayerUtil.msg('配件管家会尽快与您联系，请保持手机畅通，周六日无法提供服务', 5000, function(){
                    $('.series-span-selected').removeClass('series-span-selected');
                    initAvidCallBtn();
                });
            }else{
                if(result.data==null){
                    LayerUtil.msg('非常抱歉，急呼失败了，请重试');
                }else{
                    var div = '<div style="text-align: left;font-size: 14px;">' + _unOpenCityMsg + '</div>';
                    EPC.alertFuc(div);
                }
            }

            JqUI.unblockUI(body);
        }
    });
}

/* 页面功能引导 */
function pageGuide(){
    //判断是否需要引导
    var pageUriId = parseInt($('#pageUriId').val());
    if(!isNaN(pageUriId)){
        //alert('新功能，且未访问过，需要引导，id:'+pageUriId);

        var car = $(".series_name_span").eq(0);
        var offset = car.offset();
        //console.log("offset left: " + offset.left + ", top: " + offset.top);
        //left: 191, top: 526
        //var position = car.position();
        //console.log("position left: " + position.left + ", top: " + position.top);

        //var html = '<div class="avid_call_guide_step_1"><span>'+car.text()+'</span></div>';
        var html = template('avidCallGuideStep1', {data: car.text()});

        //var top = 428;
        //var left = 123;
        var top = offset.top - 98;
        var left = offset.left - 68;

        body.block({
            message: html,
            centerY: false,
            css: {
                top: top,
                border: 'none',
                padding: '2px',
                backgroundColor: 'none',
                cursor: 'pointer'
            },
            overlayCSS: {
                backgroundColor: '#000',
                opacity: 0.6,
                cursor: 'pointer'
            }
        });

        $('.blockMsg').css('left', left);

        $('.blockUI').click(function(){
            guideStepFun();
        });

        var bodyScrollTop = offset.top/2;
        //console.log(bodyScrollTop);
        body.animate({scrollTop: bodyScrollTop}, 200);
    }

}
var guideStepToId1;
var guideStepToId2;
function guideStepFun(){
    var step1 = $('.avid_call_guide_step_1');
    if(step1.length==1){
        if(guideStepToId1 !== undefined){
            return;
        }
        guideStepToId1 = setTimeout(function(){
            var blockMsg = $('.blockMsg');
            var top = parseInt(blockMsg.css('top').replace('px', '')) + 34;
            var left = parseInt(blockMsg.css('left').replace('px', '')) - 28;
            blockMsg.html('<img src="/img/lop/avid_call_guide_step2.png">').css({top: top, left: left});
        }, 500);

        /* 添加访问记录 */
        addPageGuideRecord();

    }else{
        if(guideStepToId2 === undefined){
            guideStepToId2 = setTimeout(function(){
                JqUI.unblockUI(body);
            }, 500);
        }

    }

}
function addPageGuideRecord(){
    $.ajax({
        url: '/common/addPageGuideRecord',
        data: {pageUriId: $('#pageUriId').val()},
        success: function(result){

        }
    });
}
