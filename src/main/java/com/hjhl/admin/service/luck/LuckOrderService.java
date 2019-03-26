package com.hjhl.admin.service.luck;

import com.baomidou.mybatisplus.plugins.Page;
import com.hjhl.admin.modal.luck.LuckOrder;
import com.hjhl.admin.vo.ResultVO;
import com.hjhl.admin.vo.TableVO;

/**
 * 创建人: Hjx
 * Date: 2019/3/21
 * Description:
 */
public interface LuckOrderService {

    TableVO view(Page<LuckOrder> page, LuckOrder luckDraw);

    ResultVO receiveView(String luckOrderId);
}
