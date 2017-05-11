<script type="text/html" id="brandLogoListTemplate">
    {{each dataList as brand b_idx}}
    <a href="#">
        <img src="{{brand.logo}}" alt="{{brand.name}}" style="cursor: default;width: auto;height: 50px;padding-left: 30px">
    </a>
    {{/each}}
</script>

<script type="text/html" id="carTypeHeaderTemplate">
    <li>
        <a href="#"><span onclick="firstWord(this)" id="-1">全部</span></a>
    </li>
    {{each data as brandList firstWord}}
    <li>
        <a href="#">
            <span onclick="firstWord(this)" title="{{firstWord}}" id="{{firstWord}}">{{firstWord}}</span>
        </a>
    </li>
    {{/each}}
</script>

<script type="text/html" id="carTypeBodyTemplate">
    {{each data as brandList firstWord}}
    {{each brandList as brand b_idx}}
    <table class="car-brand" >
        <tbody>
        <tr>
            <th class="brand-logo-th">
                {{if b_idx==0}}
                <div class="brand-first-word-div">
                    {{firstWord}}
                </div>
                {{/if}}
                <div class="brand-logo-div">
                    <img src="{{brand.logo}}" alt="{{brand.name}}">
                    <h4>{{brand.name}}</h4>
                </div>
            </th>
            <td class="border-bottom width-100">
                <ul class="car-list ul-style">
                    {{each brand.customAttr as seriesList company}}
                    <li class="float-none">
                        <h4 title="{{company}}">{{company}}</h4>
                        <div>
                            {{each seriesList as series s_idx}}
                            <span title="{{series.name}}" data-id="{{series.id}}" class="series_name_span">
                                {{series.name}}
                            </span>
                            <label {{if seriesList.length==(s_idx+1)}} class="hidden" {{/if}}>|</label>
                            {{/each}}
                        </div>
                    </li>
                    {{/each}}
                </ul>
            </td>
        </tr>
    </table>
    {{/each}}
    {{/each}}
</script>

<script type="text/html" id="carInfoHeaderTemplate">
    <span id="carInfoHeader">
        车系
        &nbsp;&nbsp;>&nbsp;&nbsp;
        {{company}}
        &nbsp;&nbsp;>&nbsp;&nbsp;
        {{series}}
    </span>
</script>

<script type="text/html" id="carStyleTemplate">
    <span id="car-type-select">车&nbsp;&nbsp;&nbsp;型</span>
    <div class="car-param-select">
        <div id="changeCarModel" class="margin-left-15">
            {{each data as centerCar idx}}
            <li class="car-list-li">
                <span onclick="changeCarModel(this)" title="{{centerCar.id}}">{{centerCar.name}}</span>
            </li>
            {{/each}}
        </div>
    </div>
</script>

<script type="text/html" id="carYearTemplate">
    <span id="car-type-select">年&nbsp;&nbsp;&nbsp;款</span>
    <div class="car-param-select">
        <div id="changeCarYear" class="margin-left-15" >
            {{each data}}
            <li class="car-list-li">
                <span onclick="changeCarYear(this)">{{$index}}</span>
            </li>
            {{/each}}
        </div>
    </div>
</script>

<script type="text/html" id="carPowerTemplate">
    <span id="car-type-select">排&nbsp;&nbsp;&nbsp;量</span>
    <div class="car-param-select">
        <div id="changeCarPower" class="margin-left-15">
            {{each data}}
            <li class="car-list-li">
                <span onclick="changeCarPower(this)">{{$value}}</span>
            </li>
            {{/each}}
        </div>
    </div>
</script>

<script type="text/html" id="carChoiceTemplate">
    <div id="car-choice-right">
        <div id="choiceCarCompare">
            {{each carList as centerCar idx}}
            <li class="car-list-li  choiceCar-li">
                <span onclick="carInfoSearch(this)" id="{{centerCar.id}}">{{centerCar.name}}</span>
            </li>
            {{/each}}
        </div>
    </div>
</script>

<script type="text/html" id="carTypeListTemplate">
    <h4>以下为可能车款:</h4>
    <table width="90%" border="1" cellspacing="0" class="car-type-list-table">
        <tbody>
        <tr>
            <th style="width: 20%;">序号</th>
            <th style="width: 70%;">车型</th>
        </tr>
        {{each carList as centerCar idx}}
        <tr class="table-car-list" onclick="carInfoSearch(this)" title="{{centerCar.id}}">
            <td>{{idx+1}}</td>
            <td>{{centerCar.name}}</td>
        </tr>
        {{/each}}
        </tbody>
    </table>
