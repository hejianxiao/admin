package com.hjhl.admin.mapper.sys;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.hjhl.admin.modal.sys.SysRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 创建人: Hjx
 * Date: 2019/3/7
 * Description:
 */
public interface SysRoleMapper extends BaseMapper<SysRole> {

    List<SysRole> selectRoleForUser(@Param("userId") String userId);

}
