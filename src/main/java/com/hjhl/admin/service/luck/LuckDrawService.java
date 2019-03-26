package com.hjhl.admin.service.luck;

import com.baomidou.mybatisplus.plugins.Page;
import com.hjhl.admin.modal.luck.LuckDraw;
import com.hjhl.admin.modal.luck.dto.LuckDrawDTO;
import com.hjhl.admin.service.BaseService;
import com.hjhl.admin.vo.ResultVO;
import com.hjhl.admin.vo.TableVO;

/**
 * 创建人: Hjx
 * Date: 2019/3/19
 * Description:
 */
public interface LuckDrawService extends BaseService<LuckDraw> {

    TableVO view(Page<LuckDraw> page, LuckDraw luckDraw);

    ResultVO add(LuckDrawDTO luckDrawDTO);

    ResultVO prizeView(String drawId);

    ResultVO list();

}
