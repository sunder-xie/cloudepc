<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8"/>
    <title></title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta content="width=device-width, initial-scale=1.0" name="viewport"/>
    <meta http-equiv="Content-type" content="text/html; charset=utf-8">
    <meta content="" name="description"/>
    <meta content="" name="author"/>

    <style type="text/css">
        * {
            margin: 0 auto;
            padding: 0;
        }

        .content {
            padding: 15px 20px 20px 20px;
            background: #f7faff;
        }

        .list {
            list-style: none;
            margin-bottom: 50px;
        }

        .list li {
            font-size: 14px;
            line-height: 28px;
        }

        .list li span {
            color: #808183;
        }

        .btn_send {
            width: 143px;
            height: 28px;
            border: 1px solid #cdced2;
            line-height: 28px;
            text-align: center;
            font-size: 14px;
            border-radius: 4px;
            cursor: pointer;
            background: #f7faff;
        }

        .btn_send:hover {
            background: #f2f5f7;
        }
    </style>

</head>
<body>
<div class="content">
    <ul class="list">
        <li>需求单号：<span>${orderSn}</span></li>
        <li>车架号：<span>${vin}</span></li>
        <li>车型信息：<span>${carType}</span></li>
    </ul>
    <div class="btn_send">发送需求信息</div>
</div>
</body>

<script src="/static/js/jquery.min.js" type="text/javascript"></script>
<script src="${monkDomain}/monk-release/resources/chat/chat-iframe.js?v=${.now?string('yyyy-MM-dd')}" type="text/javascript"></script>

<script>
    //点击发送需求单信息
    $(document).on("click", ".btn_send", function () {
        var txtArray = [];
        $(".list li").each(function () {
            txtArray.push($(this).text());
        });
        TimIframeUtil.sendMessageToChatAreaAndSend(txtArray);
        TimIframeUtil.closeTopIframe();
    });
</script>
</html>