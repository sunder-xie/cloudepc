function nextStep(e){var t=$(".payment-selected"),a=t.data("code");if(void 0==a)return void TipUtil.tooltipFun(e,"请选择支付方式");var n=$("#orderSn").val();JqAjax.get("/part/pay/setPayment",function(t){if(t.success)switch(a){case"alipay":window.location.href="/part/pay/aliPay?orderSn="+n;break;case"lianpay":window.location.href="/part/pay/selectUnionPay?orderSn="+n}else TipUtil.tooltipFun(e,t.message)},{orderSn:n,payId:t.data("id")})}$(function(){var e=$(".payment-selected");0==e.length&&$(".payment:first").addClass("payment-selected"),$(".payment").click(function(){$(".payment-selected").removeClass("payment-selected"),$(this).addClass("payment-selected")}),$(".next-step").click(function(){nextStep(this)})});