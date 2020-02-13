package com.itheima.web.controller.shiro;

import com.itheima.common.utils.Encrypt;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;

/**
 * Custom 自定义 Credentials 密码 Matcher 比较器
 *  执行用户密码和数据库密码的对比
 */
public class CustomCredentialsMatcher extends SimpleCredentialsMatcher {
    /**
     * 方法名称 : doCredentialsMatch 密码匹配的方法
     * 参数:
     *  AuthenticationToken token : 用户输入的用户名和密码
     *  AuthenticationInfo info : 数据库的用户名和密码
     *  使用用户输入的密码和数据库的密码比较
     * 返回值: boolean  如果比较成功返回true 如果比较失败返回false
     * @param token
     * @param info
     * @return
     */
    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        //System.out.println("密码比较器方法执行");
        //1.用户的密码
        UsernamePasswordToken upToken = (UsernamePasswordToken)token;
        //密码
        String password = new String(upToken.getPassword());
        //用户名
        String email = upToken.getUsername();

        //2.数据库的密码
        String dbPassword = (String)info.getCredentials();

        return password.equals(dbPassword);
    }
}
