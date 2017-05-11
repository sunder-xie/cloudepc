//是否是第一次初始化地区
var is_first_init_area = true;
//总的图片上传
var sum_uploader;

var body_obj = $("body");
var nowStep = Number($("#nowStep").val());
$(function () {
    button_click();
    if(nowStep == 3){
        EPC.alertFuc("由于您处于认证中，只能更改第三步的认证图片~");
    }

    input_init();
    if( nowStep> 1){
        other_button_click();
        //step2
        select_init();
        //step3
        pic_init();
    }
});

function input_init(){
    //输入框绑定回车事件
    $('#verifyCodeInput').bind('keypress', function (event) {
        if (event.keyCode == 13) {
            $('#login_btn').trigger('click');
        }
    });

    $(".must_input,#saleMobile").bind('keypress', function (event) {
        if (event.keyCode == 13) {
            $('#go_step_3_btn').trigger('click');
        }
    });
}
function button_click(){
    //校验码点击事件
    $("#checkCodeImg").click(function () {
        $(this).attr('src', $(this).data('url') + "?v=" + new Date().getTime());
    });
    //发送验证码
    $("#getCode").click(function(){
        var userPhone = $("#userPhone").val();
        var oldPhone = $("#phone").val();
        if(oldPhone == userPhone && userPhone != ""){
            // 从第二页过来，且手机号码没变
            TipUtil.tooltipFun($(this), '手机号没变更，无需填写验证码');
            return false;
        }
        LOGIN_NEW.sendVerify(this,$("#userPhone"),$('#checkCode'));
    });

    $("#login_btn").unbind("click").click(function () {
        var userPhone = $("#userPhone").val();
        var verifyCode = $("#verifyCodeInput").val();

        var oldPhone = $("#phone").val();
        if(oldPhone == userPhone && userPhone != ""){
            // 从第二页过来，且手机号码没变
            $("#go_step_2_btn").trigger("click");
            return false;
        }

        if (!EPC.isMobile(userPhone)) {
            TipUtil.tooltipFun($('#userPhone'), '请填写正确的手机号');
            input.focus();
            return;
        }
        if (verifyCode == '') {
            TipUtil.tooltipFun($('#verifyCodeInput'), '请填写验证码');
            input.focus();
            return;
        }

       Ajax.post({
           url:"/verifyShop/verifyPhone",
           data:{userName:userPhone,verifyCode:verifyCode},
           success:function(data){
               if(data.success){
                   window.location.reload();
               }else{
                   EPC.alertFuc(data.message);
               }
           }
       });
    });

}

function other_button_click(){
    //到第一页
    $("#go_step_1_btn").unbind("click").click(function () {
        $("#phone_tab").addClass("active");
        $("#data_tab").removeClass("active");

        $("#data_li").removeClass("active");

        $("#bar").css("width","33.33%")
    });
    //到第二页
    $("#go_step_2_btn").unbind("click").click(function () {
        $("#data_tab").addClass("active");
        $("#photo_tab").removeClass("active");
        $("#phone_tab").removeClass("active");

        $("#data_li").addClass("active");
        $("#photo_li").removeClass("active");

        $("#bar").css("width","66.66%")
    });
    //到第三页
    $("#go_step_3_btn").unbind("click").click(function () {
        if(check_step_2()) {
            $("#photo_tab").addClass("active");
            $("#data_tab").removeClass("active");

            $("#photo_li").addClass("active");

            $("#bar").css("width", "100%")
        }
    });

    //申请认证
    $("#start_verify_btn").unbind("click").click(function () {
        if(!check_step_2()) {
            EPC.alertFuc( '第二步有未填的必填信息，请填写完成后，再申请认证');
            $("#go_step_2_btn").trigger("click");
            return false;
        }
        if(!check_step_3()){return false;}
        //开始上传数据
        var confirm_str = "您确定开始提交所有资料，申请认证么？";
        if(nowStep == 3){confirm_str = "您确定更改照片资料，修改么？"}
        EPC.confirmNewFuc(confirm_str,function(){
            // 图片上传完后，在下面的 回调函数（init_sum_upload）里面处理
            JqUI.loading(body_obj);
            //排序，按次序 文件
            sum_uploader.files.sort(function(a,b){
                var a_number = Number(a.name.split(".")[0]);
                var b_number = Number(b.name.split(".")[0]);
                return a_number-b_number});
            sum_uploader.start();
        })

    });
}


