package com.hjhl.admin.controller.sys;

import com.baomidou.mybatisplus.plugins.Page;
import com.hjhl.admin.modal.sys.SysPermission;
import com.hjhl.admin.service.sys.SysPermissionService;
import com.hjhl.admin.vo.ResultVO;
import com.hjhl.admin.vo.TableVO;
import com.hjhl.admin.vo.TreeVO;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 创建人: Hjx
 * Date: 2019/3/14
 * Description:
 */
@RestController
@RequestMapping("/sys/permission")
@Validated
public class SysPermissionController {

    private SysPermissionService permissionService;

    @Autowired
    public SysPermissionController(SysPermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @GetMapping
    @RequiresPermissions("sys_permission:view")
    public TableVO view(SysPermission permission, Integer page, Integer limit) {
        Page<SysPermission> pages = new Page<>(page == null ? 1 : page,
                limit == null ? 10 : limit);
        return permissionService.view(pages, permission);
    }

    @GetMapping("/tree")
    @RequiresPermissions("sys_permission:view")
    public List<TreeVO> tree() {
        return permissionService.tree();
    }

    @GetMapping("/selectTree")
    @RequiresPermissions("sys_permission:view")
    public ResultVO selectTree(SysPermission permission) {
        return permissionService.selectTree(permission.getPcode(), permission.getCode());
    }

}
