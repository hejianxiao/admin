package com.hjhl.admin.controller.luck;

import com.baomidou.mybatisplus.plugins.Page;
import com.hjhl.admin.constant.ResultEnum;
import com.hjhl.admin.modal.luck.LuckDraw;
import com.hjhl.admin.modal.luck.dto.LuckDrawDTO;
import com.hjhl.admin.service.luck.LuckDrawService;
import com.hjhl.admin.util.ResultVOUtil;
import com.hjhl.admin.vo.ResultVO;
import com.hjhl.admin.vo.TableVO;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 创建人: Hjx
 * Date: 2019/3/19
 * Description:
 */
@RestController
@RequestMapping("/luck/luckDraw")
@Validated
public class LuckDrawController {

    private LuckDrawService drawService;

    @Autowired
    public LuckDrawController(LuckDrawService drawService) {
        this.drawService = drawService;
    }



    @GetMapping
    @RequiresPermissions("luck_draw:view")
    public TableVO view(LuckDraw luckDraw, Integer page, Integer limit) {
        Page<LuckDraw> pages = new Page<>(page == null ? 1 : page,
                limit == null ? 10 : limit);
        return drawService.view(pages, luckDraw);
    }

    @PostMapping
    @RequiresPermissions(value = {"luck_draw:add", "luck_draw:upd"}, logical= Logical.OR)
    public ResultVO add(LuckDrawDTO luckDrawDTO, BindingResult result) {
        if (result.hasErrors()) {
            return ResultVOUtil.Error(ResultEnum.HANDLE_ERROR.getCode(), result.getFieldError().getDefaultMessage());
        }
        return drawService.add(luckDrawDTO);
    }

    @GetMapping("/{drawId}")
    @RequiresPermissions("luck_draw:view")
    public ResultVO prizeView(@PathVariable("drawId") String drawId) {
        return drawService.prizeView(drawId);
    }

    @DeleteMapping
    @RequiresPermissions("luck_draw:del")
    public ResultVO del(@NotBlank(message = "抽奖活动id必传") String id) {
        return drawService.delById(id);
    }


    @GetMapping("/list")
    @RequiresPermissions("luck_draw:view")
    public ResultVO list() {
        return drawService.list();
    }



}
