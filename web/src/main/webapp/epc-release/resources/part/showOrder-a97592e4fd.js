function changeSearchType(e,a){TipUtil.removeTip();var t=$("#car-type-search-input");switch(t.val(""),$(".choose-style").removeClass("choose-style"),$(a).addClass("choose-style"),e){case 0:t.unbind("keyup"),t.attr("placeholder",_keywordSearchPlaceholder),t.removeAttr("maxlength");break;case 1:t.unbind("keyup").on("keyup",function(){$(this).val($(this).val().replace(/[\u4E00-\u9FA5]/g,""))}),t.attr("placeholder",_oeSearchPlaceholder),t.removeAttr("maxlength");break;default:t.unbind("keyup").on("keyup",function(){$(this).val($(this).val().replace(/[\u4E00-\u9FA5]/g,""))}),t.attr("placeholder",_vinSearchPlaceholder),t.attr("maxlength",_vinLength)}t.focus()}function carSearch(){var e=$("#car-type-search-input");TipUtil.destroyTip(e);var a=Number($(".choose-style").attr("data-type")),t=e.val().trim();if(0==a){if(""==t)return TipUtil.tooltipFun(e,_keywordSearchTip,"bottom"),!1;window.location.href="/autoParts/goods/keyword?q="+t}if(1==a){if(""==t)return TipUtil.tooltipFun(e,_oeSearchTip,"bottom"),!1;window.location.href="/autoParts/goods/oe?oem="+t}if(2==a){if(t=t.toUpperCase(),!EPC.isVin(t))return TipUtil.tooltipFun(e,_vinSearchTip,"bottom"),!1;JqAjax.post("/epc/car/getOnlineCarByVin",getVinResult,{vin:t})}}function getVinResult(e){var a=$("#car-type-search-input").val().trim(),t="/autoParts/category/category?carId=CARID&vinNumber="+a;if(e.success){var n=e.data;if(1==n.length)window.location.href=t.replace("CARID",n[0].id)+"&seriesId="+n[0].seriesId;else{var r=template("choose_car_template",{list:n});EPC.alertFuc(r),$(".choose_car").unbind("click").click(function(){var e=$(this).attr("data-id"),a=$(this).attr("data-sid");window.location.href=t.replace("CARID",e)+"&seriesId="+a})}}else EPC.alertFuc("很抱歉，未查询到您提交的车辆信息。");return!1}!function(){function e(e){return e.replace($,"").replace(b,",").replace(_,"").replace(k,"").replace(C,"").split(x)}function a(e){return"'"+e.replace(/('|\\)/g,"\\$1").replace(/\r/g,"\\r").replace(/\n/g,"\\n")+"'"}function t(t,n){function r(e){return d+=e.split(/\n/).length-1,p&&(e=e.replace(/\s+/g," ").replace(/<!--[\w\W]*?-->/g,"")),e&&(e=y[1]+a(e)+y[2]+"\n"),e}function o(a){var t=d;if(l?a=l(a,n):i&&(a=a.replace(/\n/g,function(){return d++,"$line="+d+";"})),0===a.indexOf("=")){var r=u&&!/^=[=#]/.test(a);if(a=a.replace(/^=[=#]?|[\s;]*$/g,""),r){var o=a.replace(/\s*\([^\)]+\)/,"");f[o]||/^(include|print)$/.test(o)||(a="$escape("+a+")")}else a="$string("+a+")";a=y[1]+a+y[2]}return i&&(a="$line="+t+";"+a),v(e(a),function(e){if(e&&!m[e]){var a;a="print"===e?b:"include"===e?_:f[e]?"$utils."+e:g[e]?"$helpers."+e:"$data."+e,k+=e+"="+a+",",m[e]=!0}}),a+"\n"}var i=n.debug,c=n.openTag,s=n.closeTag,l=n.parser,p=n.compress,u=n.escape,d=1,m={$data:1,$filename:1,$utils:1,$helpers:1,$out:1,$line:1},h="".trim,y=h?["$out='';","$out+=",";","$out"]:["$out=[];","$out.push(",");","$out.join('')"],$=h?"$out+=text;return $out;":"$out.push(text);",b="function(){var text=''.concat.apply('',arguments);"+$+"}",_="function(filename,data){data=data||$data;var text=$utils.$include(filename,data,$filename);"+$+"}",k="'use strict';var $utils=this,$helpers=$utils.$helpers,"+(i?"$line=0,":""),C=y[0],x="return new String("+y[3]+");";v(t.split(c),function(e){e=e.split(s);var a=e[0],t=e[1];1===e.length?C+=r(a):(C+=o(a),t&&(C+=r(t)))});var q=k+C+x;i&&(q="try{"+q+"}catch(e){throw {filename:$filename,name:'Render Error',message:e.message,line:$line,source:"+a(t)+".split(/\\n/)[$line-1].replace(/^\\s+/,'')};}");try{var w=new Function("$data","$filename",q);return w.prototype=f,w}catch(S){throw S.temp="function anonymous($data,$filename) {"+q+"}",S}}var n=function(e,a){return"string"==typeof a?h(a,{filename:e}):i(e,a)};n.version="3.0.0",n.config=function(e,a){r[e]=a};var r=n.defaults={openTag:"<%",closeTag:"%>",escape:!0,cache:!0,compress:!1,parser:null},o=n.cache={};n.render=function(e,a){return h(e,a)};var i=n.renderFile=function(e,a){var t=n.get(e)||m({filename:e,name:"Render Error",message:"Template not found"});return a?t(a):t};n.get=function(e){var a;if(o[e])a=o[e];else if("object"==typeof document){var t=document.getElementById(e);if(t){var n=(t.value||t.innerHTML).replace(/^\s*|\s*$/g,"");a=h(n,{filename:e})}}return a};var c=function(e,a){return"string"!=typeof e&&(a=typeof e,"number"===a?e+="":e="function"===a?c(e.call(e)):""),e},s={"<":"&#60;",">":"&#62;",'"':"&#34;","'":"&#39;","&":"&#38;"},l=function(e){return s[e]},p=function(e){return c(e).replace(/&(?![\w#]+;)|[<>"']/g,l)},u=Array.isArray||function(e){return"[object Array]"==={}.toString.call(e)},d=function(e,a){var t,n;if(u(e))for(t=0,n=e.length;n>t;t++)a.call(e,e[t],t,e);else for(t in e)a.call(e,e[t],t)},f=n.utils={$helpers:{},$include:i,$string:c,$escape:p,$each:d};n.helper=function(e,a){g[e]=a};var g=n.helpers=f.$helpers;n.onerror=function(e){var a="Template Error\n\n";for(var t in e)a+="<"+t+">\n"+e[t]+"\n\n";"object"==typeof console&&console.error(a)};var m=function(e){return n.onerror(e),function(){return"{Template Error}"}},h=n.compile=function(e,a){function n(t){try{return new s(t,c)+""}catch(n){return a.debug?m(n)():(a.debug=!0,h(e,a)(t))}}a=a||{};for(var i in r)void 0===a[i]&&(a[i]=r[i]);var c=a.filename;try{var s=t(e,a)}catch(l){return l.filename=c||"anonymous",l.name="Syntax Error",m(l)}return n.prototype=s.prototype,n.toString=function(){return s.toString()},c&&a.cache&&(o[c]=n),n},v=f.$each,y="break,case,catch,continue,debugger,default,delete,do,else,false,finally,for,function,if,in,instanceof,new,null,return,switch,this,throw,true,try,typeof,var,void,while,with,abstract,boolean,byte,char,class,const,double,enum,export,extends,final,float,goto,implements,import,int,interface,long,native,package,private,protected,public,short,static,super,synchronized,throws,transient,volatile,arguments,let,yield,undefined",$=/\/\*[\w\W]*?\*\/|\/\/[^\n]*\n|\/\/[^\n]*$|"(?:[^"\\]|\\[\w\W])*"|'(?:[^'\\]|\\[\w\W])*'|\s*\.\s*[$\w\.]+/g,b=/[^\w$]+/g,_=new RegExp(["\\b"+y.replace(/,/g,"\\b|\\b")+"\\b"].join("|"),"g"),k=/^\d[^,]*|,\d[^,]*/g,C=/^,+|,+$/g,x=/^$|,+/;r.openTag="{{",r.closeTag="}}";var q=function(e,a){var t=a.split(":"),n=t.shift(),r=t.join(":")||"";return r&&(r=", "+r),"$helpers."+n+"("+e+r+")"};r.parser=function(e){e=e.replace(/^\s/,"");var a=e.split(" "),t=a.shift(),r=a.join(" ");switch(t){case"if":e="if("+r+"){";break;case"else":a="if"===a.shift()?" if("+a.join(" ")+")":"",e="}else"+a+"{";break;case"/if":e="}";break;case"each":var o=a[0]||"$data",i=a[1]||"as",c=a[2]||"$value",s=a[3]||"$index",l=c+","+s;"as"!==i&&(o="[]"),e="$each("+o+",function("+l+"){";break;case"/each":e="});";break;case"echo":e="print("+r+");";break;case"print":case"include":e=t+"("+a.join(",")+");";break;default:if(/^\s*\|\s*[\w\$]/.test(r)){var p=!0;0===e.indexOf("#")&&(e=e.substr(1),p=!1);for(var u=0,d=e.split("|"),f=d.length,g=d[u++];f>u;u++)g=q(g,d[u]);e=(p?"=":"=#")+g}else e=n.helpers[t]?"=#"+t+"("+a.join(",")+");":"="+e}return e},"function"==typeof define?define(function(){return n}):"undefined"!=typeof exports?module.exports=n:this.template=n}();var storage=window.sessionStorage,key="autoParts_crumbs";$(function(){var e="";switch(Number($(".choose-style").attr("data-type"))){case 1:e=_oeSearchPlaceholder;break;case 2:e=_vinSearchPlaceholder;break;default:e=_keywordSearchPlaceholder}$("#car-type-search-input").bind("keypress",function(e){13==e.keyCode&&$("#car-type-head-searchBtn").trigger("click")}).attr("placeholder",e)});var JqPage={init:function(e){var a=$.extend({dom:$(".qxy_page"),itemSize:0,pageSize:10,current:0,backFn:function(){}},e);a.pageCount="number"==typeof a.itemSize&&"number"==typeof a.pageSize?Math.ceil(a.itemSize/a.pageSize):0,a.width=a.dom.width(),JqPage.toInit(a)},toInit:function(e){e.dom.addClass("qxy_page"),$(".qxy_page_inner:eq(0)",e.dom).length||e.dom.append('<div class="qxy_page_inner">'),JqPage.fillHtml(e),e.dom.append("</div>"),JqPage.bindEvent(e)},fillHtml:function(e){var a=$(".qxy_page_inner:eq(0)",e.dom);a.empty(),a.append('<span class="disabled qxy_page_count">共'+e.itemSize+"条记录</span>"),e.current<1e4&&(e.current>1&&e.current<=e.pageCount?(e.width>900&&a.append('<a class="qxy_page_first" href="javascript:;">首页</a>'),a.append('<a href="javascript:;" class="qxy_page_prev">上一页</a>')):(a.remove(".prevPage"),e.width>900&&a.append('<span class="disabled">首页</span>'),a.append('<span class="disabled">上一页</span>'))),1==e.current?a.append('<span class="yqx-pg-current">1</span>'):a.append('<a href="javascript:;" class="qxy_page_num">1</a>'),e.current>=4&&e.pageCount>5&&a.append("<span>...</span>");var t=e.current-1,n=e.current+1;for(e.current>e.pageCount-3&&(t=e.pageCount-3),1>t&&(t=1),e.current<4&&e.pageCount>=4&&(n=e.pageCount>4?4:4==e.pageCount?3:e.pageCount-1),1==e.pageCount&&(n=1);n>=t;t++)t<e.pageCount&&t>1&&(t!=e.current?a.append('<a href="javascript:;" class="qxy_page_num">'+t+"</a>"):a.append('<span class="yqx-pg-current">'+t+"</span>"));e.current+1<e.pageCount-1&&e.pageCount>5&&a.append("<span>...</span>"),e.pageCount>1&&(e.current==e.pageCount?a.append('<span class="yqx-pg-current">'+e.pageCount+"</span>"):a.append('<a href="javascript:;" class="qxy_page_num">'+e.pageCount+"</a>")),e.current<1e4&&(e.current<e.pageCount?(a.append('<a href="javascript:;" class="qxy_page_next">下一页</a>'),e.width>900&&a.append('<a class="qxy_page_last" href="javascript:;">末页</a>')):(a.remove(".nextPage"),a.append('<span class="disabled">下一页</span>'),e.width>900&&a.append('<span class="disabled qxy_page_last">末页</span>'))),e.pageCount<=1?(a.append('<span class="qxy_go_num disabled">共'+e.pageCount+'页,去第 <input type="text" disabled="disabled" value=""> 页</span>'),a.append('<span class="qxy_go_btn disabled" href="javascript:;">跳转</span>')):(a.append('<span class="qxy_go_num">共'+e.pageCount+'页,去第 <input type="text" value=""> 页</span>'),a.append('<a class="qxy_go_btn" href="javascript:;">跳转</a>'))},bindEvent:function(e){e.dom.off("click","a.qxy_page_num").on("click","a.qxy_page_num",function(){var a=parseInt($(this).text());JqPage.fillHtml({current:a,pageCount:e.pageCount,dom:e.dom,itemSize:e.itemSize}),"function"==typeof e.backFn&&e.backFn(a)}),e.dom.off("click","a.qxy_page_prev").on("click","a.qxy_page_prev",function(){var a=parseInt($("span.yqx-pg-current",e.dom).text());1!=a&&(JqPage.fillHtml({current:a-1,pageCount:e.pageCount,dom:e.dom,itemSize:e.itemSize}),"function"==typeof e.backFn&&e.backFn(a-1))}),e.dom.off("click","a.qxy_page_next").on("click","a.qxy_page_next",function(){var a=parseInt($("span.yqx-pg-current",e.dom).text());a!=e.pageCount&&(JqPage.fillHtml({current:a+1,pageCount:e.pageCount,dom:e.dom,itemSize:e.itemSize}),"function"==typeof e.backFn&&e.backFn(a+1))}),e.dom.off("click","a.qxy_page_first").on("click","a.qxy_page_first",function(){JqPage.fillHtml({current:1,pageCount:e.pageCount,dom:e.dom,itemSize:e.itemSize}),"function"==typeof e.backFn&&e.backFn(1)}),e.dom.off("click","a.qxy_page_last").on("click","a.qxy_page_last",function(){JqPage.fillHtml({current:e.pageCount,pageCount:e.pageCount,dom:e.dom,itemSize:e.itemSize}),"function"==typeof e.backFn&&e.backFn(e.pageCount)}),e.dom.off("click","a.qxy_go_btn").on("click","a.qxy_go_btn",function(){var a=parseInt(e.dom.find("span.yqx-pg-current").text()),t=$(this).prev().find("input"),n=parseInt(t.val());return n>e.pageCount||!$.isNumeric(n)||n==a?void t.val("").focus():(JqPage.fillHtml({current:e.pageCount,pageCount:e.pageCount,dom:e.dom,itemSize:e.itemSize}),void("function"==typeof e.backFn&&e.backFn(n||a)))})}},CANCEL_ORDER=function(){var e=function(e){void 0!=$(e)&&null!=$(e)&&$(e).modal("hide")},a=$("#alert_cancel_order"),t=function(e){void 0!=a&&(n(e),a.modal("show"))},n=function(e){var t=a.find("input[type=checkbox]");$("#acoOtherReason").val(""),$(t).prop("checked",!1),$(t[0]).prop("checked",!0),t.on("click",function(){a.find("input[type=checkbox]").prop("checked",!1),$(this).prop("checked",!0)}),a.find("div[id=acoSave]").unbind().on("click",function(){r(e)})},r=function(e){for(var t,n=a.find("input[type=checkbox]"),r=0;r<n.length;r++)1==$(n[r]).prop("checked")&&(t=$(n[r]).attr("title"));return void 0==t||null==t||""==t?void EPC.alertFucWithTime(1500,"请选择取消原因!"):"其他"==t&&(t=a.find("input[id=acoOtherReason]").val().trim(),""==t)?void EPC.alertFucWithTime(1500,"请填写具体原因!"):void(1==confirm("您确定要取消吗？")&&o(e,t))},o=function(e,t){var n={orderSn:e,cancelReason:t};Ajax.post({url:"/buy/order/cancelOrder",data:n,success:function(e){e.success?(CANCEL_ORDER.closeAlert(a),LayerUtil.msgFun("订单取消成功",function(){$.isFunction(CANCEL_ORDER.callback)&&CANCEL_ORDER.callback()})):LayerUtil.msg(e.message)}})};return{closeAlert:function(a){e(a)},alertCancelOrder:function(e){return null==e?!1:void t(e)},callback:void 0}}(),ADAPTIVE_CAR=function(){var e=function(e){JqUI.loading($("#alert_adaptive_car")),JqAjax.get("/shopping/getCarForGoods",function(e){e.success?$("#carForGoods").html(template("carForGoodsTemplate",{dataList:e.data})):$("#carForGoods").html(template("noCarForGoodsTemplate")),JqUI.unblockUI($("#alert_adaptive_car"))},{goodsId:e})},a=function(e){JqUI.loading($("#alert_adaptive_car")),JqAjax.get("/shopping/getCarForGoodsByOeNum",function(e){e.success?$("#carForGoods").html(template("carForGoodsTemplate",{dataList:e.data})):$("#carForGoods").html(template("noCarForGoodsTemplate")),JqUI.unblockUI($("#alert_adaptive_car"))},{oeNum:e})};return{alertAdaptiveCar:function(a){e(a),$("#alert_adaptive_car").modal("show")},alertAdaptiveCarByOeNum:function(e){a(e),$("#alert_adaptive_car").modal("show")}}}();template.helper("partOrderStatusHelper",function(e){if(void 0==e||null==e)return"";var a="";switch(e){case 0:a="待付款";break;case 1:a="已取消";break;case 2:a="待发货";break;case 3:a="已发货";break;case 4:case 5:a="已签收"}return a}),template.helper("amountFormatHelper",function(e){return EPC.parseMoney(e)});var SHOW_ORDER=function(){var e=$("body"),a="0",t=1,n=1,r=function(){s(1),$("#myOrder").addClass("active")},o=function(e,a){JqPage.init({itemSize:a,pageSize:10,current:e||1,backFn:function(e){i(e)}})},i=function(a){t=a,JqUI.loading(e);var n=c(a);JqAjax.get("/buy/order/list",function(t){t.success?(EPC.parseMoney(),$("#orderItems").html(template("orderItemTemplate",{orderItems:t.list})),o(a,t.total)):($("#orderItems").html(template("noDataTemplate",{msg:"您还没有相关的订单"})),$(".qxy_page").empty()),ImgUtil.init($(".goods-img"),["click"]),JqUI.unblockUI(e)},n,void 0)},c=function(e){var t;switch(n){case 1:t="0";break;case 2:t="2";break;case 3:t="3";break;case 4:t="1,4,5";break;default:t=a}return{searchP:e,searchStatus:t}},s=function(e,a){n=e,void 0!=a&&($(".offer-select-type li").removeClass("current"),$(a).addClass("current")),i(1)},l=function(e){CANCEL_ORDER.alertCancelOrder(e),CANCEL_ORDER.callback=function(){i(t)}},p=function(a){EPC.confirmNewFuc("你确定要确认收货吗?",function(){JqUI.loading(e),JqAjax.post("/buy/order/confirmReceive",function(a){JqUI.unblockUI(e),a.success?LayerUtil.msgFun("您已确认收货成功",function(){i(t)}):LayerUtil.msg("非常抱歉，确认收货失败")},{orderSn:a})})},u=function(e){var a="/buy/order/orderDetail?orderSn="+e;window.open(a,"_blank")};return{init:function(){r()},showOrder:function(e,a){s(e,a)},cancelOrder:function(e){l(e)},confirmReceive:function(e){void 0!=e&&null!=e&&p(e)},orderDetail:function(e){u(e)}}}();SHOW_ORDER.init();