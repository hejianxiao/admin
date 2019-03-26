package com.hjhl.admin.mapper.sys;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.hjhl.admin.modal.sys.SysPermission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 创建人: Hjx
 * Date: 2019/3/7
 * Description:
 */
public interface SysPermissionMapper extends BaseMapper<SysPermission> {

    List<SysPermission> selectPermissions(@Param("username") String username,
                                                 @Param("pcode") String pcode);


    List<SysPermission> selectShiroPermissions(@Param("username") String username);
}
