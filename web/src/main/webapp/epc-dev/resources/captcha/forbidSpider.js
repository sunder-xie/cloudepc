
$(function () {
    //添加乱码标示
    var messy_code_html = '<span title="00�kg"></span>';
    $("body").prepend(messy_code_html);
    //关闭窗口时
    $(window).unload(function () {
        $.ajax({
            url:"/epc/captcha/giveWorldClean",
            type: "POST",
            async: false
        });
    });

});