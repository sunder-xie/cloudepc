<#escape x as x?html>
<!DOCTYPE html>

<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en">
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
    <meta charset="utf-8"/>
    <title>汽配管家</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta content="width=device-width, initial-scale=1.0" name="viewport"/>
    <meta http-equiv="Content-type" content="text/html; charset=utf-8">

    <#include "/epc-release/cloudepc/common/resource/css.ftl"/>

    <style type="text/css">
        #waitWish ul,#finishedWish ul,#offerInfo ul{
            list-style-type: none;
            -webkit-padding-start: 0;
        }
        #finishedWish p{
            margin: 0 0 0px!important;
        }
        .table-width{
            width: 100%;
        }
        .wish-color-blue{
            color:#02aadb;
        }


        /*.wish-left{
            width: 19%;
            height: 500px;
            float: left;
        }
        .wish-left ul{
            margin-top: 12px;
            list-style-type:none;
            margin-top: 40px;
        }
        .wish-left li{
            margin-left: -30px;
            text-align: left;
            height:35px;
            line-height: 35px;
            font-size: 13px;
        }
        .wish-left span{
            font-size: 19px;
            font-weight: bold;
            float: left;
            padding-top: 38px;
        }
        .wish-left-title{
            width: 60%;
            height: 80px;
            float: right;
            color: black;
            margin-right: 15px;
            border-bottom: 1px solid #d6e5ec;
        }
        .wish-left-content{
            width: 100%;
            float: right;
            height: 100px;
        }*/
        .wish-right{
            width: 88%;
            float: left;
        }
        .wish-right-top{
            width: 100%;
            height: 42px;
            border-bottom: 1px solid #DDDDDD;
            background-color: #ffffff;
        }

        .wish-right-top-color{
            border-bottom:2px solid #ff7800;
        }

        .wish-right-top ul{
            list-style-type:none;
            -webkit-padding-start: 0px;
            margin-bottom: 0;
        }
        .wish-right-top li{
            width: 110px;
            height: 35px;
            font-size: 14px;
            color: #797979;
            text-align: center;
            margin-top: 7px;
            border-bottom: 1px solid #DDDDDD;
            float: left;
            cursor: pointer;
            list-style: none;
            font-weight: bold;
        }
        .wish-right-top li.current{
            border-bottom: 2px solid #FE7A00;;
            color: #000000;
        }
        .wish-right-top li:hover{
            border-bottom: 2px solid #FE7A00;;
            opacity: 0.8;
        }

        .wish-right-top-right{
            border-bottom: 1px solid #d6e5ec;
            width:60%;
            height: 80px;
            float: right;
            text-align: right;
        }
        /*.wish-search-input{*/
            /*height: 31px;*/
            /*width:250px;*/
            /*margin-top: 30px;*/
            /*padding-left: 5px;*/
        /*}*/
        .wish-search-btn{
            height: 30px;
            margin-left: -49px;
        }
        .wish-right-content{
            overflow: hidden;
        }
        .wish-right-content-form{
            border: 1px solid #bfcfd9;
            border-radius: 4px;
            width: 100%;
            margin-top: 25px;
            /*float: right;*/
        }
        .wish-right-img{
            position: absolute;
            right: 63px;

        }
        .form-one{
            width:100%;
            border-bottom:1px solid #bfcfd9;
            position: relative;
        }
        .form-one span{
            margin-right: 35px;
        }
        .form-one-div{
            background-color: #f1f1f1;
            border-bottom: 1px solid #bfcfd9;
            padding: 10px 15px;
            text-align: left;
        }

        .cancel-wish-btn, .recreate-wish-btn{
            border: 1px solid;
            padding: 3px 8px;
            border-radius: 2px;
            cursor: pointer;
        }
        .wish-table{
            width: 100%;
            margin: 10px 0;
        }
        .wish-table td:nth-child(1){width: 35%;}
        .wish-table td:nth-child(2){width: 39%;}
        .wish-table td:nth-child(3){width: 26%;}

        #finishedWish td:nth-child(1){width: 33%;}
        #finishedWish td:nth-child(2){width: 30%;}
        #finishedWish td:nth-child(3){width: 37%;}

        .wish-table td{
            text-align: left;
            padding: 3px;
            overflow:hidden;
            text-overflow:ellipsis;
            white-space:initial;
            display: inline-block !important;
        }
        .form-two{
            height:34px;
            width:100%;
            background-color: #E7F7FF;
            border-bottom:1px solid #bfcfd9;
        }
        .form-two b{
            line-height: 34px;
        }
        .form-three{
            width:100%;
            overflow: hidden;
        }
        .form-three-thread{
            height: 30px;
            border-bottom: 1px solid #bfcfd9;
            background-color: #f1f1f1;
        }
        .form-three-image{
            height: 70px;
            width: 70px;
            border: 1px solid #d6e5ec;
        }
        .form-three-image2{
            height: 40px;
            width: 45px;
            border: 1px solid #d6e5ec;
        }
        .form-four{
            height: 34px;
            width: 100%;
            line-height: 34px;
            background-color: #E7F7FF;
            border-bottom: 1px solid #bfcfd9;
        }
        .competitor-info{
            width: 100%;
        }
        .competitor-info-status{
            position: relative;
            text-align: center;
            overflow: hidden;
            -webkit-padding-start: 0px;
            margin-bottom: 0px;
        }
        .competitor-info-status li{
            position: relative;
            display: inline-block;
            vertical-align: bottom;
            padding:15px 30px;
        }
        .competitor-info-wait{
            background-color: #f1f1f1;
            padding: 5px 18px;
            border-radius: 5px;
        }
        .competitor-backColor{
            background-color: #ff7800;
            color: white;
            cursor: pointer;
        }
        .competitor-ul{
            background-color: rgba(0, 0, 0, 0.5);
        }
        .offer-li{
            background-color: white;
            z-index: 100;
        }
        .offer-info{
            /*display: none;*/
            /*padding:0px 15px;*/
            /*width:100%;*/
        }
        .offer-display-block{
            display: block!important;
        }
        .offer-display-none{
            display: none!important;
        }
        .goods-list dt {
            height: 40px;
        }
        .goods-list dd {
            margin-bottom: 20px;
            border: 1px solid #c9c9c9;
            padding: 0px 5px;
        }
        .goods-item {
            line-height: 35px;
            overflow: hidden;
            margin-bottom: 0px;
            -webkit-padding-start: 0px;
        }
        .dt-line-height{
            line-height: 40px;
        }
        .goods-item li {
            float: left;
            list-style-type: none;
            text-overflow: ellipsis;
            overflow: hidden;
        }
        .detail-foot {
            overflow: hidden;
            padding-left: 19px;
        }
        .foot-memo{
            margin-bottom: 10px;
            padding-left:19px;
            width: 100%;
            text-align: left;
        }
        .detail-foot .d-left {
            height: 30px;
            line-height: 30px;
            float: left;
        }
        .detail-foot .d-left strong,.detail-foot span {
            font-size: 18px;
            font-weight: 700;
            color: #ea5802;
        }
        .detail-foot .ok-btn {
            width: 120px;
            height: 30px;
            line-height: 30px;
            font-size: 15px;
            font-weight: lighter;
            text-align: center;
            color: #fff;
            background-color: #02aadb;
            border: 0;
            -webkit-border-radius: 6px;
            -moz-border-radius: 6px;
            border-radius: 4px;
            float: right;
        }
        .competitor-model-close{
            position: absolute;
            font-size: 12px;
            top: 6px;
            right: 0;
            height: 18px;
            width: 18px;
            text-align: center;
            padding: 0;
            border-radius: 0 !important;
            z-index: 120;
        }
        .btn > i{
            margin-top: 1px;
        }
        .competitor-model-close-a {
            color: #FFF;
            background-color: #F50202;
            margin-left: 12px;
            margin-top: -6px;
        }
        .margin-bottom-20{
            margin: 20px 0px;
        }
        .form-one-div-p{
            float: left;
            line-height: 38px;
            margin-right: 35px;
        }
        .margin-left-5{
            margin:8px 0px 8px 15px;
        }
        .form-two {
            height: 34px;
            width: 100%;
            background-color: #E7F7FF;
            border-bottom: 1px solid #bfcfd9;
        }
        .form-two-left{
            float: left;
            width:50%;
            height:100%;
            line-height: 34px;
            /*border-top:1px solid #bfcfd9;*/
            border-right:1px solid #bfcfd9;
        }
        .form-two-right{
            float: right;
            width: 50%;
            height: 100%;
            line-height: 34px;
            /*border-top:1px solid #bfcfd9;*/
        }
        .color-red{
            background-color: #ff7800;
            color: white;
        }
        .info-ico{
            width:16px;
            height:15px;
            background: url("/img/info_ico.png") no-repeat left -74px;
            display: inline-block;
            margin-left: 5px;
            cursor: pointer;
        }

        /* 需求单状态标记 */
        .mark{
            position: absolute;
            right: -1px;
            top: -2px;
            display: block;
            width: 73px;
            height: 73px;
            z-index: 9;
        }
        /* 已报价 */
        .mark-ybj{
            background: url(/img/lop/ybj.png) center no-repeat;
        }
        /* 待报价 */
        .mark-dbj{
            background: url(/img/lop/dbj.png) center no-repeat;
        }
        /* 已取消 */
        .mark-yqx{
            background: url(/img/lop/yqx.png) center no-repeat;
        }
        /* 确认报价 */
        .mark-qrbj{
            background: url(/img/lop/yxd.png) center no-repeat;
        }

        /* 表单td修正 */
        .td-first-fix{
            padding-left: 10px;
        }
        .td-last-fix{
            padding-right: 10px;
        }

        /* im */
        .im-icon{
            width: 16px;
            height: 14px;
            background: url(/img/lop/im_icon.png) no-repeat;
            display: inline-block;
            margin-left: 5px;
            cursor: pointer;
        }

        /*lyj*/
        .content {
            padding: 30px 0;
            overflow: hidden;
            margin: 0 auto;
        }

        .wish-show-all {
            width: 70px;
            height: 30px;
            margin-top: 5px;
            margin-left: 10px;
            line-height: 28px;
            border: 1px solid #ededed;
            background: #F2F2F2;
            float: right;
            cursor: pointer;
        }

        .wish-search-button {
            height: 30px;
            margin-top: 5px;
            line-height: 28px;
            border-top: 1px solid #ededed;
            border-right: 1px solid #ededed;
            border-bottom: 1px solid #ededed;
            background: #F2F2F2;
            float: right;
            cursor: pointer;
            padding-left: 10px;
            padding-right: 10px;
        }

        .wish-search-div {
            float: right;
        }

        .wish-search-input {
            width: 200px;
            height: 30px;
            padding: 10px;
            margin-top: 5px;
        }

        /* 报价状态 */
        .competitor-info-status .show {
            display: block;
        }
        .competitor-info-status .mask {
            position: absolute;
            left: 0;
            top: 0;
            z-index: 9;
            width: 100%;
            height: 100%;
            background: #000;
            opacity: 0.6;
            filter: opacity(60);
            display: none;
        }
        .competitor-info-status .cur {
            margin-bottom: -1px;
            border-bottom: 1px solid #fff;
            z-index: 99;
            background-color: #fff;
        }
        .operation-div{
            width: 110px;
            margin: 0 auto;
            padding-top: 12px;
        }
        .create-wish-btn{
            width: 100%;
            height: 100%;
            color: #FFFFFF;
            background: #FE7A00;
            border-radius: 5px;
            cursor: pointer;
            padding: 6px 10px;
        }
        /* 管家急呼打标 */
        .avid-call-tag{
            background: url("/img/lop/avid_call_tag.png") no-repeat;
            height: 28px;
            width: 104px;
            position: absolute;
            top: 4px;
            right: 80px;
        }

    </style>
