function changeSearchType(e,t){TipUtil.removeTip();var r=$("#car-type-search-input");switch(r.val(""),$(".choose-style").removeClass("choose-style"),$(t).addClass("choose-style"),e){case 0:r.unbind("keyup"),r.attr("placeholder",_keywordSearchPlaceholder),r.removeAttr("maxlength");break;case 1:r.unbind("keyup").on("keyup",function(){$(this).val($(this).val().replace(/[\u4E00-\u9FA5]/g,""))}),r.attr("placeholder",_oeSearchPlaceholder),r.removeAttr("maxlength");break;default:r.unbind("keyup").on("keyup",function(){$(this).val($(this).val().replace(/[\u4E00-\u9FA5]/g,""))}),r.attr("placeholder",_vinSearchPlaceholder),r.attr("maxlength",_vinLength)}r.focus()}function carSearch(){var e=$("#car-type-search-input");TipUtil.destroyTip(e);var t=Number($(".choose-style").attr("data-type")),r=e.val().trim();if(0==t){if(""==r)return TipUtil.tooltipFun(e,_keywordSearchTip,"bottom"),!1;window.location.href="/autoParts/goods/keyword?q="+r}if(1==t){if(""==r)return TipUtil.tooltipFun(e,_oeSearchTip,"bottom"),!1;window.location.href="/autoParts/goods/oe?oem="+r}if(2==t){if(r=r.toUpperCase(),!EPC.isVin(r))return TipUtil.tooltipFun(e,_vinSearchTip,"bottom"),!1;JqAjax.post("/epc/car/getOnlineCarByVin",getVinResult,{vin:r})}}function getVinResult(e){var t=$("#car-type-search-input").val().trim(),r="/autoParts/category/category?carId=CARID&vinNumber="+t;if(e.success){var a=e.data;if(1==a.length)window.location.href=r.replace("CARID",a[0].id)+"&seriesId="+a[0].seriesId;else{var n=template("choose_car_template",{list:a});EPC.alertFuc(n),$(".choose_car").unbind("click").click(function(){var e=$(this).attr("data-id"),t=$(this).attr("data-sid");window.location.href=r.replace("CARID",e)+"&seriesId="+t})}}else EPC.alertFuc("很抱歉，未查询到您提交的车辆信息。");return!1}function getCarBrandForFilter(){JqUI.loading(body),JqAjax.get("/epc/car/carBrandForFilter",function(e){e.success&&($("#firstWord-li").html(template("carTypeHeaderTemplate",e)),$("#firstWord-li span:first").addClass("backColor"),$("#carTypeBody").html(template("carTypeBodyTemplate",e)),bindClickOnCarModel(),carBrandSeriesResult=e),JqUI.unblockUI(body)})}function firstWord(e){$("#firstWord-li span").removeClass("backColor");var t=$(e).attr("id");$(e).addClass("backColor"),$("#carTypeBody").html(template("carTypeBodyTemplate",handleResultMap(carBrandSeriesResult,t))),bindClickOnCarModel()}function handleResultMap(e,t){if(-1==t)return e;var r={};return r[t]=e.data[t],{data:r}}function bindClickOnCarModel(){$(".series_name_span").unbind("click").click(function(){var e=$(this).attr("data-id");window.location.href="/autoParts/car/carFilter?seriesId="+e})}!function(){function e(e){return e.replace(y,"").replace(b,",").replace(w,"").replace(k,"").replace(T,"").split(C)}function t(e){return"'"+e.replace(/('|\\)/g,"\\$1").replace(/\r/g,"\\r").replace(/\n/g,"\\n")+"'"}function r(r,a){function n(e){return d+=e.split(/\n/).length-1,u&&(e=e.replace(/\s+/g," ").replace(/<!--[\w\W]*?-->/g,"")),e&&(e=m[1]+t(e)+m[2]+"\n"),e}function i(t){var r=d;if(s?t=s(t,a):o&&(t=t.replace(/\n/g,function(){return d++,"$line="+d+";"})),0===t.indexOf("=")){var n=p&&!/^=[=#]/.test(t);if(t=t.replace(/^=[=#]?|[\s;]*$/g,""),n){var i=t.replace(/\s*\([^\)]+\)/,"");f[i]||/^(include|print)$/.test(i)||(t="$escape("+t+")")}else t="$string("+t+")";t=m[1]+t+m[2]}return o&&(t="$line="+r+";"+t),v(e(t),function(e){if(e&&!$[e]){var t;t="print"===e?b:"include"===e?w:f[e]?"$utils."+e:h[e]?"$helpers."+e:"$data."+e,k+=e+"="+t+",",$[e]=!0}}),t+"\n"}var o=a.debug,c=a.openTag,l=a.closeTag,s=a.parser,u=a.compress,p=a.escape,d=1,$={$data:1,$filename:1,$utils:1,$helpers:1,$out:1,$line:1},g="".trim,m=g?["$out='';","$out+=",";","$out"]:["$out=[];","$out.push(",");","$out.join('')"],y=g?"$out+=text;return $out;":"$out.push(text);",b="function(){var text=''.concat.apply('',arguments);"+y+"}",w="function(filename,data){data=data||$data;var text=$utils.$include(filename,data,$filename);"+y+"}",k="'use strict';var $utils=this,$helpers=$utils.$helpers,"+(o?"$line=0,":""),T=m[0],C="return new String("+m[3]+");";v(r.split(c),function(e){e=e.split(l);var t=e[0],r=e[1];1===e.length?T+=n(t):(T+=i(t),r&&(T+=n(r)))});var S=k+T+C;o&&(S="try{"+S+"}catch(e){throw {filename:$filename,name:'Render Error',message:e.message,line:$line,source:"+t(r)+".split(/\\n/)[$line-1].replace(/^\\s+/,'')};}");try{var x=new Function("$data","$filename",S);return x.prototype=f,x}catch(F){throw F.temp="function anonymous($data,$filename) {"+S+"}",F}}var a=function(e,t){return"string"==typeof t?g(t,{filename:e}):o(e,t)};a.version="3.0.0",a.config=function(e,t){n[e]=t};var n=a.defaults={openTag:"<%",closeTag:"%>",escape:!0,cache:!0,compress:!1,parser:null},i=a.cache={};a.render=function(e,t){return g(e,t)};var o=a.renderFile=function(e,t){var r=a.get(e)||$({filename:e,name:"Render Error",message:"Template not found"});return t?r(t):r};a.get=function(e){var t;if(i[e])t=i[e];else if("object"==typeof document){var r=document.getElementById(e);if(r){var a=(r.value||r.innerHTML).replace(/^\s*|\s*$/g,"");t=g(a,{filename:e})}}return t};var c=function(e,t){return"string"!=typeof e&&(t=typeof e,"number"===t?e+="":e="function"===t?c(e.call(e)):""),e},l={"<":"&#60;",">":"&#62;",'"':"&#34;","'":"&#39;","&":"&#38;"},s=function(e){return l[e]},u=function(e){return c(e).replace(/&(?![\w#]+;)|[<>"']/g,s)},p=Array.isArray||function(e){return"[object Array]"==={}.toString.call(e)},d=function(e,t){var r,a;if(p(e))for(r=0,a=e.length;a>r;r++)t.call(e,e[r],r,e);else for(r in e)t.call(e,e[r],r)},f=a.utils={$helpers:{},$include:o,$string:c,$escape:u,$each:d};a.helper=function(e,t){h[e]=t};var h=a.helpers=f.$helpers;a.onerror=function(e){var t="Template Error\n\n";for(var r in e)t+="<"+r+">\n"+e[r]+"\n\n";"object"==typeof console&&console.error(t)};var $=function(e){return a.onerror(e),function(){return"{Template Error}"}},g=a.compile=function(e,t){function a(r){try{return new l(r,c)+""}catch(a){return t.debug?$(a)():(t.debug=!0,g(e,t)(r))}}t=t||{};for(var o in n)void 0===t[o]&&(t[o]=n[o]);var c=t.filename;try{var l=r(e,t)}catch(s){return s.filename=c||"anonymous",s.name="Syntax Error",$(s)}return a.prototype=l.prototype,a.toString=function(){return l.toString()},c&&t.cache&&(i[c]=a),a},v=f.$each,m="break,case,catch,continue,debugger,default,delete,do,else,false,finally,for,function,if,in,instanceof,new,null,return,switch,this,throw,true,try,typeof,var,void,while,with,abstract,boolean,byte,char,class,const,double,enum,export,extends,final,float,goto,implements,import,int,interface,long,native,package,private,protected,public,short,static,super,synchronized,throws,transient,volatile,arguments,let,yield,undefined",y=/\/\*[\w\W]*?\*\/|\/\/[^\n]*\n|\/\/[^\n]*$|"(?:[^"\\]|\\[\w\W])*"|'(?:[^'\\]|\\[\w\W])*'|\s*\.\s*[$\w\.]+/g,b=/[^\w$]+/g,w=new RegExp(["\\b"+m.replace(/,/g,"\\b|\\b")+"\\b"].join("|"),"g"),k=/^\d[^,]*|,\d[^,]*/g,T=/^,+|,+$/g,C=/^$|,+/;n.openTag="{{",n.closeTag="}}";var S=function(e,t){var r=t.split(":"),a=r.shift(),n=r.join(":")||"";return n&&(n=", "+n),"$helpers."+a+"("+e+n+")"};n.parser=function(e){e=e.replace(/^\s/,"");var t=e.split(" "),r=t.shift(),n=t.join(" ");switch(r){case"if":e="if("+n+"){";break;case"else":t="if"===t.shift()?" if("+t.join(" ")+")":"",e="}else"+t+"{";break;case"/if":e="}";break;case"each":var i=t[0]||"$data",o=t[1]||"as",c=t[2]||"$value",l=t[3]||"$index",s=c+","+l;"as"!==o&&(i="[]"),e="$each("+i+",function("+s+"){";break;case"/each":e="});";break;case"echo":e="print("+n+");";break;case"print":case"include":e=r+"("+t.join(",")+");";break;default:if(/^\s*\|\s*[\w\$]/.test(n)){var u=!0;0===e.indexOf("#")&&(e=e.substr(1),u=!1);for(var p=0,d=e.split("|"),f=d.length,h=d[p++];f>p;p++)h=S(h,d[p]);e=(u?"=":"=#")+h}else e=a.helpers[r]?"=#"+r+"("+t.join(",")+");":"="+e}return e},"function"==typeof define?define(function(){return a}):"undefined"!=typeof exports?module.exports=a:this.template=a}();var storage=window.sessionStorage,key="autoParts_crumbs";$(function(){var e="";switch(Number($(".choose-style").attr("data-type"))){case 1:e=_oeSearchPlaceholder;break;case 2:e=_vinSearchPlaceholder;break;default:e=_keywordSearchPlaceholder}$("#car-type-search-input").bind("keypress",function(e){13==e.keyCode&&$("#car-type-head-searchBtn").trigger("click")}).attr("placeholder",e)});var body=$("body"),carBrandSeriesResult;$(function(){getCarBrandForFilter()});