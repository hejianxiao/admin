var prizeType, formSelects, upload;
layui.use(['form','layer', 'share', 'formSelects', 'upload', 'laydate'],function(){
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        share = layui.share,
        laydate = layui.laydate;
        upload = layui.upload;
        formSelects = layui.formSelects;
    $ = layui.jquery;


    var args = parent.layer.methodConfig.data.edit;
    if (args) {
        share.ajax({
            type: 'GET',
            url: '/luck/luckDraw/' + args.id,
            success: function (data) {
                if (data.code === 200) {
                    var _data = data.data;
                    var tbody = $('#tbody');
                    var _trHtml = '';
                    $.each(_data, function (i, ele) {
                        _trHtml += loadPrize(ele);
                    });
                    tbody.html(_trHtml);
                }
            }
        });

        if (args.receiveInfo) {
            var _info = args.receiveInfo.split(',');
            $.each(_info, function (i, ele) {
                var _checkbox = $('input[type="checkbox"][value="'+ ele +'"]');
                if (_checkbox.length > 0) {
                    _checkbox.attr('checked', true);
                } else {
                    var custom1 = $('#custom1');
                    if (custom1.val() === '') {
                        custom1.val(ele);
                    } else {
                        var custom2 = $('#custom2');
                        custom2.val(ele);
                    }
                }
            });
            form.render();
        }
    }

    //远程加载一次奖品类型
    share.ajax({
        type: 'GET',
        url: '/ui/json/prizeType.json',
        success: function (data) {
            prizeType = data;
        }
    });

    laydate.render({
        elem: '#startTime',
        type: 'datetime'
    });
    laydate.render({
        elem: '#endTime',
        type: 'datetime'
    });

    form.on("submit(addLuckDraw)",function(){
        if ($('#addTr').length > 0) {
            if (!verifyAddPrize()) {
                return false;
            }
            layer.msg('已经展开一个维护行了，请先填写~', {
                time:1000
            });
            return false;
        }

        var _tr = $('#tbody').children('tr');
        if (_tr.length < 6) {
            layer.msg('奖品数量至少维护6个', {
                time:1000
            });
            return false;
        }
        if (_tr.length > 10) {
            layer.msg('奖品数量不能超过10个', {
                time:1000
            });
            return false;
        }

        var _prizeData = [];

        $.each(_tr, function () {
            var _prize = {};
            _prize.prizePic = $(this).children('td:eq(0)').find('img').attr('src');
            _prize.prizeName = $(this).children('td:eq(1)').text();
            _prize.prizeType = $(this).children('td:eq(2)').attr('id');
            _prize.prizeAmount = $(this).children('td:eq(3)').text();
            _prize.prizeWeight = $(this).children('td:eq(4)').text().replace('%', '');
            _prizeData.push(_prize);
        });


        //弹出loading
        var index = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});

        var form = $('#form').serializeArray();

        var jsonPrizes = JSON.stringify(_prizeData);
        var _luckPrize = {};
        _luckPrize.name = "luckPrizes";
        _luckPrize.value = jsonPrizes;
        form.push(_luckPrize);

        var custom1 = $.trim($('#custom1').val());
        var custom2 = $.trim($('#custom2').val());
        if (custom1 !== '') {
            form.push({name:'receiveInfo', value: custom1});
        }
        if (custom2 !== '') {
            form.push({name:'receiveInfo', value: custom2});
        }

        share.ajax({
            type: 'POST',
            url: '/luck/luckDraw',
            data: form,
            success: function (data) {
                top.layer.close(index);
                top.layer.msg(data.msg);
                if (data.code === 200) {
                    layer.closeAll("iframe");
                    parent.location.reload();
                }
            }
        });
        return false;
    });




    $('#addPrize').click(function () {
        var trs = $('#tbody').children('tr').not('#addTr');
        if (trs.length >= 10) {
            layer.msg('最多添加10个奖品~', {
                time:1000
            });
            return;
        }

        if ($('#addTr').length > 0) {
            layer.msg('已经展开一个维护行了，请先填写~', {
                time:1000
            });
            return;
        }
        var _trHtml = '<tr id="addTr">' +
            '<td><div class="layui-upload-list" id="prizeDiv"><img class="layui-upload-img" src="/ui/images/prizeIcon.png" id="prizeImg"></div></td>' +
            '<td><input type="text" class="layui-input" lay-verify="required" placeholder="奖品名称" id="prizeName"></td>' +
            '<td><select xm-select="prizeType" xm-select-radio=""></select></td>' +
            '<td><input type="text" class="layui-input" lay-verify="number" placeholder="奖品数量" id="prizeAmount"></td>' +
            '<td><input type="text" class="layui-input" lay-verify="number" placeholder="中奖概率" id="prizeWeight"></td>' +
            '<td><a class="layui-btn layui-btn-sm" onclick="surePrize($(this))">确定</a><a class="layui-btn layui-btn-sm" onclick="cancelPrize($(this), 0)">取消</a></td>' +
            '</tr>';
        $('#tbody').append(_trHtml);
        upLoadRender(upload);
        formSelectRender(formSelects);
    });


    function loadPrize(data) {
        var prizeObj = prizeType.find(function (x) {
            return x.value === data.prizeType;
        });
        return '<tr>' +
        '<td><img class="layui-upload-img" src="' + data.prizePic + '"></td>' +
        '<td>' + data.prizeName + '</td>' +
        '<td id="' + data.prizeType + '">' + prizeObj.name + '</td>' +
        '<td>' + data.prizeAmount + '</td>' +
        '<td>' + (data.prizeWeight * 100) + '%</td>' +
        '<td><a class="layui-btn layui-btn-sm layui-bg-orange" onclick="updPrize($(this))">修改</a><a class="layui-btn layui-btn-sm layui-bg-red" onclick="delPrize($(this))">删除</a></td>' +
        '</tr>';
    }
});

