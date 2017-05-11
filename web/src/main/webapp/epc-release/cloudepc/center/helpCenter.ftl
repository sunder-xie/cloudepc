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

<#--公共css-->
    <#include "/epc-release/cloudepc/common/resource/css.ftl"/>
    <style type="text/css">
        .main{
            margin: 30px auto 40px;
            overflow: hidden;
        }
        .main ul{
            list-style-type: none;
        }
        .main li{
            line-height: 30px;
            text-align: left!important;
            -webkit-padding-start: 0;
        }
        .main hr{
            margin:12px 0;
        }
        .main-introduce{
            text-align: left;
            float: right;
            width: 85%;
            padding:0 35px;
            border:1px solid #eee;
        }
        .main-introduce span{
            line-height: 35px;
            margin-left: 22px;
            font-size: 13px;
        }
        .main-left {
            width: 15%;
            color: #333C4A;
            float: left;
        }

        .main-left-title{
            font-size: 15px;
            font-weight: bold;
        }

        .main-left-btn {
            margin-bottom: 10px;
            text-align: left;
            padding-left: 10px;
            cursor: pointer;
        }

        .main-left-btn:hover {
            color: #FE7A00;
            border-left:2px solid #FE7A00;
        }
        .main-left-btn.active{
            color: #FE7A00;
            border-left:2px solid #FE7A00;
        }

        .main-introduce-img{
            overflow: hidden;
            margin:40px 0;
        }
        .main-introduce-text{
            text-align: left!important;
            line-height: 30px;
            font-weight: lighter;
        }
        .main-introduce-div{
            text-align: left;
            margin: 20px -22px 45px -22px;
            font-size: 13px;
        }
        .main-guarantee,.main-return{
            float: right;
            width: 85%;
            padding:30px 58px;
            border:1px solid #eee;
            overflow: hidden;
            text-align: left;
        }
        .main-guarantee span,.main-return span{
            line-height: 29px;
            font-size: 13px;
            font-weight: lighter;
        }
        .main-color-blue{
            color: #02aadb;
            font-weight: bolder!important;
        }
        .main-return-div{
            text-align: left;
            margin: 30px 0;
            font-size: 13px;
        }
        .main-contact{
            float: right;
            width: 85%;
            border:1px solid #eee;
            overflow: hidden;
            text-align: left;
        }
        .main-contact p{
            line-height: 45px;
            height: 45px;
            font-size: 22px;
            font-weight: bolder;
            color: #c0b7b0;
            border-bottom:1px solid #eee;
        }
        .main-contact span{
            line-height: 35px;
            font-size: 13px;
            font-weight: lighter;
        }
        .main-contact-div,.main-contact-set{
            margin:30px 25px;
            text-align: left;
        }
        .main-contact-set span{
            font-size: 15px;
            font-family: serif;
            color: #333;
        }
        .contact-one{
            font-size: 15px;
            font-family: serif;
            color: black;
        }
        .contact-two{
            font-size: 30px;
            color: black;
        }
        .contact-three{
            font-size: xx-large;
            color: red;
        }
        .contact-four{
            font-family: 微软雅黑, sans-serif;
            font-size: 18px;
            color: #333;
        }
        .contact-five{
            font-size: 14px;
            font-family: serif;
            color: #333;
        }
    </style>
</head>

