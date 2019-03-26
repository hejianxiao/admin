package com.hjhl.admin.mapper.luck;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.hjhl.admin.modal.luck.LuckOrder;

import java.util.List;

/**
 * 创建人: Hjx
 * Date: 2019/3/21
 * Description:
 */
public interface LuckOrderMapper extends BaseMapper<LuckOrder> {

    List<LuckOrder> selectTable(Page page, LuckOrder luckOrder);

}
