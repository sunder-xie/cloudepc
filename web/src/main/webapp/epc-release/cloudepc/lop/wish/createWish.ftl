<#escape x as x?html>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en" xmlns="http://www.w3.org/1999/html">
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
    <meta charset="utf-8"/>
    <title>汽配管家</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta content="width=device-width, initial-scale=1.0" name="viewport"/>
    <meta http-equiv="Content-type" content="text/html; charset=utf-8">

    <#include "/epc-release/cloudepc/common/resource/css.ftl"/>

    <!-- 输入联想样式 -->
    <link rel="stylesheet" href="/epc-release/resources/lop/createWish-cd4fef5265.css">

    <style type="text/css">
        .select2-container--default .select2-selection--single .select2-selection__rendered {
            color: #ABABAB;
        }

        .content label {
            font-size: 12px;
        }

        .content input {
            height: 30px;
            padding-left: 10px;
            border-radius: 5px;
            border: 1px solid #DDDDDD;
        }

        .content textarea {
            color: #ABABAB;
            padding-left: 10px;
            border: 1px solid #DDDDDD;
            border-radius: 5px;
        }

        .create-wish-background {
            padding-bottom: 40px;
            background: url('${ossImage}/img/epc/release_bg.jpg') no-repeat #F2F2F2;
            background-size: 100% 250px;
        }

        .content {
            width: 1000px;
            padding-left: 25px;
            padding-right: 25px;
            margin: 0 auto;
            color: #ABABAB;
            background-color: #FFFFFF;
        }

        .content-top {
            height: 80px;
        }

        .content-main {
            padding-bottom: 40px;
        }

        .sub-module {
            margin-bottom: 30px;
            text-align: left;
        }

        .sub-module-title {
            height: 36px;
            padding-left: 20px;
            margin-bottom: 20px;
            line-height: 36px;
            text-align: left;
            color: #00A9DD;
            background: #F5FAFB;
        }

        .sub-module-body {
            padding-left: 15px;
            padding-right: 15px;
        }

        .process-description {
            line-height: 80px;
            float: left;
        }

        .view-history-wish {
            width: 130px
            height: 28px;
            margin-top: 26px;
            background: #FE7A00;
            float: right;
        }

        .view-history-wish-text {
            line-height: 28px;
        }

        .vin-code {
            margin-right: 15px;
            width: 190px;
        }

        .conversion {
            line-height: 36px;
        }

        .car-select div {
            width: 190px;
            margin-right: 15px;
            text-align: left;
        }

        .car-select select {
            width: 100%;
        }

        .goods-attr {
            padding-right: 10px;
            text-align: left;
        }

        .goods-attr input, select {
            width: 100%;
        }

        .add-goods-item-btn {
            width: 160px;
            height: 38px;
            margin-left: 758px;
            line-height: 38px;
            color: #FFFFFF;
            background: #6EAE27;
        }

        .order-remark {
            width: 100%;
            height: 60px;
            resize: none;
        }

        .submit-wish-btn {
            width: 180px;
            height: 38px;
            margin: 0 auto;
            font-size: 14px;
            line-height: 38px;
            background: #00AADD;
        }

        /*创建需求单成功后的弹出框的样式*/
        .asw-title {
            font-size: 16px;
            text-align: left;
            color: #000000;
        }

        .asw-message {
            color: #ABABAB;
        }

        .asw-message span {
            color: #00A9DD;
        }

        .asw-operation {
            margin-left: 150px;
            margin-top: 40px;
        }

        .asw-btn {
            line-height: 30px;
            background: #00AADD;
            float: left;
        }

        .asw-continue-add {
            background: #FE7A00;
            margin-left: 10px;
        }

        /* 顶部 */
        .top-logo {
            float: left;
            background: url("/img/logo/logo.png");
            width: 168px;
            height: 58px;
            cursor: pointer;
        }

        .vertical-bar {
            width: 20px;
            height: 40px;
            margin-right: 20px;
            border-right: 1px solid #CACACA;
            float: left;
            margin-top: 8px;
        }

        .goto-hyperlink {
            float: left;
            margin-top: 6px;
        }

        .user-center {
            width: 60px;
            color: #FFFFFF;

        }

        .return-index {
            width: 60px;
            margin-top: 5px;
            color: #0BC6E4;
            background: #FFFFFF;
            border: 1px solid #CACACA;
            border-radius: 5px;
            cursor: pointer;
        }

        .top-content {
            margin: 0 auto;
            overflow: hidden;
            padding-top: 30px;
        }

        .top-text {
            margin: 25px auto 45px;
            font-size: 20px;
            color: #FE7A00;

        }

        .user-company-name {
            background: #F2F2F2;
            cursor: not-allowed;
        }

    </style>

