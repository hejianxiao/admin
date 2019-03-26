package com.hjhl.admin.shiro;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.hjhl.admin.constant.ResultEnum;
import com.hjhl.admin.exception.GlobalException;
import com.hjhl.admin.mapper.sys.SysPermissionMapper;
import com.hjhl.admin.modal.sys.SysPermission;
import com.hjhl.admin.modal.sys.SysUser;
import com.hjhl.admin.properties.AdminProperties;
import com.hjhl.admin.service.sys.SysUserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 创建人: Hjx
 * Date: 2019/3/4
 * Description:
 * Lazy 懒加载目的为防止业务层事务异常回滚失效
 */
public class UserRealm extends AuthorizingRealm {

    @Autowired
    @Lazy
    private SysUserService userService;
    @Autowired
    @Lazy
    private SysPermissionMapper permissionMapper;

    @Autowired
    private AdminProperties adminProperties;

    /**
     *  用于授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SysUser user = (SysUser)principals.getPrimaryPrincipal();
        if (user != null){
            SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
//            authorizationInfo.addRoles(permissionService.findPermissionRolesOfUser(userToken.getUser()));
            Set<String> permissions = new HashSet<>();

            List<SysPermission> list;
            if (user.getUsername().equals(adminProperties.getUserName())) {
                list = permissionMapper.selectList(new EntityWrapper<>(new SysPermission()).setSqlSelect("code"));
                if (!CollectionUtils.isEmpty(list)) {
                    SysPermission menuPermission = new SysPermission();
                    menuPermission.setCode("sys_permission");
                    list.add(menuPermission);

                    list.forEach(a -> {
                        permissions.add(a.getCode() + ":view");
                        permissions.add(a.getCode() + ":add");
                        permissions.add(a.getCode() + ":upd");
                        permissions.add(a.getCode() + ":del");
                    });
                }
            } else {
                list = permissionMapper.selectShiroPermissions(user.getUsername());
                if (!CollectionUtils.isEmpty(list)) {
                    list.forEach(a -> {
                        String[] cruds = a.getCruds().split(",");
                        Arrays.asList(cruds).forEach(b -> permissions.add(a.getCode() + ":" + b));
                    });
                }
            }


            authorizationInfo.setStringPermissions(permissions);
            return authorizationInfo;
        }
        return null;
    }

    /**
     *  定义如何获取用户信息的业务逻辑，给shiro做登录
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        // 将AuthenticationToken强转为AuthenticationToken对象
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;

        // 获取从表单传过来的用户名
        String username = upToken.getUsername();

        SysUser user;
        if (username.equals(adminProperties.getUserName())) {
            user = new SysUser();
            user.setId(adminProperties.getId());
            user.setUsername(adminProperties.getUserName());
            user.setRealName(adminProperties.getRealName());
            user.setPassword(adminProperties.getPassword());
        } else {
            user = userService.selectOne(new EntityWrapper<>(new SysUser()).where("username={0}", username));
            if (!user.getStatus().equals("1")){
                throw new GlobalException(ResultEnum.USER_FORBIDDEN);
            }
        }

        return new SimpleAuthenticationInfo(user, user.getPassword(), getName());
    }
}
