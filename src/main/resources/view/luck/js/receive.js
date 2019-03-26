layui.use(['form'],function(){
    var $ = layui.jquery;
    var receive = parent.layer.methodConfig.data.receive;
    var _html = '';
    $.each(receive, function (i, ele) {
        _html += '<div class="layui-form-item">' +
                    '<label class="layui-form-label">'+ ele.receiveName +'</label>' +
                    '<div class="layui-input-block">' +
                        '<input type="text" class="layui-input" disabled="disabled" readonly="readonly"  value="'+ ele.receiveValue +'"/>' +
                    '</div>' +
                 '</div>';
    });

    $('#infoList').html(_html);
});