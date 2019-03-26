package com.hjhl.admin.service.luck.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hjhl.admin.constant.ResultEnum;
import com.hjhl.admin.mapper.luck.LuckOrderMapper;
import com.hjhl.admin.mapper.luck.LuckReceiveMapper;
import com.hjhl.admin.modal.luck.LuckOrder;
import com.hjhl.admin.modal.luck.LuckReceive;
import com.hjhl.admin.service.luck.LuckOrderService;
import com.hjhl.admin.util.ResultVOUtil;
import com.hjhl.admin.vo.ResultVO;
import com.hjhl.admin.vo.TableVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 创建人: Hjx
 * Date: 2019/3/21
 * Description:
 */
@Service
public class LuckOrderServiceImpl extends ServiceImpl<LuckOrderMapper, LuckOrder> implements LuckOrderService {

    private LuckReceiveMapper receiveMapper;

    @Autowired
    public LuckOrderServiceImpl(LuckReceiveMapper receiveMapper) {
        this.receiveMapper = receiveMapper;
    }

    @Override
    public TableVO view(Page<LuckOrder> page, LuckOrder luckOrder) {
        List<LuckOrder> list = baseMapper.selectTable(page, luckOrder);
        page.setRecords(list);
        return ResultVOUtil.TableSuccess(page);
    }

    @Override
    public ResultVO receiveView(String luckOrderId) {
        List<LuckReceive> receives = receiveMapper.selectList(new EntityWrapper<>(new LuckReceive())
                    .where("luck_order_id={0}", luckOrderId));
        if (CollectionUtils.isEmpty(receives)) {
            return ResultVOUtil.Error(ResultEnum.LIST_EMPTY);
        }
        return ResultVOUtil.Success(receives);
    }


}
