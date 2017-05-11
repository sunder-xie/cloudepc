/**
 * Created by huangzhangting on 16/7/12.
 */

var AjaxLogin = {
    init: function(callbackFun){
        EPC.alertFuc(template('loginFormTemplate'), undefined, '460');

        //聚焦无效，弹窗比较慢
        //$('#userNameInput').focus();

        //输入框绑定回车事件
        $('.form-control').bind('keypress', function (event) {
            if (event.keyCode == 13) {
                $('#loginButton').trigger('click');
            }
        });

        //登录按钮
        $('#loginButton').click(function(){
            var input = $('#userNameInput');
            var mobile = input.val().trim();
            if(!EPC.isMobile(mobile)){
                TipUtil.tooltipFun(input, '请填写正确的手机号');
                input.focus();
                return;
            }
            input = $('#verifyCodeInput');
            var verifyCode = input.val().trim();
            if(verifyCode==''){
                TipUtil.tooltipFun(input, '请填写验证码');
                input.focus();
                return;
            }

            JqAjax.post('/user/doLogin', function(result){
                if(result.success){
                    EPC.closeAlert();
                    //修改头部显示
                    var text = '欢迎您，'+result.data.hiddenMobile;
                    $('.header-login').text(text).removeClass('header-login-color').attr('href', '#');
                    $('.header-logout').removeClass('hidden');
                    //修改用户全局状态
                    IsLogin = 1;
                    if(result.data.shopBO != null && result.data.shopBO.verifyStatus == 2){
                        IsVerifySuccessShop = 1;
                    }

                    if($.isFunction(callbackFun)){
                        callbackFun();
                    }

                    //TODO 登陆成功，查询用户相关信息


                }else{
                    alert(result.message);
                }
            }, {userName: mobile, verifyCode: verifyCode});

        });

        //获取验证码
        $('.verify-code-bt').click(function(){

            sendVerify(this);

        });


        var intervalId; //定时器id
        var count = 60; //倒计时，秒
        var curCount; //当前剩余秒数

        function sendVerify(el) {
            var input = $('#userNameInput');
            var mobile = input.val().trim();
            if(!EPC.isMobile(mobile)){
                TipUtil.tooltipFun(input, '请填写正确的手机号');
                input.focus();
                return;
            }

            $(el).attr('disabled', true).addClass('disabled-bt');

            JqAjax.get('/user/sendVerify', function(result){

                TipUtil.tooltipFun(el, result.message);

                if(result.success){
                    $('#verifyCodeInput').focus();
                }else{
                    initVerifyCodeBt();
                }

            }, {mobile: mobile});

            curCount = count;
            $(el).text(curCount + '秒再获取');
            intervalId = window.setInterval(setRemainTime, 1000);
        }

        function setRemainTime() {
            if (curCount == 0) {
                initVerifyCodeBt();
            } else {
                curCount--;
                $('.verify-code-bt').text(curCount + '秒再获取');
            }
        }

        function initVerifyCodeBt() {
            window.clearInterval(intervalId);
            $('.verify-code-bt').text('重新获取验证码').removeClass('disabled-bt').removeAttr('disabled');
        }

    }
};

var TimeoutCallback = {
    timeout: function(){
        AjaxLogin.init(this.callbackFun);
    },
    callbackFun: undefined
};