</head>
<body>
    <input type="hidden" id="currShopId" value="${CURRENT_USER.shopId}">
    <input type="hidden" id="currShopId" value="${CURRENT_USER.shopId}">

    <#include "/epc-release/cloudepc/common/pagePart/topWish.ftl">
    <div class="content w1190">

        <#include "/epc-release/cloudepc/lop/wish/left.ftl">
        <div class="wish-right">
            <div class="wish-right-top">
                <ul>
                    <li class="current" onclick="changeView(1, this)">报价中</li>
                    <li onclick="changeView(2, this)">已完成</li>
                </ul>

                <div class="wish-show-all">
                    显示全部
                </div>

                <div class="wish-search-button">
                    搜索
                </div>

                <div class="wish-search-div">
                    <input class="wish-search-input" placeholder="输入车型、车品牌">
                </div>
            </div>
            <div id="wishListContent" class="wish-right-content">
                <div style="padding-top: 120px;padding-bottom: 230px;"></div>
            </div>
            <!--分页组件-->
            <div id="pagination" class="qxy_page"></div>
        </div>
    </div>

    <#include "/epc-release/cloudepc/common/pagePart/bottom.ftl">
    <#include "/epc-release/cloudepc/common/pagePart/popup.ftl"/>
    <#include "/epc-release/cloudepc/common/resource/js.ftl"/>
    <#include "/epc-release/cloudepc/template/lopTemplate.ftl"/>
    <#include "/epc-release/cloudepc/lop/wish/cancelWish.ftl">

</body>
<!-- END BODY -->
<script src="/epc-release/resources/lop/showWish-e15b431c85.js"></script>
</html>

</#escape>
