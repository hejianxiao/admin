package com.hjhl.admin.controller.luck;

import com.baomidou.mybatisplus.plugins.Page;
import com.hjhl.admin.modal.luck.AnsScoreControl;
import com.hjhl.admin.service.luck.AnsScoreControlService;
import com.hjhl.admin.vo.ResultVO;
import com.hjhl.admin.vo.TableVO;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 创建人: Hjx
 * Date: 2019/3/20
 * Description:
 */
@RestController
@RequestMapping("/ans/control")
public class AnsScoreControlController {

    private AnsScoreControlService controlService;

    @Autowired
    public AnsScoreControlController(AnsScoreControlService controlService) {
        this.controlService = controlService;
    }

    @GetMapping
    @RequiresPermissions("ans_control:view")
    public TableVO view(AnsScoreControl scoreControl, Integer page, Integer limit) {
        Page<AnsScoreControl> pages = new Page<>(page == null ? 1 : page,
                limit == null ? 10 : limit);
        return controlService.view(pages, scoreControl);
    }

    @PostMapping
    @RequiresPermissions(value = {"ans_control:add", "ans_control:upd"}, logical= Logical.OR)
    public ResultVO add(AnsScoreControl scoreControl) {
        return controlService.addOrUpd(scoreControl);
    }

    @DeleteMapping
    @RequiresPermissions("ans_control:del")
    public ResultVO del(@NotBlank(message = "分数控制id必传") String id) {
        return controlService.delById(id);
    }


}
