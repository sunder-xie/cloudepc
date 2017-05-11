/**
 * 聊天框需求单js
 * Created by lyj on 16/8/12.
 */

var parentWindow = window.parent.document.getElementById('expand_bottom_iframe');
if(parentWindow!==void 0){
    $('body').css('height', $(parentWindow).height()+'px');
}

var REQUIREMENTS = function () {
    var NO_SN_TEXT = '<p class="noTxt">您与当前联系人的今日聊天记录中尚未涉及任何需求单号</p>';

    var initEvent = function () {
        //初始化需求单号点击事件
        initSnClick();

        //初始化第一个需求单号显示的内容
        initFirstSn();

        //初始化刷新按钮
        $('.refresh').on("click", function () {
            location.reload();
        });
    };

    var initSnClick = function () {
        $('#tab1').find('span').on('click', function () {
            if ($(this).hasClass("choose")) {
                return false;
            }

            $(this).addClass("choose").siblings().removeClass("choose");
            initDetail($(this).text(), $(this).data("seller-id"));
        });
    };

    var initImgClick = function () {
        $('.img-click').on("click", function () {
            var img_url = $(this).attr("src");
            TimIframeUtil.alertImg(img_url);
        });
    };

    var initMoreInfoClick = function (sellerName, suppleGoodsList) {
        $('#moreInfoBt').on('click', function () {
            $("#mylist").hide();
            $('#basicModal').modal('show');
            $('#more').html(template('moreInfoTemplate', {
                text: "【" + sellerName + "】" + "对需求单进行了配件补充，具体如下",
                list: suppleGoodsList
            }));
        });
    };

    var initHasPrice = function (sellerName, goodsBOList) {
        $('.hasPrice').on("click", function () {
            var mylist = $(this).parent("tr").html();
            mylist = "<tr>" + dele(mylist) + "</tr>";
            $(".mylist").html(mylist);
            $("#mylist").show();
            $('#basicModal').modal('show');
            $('#more').html(template('moreInfoTemplate', {text: "【" + sellerName + "】" + "的报价", list: goodsBOList}));

            var id = $(this).parent("tr").attr("id");
            $("#moreInfor tbody tr").each(function () {
                var groupId = $(this).data("id");
                if (groupId != id) {
                    $(this).remove();
                }
            });

            initImgClick();
        })
    };

    var todayWishList = function (result) {
        var sn = result['wish_list'];
        if (sn == undefined || sn.length <= 0) {
            return;
        }

        var sellerId = result['to_sys_id'];
        var dataMap;
        dataMap = {
            chatObjectId: sellerId,
            snList: sn
        };

        JqAjax.postJson('/monkChat/cacheWishSn', function (result) {
            var tempHtml = '';
            var spanNum = $('#tab1').find('span').length;

            if (result.success) {
                var resultLength = result.data.length;

                if (resultLength > 0) {
                    for (var i = 0; i < resultLength; i++) {
                        tempHtml += '<span class="order-num-span" data-seller-id="' + sellerId + '">' + result.data[i] + '</span>';
                    }

                    if (spanNum == 0) {
                        $('#tab1').html('');
                    }
                    $('#tab1').append(tempHtml);

                    initSnClick();
                    initFirstSn();

                }
            }
        }, JSON.stringify(dataMap));
    };

    var initFirstSn = function () {
        var sns = $('#tab1').find('span');
        if (sns.length == 0) {
            return;
        }

        var firstSn = $(sns[0]);
        initDetail(firstSn.text(), firstSn.data('seller-id'));
    };

    var initDetail = function (sn, sellerId) {
        var div = $('.content-div');
        JqUI.loading(div);

        JqAjax.get('/monkChat/groupedWishInfo', function (result) {
            if (result.success) {
                var data = result.data;

                $('#detailDiv').html(template('detailTemplate', data));

                //配件单价一栏
                var feed = data.goodsBOList;
                var goods = data.wishListVO.goodsList;
                var status = data.wishListVO.status;
                var keyArr = [];
                var idArr = [];

                for (var key in feed) {
                    keyArr.push(feed[key].groupId);
                }
                for (var key in goods) {
                    idArr.push(goods[key].groupId);
                }
                for (var i = 0; i < idArr.length; i++) {
                    if (keyArr.indexOf(idArr[i]) >= 0) {
                        if (status == "XQRBJ" || status == "XYQX" || status == "BYQX") {
                            $("#" + idArr[i]).find(".statusTxt").text("已报价");
                        } else {
                            $("#" + idArr[i]).find(".statusTxt").addClass("hasPrice").text("已报价");
                        }
                    } else {
                        $("#" + idArr[i]).find(".statusTxt").text("未报价");
                    }
                }

                //需求单状态
                var st = data.wishListVO['statusDesc'],
                    num = data.sellerNum,
                    statusStr = data.status,
                    txt = '';
                if (status == "XDBJ") {
                    txt = st + "(" + data.wishListVO.sellerList.length + "人将进行报价)";
                }
                else if (status == "XYBJ") {
                    txt = st + "(已被" + num + "人报价，当前联系人" + statusStr + ")";
                }
                else if (status == "XQRBJ") {
                    txt = statusStr;
                } else {
                    txt = "已取消";
                }
                $("#orderStatus").text(txt);

                initMoreInfoClick(data.sellerName, data.suppleGoodsList);
                initHasPrice(data.sellerName, data.goodsBOList);
                initImgClick();
            } else {
                $('#detailDiv').html(template('msgTemplate', {msg: result.message}));
            }

            JqUI.unblockUI(div);

        }, {wishListSn: sn, sellerId: sellerId});

    };

    var dele = function (str) {
        str = str.replace(/\<td class="statusTxt.*\<\/td>/g, '');
        return str;
    };

    return {
        init: function () {
            //绑定获取今日聊到需求单号方法（im会自动推送数据）
            TimIframeUtilForLOP.todayWishList(todayWishList);

            initEvent();
        }
    }
}();

REQUIREMENTS.init();