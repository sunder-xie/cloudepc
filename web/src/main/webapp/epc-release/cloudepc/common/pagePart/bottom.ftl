<style type="text/css">
    .bottom-with{
        width: 1100px;
    }
    .page-bottom {
        border-top: 1px solid #d6e5ec;
        color: #999999;
        background: #f8f8f8;
    }

    .page-bottom-content {
        height: 53px;
        padding-top: 18px;
        margin: 0 auto;
    }

    .page-bottom-content-left {
        float: left;
    }

    .page-bottom-content-right {
        float: right;
    }
    .page-bottom-content-right label{
        margin-left: 7px;
        margin-right: 7px;
    }

    .supported-brand {
        height: 100px;
        padding-top: 25px;
        margin: 0 auto;
        border-bottom: 1px solid #d6e5ec;
    }

    .brand-style{
        width: 75px;
        height: 50px;
        margin: 0 10px;
    }

    .circle-style{
        width: 6px;
        height: 6px;
        border-radius: 6px;
        background-color: #d2d0d0;
        margin-left: 5px;
    }

</style>

<div class="page-bottom">

    <div class="supported-brand bottom-with">
    <#list allCarBrands as brand>
        <#if brand_index < 8 >
            <img src="${brand.logo}" alt="${brand.name}" class="brand-style">
        </#if>
    </#list>
    <#if (allCarBrands?size > 8) >
        <label class="circle-style"></label>
        <label class="circle-style"></label>
        <label class="circle-style"></label>
    </#if>
    </div>

    <div class="page-bottom-content bottom-with">
        <div class="page-bottom-content-left">
            <span>Copyright © 2016 Tqmall.com All Rights Reserved.</span>
        </div>
        <div class="page-bottom-content-right">
            <a href="/help/introduction?type=5" target="_blank">联系我们</a>
            <label>|</label>
            <a href="http://www.tqmall.com" target="_blank">淘汽档口</a>
        </div>
    </div>
</div>

<#-- 右侧浮动条 -->
<#include "/epc-release/cloudepc/common/pagePart/rightLayer.ftl">
