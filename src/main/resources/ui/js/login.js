layui.config({base: '/ui/js/'}).extend({md5:'md5'}); //引入md5扩展
layui.use(['form','layer','jquery','md5'],function(){
    var form = layui.form, md5 =layui.md5,
        layer = parent.layer === undefined ? layui.layer : top.layer;
        $ = layui.jquery;

    $(".loginBody .seraph").click(function(){
        layer.msg("这只是做个样式，至于功能，你见过哪个后台能这样登录的？还是老老实实的找管理员去注册吧",{
            time:5000
        });
    });

    //登录按钮
    form.on('submit(login)',function(){
        var _this = $(this);
        _this.text('登录中...').attr('disabled', true).addClass('layui-disabled');
        $('#password').val(md5.calcMD5($('#pwd').val()));
        var form = $("#loginForm").serializeArray();
        $.ajax({
            type: 'POST',
            url: '/login',
            data: form,
            success: function (data) {
                if (data.code === 200) {
                    location.href = 'index.html';
                } else {
                    layer.msg(data.msg,{
                        time:1000
                    });
                    _this.text('登录').attr('disabled', false).removeClass('layui-disabled');
                }
            }
        });
        return false;
    });

    //表单输入效果
    $(".loginBody .input-item").click(function(e){
        e.stopPropagation();
        $(this).addClass("layui-input-focus").find(".layui-input").focus();
    });
    $(".loginBody .layui-form-item .layui-input").focus(function(){
        $(this).parent().addClass("layui-input-focus");
    });
    $(".loginBody .layui-form-item .layui-input").blur(function(){
        $(this).parent().removeClass("layui-input-focus");
        if($(this).val() != ''){
            $(this).parent().addClass("layui-input-active");
        }else{
            $(this).parent().removeClass("layui-input-active");
        }
    });

    $('#kaptcha').click(function () {
        kaptcha();
    });

    init();
});

var init = function () {
    kaptcha();
};

function kaptcha() {
    $.ajax({
        type: 'POST',
        url: '/kaptcha?d=' + new Date(),
        success: function (data) {
            $('#kaptcha').attr('src', 'data:image/png;base64,' + data);
        }
    });
}
