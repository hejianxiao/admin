package com.hjhl.admin.modal.sys;

import com.baomidou.mybatisplus.annotations.TableField;
import com.hjhl.admin.modal.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 创建人: Hjx
 * Date: 2019/3/4
 * Description:
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysPermission extends BaseEntity<SysPermission> {

    /** 权限code. */
    private String code;
    
    /** 父权限code. */
    private String pcode;
    
    /** 级别. */
    private Integer level;
    
    /** 名称. */
    private String name;
    
    /** 图标. */
    private String icon;
    
    /** 链接. */
    private String uri;

    /** 排序. */
    private Integer num;

    /** 描述. */
    private String description;

    @TableField(exist = false)
    private String cruds;
    
}
