function changeSearchType(e,t){TipUtil.removeTip();var a=$("#car-type-search-input");switch(a.val(""),$(".choose-style").removeClass("choose-style"),$(t).addClass("choose-style"),e){case 0:a.unbind("keyup"),a.attr("placeholder",_keywordSearchPlaceholder),a.removeAttr("maxlength");break;case 1:a.unbind("keyup").on("keyup",function(){$(this).val($(this).val().replace(/[\u4E00-\u9FA5]/g,""))}),a.attr("placeholder",_oeSearchPlaceholder),a.removeAttr("maxlength");break;default:a.unbind("keyup").on("keyup",function(){$(this).val($(this).val().replace(/[\u4E00-\u9FA5]/g,""))}),a.attr("placeholder",_vinSearchPlaceholder),a.attr("maxlength",_vinLength)}a.focus()}function carSearch(){var e=$("#car-type-search-input");TipUtil.destroyTip(e);var t=Number($(".choose-style").attr("data-type")),a=e.val().trim();if(0==t){if(""==a)return TipUtil.tooltipFun(e,_keywordSearchTip,"bottom"),!1;window.location.href="/autoParts/goods/keyword?q="+a}if(1==t){if(""==a)return TipUtil.tooltipFun(e,_oeSearchTip,"bottom"),!1;window.location.href="/autoParts/goods/oe?oem="+a}if(2==t){if(a=a.toUpperCase(),!EPC.isVin(a))return TipUtil.tooltipFun(e,_vinSearchTip,"bottom"),!1;JqAjax.post("/epc/car/getOnlineCarByVin",getVinResult,{vin:a})}}function getVinResult(e){var t=$("#car-type-search-input").val().trim(),a="/autoParts/category/category?carId=CARID&vinNumber="+t;if(e.success){var r=e.data;if(1==r.length)window.location.href=a.replace("CARID",r[0].id)+"&seriesId="+r[0].seriesId;else{var o=template("choose_car_template",{list:r});EPC.alertFuc(o),$(".choose_car").unbind("click").click(function(){var e=$(this).attr("data-id"),t=$(this).attr("data-sid");window.location.href=a.replace("CARID",e)+"&seriesId="+t})}}else EPC.alertFuc("很抱歉，未查询到您提交的车辆信息。");return!1}function changeView(e,t){$(".main-left-btn").removeClass("active"),$(e).addClass("active");var a=$(".help-content");a.addClass("hidden"),a.eq(t).removeClass("hidden")}var storage=window.sessionStorage,key="autoParts_crumbs";$(function(){var e="";switch(Number($(".choose-style").attr("data-type"))){case 1:e=_oeSearchPlaceholder;break;case 2:e=_vinSearchPlaceholder;break;default:e=_keywordSearchPlaceholder}$("#car-type-search-input").bind("keypress",function(e){13==e.keyCode&&$("#car-type-head-searchBtn").trigger("click")}).attr("placeholder",e)}),$(function(){});