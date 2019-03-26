package com.hjhl.admin.controller;

import com.hjhl.admin.modal.sys.SysUser;
import com.hjhl.admin.properties.AdminProperties;
import com.hjhl.admin.service.sys.SysPermissionService;
import com.hjhl.admin.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * 创建人: Hjx
 * Date: 2019/3/4
 * Description:
 */
@RestController
@Slf4j
public class IndexController {

    private SysPermissionService permissionService;
    private AdminProperties adminProperties;

    @Autowired
    public IndexController(SysPermissionService permissionService, AdminProperties adminProperties) {
        this.permissionService = permissionService;
        this.adminProperties = adminProperties;
    }

    @GetMapping("/topPermissions")
    public ResultVO findTopPermissionList() {
        SysUser user = (SysUser) SecurityUtils.getSubject().getPrincipal();
        if (user.getUsername().equals(adminProperties.getUserName())) {
            return permissionService.findTopPermissionList();
        }else {
            return permissionService.findTopPermissionList(user.getUsername());
        }
    }

    @GetMapping("/permissions/code/{code}")
    public ResultVO findPermissionList(@PathVariable String code) {
        SysUser user = (SysUser) SecurityUtils.getSubject().getPrincipal();
        if (user.getUsername().equals(adminProperties.getUserName())) {
            return permissionService.findPermissionList(code);
        } else {
            return permissionService.findPermissionList(user.getUsername(), code);
        }
    }


}
