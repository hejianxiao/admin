package com.hjhl.admin.service.sys.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.hjhl.admin.constant.ResultEnum;
import com.hjhl.admin.exception.GlobalException;
import com.hjhl.admin.mapper.sys.SysRoleMapper;
import com.hjhl.admin.mapper.sys.SysUserMapper;
import com.hjhl.admin.mapper.sys.SysUserRoleMapper;
import com.hjhl.admin.modal.sys.SysRole;
import com.hjhl.admin.modal.sys.SysUser;
import com.hjhl.admin.modal.sys.SysUserRole;
import com.hjhl.admin.service.BaseServiceImpl;
import com.hjhl.admin.service.sys.SysUserService;
import com.hjhl.admin.util.ResultVOUtil;
import com.hjhl.admin.vo.ResultVO;
import com.hjhl.admin.vo.TableVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;

/**
 * 创建人: Hjx
 * Date: 2019/3/4
 * Description:
 */
@Service
@Transactional
public class SysUserServiceImpl extends BaseServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    private SysUserRoleMapper userRoleMapper;
    private SysRoleMapper roleMapper;

    @Autowired
    public SysUserServiceImpl(SysUserRoleMapper userRoleMapper, SysRoleMapper roleMapper) {
        this.userRoleMapper = userRoleMapper;
        this.roleMapper = roleMapper;
    }

    @Override
    public TableVO view(Page<SysUser> page, SysUser user) {
        List<SysUser> list = baseMapper.selectPage(page, new EntityWrapper<>(new SysUser())
                .setSqlSelect("id", "username", "sex", "mobile", "real_name", "status", "create_time", "dept_id")
                .where("1=1")
                .like(!StringUtils.isEmpty(user.getRealName()), "real_name", user.getRealName())
                .like(!StringUtils.isEmpty(user.getMobile()), "mobile", user.getMobile()));
        page.setRecords(list);
        return ResultVOUtil.TableSuccess(page);
    }

    @Override
    public ResultVO updStatus(SysUser user) {
        return super.addOrUpd(user);
    }

    @Override
    @Transactional
    public ResultVO addOrUpd(SysUser user, String... args) {
        Wrapper<SysUser> ew = new EntityWrapper<>(new SysUser()).where("username={0}", user.getUsername());
        if (!StringUtils.isEmpty(user.getId())) {
            ew.and("id<>{0}", user.getId());
        }
        List<SysUser> list = baseMapper.selectList(ew);
        if (!CollectionUtils.isEmpty(list)) {
            return ResultVOUtil.Error(ResultEnum.DATA_EXIST.getCode(), "用户名不可重复");
        }
        super.addOrUpd(user);
        userRoleMapper.delete(new EntityWrapper<>(new SysUserRole()).where("user_id={0}", user.getId()));
        if (!StringUtils.isEmpty(args[0])) {
            String[] roleIds = args[0].split(",");
            Arrays.asList(roleIds).forEach(e -> {
                SysRole role = roleMapper.selectById(e);
                if (role == null) {
                    throw new GlobalException(ResultEnum.DATA_CRUD_ERROR);
                }
                userRoleMapper.insert(new SysUserRole(e, user.getId()));
            });
        }
        return ResultVOUtil.Success();
    }

    @Override
    public ResultVO delById(String id) {
        userRoleMapper.delete(new EntityWrapper<>(new SysUserRole()).where("user_id={0}", id));
        return super.delById(id);
    }
}
