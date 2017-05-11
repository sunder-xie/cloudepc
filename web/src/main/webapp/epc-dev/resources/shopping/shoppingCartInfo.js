/**
 * Created by zhouheng on 16/7/11.
 */

var _body = $('body');
var _cartGoodsList;

template.helper('qualityTypeHelper',function(qualityType){
    if(qualityType==null || qualityType==undefined){
        return '无';
    }
    var txt = '';
    switch (qualityType){
        case 1: txt = '正常原厂'; break;
        case 2: txt = '正厂配套'; break;
        case 3: txt = '正厂下线'; break;
        case 4: txt = '全新拆车'; break;
        case 5: txt = '旧件拆车'; break;
        default: txt = '品牌';
    }
    return txt;
});

template.helper('numberFormatHelper',function(number){
    return EPC.parseMoney(number);
});

$(function(){
    //加载渲染购物车数据
    loadCartGoodsList();

    //设置城市站回调方法
    SetCityCallback.callback = function(){
        loadCartGoodsList();
    };

});

//购物车数据渲染
function loadCartGoodsList(selectedGoods){

    JqUI.loading(_body);

    JqAjax.get('/shopping/getCartGoodsList',function(result){
        if(result.success){
            var _cartGoodsList = result.data;
            if(_cartGoodsList != undefined && _cartGoodsList != null && _cartGoodsList.length != 0){
                $('#shoppingCartBody').html(template('shoppingCartBodyTemplate',{dataList:_cartGoodsList}));
                $('#shoppingCartFoot').removeClass('hidden');
                if(selectedGoods == true){
                    //页面缓存中取出商品选中状态
                    var selectedGoodsIdList = getSelectedGoods();
                    for(var i=0;i<selectedGoodsIdList.length;i++){
                        var el = $('#'+selectedGoodsIdList[i]);
                        checkedProduct(el,selectedGoodsIdList[i]);
                    }
                }
                //添加图片放大功能
                ImgUtil.init($('.goods-info-img-set'), ['click']);
                //加载总计商品数和总额
                loadTotal();
                //im 通讯
                ChatWithSeller();
            }else{
                $('#shoppingCartBody').html(template('noShoppingCartDataTemplate'));
                $('#shoppingCartFoot').addClass('hidden');
                //加载总计商品数和总额
                loadTotal();
            }
        }else{
            $('#shoppingCartBody').html(template('noShoppingCartDataTemplate'));
            $('#shoppingCartFoot').addClass('hidden');
            //加载总计商品数和总额
            loadTotal();
        }
        JqUI.unblockUI(_body);
    });
}

//商品数量调整
function changeNum(el,goodsId,num){
    var value = $(el).parent().children("input[type='text']").val();
    var int_value = parseInt(value);

    if(int_value==1 && num<0){//商品数量不能小于1
        return false;
    }else{
        if(int_value+num > 999){//购买数量不能大于最大容量
            LayerUtil.msg('非常抱歉，已超最大库存');
            $(el).parent().children("input[type='text']").val(int_value);
            return false;
        }
        //更新购物车中商品数量
        updateProductNum(goodsId, int_value+num);
        $(el).parent().children("input[type='text']").val(int_value+num) ;
        //获取单价
        var single_price = $(el).parents("td").prev().children().last().text();
        var total_amount = EPC.parseMoney(parseFloat(single_price)*(int_value+num));
        //更新总金额
        if($(el).parents("tr").find("input[type='checkbox']").prop('checked')==true){
            var totalAmount = $('#totalAmount')[0].innerText;
            $('#totalAmount').text(EPC.parseMoney(parseFloat(totalAmount)+single_price*num));
        }
        $(el).parents("td").next().find("strong").last().text(EPC.parseMoney(total_amount));
    }
}

function changeInputNum(el,goodsId){
    var input_value = $(el).val().trim();
    if(input_value=='' || input_value.substring(0,1) == 0){
        $(el).val(1);
        input_value = 1;
    }
    //输入验证:非数字,正整数,数字位数小于4
    if(!isNaN(input_value) && (/^\d+$/.test(input_value))){
        if(input_value.length > 3){
            LayerUtil.msg('非常抱歉，已超最大库存');
            var value = input_value.substring(0,input_value.length-1);
            $(el).parent().children("input[type='text']").val(parseInt(value));
            return false;
        }
        //更新购物车中商品数量
        updateProductNum(goodsId, input_value);
        //获取单价
        var single_price = $(el).parents("td").prev().children().last().text();
        var total_amount = parseFloat(single_price)*(input_value);
        $(el).parents("td").next().find("strong").last().text(EPC.parseMoney(total_amount));
        //更新总金额
        var dom = $('#shoppingCartBody').find("input[name='goods']:checked").parents("tr").find("strong[class='total-price']");
        var total=0;
        for(var i=0;i<dom.length;i++){
            total+=JSON.parse(dom[i].innerText);
        }
        $('#totalAmount').text(EPC.parseMoney(total));
    }else{
        $(el).val(1);
        changeInputNum(el,goodsId);
    }

}

