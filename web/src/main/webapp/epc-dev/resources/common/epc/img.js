/**
 * Created by huangzhangting on 16/7/11.
 */

var ImgUtil = {
    /** 初始化 */
    init: function(el, events){
        $(el).css('cursor', 'pointer').attr('title', '点击查看原图');

        if(events==undefined){
            $(el).click(function(){
                ImgUtil.showOriginalImg(this);
            });
        }else{
            for (var i = 0; i < events.length; i++) {
                $(el).on(events[i], function(){
                    ImgUtil.showOriginalImg(this);
                });
            }
        }

        $('#originalImgModal').click(function(){
            ImgUtil.hideOriginalImg();
        });

        $('.modal-scrollable').click(function(){
            ImgUtil.hideOriginalImg();
        });

    },
    /** 查看原图 */
    showOriginalImg: function(el){
        var src = $(el).attr('src');
        var img = new Image();
        img.src = src;
        img.onload = function () {
            var originalImgModal = $('#originalImgModal');
            var globalOriginalImg = $('#globalOriginalImg');
            globalOriginalImg.attr('src', src).attr('width', img.width).attr('height', img.height);
            originalImgModal.modal('show');

            originalImgModal.click(function(){
                originalImgModal.modal('hide');
            });
            globalOriginalImg.click(function(){
                originalImgModal.modal('hide');
            });
        };
    },
    /** 隐藏原图 */
    hideOriginalImg: function(){
        $('#originalImgModal').modal('hide');
    }
};
