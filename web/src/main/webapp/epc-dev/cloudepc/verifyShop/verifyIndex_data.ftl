<div class="form-group">
    <label class="col-md-offset-2 col-md-2 col-sm-offset-1 col-sm-2 text_right control-label">
        <span class="required">*</span>手机号：
    </label>
    <div class="data_div col-md-6 col-sm-8 text_left">
        <div class="input-icon">
            <i class="fa fa-mobile"></i>
            <input type="text" autocomplete="on" value="${phone}" disabled class="form-control must_input">
        </div>
    </div>
</div><div class="form-group">
    <label class="col-md-offset-2 col-md-2 col-sm-offset-1 col-sm-2 text_right control-label">
        <span class="required">*</span>门头名称：
    </label>
    <div class="data_div col-md-6 col-sm-8 text_left">
        <div class="input-icon">
            
            <input type="text" id="userTitle" autofocus autocomplete="on" value="${shopVerityBO.userTitle}" <#if nowStep == 3 >disabled</#if> class="form-control must_input">
        </div>
    </div>
</div>
<div class="form-group">
    <label class="col-md-offset-2 col-md-2 col-sm-offset-1 col-sm-2 text_right control-label">
        <span class="required">*</span>营业执照名称：
    </label>
    <div class="data_div col-md-6 col-sm-8 text_left">
        <div class="input-icon">
            
            <input type="text" id="businessLicence" value="${shopVerityBO.businessLicence}" placeholder="如果没有营业执照,请拨打热线400-9937-288,客服人员将帮助您完成认证" <#if nowStep == 3 >disabled</#if> class="form-control must_input">
        </div>
    </div>
</div>
<div class="form-group">
    <label class="col-md-offset-2 col-md-2 col-sm-offset-1 col-sm-2 text_right control-label">
        <span class="required">*</span>联系人：
    </label>
    <div class="data_div col-md-6 col-sm-8 text_left">
        <div class="input-icon">
            
            <input type="text" id="contactName" autocomplete="on" value="${shopVerityBO.contact}" placeholder="填写联系人姓名" <#if nowStep == 3 >disabled</#if> class="form-control must_input">
        </div>
    </div>
</div>
<div class="form-group">
    <label class="col-md-offset-2 col-md-2 col-sm-offset-1 col-sm-2 text_right control-label">
        <span class="required">*</span>所在地区：
    </label>
    <div class="data_div col-md-6 col-sm-8 text_left">
        <input type="hidden" id="provinceId" value="${shopVerityBO.province}">
        <input type="hidden" id="cityId" value="${shopVerityBO.city}">
        <input type="hidden" id="districtId" value="${shopVerityBO.district}">
        <input type="hidden" id="streetId" value="${shopVerityBO.street}">
        <div class="col-md-3 select2_div">
            <select class="form-control select2me must_input" id="province" <#if nowStep == 3 >disabled</#if> data-placeholder="请选择省份">
            </select>
        </div>
        <div class="col-md-3 select2_div">
            <select class="form-control select2me must_input" id="city" <#if nowStep == 3 >disabled</#if> data-placeholder="请选择城市">
            </select>
        </div>
        <div class="col-md-3 select2_div">
            <select class="form-control select2me must_input" id="district" <#if nowStep == 3 >disabled</#if> data-placeholder="请选择区县">
            </select>
        </div>
        <div class="col-md-3 select2_div">
            <select class="form-control select2me must_input" id="street" <#if nowStep == 3 >disabled</#if> data-placeholder="请选择街道">
            </select>
        </div>

    </div>
</div>
<div class="form-group">
    <label class="col-md-offset-2 col-md-2 col-sm-offset-1 col-sm-2 text_right control-label">
        <span class="required">*</span>详细地址：
    </label>
    <div class="data_div col-md-6 col-sm-8 text_left">
        <div class="input-icon">
            
            <input type="text" id="address"  value="${shopVerityBO.address}" placeholder="填写详细地址" <#if nowStep == 3 >disabled</#if> class="form-control must_input">
        </div>
    </div>
</div>
<div class="form-group">
    <label class="col-md-offset-2 col-md-2 col-sm-offset-1 col-sm-2 text_right control-label">
        业务经理手机：
    </label>
    <div class="data_div col-md-6 col-sm-8 text_left">
        <div class="input-icon">
            
            <input type="text" id="saleMobile" value="${shopVerityBO.saleMobile}"  placeholder="非必填" <#if nowStep == 3 >disabled</#if> class="form-control">
        </div>
    </div>
</div>
