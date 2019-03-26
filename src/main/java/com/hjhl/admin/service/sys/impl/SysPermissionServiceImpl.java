package com.hjhl.admin.service.sys.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.hjhl.admin.constant.ResultEnum;
import com.hjhl.admin.constant.SelectEnum;
import com.hjhl.admin.mapper.sys.SysPermissionMapper;
import com.hjhl.admin.modal.sys.SysPermission;
import com.hjhl.admin.service.BaseServiceImpl;
import com.hjhl.admin.service.sys.SysPermissionService;
import com.hjhl.admin.util.CopyUtil;
import com.hjhl.admin.util.LayuiVOUtil;
import com.hjhl.admin.util.ResultVOUtil;
import com.hjhl.admin.vo.ResultVO;
import com.hjhl.admin.vo.SelectVO;
import com.hjhl.admin.vo.TableVO;
import com.hjhl.admin.vo.TreeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 创建人: Hjx
 * Date: 2019/3/7
 * Description:
 */
@Service
public class SysPermissionServiceImpl extends BaseServiceImpl<SysPermissionMapper, SysPermission> implements SysPermissionService {

    private SysPermissionMapper permissionMapper;

    @Autowired
    public SysPermissionServiceImpl(SysPermissionMapper permissionMapper) {
        this.permissionMapper = permissionMapper;
    }

    @Override
    public TableVO view(Page<SysPermission> page, SysPermission permission) {
        List<SysPermission> list = baseMapper.selectPage(page, new EntityWrapper<>(new SysPermission())
                .setSqlSelect("id", "code", "pcode", "name", "uri", "num", "description", "icon")
                .where("1=1")
                .and(!StringUtils.isEmpty(permission.getCode()), "pcode={0}", permission.getCode())
                .orderBy("num", true));
        page.setRecords(list);
        return ResultVOUtil.TableSuccess(page);
    }

    @Override
    public List<TreeVO> tree() {
        List<SysPermission> permissions = this.selectPermissions("0");
        if (CollectionUtils.isEmpty(permissions)) {
            ResultVOUtil.Error(ResultEnum.LIST_EMPTY);
        }
        TreeVO treeVO = new TreeVO();
        treeVO.setCode("0");
        treeVO.setFinal(false);
        treeVO.setName("-------所有菜单--------");
        treeVO.setSpread(true);
        treeVO.setChildren(this.treeList(permissions));
        List<TreeVO> treeVOList = new ArrayList<>();
        treeVOList.add(treeVO);
        return treeVOList;
    }

    @Override
    public ResultVO selectTree(String pcode, String code) {
        List<SysPermission> permissions = this.selectPermissions("0");
        if (CollectionUtils.isEmpty(permissions)) {
            return ResultVOUtil.Error(ResultEnum.LIST_EMPTY);
        }

        List<SelectVO> selectVOList =  new LayuiVOUtil<SysPermission>() {
            @Override
            public SelectVO Convert(SysPermission p) {
                SelectVO selectVO = new SelectVO(p.getName(), p.getCode());
                if (!StringUtils.isEmpty(code) && p.getCode().equals(code)) {
                    selectVO.setDisabled(SelectEnum.DISABLED.getTag());
                }

                if (!StringUtils.isEmpty(pcode) && p.getCode().equals(pcode)) {
                    selectVO.setSelected(SelectEnum.SELECTED.getTag());
                }
                List<SysPermission> ele = selectPermissions(p.getCode());
                if (!CollectionUtils.isEmpty(ele)) {
                    selectVO.setChildren(SelectTreeInit(ele));
                }
                return selectVO;
            }
        }.SelectTreeInit(permissions);

        return ResultVOUtil.Success(selectVOList, ResultEnum.SELECT_SUCCESS);
    }

    private List<TreeVO> treeList(List<SysPermission> permissions) {
        List<TreeVO> treeVOList = new ArrayList<>();
        permissions.forEach(e -> {
            TreeVO treeVO = new TreeVO();
            CopyUtil.copyProperties(e, treeVO);
            treeVO.setSpread(true);
            List<SysPermission> ele = this.selectPermissions(e.getCode());
            if (!CollectionUtils.isEmpty(ele)) {
                treeVO.setChildren(treeList(ele));
                treeVO.setFinal(false);
            } else {
                treeVO.setFinal(true);
            }
            treeVOList.add(treeVO);
        });
        return treeVOList;
    }

    @Override
    public ResultVO findTopPermissionList(String username) {
        List<SysPermission> permissions = permissionMapper.selectPermissions(username, "0");
        if (CollectionUtils.isEmpty(permissions)) {
            return ResultVOUtil.Error(ResultEnum.LIST_EMPTY);
        }
        return ResultVOUtil.Success(permissions);
    }

    @Override
    public ResultVO findTopPermissionList() {
        List<SysPermission> permissions = this.selectPermissions("0");
        if (CollectionUtils.isEmpty(permissions)) {
            return ResultVOUtil.Error(ResultEnum.LIST_EMPTY);
        }
        return ResultVOUtil.Success(permissions);
    }

    @Override
    public ResultVO findPermissionList(String username, String code) {
        List<SysPermission> permissions = permissionMapper.selectPermissions(username, code);
        if (CollectionUtils.isEmpty(permissions)) {
            return ResultVOUtil.Error(ResultEnum.LIST_EMPTY);
        }
        return ResultVOUtil.Success(sList(username, permissions));
    }

    @Override
    public ResultVO findPermissionList(String code) {
        List<SysPermission> permissions = this.selectPermissions(code);
        if (CollectionUtils.isEmpty(permissions)) {
            return ResultVOUtil.Error(ResultEnum.LIST_EMPTY);
        }
        return ResultVOUtil.Success(sList(null, permissions));
    }

    private List<Map<String, Object>> sList(String username, List<SysPermission> permissions) {
        List<Map<String, Object>> listMap = new ArrayList<>();
        permissions.forEach(e -> {
            Map<String, Object> map = new HashMap<>();
            map.put("title", e.getName());
            map.put("icon", "&#xe" + e.getIcon() + ";");
            map.put("href", e.getUri());
            map.put("spread", false);
            if (StringUtils.isEmpty(username)) {
                List<SysPermission> ele = this.selectPermissions(e.getCode());
                if (!CollectionUtils.isEmpty(ele)) {
                    map.put("children", sList(null, ele));
                }
                listMap.add(map);
            } else {
                List<SysPermission> ele = permissionMapper.selectPermissions(username, e.getCode());
                if (!CollectionUtils.isEmpty(ele)) {
                    map.put("children", sList(username, ele));
                }
                listMap.add(map);
            }

        });
        return listMap;
    }


    private List<SysPermission> selectPermissions(String code) {
        return permissionMapper.selectList(new EntityWrapper<>(new SysPermission())
                .where("pcode={0}", code));
    }
}
