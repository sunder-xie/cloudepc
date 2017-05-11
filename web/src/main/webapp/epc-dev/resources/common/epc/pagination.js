/**
 * Created by huangzhangting on 16/6/28.
 */

var JqPagination = {
    initPage: function(mp, cp, rows, doSomething){
        var string = "{current_page}/{max_page}";
        if(rows != null && rows != undefined){
            string = string + "(共" + rows + "条)";
        }
        //alert(cp);

        $('.pagination').jqPagination({
            current_page : cp, //不设置时 默认值1
            //link_string : '',
            max_page : mp,
            page_string : string,
            paged: function(page){
                //alert('paged '+page);
                doSomething(page);
                return false;
            }
        });
    },
    updatePage: function(maxPage, page, rows){
        var str = "{current_page}/{max_page}";
        if(rows != null && rows !== undefined){
            str = str + "(共" + rows + "条)";
        }
        //alert(str)
        var jqPage = $('.pagination').data('jqPagination');
        jqPage.options.page_string = str;

        if(maxPage<page){
            if(page!=1){
                page=page-1;
            }
            if((page - this.getCurrentPage()) != 0){
                jqPage.setPage(page, true);//第二个参数必须是true，否则会去调用回调函数
            }
            if(maxPage==0){
                maxPage =1;
            }
            jqPage.setMaxPage(maxPage, true);//必须有一个得更新，否则更新不了 page_string
        }else{
            if((page - this.getCurrentPage()) != 0){
                jqPage.setPage(page, true);//第二个参数必须是true，否则会去调用回调函数
            }
            jqPage.setMaxPage(maxPage, true);//必须有一个得更新，否则更新不了 page_string
        }

    },
    initCurrentPage: function(page){
        //设置当前页后会自动调用 paged 绑定的函数
        $('.pagination').jqPagination('option', 'current_page', page);
    },
    getCurrentPage: function(){
        return $('.pagination').jqPagination('option', 'current_page');
    }
};
