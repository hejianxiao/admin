layui.use(['form','layer','laydate','table','laytpl', 'share'],function(){
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        share = layui.share,
        table = layui.table;

    //用户列表
    var tableIns = table.render({
        elem: '#table',
        url : '/sys/user',
        cellMinWidth : 95,
        page : true,
        height : 'full-125',
        limit : 10,
        limits : [10,15,20,25],
        id : 'table',
        cols : [[
            {type: 'checkbox', fixed:'left', width:50},
            {field: 'id', title: 'ID', width:60, align:'center'},
            {field: 'username', title: '用户名',align:'center'},
            {field: 'mobile', title: '手机号', align:'center'},
            {field: 'realName', title: '姓名', align:'center'},
            {field: 'sex', title: '性别', align:'center', templet:'#userSex'},
            {field: 'status', title: '用户状态',  align:'center', templet: function (d) {

                    return '<input type="checkbox" '+ (d.status==='1' ? 'checked' : '') + ' value="' + d.id + '" name="status" lay-filter="status" lay-skin="switch" lay-text="开启|禁用">'
                }},
            {field: 'createTime', title: '创建时间', align:'center', minWidth:110, templet:function(d){
                return d.createTime.substring(0,10);
            }},
            {title: '操作', width:170, templet:'#listBar',fixed:"right",align:"center"}
        ]]
    });

    //用户启用禁用
    form.on('switch(status)', function(data){
        var index = layer.msg('修改中，请稍候',{icon: 16,time:false,shade:0.8});

        var status;
        var msg;
        if(data.elem.checked){
            status = '1';
            msg = '用户已启用！';
        }else{
            status = '0';
            msg = '用户已禁用！';
        }
        share.ajax({
            type: 'POST',
            url: '/sys/user/status',
            data: {
                id: data.elem.value,
                status: status
            },
            success: function (data) {
                layer.close(index);
                if (data.code === 200) {
                    layer.msg(msg);
                } else {
                    layer.msg(data.msg);
                }
            }
        });


    });

    //列表操作
    table.on('tool(table)', function(obj){
        var layEvent = obj.event,
            data = obj.data;

        if(layEvent === 'edit'){ //编辑
            addUser(data);
        } else if(layEvent === 'del'){ //删除
            layer.confirm('确定删除此用户？',{icon:3, title:'提示信息'},function(index){
                share.ajax({
                    type: 'DELETE',
                    url: '/sys/user?id=' + data.id,
                    success: function (data) {
                        layer.msg(data.msg);
                        if (data.code === 200) {
                            tableIns.reload();
                        }
                        layer.close(index);
                    }
                });
            });
        }
    });


    $('#search_btn').on('click',function(){
        table.reload('table', {
            page: {
                curr: 1 //重新从第 1 页开始
            },
            where: {
                realName: $('#s_realName').val(),
                mobile: $('#s_mobile').val()
            }
        })
    });

    $('#addUser_btn').on('click',function(){
        addUser();
    });


    //添加用户
    function addUser(edit){
        var index = layui.layer.open({
            title : '用户维护',
            type : 2,
            data: {
                edit: edit
            },
            content : 'userAdd.html',
            success: function(layero, index){
                var body = layui.layer.getChildFrame('body', index);
                if(edit){
                    body.find('#pwdDiv').remove();
                    body.find('#id').val(edit.id);  //id
                    body.find('#username').val(edit.username);  //用户名
                    body.find('.userSex input[value=' + edit.sex + ']').prop('checked', 'checked');  //性别
                    body.find('#mobile').val(edit.mobile);  //手机号
                    body.find('#realName').val(edit.realName);  //真实姓名
                    form.render();
                }

                setTimeout(function(){
                    layui.layer.tips('点击此处返回用户列表', '.layui-layer-setwin .layui-layer-close', {
                        tips: 3
                    });
                },500)
            }
        });
        layui.layer.full(index);
        window.sessionStorage.setItem('index', index);
        //改变窗口大小时，重置弹窗的宽高，防止超出可视区域（如F12调出debug的操作）
        $(window).on('resize',function(){
            layui.layer.full(window.sessionStorage.getItem('index'));
        })
    }
});