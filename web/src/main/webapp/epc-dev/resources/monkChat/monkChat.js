/**
 * monkChat js
 * Created by lyj on 16/8/11.
 */

var MONK_CHAT = function () {

    //console.log('MONK_CHAT: '+MONK_DOMAIN);

    var GET_TOKEN_URL = MONK_DOMAIN + "/monk/rest/getToken?sysname=uc";

    var PARAM = {
        header_url: MONK_DOMAIN,
        sys_id: CURRENT_SHOP_ID,
        sys_name: "uc",
        bottom_url: '/monkChat/requirements?sellerId=chat_to_sys_id',
        bottom_url_btn_name: '需求单',
        guide_content: "尊敬的客户：欢迎使用淘汽档口聊天工具！首次使用，请在需求单列表找到联系人发起对话。",
        guide_url: '/wish'
    };

    String.prototype.format = function () {
        for (var i = 0, val = this, len = arguments.length; i < len; i++)
            val = val.replace(new RegExp('\\{' + i + '\\}', 'gm'), arguments[i]);
        return val;
    };

    //初始化
    var init = function () {
        $('.im-icon').unbind().on('click', function () {
            var el = $(this);
            var toSysId = el.data("seller-id");
            var toSysName = el.data("seller-name");
            var sn = el.data('sn');
            var vin = el.data('vin');
            var carType = el.data('car');

            var top_url = "/monkChat/requestList?orderSn={0}&vin={1}&carType={2}".format(sn, vin, carType);

            Ajax.get({
                url: GET_TOKEN_URL,
                async: false,
                success: function (result) {
                    var time = result.data['time'];
                    var token = result.data['token'];

                    var _param = {
                        time: time,
                        token: token,
                        opened_url: '/monkChat/index',
                        to_sys_id: toSysId,
                        to_sys_name: "lop",
                        top_url: top_url
                    };

                    openWindow($.extend({}, PARAM, _param));
                }
            });

        });
    };

    //打开聊天窗口
    var openWindow = function (param) {
        var isOpenFun = function () {
            TimChat.focusChatWindow();
        };
        TimChat.initOpenChatPage(param, isOpenFun);
    };

    return {
        init: function () {
            init();
        },
        bind: function () {
            $.get(GET_TOKEN_URL, function (result) {
                var time = result.data['time'];
                var token = result.data['token'];

                //拿到用户未读消息总数
                var param = {
                    header_url: MONK_DOMAIN,
                    time: time,
                    token: token,
                    sys_name: 'uc',
                    sys_id: CURRENT_SHOP_ID
                };

                var chatFunc = function (sum, is_opened) {
                    console.log(sum);

                    $(".txt_num").text(sum);
                    $(".slideTxt em").text(sum);

                    if (sum != window.sessionStorage.getItem("MSG_NUM")) {
                        //设置缓存
                        window.sessionStorage.setItem("MSG_NUM", sum);
                        //聊天窗口没有打开，则消息提醒
                        if (!is_opened && sum!=0) {
                            TimChat.notify("消息提醒", "您有 " + sum + " 条未读消息");
                        }
                    }

                };

                //console.log(param);
                TimChat.initGetNewMessage(param, chatFunc);
            });

            $('.infor').unbind().on("click", function () {
                Ajax.get({
                    url: GET_TOKEN_URL,
                    async: false,
                    success: function (result) {
                        var _param = {
                            time: result.data.time,
                            token: result.data.token,
                            opened_url: '/monkChat/index'
                        };

                        openWindow($.extend({}, PARAM, _param));
                    }
                });

            })
        }
    }
}();

