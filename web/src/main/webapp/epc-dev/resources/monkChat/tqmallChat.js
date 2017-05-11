var WishChatMap = {
    bottom_url: '/monkChat/requirements?sellerId=chat_to_sys_id',
    bottom_url_btn_name: '需求单',
    guide_content: "尊敬的客户：欢迎使用淘汽档口聊天工具！首次使用，请在需求单列表找到联系人发起对话。",
    guide_url: '/wish'
};

var ShoppingChatMap = {

};

var TqmallChat = function(){
    //console.log('MONK_CHAT: '+MONK_DOMAIN);
    var GET_TOKEN_URL = MONK_DOMAIN + "/monk/rest/getToken?sysname=uc";

    //打开聊天窗口
    var openWindow = function (param) {
        var isOpenFun = function () {
            TimChat.focusChatWindow();
        };
        TimChat.initOpenChatPage(param, isOpenFun);
    };


    return{
        //清除缓存中的未读消息数
        clearMessageSum:function(){
            window.sessionStorage.removeItem('MSG_NUM');
        },
        // 获得所有未读消息数
        getMessageSum:function() {
            $.get(GET_TOKEN_URL, function (result) {
                var time = result.data['time'];
                var token = result.data['token'];
                //拿到用户未读消息总数
                var init_get_message_param = {
                    header_url: MONK_DOMAIN,
                    time: time,
                    token: token,
                    sys_id: CURRENT_SHOP_ID,
                    sys_name: "uc"
                };
                var chatFunc = function (sum, is_opened) {
                    console.log(sum);
                    $(".txt_num").text(sum);
                    $(".slideTxt em").text(sum);

                    if (sum != window.sessionStorage.getItem("MSG_NUM")) {
                        //设置缓存
                        window.sessionStorage.setItem("MSG_NUM", sum);
                        //聊天窗口没有打开，则消息提醒
                        if (!is_opened && sum != 0) {
                            TimChat.notify("消息提醒", "您有 " + sum + " 条未读消息");
                        }
                    }
                };

                TimChat.initGetNewMessage(init_get_message_param, chatFunc);
            });
        },
        //打开聊天框
        openChatWindow:function(to_sys_id,to_sys_name,top_url,bottom_url,bottom_url_btn_name,guide_url,guide_content){
                Ajax.get({
                    url: GET_TOKEN_URL,
                    async: false,
                    success: function (result) {
                        var _param = {
                            header_url: MONK_DOMAIN,
                            sys_id: CURRENT_SHOP_ID,
                            sys_name: "uc",
                            time: result.data.time,
                            token: result.data.token,
                            opened_url: '/monkChat/index'
                        };
                        if(to_sys_id != undefined){_param['to_sys_id'] = to_sys_id;}
                        if(to_sys_name != undefined){_param['to_sys_name'] = to_sys_name;}
                        if(top_url != undefined){_param['top_url'] = top_url;}
                        if(bottom_url != undefined){_param['bottom_url'] = bottom_url;}
                        if(bottom_url_btn_name != undefined){_param['bottom_url_btn_name'] = bottom_url_btn_name;}
                        if(guide_url != undefined){_param['guide_url'] = guide_url;}
                        if(guide_content != undefined){_param['guide_content'] = guide_content;}

                        openWindow(_param);
                    }
                });
        },
        //关闭聊天框
        closeChatWindow:function(){
            TimChat.closeChatWindow({sys_id: CURRENT_SHOP_ID,
                sys_name: "uc"});
        }

    }
}();