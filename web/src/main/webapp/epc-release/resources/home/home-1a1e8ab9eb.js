function init_storage(){var e=JSON.parse(storage.getItem(key));(void 0==e||null==e)&&(e={},storage.setItem(key,JSON.stringify(e)))}function changePlaceHolder(e,t){var n=$("#search_input");n.val(""),n.focus(),TipUtil.destroyTip(n),0==e?(n.unbind("keyup"),n.attr("placeholder",_keywordSearchPlaceholder),n.removeAttr("maxlength"),$("#keywordButton").css("opacity","0.7"),$("#oeButton").css("opacity","0.4"),$("#vinButton").css("opacity","0.4"),$("#searchBtn").unbind("click").click(function(){var e=n.val().trim();return""==e?(TipUtil.tooltipFun(n,_keywordSearchTip,"bottom"),!1):void(window.location.href="/autoParts/goods/keyword?q="+e)})):1==e?(n.unbind("keyup").on("keyup",function(e){37!=e.keyCode&&38!=e.keyCode&&39!=e.keyCode&&40!=e.keyCode&&$(this).val($(this).val().replace(/[\u4E00-\u9FA5]/g,""))}),n.attr("placeholder",_oeSearchPlaceholder),n.removeAttr("maxlength"),$("#keywordButton").css("opacity","0.4"),$("#oeButton").css("opacity","0.7"),$("#vinButton").css("opacity","0.4"),$("#searchBtn").unbind("click").click(function(){var e=n.val().trim();return""==e?(TipUtil.tooltipFun(n,_oeSearchTip,"bottom"),!1):void(window.location.href="/autoParts/goods/oe?oem="+e)})):(n.unbind("keyup").on("keyup",function(e){37!=e.keyCode&&38!=e.keyCode&&39!=e.keyCode&&40!=e.keyCode&&$(this).val($(this).val().replace(/[\u4E00-\u9FA5]/g,""))}),n.attr("placeholder",_vinSearchPlaceholder),n.attr("maxlength","17"),$("#keywordButton").css("opacity","0.4"),$("#oeButton").css("opacity","0.4"),$("#vinButton").css("opacity","0.7"),$("#searchBtn").unbind("click").click(function(){var e=n.val().trim();return EPC.isVin(e)?void JqAjax.post("/epc/car/getOnlineCarByVin",getVinResult,{vin:e}):(TipUtil.tooltipFun(n,_vinSearchTip,"bottom"),!1)})),$(t).css("color","#02aadb"),$(t).css("font-size","14px")}function getVinResult(e){var t=$("#search_input").val().trim(),n="/autoParts/category/category?carId=CARID&vinNumber="+t;if(e.success){var r=e.data;if(1==r.length)window.location.href=n.replace("CARID",r[0].id)+"&seriesId="+r[0].seriesId;else{var a=template("choose_car_template",{list:r});EPC.alertFuc(a),$(".choose_car").unbind("click").click(function(){var e=$(this).attr("data-id"),t=$(this).attr("data-sid");window.location.href=n.replace("CARID",e)+"&seriesId="+t})}}else EPC.alertFuc("很抱歉，未查询到您提交的车辆信息。");return!1}function hideWebsiteMessage(){$("#websiteMessage").css("display","none")}!function(){function e(e){return e.replace(m,"").replace(b,",").replace(k,"").replace(w,"").replace(x,"").split(T)}function t(e){return"'"+e.replace(/('|\\)/g,"\\$1").replace(/\r/g,"\\r").replace(/\n/g,"\\n")+"'"}function n(n,r){function a(e){return f+=e.split(/\n/).length-1,u&&(e=e.replace(/\s+/g," ").replace(/<!--[\w\W]*?-->/g,"")),e&&(e=y[1]+t(e)+y[2]+"\n"),e}function i(t){var n=f;if(l?t=l(t,r):o&&(t=t.replace(/\n/g,function(){return f++,"$line="+f+";"})),0===t.indexOf("=")){var a=p&&!/^=[=#]/.test(t);if(t=t.replace(/^=[=#]?|[\s;]*$/g,""),a){var i=t.replace(/\s*\([^\)]+\)/,"");d[i]||/^(include|print)$/.test(i)||(t="$escape("+t+")")}else t="$string("+t+")";t=y[1]+t+y[2]}return o&&(t="$line="+n+";"+t),v(e(t),function(e){if(e&&!h[e]){var t;t="print"===e?b:"include"===e?k:d[e]?"$utils."+e:$[e]?"$helpers."+e:"$data."+e,w+=e+"="+t+",",h[e]=!0}}),t+"\n"}var o=r.debug,c=r.openTag,s=r.closeTag,l=r.parser,u=r.compress,p=r.escape,f=1,h={$data:1,$filename:1,$utils:1,$helpers:1,$out:1,$line:1},g="".trim,y=g?["$out='';","$out+=",";","$out"]:["$out=[];","$out.push(",");","$out.join('')"],m=g?"$out+=text;return $out;":"$out.push(text);",b="function(){var text=''.concat.apply('',arguments);"+m+"}",k="function(filename,data){data=data||$data;var text=$utils.$include(filename,data,$filename);"+m+"}",w="'use strict';var $utils=this,$helpers=$utils.$helpers,"+(o?"$line=0,":""),x=y[0],T="return new String("+y[3]+");";v(n.split(c),function(e){e=e.split(s);var t=e[0],n=e[1];1===e.length?x+=a(t):(x+=i(t),n&&(x+=a(n)))});var C=w+x+T;o&&(C="try{"+C+"}catch(e){throw {filename:$filename,name:'Render Error',message:e.message,line:$line,source:"+t(n)+".split(/\\n/)[$line-1].replace(/^\\s+/,'')};}");try{var _=new Function("$data","$filename",C);return _.prototype=d,_}catch(B){throw B.temp="function anonymous($data,$filename) {"+C+"}",B}}var r=function(e,t){return"string"==typeof t?g(t,{filename:e}):o(e,t)};r.version="3.0.0",r.config=function(e,t){a[e]=t};var a=r.defaults={openTag:"<%",closeTag:"%>",escape:!0,cache:!0,compress:!1,parser:null},i=r.cache={};r.render=function(e,t){return g(e,t)};var o=r.renderFile=function(e,t){var n=r.get(e)||h({filename:e,name:"Render Error",message:"Template not found"});return t?n(t):n};r.get=function(e){var t;if(i[e])t=i[e];else if("object"==typeof document){var n=document.getElementById(e);if(n){var r=(n.value||n.innerHTML).replace(/^\s*|\s*$/g,"");t=g(r,{filename:e})}}return t};var c=function(e,t){return"string"!=typeof e&&(t=typeof e,"number"===t?e+="":e="function"===t?c(e.call(e)):""),e},s={"<":"&#60;",">":"&#62;",'"':"&#34;","'":"&#39;","&":"&#38;"},l=function(e){return s[e]},u=function(e){return c(e).replace(/&(?![\w#]+;)|[<>"']/g,l)},p=Array.isArray||function(e){return"[object Array]"==={}.toString.call(e)},f=function(e,t){var n,r;if(p(e))for(n=0,r=e.length;r>n;n++)t.call(e,e[n],n,e);else for(n in e)t.call(e,e[n],n)},d=r.utils={$helpers:{},$include:o,$string:c,$escape:u,$each:f};r.helper=function(e,t){$[e]=t};var $=r.helpers=d.$helpers;r.onerror=function(e){var t="Template Error\n\n";for(var n in e)t+="<"+n+">\n"+e[n]+"\n\n";"object"==typeof console&&console.error(t)};var h=function(e){return r.onerror(e),function(){return"{Template Error}"}},g=r.compile=function(e,t){function r(n){try{return new s(n,c)+""}catch(r){return t.debug?h(r)():(t.debug=!0,g(e,t)(n))}}t=t||{};for(var o in a)void 0===t[o]&&(t[o]=a[o]);var c=t.filename;try{var s=n(e,t)}catch(l){return l.filename=c||"anonymous",l.name="Syntax Error",h(l)}return r.prototype=s.prototype,r.toString=function(){return s.toString()},c&&t.cache&&(i[c]=r),r},v=d.$each,y="break,case,catch,continue,debugger,default,delete,do,else,false,finally,for,function,if,in,instanceof,new,null,return,switch,this,throw,true,try,typeof,var,void,while,with,abstract,boolean,byte,char,class,const,double,enum,export,extends,final,float,goto,implements,import,int,interface,long,native,package,private,protected,public,short,static,super,synchronized,throws,transient,volatile,arguments,let,yield,undefined",m=/\/\*[\w\W]*?\*\/|\/\/[^\n]*\n|\/\/[^\n]*$|"(?:[^"\\]|\\[\w\W])*"|'(?:[^'\\]|\\[\w\W])*'|\s*\.\s*[$\w\.]+/g,b=/[^\w$]+/g,k=new RegExp(["\\b"+y.replace(/,/g,"\\b|\\b")+"\\b"].join("|"),"g"),w=/^\d[^,]*|,\d[^,]*/g,x=/^,+|,+$/g,T=/^$|,+/;a.openTag="{{",a.closeTag="}}";var C=function(e,t){var n=t.split(":"),r=n.shift(),a=n.join(":")||"";return a&&(a=", "+a),"$helpers."+r+"("+e+a+")"};a.parser=function(e){e=e.replace(/^\s/,"");var t=e.split(" "),n=t.shift(),a=t.join(" ");switch(n){case"if":e="if("+a+"){";break;case"else":t="if"===t.shift()?" if("+t.join(" ")+")":"",e="}else"+t+"{";break;case"/if":e="}";break;case"each":var i=t[0]||"$data",o=t[1]||"as",c=t[2]||"$value",s=t[3]||"$index",l=c+","+s;"as"!==o&&(i="[]"),e="$each("+i+",function("+l+"){";break;case"/each":e="});";break;case"echo":e="print("+a+");";break;case"print":case"include":e=n+"("+t.join(",")+");";break;default:if(/^\s*\|\s*[\w\$]/.test(a)){var u=!0;0===e.indexOf("#")&&(e=e.substr(1),u=!1);for(var p=0,f=e.split("|"),d=f.length,$=f[p++];d>p;p++)$=C($,f[p]);e=(u?"=":"=#")+$}else e=r.helpers[n]?"=#"+n+"("+t.join(",")+");":"="+e}return e},"function"==typeof define?define(function(){return r}):"undefined"!=typeof exports?module.exports=r:this.template=r}();var size,list1,list2,storage=window.sessionStorage,key="autoParts_crumbs";$(document).ready(function(){init_storage(),$("#website_message").modal("show");var e=$("#search_input");e.focus(),e.bind("keypress",function(e){13==e.keyCode&&$("#searchBtn").trigger("click")}),changePlaceHolder(0)});