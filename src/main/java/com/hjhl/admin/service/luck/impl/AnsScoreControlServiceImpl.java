package com.hjhl.admin.service.luck.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.hjhl.admin.constant.ResultEnum;
import com.hjhl.admin.mapper.luck.AnsScoreControlMapper;
import com.hjhl.admin.modal.luck.AnsScoreControl;
import com.hjhl.admin.service.BaseServiceImpl;
import com.hjhl.admin.service.luck.AnsScoreControlService;
import com.hjhl.admin.util.ResultVOUtil;
import com.hjhl.admin.vo.ResultVO;
import com.hjhl.admin.vo.TableVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * 创建人: Hjx
 * Date: 2019/3/20
 * Description:
 */
@Service
public class AnsScoreControlServiceImpl extends BaseServiceImpl<AnsScoreControlMapper, AnsScoreControl> implements AnsScoreControlService {

    @Override
    public TableVO view(Page<AnsScoreControl> page, AnsScoreControl scoreControl) {
        List<AnsScoreControl> list = baseMapper.selectTable();
        page.setRecords(list);
        return ResultVOUtil.TableSuccess(page);
    }


    @Override
    @Transactional
    public ResultVO addOrUpd(AnsScoreControl scoreControl, String... args) {
        Wrapper<AnsScoreControl> ew = new EntityWrapper<>(new AnsScoreControl()).where("luck_draw_id={0}", scoreControl.getLuckDrawId());
        if (!StringUtils.isEmpty(scoreControl.getId())) {
            ew.and("id<>{0}", scoreControl.getId());
        } else {
            scoreControl.setCreateTime(new Date());
        }
        List<AnsScoreControl> list = baseMapper.selectList(ew);
        if (!CollectionUtils.isEmpty(list)) {
            return ResultVOUtil.Error(ResultEnum.DATA_EXIST.getCode(), "一个分数只能控制一个活动");
        }
        return super.addOrUpd(scoreControl);
    }
}
