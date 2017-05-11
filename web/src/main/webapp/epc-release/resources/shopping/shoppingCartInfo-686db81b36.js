function changeSearchType(e,t){TipUtil.removeTip();var a=$("#car-type-search-input");switch(a.val(""),$(".choose-style").removeClass("choose-style"),$(t).addClass("choose-style"),e){case 0:a.unbind("keyup"),a.attr("placeholder",_keywordSearchPlaceholder),a.removeAttr("maxlength");break;case 1:a.unbind("keyup").on("keyup",function(){$(this).val($(this).val().replace(/[\u4E00-\u9FA5]/g,""))}),a.attr("placeholder",_oeSearchPlaceholder),a.removeAttr("maxlength");break;default:a.unbind("keyup").on("keyup",function(){$(this).val($(this).val().replace(/[\u4E00-\u9FA5]/g,""))}),a.attr("placeholder",_vinSearchPlaceholder),a.attr("maxlength",_vinLength)}a.focus()}function carSearch(){var e=$("#car-type-search-input");TipUtil.destroyTip(e);var t=Number($(".choose-style").attr("data-type")),a=e.val().trim();if(0==t){if(""==a)return TipUtil.tooltipFun(e,_keywordSearchTip,"bottom"),!1;window.location.href="/autoParts/goods/keyword?q="+a}if(1==t){if(""==a)return TipUtil.tooltipFun(e,_oeSearchTip,"bottom"),!1;window.location.href="/autoParts/goods/oe?oem="+a}if(2==t){if(a=a.toUpperCase(),!EPC.isVin(a))return TipUtil.tooltipFun(e,_vinSearchTip,"bottom"),!1;JqAjax.post("/epc/car/getOnlineCarByVin",getVinResult,{vin:a})}}function getVinResult(e){var t=$("#car-type-search-input").val().trim(),a="/autoParts/category/category?carId=CARID&vinNumber="+t;if(e.success){var n=e.data;if(1==n.length)window.location.href=a.replace("CARID",n[0].id)+"&seriesId="+n[0].seriesId;else{var o=template("choose_car_template",{list:n});EPC.alertFuc(o),$(".choose_car").unbind("click").click(function(){var e=$(this).attr("data-id"),t=$(this).attr("data-sid");window.location.href=a.replace("CARID",e)+"&seriesId="+t})}}else EPC.alertFuc("很抱歉，未查询到您提交的车辆信息。");return!1}function loadCartGoodsList(e){JqUI.loading(_body),JqAjax.get("/shopping/getCartGoodsList",function(t){if(t.success){var a=t.data;if(void 0!=a&&null!=a&&0!=a.length){if($("#shoppingCartBody").html(template("shoppingCartBodyTemplate",{dataList:a})),$("#shoppingCartFoot").removeClass("hidden"),1==e)for(var n=getSelectedGoods(),o=0;o<n.length;o++){var r=$("#"+n[o]);checkedProduct(r,n[o])}ImgUtil.init($(".goods-info-img-set"),["click"]),loadTotal(),ChatWithSeller()}else $("#shoppingCartBody").html(template("noShoppingCartDataTemplate")),$("#shoppingCartFoot").addClass("hidden"),loadTotal()}else $("#shoppingCartBody").html(template("noShoppingCartDataTemplate")),$("#shoppingCartFoot").addClass("hidden"),loadTotal();JqUI.unblockUI(_body)})}function changeNum(e,t,a){var n=$(e).parent().children("input[type='text']").val(),o=parseInt(n);if(1==o&&0>a)return!1;if(o+a>999)return LayerUtil.msg("非常抱歉，已超最大库存"),$(e).parent().children("input[type='text']").val(o),!1;updateProductNum(t,o+a),$(e).parent().children("input[type='text']").val(o+a);var r=$(e).parents("td").prev().children().last().text(),i=EPC.parseMoney(parseFloat(r)*(o+a));if(1==$(e).parents("tr").find("input[type='checkbox']").prop("checked")){var s=$("#totalAmount")[0].innerText;$("#totalAmount").text(EPC.parseMoney(parseFloat(s)+r*a))}$(e).parents("td").next().find("strong").last().text(EPC.parseMoney(i))}function changeInputNum(e,t){var a=$(e).val().trim();if((""==a||0==a.substring(0,1))&&($(e).val(1),a=1),!isNaN(a)&&/^\d+$/.test(a)){if(a.length>3){LayerUtil.msg("非常抱歉，已超最大库存");var n=a.substring(0,a.length-1);return $(e).parent().children("input[type='text']").val(parseInt(n)),!1}updateProductNum(t,a);var o=$(e).parents("td").prev().children().last().text(),r=parseFloat(o)*a;$(e).parents("td").next().find("strong").last().text(EPC.parseMoney(r));for(var i=$("#shoppingCartBody").find("input[name='goods']:checked").parents("tr").find("strong[class='total-price']"),s=0,l=0;l<i.length;l++)s+=JSON.parse(i[l].innerText);$("#totalAmount").text(EPC.parseMoney(s))}else $(e).val(1),changeInputNum(e,t)}function loadTotal(){for(var e=0,t=$("#shoppingCartBody").find("input[name='goods']:checked").parents("tr").find("strong[class='total-price']"),a=0;a<t.length;a++)e+=parseFloat(t[a].innerText);$("#totalAmount").text(EPC.parseMoney(e));var n=$("#shoppingCartBody").find("input[name='goods']").length,o=$("#shoppingCartBody").find("input[name='goods']:checked").length;$("#totalNum").text(o),o!=n?$("input[name='checkedAll']").prop("checked",!1):$("input[name='checkedAll']").prop("checked",!0),ShoppingCart.reloadCartNum()}function checkedAll(e){0==$(e).prop("checked")?($("#shoppingCartBody").find("input[type='checkbox']").prop("checked",!1),$("input[name='checkedAll']").prop("checked",!1),$("#totalAmount").text("0.00"),$("#totalNum").text(0)):($("#shoppingCartBody").find("input[type='checkbox']").prop("checked",!0),$("input[name='checkedAll']").prop("checked",!0),loadTotal())}function checkedProduct(e,t){var a=$(e).data("title");if(0==$(e).prop("checked")){$("#"+t).find("input[type='checkbox']").prop("checked",!1);for(var n=0,o=$("#totalAmount")[0].innerText,r=$("#"+t).find("strong[class='total-price']"),i=0;i<r.length;i++)n+=JSON.parse(r[i].innerText);u=JSON.parse(o)-n,$("#totalAmount").text(EPC.parseMoney(u));var s=$("#"+a).find("input[name='goods']").length,l=$("#"+a).find("input[name='goods']:checked").length;l!=s&&$("#"+a).find("input[name='seller']").prop("checked",!1);var c=$("#shoppingCartBody").find("input[name='seller']").length,d=$("#shoppingCartBody").find("input[name='seller']:checked").length;c!=d&&$("input[name='checkedAll']").prop("checked",!1);var p=$("#shoppingCartBody").find("input[name='goods']:checked").length;$("#totalNum").text(p)}else{$("#"+t).find("input[type='checkbox']").prop("checked",!0);for(var u=0,o=JSON.parse($("#totalAmount")[0].innerText),r=$("#"+t).find("strong[class='total-price']"),i=0;i<r.length;i++)u+=JSON.parse(r[i].innerText);$("#totalAmount").text(EPC.parseMoney(u+o));var s=$("#"+a).find("input[name='goods']").length,l=$("#"+a).find("input[name='goods']:checked").length;l==s&&$("#"+a).find("input[name='seller']").prop("checked",!0);var c=$("#shoppingCartBody").find("input[name='seller']").length,d=$("#shoppingCartBody").find("input[name='seller']:checked").length;c==d&&$("input[name='checkedAll']").prop("checked",!0);var p=$("#shoppingCartBody").find("input[name='goods']:checked").length;$("#totalNum").text(p)}}function deleteProduct(e){if(-1==e){var t=$("#shoppingCartBody").find("input[name='goods']:checked");0==t.length?LayerUtil.msg("请选择要删除的商品"):EPC.confirmNewFuc("你确定要删除选中的商品吗?",function(){for(var e=[],a=0;a<t.length;a++)e.push($(t[a]).data("goods-id"));var n=e.join(",");deleteGoodsByIdList(n)})}else EPC.confirmNewFuc("你确定要删除商品吗?",function(){deleteCartGoods(e)})}function deleteGoodsByIdList(e){JqAjax.post("/shopping/deleteGoodsByIdList",function(t){t.success?(setSelectedGoods(e),loadCartGoodsList(!0)):LayerUtil.msg("批量删除商品失败")},{goodsIdStr:e})}function deleteCartGoods(e){JqAjax.post("/shopping/deleteCartGoods",function(t){t.success?(setSelectedGoods(e.toString()),loadCartGoodsList(!0)):LayerUtil.msg("删除该商品失败")},{goodsId:e})}function updateProductNum(e,t){var a={goodsId:e,goodsNumber:t};JqAjax.post("/shopping/modifyGoodsNumber",function(){},a)}function consignee(){var e=$("#shoppingCartBody").find("input[name='goods']:checked");if(0==e.length)return void LayerUtil.msg("请先选择商品，再结算");JqUI.loading(_body);for(var t=[],a=[],n=0;n<e.length;n++){var o=$(e[n]);t.push(o.val());var r={goodsId:o.data("goods-id"),partName:o.data("part-name")};a.push(r)}var i=t.join(",");Ajax.postJson({url:"/shopping/checkGoodsForSettlement",data:JSON.stringify(a),success:function(e){JqUI.unblockUI(_body),e.success?window.location.href="/shopping/consignee?idStr="+i:LayerUtil.msgFun(e.message,function(){setSelectedGoods(),loadCartGoodsList(!0)})}})}function deleteUnAvailableGoods(){JqUI.loading(_body),Ajax.get({url:"/shopping/deleteUnAvailableGoods",success:function(e){JqUI.unblockUI(_body),e.success?LayerUtil.msgFun("清空失效商品成功",function(){setSelectedGoods(),loadCartGoodsList(!0)}):LayerUtil.msgFun(e.message,function(){setSelectedGoods(),loadCartGoodsList(!0)})}})}function ChatWithSeller(){$(".seller-chat").unbind("click").click(function(){var e=$(this).data("orgid");TqmallChat.openChatWindow(e,"yunpei",void 0,WishChatMap.bottom_url,WishChatMap.bottom_url_btn_name,WishChatMap.guide_url,WishChatMap.guide_content)})}function setSelectedGoods(e){var t=[];t=null!=e||void 0!=e?e.split(","):[],LocalStorageUtil.setParam("selectedGoodsId",null);for(var a=$("#shoppingCartBody").find("input[name='goods']:checked"),n=[],o=0;o<a.length;o++)n.push(JSON.parse($(a[o]).data("goods-id")));$.each(t,function(e,t){$.each(n,function(e,a){t==a&&n.splice(e,1)})}),LocalStorageUtil.setParam("selectedGoodsId",JSON.stringify(n))}function getSelectedGoods(){var e=JSON.parse(LocalStorageUtil.getParam("selectedGoodsId"));return null==e||""==e?[]:e}var storage=window.sessionStorage,key="autoParts_crumbs";$(function(){var e="";switch(Number($(".choose-style").attr("data-type"))){case 1:e=_oeSearchPlaceholder;break;case 2:e=_vinSearchPlaceholder;break;default:e=_keywordSearchPlaceholder}$("#car-type-search-input").bind("keypress",function(e){13==e.keyCode&&$("#car-type-head-searchBtn").trigger("click")}).attr("placeholder",e)}),!function(){function e(e){return e.replace(y,"").replace(k,",").replace(C,"").replace(b,"").replace(x,"").split(S)}function t(e){return"'"+e.replace(/('|\\)/g,"\\$1").replace(/\r/g,"\\r").replace(/\n/g,"\\n")+"'"}function a(a,n){function o(e){return u+=e.split(/\n/).length-1,d&&(e=e.replace(/\s+/g," ").replace(/<!--[\w\W]*?-->/g,"")),e&&(e=v[1]+t(e)+v[2]+"\n"),e}function r(t){var a=u;if(c?t=c(t,n):i&&(t=t.replace(/\n/g,function(){return u++,"$line="+u+";"})),0===t.indexOf("=")){var o=p&&!/^=[=#]/.test(t);if(t=t.replace(/^=[=#]?|[\s;]*$/g,""),o){var r=t.replace(/\s*\([^\)]+\)/,"");h[r]||/^(include|print)$/.test(r)||(t="$escape("+t+")")}else t="$string("+t+")";t=v[1]+t+v[2]}return i&&(t="$line="+a+";"+t),$(e(t),function(e){if(e&&!g[e]){var t;t="print"===e?k:"include"===e?C:h[e]?"$utils."+e:f[e]?"$helpers."+e:"$data."+e,b+=e+"="+t+",",g[e]=!0}}),t+"\n"}var i=n.debug,s=n.openTag,l=n.closeTag,c=n.parser,d=n.compress,p=n.escape,u=1,g={$data:1,$filename:1,$utils:1,$helpers:1,$out:1,$line:1},m="".trim,v=m?["$out='';","$out+=",";","$out"]:["$out=[];","$out.push(",");","$out.join('')"],y=m?"$out+=text;return $out;":"$out.push(text);",k="function(){var text=''.concat.apply('',arguments);"+y+"}",C="function(filename,data){data=data||$data;var text=$utils.$include(filename,data,$filename);"+y+"}",b="'use strict';var $utils=this,$helpers=$utils.$helpers,"+(i?"$line=0,":""),x=v[0],S="return new String("+v[3]+");";$(a.split(s),function(e){e=e.split(l);var t=e[0],a=e[1];1===e.length?x+=o(t):(x+=r(t),a&&(x+=o(a)))});var w=b+x+S;i&&(w="try{"+w+"}catch(e){throw {filename:$filename,name:'Render Error',message:e.message,line:$line,source:"+t(a)+".split(/\\n/)[$line-1].replace(/^\\s+/,'')};}");try{var T=new Function("$data","$filename",w);return T.prototype=h,T}catch(A){throw A.temp="function anonymous($data,$filename) {"+w+"}",A}}var n=function(e,t){return"string"==typeof t?m(t,{filename:e}):i(e,t)};n.version="3.0.0",n.config=function(e,t){o[e]=t};var o=n.defaults={openTag:"<%",closeTag:"%>",escape:!0,cache:!0,compress:!1,parser:null},r=n.cache={};n.render=function(e,t){return m(e,t)};var i=n.renderFile=function(e,t){var a=n.get(e)||g({filename:e,name:"Render Error",message:"Template not found"});return t?a(t):a};n.get=function(e){var t;if(r[e])t=r[e];else if("object"==typeof document){var a=document.getElementById(e);if(a){var n=(a.value||a.innerHTML).replace(/^\s*|\s*$/g,"");t=m(n,{filename:e})}}return t};var s=function(e,t){return"string"!=typeof e&&(t=typeof e,"number"===t?e+="":e="function"===t?s(e.call(e)):""),e},l={"<":"&#60;",">":"&#62;",'"':"&#34;","'":"&#39;","&":"&#38;"},c=function(e){return l[e]},d=function(e){return s(e).replace(/&(?![\w#]+;)|[<>"']/g,c)},p=Array.isArray||function(e){return"[object Array]"==={}.toString.call(e)},u=function(e,t){var a,n;if(p(e))for(a=0,n=e.length;n>a;a++)t.call(e,e[a],a,e);else for(a in e)t.call(e,e[a],a)},h=n.utils={$helpers:{},$include:i,$string:s,$escape:d,$each:u};n.helper=function(e,t){f[e]=t};var f=n.helpers=h.$helpers;n.onerror=function(e){var t="Template Error\n\n";for(var a in e)t+="<"+a+">\n"+e[a]+"\n\n";"object"==typeof console&&console.error(t)};var g=function(e){return n.onerror(e),function(){return"{Template Error}"}},m=n.compile=function(e,t){function n(a){try{return new l(a,s)+""}catch(n){return t.debug?g(n)():(t.debug=!0,m(e,t)(a))}}t=t||{};for(var i in o)void 0===t[i]&&(t[i]=o[i]);var s=t.filename;try{var l=a(e,t)}catch(c){return c.filename=s||"anonymous",c.name="Syntax Error",g(c)}return n.prototype=l.prototype,n.toString=function(){return l.toString()},s&&t.cache&&(r[s]=n),n},$=h.$each,v="break,case,catch,continue,debugger,default,delete,do,else,false,finally,for,function,if,in,instanceof,new,null,return,switch,this,throw,true,try,typeof,var,void,while,with,abstract,boolean,byte,char,class,const,double,enum,export,extends,final,float,goto,implements,import,int,interface,long,native,package,private,protected,public,short,static,super,synchronized,throws,transient,volatile,arguments,let,yield,undefined",y=/\/\*[\w\W]*?\*\/|\/\/[^\n]*\n|\/\/[^\n]*$|"(?:[^"\\]|\\[\w\W])*"|'(?:[^'\\]|\\[\w\W])*'|\s*\.\s*[$\w\.]+/g,k=/[^\w$]+/g,C=new RegExp(["\\b"+v.replace(/,/g,"\\b|\\b")+"\\b"].join("|"),"g"),b=/^\d[^,]*|,\d[^,]*/g,x=/^,+|,+$/g,S=/^$|,+/;o.openTag="{{",o.closeTag="}}";var w=function(e,t){var a=t.split(":"),n=a.shift(),o=a.join(":")||"";return o&&(o=", "+o),"$helpers."+n+"("+e+o+")"};o.parser=function(e){e=e.replace(/^\s/,"");var t=e.split(" "),a=t.shift(),o=t.join(" ");switch(a){case"if":e="if("+o+"){";break;case"else":t="if"===t.shift()?" if("+t.join(" ")+")":"",e="}else"+t+"{";break;case"/if":e="}";break;case"each":var r=t[0]||"$data",i=t[1]||"as",s=t[2]||"$value",l=t[3]||"$index",c=s+","+l;"as"!==i&&(r="[]"),e="$each("+r+",function("+c+"){";break;case"/each":e="});";break;case"echo":e="print("+o+");";break;case"print":case"include":e=a+"("+t.join(",")+");";break;default:if(/^\s*\|\s*[\w\$]/.test(o)){var d=!0;0===e.indexOf("#")&&(e=e.substr(1),d=!1);for(var p=0,u=e.split("|"),h=u.length,f=u[p++];h>p;p++)f=w(f,u[p]);e=(d?"=":"=#")+f}else e=n.helpers[a]?"=#"+a+"("+t.join(",")+");":"="+e}return e},"function"==typeof define?define(function(){return n}):"undefined"!=typeof exports?module.exports=n:this.template=n}();var ADAPTIVE_CAR=function(){var e=function(e){JqUI.loading($("#alert_adaptive_car")),JqAjax.get("/shopping/getCarForGoods",function(e){e.success?$("#carForGoods").html(template("carForGoodsTemplate",{dataList:e.data})):$("#carForGoods").html(template("noCarForGoodsTemplate")),JqUI.unblockUI($("#alert_adaptive_car"))},{goodsId:e})},t=function(e){JqUI.loading($("#alert_adaptive_car")),JqAjax.get("/shopping/getCarForGoodsByOeNum",function(e){e.success?$("#carForGoods").html(template("carForGoodsTemplate",{dataList:e.data})):$("#carForGoods").html(template("noCarForGoodsTemplate")),JqUI.unblockUI($("#alert_adaptive_car"))},{oeNum:e})};return{alertAdaptiveCar:function(t){e(t),$("#alert_adaptive_car").modal("show")},alertAdaptiveCarByOeNum:function(e){t(e),$("#alert_adaptive_car").modal("show")}}}(),_body=$("body"),_cartGoodsList;template.helper("qualityTypeHelper",function(e){if(null==e||void 0==e)return"无";var t="";switch(e){case 1:t="正常原厂";break;case 2:t="正厂配套";break;case 3:t="正厂下线";break;case 4:t="全新拆车";break;case 5:t="旧件拆车";break;default:t="品牌"}return t}),template.helper("numberFormatHelper",function(e){return EPC.parseMoney(e)}),$(function(){loadCartGoodsList(),SetCityCallback.callback=function(){loadCartGoodsList()}});