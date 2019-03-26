package com.hjhl.admin.service.luck.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.hjhl.admin.constant.ResultEnum;
import com.hjhl.admin.mapper.luck.LuckDrawMapper;
import com.hjhl.admin.mapper.luck.LuckPrizeMapper;
import com.hjhl.admin.modal.luck.LuckDraw;
import com.hjhl.admin.modal.luck.LuckPrize;
import com.hjhl.admin.modal.luck.dto.LuckDrawDTO;
import com.hjhl.admin.service.BaseServiceImpl;
import com.hjhl.admin.service.luck.LuckDrawService;
import com.hjhl.admin.util.CopyUtil;
import com.hjhl.admin.util.LayuiVOUtil;
import com.hjhl.admin.util.ResultVOUtil;
import com.hjhl.admin.vo.ResultVO;
import com.hjhl.admin.vo.SelectVO;
import com.hjhl.admin.vo.TableVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * 创建人: Hjx
 * Date: 2019/3/19
 * Description:
 */
@Service
public class LuckDrawServiceImpl extends BaseServiceImpl<LuckDrawMapper, LuckDraw> implements LuckDrawService {

    private LuckPrizeMapper prizeMapper;

    @Autowired
    public LuckDrawServiceImpl(LuckPrizeMapper prizeMapper) {
        this.prizeMapper = prizeMapper;
    }

    @Override
    public TableVO view(Page<LuckDraw> page, LuckDraw luckDraw) {
        List<LuckDraw> list = baseMapper.selectPage(page, new EntityWrapper<>(new LuckDraw())
                .where("or_delete={0}", 1)
                .like(!StringUtils.isEmpty(luckDraw.getActName()),"act_name", luckDraw.getActName())
                .orderBy("create_time", false));
        page.setRecords(list);
        return ResultVOUtil.TableSuccess(page);
    }

    @Override
    @Transactional
    public ResultVO add(LuckDrawDTO luckDrawDTO) {
        LuckDraw luckDraw = new LuckDraw();
        CopyUtil.copyProperties(luckDrawDTO, luckDraw);
        if (StringUtils.isEmpty(luckDrawDTO.getId())) {
            luckDraw.setCreateTime(new Date());
            luckDraw.setOrDelete("1");
            baseMapper.insert(luckDraw);
        } else {
            baseMapper.updateById(luckDraw);
        }

        prizeMapper.updateForSet("or_delete = 0", new EntityWrapper<>(new LuckPrize()).where("luck_draw_id={0}", luckDraw.getId()));
        List<LuckPrize> luckPrizes = JSON.parseObject(luckDrawDTO.getLuckPrizes(), new TypeReference<List<LuckPrize>>(){}); // Json 转List
        luckPrizes.forEach(e -> {
            e.setOrDelete("1");
            e.setLuckDrawId(luckDraw.getId());
            e.setPrizeWeight(e.getPrizeWeight()/100D);
            prizeMapper.insert(e);
        });
        return ResultVOUtil.Success();
    }

    @Override
    public ResultVO prizeView(String drawId) {
        List<LuckPrize> luckPrizes = prizeMapper.selectList(new EntityWrapper<>(new LuckPrize())
                .where("luck_draw_id={0}", drawId).and("or_delete={0}", 1));
        if (CollectionUtils.isEmpty(luckPrizes)) {
            return ResultVOUtil.Error(ResultEnum.LIST_EMPTY);
        }
        return ResultVOUtil.Success(luckPrizes);
    }

    @Override
    public ResultVO list() {
        List<LuckDraw> list = baseMapper.selectList(new EntityWrapper<>(new LuckDraw())
                .where("or_delete={0}", 1)
                .orderBy("create_time", false));
        if (CollectionUtils.isEmpty(list)) {
            return ResultVOUtil.Error(ResultEnum.SELECT_EMPTY);
        }
        return new LayuiVOUtil<LuckDraw>() {
            @Override
            public SelectVO Convert(LuckDraw luckDraw) {
                return new SelectVO(luckDraw.getActName(), luckDraw.getId());
            }
        }.SelectInit(list);
    }

    @Override
    @Transactional
    public ResultVO delById(String id) {
        prizeMapper.updateForSet("or_delete = 0", new EntityWrapper<>(new LuckPrize()).where("luck_draw_id={0}", id));
        baseMapper.updateForSet("or_delete = 0", new EntityWrapper<>(new LuckDraw()).where("id={0}", id));
        return ResultVOUtil.Success();
    }
}
