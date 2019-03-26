package com.hjhl.admin.modal.sys.dto;

import com.hjhl.admin.constant.ResultEnum;
import com.hjhl.admin.exception.GlobalException;
import com.hjhl.admin.modal.BaseDTO;
import com.hjhl.admin.modal.sys.SysUser;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.util.Date;

/**
 * 创建人: Hjx
 * Date: 2019/3/11
 * Description:
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysUserDTO extends BaseDTO<SysUser> {

    /** id. */
    private String id;

    /** 用户名. */
    @NotBlank(message = "用户名必填")
    private String username;

    /** 密码. */
    private String password;

    /** 手机号. */
    @NotBlank(message = "手机号必填")
    private String mobile;

    /** 真实姓名. */
    @NotBlank(message = "真实姓名必填")
    private String realName;

    /** 性别. */
    @Range(min = 0, max = 1, message = "性别取值有误")
    private String sex;

    private String status;

    private String userType;

    private Date createTime;

    private String deptId;

    private String roleIds;

    @Override
    public SysUser convertT2Dto(SysUser user) {
        if (StringUtils.isEmpty(this.deptId)) {
            this.deptId = "";
        }

        if (StringUtils.isEmpty(this.id)) {
            if (StringUtils.isEmpty(this.password)) {
                throw new GlobalException(ResultEnum.HANDLE_ERROR, "密码必填");
            }
            this.userType = "1";//管理端用户
            this.status = "1";
            this.createTime = new Date();
            this.password = DigestUtils.md5DigestAsHex((this.username + this.password).getBytes());
        }
        return super.convertT2Dto(user);
    }
}
