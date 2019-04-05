layui.use(['form','layer', 'share', 'formSelects', 'md5'],function(){
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        share = layui.share,
        md5 = layui.md5,
        formSelects = layui.formSelects;
        $ = layui.jquery;

    var args = parent.layer.methodConfig.data.edit;
    if (args) {
        share.selectInit(formSelects, 'dept', args.deptId);
    }
    formSelects.data('dept', 'server', {
        url: '/sys/dept'
    });
    formSelects.data('role', 'server', {
        url: '/sys/role/roleList' + (args ? '?userId=' + args.id : '')
    });

    form.on("submit(addUser)",function(){
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