</script>

<script type="text/html" id="carParamListTemplate">

</script>

<script type="text/html" id="firstListTemplate">
    <ul>
        {{each firstList as firstFixing idx}}
        <li onclick="firstList(this)" id="{{firstFixing.catId}}">
            <span>{{firstFixing.catName}}</span>
        </li>
        {{/each}}
    </ul>
</script>

<script type="text/html" id="secondListTemplate">
    <ul>
        {{each secondList as secondFixing idx}}
        <li onclick="secondList(this)" class="float-none" id="{{secondFixing.catId}}" data-title="{{secondFixing.catId}}">
            <span>{{secondFixing.catName}}</span>
        </li>
        {{/each}}
    </ul>
</script>

<script type="text/html" id="thirdListFirstWordTemplate">
    <div id="thirdList-up">
        <ul>
            <li class="thirdList-up-li float-left" id="-1" onclick="choiceThirdListCat(this)">全部</li>
            {{each thirdMap}}
            <li class="thirdList-up-li" id="{{$index}}" onclick="choiceThirdListCat(this)">{{$index}}</li>
            {{/each}}
        </ul>
    </div>
</script>

<script type="text/html" id="thirdListTemplate">
    <div id="thirdList-down">
        <ul id="choiceCat" class="ul-style">
            {{each thirdMap as value key}}
            {{each value as cat idx}}
            <li onclick="choiceCat(this)" id="{{cat.catId}}" data-title="{{cat.catId}}">
                <span>{{cat.catName}}</span></li>
            {{/each}}
            {{/each}}
        </ul>
    </div>
</script>

<script type="text/html" id="oeListTemplate">
    <table class="goods-table" border="1" id="oeTable">
        <tbody>
        <tr>
            <th style="width: 10%;">序号</th>
            <th style="width: 35%;">配件OE码</th>
            <th style="width: 40%;">配件名称</th>
            <th style="width: 15%;">操作</th>
        </tr>
        {{each dataList as obj idx}}
        <tr onclick="choicePartOe(this)" class="goods-table-tr" id="{{obj.goodsId}}" value="{{obj.oeNumber}}" title="{{obj.remarks}}"
            data-id="{{obj.epcPic}}">
            <td>{{idx+1}}</td>
            <td>{{obj.oeNumber}}</td>
            <td>{{obj.partName}}</td>
            <td><a href="javascript: enterPartPage(this)" class="show-goods-detail">查看详情</a></td>
        </tr>
        {{/each}}
        </tbody>
    </table>
</script>

<script type="text/html" id="remarksTemplate">
    <span>配件备注<span class="goodsDetail-style">{{remarks}}</span><a href="#" class="font-decoration  display-none">纠错</a></span>
    <p style="margin: 4px 0px 4px;" class="text-align-left ">
        <span>配图编号<span class="goodsDetail-style">{{epcPicNum}}</span><a href="#" class="font-decoration display-none">上传图片</a>
    </p>
</script>


<script type="text/html" id="pictureListTemplate">
    {{each dataList}}
    <img class="small-pic {{if $index==0}} picture-border {{/if}}" src="{{$value}}" onclick="changePicture(this)"
         onerror="javascript:this.src='/img/no-pic-big.png';this.onerror=null;">
    {{/each}}
</script>

<script type="text/html" id="carModelTemplate">
    {{each brandMap as companyMap brand}}
    <div class="car-brand-div">
        <table>
            <tr>
                <td>
                    <div>
                        <img src="{{brandLogoMap[brand]}}" class="oe-carModel-head">
                    </div>
                    <div style="padding: 0 0 10px 0;">
                        {{brand}}
                    </div>
                </td>
                <td>
                    <ul class="ul-style car-model-ul">
                        {{each companyMap as carModels company}}
                        <li>
                            <h>{{company}}</h>
                            <div class="car-model-div">
                                {{each carModels}}
                                <span class="car-model-span {{if modelId==$value.id}} car-model-selected {{/if}}"
                                      data-id="{{$value.id}}" data-name="{{$value.carName}}" onclick="selectCarModel(this)" >
                                    {{$value.carName}}
                                </span>
                                <label {{if carModels.length==($index+1)}} class="hidden" {{/if}}>|</label>
                                {{/each}}
                            </div>
                        </li>
                        {{/each}}
                    </ul>
                </td>
            </tr>
        </table>
    </div>
    {{/each}}
