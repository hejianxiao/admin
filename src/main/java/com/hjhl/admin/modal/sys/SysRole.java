package com.hjhl.admin.modal.sys;

import com.baomidou.mybatisplus.annotations.TableField;
import com.hjhl.admin.modal.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 创建人: Hjx
 * Date: 2019/3/7
 * Description:
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysRole extends BaseEntity<SysRole> {

    /** 角色名称. */
    private String name;
    
    /** 排序. */
    private Integer num;

    /** 描述. */
    private String description;

    /** 创建时间. */
    private Date createTime;

    @TableField(exist = false)
    private String selected;

}
