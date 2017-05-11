/**
 * Created by huangzhangting on 16/6/28.
 */

//jquery ui
var JqUI = {
    blockUI: function(el, msg, centerY) {
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
    unblockUI: function (el) {
        $(el).unblock({
            onUnblock: function () {
                $(el).css('position', '');
                $(el).css('zoom', '');
            }
        });
    },
    loading: function(el){
        this.blockUI(el, '<img src="/img/loading/loading-spinner.gif">');
    }

};
