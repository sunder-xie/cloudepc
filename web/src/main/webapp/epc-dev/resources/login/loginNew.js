/**
 * 新改版的首页
 * Created by lyj on 16/8/10.
 */

var LOGIN_NEW = function () {
    var intervalId; //定时器id
    var count = 60; //倒计时，秒
    var curCount; //当前剩余秒数

    var init = function () {
        $('#userNameInput').focus();

        $('#clearPhone').on('click', function(){
           $('#userNameInput').val('');
           $('#verifyCodeInput').val('');
        });

        //输入框绑定回车事件
        $('#userNameInput').bind('keypress', function (event) {
            if (event.keyCode == 13) {
                $('#loginButton').trigger('click');
            }
        });
        $('#verifyCodeInput').bind('keypress', function (event) {
            if (event.keyCode == 13) {
                $('#loginButton').trigger('click');
            }
        });

        //登录按钮
        $('#loginButton').click(function () {
            var input = $('#userNameInput');
            var mobile = input.val().trim();
            if (!EPC.isMobile(mobile)) {
                TipUtil.tooltipFun($('#loginButton'), '请填写正确的手机号');
                input.focus();
                return;
            }
            input = $('#verifyCodeInput');
            var verifyCode = input.val().trim();
            if (verifyCode == '') {
                TipUtil.tooltipFun($('#loginButton'), '请填写验证码');
                input.focus();
                return;
            }

            window.location.href = '/user/login?userName=' + mobile + '&verifyCode=' + verifyCode;
        });

        //获取验证码
        $('#getCode').click(function () {
            sendVerify(this,$("#userNameInput"),$('#checkCode'));
        });

        //校验码点击事件
        $("#checkCodeImg").click(function () {
            $(this).attr('src', $(this).data('url') + "?v=" + new Date().getTime());
        });

        //认证引导页
        $('#cert-btn,.login-cert-wrap').hover(function () {
            $('.login-cert-wrap').show();
        }, function () {
            $('.login-cert-wrap').hide();
        });
        //跳转到认证页面
        $("#cert-btn,.cert-fast").click(function(){
            window.location.href= '/verifyShop/verifyIndex';
        });
    };

    function sendVerify(send_btn_obj,phone_obj,checkCode_obj) {
        var input = $(phone_obj);
        var mobile = input.val().trim();
        if (!EPC.isMobile(mobile)) {
            TipUtil.tooltipFun(input, '请填写正确的手机号');
            input.focus();
            return;
        }

        input = $(checkCode_obj);
        var checkCode = input.val().trim();
        if (checkCode == '') {
            TipUtil.tooltipFun(input, '请填写校验码');
            input.focus();
            return;
        }

        $(send_btn_obj).attr('disabled', true).addClass('disabled-bt');

        JqAjax.get('/user/sendVerify', function (result) {
            TipUtil.tooltipFun(send_btn_obj, result.message);

            if (result.success) {
                $('#verifyCodeInput').focus();
            } else {
                initVerifyCodeBt(send_btn_obj);
                //刷新校验码
                //$('#checkCodeImg').attr('src', $('#checkCodeImg').data('url') + "?v=" + new Date().getTime());
            }

        }, {mobile: mobile, checkCode: checkCode});

        curCount = count;
        $(send_btn_obj).text(curCount + '秒再获取');

        intervalId = setInterval(function(){setRemainTime(send_btn_obj)}, 1000);
    }

    function setRemainTime(send_btn_obj) {
        if (curCount == 0) {
            initVerifyCodeBt(send_btn_obj);
        } else {
            curCount--;
            $(send_btn_obj).text(curCount + '秒再获取');
        }
    }

    function initVerifyCodeBt(send_btn_obj) {
        clearInterval(intervalId);
        $(send_btn_obj).text('重新获取验证码').removeClass('disabled-bt').removeAttr('disabled');
    }

    return {
        init: function () {
            init();
        },
        sendVerify:function(send_btn_obj,phone_obj,checkCode_obj){
            sendVerify(send_btn_obj,phone_obj,checkCode_obj);
        }
    }
}();

