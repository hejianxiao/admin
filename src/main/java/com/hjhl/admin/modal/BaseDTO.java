package com.hjhl.admin.modal;

import com.hjhl.admin.util.CopyUtil;

/**
 * 创建人: Hjx
 * Date: 2019/3/12
 * Description:
 */
public class BaseDTO<T> {

    public T convertT2Dto(T t) {
        CopyUtil.copyProperties(this, t);
        return t;
    }


}
