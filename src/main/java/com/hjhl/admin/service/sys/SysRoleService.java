package com.hjhl.admin.service.sys;

import com.baomidou.mybatisplus.plugins.Page;
import com.hjhl.admin.modal.sys.SysRole;
import com.hjhl.admin.vo.ResultVO;
import com.hjhl.admin.vo.TableVO;

/**
 * 创建人: Hjx
 * Date: 2019/3/11
 * Description:
 */
public interface SysRoleService {

    TableVO view(Page<SysRole> page, SysRole role);

    ResultVO findRoleList(String userId);

}