</script>

<script type="text/html" id="carTemplate">
    <ul class="ul-style">
        {{each carList as car idx}}
        <li onclick="selectCar(this)" data-id="{{car.id}}" class="car-li">
            {{car.name}}
        </li>
        {{/each}}
    </ul>
</script>


<script type="text/html" id="goodsSearchTemplate">
    {{each dataList as obj idx}}
    <tr class="goods-tr-style" onclick="choiceOe(this)"
        data-id="{{obj.id}}">
        <td>{{idx+1}}</td>
        <td>{{obj.oeNumber}}</td>
        <td>{{obj.partName}}</td>
        <td id="goodsCarRemark{{obj.id}}" class="goods-car-remark"></td>
        <td><a href="javascript: enterOeInfo('{{obj.id}}')" class="show-goods-detail">查看详情</a></td>
    </tr>
    {{/each}}
</script>

<script type="text/html" id="carNameTemplate">
    {{each dataList as obj idx}}
    <span class="choose_span" id="{{obj.id}}" onclick="selectCarModel(this)">{{obj.carName}}</span>
    {{/each}}
</script>

<script id="choose_car_template" type="text/html">
    <div style="border: 1px solid #d6e5ec;border-radius: 5px;">
        {{each list as carBO i}}
        <div data-id="{{carBO.id}}" data-sid="{{carBO.seriesId}}" class="choose_car"
             style="text-align: left;padding: 15px;{{if list.length != (i+1)}}border-bottom: 1px solid #d6e5ec;{{/if}}" >
            <i class="fa fa-car"></i>
            <span>{{carBO.carName}}</span>
        </div>
        {{/each}}
    </div>
</script>


<script type="text/html" id="descriptionTemplate">
    <tr>
        <td>
            配件备注<span>{{remarks}}</span>
        </td>
        <td>配件用量<span>{{amountUsed}}</span></td>
    </tr>
    <tr>
        <td>配图编号<span>{{epcPicNum}}</span></td>
        <td>位置编号<span>{{epcIndex}}</span></td>
    </tr>
</script>

<script type="text/html" id="goodsDetailTemplate">
    <tr>
        <td>配件OE码<span id="oeNumber" data-title="{{oeNumber}}">{{oeNumber}}</span></td>
        <td>配件名称<span id="partName" data-title="{{partName}}">{{partName}}</span></td>
    </tr>
    <tr>
        <td>
            配件备注<span title="{{remarks}}">{{remarks}}</span>
        </td>
        <td>配件用量<span>{{amountUsed}}</span></td>
    </tr>
    <tr>
        <td>配图编号<span>{{epcPicNum}}</span></td>
        <td>位置编号<span>{{epcIndex}}</span></td>
    </tr>
</script>

<script type="text/html" id="goodsSearchNoDataTemplate">
    <div class="no-data-div">
        <span class="no-data-tip">很抱歉，没有找到满足条件的配件</span>
    </div>
</script>

<script type="text/html" id="brandSelectTemplate">
    <option value="-1">选择品牌</option>
    {{each data}}
    <option value="{{$index}}">{{$index}}</option>
    {{/each}}
</script>

<script type="text/html" id="modelSelectTemplate">
    <option value="-1">选择车型</option>
    {{each dataList as model idx}}
    <option value="{{model.id}}">{{model.name}}</option>
    {{/each}}
</script>

<script type="text/html" id="cateSelectTemplate">
    <option value="-1">选择分类</option>
    {{each dataList as cate idx}}
    <option value="{{cate.id}}">{{cate.catName}}</option>
    {{/each}}
</script>

