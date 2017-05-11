/**
 * Created by huangzhangting on 16/8/6.
 */

$(function(){

    var payment = $('.payment-selected');
    if(payment.length == 0){
        $('.payment:first').addClass('payment-selected');
    }

    $('.payment').click(function(){
        $('.payment-selected').removeClass('payment-selected');
        $(this).addClass('payment-selected');
    });

    $('.next-step').click(function(){
        nextStep(this);
    });

});

//下一步
function nextStep(el){
    var payment = $('.payment-selected');
    var payCode = payment.data('code');

    if(payCode==undefined){
        TipUtil.tooltipFun(el, '请选择支付方式');
        return;
    }

    var orderSn = $('#orderSn').val();

    JqAjax.get('/part/pay/setPayment', function(result){
        if(result.success){
            switch(payCode){
                case 'alipay':
                    window.location.href = '/part/pay/aliPay?orderSn='+orderSn;
                    break;
                case 'lianpay':
                    window.location.href = '/part/pay/selectUnionPay?orderSn='+orderSn;
                    break;
                default:
            }

        }else{
            TipUtil.tooltipFun(el, result.message);
        }
    }, {orderSn: orderSn, payId: payment.data('id')});
}