<body>
    <#include "/epc-release/cloudepc/common/pagePart/topWish.ftl">
    <div class="main w1190">
        <div class="main-left">
            <div style="width: 80%">
                <div class="text-align-left">
                    <span class="main-left-title">帮助中心</span>
                    <hr/>
                </div>
                <div class="main-left-btn <#if type==null || type==0>active</#if>" onclick="changeView(this,0)">汽配管家简介</div>
                <div class="main-left-btn <#if type==1>active</#if>" onclick="changeView(this,1)">质保政策</div>
                <div class="main-left-btn <#if type==2>active</#if>" onclick="changeView(this,2)">退换货政策</div>
                <div class="main-left-btn <#if type==3>active</#if>" onclick="changeView(this,3)">退换货流程</div>
                <div class="main-left-btn <#if type==4>active</#if>" onclick="changeView(this,4)">联系客服</div>
                <div class="main-left-btn <#if type==5>active</#if>" onclick="changeView(this,5)">联系我们</div>
            </div>
        </div>

        <#-- tab 1 -->
        <div class=" help-content <#if type!=null && type!=0>hidden</#if> main-introduce">
            <div class="main-introduce-img">
                <img src="${ossImage}/img/epc/help_sketch_new.png" onerror="javascript:this.src='/img/no-pic-big.png';this.onerror=null;">
            </div>
            <span class="main-introduce-text">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;汽配管家是一个为第三方汽配供应商以及汽修门店提供的汽车配件交易平台。
                采用的是汽修门店发布需求单，供应商回复报价的形式进行交易磋商，
                最终在汽配管家的保障下完成交易。</span>
            <div class="main-introduce-div">
                <span>汽配管家品类定义</span>
                <ul>
                    <li>正厂原厂：具备主机厂商授权资格的厂商，在有该汽车品牌授权的前提下，生产的汽车配件，此配件带有原厂logo；</li>
                    <li>品牌配套：具备正规生产资质和品牌标志（商标为R）的厂商生产的汽车配件；</li>
                    <li>正厂配套：根据原车厂配件标准生产的相同品质的配件，如鑫毅保险杠；</li>
                </ul>
            </div>

        </div>

        <#-- tab 2 -->
        <div class=" help-content <#if type!=1>hidden</#if> main-guarantee">
            <span>汽配管家引进第三方汽配供应商，旨为广大的汽修店提供更全，更优的产品和更好，更便捷的服务。为保证汽修供应商及汽修店的合理权益，由第三方汽配供应商提供以下质保政策，由双方共同遵守。</span>
            <div style="margin: 40px -22px 40px -22px;">
                <ol>
                    <li>在无任何违反保修条款规定的情况下，遇到零件不良时，双方应友好协商，以客户第一为原则给予客户进行免费更换或修理；</li>
                    <li>保修期起始日期：以系统显示确认收货日期开始计算；</li>
                    <li>除以下零件，剩余保修期均为一万或两万公里数（以先到为准）。注：双方在交易时针对订货商品或部分商品约定保修期时间的，以双方协商为准；</li>
                    <li>以下情况为不能质保的范围
                        <ul style="margin-left: -40px;">
                            <li>4.1. 非产品质量问题造成的损坏：如事故，被盗，洪水，冻坏等；</li>
                            <li>4.2. 因客户自身原因造成的商品损坏（如因不当使用，安装造成的商品损坏，弄脏导致不能二次销售，使用不恰当导致的损坏等）；</li>
                            <li>4.3. 由其他零件损坏连带导致损坏；</li>
                            <li>4.4. 任何非通过汽配管家成交的商品；</li>
                            <li>4.5. 任何已使用的商品，有质量问题的除外；</li>
                        </ul>
                    </li>
                </ol>
            </div>
            <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;淘汽档口作为第三方开放平台，将为汽修店提供优质的供应商，也为供应商提供更广阔的销售渠道。为维护第三方平台的交易体系，请双方共同遵守和维护质保体系，当双方协商发生争执，无法达成一致时，淘汽档口将作为第三方介入并协调。</span>
        </div>

        <#-- tab 3 -->
        <div class=" help-content <#if type!=2>hidden</#if> main-return">
            <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;汽配管家作为第三方开放平台，供第三方汽配供应商及汽修店进行汽车配件的交易，双方须共同遵守退换货政策，并根据退换货的流程予以办理相关退换货。</span><br/>
            <span>注：双方在交易时另有约定的（如订货产品或其他特殊商品），按约定执行，如有争执，请双方自行协商，此种情况下不适用于此退换货政策及流程。</span>
            <div class="main-return-div">
                <span class="main-color-blue">1. 七天无理由退换货</span><br/>
                <span>“无理由退换货” 是指汽修店在购买支持无理由退换货的商品后，自实际收货日期的次日7天内，商品包装好，未使用过，且相关附属的配件齐全，不影响二次销售的情况下，可以退换商品，需由汽修店承担商品发出与返回的运费。</span>
            </div>
            <div class="main-return-div">
                <span class="main-color-blue">2. 允许退换货情况描述</span><br/>
                <span>退换货分为换货，退货（仅退款，仅退货退款），退换货以及双方友好协商为前提，在汽修店为确认收货前，以下情况可以提交退换货的申请：</span>
                <ul style="margin-left: -30px">
                    <li>2.1 供应商问题， 供应商需要承担商品的来回运费
                        <ul style="margin-left: -25px;">
                            <li>* 商品外观质量问题；</li>
                            <li>* 发错货，由汽修店收到实际货物与订单货物不符；</li>
                            <li>* 第三方物流导致的商品损坏，丢失，缺失等物理问题。（注：物流/快递送货上门时，汽修店需先验证商品，确认商品及配件齐全后再签收，如有问题需在物流/快递送达时当场反馈）；</li>
                            <li>* 商品质量问题，汽修店须出具确认商品存在质量问题的有效证明；</li>
                            <li>* 未按双方约定时间发货，导致汽修店无法正常或继续使用；</li>
                            <li>* 其他因供应商问题引起的退换货；</li>
                        </ul>
                    </li>
                    <li>2.2 汽修店问题，汽修店需要承担商品的来回运费
                        <ul style="margin-left: -25px;">
                            <li>* 个人操作失误，七天无理由退换货；</li>
                            <li>* 汽修店填写车型或配件信息有误导致配件不匹配，对于订货商品，汽修店的错误导致的定错，一般情况下不予退货；</li>
                            <li>* 对于事故试装拍照，故障排查试装，超期修理等场景，以供应商和汽修店当初发货协商的约定为准，如无约定，则视同其他因汽修店问题引起的退换货；</li>
                            <li>* 其他因汽修点问题引起的退换货；</li>
                        </ul>
                    </li>
                </ul>
                <span>注：确认收货后（确认收货时间为发货完成后的15天，特殊商品可申请延长收货时时间），订单为交易完成状态，如商品有质量问题请申请售后，以保证政策为前提，为供应商协商换货事宜。</span>
            </div>
            <div class="main-return-div">
                <span class="main-color-blue">3. 不予退货情况</span>
                <ul style="margin-left: -30px">
                    <li>* 易损易碎货品，汽修店为同意钉箱或加固，发出破碎的产品；</li>
                    <li>* 汽车配件已装车使用过（经过加工，改装或喷涂等的配件）；</li>
                    <li>* 退回配件商品不完整或者有损坏的；</li>
                    <li>* 售前已告知不退货的产品，如冷泵，电器类商品等提供商特别申明商品；</li>
                    <li>* 超过质保索赔时间的产品；</li>
                    <li>* 商品售后因自然灾害造成损坏；</li>
                    <li>* 机油，添加剂，防冻液，玻璃水，清洁剂等汽车油品，化学品，深度养护类商品，如已拆封，不予退换货；</li>
                </ul>
            </div>
            <div  class="main-return-div">
                <span class="main-color-blue">4. 退换货流程注意事项：</span>
                <ul style="margin-left: -30px">
                    <li>4.1 如您进行礼包或套餐中的商品部分退换，将无法享受礼包或套装购买时的优惠。</li>
                    <li>4.2 如果该产品有附加的赠品，请将赠品一同退回，赠品需要保持收到赠品是的原质原样，如赠品不能退回，按照供应商原售价格在退款中扣除；</li>
                    <li>4.3 在您办理退换货时，根据您退货的具体商品积分原则，您在淘汽档口的账号积分将会相应的增减。（积分政策正在积极准备中）；</li>
                    <li>4.4 购物后赠送优惠，后发生退货，淘汽档口将回收该优惠券，如优惠券已被使用，则退款时扣除相同金额的现金；</li>
                    <li>4.5 退货时，如该商品涉及发票，需同时将发票退回，如发票不能退回，退款需扣除税金（具体税金由双方协商确定）；</li>
                </ul>
            </div>
        </div>

        <#-- tab 4 -->
        <div  class=" help-content <#if type!=3>hidden</#if> main-return" >
            <span>说明 : 由于目前平台的线上退，换货还在测试中，现以此版作为试运营版</span>
            <div class="main-return-div">
                <img src="${ossImage}/img/epc/help_return.png" style="width: 100%;">
            </div>
        </div>

        <#-- tab 5 -->
        <div class=" help-content <#if type!=4>hidden</#if> main-contact" style="padding:0 25px 130px 25px;">
            <p>联系客服</p>
            <div class="main-contact-div">
                <span class="contact-one">联系客服</span><br/>
                <span class="contact-two">服务热线</span><br/>
                <span class="contact-three">400-9937-288</span><br/>
                <span class="contact-four">服务时间：周一至周六9：00-18：00</span><br/>
                <span class="contact-five">温馨提示：全国地区的顾客在所属地拨打400-9937-288，固话只需支付本地市话费，手机只需支付本地通话相应套餐资费，在非所属地拨打时，将收取相应套餐漫游费。（漫游费请咨询相应的运营商）</span>
            </div>
        </div>

        <#-- tab 6 -->
        <div class=" help-content <#if type!=5>hidden</#if> main-contact" style="padding:0 25px 150px 25px;">
            <p>联系我们</p>
            <div class="main-contact-set">
                <span>联系地址：浙江省杭州市文一西路998号海创园18幢710</span><br/>
                <span>联系电话：400-9937-288</span><br/>
                <span>公司邮箱：tqmall@tqmall.com</span><br/>
                <span>公司官方微信公众号：taoqidangkou</span>
            </div>
        </div>
    </div>

    <#--公共资源-->
    <#include "/epc-release/cloudepc/common/pagePart/bottom.ftl">
    <#include "/epc-release/cloudepc/common/pagePart/popup.ftl"/>
    <#include "/epc-release/cloudepc/common/resource/js.ftl"/>

</body>

<script src="/epc-release/resources/center/helpCenter-fa0fe31861.js"></script>

</html>

</#escape>