</head>
<body>
    <#include "/epc-release/cloudepc/common/pagePart/header.ftl">

<div class="create-wish-background">
    <input type="hidden" id="wishId">
    <input type="hidden" id="token" value="${token}">
    <#if wishInfo??>
        <input type="hidden" id="reCreateFlag" value="true">
    </#if>

    <#--<#include "/epc-release/cloudepc/common/avidCall/avidCallEntry.ftl">-->

    <div class="w1190 top-content">
        <div class="top-logo" onclick="location.href='/home'"></div>
        <div class="vertical-bar"></div>
        <div class="goto-hyperlink">
            <div class="user-center">
                发布需求
            </div>
            <div class="return-index" onclick="location.href='/home'">
                返回首页
            </div>
        </div>
    </div>
    <div class="w1190 top-text">
        全城数百家优质汽配商 / 在线客服专业服务
    </div>

    <div class="content ">

        <div class="content-top">
            <div class="process-description">
                <span>流程说明: </span><img src="/img/lop/release_steps.png">
            </div>
            <div class="operation-btn view-history-wish" onclick="location.href='/wish/myWishPage'">
                <span class="view-history-wish-text">查看历史需求单</span>
            </div>
        </div>

        <div class="content-main">
            <div class="sub-module">
                <div class="sub-module-title">
                    <span>一、&nbsp;输入车辆信息</span>&nbsp;(&nbsp;带有&nbsp;<span class="necessary-star">*</span>的项目是必填项&nbsp;)
                </div>

                <div class="sub-module-body">
                    <div class="text-align-left">
                        <table>
                            <tr>
                                <td class="text-align-left">
                                    <span class="necessary-star">*</span><label class="key-word">车架号/VIN码</label>
                                    <br/>
                                    <input type="text" id="vinCode" maxlength="17" class="vin-code" <#if wishInfo??>value="${wishInfo.vin}"</#if>>
                                    <label>还可以输入&nbsp;<span id="vinCodeLimit" class="necessary-star">17</span>字符</label>
                                </td>
                                <td class="text-align-left" style="padding-left: 100px;">
                                    <lable class="key-word">车辆备注</lable>
                                    <br/>
                                    <div style="height: 30px;">
                                        <input type="checkbox" id="isModifiedVehicle" style="float: left;"
                                               <#if wishInfo?? && wishInfo.isModifiedVehicle = 1>checked="checked"</#if>>
                                        <span class="conversion">改装</span>
                                    </div>
                                </td>
                            </tr>
                        </table>
                    </div>

                    <hr/>

                    <table class="car-select">
                        <tr>
                            <#if wishInfo??>
                                <input type="hidden" id="reCreateBrand" value="${wishInfo.brand}">
                                <input type="hidden" id="reCreateSeries" value="${wishInfo.series}">
                                <input type="hidden" id="reCreateModel" value="${wishInfo.model}">
                                <input type="hidden" id="reCreateYear" value="${wishInfo.year}">
                            </#if>
                            <td>
                                <div>
                                    <span class="necessary-star">*</span><label class="key-word">品牌</label>
                                    <select id="brandSelect"></select>
                                </div>
                            </td>
                            <td>
                                <div>
                                    <span class="necessary-star">*</span><label class="key-word">车系</label>
                                    <select id="seriesSelect"></select>
                                </div>
                            </td>
                            <td>
                                <div>
                                    <span class="necessary-star">*</span><label class="key-word">车型</label>
                                    <select id="modelSelect"></select>
                                </div>
                            </td>
                            <td>
                                <div>
                                    <span class="necessary-star">*</span><label class="key-word">排量年款</label>
                                    <select id="carNameSelect"></select>
                                </div>
                            </td>
                        </tr>
                    </table>
                </div>
            </div>

            <div class="sub-module">
                <div class="sub-module-title">
                    <span>二、&nbsp;输入配件信息</span>&nbsp;(&nbsp;带有&nbsp;<span class="necessary-star">*</span>的项目是必填项&nbsp;)
                </div>

                <div class="sub-module-body">
                    <#if wishInfo??>
                        <input type="hidden" id="reGoodsList" value="${goodsList}">
                    </#if>
                    <table id="goodsItem"></table>
                    <br/>
                    <div class="operation-btn add-goods-item-btn" onclick="CREATE_WISH.addGoodsItem()">
                        <span>+&nbsp;&nbsp;添加配件</span>
                    </div>
                </div>
            </div>

            <div class="sub-module">
                <div class="sub-module-title">
                    <span>三、&nbsp;输入联系方式</span>&nbsp;(&nbsp;带有&nbsp;<span class="necessary-star">*</span>的项目是必填项&nbsp;)
                </div>

                <div class="sub-module-body">
                    <table>
                        <tr>
                            <td width="250" class="goods-attr">
                                <span class="necessary-star">*</span><label class="key-word" readonly="readonly">门店名称</label>
                                <input type="text" id="userCompanyName" value="${CURRENT_USER.shopBO.companyName}" readonly="readonly" class="user-company-name">
                            </td>
                            <td width="150" class="goods-attr">
                                <span class="necessary-star">*</span><label class="key-word">联系电话</label>
                                <input type="text" id="userMobile" value="<#if wishInfo??>${wishInfo.telephone}<#else>${CURRENT_USER.mobile}</#if>">
                            </td>
                            <td width="150" class="goods-attr">
                                <span class="necessary-star">*</span><label class="key-word">填单人</label>
                                <input type="text" id="billRealName" value="<#if wishInfo??>${wishInfo.wishListMaker}<#else>${CURRENT_USER.realName}</#if>">
                            </td>
                            <td width="150" class="goods-attr">
                                <span class="necessary-star">*</span><label class="key-word">填单人电话</label>
                                <input type="text" id="billMobile" value="<#if wishInfo??>${wishInfo.wishListMakerTel}<#else>${CURRENT_USER.mobile}</#if>">
                            </td>
                        </tr>
                    </table>
                </div>
            </div>

            <div class="sub-module">
                <div class="sub-module-title">
                    <span>四、&nbsp;输入订单备注</span>&nbsp;(&nbsp;带有&nbsp;<span class="necessary-star">*</span>的项目是必填项&nbsp;)
                </div>

                <div class="sub-module-body text-align-left">
                    <div class="text-align-left" style="width: 100px">
                        <span class="necessary-star">*</span><label class="key-word">是否开票</label>
                        <br/>
                        <#if wishInfo??>
                            <input type="hidden" id="reIsReceiptPrice" value="${wishInfo.isReceiptPrice}">
                        </#if>
                        <select id="receiptType" style="width: 120px">
                            <option value="0">不开具发票</option>
                            <option value="1">开普通发票</option>
                            <option value="2">开增值税发票</option>
                        </select>
                    </div>

                    <hr/>

                    <label class="key-word">订单备注</label>
                    <br/>
                    <textarea id="wishMemo" class="order-remark" title=""><#if wishInfo??>${wishInfo.wishListMemo}</#if></textarea>
                </div>
            </div>

            <div class="operation-btn submit-wish-btn" onclick="CREATE_WISH.createWish()">免费获取报价</div>

        </div>

    </div>

