package com.blaife.shiro;

import com.blaife.model.User;
import com.blaife.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Blaife
 * @description 自定义shiro程序
 * @data 2020/4/24 23:25
 */
public class UserRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    /**
     * 功能描述: 执行授权逻辑
     * @Param: [principals]
     * @Return: org.apache.shiro.authz.AuthorizationInfo
     * @Author: Blaife
     * @Date: 2020/4/24 23:26
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        // 到数据库查询当前用户的授权信息
        // 1. 获取subject
        Subject subject = SecurityUtils.getSubject();
        // 2. 获取principal(登录时存储的用户信息)
        User user = (User) subject.getPrincipal();
        // 3. 将用户的授权信息添加进来
        info.addStringPermission(user.getPerms());
        return info;
    }


    /**
     * 功能描述: 执行认证逻辑
     * @Param: [token]
     * @Return: org.apache.shiro.authc.AuthenticationInfo
     * @Author: Blaife
     * @Date: 2020/4/24 23:26
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken arg0) throws AuthenticationException {

        UsernamePasswordToken token = (UsernamePasswordToken) arg0;
        User user = userService.findUserByName(token.getUsername());
        // 1. 判断用户是否存在
        if (user == null) {
            // 抛出UnknownAccountException异常，表示用户名不存在
            return null;
        }
        // 2。 判断密码是否存在
        // 密码错误，抛出IncorrectCredentialsException异常 表示密码错误
        return new SimpleAuthenticationInfo(user, user.getPassword(), "");
    }
}
