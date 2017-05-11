/**
 * Created by huangzhangting on 16/4/29.
 */
var BUI = {
    jqBlockUI: function(el, msg, centerY) {
        if (msg == undefined) {
            msg = '';
        }
        var $el = $(el);
        if ($el.height() <= 400) {
            centerY = true;
        }
        $el.block({
            message: msg,
            centerY: centerY != undefined ? centerY : true,
            css: {
                top: '10%',
                border: 'none',
                padding: '2px',
                backgroundColor: 'none'
            },
            overlayCSS: {
                backgroundColor: 'grey',
                opacity: 0.3,
                cursor: 'wait'
            }
        });
    },
    jqUnblockUI: function (el) {
        $(el).unblock({
            onUnblock: function () {
                $(el).css('position', '');
                $(el).css('zoom', '');
            }
        });
    },
    jqLoading: function(el){
        this.jqBlockUI(el, '<img src="/img/loading/loading-spinner.gif">');
    },
    imgBigger: function () {
        var x = -150;
        var y = -150;
        var max_height = 850;

        //跟浏览器 高度和宽度对比，若浏览器高宽过小，则设置其最大值为其小值
        var do_height = document.documentElement.clientHeight;
        var do_width = document.documentElement.clientWidth;

        if(do_height < max_height) max_height = do_height;
        if(do_width < max_height) max_height = do_width;

        //改鼠标移动为点击事件
        $("img.small-img").unbind("click").click(function (e) {
            if($(this).hasClass("active")){
                return false;
            }
            if($("#tooltip").length>0){
                $("#tooltip").remove();
            }
            $("img.small-img").removeClass("active");
            $(this).addClass("active");

            var img = new Image();
            img.src = $(this).attr('src');
            document.body.appendChild(img);

            //
            var imgWid = img.width < max_height ? img.width : max_height;
            var imgHei = img.height < max_height ? img.height : max_height;

            if (imgHei >= max_height) {
                imgWid = img.width * (max_height / img.height);
            }
            if (imgWid >= max_height) {
                imgHei = img.height * (max_height / img.width);
            }

            x = -(imgWid + 15);
            y = -(imgHei + 15);
            img.setAttribute("id", "tooltip");

            // 防止图片过大 超出浏览器范围
            var css_top = e.pageY + y;
            if(css_top < 0){
                css_top = 0;
            }
            var css_left = e.pageX + x;
            if(css_left < 0){
                css_left = 0;
            }

            $("#tooltip")
                .css({
                    "position": "absolute",
                    "border": "1px solid #cdcdcd",
                    "background": "#cdcdcd",
                    "color": "#fff",
                    "z-index": 9999,
                    "top": css_top + "px",
                    "left": css_left + "px",
                    "max-width": max_height+ "px",
                    "max-height": max_height+ "px"
                }).show("fast");
            return false;
        });

        $("body").unbind("click").click(function(){
            if($("#tooltip").length>0){
                $("#tooltip").remove();
                $("img.small-img").removeClass("active");
            }
        });
    }

};