//加载总计商品数和总额
function loadTotal(){
    //总金额
    var totalAmount = 0.00;
    var domList = $('#shoppingCartBody').find("input[name='goods']:checked").parents("tr").find("strong[class='total-price']");
    for(var i=0;i<domList.length;i++){
        totalAmount = totalAmount+parseFloat(domList[i].innerText);
    }
    $('#totalAmount').text(EPC.parseMoney(totalAmount));
    //总商品数
    var length = $('#shoppingCartBody').find("input[name='goods']").length;
    //总选中商品数
    var checkedLength = $('#shoppingCartBody').find("input[name='goods']:checked").length;
    $('#totalNum').text(checkedLength);
    if(checkedLength!=length){
        $("input[name='checkedAll']").prop("checked",false);
    }else{
        $("input[name='checkedAll']").prop("checked",true);
    }
    //加载header 中购物车中商品数量图标小提示
    ShoppingCart.reloadCartNum();
}

//购物车中全选商品
function checkedAll(el) {
    if ($(el).prop('checked') == false) {//全不选
        $('#shoppingCartBody').find("input[type='checkbox']").prop("checked", false);
        $("input[name='checkedAll']").prop("checked", false);
        $('#totalAmount').text("0.00");
        $('#totalNum').text(0);
    } else {//全选
        $('#shoppingCartBody').find("input[type='checkbox']").prop("checked", true);
        $("input[name='checkedAll']").prop("checked", true);
        //更新加载总计商品数和总额
        loadTotal();
    }
}

//单选购物车中商品
function checkedProduct(el,id){
    var sellerId = $(el).data('title');
    if($(el).prop('checked')==false){//
        $('#'+id).find("input[type='checkbox']").prop("checked",false);
        var singleAmount = 0.00;
        var totalAmount = $('#totalAmount')[0].innerText;
        var domList = $('#'+id).find("strong[class='total-price']");
        for(var i=0;i<domList.length;i++){
            singleAmount = singleAmount + JSON.parse(domList[i].innerText);
        }
        remainAmount = JSON.parse(totalAmount)-singleAmount;
        $('#totalAmount').text(EPC.parseMoney(remainAmount));//总金额
        //单个卖家中商品总数
        var productLength = $('#'+sellerId).find("input[name='goods']").length;
        // 单个卖家中选中商品数
        var checkedProductLength = $('#'+sellerId).find("input[name='goods']:checked").length;
        if(checkedProductLength != productLength){
             $('#'+sellerId).find("input[name='seller']").prop("checked",false);
        }
        //总商家数
        var sellerLength = $('#shoppingCartBody').find("input[name='seller']").length;
        //选中商家数
        var checkedSellerLength = $('#shoppingCartBody').find("input[name='seller']:checked").length;
        if(sellerLength != checkedSellerLength){
            $("input[name='checkedAll']").prop("checked",false);
        }
        // 购物车中总选中商品数
        var checkedProduct_total = $('#shoppingCartBody').find("input[name='goods']:checked").length;
        $('#totalNum').text(checkedProduct_total);
    }else{
        $('#'+id).find("input[type='checkbox']").prop("checked",true);
        //总金额
        var remainAmount = 0.00;
        var totalAmount = JSON.parse($('#totalAmount')[0].innerText);
        var domList = $('#'+id).find("strong[class='total-price']");
        for(var i=0;i<domList.length;i++){
            remainAmount = remainAmount + JSON.parse(domList[i].innerText);
        }
        $('#totalAmount').text(EPC.parseMoney((remainAmount+totalAmount)));
        //单个卖家中商品总数
        var productLength = $('#'+sellerId).find("input[name='goods']").length;
        //单个卖家中选中商品数
        var checkedProductLength = $('#'+sellerId).find("input[name='goods']:checked").length;
        if(checkedProductLength==productLength){
            $('#'+sellerId).find("input[name='seller']").prop("checked",true);
        }
        //总商家数
        var sellerLength = $('#shoppingCartBody').find("input[name='seller']").length;
        //选中商家数
        var checkedSellerLength = $('#shoppingCartBody').find("input[name='seller']:checked").length;
        if(sellerLength == checkedSellerLength){
            $("input[name='checkedAll']").prop("checked",true);
        }
        // 购物车中总选中商品数
        var checkedProduct_total = $('#shoppingCartBody').find("input[name='goods']:checked").length;
        $('#totalNum').text(checkedProduct_total);
    }
}

