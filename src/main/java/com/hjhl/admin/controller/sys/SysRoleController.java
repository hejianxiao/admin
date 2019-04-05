package com.hjhl.admin.controller.sys;

import com.baomidou.mybatisplus.plugins.Page;
import com.hjhl.admin.modal.sys.SysRole;
import com.hjhl.admin.service.sys.SysRoleService;
import com.hjhl.admin.vo.ResultVO;
import com.hjhl.admin.vo.TableVO;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 创建人: Hjx
 * Date: 2019/3/11
 * Description:
 */
@RestController
@RequestMapping("/sys/role")
public class SysRoleController {

    private SysRoleService roleService;

    @Autowired
    public SysRoleController(SysRoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    @RequiresPermissions("sys_role:view")
    public TableVO view(SysRole user, Integer page, Integer limit) {
        Page<SysRole> pages = new Page<>(page == null ? 1 : page,
                limit == null ? 10 : limit);
        return roleService.view(pages, user);
    }

    @GetMapping("/roleList")
    public ResultVO findRoleList(String userId) {
        return roleService.findRoleList(userId);
    }

}
