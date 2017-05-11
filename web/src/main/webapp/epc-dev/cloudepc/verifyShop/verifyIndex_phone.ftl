<div class="form-group">
    <label class="col-md-offset-2 col-md-2 col-sm-offset-1 col-sm-2 text_right control-label">
        <span class="required">*</span>手机号码：
    </label>
    <div class="data_div col-md-6 col-sm-8 text_left">
        <div class="input-icon">
            <i class="fa fa-mobile"></i>
            <input type="text" id="userPhone" autofocus autocomplete="on" value="${phone}" placeholder="手机号" class="form-control">
        </div>
    </div>
</div>
<div class="form-group">
    <label class="col-md-offset-2 col-md-2 col-sm-offset-1 col-sm-2 text_right control-label">
        <span class="required">*</span>校验码：
    </label>
    <div class="data_div col-md-6 col-sm-8 text_left">
        <div class="input-icon col-md-8 col-sm-6 none_padding">
            <i class="fa fa-cog"></i>
            <input type="text" id="checkCode" value="" placeholder="若来自第二步且手机号码不变，可不填写" class="form-control">
        </div>
        <img id="checkCodeImg" class="check-code-img col-md-4 col-sm-6" src="/verifyCode/getVerifyCode?v=${.now?time}" data-url="/verifyCode/getVerifyCode" >

    </div>
</div>
<div class="form-group">
    <label class="col-md-offset-2 col-md-2 col-sm-offset-1 col-sm-2 text_right control-label">
        <span class="required">*</span>验证码：
    </label>
    <div class="data_div col-md-6 col-sm-8 text_left">
        <div class="input-icon col-md-8 col-sm-6 none_padding">
            <i class="fa fa-lock"></i>
            <input type="text" id="verifyCodeInput" value="" placeholder="若来自第二步且手机号码不变，可不填写" class="form-control">
        </div>
        <button id="getCode" class="get-code btn btn-info ">
            获取验证码
        </button>
    </div>
</div>
