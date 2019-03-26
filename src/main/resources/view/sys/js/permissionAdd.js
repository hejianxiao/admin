layui.use(['form','layer', 'share', 'formSelects', 'md5'],function(){
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        share = layui.share,
        md5 = layui.md5,
        formSelects = layui.formSelects;
    $ = layui.jquery;


    var args = parent.layer.methodConfig.data.edit;
    if (args) {
        share.selectInit(formSelects, 'icon', args.icon);
    }
    formSelects.data('pcode', 'server', {
        url: '/sys/permission/selectTree',
        data: {
            code: args && args.code,
            pcode: args && args.pcode
        }
    });

    formSelects.data('icon', 'server', {
        url: '/ui/json/icon.json'
    });
    formSelects.render('icon', {
        template: function (name, vals) {
            return '<span class="layui-icon">&#xe' + vals.value + ';</span>&nbsp;' + vals.name;
        }
    });

    form.on("submit(addPermission)",function(){
        //弹出loading
        var index = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});

        var pwd = $('#password');
        if (pwd.length > 0) {
            pwd.val(md5.calcMD5($('#pwd').val()));
        }
        var form = $('#form').serializeArray();
        share.ajax({
            type: 'POST',
            url: '/sys/user',
            data: form,
            success: function (data) {
                top.layer.close(index);
                top.layer.msg(data.msg);
                if (data.code === 200) {
                    layer.closeAll("iframe");
                    //刷新父页面
                    parent.location.reload();
                }
            }
        });

        return false;
    });

});