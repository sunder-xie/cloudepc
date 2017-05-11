<#--这个其实是 订单  这一期的头部-->

<style type="text/css">
    .select2-container--default .select2-selection--single {
        background-color: #fff;
        border: 1px solid #aaa;
        border-radius: 0;
    }

    .top-first {
        background: url('${ossImage}/img/epc/center_bg.jpg') no-repeat;
        background-size: 100% 100%;
        height: 100px;
        padding-top: 22px;
    }

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

    .operation {
        float: right;
        padding-top: 12px;
    }

    .create-wish {
        width: 100px;
        height: 28px;
        margin-left: 15px;
        color: #0BC6E4;
        line-height: 26px;
        background: #FFFFFF;
        border-radius: 3px;
        cursor: pointer;
    }

    .select2-container--default .select2-selection--single .select2-selection__rendered {
        color: #ABABAB;
        line-height: 26px;
    }

    .top-content {
        margin: 0 auto;
    }

    /*search*/
    .search-operation {
        padding-top: 4px;
        float: left;
    }

    .options-switch {
        width: 80px;
        height: 28px;
        border: 1px solid #CACACA;
        border-left: none !important;
        border-right: none !important;
    }

    .search-input {
        width: 200px;
        height: 28px;
        padding-left: 10px;
        padding-right: 10px;
        border: 1px solid #CACACA;
        border-left: none;
        border-right: none;
    }

    .search-btn {
        width: 28px;
        height: 28px;
        line-height: 26px;
        background: #FFF;
        border: 1px solid #CACACA;
        border-left: none;
        border-top-right-radius: 3px;
        border-bottom-right-radius: 3px;
        cursor: pointer;
    }

</style>

<#include "/epc-dev/cloudepc/common/pagePart/header.ftl"/>
<div class="top-first">
    <div class="w1190 top-content">
        <div class="top-logo" onclick="location.href='/home'"></div>

        <div class="vertical-bar"></div>

        <div class="goto-hyperlink">
            <div class="user-center">
            ${TOP_MSG?default('个人中心')}
            </div>
            <div class="return-index" onclick="location.href='/home'">
                返回首页
            </div>
        </div>

        <div class="operation">

        </div>

        <div class="operation">

            <div class="search-operation">
                <select id="optionsSwitch" class="options-switch">
                    <option value="0">关键词</option>
                    <option value="1">OE码</option>
                    <option value="2">VIN码</option>
                </select>
            </div>
            <div class="search-operation">
                <input id="searchInput" type="text" class="search-input">
            </div>
            <div class="search-operation">
                <div id="searchBtn" class="search-btn">
                    <i class="fa fa-search"></i>
                </div>
            </div>
            <div class="search-operation " onclick="CityUtil.checkCreateWish()">
                <div class="create-wish">发布需求单</div>
            </div>
        </div>
    </div>

</div>

<!-- build:js ../resources/common/pagePart/topNew.js -->
<script src="/epc-dev/resources/common/other/template.js" type="text/javascript"></script>
<!-- endbuild -->

<script id="chooseCarTemplate" type="text/html">
    <div style="border: 1px solid #d6e5ec;border-radius: 5px;">
        {{each list as carBO i}}
        <div data-id="{{carBO.id}}" data-sid="{{carBO.seriesId}}" class="choose_car"
             style="text-align: left;padding: 15px;{{if list.length != (i+1)}}border-bottom: 1px solid #d6e5ec;{{/if}}">
            <i class="fa fa-car"></i>
            <span>{{carBO.carName}}</span>
        </div>
        {{/each}}
    </div>
</script>
