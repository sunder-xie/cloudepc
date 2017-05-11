/**
 * Created by huangzhangting on 16/7/8.
 */

/** 购物相关js */
var _OfferGoodsList = [];
var _oeNumber;
var _partName;

var Shopping = {
    init: function(){
        $('.top-shopping-cart').click(function(){

        });
    },
    /** 查询报价信息 */
    getOfferInfo: function(carId,oeNum, qualityId, partName){
        _partName = partName;
        var contentDiv = $('#offerInfoContent');

        //if(IsLogin !== 1){
        //    contentDiv.html(template('loginBtnTemplate'));
        //    contentDiv.find('.login-btn-style').click(function(){
        //        var callbackFun = function(){
        //            if(IsVerifySuccessShop !== 1){
        //                contentDiv.html(template('authBtnTemplate'));
        //            }else{
        //                contentDiv.html('');
        //                Shopping.toGetOfferInfo(carId,oeNum, qualityId, contentDiv, partName);
        //            }
        //            //查询购物车数量
        //            ShoppingCart.reloadCartNum();
        //        };
        //        AjaxLogin.init(callbackFun);
        //    });
        //    return;
        //}
        //if(IsVerifySuccessShop !== 1){
        //    contentDiv.html(template('authBtnTemplate'));
        //    return;
        //}

        Shopping.toGetOfferInfo(carId,oeNum, qualityId, contentDiv, partName);
    },
    /** 访问后台查询报价信息 */
    toGetOfferInfo: function(carId,oeNum, qualityId, contentDiv, partName){
        var param = {carId:carId, oeNum:oeNum};
        if(qualityId!==undefined && qualityId!==null && qualityId != 0){
            param['qualityId'] = qualityId;
        }

        JqUI.loading(contentDiv);

        //异常错误，回调方法
        var errorCallback = {
            unblockUI: function(){
                JqUI.unblockUI(contentDiv);
            }
        };

        JqAjax.get('/goodsDetail/getGoodsQuote', function(result){
            if(result.success){
                _oeNumber = oeNum;
                _partName = partName;

                contentDiv.html(template('offerInfoTemplate', result));
                Shopping.bindButton(carId);

                _OfferGoodsList = result.data;

            }else{
                contentDiv.html('暂无报价信息');

                _OfferGoodsList = [];
            }

            JqUI.unblockUI(contentDiv);

        }, param, errorCallback);
    },
    /** 按钮绑定事件 */
    bindButton: function(carId){
        $('.join-cart-bt').click(function(){
            if(!Shopping.checkVerifyStatus()){
                return
            }
            var bt = $(this);
            var id = bt.attr('data-id');
            var goodsNumber = bt.parent().prev().find("input[type='text']").val();
            Shopping.joinCart(bt,id,parseInt(goodsNumber));
        });
        
        $('.buy-now-bt').click(function(){
            if(!Shopping.checkVerifyStatus()){
                return
            }
            var bt = $(this);
            var id = bt.attr('data-id');
            var goodsNumber = bt.parent().prev().find("input[type='text']").val();

            var size = _OfferGoodsList.length;
            var obj;
            for(var i=0; i<size; i++){
                if(id==_OfferGoodsList[i].id){
                    obj = _OfferGoodsList[i];
                    break;
                }
            }

            var param = {
                goodsId: obj.goodsId,
                partName: _partName,
                goodsPrice: obj.price
            };

            var body = $('body');
            JqUI.blockUI(body);

            Ajax.post({
                url: '/shopping/checkGoodsForBuyNow',
                data: param,
                success: function(result){
                    JqUI.unblockUI(body);

                    if(result.success){
                        //请求跳转
                        window.location.href = '/shopping/purchaseImmediately?goodsId='+obj.goodsId+'&oeNumber='+_oeNumber+
                            '&goodsNumber='+goodsNumber+'&sellerId='+obj.thirdPartId+'&partName='+_partName+'&sellerName='+obj.companyName+'&sellerTel='+obj.sellerTel;
                    }else{
                        LayerUtil.msg(result.message);
                    }
                }
            });

        });
        
        $('.contact-seller-bt').click(function(){
            if(!Shopping.checkVerifyStatus()){
                return
            }
            var to_sys_id = $(this).data("orgid");
            TqmallChat.openChatWindow(to_sys_id,"yunpei",undefined,WishChatMap.bottom_url,WishChatMap.bottom_url_btn_name,WishChatMap.guide_url,WishChatMap.guide_content);
            //TipUtil.developing(this);
        });
    },
    /** 后台保存购物车数据*/
    addGoods : function(goods,el){
        var body = $('body');
        //异步登录成功后，回调方法
        //TimeoutCallback.callbackFun = function(){
        //    JqUI.blockUI(body);
        //    JqAjax.postJson('/shopping/joinShoppingCart', function(result){
        //        JqUI.unblockUI(body);
        //        if(result.success){
        //            layer.msg('成功加入购物车!', {shade: [0.1, '#393D49'], time: 1500});
        //            ShoppingCart.reloadCartNum();
        //        }else{
        //            layer.msg('加入购物车失败!', {shade: [0.1, '#393D49'], time: 1500});
        //        }
        //    },JSON.stringify(goods));
        //};

        JqUI.blockUI(body);

        JqAjax.postJson('/shopping/joinShoppingCart',function(result){
            JqUI.unblockUI(body);
            if(result.success){
                LayerUtil.msg('成功加入购物车');
                ShoppingCart.reloadCartNum();
            }else{
                LayerUtil.msg('加入购物车失败');
            }
        }, JSON.stringify(goods), TimeoutCallback);
    },
    /** 加入购物车 */
    joinCart: function(el,id,goodsNumber){
        var partName = $('#partName')[0].innerText;
        var size = _OfferGoodsList.length;
        for(var i=0; i<size; i++){
            var obj = _OfferGoodsList[i];
            if(id==obj.id){
                var goods =  {
                    "goodsId": obj.goodsId,
                    "goodsSn" : obj.newGoodsSn,
                    "partName" : partName,
                    "oeNumber":_oeNumber,
                    "goodsNumber":goodsNumber,
                    "sellerId": obj.thirdPartId,
                    "sellerTelephone" : obj.sellerTel,
                    "sellerCompanyName" : obj.companyName
                };
                //保存购物车数据
                Shopping.addGoods(goods,el);
                break;
            }
        }
        //更新购物车中商品数量图标小提示
        ShoppingCart.reloadCartNum();
    },
    checkVerifyStatus: function(){
        if(IsVerifySuccessShop !== 1){
            EPC.alertFuc("您尚未成为汽配管家会员，无法使用此功能！请至左上角进行认证");
            //layer.msg('您尚未成为汽配管家会员，无法使用此功能！', {shade: [0.1, '#393D49'], time: 2000});
            return false;
        }
        return true;
    }
};