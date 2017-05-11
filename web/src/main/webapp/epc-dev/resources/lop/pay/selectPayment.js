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
    var orderId = $('#orderId').val();
    JqAjax.get('/wish/pay/setPayment', function(result){
        if(result.success){
            switch(payCode){
                case 'alipay':

                    //EPC.alertFuc(template('payConfirmTemplate', {data: orderId}));

                    window.location.href = '/wish/pay/aliPay?orderId='+orderId;
                    break;
                case 'lianpay':
                    window.location.href = '/wish/pay/selectUnionPay?orderId='+orderId;
                    break;
                default:
            }

        }else{
            TipUtil.tooltipFun(el, result.message);
        }
    }, {orderId: orderId, paymentId: payment.data('id')});
}
