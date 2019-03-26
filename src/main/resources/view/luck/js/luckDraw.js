layui.use(['form','layer','laydate','table','laytpl', 'share'],function(){
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        share = layui.share,
        table = layui.table;

    //用户列表
    var tableIns = table.render({
        elem: '#table',
        url : '/luck/luckDraw',
        cellMinWidth : 95,
        page : true,
        height : 'full-125',
        limit : 10,
        limits : [10,15,20,25],
        id : "table",
        cols : [[
            {type: 'checkbox', fixed:'left', width:50},
            {field: 'id', title: 'ID', width:60, align:'center'},
            {field: 'actCode', title: '活动代码',align:'center'},
            {field: 'actName', title: '活动名称', align:'center'},
            {field: 'drawCount', title: '抽奖次数 次/天', align:'center'},
            {field: 'maxCount', title: '每人最多抽中次数', align:'center'},
            {field: 'dayMaxCount', title: '每人每天最多抽中次数', align:'center'},
            {field: 'startTime', title: '活动开始时间', align:'center', templet: function (d) {
                    return d.startTime.substring(0,19);
                }},
            {field: 'endTime', title: '活动结束时间', align:'center', templet: function (d) {
                    return d.endTime.substring(0,19);
                }},
            {field: 'createTime', title: '创建时间', align:'center'},
            {title: '操作', width:170, templet:'#listBar',fixed:"right",align:"center"}
        ]]
    });


    //列表操作
    table.on('tool(table)', function(obj){
        var layEvent = obj.event,
            data = obj.data;

        if(layEvent === 'edit'){ //编辑
            addLuckDraw(data);
        } else if(layEvent === 'del'){ //删除
            layer.confirm('确定删除此条抽奖活动？',{icon:3, title:'提示信息'},function(index){
                share.ajax({
                    type: 'DELETE',
                    url: '/luck/luckDraw?id=' + data.id,
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
                actName: $('#s_actName').val()
            }
        })
    });

    $('#addLuckDraw_btn').on('click',function(){
        addLuckDraw();
    });


    //添加用户
    function addLuckDraw(edit){
        var index = layui.layer.open({
            title : '抽奖活动维护',
            type : 2,
            data: {
                edit: edit
            },
            content : 'luckDrawAdd.html',
            success: function(layero, index){
                var body = layui.layer.getChildFrame('body', index);
                if(edit){
                    body.find('#id').val(edit.id);  //id
                    body.find('#actCode').val(edit.actCode);
                    body.find('#actName').val(edit.actName);

                    body.find('#startTime').val(edit.startTime.substring(0,19));
                    body.find('#endTime').val(edit.endTime.substring(0,19));

                    body.find('#drawCount').val(edit.drawCount);
                    body.find('#maxCount').val(edit.maxCount);
                    body.find('#dayMaxCount').val(edit.dayMaxCount);

                    form.render();
                }

                setTimeout(function(){
                    layui.layer.tips('点击此处返回抽奖活动列表', '.layui-layer-setwin .layui-layer-close', {
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