<script type="text/html" id="offerInfoTemplate">
    {{each data as obj idx}}
    <div class="offering-div">
        <div class="offering-company">{{obj.hiddenCompanyName}}</div>
        <div id="offeringTable">
            <table class="detail-table">
                <tbody>
                <tr>
                    <td>品质:<span>{{obj.goodsQualityTypeName}}</span></td>
                    <td>品牌:<span>{{obj.brandName}}</span></td>
                </tr>
                <tr>
                    <td>零售价:<span style="color: #ff7800;">{{if obj.price == null}}会员可见{{else}}￥{{obj.price | numberFormatHelper}}{{/if}}</span></td>
                    <td>运费:<span>与商家协商</span></td>
                </tr>
                <tr>
                    <td>库存:<span>有货</span></td>
                    <td>
                        <div class="count">数量:</div>
                        <div class="countBox">
                            <input type="button" class="countCon1 countCon1-extend"  value="-" title="-1" onclick="changeNum(this,-1)">
                            <input type="text"  class="countCon3" value="1" oninput="changeInputNum(this)">
                            <input type="button" class="countCon2" value="+" title="+1" onclick="changeNum(this,1)">
                        </div>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
        <div>
            <button class="buy-btn join-cart-bt btn-color-red" data-id="{{obj.id}}">加入购物车</button>
            <button class="buy-btn buy-now-bt btn-color-blue" data-id="{{obj.id}}">立即购买</button>
            <button class="buy-btn contact-seller-bt btn-color-green" data-orgid="{{obj.thirdPartId}}">联系商家</button>
        </div>
    </div>
    {{/each}}
</script>
<script type="text/html" id="chooseQualityTemplate">
    <select id="qualitySelect" class="choose-quality" onchange="offeringFilter(this)">
        <option value="0" selected="selected">不限品质</option>
        {{each dataList as quality index}}
        <option value="{{quality.id}}">{{quality.name}}</option>
        {{/each}}
    </select>
</script>
<script type="text/html" id="cartTipContentTemplate">
    <div class="cart-content-height1">
        {{each productList as product idx}}
        <div style="border-bottom: 1px solid #d6e5ec;height: 40px;">
            <table class="cart-table">
                <tbody>
                <tr>
                    <td>{{product.goodsName}}</td>
                    <td>￥{{product.price}}&nbsp;&nbsp;X&nbsp;&nbsp;{{product.num}}</td>
                    <td>
                        <a href="#" class="model-close btn model-close-a" onclick="removeGoods({{product.id}})">
                            <i class="fa fa-times"></i>
                        </a>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
        {{/each}}

    </div>
    <div  class="cart-content-height2">
        <button class="view-cart-btn">详&nbsp;细</button>
        <div class="cart-content-table">
            <table class="detail-table">
                <tbody>
                <tr>
                    <td>总商品数:</td>
                    <td>{{totalNumber}}</td>
                </tr>
                <tr>
                    <td>总金额:</td>
                    <td>￥{{totalAmount}}</td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
</script>


<script type="text/html" id="picGoodsTemplate">
    {{each data as obj idx}}
    <tr class="goods-tr-style" data-id="{{obj.goodsId}}" data-title="{{obj.oeNumber}}" data-name="{{obj.partName}}">
        <td>{{obj.oeNumber}}</td>
        <td>{{obj.partName}}</td>
        <td>{{obj.epcIndex}}</td>
    </tr>
    {{/each}}
</script>

<script type="text/html" id="loginFormTemplate">
    <h4 class="form-title">账户登录</h4>
    <div class="form-group">
        <#--<label class="control-label visible-ie8 visible-ie9">手机号</label>-->
        <div class="input-icon">
            <i class="fa fa-user"></i>
            <input class="form-control" type="text" placeholder="请填写手机号" id="userNameInput">
        </div>
    </div>
    <div class="form-group">
        <#--<label class="control-label visible-ie8 visible-ie9">验证码</label>-->
        <div class="input-icon">
            <i class="fa fa-lock"></i>
            <input class="form-control verify-code" type="text" placeholder="请填写验证码" id="verifyCodeInput">
            <button type="button" class="btn green verify-code-bt">获取验证码</button>
        </div>
    </div>
    <div class="form-actions">
        <button id="loginButton" type="button" class="btn red">登录</button>
    </div>
</script>

<script type="text/html" id="authBtnTemplate">
    <div class="margin-top-20">
        <a class="login-btn-style" href="#" target="_blank">请先认证</a>
    </div>
</script>
<script type="text/html" id="loginBtnTemplate">
    <div class="btn login-btn-style margin-top-20">请先登录</div>
</script>

