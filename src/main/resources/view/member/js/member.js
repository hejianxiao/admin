layui.use(['form','layer','laydate','table','laytpl', 'share'],function(){
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        share = layui.share,
        table = layui.table;

    //用户列表
    var tableIns = table.render({
        elem: '#table',
        url : '/member',
        cellMinWidth : 95,
        page : true,
        height : 'full-125',
        limit : 10,
        limits : [10,15,20,25],
        id : 'table',
        cols : [[
            {type: 'checkbox', fixed:'left', width:50},
            {field: 'id', title: 'ID', width:60, align:'center'},
            {field: 'openid', title: 'openid',align:'center'},
            {field: 'createTime', title: '创建时间', align:'center', minWidth:110, templet:function(d){
                    return d.createTime.substring(0,10);
                }}
        ]]
    });

    $('#search_btn').on('click',function(){
        table.reload('table', {
            page: {
                curr: 1 //重新从第 1 页开始
            },
            where: {
                id: $('#s_memberId').val()
            }
        })
    });

});