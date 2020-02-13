package com.itheima.web.controller.weixinLogin;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itheima.common.utils.Encrypt;
import com.itheima.domain.system.Module;
import com.itheima.domain.system.User;
import com.itheima.domain.weiXinLogin.WeiXinLogin;
import com.itheima.service.system.ModuleService;
import com.itheima.service.system.UserService;
import com.itheima.service.weinXinLogin.WeiXinLoginService;
import com.itheima.web.controller.base.BaseController;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


// 引入配置文件
@PropertySource("classpath:properties/weixin.properties")

@Controller
@RequestMapping("/weixin")
public class WebxinLoginController extends BaseController {
    //    https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code
    @Value("${weixin.appId}")
    private String appId;
    @Value("${weixin.secret}")
    private String secret;


    @Autowired
    private UserService userService;
    @Autowired
    private ModuleService moduleService;
    @Autowired
    private WeiXinLoginService weiXinLoginService;

    /**
     * 微信登录
     * @param code
     * @return
     */
    @RequestMapping("/login")
    public String login(String code) {
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + appId + "&secret=" + secret + "&code=" + code + "&grant_type=authorization_code";
        try {
            //创建HttpClient对象
            CloseableHttpClient client = HttpClients.createDefault();
            //创建get请求
            HttpGet get = new HttpGet(url);
            //发送get请求
            CloseableHttpResponse response = client.execute(get);
            HttpEntity entity = response.getEntity();
            String json = EntityUtils.toString(entity, "utf-8");
            // 讲json字符串转化为json对象
            JsonNode jsonNode = new ObjectMapper().readTree(json);
            // 获取用户的唯一标识
            String openid = jsonNode.get("openid").asText();
            // 用户标示存入session
            session.setAttribute("openid",openid);
            // 查询是有绑定了用户
            WeiXinLogin weiXinLogin = weiXinLoginService.findByOpenId(openid);
            // 如果有，则登录成功
            if (weiXinLogin != null) {
                // 查询指定id的用户信息
                User user1 = userService.findById(weiXinLogin.getUserId());
                // 获取email
                String email = user1.getEmail();
                // 获取密码
                String password = user1.getPassword();

                //1.获得subject 对象 获得主体对象
                Subject subject = SecurityUtils.getSubject();
                //2.将我们的用户名密码数据交给shiro 让shiro进行认证
                //User  TUser MyUser  ==>> 转换成 AuthenticationToken对象
                //2.1 构建用户名密码的对象
                UsernamePasswordToken upToken = new UsernamePasswordToken(email, password);
                //2.2 登录  ->login在执行的时候 默认调用 realm域中的认证方法(登录 获得到user数据)
                //返回值void 需要自己从shiro中手动获得数据
                subject.login(upToken);//只要登录不成功 此处都会报错(异常没有查到数据 , 或者是 密码不对 直接抛出异常)

                //3.从shiro获取登录数据(User) -> 一定是登录成功的代码
                //getPrincipal 获得主要的数据 (shiro安全管理框架 管理用户的权限 主要的数据就是用户对象 )
                User user = (User) subject.getPrincipal();
                //4.将user放入session
                session.setAttribute("loginUser", user);
                //将原密码存入会话中方便修改时进行比较
                session.setAttribute("password", password);
                //5.查询模块信息
                List<Module> moduleList = moduleService.findByUser(user);
                request.setAttribute("moduleList", moduleList);
                return "home/main";
            } else {
                // 如果没有，则跳转绑定用户界面

                // 跳转到绑定页面
                return "weiXin/weixin-add";
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        // 重定向到登录页面
        return "redirect:/";
    }


    /**
     * 微信绑定用户
     * @param email
     * @param password
     * @return
     */
    @RequestMapping("/regirect")
    public ModelAndView regirect(String email, String password){
        //对用户输入的密码进行加密
        password = Encrypt.md5(password , email);
        // 创建视图
        ModelAndView mv = new ModelAndView();
        try {
            //1.获得subject 对象 获得主体对象
            Subject subject = SecurityUtils.getSubject();
            //2.将我们的用户名密码数据交给shiro 让shiro进行认证
            //User  TUser MyUser  ==>> 转换成 AuthenticationToken对象
            //2.1 构建用户名密码的对象
            UsernamePasswordToken upToken = new UsernamePasswordToken(email, password);
            //2.2 登录  ->login在执行的时候 默认调用 realm域中的认证方法(登录 获得到user数据)
            //返回值void 需要自己从shiro中手动获得数据
            subject.login(upToken);//只要登录不成功 此处都会报错(异常没有查到数据 , 或者是 密码不对 直接抛出异常)

            //3.从shiro获取登录数据(User) -> 一定是登录成功的代码
            //getPrincipal 获得主要的数据 (shiro安全管理框架 管理用户的权限 主要的数据就是用户对象 )
            User user = (User) subject.getPrincipal();
            //4.将user放入session
            session.setAttribute("loginUser", user);
            //将原密码存入会话中方便修改时进行比较
            session.setAttribute("password", password);

            // 绑定微信信息
            // 创建绑定对象
            WeiXinLogin weiXinLogin = new WeiXinLogin();
            // 赋值用户id
            weiXinLogin.setUserId(user.getId());
            // 赋值openId
            weiXinLogin.setOpenId((String) session.getAttribute("openid"));

            // 添加到数据库
            weiXinLoginService.insert(weiXinLogin);

            //5.查询模块信息
            List<Module> moduleList = moduleService.findByUser(user);
            request.setAttribute("moduleList", moduleList);

            // 跳转到首页
            mv.setViewName("home/main");
        }catch (Exception e){
            e.printStackTrace();
            Map<String, Object> model = mv.getModel();
            // 添加错误提示信息
            model.put("error","用户名或者密码错误");
            // 重定向到首页
            mv.setViewName("redirect:/");
        }


        return mv;
    }

}