function select_init(){
    var china_id = 1;
    var province_obj = $("#province");
    var city_obj = $("#city");
    var district_obj = $("#district");
    var street_obj = $("#street");

    clear_select2([$(".select2me")]);
    //初始化省份
    select_ajax(province_obj,china_id,function(choose_province_id){
        //清除后面三个
        clear_select2([city_obj,district_obj,street_obj]);
        //设置城市
        select_ajax(city_obj,choose_province_id,function(choose_city_id){
            //清除后面2个
            clear_select2([district_obj,street_obj]);
            //设置区县
            select_ajax(district_obj,choose_city_id,function(choose_district_id){
                //清除后面1个
                clear_select2([street_obj]);
                select_ajax(street_obj,choose_district_id)
            })
        })
    });
    //赋予默认值
    var province_default_val = $("#provinceId").val();
    var city_default_val = $("#cityId").val();
    var district_default_val = $("#districtId").val();
    var street_default_val = $("#streetId").val();
    if(province_default_val != "") province_obj.val(province_default_val).trigger("change");
    if(city_default_val != "") city_obj.val(city_default_val).trigger("change");
    if(district_default_val != "") district_obj.val(district_default_val).trigger("change");
    if(street_default_val != "") street_obj.val(street_default_val).trigger("change");
    is_first_init_area = false;
}

// 后台取数据,successFunc--当数据变更时，处理类
function select_ajax(the_obj,pid,successFunc){
    var el_obj = body_obj;
    JqUI.loading(el_obj);

    var area_key = "session_regions_"+pid;
    var areaResult = window.sessionStorage.getItem(area_key);
    if(areaResult) {
        var resultList = JSON.parse(areaResult);
        select_ajax_help(resultList,the_obj,el_obj,successFunc);
    }else{
        var async_val = true;
        if(is_first_init_area){
            async_val = false;
        }
        Ajax.get({
            url: "/region/subRegions",
            data: {pid: pid},
            async: async_val,
            success: function (resultList) {

                window.sessionStorage.setItem(area_key,JSON.stringify(resultList));
                select_ajax_help(resultList,the_obj,el_obj,successFunc)
            }
        });
    }
}

function select_ajax_help(resultList,the_obj,block_obj,successFunc){
    var data = [];
    $.each(resultList, function (i, reginBO) {
        data.push({id: reginBO.id, text: reginBO.firstLetter + " " + reginBO.regionName});
    });
    $(the_obj).select2({
        data: data
    });

    JqUI.unblockUI(block_obj);
    $(the_obj).change(function () {
        var now_val = $(the_obj).val();
        if (now_val == "0") {
            return true;
        }
        if ($.isFunction(successFunc)) {
            successFunc(now_val);
        }
    });
}

//清除数据
function clear_select2(obj_list){
    $.each(obj_list, function (i,the_obj) {
        $(the_obj).unbind("change").empty();
        $(the_obj).val("").trigger("change");

        var placeholder_val = $(the_obj).data("placeholder");
        $(the_obj).select2({
            data: [{id:0,text:placeholder_val}]
        });

    })
}


