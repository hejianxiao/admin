package com.hjhl.admin.exception;

import com.hjhl.admin.constant.ResultEnum;
import lombok.Getter;

/**
 * 创建人: Hjx
 * Date: 2019/2/25
 * Description:
 */
@Getter
public class GlobalException extends RuntimeException{

    private Integer code;

    public GlobalException(ResultEnum resultEnum) {
        super(resultEnum.getMsg());
        this.code = resultEnum.getCode();
    }

    public GlobalException(ResultEnum resultEnum, String msg) {
        super(msg);
        this.code = resultEnum.getCode();
    }

    public GlobalException(Integer httpStatus, String msg) {
        super(msg);
        this.code = httpStatus;
    }

}
