package com.hjhl.admin.controller.sys;

import com.hjhl.admin.service.sys.SysDeptService;
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
@RequestMapping("/sys/dept")
public class SysDeptController {

    private SysDeptService deptService;

    @Autowired
    public SysDeptController(SysDeptService deptService) {
        this.deptService = deptService;
    }

    @GetMapping
    public ResultVO findDeptList(String deptId) {
        return deptService.findDeptList(deptId);
    }


}
