package com.hjhl.admin.service.sys;

import com.baomidou.mybatisplus.plugins.Page;
import com.hjhl.admin.modal.sys.SysPermission;
import com.hjhl.admin.service.BaseService;
import com.hjhl.admin.vo.ResultVO;
import com.hjhl.admin.vo.TableVO;
import com.hjhl.admin.vo.TreeVO;

import java.util.List;

/**
 * 创建人: Hjx
 * Date: 2019/3/7
 * Description:
 */
public interface SysPermissionService extends BaseService<SysPermission> {

    TableVO view(Page<SysPermission> page, SysPermission permission);

    List<TreeVO> tree();

    ResultVO selectTree(String pcode, String code);

    ResultVO findTopPermissionList(String username);

    ResultVO findTopPermissionList();

    ResultVO findPermissionList(String username, String code);

    ResultVO findPermissionList(String code);

}
