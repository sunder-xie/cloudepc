<#escape x as x?html>
<!DOCTYPE html>

<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en">
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
    <meta charset="UTF-8">
    <title>汽配管家，把你的全车件需求告诉我！guanjia.tqmall.com</title>
    <meta name="keywords" content="OE,汽配管家,汽配商城,汽配,汽车配件,全车件,车型件,事故件,外观件,汽车配件价格,汽车配件批发,定损,配件">
    <meta name="description" content="汽配管家是一个为第三方汽配供应商以及汽修门店提供的汽车配件交易平台。采用的是汽修门店发布需求单，供应商回复报价的形式进行交易磋商，最终在汽配管家的保障下完成交易。">

    <#include "/epc-release/cloudepc/common/resource/css.ftl"/>

    <style type="text/css">
        .cursor-pointer {
            cursor: pointer;
        }

        .index-head {
            height: 552px;
        }

        .user-info {
            height: 42px;
            padding-right: 200px;
            background-color: #f8f8f8;
        }

        .user-info > li {
            float: right;
            margin-left: 10px;
        }

        .choose-type {
            height: 32px;
        }

        .choose-type li {
            width: 60px;
            padding-top: 7px;
            background: #333a4d;
            opacity: 0.4;
            border-top-left-radius: 10px;
            border-top-right-radius: 10px;
            height: 32px;
            font-size: 14px;
        }

        .choose-type li:hover {
            opacity: 0.6 !important;
        }

        .choose-type-name {
            color: #ffffff;
            font-size: 14px
        }

        .search-input-div {
            width: 90%;
            float: left;
            border: 3px solid #729EBB;
            border-right: none;
            border-bottom-left-radius: 5px;
        }

        .input-search {
            width: 100%;
            height: 54px;
            padding-left: 10px;
            padding-right: 10px;
            background: white;
        }

        .search-button-div {
            float: right;
            width: 10%;
            height: 60px;
            padding-top: 19px;
            background: #f1592a;
            cursor: pointer;
            border: 3px solid #729EBB;
            border-left: none;
            border-top-right-radius: 5px;
            border-bottom-right-radius: 5px;
        }

        .search-button {
            color: #ffffff;
            width: 60px;
            height: 60px;
            background: none;
        }

        .user-operation {
            height: 510px;
            background: url('${ossImage}/img/epc/home/beijing.png') 50%;
            position: relative;
        }

        .index-logo {
            padding-top: 25px;
            text-align: right
        }

        .user-search {
            padding-top: 218px;
        }

        .user-search > li {
            padding-top: 218px;
        }

        .website-message {
            width: 100%;
            height: 50px;
            padding-top: 15px;
            position: absolute;
            bottom: 0;
            background-color: rgba(0, 0, 0, 0.5);
        }

        .website-message > label {
            font-size: 16px;
            color: #fffefe;
        }

        .website-message > span {
            font-size: 16px;
            color: #f4523d;
        }

        .website-message > a {
            padding-right: 15px;
            float: right;
        }

        .index-slogan {
            height: 160px;
            padding-top: 65px;
            padding-bottom: 65px;
            background: #F9F6F6;
        }

        .website-slogan {
            font-size: 36px;
            color: #333333;
        }

        .index-functional-introduce {
            width: 1090px;
            height: 160px;
            margin: 40px auto;
        }

        .functional-ico div {
            float: left;
            width: 362px;
        }

        .introduce-info div {
            margin-top: 20px;
            float: left;
            width: 362px;
        }

        .introduce-info h {
            font-size: 20px;
        }

        .part-search {
            color: #bdc63f;
        }

        .vin-search {
            color: #5ca9dd;
        }

        .model-search {
            color: #f1592a;
        }

        .introduce-info p {
            font-size: 14px;
            color: #999999;
            margin-bottom: 0;
            padding-left: 121px;
            text-align: left;
        }

        .model-search-div > p {
            padding-left: 131px;
        }

        .first-introduce {
            margin-top: 5px;
        }

        /*.supported-brand {*/
            /*width: 100%;*/
            /*height: 100px;*/
            /*padding-top: 25px;*/
            /*margin: 0 auto;*/
            /*background: #f8f8f8;*/
        /*}*/
    </style>
</head>
<body>
<#include "/epc-release/cloudepc/common/pagePart/header.ftl"/>
<div class="index-head">

    <div class="user-operation">
        <div class="row">
            <div class="col-md-3 index-logo">
                <img src="/img/logo/logo_02.png" style="cursor: pointer" onclick="location.href='/home'">
            </div>
            <div class="col-md-6 user-search">
                <div class="choose-type">
                    <ul class="ul-style">
                        <li id="keywordButton" class="cursor-pointer" onclick="changePlaceHolder(0, this)">
                            <span class="choose-type-name">关键词</span>
                        </li>
                        <li id="oeButton" class="margin-left-3 cursor-pointer" onclick="changePlaceHolder(1, this)">
                            <span class="choose-type-name">OE码</span>
                        </li>
                        <li id="vinButton" class="margin-left-3 cursor-pointer" onclick="changePlaceHolder(2, this)">
                            <span class="choose-type-name">VIN码</span></li>
                        <li class="margin-left-3 cursor-pointer" onclick="window.location.href='/autoParts/car/carModel'">
                            <span class="choose-type-name">车型</span>
                        </li>
                    </ul>
                </div>
                <div class="search-input-div">
                    <input id="search_input" type="text" class="input-search">
                </div>
                <div id="searchBtn" class="search-button-div cursor-pointer">
                    <span class="search-button">搜 索</span>
                </div>

            </div>
        </div>
        <div id="websiteMessage" class="website-message">
            <label>即将新增大批车型数据，</label>
            <span>敬请关注</span>
            <label>！</label>
            <a href="javascript: hideWebsiteMessage()">X</a>
        </div>
    </div>
</div>

<div class="index-slogan">
    <span class="website-slogan">查配件，买配件，就上汽配管家！</span>
</div>

<div class="index-functional-introduce">
    <div class="functional-ico">
        <div><img src="/img/home/peijian.png"></div>
        <div><img src="/img/home/vin.png"></div>
        <div><img src="/img/home/chexing.png"></div>
    </div>

    <div class="introduce-info">
        <div>
            <h class="part-search">按配件号查询</h>
            <p class="first-introduce">配件号精准查询</p>
            <p>快速获取配件信息及价格</p>
            <p>找件原来很简单</p>
        </div>
        <div>
            <h class="vin-search">按车架号查询</h>
            <p class="first-introduce">车架号快速匹配</p>
            <p>锁定车型无烦恼/车架号极速匹配</p>
            <p>锁定车型快，精，准</p>
        </div>
        <div class="model-search-div">
            <h class="model-search">按车型查询</h>
            <p class="first-introduce">全面的车型库</p>
            <p>精细的配置参数和适配信息</p>
            <p>配件选择最佳入口</p>
        </div>
    </div>
</div>

    <#include "/epc-release/cloudepc/common/pagePart/bottom.ftl"/>
    <#include "/epc-release/cloudepc/common/pagePart/popup.ftl"/>
    <#include "/epc-release/cloudepc/common/resource/js.ftl"/>
    <#include "/epc-release/cloudepc/template/template.ftl"/>
</body>
<!-- END BODY -->

<script src="/epc-release/resources/home/home-1a1e8ab9eb.js"></script>

</html>

</#escape>
