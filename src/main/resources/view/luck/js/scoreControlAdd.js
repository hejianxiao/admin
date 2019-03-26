layui.use(['form','layer', 'share', 'formSelects'],function(){
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        share = layui.share,
        formSelects = layui.formSelects;
    $ = layui.jquery;

    var args = parent.layer.methodConfig.data.edit;
    if (args) {
        share.selectInit(formSelects, 'luckDraw', args.luckDrawId);
    }
    formSelects.data('luckDraw', 'server', {
        url: '/luck/luckDraw/list'
    });


    form.on("submit(addScoreControl)",function(){
        var luckDraw = formSelects.value('luckDraw');
        if (luckDraw.length === 0) {
            layer.msg('请选择关联活动', {
                time:1000
            });
            return false;
        }

        //弹出loading
        var index = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});

        var form = $('#form').serializeArray();
        share.ajax({
            type: 'POST',
            url: '/ans/control',
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