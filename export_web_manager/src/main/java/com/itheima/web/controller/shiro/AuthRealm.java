package com.itheima.web.controller.shiro;


import com.itheima.domain.system.Module;
import com.itheima.domain.system.User;
import com.itheima.service.system.ModuleService;
import com.itheima.service.system.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 自定义的realm域 继承  AuthenticatingRealm   AuthorizingRealm  CachingRealm
 * 或者实现 Realm
 */
public class AuthRealm extends AuthorizingRealm {
    @Autowired
    private ModuleService moduleService;
    /**
     *  获得授权信息
     *  方法名称:  doGetAuthorizationInfo 执行获得授权的信息(哪些权限可以被放行)
     *  方法参数:  PrincipalCollection 重要数据的集合(最主要的就是user)
     *  方法的返回值:  授权数据信息 AuthorizationInfo
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        //System.out.println("授权方法执行");
        //1.授权数据信息
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        //3.定义权限集合
        Set<String> stringPermissions = new HashSet<>();
        //5.获得登录后的user
        User user = (User)principals.getPrimaryPrincipal();
        //4.查询数据库得到用户的授权数据
        List<Module> moduleList = moduleService.findByUser(user);
        //6.将list转换成set
        for (Module module : moduleList) {
            stringPermissions.add(module.getName());
        }
        //System.out.println(stringPermissions);
        //2.具备某种权限可以访问集合
        authorizationInfo.setStringPermissions(stringPermissions);
        return authorizationInfo;
    }

    @Autowired
    private UserService userService;
    /**
     * 获得认证信息 当前方法的目的 其实就是登录
     * 看方法名称 doGetAuthenticationInfo 执行获得认证(安全)数据的方法
     * 看参数 :  AuthenticationToken 用户输入的用户名和密码
     * 看返回值 : AuthenticationInfo 返回数据库中的安全数据
     * 如果有数据 默认继续执行密码比较器
     * 如果没有数据 直接抛出异常 登录失败
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //System.out.println("认证方法执行");
        //3.获得用户输入的用户名和密码
        UsernamePasswordToken upToken = (UsernamePasswordToken)token;
        //用户名
        String email = upToken.getUsername();
        //2.从数据库中查询数据
        User user = userService.findByEmail(email); //查不到数据user ==null

        if(user!=null){
            //1.安全数据对象  创建对象并返回
            //参数1: 重要数据 安全数据
            //参数2: credentials 密码(数据库的真实的密码数据 不是用户输入的密码) , 不是现在用的而是一会为了快速获得密码使用
            //参数3: realm域的名称(任意 只要唯一即可)  this.getName()当前类的名称
            //public SimpleAuthenticationInfo(Object principal, Object credentials, String realmName) {
            SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user , user.getPassword() , this.getName()); //user.getPassword() 空指针
            return info;
        }else{
            return null;
        }

    }
}
