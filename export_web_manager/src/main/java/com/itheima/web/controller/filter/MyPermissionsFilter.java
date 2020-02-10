package com.itheima.web.controller.filter;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * 自定义过滤器
 *  1.创建类
 *  2.继承 AuthorizationFilter
 *  3.实现方法 isAccessAllowed  是否允许被访问
 *      如果返回值为true 放行 不拦截
 *      如果返回值为false 不放行,权限不足
 *
 *  需要将此过滤器放入spring容器中
 */
public class MyPermissionsFilter extends AuthorizationFilter {
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        System.out.println("执行自己的过滤器");

        Subject subject = getSubject(request, response);
        String[] perms = (String[]) mappedValue;
        if (perms != null && perms.length > 0) {
            for (String perm : perms) {
                boolean flag = subject.isPermitted(perm);//判断是否含有权限  只要有一个为true就放行
                if(flag){
                    return true;//放行
                }
            }
        }
        return false;
    }
}
