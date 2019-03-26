package com.hjhl.admin.constant;

import lombok.Getter;

/**
 * 创建人: Hjx
 * Date: 2019/2/25
 * Description:
 */
@Getter
public enum ResultEnum {

    HANDLE_SUCCESS(200, "操作成功"),
    /** form-select */
    SELECT_SUCCESS(0, "success"),
    SELECT_EMPTY(0, "远程JSON数据为空"),


    HANDLE_ERROR(400, "操作失败"),
    LOGIN_ERROR(401, "登录失败，请检查用户名密码"),
    LOGIN_PIC_EXPIRE_ERROR(401, "登录失败，图形验证码已失效~"),
    LOGIN_PIC_ERROR(401, "登录失败，请输入正确的图形验证码~"),
    LOGOUT_ERROR(401, "登出失败，已返回登录页面"),
    USER_FORBIDDEN(401, "用户已禁用"),
    DATA_CRUD_ERROR(403, "操作数据失败"),
    OBJECT_EMPTY(404, "对象不存在"),
    LIST_EMPTY(404, "列表为空"),
    DATA_EXIST(409, "数据已存在"),

    USER_EMPTY(404, "用户不存在"),

    /******* oss 图片 *******/
    OSS_IMAGE_EMPTY(404, "图片不存在"),
    OSS_IMAGE_ERROR(400, "图片上传失败"),
    OSS_IMAGE_MAX(400, "图片过大"),
    OSS_IMAGE_SIZE(400, "url路径参数异常");


    private Integer code;
    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
