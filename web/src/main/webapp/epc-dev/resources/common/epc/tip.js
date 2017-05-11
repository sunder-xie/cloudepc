/**
 * Created by huangzhangting on 16/7/11.
 */

/** 提示信息工具类，依赖于tooltip插件 */
var TO;
var TipUtil = {
    tooltipFun: function(obj, msg, place, time) {
        if (place === void 0 || place === null) {
            place = 'top';
        }
        $(obj).attr({'title': msg, 'data-placement': place}).tooltip('fixTitle').tooltip('show');

        if (time === void 0) {
            time = 2000;
        }
        if (TO !== void 0) {
            clearTimeout(TO); //避免多次点击，展示效果不理想
        }
        TO = setTimeout(function () {
            TipUtil.destroyTip(obj);
        }, time);
    },
    addTip: function(obj, msg, place) {
        if(place===undefined || place===null){
            place = 'top';
        }
        $(obj).attr({'title': msg, 'data-placement': place}).tooltip();
    },
    showTip: function(obj, msg, place) {
        if(msg !== null && msg !== undefined){
            if (place === undefined || place === null) {
                place = 'top';
            }
            $(obj).attr({'title': msg, 'data-placement': place}).tooltip('fixTitle');
        }
        $(obj).tooltip('show');
    },
    destroyTip: function(obj) {
        $(obj).tooltip('destroy');
    },
    removeTip: function(){
        $('.tooltip').remove();
    },
    developing: function(el, place){
        TipUtil.tooltipFun(el, '该功能还在开发中', place);
    }
};
