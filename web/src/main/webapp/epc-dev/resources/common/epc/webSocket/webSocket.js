/**
 * Created by lyj on 16/7/14.
 */

//socket客户端
var _WS_SOCKET;
//event名称集
var _WS_EVENT_NAMES;

$(function () {
    //获取用户登录信息
    /*JqAjax.get('/webSocket/getCurrentUser', function (result) {
        var data = result.data;

        //如果用户已经登录, 则连接
        if (data.currentUser != null) {
            initServer(data.socketUrl, data.eventNames, data.currentUser.shopId);
            _WS_EVENT_NAMES = data.eventNames;
        }
    }, {});*/
});

//连接服务器
function initServer(socketUrl, eventNames, shopId) {
    if (_WS_SOCKET == undefined) {
        _WS_SOCKET = io.connect(socketUrl);
    } else {
        _WS_SOCKET = io.connect(socketUrl, {'force new connection': true});
    }

    _WS_SOCKET.emit(eventNames['EN_SHOP_ID'], {
        shopId: shopId
    });

    _WS_SOCKET.on('connect', function () {
        output('<span class="connect-msg">Client has connected to the server!</span>');
    });

    _WS_SOCKET.on('disconnect', function () {
        output('<span class="disconnect-msg">The client has disconnected! </span>');
    });

    _WS_SOCKET.on(eventNames['EN_SERVER_REPLY'], function (data) {
        output('<span class="username-msg">' + data.userName + ' : </span>'
            + data.message);
    });

    _WS_SOCKET.on(eventNames['EN_ORDER_STATE'], function (data) {
        output('<span class="username-msg">' + data.to + ' : </span>'
            + data.message);
    });
}

//之后有了退出功能, 退出时候关闭webSocket连接
function sendDisconnect() {
    _WS_SOCKET.disconnect();
}

//
function sendMessage() {
    var userName = $("#name").val()
    var message = $('#msg').val();
    $('#msg').val('');

    _WS_SOCKET.emit(_WS_EVENT_NAMES['EN_SERVER_REPLY'], {
        userName: userName,
        message: message
    });
}
//
function output(message) {
    var currentTime = "<span class='time' >" + new Date() + "</span>";
    var element = $("<div>" + currentTime + " " + message + "</div>");
    $('#console').prepend(element);
}