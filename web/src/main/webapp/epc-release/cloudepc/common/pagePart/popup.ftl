<style type="text/css">
    .model-close {
        position: absolute;
        top: 8px;
        right: 8px;
        height: 22px;
        width: 22px;
        text-align: center;
        padding: 1px;
        border-radius: 16px !important;
        z-index: 1200;
    }

    .model-close > i {
        margin-top: 2px;
        font-size: 16px;
    }

    .modal-section {
        margin: 16px;
        background-color: #ffffff;
        border: 1px solid rgba(0, 0, 0, 0.2);
        border-radius: 6px;
        -webkit-box-shadow: 0 3px 9px rgba(0, 0, 0, 0.5);
        box-shadow: 0 3px 9px rgba(0, 0, 0, 0.5);
        background-clip: padding-box;
    }

    .modal-backdrop, .modal-backdrop.fade.in {
        background: #272323;
    }

    .close-btn {
        color: #FFF;
        background-color: #c63927;
    }

    .fa {
        display: inline;
    }
    .model-width{
        width: 400px!important;
    }
    .modal-body-set{
        overflow-x: hidden;
        font-size: 14px;
        padding-bottom: 0;
        padding-top: 25px!important;
    }
    .modal-footer_new{
        padding-top: 20px;
        padding-bottom: 22px;
    }
    .btn-danger-new,.btn-danger-new:hover, .btn-danger-new:focus, .btn-danger-new:active, .btn-danger-new.active {
        color: #fff;
        width: 90px;
        height: 30px;
        line-height: 18px;
        margin-right: 10px;
        background-color: #ff7800;
        border-color: #ff7800;
        border-radius: 2px!important;
    }
    .btn-close{
        color: #fff;
        width: 90px;
        height: 30px;
        line-height: 18px;
        border-radius: 2px!important;
    }
</style>

<div id="alert_model" class="modal fade modal-scroll" tabindex="-1" data-keyboard="false" aria-hidden="true">
    <a href="#" class="model-close btn" style="color:#FFF;background-color:#c63927" data-dismiss="modal" aria-hidden="true">
        <i class="fa fa-times"></i>
    </a>

    <div class="modal-section">
        <div class="modal-header">
            <h4 class="modal-title"></h4>
        </div>
        <div class="modal-body" style="overflow-x: hidden;">

        </div>
    </div>

</div>

<div id="make_sure_model" class="modal fade model-width" tabindex="-1" data-keyboard="false">
    <div class="modal-section" style="border-radius: 2px!important;">
        <div class="modal-body modal-body-set" >

        </div>
        <div class="modal-footer_new">
            <button type="button" data-dismiss="modal" class="btn btn-danger" id="sure_model_btn">确定</button>
            <button type="button" data-dismiss="modal" class="btn ">取消</button>
        </div>
    </div>
</div>

<div id="make_sure_model_new" class="modal fade model-width" tabindex="-1" data-keyboard="false">
    <div class="modal-section" style="border-radius: 2px!important;">
        <div class="modal-body modal-body-set">

        </div>
        <div class="modal-footer_new">
            <button type="button" data-dismiss="modal" class="btn btn-danger-new" id="sure_model_btn_new">确定</button>
            <button type="button" data-dismiss="modal" class="btn btn-close">取消</button>
        </div>
    </div>
</div>