<#--创建需求单成功后的弹出框-->
    <div id="alert_success_wish" class="modal fade modal-scroll" tabindex="-1" data-keyboard="false" aria-hidden="true" data-width="600px" data-height="200px"
         data-backdrop="static">
        <div class="modal-section">
            <div class="modal-body" style="overflow-x: hidden;">
                <div class="asw-title">需求单已提交成功</div>
                <hr/>
                <div class="asw-message">
                    是否立即前往&nbsp;
                    <span>[我的需求单]</span>
                    &nbsp;查看报价进度?
                </div>
                <div class="asw-operation">
                    <div class="operation-btn asw-btn" onclick="location.href='/wish/myWishPage'">立即前往</div>
                    <div class="operation-btn asw-btn asw-continue-add" style="" onclick="location.href='/wish/createWishPage'">继续填写</div>
                </div>
            </div>
        </div>
    </div>

</div>

    <#include "/epc-release/cloudepc/common/pagePart/bottom.ftl">
    <#include "/epc-release/cloudepc/common/pagePart/popup.ftl"/>
    <#include "/epc-release/cloudepc/common/resource/js.ftl"/>
    <#include "/epc-release/cloudepc/template/template.ftl"/>

<script type="text/html" id="goodsItemTemplate">
    <tr id="goodsItem{{goodsItemNum}}" style="height: 74px;border-bottom: 1px solid #EEEEEE;">
        <td width="190" class="goods-attr">
            <span class="necessary-star">*</span><label class="key-word">商品名称</label>
            <input type="text" id="goodsName{{goodsItemNum}}" name="goodsName">
        </td>
        <td width="120" class="goods-attr">
            <label class="key-word">OE码</label>
            <input type="text" name="goodsOe">
        </td>
        <td width="50" class="goods-attr">
            <span class="necessary-star">*</span><label class="key-word">数量</label>
            <input type="text" value="1" name="goodsNumber" style="text-align: center;padding-left: 0;">
        </td>
        <td width="50" class="goods-attr">
            <label class="key-word">单位</label>
            <input type="text" name="goodsMeasureUnit" style="text-align: center;padding-left: 0;">
        </td>
        <td width="120" class="goods-attr">
            <span class="necessary-star">*</span><label class="key-word">首选品质要求</label>
            <select></select>
        </td>
        <td width="120" class="goods-attr">
            <label class="key-word">备选品质要求</label>
            <select></select>
        </td>
        <td width="190" class="goods-attr">
            <label class="key-word">商品备注</label>
            <input type="text" name="goodsMemo">
        </td>
        <td width="90" class="goods-attr">
            <label class="key-word">上传照片</label>
            <br/>
            <img id="uploadBtn{{goodsItemNum}}" src="/img/lop/addImg.jpg" width="32" height="32" style="margin-right: 8px">
            <img id="preview{{goodsItemNum}}" width="32" height="32" style="border: 1px solid #EDEDED;"
                 onerror="javascript:this.src='/img/no-pic-big.png';this.onerror=null;">
            <input type="hidden" id="goodsImages{{goodsItemNum}}" name="goodsImages">
        </td>
        <td onclick="CREATE_WISH.deleteGoodsItem(this, 'goodsImages{{goodsItemNum}}')">
            <input type="hidden" name="goodsId">
            <img src="/img/lop/release_del_has.png" style="margin-top: 22px;cursor: pointer;">
        </td>
    </tr>
</script>

</body>
<!-- END BODY -->
<script src="/epc-release/resources/lop/createWish-e3cc71d122.js"></script>

</html>

</#escape>
