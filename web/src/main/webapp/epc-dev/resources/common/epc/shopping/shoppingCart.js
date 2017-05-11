/**
 * Created by zhouheng on 16/7/7.
 */


var LocalStorageUtil = {
    setParam : function (name,value){
        localStorage.setItem(name,value)
    },
    getParam : function(name){
        return localStorage.getItem(name)
    }
};

$(function(){
    if($('.header-logout').hasClass('hidden')){
        //alert('请先登录');
    }else{
        ShoppingCart.reloadCartNum();
    }
});

var ShoppingCart = {
    /** 更新购物车数字*/
    reloadCartNum: function(){
        JqAjax.get('/shopping/getCartGoodsNum',function(result){
            if(result.success){
                $('#cartTip').text(result.data);   
            }else{
                $('#cartTip').text(0);
                TipUtil.tooltipFun($('#cartTip'), '数据异常!');
            }
        });
    }
};
