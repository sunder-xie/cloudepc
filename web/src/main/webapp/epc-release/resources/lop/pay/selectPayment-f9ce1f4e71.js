function nextStep(e){var t=$(".payment-selected"),a=t.data("code");if(void 0==a)return void TipUtil.tooltipFun(e,"请选择支付方式");var n=$("#orderId").val();JqAjax.get("/wish/pay/setPayment",function(t){if(t.success)switch(a){case"alipay":window.location.href="/wish/pay/aliPay?orderId="+n;break;case"lianpay":window.location.href="/wish/pay/selectUnionPay?orderId="+n}else TipUtil.tooltipFun(e,t.message)},{orderId:n,paymentId:t.data("id")})}$(function(){var e=$(".payment-selected");0==e.length&&$(".payment:first").addClass("payment-selected"),$(".payment").click(function(){$(".payment-selected").removeClass("payment-selected"),$(this).addClass("payment-selected")}),$(".next-step").click(function(){nextStep(this)})});