/*=============step3=======*/
//图片的初始化
function pic_init(){
    //若存在原图，则把原图的数据放入图片栏目
    var old_imgs = $("#old_imgs").val();
    if(old_imgs != ""){
        var old_array = old_imgs.split(",");
        $.each($(".photo_img img"),function(i,the_obj){
            var old_photo_url =  old_array[i];
            var show_photo_url = old_photo_url;
            if(old_photo_url != ""){
                if(old_photo_url.indexOf("http") > -1){
                    show_photo_url = show_photo_url.replace("://","");
                    show_photo_url = show_photo_url.substring(show_photo_url.indexOf("/"));
                    old_imgs = old_imgs.replace(old_photo_url,show_photo_url);
                }
                $(the_obj).parent().siblings(".preview_btn").data("url",show_photo_url);
                $(the_obj).attr("src",_ossImgHost+show_photo_url);
            }
        });

        $("#old_imgs").val(old_imgs);
    }
    //放大图片
    $(".photo_img  .fa-search").click(function(){
        var parent_obj = $(this).parent().parent();
        var img_obj = parent_obj.children("img");
        ImgUtil.showOriginalImg(img_obj);
    });
    //删除图片，若是 示例图片，则触发上传图片按钮
    $(".photo_img  .fa-times").click(function(){
        var parent_obj = $(this).parent().parent();
        var img_obj = parent_obj.children("img");

        var default_src = parent_obj.data("img-url");
        var img_src = img_obj.attr('src');

        var preview_btn_obj = parent_obj.siblings(".preview_btn");
        if(default_src == img_src){
            // 触发上传事件
            preview_btn_obj.trigger("click");
        }else{
            img_obj.attr('src',default_src);
            preview_btn_obj.data("url","");
            var old_file_name = preview_btn_obj.data("oldfile");
            if(old_file_name != "") {
                preview_btn_obj.data("oldfile","");
                var old_paixu_file_name =  preview_btn_obj.data("photo")+".jpg";
                del_sum_upload_file(old_paixu_file_name);
            }
        }
        return false;
    });


    //初始化 图片提交的load
    sum_uploader = init_sum_upload();
    upload_btn("photo_btn_01");
    upload_btn("photo_btn_02");
    upload_btn("photo_btn_03");
    upload_btn("photo_btn_04");

}

function upload_btn(the_id){
    // 图片上传的接口
    var config = {
        browse_button:the_id,
        max_file_size:"10mb"
        //max_file_size:"200kb"
    };
    var callBackFunc = {
        //当文件添加到上传队列后触发,此处为预览和添加到总上传入口中
        FilesAdded: function (up, files) {
            console.log("FilesAdded");
            var btn_obj = $("#"+the_id);

            //删除老的 file
            var the_file = files[0];

            var file_name = the_file.name;
            var old_file_name = btn_obj.data("oldfile");
            if(file_name == old_file_name){
                //没有变更
                return;
            }

            var suffix = file_name.substring(file_name.indexOf("."));
            the_file.name = btn_obj.data("photo")+suffix;
            var paixu_file_name = the_file.name;
            if(old_file_name != "" ) {
                del_sum_upload_file(paixu_file_name);
                //自身无需存储file
                up.files.splice(0, 1);
            }

            //添加新的file
            btn_obj.data("oldfile",file_name);
            sum_uploader.addFile(the_file,paixu_file_name);
            if(the_file.type == "image/jpg" || the_file.type == "image/png" || the_file.type == "image/jpeg") {
                //预览此图片
                var img = new o.Image();
                img.onload = function () {
                    //var imgsrc = the_file.type == "image/jpeg" ? this.getAsDataURL("image/jpeg", 80) : this.getAsDataURL();
                    var imgsrc = this.getAsDataURL();
                    btn_obj.siblings(".photo_img").children("img").attr('src', imgsrc);
                    img.destroy();
                    img = null;
                };
                img.load(the_file.getSource());
            }else{
                var fr = new mOxie.FileReader();
                fr.onload = function () {
                    btn_obj.siblings(".photo_img").children("img").attr('src', fr.result);
                    fr.destroy();
                    fr = null;
                };
                fr.readAsDataURL(the_file.getSource());
            }

            console.log(sum_uploader.files);
        },
        //发生错误时触发
        Error: function (up, err) {
            if(err.code == -600){
                EPC.alertFuc("图片大小超过"+config['max_file_size']+",无法上传");
            }
        }
    };

    UPLOAD.upload(config, callBackFunc);
}

