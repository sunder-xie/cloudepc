/**
 * Created by zhouheng on 16/8/10.
 */
$(function(){
    //添加图片放大功能
    ImgUtil.init($('.goods-img'), ['click']);

    $('.cancel-btn').click(function(){
        var wishId = $(this).data('wish-id');
        var orderId = $(this).data('order-id');
        CANCEL_WISH.alertCancelWish(wishId, orderId);
        CANCEL_WISH.callback = function () {
            window.location.reload();
        }
    });

});
