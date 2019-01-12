$(function(){
    var shopId = getQueryString("shopId");
    var isEdit = (shopId == '') ? false : true;

    var initUrl = '/o2owork/shopadmin/getshopinitinfo';
    var registerShopUrl = '/o2owork/shopadmin/registershop';
    var shopInfoUrl = '/o2owork/shopadmin/getshopbyid?shopId=' +  shopId;
    var editShopUrl = '/o2owork/shopadmin/modifyshop';
    if(!isEdit){
        getShopInitInfo();
    }else {
        getShopInfo(shopId);
    }

    function getShopInfo(shopId) {
        $.getJSON(shopInfoUrl,function (data) {
           if(data.success){
               var shop = data.shop;
               $('#shop-name').val(shop.shopName);
               $('#shop-addr').val(shop.shopAddr);
               $('#shop-phone').val(shop.phone);
               $('#shop-desc').val(shop.shopDesc);
               var shopCategory = '<option data-id="'
                   + shop.shopCategory.shopCategoryId
                   + '" selected>'
                   + shop.shopCategory.shopCategoryName + '</option>';
               var tempAreaHtml = '';
               data.areaList.map(function (item, index) {
                   tempAreaHtml += '<option data-id="' + item.areaId + '">'
                   + item.areaName + '</option>';
               });
               $('#shop-category').html(shopCategory);
               $('#shop-category').attr('disabled','disabled');
               $('#area').html(tempAreaHtml);
               $('#area option[data-id= "' + shop.area.areaId+'"]').attr("selected","selected");

           }
        });
    }

    $("#submit").click(function () {
        var shop = {};
        if(isEdit){
            shop.shopId = shopId;
        }
        shop.shopName =  $('#shop-name').val();
        shop.shopAddr = $('#shop-addr').val();
        shop.shopDesc = $('#shop-desc').val();
        shop.phone = $('#shop-phone').val();
        shop.shopCategory = {
            shopCategoryId : $('#shop-category').find('option').not(function () {
                return !this.selected;
            }).data('id')
        };
        shop.area = {
            areaId : $('#area').find('option').not(function () {
                return !this.selected;
            }).data('id')
        };
        var shopImg = $('#shop-img')[0].files[0];
        var formDate = new FormData();
        formDate.append('shopImg',shopImg);
        formDate.append('shopStr',JSON.stringify(shop));
        var verifyCodeActual = $('#j_captcha').val();
        if(!verifyCodeActual){
            $.toast('请输入验证码！');
            return;
        }
        formDate.append('verifyCodeActual',verifyCodeActual);
        $.ajax({
            url : (isEdit?editShopUrl:registerShopUrl),
            type : 'POST',
            contentType : false,
            data : formDate,
            processData : false,
            cache : false,
            success : function (data) {
                if(data.success){
                    $.toast("提交成功！");
                } else {
                    $.toast("提交失败！" + data.errMsg );
                }
                $("#catpcha_img").click();
            }
        });
    });

    function getShopInitInfo() {
        $.getJSON(initUrl,function (data) {
            if (data.success) {
                var tempHtml = '';
                var tempAreaHtml = '';
                data.shopCategoryList.map(function (value, index) {
                    tempHtml += '<option data-id="' + value.shopCategoryId +'">' + value.shopCategoryName + '</option>';
                });
                data.areaList.map(function (value, index) {
                    tempAreaHtml += '<option data-id="' + value.areaId + '">' + value.areaName + '</option>';
                });
                $("#shop-category").html(tempHtml);
                $("#area").html(tempAreaHtml);
            }
        });
    }
});