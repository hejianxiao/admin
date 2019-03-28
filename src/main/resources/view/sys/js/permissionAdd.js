layui.use(['form','layer', 'share', 'formSelects'],function(){
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        share = layui.share,
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

        var form = $('#form').serializeArray();
        share.ajax({
            type: 'POST',
            url: '/sys/permission',
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