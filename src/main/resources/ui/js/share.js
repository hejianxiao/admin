layui.define(['jquery'], function(exports){
    var $ = layui.jquery;
    var obj = {
        ajax: function (options) {
            var _errorAlert = options.errorAlert === undefined ? false : options.errorAlert;
            options.url = options.url + (options.url.indexOf('?') > 0 ? '&' : '?') + ('rand' + new Date().getTime() + '=') + new Date().getTime();

            options.cache = false;
            options.type = options.type === undefined ? 'POST' : options.type;
            options.dataType = options.dataType === undefined ? 'json' : options.dataType;
            options.async = options.async === undefined ? true : options.async;

            var _error = options.error;
            options.error = function (xhr, status, err) {
                if (xhr) {
                    if (xhr.status === 405 || xhr.status === 0) {
                        localStorage.removeItem('LoginInfo');
                        top.location.href = '/view/login.html';
                    }
                    if (_errorAlert) {
                        layer.msg(xhr.responseJSON.err,{
                            time:1000
                        });
                    }
                    if ($.isFunction(_error)) {
                        _error.call(this, xhr, status, err);
                    }
                }
            };
            $.ajax(options);
        },

        /**
         * form-select 4.0
         * @param formSelects 组件对象
         * @param name 操作select name
         * @param val 需要选中的值
         */
        selectInit: function(formSelects, name, val) {
            formSelects.config(name, {
                beforeSuccess: function (id, url, searchVal, result) {
                    result = result.data ? result.data : result;
                    $.each(result, function(index, item) {
                        item.value && (item.value === val ? item.selected = 'selected' : '')
                    });
                    return result;
                }
            });
        }
    };
    //输出接口
    exports('share', obj);
});
