package com.hjhl.admin.controller.sys;

import com.baomidou.mybatisplus.plugins.Page;
import com.hjhl.admin.constant.ResultEnum;
import com.hjhl.admin.modal.sys.SysUser;
import com.hjhl.admin.modal.sys.dto.SysUserDTO;
import com.hjhl.admin.service.sys.SysUserService;
import com.hjhl.admin.util.ResultVOUtil;
import com.hjhl.admin.vo.ResultVO;
import com.hjhl.admin.vo.TableVO;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 创建人: Hjx
 * Date: 2019/3/9
 * Description:
 */
@RestController
@RequestMapping("/sys/user")
@Validated
public class SysUserController {

    private SysUserService userService;

    @Autowired
    public SysUserController(SysUserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @RequiresPermissions("sys_user:view")
    public TableVO view(SysUser user, Integer page, Integer limit) {
        Page<SysUser> pages = new Page<>(page == null ? 1 : page,
                limit == null ? 10 : limit);
        return userService.view(pages, user);
    }

    @PostMapping
    @RequiresPermissions(value = {"sys_user:add", "sys_user:upd"}, logical= Logical.OR)
    public ResultVO add(@Valid SysUserDTO userDTO, BindingResult result) {
        if (result.hasErrors()) {
            return ResultVOUtil.Error(ResultEnum.HANDLE_ERROR.getCode(), result.getFieldError().getDefaultMessage());
        }
        return userService.addOrUpd(userDTO.convertT2Dto(new SysUser()), userDTO.getRoleIds());
    }

    @DeleteMapping
    @RequiresPermissions("sys_user:del")
    public ResultVO del(@NotBlank(message = "用户id必传") String id) {
        return userService.delById(id);
    }


    @PostMapping("/status")
    @RequiresPermissions("sys_user:upd")
    public ResultVO updStatus(@NotBlank(message = "用户id必传") String id,
                        @Range(min = 0, max = 1, message = "状态值有误") String status) {
        SysUser user = new SysUser();
        user.setId(id);
        user.setStatus(status);
        return userService.updStatus(user);
    }

}
