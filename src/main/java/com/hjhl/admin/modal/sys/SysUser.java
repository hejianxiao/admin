package com.hjhl.admin.modal.sys;

import com.hjhl.admin.modal.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 创建人: Hjx
 * Date: 2019/3/4
 * Description:
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysUser extends BaseEntity<SysUser> {

    /** 主键. */
    private String id;

    /** 用户名. */
    private String username;

    /** 密码. */
    private String password;

    /** 手机号. */
    private String mobile;

    /** 用户姓名. */
    private String realName;

    /** 性别: 0 女. 1 男. */
    private String sex;

    /** 是否启用. */
    private String status;
    
    /** 部门id. */
    private String deptId;

    /** 用户类别. */
    private String userType;

    /** 创建时间. */
    private Date createTime;

    public SysUser() {
    }

    public SysUser(String username) {
        this.username = username;
    }
}
