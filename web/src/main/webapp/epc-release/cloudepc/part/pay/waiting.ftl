<html>
<head>
    <meta charset="utf-8"/>
    <title>汽配管家</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta content="width=device-width, initial-scale=1.0" name="viewport"/>
    <meta http-equiv="Content-type" content="text/html; charset=utf-8">

    <#include "/epc-release/cloudepc/common/resource/css.ftl"/>

    <link rel="stylesheet" href="/epc-release/resources/part/pay/payment-5f2bda70c7.css">

    <style type="text/css">
        .content{
            border: 1px solid ;
            height: 380px;
        }
        .content-detail{
            margin: 0 auto;
            width: 340px;
            padding-top: 90px;
        }
        .btn-div button{
            border: 1px solid transparent;
            cursor: pointer;
            border-radius: 5px;
            width: 120px;
            color: #FFF;
            padding: 10px;
            margin-top: 10px;
            font-size: 14px;
        }
        .help-btn{
            background: #FE7A00;
            margin-right: 20px;
        }
        .show-detail-btn{
            background: #5db6e6;
        }
        .msg-div{
            padding: 15px;
        }
        .msg-div img{
            margin-right: 15px;
        }
        .important-msg{
            color: #333;
            font-size: 20px;
            padding-top: 5px;
        }

    </style>

</head>
<body>
<#include "/epc-release/cloudepc/common/pagePart/header.ftl">

    <div class="content-wrapper w1190">
        <div class="pc-header cl">
            <div class="fl">
                <div class="fl gj-logo" onclick="location.href='/home'"></div>
                <div class="fl purchase-tip">支付中</div>
            </div>
            <div class="fr purchase-step"></div>
        </div>
        <h4 class="ta-l purchase-title">提交订单</h4>
        <div class="content ">
            <div class="content-detail cl">
                <div class="msg-div cl">
                    <div class="fl">
                        <img src="/img/lop/pay_wait.gif" width="36" height="36">
                    </div>
                    <div class="fl ta-l">
                        <p class="important-msg">正在修改订单状态，请稍等...</p>
                    </div>
                </div>
                <div class="btn-div">
                    <button type="button" class="help-btn" onclick="location.href='/help/introduction?type=4'">付款遇到问题</button>
                    <button type="button" class="show-detail-btn" onclick="location.href='/buy/order/orderDetail?orderSn=${orderSn}'">查看订单详情</button>
                </div>
            </div>
        </div>

    </div>

<#include "/epc-release/cloudepc/common/pagePart/bottom.ftl"/>
<#include "/epc-release/cloudepc/common/pagePart/popup.ftl"/>
<#include "/epc-release/cloudepc/common/resource/js.ftl"/>

</body>
    <script type="text/javascript">

        var orderSn = '${orderSn}';
        var intervalId; //定时器id

        $(function(){
            intervalId = window.setInterval(checkOrderPayStatus, 5000);
        });

        function checkOrderPayStatus() {
            $.get('/part/pay/checkOrderPayStatus', {orderSn: orderSn}, function(result){
                if(result.success){
                    window.location.href = '/part/pay/success?orderSn='+orderSn;
                }
            });
        }

    </script>
</html>
