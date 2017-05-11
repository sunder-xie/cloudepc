<style type="text/css">

    /* 管家急呼入口相关 */
    .avid-call-entry{
        background: url("/img/lop/avid_call_entry.png") no-repeat;
        background-size: 100%;
        height: 70px;
        width: 300px;
        position: fixed;
        top: 60px;
        right: 2px;
        cursor: pointer;
        z-index: 999;
        padding-left: 70px;
        padding-top: 14px;
    }
    .avid-call-entry > div{
        text-align: left;
    }
    .entry-msg-1{
        font-size: 16px;
        font-weight: bold;
        color: #00a9dd;
    }
    .entry-msg-2{
        font-size: 12px;
        color: #ccc;
        font-family: "Arial";
        margin-left: 2px;
    }
    .entry-msg-3{
        font-size: 14px;
        color: #999;
    }

    .close-avid-introduce-btn{
        background: url("/img/lop/introduce-up-grey.png") no-repeat;
        width: 16px;
        height: 16px;
        position: fixed;
        right: 20px;
        top: 87px;
        z-index: 1010;
    }
    .close-avid-introduce-btn:hover{
        background: url("/img/lop/introduce-up.png") no-repeat;
    }
    .open-avid-introduce-btn{
        background: url("/img/lop/introduce-down-grey.png") no-repeat;
    }
    .open-avid-introduce-btn:hover{
        background: url("/img/lop/introduce-down.png") no-repeat;
    }

    .avid-call-introduce{
        /*background: url("/img/lop/introduce-background-img.png") no-repeat;*/
        /*background-size: 100%;*/
        height: 205px;
        width: 290px;
        position: fixed;
        top: 128px;
        right: 6px;
        z-index: 999;
        text-align: left;
        /*padding: 34px 2px 2px 22px;*/
        font-size: 13px;
    }
    .avid-call-introduce img{
        position: absolute;
    }
    .introduce-info-div{
        position: absolute;
        padding: 34px 2px 2px 22px;
    }

    .introduce-info-div div{
        color: #666;
        text-align: left;
    }
    .phone-style{
        color: #ff7900;
        font-size: 13px;
    }

    .circle-style {
        width: 7px;
        height: 7px;
        border-radius: 7px;
        background-color: #666;
    }
    .slogan-div{
        margin-left: 105px;

    }
    .slogan-msg-1{
        color: #fff !important;
        font-size: 16px;
    }
    .slogan-msg-2{
        color: #91e6ff !important;
    }
    .service-time-div{
        margin-top: 28px;
        margin-bottom: 10px;
    }

</style>

<div >
    <div class="avid-call-entry" onclick="CityUtil.checkAvidCall()">
        <div>
            <span class="entry-msg-1">管家急呼</span>
            <span class="entry-msg-2">Online Service</span>
        </div>
        <div class="entry-msg-3">
            询报价不想填单，请找我
        </div>
    </div>
    <i class="close-avid-introduce-btn"></i>
    <div class="avid-call-introduce" >
        <img src="/img/lop/introduce-background-img.png" onload="$('.introduce-info-div').removeClass('hidden')">
        <div class="introduce-info-div hidden">
            <div class="slogan-div">
                <div class="slogan-msg-1">
                    找配件，点急呼
                </div>
                <div class="slogan-msg-2">
                    三分钟内管家与您联系
                </div>
            </div>
            <div class="service-time-div">
                服务时间：周一到周五（法定节假日除外）
                <br>
                上午 09:00 - 12:00
                <br>
                下午 13:30 - 18:00
            </div>
            <div >
                管家电话：<span class="phone-style">400-9937-288 转 3</span>
            </div>
        </div>
    </div>
</div>
