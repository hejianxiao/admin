package com.hjhl.admin.service.sys;

import com.baomidou.mybatisplus.plugins.Page;
import com.hjhl.admin.modal.sys.SysUser;
import com.hjhl.admin.modal.sys.dto.SysUserDTO;
import com.hjhl.admin.service.BaseService;
import com.hjhl.admin.vo.ResultVO;
import com.hjhl.admin.vo.TableVO;

/**
 * 创建人: Hjx
 * Date: 2019/3/4
 * Description:
 */
public interface SysUserService extends BaseService<SysUser> {

    TableVO view(Page<SysUser> page, SysUser user);

    ResultVO updStatus(SysUser user);

}
