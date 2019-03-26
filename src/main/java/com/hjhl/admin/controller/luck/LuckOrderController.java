package com.hjhl.admin.controller.luck;

import com.baomidou.mybatisplus.plugins.Page;
import com.hjhl.admin.modal.luck.LuckOrder;
import com.hjhl.admin.service.luck.LuckOrderService;
import com.hjhl.admin.vo.ResultVO;
import com.hjhl.admin.vo.TableVO;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 创建人: Hjx
 * Date: 2019/3/21
 * Description:
 */
@RestController
@RequestMapping("/luck/luckOrder")
public class LuckOrderController {

    private LuckOrderService orderService;

    @Autowired
    public LuckOrderController(LuckOrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    @RequiresPermissions("luck_order:view")
    public TableVO view(LuckOrder luckOrder, Integer page, Integer limit) {
        Page<LuckOrder> pages = new Page<>(page == null ? 1 : page,
                limit == null ? 10 : limit);
        return orderService.view(pages, luckOrder);
    }

    @GetMapping("{luckOrderId}")
    @RequiresPermissions("luck_order:view")
    public ResultVO receiveView(@PathVariable("luckOrderId") String luckOrderId) {
        return orderService.receiveView(luckOrderId);
    }

}