function del_sum_upload_file(file_name){
    var sum_files = sum_uploader.files;
    var del_index = undefined;
    $.each(sum_files,function(i,sum_file){
        if(sum_file.name == file_name){
            del_index = i;
            return false;
        }
    });
    if(del_index != undefined) {
        sum_files.splice(del_index, 1);
    }
}
function init_sum_upload(){



    var config = {
        browse_button:'photo_sum_btn',
        url:"/upload/uploadImage"
    };
    var callBackFunc = {
        PostInit:function(){
            console.log("PostInit");
        },
        //当文件添加到上传队列后触发
        FilesAdded: function (up, files) {
            console.log("FilesAdded");
        },
        //当队列中的某一个文件上传完成后触发
        FileUploaded: function (up, file, result) {
            console.log("FileUploaded");
            var result_data = JSON.parse(result.response);

            var file_name = file.name;
            var file_number = file_name.split(".")[0];
            if(result_data.success){
                var img_url =  result_data.data;
                $("#photo_btn_0"+file_number).data("url","/"+img_url);
            }else{
                JqUI.unblockUI(body_obj);
                sum_uploader.stop();
                $.each($(".photo_img  .fa-times"), function (i,the_obj) {
                    var photo_btn_obj = $(the_obj).parent().parent().siblings(".preview_btn");
                    if(photo_btn_obj.data("url") == ""){
                        $(the_obj).trigger("click");
                    }
                });
                EPC.alertFuc("认证过程中，上传图片出错，请稍后重试");
            }
        },
        //当上传队列中所有文件都上传完成后触发
        UploadComplete: function (up, files) {
            console.log("UploadComplete");
            //数据提交
            save_verify();
        },
        //会在文件上传过程中不断触发，可以用此事件来显示上传进度
        UploadProgress: function (up, file) {
            console.log("UploadProgress");
        },
        //发生错误时触发
        Error: function (up, err) {
            console.log("Error");
        }
    };


    return UPLOAD.upload(config,callBackFunc)
}

/*====提交数据-认证===*/
function save_verify(){

    //图片上传完毕后的url
    var photo_url_list = [];
    $.each($(".preview_btn"),function(i,the_obj){
       var url = $(the_obj).data("url").trim();
        photo_url_list.push(url);
    });

    var photo_str = photo_url_list.join(",");
    var save_data = {};

    if(nowStep == 3){
        var old_imgs = $("#old_imgs").val();
        if(old_imgs == photo_str){
            JqUI.unblockUI(body_obj);
            EPC.confirmFuc("您没有更改任意认证图片,点击确定 放弃更改，跳转到首页~",function(){
                window.location.href = "/home";
            });
            return false;
        }
        //仅更新图片
        save_data = {imgs:photo_str};
    }else{
        save_data = {
            userTitle:$("#userTitle").val(),
            businessLicence:$("#businessLicence").val(),
            contact:$("#contactName").val(),
            province:$("#province").val(),
            city:$("#city").val(),
            district:$("#district").val(),
            street:$("#street").val(),
            address:$("#address").val(),
            saleMobile:$("#saleMobile").val(),
            imgs:photo_str
        };
    }

    Ajax.post({
        url:"/verifyShop/startVerify",
        data:save_data,
        success:function(result){
            JqUI.unblockUI(body_obj);
            if(result.success){
                var alert_str = "申请认证成功，请耐心等待结果~~即将跳转到首页";
                if(nowStep == 3){
                    alert_str = "更新认证的图片成功，请耐心等待结果~~即将跳转到首页";
                }
                EPC.alertFuc(alert_str);
                setTimeout(function(){
                    window.location.href = "/home";
                },2000);
            }else{
                EPC.alertFuc(result.message);
            }
        }
    })
}

 /*=====check 输入项=====*/
function check_step_2(){
    var result = true;
    $.each($(".must_input"),function(i,the_obj){
       var the_val = $(the_obj).val().trim();
        if(the_val == ""){
            $(the_obj).focus();
            TipUtil.tooltipFun($(the_obj), '不能为空，请补充完全');
            result = false;
            return false;
        }
        if($(the_obj).hasClass("select2me") && the_val == "0"){
            TipUtil.tooltipFun($(the_obj), '所在地区 请选择，不能为空');
            result = false;
            return false;
        }
    });
    return result;
}

function check_step_3(){
    var result = true;
    $.each($(".must_photo"),function(i,the_obj){
        var parent_obj = $(the_obj).parent();
        var default_val = parent_obj.data("img-url");
        var the_val = $(the_obj).attr("src");

        if(default_val == the_val){
            TipUtil.tooltipFun(parent_obj.siblings(".preview_btn"), '图片不能为示例图片，请选择正确图片');
            result = false;
            return false;
        }
    });
    return result;
}