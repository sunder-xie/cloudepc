/**
 * Created by huangzhangting on 16/8/7.
 */
$(function(){
    $('.zyy-bank-item').click(function(){
        $('.zyy-bank-item').removeClass('zyy-bank-item-selected');
        $(this).addClass('zyy-bank-item-selected');
    });
    $('.confirm-pay').click(function(){
        confirmPay(this);
    });
});

/* 确认支付 */
function confirmPay(el){
    var card = $('.zyy-bank-item-selected');
    if(card.length==0){
        TipUtil.tooltipFun(el, '请选择银行卡');
        return;
    }

    var orderSn = $('#orderSn').val();
    var agreeNo = card.find(".agreeNo").val();
    var cardNo = card.find(".cardNo").val();
    var acctName = card.find(".acctName").val();
    var idNo = card.find(".idNo").val();

    var url = '/part/pay/unionPay?sn='+orderSn+'&noAgree='+agreeNo+'&cardNo='+cardNo+'&acctName='+acctName+'&idNo='+idNo;

    window.location.href = url;

}
