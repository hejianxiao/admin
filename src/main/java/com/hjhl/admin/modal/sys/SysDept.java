package com.hjhl.admin.modal.sys;

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
public class SysDept extends BaseEntity<SysDept> {

    /** 部门名称. */
    private String name;

    /** 排序. */
    private Integer num;

    /** 描述. */
    private String description;

    /** 创建时间. */
    private Date createTime;
}
