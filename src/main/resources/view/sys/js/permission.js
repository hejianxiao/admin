layui.use(['form','layer','table', 'tree', 'share'],function(){
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        share = layui.share,
        tree = layui.tree,
        table = layui.table;

    //权限列表
    var tableIns = table.render({
        elem: '#table',
        url : '/sys/permission',
        cellMinWidth : 95,
        page : true,
        height : 'full-125',
        limit : 10,
        limits : [10,15,20,25],
        id : 'table',
        where: {code:"0"},
        cols : [[
            {field: 'id', title: 'ID', width:60, align:'center'},
            {field: 'code', title: '权限编号',align:'center'},
            {field: 'name', title: '权限名称', align:'center'},
            {field: 'uri', title: 'uri', align:'center'},
            {field: 'num', title: '排序', align:'center'},
            {field: 'description', title: '描述', align:'center'},
            {title: '操作', width:170, templet:'#listBar',fixed:"right",align:"center"}
        ]]
    });

    //权限树
    share.ajax({
        type: 'GET',
        url: '/sys/permission/tree',
        async: false,
        success: function (data) {
            tree({
                elem: '#tree', //指定元素
                click: function(item){ //点击节点回调
                    if (item.final) {
                        addPermission(item);
                    } else {
                        table.reload('table', {
                            page: {
                                curr: 1 //重新从第 1 页开始
                            },
                            where: {
                                code: item.code
                            }
                        })
                    }
                },
                nodes: data
            });
        }
    });

    //列表操作
    table.on('tool(table)', function(obj){
        var layEvent = obj.event,
            data = obj.data;

        if(layEvent === 'edit'){ //编辑
            addPermission(data);
        } else if(layEvent === 'del'){ //删除
            layer.confirm('确定删除此权限？',{icon:3, title:'提示信息'},function(index){
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

    $('#addPermission_btn').on('click',function(){
        addPermission();
    });

    //添加权限
    function addPermission(edit){
        var index = layui.layer.open({
            title : '权限维护',
            type : 2,
            data: {
                edit: edit
            },
            content : 'permissionAdd.html',
            success: function(layero, index){
                var body = layui.layer.getChildFrame('body', index);
                if(edit){
                    body.find('#id').val(edit.id);  //id
                    body.find('#code').val(edit.code);
                    body.find('#name').val(edit.name);
                    body.find('#uri').val(edit.uri);
                    body.find('#num').val(edit.num);
                    body.find('#description').val(edit.description);
                    form.render();
                }

                setTimeout(function(){
                    layui.layer.tips('点击此处返回权限列表', '.layui-layer-setwin .layui-layer-close', {
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