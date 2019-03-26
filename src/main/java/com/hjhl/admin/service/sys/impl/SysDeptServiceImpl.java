package com.hjhl.admin.service.sys.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hjhl.admin.constant.ResultEnum;
import com.hjhl.admin.constant.SelectEnum;
import com.hjhl.admin.mapper.sys.SysDeptMapper;
import com.hjhl.admin.modal.sys.SysDept;
import com.hjhl.admin.service.sys.SysDeptService;
import com.hjhl.admin.util.LayuiVOUtil;
import com.hjhl.admin.util.ResultVOUtil;
import com.hjhl.admin.vo.ResultVO;
import com.hjhl.admin.vo.SelectVO;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 创建人: Hjx
 * Date: 2019/3/11
 * Description:
 */
@Service
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDept> implements SysDeptService {

    @Override
    public ResultVO findDeptList(String deptId) {
        List<SysDept> list = baseMapper.selectList(new EntityWrapper<>());
        if (CollectionUtils.isEmpty(list)) {
            return ResultVOUtil.Error(ResultEnum.SELECT_EMPTY);
        }
        return new LayuiVOUtil<SysDept>() {
            @Override
            public SelectVO Convert(SysDept sysDept) {
                if (!StringUtils.isEmpty(deptId) && sysDept.getId().equals(deptId)) {
                    return new SelectVO(sysDept.getName(), sysDept.getId(), SelectEnum.SELECTED.getTag(), "");
                }else {
                    return new SelectVO(sysDept.getName(), sysDept.getId());
                }
            }
        }.SelectInit(list);
    }
}
