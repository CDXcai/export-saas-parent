package com.itheima.web.controller.login;


import com.itheima.common.utils.Encrypt;
import com.itheima.domain.system.Module;
import com.itheima.domain.system.User;
import com.itheima.service.system.ModuleService;
import com.itheima.service.system.UserService;
import com.itheima.web.controller.base.BaseController;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class LoginController extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private ModuleService moduleService;
    /**
     * 1.获得用户名密码
     * 2.密码进行加密 跟数据库的数据进行比对
     * 3.根据用户名查询数据库
     * 4.拿到数据进行比对密码
     * 5.跳转页面
     * 5.1 成功 进入首页
     * 5.2 失败 回到登录页面 并提示信息
     * @param email
     * @param password
     * @return
     */
	/*@RequestMapping("/login")
	public String login(String email,String password) {
	    //1.判断非空
        if(StringUtils.isEmpty(email) || StringUtils.isEmpty(password)){
            request.setAttribute("error" , "用户名或密码错误1");
            return "forward:/login.jsp";
        }
        //2.密码加密  固定参数: 参数1 密码 参数2: 邮箱
        password = Encrypt.md5(password , email);

        //3.查询数据库
        User user = userService.findByEmail(email);
        if(user == null){//数据库没有这个用户
            request.setAttribute("error" , "用户名或密码错误2");
            return "forward:/login.jsp";
        }else if(user != null && !user.getPassword().equals(password)){//密码不相等
            request.setAttribute("error" , "用户名或密码错误3");
            return "forward:/login.jsp";
        }else{//用户名和密码都一致  登录成功
            //将用户的数据放入session
            session.setAttribute("loginUser" , user);

            //查询当前用户所具有的权限信息
            List<Module> moduleList = moduleService.findByUser(user);

            request.setAttribute("moduleList" , moduleList);
            return "home/main";
        }
	}*/

    /**
     * 登陆
     * shiro的开发步骤
     *  1.获得subject 对象
     *  2.将我们的用户名密码数据交给shiro 让shiro进行认证
     *  3.从shiro获取登录数据(User)
     *  4.将user放入session
     *  5.查询模块信息
     * @return
     */
    @RequestMapping("/login")
    public String login(String email,String password) {
        //对用户输入的密码进行加密
        password = Encrypt.md5(password , email);
        try {
            //判断非空
            if(StringUtils.isEmpty(email) || StringUtils.isEmpty(password)){
                request.setAttribute("error" , "用户名或密码不能为空");
                return "forward:/login.jsp";
            }
            //1.获得subject 对象 获得主体对象
            Subject subject = SecurityUtils.getSubject();
            //2.将我们的用户名密码数据交给shiro 让shiro进行认证
            //User  TUser MyUser  ==>> 转换成 AuthenticationToken对象
            //2.1 构建用户名密码的对象
            UsernamePasswordToken upToken = new UsernamePasswordToken(email , password);
            //2.2 登录  ->login在执行的时候 默认调用 realm域中的认证方法(登录 获得到user数据)
            //返回值void 需要自己从shiro中手动获得数据
            subject.login(upToken);//只要登录不成功 此处都会报错(异常没有查到数据 , 或者是 密码不对 直接抛出异常)

            //3.从shiro获取登录数据(User) -> 一定是登录成功的代码
            //getPrincipal 获得主要的数据 (shiro安全管理框架 管理用户的权限 主要的数据就是用户对象 )
            User user = (User)subject.getPrincipal();
            //4.将user放入session
            session.setAttribute("loginUser" , user);
            //将原密码存入会话中方便修改时进行比较
            session.setAttribute("password",password);

            //5.查询模块信息
            List<Module> moduleList = moduleService.findByUser(user);
            request.setAttribute("moduleList" , moduleList);
        } catch (AuthenticationException e) {
            e.printStackTrace();//登录失败
            request.setAttribute("error" , "用户名或密码错误");
            return "forward:/login.jsp";
        }
        return "home/main";
    }

    //退出
    @RequestMapping(value = "/logout",name="用户登出")
    public String logout(){
        //SecurityUtils.getSubject().logout();//当用户退出的时候 删除缓存中自己的数据
        return "forward:login.jsp";
    }

    @RequestMapping("/home")
    public String home(){
	    return "home/home";
    }
}
