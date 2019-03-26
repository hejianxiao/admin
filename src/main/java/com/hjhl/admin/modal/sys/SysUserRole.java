package com.hjhl.admin.modal.sys;

import com.hjhl.admin.modal.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 创建人: Hjx
 * Date: 2019/3/13
 * Description:
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysUserRole extends BaseEntity<SysUserRole> {

    /** 角色id. */
    private String roleId;
    
    /** 用户id. */
    private String userId;

    public SysUserRole() {
    }

    public SysUserRole(String roleId, String userId) {
        this.roleId = roleId;
        this.userId = userId;
    }
}