//购物车中删除商品
function deleteProduct(goodsId){
    //批量删除购物车内商品
    if(goodsId==-1){
        var list = $('#shoppingCartBody').find("input[name='goods']:checked");
        if(list.length == 0){
            LayerUtil.msg('请选择要删除的商品');
        }else{
            EPC.confirmNewFuc("你确定要删除选中的商品吗?",function () {
                var goodsIdList = [];
                for(var i=0;i<list.length;i++){
                    goodsIdList.push($(list[i]).data('goods-id'));
                }
                var goodsIdStr = goodsIdList.join(",");
                deleteGoodsByIdList(goodsIdStr);
            });
        }
    }else{ //单个商品删除
        EPC.confirmNewFuc("你确定要删除商品吗?",function(){
            deleteCartGoods(goodsId);
        });
    }
}
//批量删购物车中数据
function deleteGoodsByIdList(goodsIdStr){
    JqAjax.post('/shopping/deleteGoodsByIdList',function(result){
        if(result.success){
            setSelectedGoods(goodsIdStr);
            loadCartGoodsList(true);
        }else{
            LayerUtil.msg('批量删除商品失败');
        }
    },{goodsIdStr:goodsIdStr});
}

//删除购物车数据
function deleteCartGoods(goodsId){
    JqAjax.post('/shopping/deleteCartGoods',function(result){
        if(result.success){
            setSelectedGoods(goodsId.toString());
            loadCartGoodsList(true);
        }else{
            LayerUtil.msg('删除该商品失败');
        }
    },{goodsId:goodsId});
}

//修改给买商品数量
function updateProductNum(goodsId, goodsNumber){
    var param = {
        goodsId : goodsId,
        goodsNumber : goodsNumber
    };
    JqAjax.post('/shopping/modifyGoodsNumber',function(){},param);
}

//点击结算按钮,进入核对订单信息页面
function consignee(){
    var list = $('#shoppingCartBody').find("input[name='goods']:checked");
    if(list.length == 0){
        LayerUtil.msg('请先选择商品，再结算');
        return;
    }

    JqUI.loading(_body);

    var idList=[];
    var checkGoodsParamList = [];
    for(var i=0;i<list.length;i++){
        var obj = $(list[i]);
        idList.push(obj.val());

        var checkGoods = {
            goodsId: obj.data('goods-id'),
            partName: obj.data('part-name')
        };
        checkGoodsParamList.push(checkGoods);
    }
    var idStr = idList.join(",");

    Ajax.postJson({
        url: '/shopping/checkGoodsForSettlement',
        data: JSON.stringify(checkGoodsParamList),
        success: function(result){

            JqUI.unblockUI(_body);

            if(result.success){
                window.location.href = '/shopping/consignee?idStr='+idStr;
            }else{
                LayerUtil.msgFun(result.message, function(){
                    setSelectedGoods();
                    loadCartGoodsList(true);
                });
            }
        }
    });
}

/* 清空失效商品 */
function deleteUnAvailableGoods(){
    JqUI.loading(_body);

    Ajax.get({
        url: '/shopping/deleteUnAvailableGoods',
        success: function(result){
            JqUI.unblockUI(_body);

            if(result.success){
                LayerUtil.msgFun('清空失效商品成功', function(){
                    setSelectedGoods();
                    loadCartGoodsList(true);
                });
            }else{
                LayerUtil.msgFun(result.message, function(){
                    setSelectedGoods();
                    loadCartGoodsList(true);
                });
            }
        }
    });
}

//im 通讯
function ChatWithSeller(){
    $(".seller-chat").unbind("click").click(function(){
        var to_sys_id = $(this).data("orgid");
        TqmallChat.openChatWindow(to_sys_id,"yunpei",undefined,WishChatMap.bottom_url,WishChatMap.bottom_url_btn_name,WishChatMap.guide_url,WishChatMap.guide_content);
    });
}
//缓存选中商品
function setSelectedGoods(goodsStr){
    var goodsArr = [];
    if(goodsStr != null || goodsStr!= undefined ){
        goodsArr = goodsStr.split(",");
    }else{
        goodsArr = [];
    }

    LocalStorageUtil.setParam("selectedGoodsId",null);
    //总选中商品数
    var list = $('#shoppingCartBody').find("input[name='goods']:checked");
    var goodsIdList = [];
    for(var i=0;i<list.length;i++){
       goodsIdList.push(JSON.parse($(list[i]).data('goods-id')));
    }
    $.each(goodsArr,function(index,goodsId){
       $.each(goodsIdList,function(idx,goods_id){
           if(goodsId == goods_id){
               goodsIdList.splice(idx,1);
           }
       });
    });
    LocalStorageUtil.setParam("selectedGoodsId",JSON.stringify(goodsIdList));

}
//localStorage缓存页面数据:获取商品选中项
function getSelectedGoods(){

    var jsonGoodsId = JSON.parse(LocalStorageUtil.getParam("selectedGoodsId"));
    if(jsonGoodsId==null || jsonGoodsId == ""){
        return [];
    }else{
        return jsonGoodsId;
    }
}