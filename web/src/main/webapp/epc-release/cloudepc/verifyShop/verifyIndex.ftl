<#escape x as x?html>
<!DOCTYPE html>

<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en" xmlns="http://www.w3.org/1999/html">
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
    <meta charset="UTF-8">
    <title>汽配管家，把你的全车件需求告诉我！guanjia.tqmall.com</title>
    <meta name="keywords" content=“汽配管家,汽配商城,汽配,汽车配件,全车件,车型件,事故件,外观件,汽车配件价格,汽车配件批发,定损,配件">
    <meta name="description"
          content="汽配管家是一个为第三方汽配供应商以及汽修门店提供的汽车配件交易平台。采用的是汽修门店发布需求单，供应商回复报价的形式进行交易磋商，最终在汽配管家的保障下完成交易。">

    <#include "/epc-release/cloudepc/common/resource/css.ftl"/>

    <link rel="stylesheet" href="/epc-release/resources/verifyShop/verify-413d162a08.css">

    <style type="text/css">

    </style>

</head>
<body>
    <#include "/epc-release/cloudepc/common/pagePart/header.ftl">

<input type="hidden" id="nowStep" value="${nowStep}">
<input type="hidden" id="phone" value="${phone}">

    <#if !verifyStatus?exists ||  verifyStatus != 2>

    <section class="top_section">
        <div class="top-content">
            <div class="top-logo" onclick="location.href='/home'"></div>
            <div class="vertical-bar"></div>
            <span class="top_title">
                门店认证
            </span>
        </div>
    </section>


    <div class="content form-wizard">
        <div class="progress_div">
            <ul class="nav nav-pills nav-justified steps">
                <li class="<#if nowStep gt 0 >active</#if>" id="phone_li">
                    <a href="#" data-toggle="tab" class="step">
                        <span class="number">1 </span>
                        <span class="desc">手机号验证</span>
                    </a>
                </li>
                <li class="<#if nowStep gt 1 >active</#if>" id="data_li">
                    <a href="#" data-toggle="tab" class="step">
                        <span class="number">2 </span>
                        <span class="desc">填写资料</span>
                    </a>
                </li>
                <li class="<#if nowStep gt 2 >active</#if>" id="photo_li">
                    <a href="#" data-toggle="tab" class="step">
                        <span class="number">3 </span>
                        <span class="desc">上传照片</span>
                    </a>
                </li>
            </ul>
            <div class="progress progress-striped" role="progressbar">
                <div class="progress-bar progress-bar-success" id="bar" style="width:
                    <#if nowStep == 1 >33.33%;</#if>
                    <#if nowStep == 2 >66.66%;</#if>
                    <#if nowStep == 3 >100%;</#if>
                        ">
                </div>
            </div>
        </div>

        <div class="tab-content">
        <#--手机号码-->
            <div class="tab-pane <#if nowStep == 1 >active</#if>" id="phone_tab">
                <#include "/epc-release/cloudepc/verifyShop/verifyIndex_phone.ftl">
                <div class="btn-group">
                    <div class="col-sm-offset-3 col-md-offset-4 col-sm-9 col-md-8 text_left">
                        <a href="/home" class="cancel-next">取消认证</a>
                        <button class="btn red btn-lg next_btn_show" id="login_btn">下一步</button>
                    </div>
                </div>
                <span class="help_phone_notice">
                        <span class="badge badge-danger">!</span>
                        <span class="problem_span">如有其他疑问，请拨打热线<span class="red">400-9937-288</span></span>
                </span>
            </div>

        <#--填写资料-->
            <#if nowStep gt 1 >
                <div class="tab-pane <#if nowStep == 2 >active</#if>" id="data_tab">
                    <#include "/epc-release/cloudepc/verifyShop/verifyIndex_data.ftl">
                    <div class="btn-group">
                        <div class="col-sm-offset-3 col-md-offset-4 col-sm-9 col-md-8 text_left">
                            <a href="/" class="cancel-next">取消认证</a>
                            <button class="btn blue btn-lg next_btn_show " id="go_step_1_btn">上一步</button>
                            <button class="btn red btn-lg next_btn_show " id="go_step_3_btn">下一步</button>
                        </div>
                    </div>
                    <span class="help_phone_notice">
                        <span class="badge badge-danger">!</span>
                        <span class="problem_span">如有其他疑问，请拨打热线<span class="red">400-9937-288</span></span>
                    </span>
                </div>

            <#--上传照片-->
                <div class="tab-pane <#if nowStep == 3 >active</#if>" id="photo_tab">
                    <#include "/epc-release/cloudepc/verifyShop/verifyIndex_photo.ftl">
                    <div class="btn-group">
                        <div class="col-sm-offset-3 col-md-offset-4 col-sm-9 col-md-8 text_left">
                            <a href="/" class="cancel-next">取消认证</a>
                            <button class="btn blue btn-lg next_btn_show " id="go_step_2_btn">上一步</button>
                            <button class="btn red btn-lg next_btn_show " id="start_verify_btn">申请认证</button>
                        </div>
                    </div>

                    <span class="help_phone_notice">
                        <span class="badge badge-danger">!</span>
                        <span class="problem_span">如有其他疑问，请拨打热线<span class="red">400-9937-288</span></span>
                    </span>
                </div>
            </#if>
        </div>
    </div>

    </#if>

        <#include "/epc-release/cloudepc/common/pagePart/bottom.ftl">
        <#include "/epc-release/cloudepc/common/pagePart/popup.ftl"/>
        <#include "/epc-release/cloudepc/common/resource/js.ftl"/>



</body>
<!-- END BODY -->

<script src="/epc-release/resources/verify/verify-c4bd9fbbc5.js"></script>



    <#if shopVerityBO.verifyFeedback?exists && shopVerityBO.verifyFeedback != "">
    <script type="text/javascript">
        $(function () {
            EPC.alertFuc("您认证失败，失败原因：${shopVerityBO.verifyFeedback}");
        });
    </script>
    </#if>

    <#if verifyStatus?exists &&  verifyStatus gt 0>
    <script type="text/javascript">
        $(function () {
            <#if verifyStatus == 1 >
            EPC.alertFuc("您的认证正在审核中，请耐心等待~~~");
            <#else >
            EPC.alertFuc("您已经认证通过，无需认证~~~");
            </#if>
            setTimeout(function(){
                location.href = "/home";
            },1500)
        });
    </script>
    </#if>

</html>

</#escape>