function surePrize(node, flag) {
    if (verifyAddPrize()) {
        var prizeType = formSelects.value('prizeType')[0];
        var _tr = node.parent().parent();
        var _trHtml =
            '<td><img class="layui-upload-img" src="' + $('#prizeImg').attr('src') + '"></td>' +
            '<td>' + $('#prizeName').val() + '</td>' +
            '<td id="' + prizeType.value + '">' + prizeType.name + '</td>' +
            '<td>' + $('#prizeAmount').val() + '</td>' +
            '<td>' + $('#prizeWeight').val() + '%</td>' +
            '<td><a class="layui-btn layui-btn-sm layui-bg-orange" onclick="updPrize($(this))">修改</a><a class="layui-btn layui-btn-sm layui-bg-red" onclick="delPrize($(this))">删除</a></td>';
        _tr.html(_trHtml);
        _tr.removeAttr('id');
        if (flag === 1) {
            _tr.prev().remove();
        }
    }
}

function cancelPrize(node, flag) {
    if (flag === 1) {
        node.parent().parent().prev().show();
    }
    node.parent().parent().remove();
}

function updPrize(node) {
    if ($('#addTr').length > 0) {
        layer.msg('已经展开一个维护行了，请先填写~', {
            time:1000
        });
        return;
    }
    var _tr = node.parent().parent();
    var _trHtml = '<tr id="addTr">' +
        '<td><div class="layui-upload-list" id="prizeDiv"><img class="layui-upload-img" src="'+ _tr.children('td:eq(0)').find('img').attr('src') +'" id="prizeImg"></div></td>' +
        '<td><input type="text" class="layui-input" lay-verify="required" placeholder="奖品名称" id="prizeName" value="'+ _tr.children('td:eq(1)').text() +'"></td>' +
        '<td><select xm-select="prizeType" xm-select-radio=""></select></td>' +
        '<td><input type="text" class="layui-input" lay-verify="number" placeholder="奖品数量" id="prizeAmount" value="'+ _tr.children('td:eq(3)').text() +'"></td>' +
        '<td><input type="text" class="layui-input" lay-verify="number" placeholder="中奖概率" id="prizeWeight" value="'+ _tr.children('td:eq(4)').text().replace('%', '') +'"></td>' +
        '<td><a class="layui-btn layui-btn-sm" onclick="surePrize($(this), 1)">确定</a><a class="layui-btn layui-btn-sm" onclick="cancelPrize($(this), 1)">取消</a></td>' +
        '</tr>';
    _tr.hide();
    _tr.after(_trHtml);
    upLoadRender(upload);
    formSelectRender(formSelects);
    formSelects.value('prizeType', [_tr.children('td:eq(2)').attr('id')]);
}

function delPrize(node) {
    node.parent().parent().remove();
}


function upLoadRender(upload) {
    upload.render({
        elem: '#prizeDiv',
        method : 'POST',
        url: '/upload/pic',
        // before: function(obj){
        //     //预读本地文件示例，不支持ie8
        //     obj.preview(function(index, file, result){
        //         $('.prizeImg').attr('src', result); //图片链接（base64）
        //     });
        // },
        done: function(res){
            if (res.code === 200) {
                $('#prizeImg').attr('src', res.data);
                $('#prizeDiv').css("background","#fff");
            } else {
                layer.msg(res.msg,{
                    time:1000
                });
            }
        }
    });
}

function formSelectRender(formSelects) {
    formSelects.data('prizeType', 'local', {
        arr: prizeType
    });
}

function verifyAddPrize() {
    var prizeImg = $('#prizeImg').attr('src');
    if (prizeImg === '') {
        layer.msg('奖品图片必传', {
            time:1000
        });
        return false;
    }

    var prizeName = $('#prizeName').val();
    if (prizeName === '') {
        layer.msg('奖品名称必填', {
            time:1000
        });
        return false;
    }

    var prizeType = formSelects.value('prizeType');
    if (prizeType.length === 0) {
        layer.msg('奖品类型必选', {
            time:1000
        });
        return false;
    }

    var prizeAmount = $('#prizeAmount').val();
    if (prizeAmount === '') {
        layer.msg('奖品数量必填', {
            time:1000
        });
        return false;
    }
    if (!(/(^[1-9]\d*$)/.test(prizeAmount)) && prizeAmount !== '0') {
        layer.msg('奖品数量格式有误', {
            time:1000
        });
        return false;
    }

    var prizeWeight = $('#prizeWeight').val();
    if (prizeWeight === '') {
        layer.msg('中奖概率必填', {
            time:1000
        });
        return false;
    }
    if (!(/(^[1-9]\d*$)/.test(prizeWeight)) && prizeWeight !== '0') {
        layer.msg('中奖概率格式有误', {
            time:1000
        });
        return false;
    }

    return true;
}