var JqAjax={sendAjaxRequest:function(t,e,i,n,c,o,a){var s={url:e,type:t,success:i,errorCallback:c};void 0!==n&&(s.data=n),void 0!==o&&(s.contentType=o),a===!1&&(s.async=!1);var r=AjaxParamProcessor.process(s);$.ajax(r)},get:function(t,e,i,n,c){this.sendAjaxRequest("GET",t,e,i,n,void 0,c)},post:function(t,e,i,n,c){this.sendAjaxRequest("POST",t,e,i,n,c)},postJson:function(t,e,i,n){this.post(t,e,i,n,"application/json")}},Ajax=function(){var t=function(t){var e=AjaxParamProcessor.process(t);$.ajax(e)};return{get:function(e){t(e)},post:function(t){t.type="POST",this.get(t)},postJson:function(t){t.contentType="application/json",this.post(t)}}}(),AjaxParamProcessor={process:function(t){var e=$.extend({url:"",type:"GET",dataType:"json",data:null,async:!0,cache:!0,contentType:"application/x-www-form-urlencoded",timeout:6e4,success:function(){}},t),i=function(){var t=$("#isLoginPage").val();void 0===t&&EPC.confirmNewFuc("非常抱歉，您登录超时了，请重新登录",function(){location.href="/user/loginPage"})},n=e.success;e.success=function(t){if(!t.success){if("99999999"==t.code)return i(),JqUI.unblockUI($("body")),!1;if("99999995"==t.code)return EPC.alertFuc(t.message),JqUI.unblockUI($("body")),!1}n(t)};var c=t.errorCallback;return e.error=function(t,e,n){if(200==t.status&&"parsererror"==e){var o=t.responseText;if(o.indexOf("isLoginPage")>-1){var a=!1;void 0!==c&&null!==c&&$.isFunction(c.timeout)&&(c.timeout(),a=!0),a||i()}else o.indexOf("isErrorPage")>-1&&EPC.alertFuc("非常抱歉，系统发生内部错误")}else 0==t.readyState&&"error"==t.statusText||EPC.alertFuc("非常抱歉，系统发生内部错误");void 0!==c&&null!==c&&$.isFunction(c.unblockUI)&&c.unblockUI(),JqUI.unblockUI($("body"))},e}},CurrentCityId=383,ReloadPageFlag=!1,SetCityCallback={callback:void 0},CityUtil={getAllCity:function(){JqAjax.get("/region/allCity",function(t){t.length>0&&CityUtil.initDropdownCity(t)})},initDropdownCity:function(t){for(var e,i="",n=1,c=1,o=1,a=t.length,s=0;a>s;s++){i+='<p><span class="province-span">'+t[s].name+":</span>",o=t[s].name.length,e=t[s].children;for(var r=e.length,l=0;r>l;l++)i+='<span class="city-span" data-id="'+e[l].id+'">'+e[l].name+"</span>",o+=e[l].name.length;i+="</p>",r>n&&(n=r),o>c&&(c=o)}var u=10*(n+1)+14*c+5,d=30*(a+1)+5,y=450,C=700;d>y&&(d=y),u>C&&(u=C),$("#dropdownCityMenu").width(u).height(d).html(i),CityUtil.bindSelectCity()},bindSelectCity:function(){$(".city-span").click(function(){CityUtil.selectCity(this)})},selectCity:function(t){CityUtil.setCity($(t).text()),CurrentCityId=$(t).data("id");var e=$("body");JqUI.blockUI(e),JqAjax.post("/user/selectCity",function(t){t.success&&(1===IsLogin&&ShoppingCart.reloadCartNum(),$.isFunction(SetCityCallback.callback)&&SetCityCallback.callback()),JqUI.unblockUI(e)},{cityId:CurrentCityId})},setCity:function(t){$("#citySelect").text(t)},checkOpenCity:function(t){(void 0===t||null===t)&&(t="/wish/createWishPage");var e=$("body");JqUI.blockUI(e),Ajax.get({url:"/user/checkCityForWish",success:function(i){if(i.success)location.href=t;else{var n=i.code;if("01"==n){var c=$("#isLoginPage").val();void 0===c&&EPC.confirmNewFuc("非常抱歉，您登录超时了，请重新登录",function(){location.href="/user/loginPage"})}else if("02"==n)EPC.alertFuc(i.message);else{var o='<div style="text-align: left;font-size: 14px;">'+_unOpenCityMsg+"</div>";EPC.alertFuc(o)}}JqUI.unblockUI(e)}})},checkCreateWish:function(){CityUtil.checkOpenCity()},checkAvidCall:function(){CityUtil.checkOpenCity("/avidCall")}};