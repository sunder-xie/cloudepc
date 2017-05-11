/**
 * Created by lyj on 16/8/2.
 * 公用js
 */

var LOP = function () {

    /**
     * 初始化select, 含有一条选项, 文本内容为text, val默认为0(也可传入其他值), 样式为select2
     * @param dom
     * @param text
     * @param val 可选参数, 默认值为0
     */
    var default_select = function (dom, text, val) {
        if (val == undefined) {
            val = 0;
        }

        $(dom).append('<option value="' + val + '">' + text + '</option>');
        $(dom).select2();
    };

    /**
     * 向dom追加option, 数据为data
     * @param dom
     * @param data
     * @param valAttr 可选参数, 默认值为id
     * @param textAttr 可选参数, 默认值为name
     */
    var append_option = function (dom, data, valAttr, textAttr) {
        if (valAttr == undefined) {
            valAttr = 'id';
        }
        if (textAttr == undefined) {
            textAttr = 'name';
        }

        var op;
        for (var i in data) {
            var textStr = data[i][textAttr];
            var valStr = data[i][valAttr];

            //业务代码
            if (data[i]['importInfo'] != undefined && '进口' == data[i]['importInfo']) {
                textStr = '进口 ' + data[i][textAttr];
                //textStr = '进口 ' + data[i]['model'] + data[i][textAttr];
            }

            op = $("<option></option>");
            op.val(valStr);
            op.text(textStr);
            $(dom).append(op);
        }
    };

    /**
     * 关闭弹框
     * @param dom
     */
    var closeAlert = function (dom) {
        if ($(dom) != undefined && $(dom) != null) {
            $(dom).modal('hide');
        }
    };

    return {
        default_select: default_select,
        append_option: append_option,
        closeAlert: function (dom) {
            closeAlert(dom);
        }
    }
}();