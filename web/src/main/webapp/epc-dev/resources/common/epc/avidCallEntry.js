/**
 * Created by huangzhangting on 16/11/17.
 */

$(function(){
    var toId = setTimeout(function(){
        $('.avid-call-introduce').addClass('hidden');
        $('.close-avid-introduce-btn').addClass('open-avid-introduce-btn');
    }, 10000);

    $('.close-avid-introduce-btn').click(function(){

        clearTimeout(toId);

        var introduce = $('.avid-call-introduce');
        if(introduce.hasClass('hidden')){
            introduce.removeClass('hidden');
            $('.close-avid-introduce-btn').removeClass('open-avid-introduce-btn');
        }else{
            introduce.addClass('hidden');
            $('.close-avid-introduce-btn').addClass('open-avid-introduce-btn');
        }
    });

});
