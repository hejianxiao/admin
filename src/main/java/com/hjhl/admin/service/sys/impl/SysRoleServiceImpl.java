package com.hjhl.admin.service.sys.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.hjhl.admin.constant.ResultEnum;
import com.hjhl.admin.mapper.sys.SysRoleMapper;
import com.hjhl.admin.modal.sys.SysRole;
import com.hjhl.admin.service.sys.SysRoleService;
import com.hjhl.admin.util.LayuiVOUtil;
import com.hjhl.admin.util.ResultVOUtil;
import com.hjhl.admin.vo.ResultVO;
import com.hjhl.admin.vo.SelectVO;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 创建人: Hjx
 * Date: 2019/3/11
 * Description:
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {


    @Override
    public ResultVO findRoleList(String userId) {
        List<SysRole> list = baseMapper.selectRoleForUser(userId);
        if (CollectionUtils.isEmpty(list)) {
            return ResultVOUtil.Error(ResultEnum.SELECT_EMPTY);
        }
        return new LayuiVOUtil<SysRole>() {
            @Override
            public SelectVO Convert(SysRole sysRole) {
                return new SelectVO(sysRole.getName(), sysRole.getId(), sysRole.getSelected(), "");
            }
        }.SelectInit(list);
    }
}
