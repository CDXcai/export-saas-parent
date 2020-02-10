package com.itheima.web.controller.exception;


import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 1.实现异常接口
 * 2.配置到容器中
 */
@Component
public class MyExceptionHandler implements HandlerExceptionResolver {

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        System.out.println("发生了异常");
        ModelAndView mv = new ModelAndView();
        if(ex instanceof UnauthorizedException){ //比较类型
            mv.setViewName("redirect:/unauthorized.jsp");
        }else{
            //配置了视图解析器 从pages下开始的
            mv.setViewName("error");
            mv.addObject("msg" , "对不起,发送不可预知异常,请联系管理员");
        }

        return mv;
    }
}
