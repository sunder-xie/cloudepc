function changeSearchType(e,t){TipUtil.removeTip();var a=$("#car-type-search-input");switch(a.val(""),$(".choose-style").removeClass("choose-style"),$(t).addClass("choose-style"),e){case 0:a.unbind("keyup"),a.attr("placeholder",_keywordSearchPlaceholder),a.removeAttr("maxlength");break;case 1:a.unbind("keyup").on("keyup",function(){$(this).val($(this).val().replace(/[\u4E00-\u9FA5]/g,""))}),a.attr("placeholder",_oeSearchPlaceholder),a.removeAttr("maxlength");break;default:a.unbind("keyup").on("keyup",function(){$(this).val($(this).val().replace(/[\u4E00-\u9FA5]/g,""))}),a.attr("placeholder",_vinSearchPlaceholder),a.attr("maxlength",_vinLength)}a.focus()}function carSearch(){var e=$("#car-type-search-input");TipUtil.destroyTip(e);var t=Number($(".choose-style").attr("data-type")),a=e.val().trim();if(0==t){if(""==a)return TipUtil.tooltipFun(e,_keywordSearchTip,"bottom"),!1;window.location.href="/autoParts/goods/keyword?q="+a}if(1==t){if(""==a)return TipUtil.tooltipFun(e,_oeSearchTip,"bottom"),!1;window.location.href="/autoParts/goods/oe?oem="+a}if(2==t){if(a=a.toUpperCase(),!EPC.isVin(a))return TipUtil.tooltipFun(e,_vinSearchTip,"bottom"),!1;JqAjax.post("/epc/car/getOnlineCarByVin",getVinResult,{vin:a})}}function getVinResult(e){var t=$("#car-type-search-input").val().trim(),a="/autoParts/category/category?carId=CARID&vinNumber="+t;if(e.success){var n=e.data;if(1==n.length)window.location.href=a.replace("CARID",n[0].id)+"&seriesId="+n[0].seriesId;else{var i=template("choose_car_template",{list:n});EPC.alertFuc(i),$(".choose_car").unbind("click").click(function(){var e=$(this).attr("data-id"),t=$(this).attr("data-sid");window.location.href=a.replace("CARID",e)+"&seriesId="+t})}}else EPC.alertFuc("很抱歉，未查询到您提交的车辆信息。");return!1}function changeView(e,t){void 0!==t&&($(".wish-right-top li").removeClass("current"),$(t).addClass("current")),void 0!==competitorInfoIntervalId&&window.clearInterval(competitorInfoIntervalId),_num=e,1==e?loadWaitWishData(1):loadFinishedWishData(1)}function getSearchParam(e){var t={pageIndex:e},a=$(".wish-search-input").val().trim();return""!=a&&(t.keyword=a),t}function loadWaitWishData(e){offerInfoMap={},JqUI.loading(_body),JqAjax.get("/wish/wait",function(t){t.success?(_wishLists=t.list,$("#wishListContent").html(template("waitWishTemplate",{waitList:_wishLists})),competitorInfo(),competitorInfoIntervalId=window.setInterval(competitorInfo,6e4),pageInit(e,t.total)):($("#wishListContent").html(template("noWishTemplate",{msg:"您还没有相关的需求单"})),$(".qxy_page").empty()),ImgUtil.init($(".form-three-image"),["click"]),JqUI.unblockUI(_body)},getSearchParam(e))}function loadFinishedWishData(e){JqUI.loading(_body),JqAjax.get("/wish/complete",function(t){if(t.success){var a=t.list;$("#wishListContent").html(template("finishedWishTemplate",{dataList:a})),pageInit(e,t.total),initImIcon("#wishListContent")}else $("#wishListContent").html(template("noWishTemplate",{msg:"您还没有相关的需求单"})),$(".qxy_page").empty();ImgUtil.init($(".form-three-image2"),["click"]),JqUI.unblockUI(_body)},getSearchParam(e))}function initImIcon(e){$(e).on("mouseover",function(){TipUtil.addTip($(e).find(".im-icon"),"点我聊天","right")}),$(".im-icon").unbind().on("click",function(){var e=$(this),t=e.data("seller-id"),a=e.data("sn"),n=e.data("vin"),i=e.data("car"),r="/monkChat/requestList?orderSn="+a+"&vin="+n+"&carType="+i;TqmallChat.openChatWindow(t,"lop",r,WishChatMap.bottom_url,WishChatMap.bottom_url_btn_name,WishChatMap.guide_url,WishChatMap.guide_content)})}function competitorInfo(){for(var e=_wishLists.length,t=0;e>t;t++)$.get("/wish/competitorInfo",{wishListId:_wishLists[t].wishListId},function(e){if(e.success){for(var t,a=e.data,n=a[0].wishListId,i="#"+n,r=a.length,o=0;r>o;o++){var c=a[o].wishListId+"-"+a[o].sellerId;if(void 0!==offerInfoMap[c]){t=c;break}}var s=$(i),l={sn:s.data("sn"),vin:s.data("vin"),car:s.data("car"),dataList:e.data};$(i).html(template("competitorInfoTemplate",l)),void 0!==t&&$(".offer-btn-"+t).trigger("click"),initImIcon(i)}else $("#"+e.data).html(template("noCompetitorTemplate"))})}function getOfferInfo(e){var t=$(e).parent();t.addClass("cur"),t.siblings(".mask").addClass("show"),$(e).next().removeClass("hidden");var a=0,n=$(e).attr("data-wishListId"),i=$(e).attr("data-sellerId");offerInfoMap[n+"-"+i]=1,console.log(offerInfoMap),JqAjax.get("/wish/offer/goods",function(e){if(e.success){var t={},i=e.data.offerListGoodsList;$.each(i,function(e,a){var n=t[a.groupId];void 0===n&&(n=[],t[a.groupId]=n),n.push(a)});var r=0;$.each(t,function(e,t){a+=t[0].goodsPriceAmount,r=t[0].offerListId});var o={dataMap:t,offerListMemo:e.data.offerListMemo,total:a.toFixed(2),offerId:r,wishListId:n};$(".offer-info-"+n).html(template("offerInfoTemplate",o))}},{sellerId:i,wishListId:n})}function closeCompetitor(e){var t=$(e).parent();t.removeClass("cur"),t.siblings(".mask").removeClass("show"),$(e).addClass("hidden");var a=$(e).data("wish-id");$(".offer-info-"+a).empty();var n=$(e).data("seller-id");delete offerInfoMap[a+"-"+n]}function checkedGoods(e,t){1==$(e).prop("checked")&&($(e).parents("dd").find("input[name='checkedGoods']").prop("checked",!1),$(e).prop("checked",!0));for(var a=0,n=$(e).parents("dl").find("input[type='checkBox']:checked").parents("ul").find("li[name='singleTotal']"),i=0;i<n.length;i++){var r=n[i].innerText;a+=parseFloat(r)}0==a&&(a="0.00"),$(e).parents("div").find("span[name='total']").text(a)}function cancelWish(e){CANCEL_WISH.callback=function(){send(_pageIndex)},CANCEL_WISH.alertCancelWish(e)}function reCreateWish(e){var t=$(e).data("wish-id");window.location.href="/wish/createWishPage?wishListId="+t}function confirmWish(e){if("0.00"==$(e).parent().find("span[name='total']")[0].innerText)return void EPC.alertFuc("请选择要确认购买的商品!");var t=[],a=$(e).data("offer-id"),n=$(e).data("wish-id"),i=$("#"+n).next().find("input[type='checkBox']:checked");i.each(function(e,a){t.push($(a).data("goods-id"))});var r={wishListId:n,offerListId:a,offerListGoodsIds:t.join(",")};JqUI.loading(_body),JqAjax.post("/wish/chooseOfferGoods",function(e){e.success?window.location.href="/wish/consignee?offerId="+a:EPC.alertFuc(e.message),JqUI.unblockUI(_body)},r)}!function(){function e(e){return e.replace(y,"").replace(b,",").replace(w,"").replace(_,"").replace(k,"").split(C)}function t(e){return"'"+e.replace(/('|\\)/g,"\\$1").replace(/\r/g,"\\r").replace(/\n/g,"\\n")+"'"}function a(a,n){function i(e){return d+=e.split(/\n/).length-1,p&&(e=e.replace(/\s+/g," ").replace(/<!--[\w\W]*?-->/g,"")),e&&(e=$[1]+t(e)+$[2]+"\n"),e}function r(t){var a=d;if(l?t=l(t,n):o&&(t=t.replace(/\n/g,function(){return d++,"$line="+d+";"})),0===t.indexOf("=")){var i=u&&!/^=[=#]/.test(t);if(t=t.replace(/^=[=#]?|[\s;]*$/g,""),i){var r=t.replace(/\s*\([^\)]+\)/,"");f[r]||/^(include|print)$/.test(r)||(t="$escape("+t+")")}else t="$string("+t+")";t=$[1]+t+$[2]}return o&&(t="$line="+a+";"+t),v(e(t),function(e){if(e&&!m[e]){var t;t="print"===e?b:"include"===e?w:f[e]?"$utils."+e:h[e]?"$helpers."+e:"$data."+e,_+=e+"="+t+",",m[e]=!0}}),t+"\n"}var o=n.debug,c=n.openTag,s=n.closeTag,l=n.parser,p=n.compress,u=n.escape,d=1,m={$data:1,$filename:1,$utils:1,$helpers:1,$out:1,$line:1},g="".trim,$=g?["$out='';","$out+=",";","$out"]:["$out=[];","$out.push(",");","$out.join('')"],y=g?"$out+=text;return $out;":"$out.push(text);",b="function(){var text=''.concat.apply('',arguments);"+y+"}",w="function(filename,data){data=data||$data;var text=$utils.$include(filename,data,$filename);"+y+"}",_="'use strict';var $utils=this,$helpers=$utils.$helpers,"+(o?"$line=0,":""),k=$[0],C="return new String("+$[3]+");";v(a.split(c),function(e){e=e.split(s);var t=e[0],a=e[1];1===e.length?k+=i(t):(k+=r(t),a&&(k+=i(a)))});var I=_+k+C;o&&(I="try{"+I+"}catch(e){throw {filename:$filename,name:'Render Error',message:e.message,line:$line,source:"+t(a)+".split(/\\n/)[$line-1].replace(/^\\s+/,'')};}");try{var x=new Function("$data","$filename",I);return x.prototype=f,x}catch(q){throw q.temp="function anonymous($data,$filename) {"+I+"}",q}}var n=function(e,t){return"string"==typeof t?g(t,{filename:e}):o(e,t)};n.version="3.0.0",n.config=function(e,t){i[e]=t};var i=n.defaults={openTag:"<%",closeTag:"%>",escape:!0,cache:!0,compress:!1,parser:null},r=n.cache={};n.render=function(e,t){return g(e,t)};var o=n.renderFile=function(e,t){var a=n.get(e)||m({filename:e,name:"Render Error",message:"Template not found"});return t?a(t):a};n.get=function(e){var t;if(r[e])t=r[e];else if("object"==typeof document){var a=document.getElementById(e);if(a){var n=(a.value||a.innerHTML).replace(/^\s*|\s*$/g,"");t=g(n,{filename:e})}}return t};var c=function(e,t){return"string"!=typeof e&&(t=typeof e,"number"===t?e+="":e="function"===t?c(e.call(e)):""),e},s={"<":"&#60;",">":"&#62;",'"':"&#34;","'":"&#39;","&":"&#38;"},l=function(e){return s[e]},p=function(e){return c(e).replace(/&(?![\w#]+;)|[<>"']/g,l)},u=Array.isArray||function(e){return"[object Array]"==={}.toString.call(e)},d=function(e,t){var a,n;if(u(e))for(a=0,n=e.length;n>a;a++)t.call(e,e[a],a,e);else for(a in e)t.call(e,e[a],a)},f=n.utils={$helpers:{},$include:o,$string:c,$escape:p,$each:d};n.helper=function(e,t){h[e]=t};var h=n.helpers=f.$helpers;n.onerror=function(e){var t="Template Error\n\n";for(var a in e)t+="<"+a+">\n"+e[a]+"\n\n";"object"==typeof console&&console.error(t)};var m=function(e){return n.onerror(e),function(){return"{Template Error}"}},g=n.compile=function(e,t){function n(a){try{return new s(a,c)+""}catch(n){return t.debug?m(n)():(t.debug=!0,g(e,t)(a))}}t=t||{};for(var o in i)void 0===t[o]&&(t[o]=i[o]);var c=t.filename;try{var s=a(e,t)}catch(l){return l.filename=c||"anonymous",l.name="Syntax Error",m(l)}return n.prototype=s.prototype,n.toString=function(){return s.toString()},c&&t.cache&&(r[c]=n),n},v=f.$each,$="break,case,catch,continue,debugger,default,delete,do,else,false,finally,for,function,if,in,instanceof,new,null,return,switch,this,throw,true,try,typeof,var,void,while,with,abstract,boolean,byte,char,class,const,double,enum,export,extends,final,float,goto,implements,import,int,interface,long,native,package,private,protected,public,short,static,super,synchronized,throws,transient,volatile,arguments,let,yield,undefined",y=/\/\*[\w\W]*?\*\/|\/\/[^\n]*\n|\/\/[^\n]*$|"(?:[^"\\]|\\[\w\W])*"|'(?:[^'\\]|\\[\w\W])*'|\s*\.\s*[$\w\.]+/g,b=/[^\w$]+/g,w=new RegExp(["\\b"+$.replace(/,/g,"\\b|\\b")+"\\b"].join("|"),"g"),_=/^\d[^,]*|,\d[^,]*/g,k=/^,+|,+$/g,C=/^$|,+/;i.openTag="{{",i.closeTag="}}";var I=function(e,t){var a=t.split(":"),n=a.shift(),i=a.join(":")||"";return i&&(i=", "+i),"$helpers."+n+"("+e+i+")"};i.parser=function(e){e=e.replace(/^\s/,"");var t=e.split(" "),a=t.shift(),i=t.join(" ");switch(a){case"if":e="if("+i+"){";break;case"else":t="if"===t.shift()?" if("+t.join(" ")+")":"",e="}else"+t+"{";break;case"/if":e="}";break;case"each":var r=t[0]||"$data",o=t[1]||"as",c=t[2]||"$value",s=t[3]||"$index",l=c+","+s;"as"!==o&&(r="[]"),e="$each("+r+",function("+l+"){";break;case"/each":e="});";break;case"echo":e="print("+i+");";break;case"print":case"include":e=a+"("+t.join(",")+");";break;default:if(/^\s*\|\s*[\w\$]/.test(i)){var p=!0;0===e.indexOf("#")&&(e=e.substr(1),p=!1);for(var u=0,d=e.split("|"),f=d.length,h=d[u++];f>u;u++)h=I(h,d[u]);e=(p?"=":"=#")+h}else e=n.helpers[a]?"=#"+a+"("+t.join(",")+");":"="+e}return e},"function"==typeof define?define(function(){return n}):"undefined"!=typeof exports?module.exports=n:this.template=n}();var storage=window.sessionStorage,key="autoParts_crumbs";$(function(){var e="";switch(Number($(".choose-style").attr("data-type"))){case 1:e=_oeSearchPlaceholder;break;case 2:e=_vinSearchPlaceholder;break;default:e=_keywordSearchPlaceholder}$("#car-type-search-input").bind("keypress",function(e){13==e.keyCode&&$("#car-type-head-searchBtn").trigger("click")}).attr("placeholder",e)});var JqPage={init:function(e){var t=$.extend({dom:$(".qxy_page"),itemSize:0,pageSize:10,current:0,backFn:function(){}},e);t.pageCount="number"==typeof t.itemSize&&"number"==typeof t.pageSize?Math.ceil(t.itemSize/t.pageSize):0,t.width=t.dom.width(),JqPage.toInit(t)},toInit:function(e){e.dom.addClass("qxy_page"),$(".qxy_page_inner:eq(0)",e.dom).length||e.dom.append('<div class="qxy_page_inner">'),JqPage.fillHtml(e),e.dom.append("</div>"),JqPage.bindEvent(e)},fillHtml:function(e){var t=$(".qxy_page_inner:eq(0)",e.dom);t.empty(),t.append('<span class="disabled qxy_page_count">共'+e.itemSize+"条记录</span>"),e.current<1e4&&(e.current>1&&e.current<=e.pageCount?(e.width>900&&t.append('<a class="qxy_page_first" href="javascript:;">首页</a>'),t.append('<a href="javascript:;" class="qxy_page_prev">上一页</a>')):(t.remove(".prevPage"),e.width>900&&t.append('<span class="disabled">首页</span>'),t.append('<span class="disabled">上一页</span>'))),1==e.current?t.append('<span class="yqx-pg-current">1</span>'):t.append('<a href="javascript:;" class="qxy_page_num">1</a>'),e.current>=4&&e.pageCount>5&&t.append("<span>...</span>");var a=e.current-1,n=e.current+1;for(e.current>e.pageCount-3&&(a=e.pageCount-3),1>a&&(a=1),e.current<4&&e.pageCount>=4&&(n=e.pageCount>4?4:4==e.pageCount?3:e.pageCount-1),1==e.pageCount&&(n=1);n>=a;a++)a<e.pageCount&&a>1&&(a!=e.current?t.append('<a href="javascript:;" class="qxy_page_num">'+a+"</a>"):t.append('<span class="yqx-pg-current">'+a+"</span>"));e.current+1<e.pageCount-1&&e.pageCount>5&&t.append("<span>...</span>"),e.pageCount>1&&(e.current==e.pageCount?t.append('<span class="yqx-pg-current">'+e.pageCount+"</span>"):t.append('<a href="javascript:;" class="qxy_page_num">'+e.pageCount+"</a>")),e.current<1e4&&(e.current<e.pageCount?(t.append('<a href="javascript:;" class="qxy_page_next">下一页</a>'),e.width>900&&t.append('<a class="qxy_page_last" href="javascript:;">末页</a>')):(t.remove(".nextPage"),t.append('<span class="disabled">下一页</span>'),e.width>900&&t.append('<span class="disabled qxy_page_last">末页</span>'))),e.pageCount<=1?(t.append('<span class="qxy_go_num disabled">共'+e.pageCount+'页,去第 <input type="text" disabled="disabled" value=""> 页</span>'),t.append('<span class="qxy_go_btn disabled" href="javascript:;">跳转</span>')):(t.append('<span class="qxy_go_num">共'+e.pageCount+'页,去第 <input type="text" value=""> 页</span>'),t.append('<a class="qxy_go_btn" href="javascript:;">跳转</a>'))},bindEvent:function(e){e.dom.off("click","a.qxy_page_num").on("click","a.qxy_page_num",function(){var t=parseInt($(this).text());JqPage.fillHtml({current:t,pageCount:e.pageCount,dom:e.dom,itemSize:e.itemSize}),"function"==typeof e.backFn&&e.backFn(t)}),e.dom.off("click","a.qxy_page_prev").on("click","a.qxy_page_prev",function(){var t=parseInt($("span.yqx-pg-current",e.dom).text());1!=t&&(JqPage.fillHtml({current:t-1,pageCount:e.pageCount,dom:e.dom,itemSize:e.itemSize}),"function"==typeof e.backFn&&e.backFn(t-1))}),e.dom.off("click","a.qxy_page_next").on("click","a.qxy_page_next",function(){var t=parseInt($("span.yqx-pg-current",e.dom).text());t!=e.pageCount&&(JqPage.fillHtml({current:t+1,pageCount:e.pageCount,dom:e.dom,itemSize:e.itemSize}),"function"==typeof e.backFn&&e.backFn(t+1))}),e.dom.off("click","a.qxy_page_first").on("click","a.qxy_page_first",function(){JqPage.fillHtml({current:1,pageCount:e.pageCount,dom:e.dom,itemSize:e.itemSize}),"function"==typeof e.backFn&&e.backFn(1)}),e.dom.off("click","a.qxy_page_last").on("click","a.qxy_page_last",function(){JqPage.fillHtml({current:e.pageCount,pageCount:e.pageCount,dom:e.dom,itemSize:e.itemSize}),"function"==typeof e.backFn&&e.backFn(e.pageCount)}),e.dom.off("click","a.qxy_go_btn").on("click","a.qxy_go_btn",function(){var t=parseInt(e.dom.find("span.yqx-pg-current").text()),a=$(this).prev().find("input"),n=parseInt(a.val());return n>e.pageCount||!$.isNumeric(n)||n==t?void a.val("").focus():(JqPage.fillHtml({current:e.pageCount,pageCount:e.pageCount,dom:e.dom,itemSize:e.itemSize}),void("function"==typeof e.backFn&&e.backFn(n||t)))})}},CANCEL_WISH=function(){var e=function(e){void 0!=$(e)&&null!=$(e)&&$(e).modal("hide")},t=$("#alert_cancel_wish"),a=function(e,a){void 0!=t&&(n(e,a),t.modal("show"))},n=function(e,a){var n=t.find("input[type=checkbox]");$("#acwOtherReason").val(""),$(n).prop("checked",!1),$(n[0]).prop("checked",!0),n.on("click",function(){t.find("input[type=checkbox]").prop("checked",!1),$(this).prop("checked",!0)}),t.find("div[id=acwSave]").unbind().on("click",function(){i(e,a)})},i=function(e,a){for(var n,i=t.find("input[type=checkbox]"),o=0;o<i.length;o++)1==$(i[o]).prop("checked")&&(n=$(i[o]).attr("title"));return void 0==n||null==n||""==n?void EPC.alertFucWithTime(1500,"请选择取消原因!"):"其他"==n&&(n=t.find("input[id=acwOtherReason]").val().trim(),""==n)?void EPC.alertFucWithTime(1500,"请填写具体原因!"):void(1==confirm("您确定要取消吗？")&&r(e,a,n))},r=function(e,a,n){var i={wishId:e,offerId:a,reason:n};$.ajax({type:"POST",url:"/wish/cancel",data:JSON.stringify(i),headers:{Accept:"application/json","Content-Type":"application/json"},beforeSend:function(e){var t=(new Date).getTime();e.setRequestHeader("timestamp",t)},success:function(e){e.success?(CANCEL_WISH.closeAlert(t),EPC.alertFuc("取消成功!"),$.isFunction(CANCEL_WISH.callback)&&CANCEL_WISH.callback()):EPC.alertFuc(e.message)},error:function(e){}})};return{closeAlert:function(t){e(t)},alertCancelWish:function(e,t){return null==e||void 0==e?!1:void a(e,t)},callback:void 0}}();template.helper("feedbackGoodsHelper",function(e){return $.isEmptyObject(e)?"":template("feedbackGoodsTemplate",{data:e})}),template.helper("emptyHelper",function(e){return null==e||""==e?"无":e}),template.helper("receiptHelper",function(e){if(null==e||void 0==e)return"";var t="";switch(e){case 0:t="不带票";break;case 1:t="普通发票";break;default:t="增值发票"}return t});var _wishLists=[],_num=1,_pageIndex=1,_body=$("body");$(function(){changeView(1),$("#myWish").addClass("active"),$(".wish-search-button").click(function(){send(1)}),$(".wish-search-input").bind("keypress",function(e){13==e.keyCode&&$(".wish-search-button").trigger("click")}),$(".wish-show-all").click(function(){$(".wish-search-input").val(""),$(".wish-search-button").trigger("click")})});var pageInit=function(e,t){JqPage.init({itemSize:t,pageSize:5,current:e||1,backFn:function(e){send(e)}})},send=function(e){_pageIndex=e,1==_num?loadWaitWishData(e):loadFinishedWishData(e)},competitorInfoIntervalId,offerInfoMap={};