<style type="text/css">
    .messages {
        bottom: 80px;
        position: fixed;
        right: 0;
        z-index: 360;
        clear: both;
        width: 62px;
        height: 62px;
    }

    .messages .m-link {
        position: absolute;
        top: 0;
        right: 0;
        display: block;
        height: 62px;
        width: 62px;
        background: url('/img/im/message_ico.png') no-repeat 0 -85px;
        text-indent: -9999px;
    }

    .messages .m-txt {
        line-height: 62px;
        height: 62px;
        color: #333;
        font-size: 13px;
        padding: 0 15px;
        position: absolute;
        top: 0;
        right: -500px;
        background-color: #fff;
        white-space: nowrap;
        border: 1px solid #ff7a51;
    }

    .m-txt em {
        color: #f43232;
        margin: 0 5px;
    }

    .messages.on .m-link {
        background: url('/img/im/message_ico.png') no-repeat 0 -20px;
    }

    .messages .m-num {
        position: absolute;
        background: #f43232;
        color: #fff;
        text-align: center;
        top: 4px;
        left: -10px;
        z-index: 999;
        border-radius: 8px;
        padding: 1px 8px;
    }

    .messages.on .m-num {
        display: none;
    }

    .messages.on {
        width: 282px;
    }

    #to-top {
        right: 0;
        bottom: 15px;
        position: fixed;
        z-index: 360;
        clear: both;
        width: 62px;
        height: 62px;
    }

    #to-top a {
        background: url('/img/im/common_ico.png?t=20150715') -137px -59px;
    }

    #to-top a {
        display: block;
        height: 58px !important;
        width: 62px;
        background: url('/img/im/common_ico.png?t=20150715') -137px -59px;
        text-indent: -9999px;
    }

    .infor {
        position: fixed;
        /*bottom: 144px;*/
        bottom: 80px;
        right: 0;
        width: 62px;
        height: 62px;
        z-index: 999;
    }

    .info_btn {
        position: relative;
        width: 60px;
        height: 60px;
        border: 1px solid #ddd;
        background: #fff;
        display: block;
    }

    .info_btn i, .active .info_btn i, .triangle {
        background: url('/img/im/info_ico.png');
    }

    .info_btn i {
        width: 24px;
        height: 21px;
        display: block;
        background-position: left top;
        margin: 20px auto;
    }

    .active .info_btn {
        background: #ff7a51;
        border: 1px solid #ff7a51;
    }

    .active .info_btn i {
        background-position: left -31px;
    }

    .info_num {
        position: absolute;
        top: 2px;
        left: -10px;
        padding: 1px 8px;
        background: #f43232;
        border-radius: 8px;

    }

    .slideTxt {
        position: absolute;
        top: 0;
        right: -200px;
        height: 60px;
        line-height: 60px;
        padding: 0 15px;
        border: 1px solid #ff7a51;
        white-space: nowrap;
        background: #fff;
        font-size: 13px;
    }

    .txt_num {
        position: relative;
        color: #fff;
        z-index: 999;
    }

    .slideTxt em {
        color: #f43232;
        margin: 0 5px;
    }

    .triangle {
        position: absolute;
        left: 0;
        bottom: 0;
        width: 10px;
        height: 10px;
        display: inline-block;
        background-position: left -60px;
        z-index: 0;
    }

</style>

<div class="infor hidden">
    <a href="" class="slideTxt">您有<em>0</em>条信息</a>
    <a class="info_btn" href="javascript:">
        <i></i>

        <div class="info_num">
            <span class="txt_num">0</span>
        <#--<span class="triangle"></span>-->
        </div>
    </a>
</div>

<div class="messages hidden">
    <span id="fb1" class="m-txt"></span>
    <a class="m-link" href="javascript:"></a>

    <div class="m-num" id="fb2">0</div>
</div>

<div id="to-top">
    <a href="javascript:">回到顶部</a>
</div>

<#-- IM -->
<script src="${monkDomain}/monk-release/resources/chat/chat-min.js?v=${.now?string('yyyy-MM-dd')}"
        type="text/javascript"></script>


