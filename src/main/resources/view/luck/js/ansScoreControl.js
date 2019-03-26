layui.use(['form','layer','table', 'share'],function(){
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        share = layui.share,
        table = layui.table;

    var tableIns = table.render({
        elem: '#table',
        url : '/ans/control',
        cellMinWidth : 95,
        page : true,
        height : 'full-125',
        limit : 10,
        limits : [10,15,20,25],
        id : "table",
        cols : [[
            {type: 'checkbox', fixed:'left', width:50},
            {field: 'id', title: 'ID', width:60, align:'center'},
            {field: 'score', title: '分数',align:'center'},
            {field: 'drawName', title: '活动名称', align:'center'},
            {field: 'createTime', title: '创建时间', align:'center'},
            {title: '操作', width:170, templet:'#listBar',fixed:"right",align:"center"}
        ]]
    });


    //列表操作
    table.on('tool(table)', function(obj){
        var layEvent = obj.event,
            data = obj.data;

        if(layEvent === 'edit'){ //编辑
            addScoreControl(data);
        } else if(layEvent === 'del'){ //删除
            layer.confirm('确定删除此条分数控制记录？',{icon:3, title:'提示信息'},function(index){
                share.ajax({
                    type: 'DELETE',
                    url: '/ans/control?id=' + data.id,
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


    // $('#search_btn').on('click',function(){
    //     table.reload('table', {
    //         page: {
    //             curr: 1 //重新从第 1 页开始
    //         },
    //         where: {
    //             realName: $('#s_realName').val(),
    //             mobile: $('#s_mobile').val()
    //         }
    //     })
    // });

    $('#addScoreControl_btn').on('click',function(){
        addScoreControl();
    });


    //添加用户
    function addScoreControl(edit){
        var index = layui.layer.open({
            title : '抽奖活动维护',
            type : 2,
            data: {
                edit: edit
            },
            content : 'scoreControlAdd.html',
            success: function(layero, index){
                var body = layui.layer.getChildFrame('body', index);
                if(edit){
                    body.find('#id').val(edit.id);  //id
                    body.find('#score').val(edit.score);
                    form.render();
                }

                setTimeout(function(){
                    layui.layer.tips('点击此处返回分数控制列表', '.layui-layer-setwin .layui-layer-close', {
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