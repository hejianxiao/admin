package com.hjhl.admin.controller.sys;

import com.hjhl.admin.service.sys.SysRoleService;
import com.hjhl.admin.vo.ResultVO;
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
    public ResultVO findRoleList(String userId) {
        return roleService.findRoleList(userId);
    }

}
