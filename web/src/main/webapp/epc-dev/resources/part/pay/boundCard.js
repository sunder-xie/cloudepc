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

    $('#cardNo').blur(function(){
        var el = this;
        var cardNo = $.trim(el.value);
        if('' == cardNo){
            return;
        }
        var bankCode = $('.zyy-bank-item-selected').data('code');
        if(bankCode==undefined){
            return;
        }

        JqAjax.get('/part/pay/getBankCard', function(result){
            if(result.success){
                if(bankCode != result.data.bankCode){
                    TipUtil.tooltipFun(el, '该卡号开户行不属于该银行');
                }
            }else{
                TipUtil.tooltipFun(el, '您输入的卡号错误');
            }
        }, {cardNo: cardNo});

    });

});

/* 确认支付 */
function confirmPay(el){
    var card = $('.zyy-bank-item-selected');
    var checkFlag = true;
    if(card.length==0){
        TipUtil.tooltipFun(el, '请选择付款银行');
        checkFlag = false;
    }

    var input = $('#cardNo');
    var cardNo = $.trim(input.val());
    if('' == cardNo){
        TipUtil.showTip(input, '请输入银行卡号');
        checkFlag = false;
    }

    input = $('#idNo');
    var idNo = $.trim(input.val());
    if('' == idNo){
        TipUtil.showTip(input, '请输入银行预留身份证号');
        checkFlag = false;
    }

    input = $('#acctName');
    var acctName = $.trim(input.val());
    if('' == acctName){
        TipUtil.showTip(input, '请输入银行预留姓名');
        checkFlag = false;
    }

    if(!checkFlag){
        return;
    }

    var orderSn = $('#orderSn').val();

    var url = '/part/pay/unionPay?sn='+orderSn+'&cardNo='+cardNo+'&acctName='+acctName+'&idNo='+idNo;

    window.location.href = url;

}
