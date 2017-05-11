
$(function () {
    var overTime = Number($("#overNum").val());
    if(overTime == -2){
        // go home
        window.location.href = "/home";
        return false
    }
    if(overTime > 0){
        //倒计时
        var nowTime = new Date().getTime();
        var ttlTime = Number($("#ttlTime").val());
        var closeDate = new Date(nowTime+ttlTime*1000);
        $('#defaultCountdown').countdown({
            until: closeDate,
            onExpiry:function(){
                window.history.go(-1);
                window.location.href=window.location.href;
            }
        });
    }

    //更改浏览器的url
    var refuseFtl = $("#refuseFtl").val();
    var true_url = 'http://' + window.location.host+refuseFtl;

    if( window.location.href != true_url) {
        var stateObj = { foo: new Date().getTime() };
        history.pushState(stateObj, "", refuseFtl);
    }

});