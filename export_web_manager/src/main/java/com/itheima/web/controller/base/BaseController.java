package com.itheima.web.controller.base;


import com.itheima.domain.system.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class BaseController {

    //使用protected  只要继承BaseController 子类都可以使用如下的三个对象
    @Autowired
    protected HttpServletRequest request;
    @Autowired
    protected HttpServletResponse response;//idea编译问题 可以使用
    @Autowired
    protected HttpSession session;

    //这两个值以后必须从session中获得
    protected  String companyId;
    protected  String companyName;

    /**
     * @ModelAttribute 预处理注解: 在执行每一个方法之前 先执行此方法
     */
    @ModelAttribute
    public void before(){
        User loginUser =(User)session.getAttribute("loginUser");
        //System.out.println(loginUser);
        if(loginUser!=null){
            companyName=loginUser.getCompanyName();//获得企业名称
            companyId=loginUser.getCompanyId();//获得企业的id
        }
    }
}
