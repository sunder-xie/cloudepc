/**
 * Created by zhouheng on 16/8/13.
 */
$(function(){
    
});

function changeView(el, idx){
    $('.main-left-btn').removeClass('active');
    $(el).addClass('active');

    var helpContent = $('.help-content');
    helpContent.addClass('hidden');
    helpContent.eq(idx).removeClass('hidden');
}