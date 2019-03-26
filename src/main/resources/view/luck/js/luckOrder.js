layui.use(['form','layer','laydate','table','laytpl', 'share'],function(){
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        share = layui.share,
        table = layui.table;

    //用户列表
    var tableIns = table.render({
        elem: '#table',
        url : '/luck/luckOrder',
        cellMinWidth : 95,
        page : true,
        height : 'full-125',
        limit : 10,
        limits : [10,15,20,25],
        id : "table",
        cols : [[
            {type: 'checkbox', fixed:'left', width:50},
            {field: 'id', title: 'ID', width:60, align:'center'},
            {field: 'memberId', title: '会员id', align:'center'},
            {field: 'actName', title: '活动名称',align:'center'},
            {field: 'prizeName', title: '奖品名称', align:'center'},
            {field: 'createTime', title: '创建时间', align:'center'},
            {title: '操作', width:170, templet:'#listBar',fixed:"right",align:"center"}
        ]]
    });


    //列表操作
    table.on('tool(table)', function(obj){
        var layEvent = obj.event,
            data = obj.data;

        if(layEvent === 'showReceive'){ //编辑
            showReceive(data);
        }
    });


    $('#search_btn').on('click',function(){
        table.reload('table', {
            page: {
                curr: 1 //重新从第 1 页开始
            },
            where: {
                actName: $('#s_actName').val(),
                memberId: $('#s_memberId').val()
            }
        })
    });

    function showReceive(edit){
        share.ajax({
            type: 'GET',
            async: false,
            url: '/luck/luckOrder/' + edit.id,
            success: function (data) {
                if (data.code === 200) {
                    var index = layer.open({
                        title : "领奖信息",
                        type : 2,
                        area : ["300px","355px"],
                        data: {
                            receive: data.data
                        },
                        content : "luck/receive.html",
                        success : function(layero, index){
                            // var body = $($(".layui-layer-iframe",parent.document).find("iframe")[0].contentWindow.document.body);
                            // if(edit){
                            //     body.find(".linkLogo").css("background","#fff");
                            //     body.find(".linkLogoImg").attr("src",edit.logo);
                            //     body.find(".linkName").val(edit.websiteName);
                            //     body.find(".linkUrl").val(edit.websiteUrl);
                            //     body.find(".masterEmail").val(edit.masterEmail);
                            //     body.find(".showAddress").prop("checked",edit.showAddress);
                            //     form.render();
                            // }
                            setTimeout(function(){
                                layui.layer.tips('点击此处返回抽奖记录列表', '.layui-layer-setwin .layui-layer-close', {
                                    tips: 3
                                });
                            },500)
                        }
                    })
                } else {
                    layer.msg('会员暂未填写领奖信息~', {
                        time:1000
                    });
                }
            }
        });
    }


});