package com.hjhl.admin.modal.sys.dto;

import com.hjhl.admin.modal.BaseDTO;
import com.hjhl.admin.modal.sys.SysPermission;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Min;

/**
 * 创建人: Hjx
 * Date: 2019/3/27
 * Description:
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysPermissionDTO extends BaseDTO<SysPermission> {

    /** 主键id. */
    private String id;

    /** 权限code. */
    @NotBlank(message = "权限code必填")
    private String code;

    /** 父权限code. */
    private String pcode;

    /** 名称. */
    @NotBlank(message = "权限名称必填")
    private String name;

    /** 图标. */
    @NotBlank(message = "图标必填")
    private String icon;

    /** 链接. */
    private String uri;

    /** 排序. */
    @Min(value = 0, message = "排序格式有误")
    private Integer num;

    /** 描述. */
    private String description;

    @Override
    public SysPermission convertT2Dto(SysPermission permission) {
        return super.convertT2Dto(permission);
    }
}
