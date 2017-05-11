/**
 * Created by lyj on 16/8/1.
 * 对plupload的封装, 高可用高可配超简单
 * 缺少参数的话, 则用默认值
 * 插件学习地址: http://www.cnblogs.com/2050/p/3913184.html
 */

var UPLOAD = function () {
    //固定参数
    var runtimes = 'html5,flash,silverlight,html4';
    var flash_swf_url = '/static/upload/Moxie.swf';
    var silverlight_xap_url = '/static/upload/Moxie.xap';

    //可配置参数
    var browse_button = 'browse_button';
    var url = '/upload/uploadImage';
    var max_file_size = '10mb';
    var mime_types = [
        {
            title: "Image files", extensions: "jpg,png,jpeg,bmp"
        }
    ];

    return {
        //上传
        upload: function (config, callBackFunc) {
            var uploader_obj = new plupload.Uploader({
                runtimes: runtimes,
                flash_swf_url: flash_swf_url,
                silverlight_xap_url: silverlight_xap_url,

                browse_button: config['browse_button'] == undefined ? browse_button : config['browse_button'],
                url: config['url'] == undefined ? url : config['url'],
                //限制上传文件的类型，大小等，该参数以对象的形式传入
                filters: {
                    prevent_duplicates:"true",//是否允许选取重复的文件，为true时表示不允许，为false时表示允许，默认为false
                    max_file_size: config['max_file_size'] == undefined ? max_file_size : config['max_file_size'],
                    mime_types: config['mime_types'] == undefined ? mime_types : config['mime_types']
                },

                //multipart_params:附加参数.上传时的附加参数，以键/值对的形式传入
                multipart_params:config['multipart_params'] == undefined ? "" : config['multipart_params'],
                init: {
                    //当Init事件发生后触发
                    PostInit: function () {

                        if (callBackFunc && callBackFunc['PostInit'] != undefined) {
                            callBackFunc['PostInit']();
                        }
                    },

                    //当文件添加到上传队列后触发
                    FilesAdded: function (up, files) {
                        if (callBackFunc && callBackFunc['FilesAdded'] != undefined) {
                            callBackFunc['FilesAdded'](up, files);
                        }
                    },

                    //当队列中的某一个文件上传完成后触发
                    FileUploaded: function (up, files, url) {
                        if (callBackFunc && callBackFunc['FileUploaded'] != undefined) {
                            callBackFunc['FileUploaded'](up, files, url, config);
                        }
                    },
                    //当上传队列中所有文件都上传完成后触发
                    UploadComplete: function (up, files) {
                        if (callBackFunc && callBackFunc['UploadComplete'] != undefined) {
                            callBackFunc['UploadComplete'](up, files);
                        }
                    },
                    //会在文件上传过程中不断触发，可以用此事件来显示上传进度
                    UploadProgress: function (up, file) {
                        if (callBackFunc && callBackFunc['UploadProgress'] != undefined) {
                            callBackFunc['UploadProgress'](up, file);
                        }
                    },
                    //发生错误时触发
                    Error: function (up, err) {
                        if (callBackFunc && callBackFunc['Error'] != undefined) {
                            callBackFunc['Error'](up, err);
                        }
                    }
                }
            });

            uploader_obj.init();

            return uploader_obj;
        }
    }
}();