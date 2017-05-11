function changeSearchType(e,t){TipUtil.removeTip();var a=$("#car-type-search-input");switch(a.val(""),$(".choose-style").removeClass("choose-style"),$(t).addClass("choose-style"),e){case 0:a.unbind("keyup"),a.attr("placeholder",_keywordSearchPlaceholder),a.removeAttr("maxlength");break;case 1:a.unbind("keyup").on("keyup",function(){$(this).val($(this).val().replace(/[\u4E00-\u9FA5]/g,""))}),a.attr("placeholder",_oeSearchPlaceholder),a.removeAttr("maxlength");break;default:a.unbind("keyup").on("keyup",function(){$(this).val($(this).val().replace(/[\u4E00-\u9FA5]/g,""))}),a.attr("placeholder",_vinSearchPlaceholder),a.attr("maxlength",_vinLength)}a.focus()}function carSearch(){var e=$("#car-type-search-input");TipUtil.destroyTip(e);var t=Number($(".choose-style").attr("data-type")),a=e.val().trim();if(0==t){if(""==a)return TipUtil.tooltipFun(e,_keywordSearchTip,"bottom"),!1;window.location.href="/autoParts/goods/keyword?q="+a}if(1==t){if(""==a)return TipUtil.tooltipFun(e,_oeSearchTip,"bottom"),!1;window.location.href="/autoParts/goods/oe?oem="+a}if(2==t){if(a=a.toUpperCase(),!EPC.isVin(a))return TipUtil.tooltipFun(e,_vinSearchTip,"bottom"),!1;JqAjax.post("/epc/car/getOnlineCarByVin",getVinResult,{vin:a})}}function getVinResult(e){var t=$("#car-type-search-input").val().trim(),a="/autoParts/category/category?carId=CARID&vinNumber="+t;if(e.success){var r=e.data;if(1==r.length)window.location.href=a.replace("CARID",r[0].id)+"&seriesId="+r[0].seriesId;else{var i=template("choose_car_template",{list:r});EPC.alertFuc(i),$(".choose_car").unbind("click").click(function(){var e=$(this).attr("data-id"),t=$(this).attr("data-sid");window.location.href=a.replace("CARID",e)+"&seriesId="+t})}}else EPC.alertFuc("很抱歉，未查询到您提交的车辆信息。");return!1}function getCatResult(e){if(e.success){$("#content").css("display","block");var t=e.data.firstList;_firstMap=e.data.firstMap,_secondMap=e.data.secondMap,$("#firstList").html(template("firstListTemplate",{firstList:t})),$("#firstList li:first").addClass("firstList-backColor");var a=t[0].catId,r=_firstMap[a].boList;$("#secondList").html(template("secondListTemplate",{secondList:r}));var i=r[0].catId,o=_secondMap[i];o[0].catId;handleFixingArray(o),$("#secondList li:first").addClass("secondList-backColor")}else $("#vinSearch-noData").css("display","block")}function firstList(e){$("#firstList li").removeClass("firstList-backColor"),$(e).addClass("firstList-backColor");var t=$(e).attr("id"),a=_firstMap[t],r=a.boList;$("#secondList").html(template("secondListTemplate",{secondList:r})),secondList($("#"+r[0].catId))}function secondList(e){$("#secondList li").removeClass("secondList-backColor"),$(e).addClass("secondList-backColor"),$("#secondList li:first").css(" border-top","none"),e==$("#secondList li:first")&&$("#secondList li:first").css(" border-top","none");var t=$(e).data("title"),a=_secondMap[t];handleFixingArray(a)}function handleFixingArray(e){_thirdMap={},$.each(e,function(e,t){var a=_thirdMap[t.firstLetter];void 0===a&&(a=[],_thirdMap[t.firstLetter]=a),a.push(t)}),$("#thirdList-firstWord").html(template("thirdListFirstWordTemplate",{thirdMap:_thirdMap})),$("#thirdList-firstWord li:first").addClass("change-backColor"),$("#thirdList").html(template("thirdListTemplate",{thirdMap:_thirdMap})),$("#oeTable tr:eq(1)").addClass("change-backColor"),choiceCat($("#"+e[0].catId))}function choiceThirdListCat(e){var t=$(e).attr("id");if($("#thirdList-firstWord li").removeClass("change-backColor"),$(e).addClass("change-backColor"),-1==t)$("#thirdList").html(template("thirdListTemplate",{thirdMap:_thirdMap}));else{var a=$.extend(!0,{},_thirdMap);handleResultMap(a,t),$("#thirdList").html(template("thirdListTemplate",{thirdMap:a}))}}function handleResultMap(e,t){$.each(e,function(a,r){t!=a&&""!=t&&delete e[a]})}function choiceCat(e){var t=$(e).data("title");$("#choiceCat li").removeClass("thirdList-backColor"),$(e).addClass("thirdList-backColor"),JqUI.loading(_body),JqAjax.post("/goods/getGoodsByCarCate",tableDataFunc,{carId:carId,catId:t})}function tableDataFunc(e){if(_epcList=[],e.success){var t=e.data;_epcList=t[0].epcPicList,$("#oeList").html(template("oeListTemplate",{dataList:t})),choicePartOe($(".goods-table-tr:first"))}JqUI.unblockUI(_body)}function choicePartOe(e){$("#oeTable tr").removeClass("thirdList-backColor"),$(e).addClass("thirdList-backColor");for(var t,a=$("#oeTable tr"),r=0;r<a.length;r++)$(a[r]).hasClass("thirdList-backColor")&&(_goodsId=$(a[r]).attr("id"),_oeNumber=$(a[r]).attr("value"),t=$(a[r]).children().eq(3).text());onChange($("#describe"));var i=$(".car-info-right-div");JqUI.blockUI(i),JqAjax.get("/autoParts/getGoodsDetail",function(e){if(e.success){$("#remarks").html(template("descriptionTemplate",e.data));var t=e.data.epcPicList;(null==t||void 0==t||0==t.length)&&(t=["/img/no-pic-big.png"]),$("#pictureList").html(template("pictureListTemplate",{dataList:t})),$("#bigPicture").attr("src",t[0]);var a="/goods/picGoods?picNum="+e.data.epcPicNum+"&carId="+carId;$("#showPicGoods").attr("href",a)}JqUI.unblockUI(i)},{goodsId:_goodsId,carId:carId}),JqAjax.get("/autoParts/carModelForGoods",function(e){e.success?$("#CarModel").html(template("carModelTemplate",e.data)):$("#CarModel").html("没有适配车型")},{goodsId:_goodsId})}function enterPartPage(e){var t=$(".car-model-span:first").attr("data-id");selectCarModel(t)}function onChange(e){$(".change-backColor").removeClass("change-backColor"),$(e).addClass("change-backColor");var t=$(e).attr("id");"suitableModel"==t?($("#onDescribe").css("display","none"),$("#onSuitableModel").css("display","block")):($("#onDescribe").css("display","block"),$("#onSuitableModel").css("display","none"))}function selectCarModel(e){window.location.href="/autoParts/goods/goodsDetail?from=CATEGORY_DETAIL&goodsId="+_goodsId+"&modelId="+e}function changePicture(e){$(".picture-border").removeClass("picture-border"),$(e).addClass("picture-border");var t=$(e).attr("src");$("#bigPicture").attr("src",t)}!function(){function e(e){return e.replace(b,"").replace(y,",").replace(C,"").replace(L,"").replace(k,"").split(_)}function t(e){return"'"+e.replace(/('|\\)/g,"\\$1").replace(/\r/g,"\\r").replace(/\n/g,"\\n")+"'"}function a(a,r){function i(e){return p+=e.split(/\n/).length-1,d&&(e=e.replace(/\s+/g," ").replace(/<!--[\w\W]*?-->/g,"")),e&&(e=v[1]+t(e)+v[2]+"\n"),e}function o(t){var a=p;if(l?t=l(t,r):n&&(t=t.replace(/\n/g,function(){return p++,"$line="+p+";"})),0===t.indexOf("=")){var i=u&&!/^=[=#]/.test(t);if(t=t.replace(/^=[=#]?|[\s;]*$/g,""),i){var o=t.replace(/\s*\([^\)]+\)/,"");f[o]||/^(include|print)$/.test(o)||(t="$escape("+t+")")}else t="$string("+t+")";t=v[1]+t+v[2]}return n&&(t="$line="+a+";"+t),m(e(t),function(e){if(e&&!$[e]){var t;t="print"===e?y:"include"===e?C:f[e]?"$utils."+e:h[e]?"$helpers."+e:"$data."+e,L+=e+"="+t+",",$[e]=!0}}),t+"\n"}var n=r.debug,s=r.openTag,c=r.closeTag,l=r.parser,d=r.compress,u=r.escape,p=1,$={$data:1,$filename:1,$utils:1,$helpers:1,$out:1,$line:1},g="".trim,v=g?["$out='';","$out+=",";","$out"]:["$out=[];","$out.push(",");","$out.join('')"],b=g?"$out+=text;return $out;":"$out.push(text);",y="function(){var text=''.concat.apply('',arguments);"+b+"}",C="function(filename,data){data=data||$data;var text=$utils.$include(filename,data,$filename);"+b+"}",L="'use strict';var $utils=this,$helpers=$utils.$helpers,"+(n?"$line=0,":""),k=v[0],_="return new String("+v[3]+");";m(a.split(s),function(e){e=e.split(c);var t=e[0],a=e[1];1===e.length?k+=i(t):(k+=o(t),a&&(k+=i(a)))});var I=L+k+_;n&&(I="try{"+I+"}catch(e){throw {filename:$filename,name:'Render Error',message:e.message,line:$line,source:"+t(a)+".split(/\\n/)[$line-1].replace(/^\\s+/,'')};}");try{var w=new Function("$data","$filename",I);return w.prototype=f,w}catch(T){throw T.temp="function anonymous($data,$filename) {"+I+"}",T}}var r=function(e,t){return"string"==typeof t?g(t,{filename:e}):n(e,t)};r.version="3.0.0",r.config=function(e,t){i[e]=t};var i=r.defaults={openTag:"<%",closeTag:"%>",escape:!0,cache:!0,compress:!1,parser:null},o=r.cache={};r.render=function(e,t){return g(e,t)};var n=r.renderFile=function(e,t){var a=r.get(e)||$({filename:e,name:"Render Error",message:"Template not found"});return t?a(t):a};r.get=function(e){var t;if(o[e])t=o[e];else if("object"==typeof document){var a=document.getElementById(e);if(a){var r=(a.value||a.innerHTML).replace(/^\s*|\s*$/g,"");t=g(r,{filename:e})}}return t};var s=function(e,t){return"string"!=typeof e&&(t=typeof e,"number"===t?e+="":e="function"===t?s(e.call(e)):""),e},c={"<":"&#60;",">":"&#62;",'"':"&#34;","'":"&#39;","&":"&#38;"},l=function(e){return c[e]},d=function(e){return s(e).replace(/&(?![\w#]+;)|[<>"']/g,l)},u=Array.isArray||function(e){return"[object Array]"==={}.toString.call(e)},p=function(e,t){var a,r;if(u(e))for(a=0,r=e.length;r>a;a++)t.call(e,e[a],a,e);else for(a in e)t.call(e,e[a],a)},f=r.utils={$helpers:{},$include:n,$string:s,$escape:d,$each:p};r.helper=function(e,t){h[e]=t};var h=r.helpers=f.$helpers;r.onerror=function(e){var t="Template Error\n\n";for(var a in e)t+="<"+a+">\n"+e[a]+"\n\n";"object"==typeof console&&console.error(t)};var $=function(e){return r.onerror(e),function(){return"{Template Error}"}},g=r.compile=function(e,t){function r(a){try{return new c(a,s)+""}catch(r){return t.debug?$(r)():(t.debug=!0,g(e,t)(a))}}t=t||{};for(var n in i)void 0===t[n]&&(t[n]=i[n]);var s=t.filename;try{var c=a(e,t)}catch(l){return l.filename=s||"anonymous",l.name="Syntax Error",$(l)}return r.prototype=c.prototype,r.toString=function(){return c.toString()},s&&t.cache&&(o[s]=r),r},m=f.$each,v="break,case,catch,continue,debugger,default,delete,do,else,false,finally,for,function,if,in,instanceof,new,null,return,switch,this,throw,true,try,typeof,var,void,while,with,abstract,boolean,byte,char,class,const,double,enum,export,extends,final,float,goto,implements,import,int,interface,long,native,package,private,protected,public,short,static,super,synchronized,throws,transient,volatile,arguments,let,yield,undefined",b=/\/\*[\w\W]*?\*\/|\/\/[^\n]*\n|\/\/[^\n]*$|"(?:[^"\\]|\\[\w\W])*"|'(?:[^'\\]|\\[\w\W])*'|\s*\.\s*[$\w\.]+/g,y=/[^\w$]+/g,C=new RegExp(["\\b"+v.replace(/,/g,"\\b|\\b")+"\\b"].join("|"),"g"),L=/^\d[^,]*|,\d[^,]*/g,k=/^,+|,+$/g,_=/^$|,+/;i.openTag="{{",i.closeTag="}}";var I=function(e,t){var a=t.split(":"),r=a.shift(),i=a.join(":")||"";return i&&(i=", "+i),"$helpers."+r+"("+e+i+")"};i.parser=function(e){e=e.replace(/^\s/,"");var t=e.split(" "),a=t.shift(),i=t.join(" ");switch(a){case"if":e="if("+i+"){";break;case"else":t="if"===t.shift()?" if("+t.join(" ")+")":"",e="}else"+t+"{";break;case"/if":e="}";break;case"each":var o=t[0]||"$data",n=t[1]||"as",s=t[2]||"$value",c=t[3]||"$index",l=s+","+c;"as"!==n&&(o="[]"),e="$each("+o+",function("+l+"){";break;case"/each":e="});";break;case"echo":e="print("+i+");";break;case"print":case"include":e=a+"("+t.join(",")+");";break;default:if(/^\s*\|\s*[\w\$]/.test(i)){var d=!0;0===e.indexOf("#")&&(e=e.substr(1),d=!1);for(var u=0,p=e.split("|"),f=p.length,h=p[u++];f>u;u++)h=I(h,p[u]);e=(d?"=":"=#")+h}else e=r.helpers[a]?"=#"+a+"("+t.join(",")+");":"="+e}return e},"function"==typeof define?define(function(){return r}):"undefined"!=typeof exports?module.exports=r:this.template=r}();var storage=window.sessionStorage,key="autoParts_crumbs";$(function(){var e="";switch(Number($(".choose-style").attr("data-type"))){case 1:e=_oeSearchPlaceholder;break;case 2:e=_vinSearchPlaceholder;break;default:e=_keywordSearchPlaceholder}$("#car-type-search-input").bind("keypress",function(e){13==e.keyCode&&$("#car-type-head-searchBtn").trigger("click")}).attr("placeholder",e)});var _body=$("body");$(function(){var e=$("#vinNumber").val();$("#car-type-search-input").val(e),JqAjax.get("/epc/category/getCatByCarId",getCatResult,{carId:$("#carId").val()}),ImgUtil.init($("#bigPicture"))});var carId=$("#carId").val(),_firstMap,_secondMap,_thirdMap,_epcList,_goodsId,_oeNumber;