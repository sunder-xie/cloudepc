/**
 Core script to handle the entire theme and core functions
 **/
var EPC = function () {

    var imgLoadingPath = '../../../../../img/loading/';


    var alertContent = function (content, heard_html, width) {
        var hearder = $("#alert_model .modal-header");
        if (heard_html == undefined) {
            hearder.addClass('hidden');
        } else {
            hearder.html(heard_html);
            hearder.removeClass('hidden');
        }
        $("#alert_model .modal-body").html(content);

        if (width == undefined) {
            width = "500";
        }
        $('#alert_model').data("width", width);

        $('#alert_model').modal('show');


        //alertHeightChange();
        return false;
    };
    //高度改变，自适应
    var alertHeightChange = function () {
        //var container = $('body > .container');
        //var alert_model = $('#alert_model');
        //var document_height = document.documentElement.clientHeight;
        //var alert_height = alert_model[0].clientHeight;
        //
        //console.log("document_height:"+document_height+" alert_height:"+alert_height);
        //if(document_height>alert_height){
        //    alert_model.css('margin-top',"-"+Number(alert_height)/2+"px");
        //    alert_model.css('top',"50%");
        //
        //}else{
        //    alert_model.css('margin-top',"0px");
        //    alert_model.css('top',"0px");
        //    //
        //    //alert_model.data('top',"0px");
        //    //alert_model.data('margin-top',"0px");
        //}

    };

    var confirmContent = function (content, successFunction) {
        $('#make_sure_model .modal-body').html(content);
        $('#make_sure_model').modal('show');

        $('#sure_model_btn').unbind().click(function () {
            successFunction();
        });

        alertHeightChange();

        return false;
    };

    var confirmNewContent = function (content, successFunction) {
        $('#make_sure_model_new .modal-body').html(content);
        $('#make_sure_model_new').modal('show');

        $('#sure_model_btn_new').unbind().click(function () {
            successFunction();
        });

        alertHeightChange();

        return false;
    };

    var postFunc = function (url, successFunc, contents, async_bl) {
        //默认异步
        if (async_bl == undefined) {
            async_bl = true;
        }

        $.ajax({
            type: "POST",
            url: url,
            data: contents,
            async: async_bl,
            beforeSend: function (request) {
                //设置hearder头
                var timestamp = new Date().getTime();
                request.setRequestHeader("timestamp", timestamp);
            },
            success: function (result) {
                successFunc(result)
            }
        });
    };
    var getFunc = function (url, successFunc, contents, async_bl) {
        //默认异步
        if (async_bl == undefined) {
            async_bl = true;
        }

        $.ajax({
            type: "GET",
            url: url,
            data: contents,
            async: async_bl,
            beforeSend: function (request) {
                //设置hearder头
                var timestamp = new Date().getTime();
                request.setRequestHeader("timestamp", timestamp);
            },
            success: function (result) {
                successFunc(result)
            }
        });
    };

    // Handles the go to top button at the footer
    var handleGoTop = function () {
        var offset = 100;
        var duration = 500;

        if (navigator.userAgent.match(/iPhone|iPad|iPod/i)) { // ios supported
            $(window).bind("touchend touchcancel touchleave", function (e) {
                if ($(this).scrollTop() > offset) {
                    $('.scroll-to-top').fadeIn(duration);
                } else {
                    $('.scroll-to-top').fadeOut(duration);
                }
            });
        } else { // general
            $(window).scroll(function () {
                if ($(this).scrollTop() > offset) {
                    $('.scroll-to-top').fadeIn(duration);
                } else {
                    $('.scroll-to-top').fadeOut(duration);
                }
            });
        }

        $('.scroll-to-top').click(function (e) {
            e.preventDefault();
            $('html, body').animate({
                scrollTop: 0
            }, duration);
            return false;
        });
    };
    //* END:CORE HANDLERS *//


    return {

        init: function () {
            handleGoTop();
            $(window).resize(function () {
                alertHeightChange();
                return false;
            });

        },
        alertFuc: function (content, heard_html, width) {
            //提示框
            alertContent(content, heard_html, width);
        },
        alertFucWithTime: function (time, content, heard_html, width) {
            //提示框
            alertContent(content, heard_html, width);
            //关闭，time：ms
            window.setTimeout(function () {
                $('#alert_model').modal('hide');
            }, time);
        },
        closeAlert: function(){
            $('#alert_model').modal('hide');
        },
        //提示框ajax加载数据后，高度改变，自适应
        alertHeightChange: function () {
            alertHeightChange();
        },
        confirmFuc: function (content, successFunc) {
            //确认框
            confirmContent(content, successFunc);
        },
        confirmNewFuc: function (content, successFunc) {
            //确认框
            confirmNewContent(content, successFunc);
        },
        /* 不再使用
        post: function (url, successFunc, contents, async_bl) {
            //post请求
            postFunc(url, successFunc, contents, async_bl);
        },
        get: function (url, successFunc, contents, async_bl) {
            //get请求
            getFunc(url, successFunc, contents, async_bl);
        },
        */
        initFooter: function () {
            handleGoTop(); //handles scroll to top functionality in the footer
        },
        //验证vin码
        isVin: function (vin) {
            if (vin.length != _vinLength) {
                return false;
            }
            return /^[A-Z0-9]+$/.test(vin);
        },
        //验证是否手机号
        isMobile: function (val) {
            return /^1[34578]\d{9}$/.test(val);
        },
        alert: function (content) {
            $('#alert_dialog .modal-body').val(content);
            $('#alert_dialog').modal('show');
        },
        parseMoney: function (num) {
            if (num == null || num == undefined) {
                return '0.00';
            }
            return (Math.round(num * 100) / 100).toFixed(2);
        }
    };

}();