<script type="text/javascript">
    //monk项目host
    var MONK_DOMAIN = '${monkDomain}';

    $(window).scroll(function (e) {
        //若滚动条离顶部大于100元素
        if ($(window).scrollTop() > 0) {
            $("#to-top").show();//以1秒的间隔渐显id=gotop的元素
        } else {
            $("#to-top").hide();//以1秒的间隔渐隐id=gotop的元素
        }
    });

    //点击回到顶部的元素
    $("#to-top").click(function (e) {

        $('html,body').animate({scrollTop: '0px'}, 300);

    }).mouseover(function (e) {
        $(this).find("a").addClass("hover");
    }).mouseout(function (e) {
        $(this).find("a").removeClass("hover");
    });

    /*鼠标移入移出事件 防止多次触发*/
    function contains(parentNode, childNode) {
        if (parentNode.contains) {
            return parentNode != childNode && parentNode.contains(childNode);
        } else {
            return !!(parentNode.compareDocumentPosition(childNode) & 16);
        }
    }

    function checkHover(e, target) {
        if (getEvent(e).type == "mouseover") {
            return !contains(target, getEvent(e).relatedTarget || getEvent(e).fromElement) && !((getEvent(e).relatedTarget || getEvent(e).fromElement) === target);
        } else {
            return !contains(target, getEvent(e).relatedTarget || getEvent(e).toElement) && !((getEvent(e).relatedTarget || getEvent(e).toElement) === target);
        }
    }

    function getEvent(e) {
        return e || window.event;
    }

    /*鼠标移入移出事件 防止多次触发*/
    $(document).on("mouseover", ".messages", function (e) {
        if (checkHover(e, this)) {

            $("#fb1").html('<a href="/wish/myWishPage">您有<em>' + $("#fb2").text() + '</em>个报价未查看哦，点击前往</a>');

//            var num = Number($("#fb2").text());
//            if(num > 0){
//                $("#fb1").html('<a href="/wish/myWishPage">您有<em>' + num + '</em>个报价未查看哦，点击前往</a>');
//            }else{
//                $("#fb1").html('<a href="/wish/createWishPage">点击前往发起需求单</a>');
//            }
            $(this).addClass("on");
            $(this).find(".m-txt").animate({right: "60px"}, 100);
        }
    }).on("mouseout", ".messages", function (e) {
        if (checkHover(e, this)) {
            $(this).removeClass("on");
            $(this).find(".m-txt").animate({right: "-200px"}, 100);
        }
    }).on("mouseover", ".infor", function (e) {
        if (checkHover(e, this)) {
            $(this).addClass("active");
            $(".info_num").hide();
            $(this).find(".slideTxt").animate({right: "60px"}, 100);
        }
    }).on("mouseout", ".infor", function (e) {
        if (checkHover(e, this)) {
            $(this).removeClass("active");
            $(".info_num").show();
            $(this).find(".slideTxt").animate({right: "-200px"}, 100);
        }
    });

    //    $("#fb1").html('<a href="/wish/createWishPage">点击前往发起需求单</a>');
    //    $(".messages").addClass("on");
    //    $(".messages").find(".m-txt").animate({right: "60px"}, 100);


    var ybjNumIntervalId;

    $(function () {
        //已认证门店，IsVerifySuccessShop 变量在 header.ftl 中定义、赋值
        if (IsVerifySuccessShop === 1) {
            var infor_obj = $('.infor');
            /** ========== IM相关 ========== */
            infor_obj.removeClass('hidden');
            TqmallChat.getMessageSum();
            infor_obj.unbind().on("click", function () {
                TqmallChat.openChatWindow(undefined, undefined,undefined,
                        WishChatMap.bottom_url,WishChatMap.bottom_url_btn_name,WishChatMap.guide_url,WishChatMap.guide_content);
            });

        } else {
            /** ========== IM相关 ========== */
            $('.infor').addClass('hidden');

            //清除缓存中的消息数量
            TqmallChat.clearMessageSum();

        }

        //退出登录按钮点击
        $(".header-logout").unbind("click").click(function () {
            //关闭聊天窗口
            TqmallChat.closeChatWindow();

            //到登录页面
            window.location.href = '/user/logout';
            return false;
        })

    });

    function getYbjWishListNum() {
        Ajax.get({
            url: '/wish/getYbjWishListNum',
            success: function (result) {
                if (result.success) {
                    $("#fb2").text(result.data.ybjNumber);
                }
            }
        });
    }

</script>

<script src="/epc-release/resources/common/monk-chat-5be8848bb6.js"></script>
