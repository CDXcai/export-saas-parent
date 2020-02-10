package com.itheima.web.controller.system;

import com.github.pagehelper.PageInfo;
import com.itheima.common.utils.Encrypt;
import com.itheima.domain.system.Dept;
import com.itheima.domain.system.Role;
import com.itheima.domain.system.User;
import com.itheima.service.system.DeptService;
import com.itheima.service.system.RoleService;
import com.itheima.service.system.UserService;
import com.itheima.web.controller.base.BaseController;
import com.itheima.web.controller.rabbitMQ.RabbitMQProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/system/user")
public class UserController extends BaseController {
    @Autowired
    private UserService userService;


    /**
     * 用户查询 : 根据企业进行用户查询
     * @return
     */
    @RequestMapping(value = "/list" ,name = "用户查询")
    public String list(@RequestParam(defaultValue ="1") Integer page ,@RequestParam(defaultValue ="5") Integer size){
        //String companyId = "1"; //必须动态获得 根据不同的登录用户 获得企业id
        //1.需要service 调用分页方法
        PageInfo pageInfo = userService.findByPage(page , size , companyId);
        //2.将返回的分页对象 放入request域
        request.setAttribute("page" , pageInfo);
        return "system/user/user-list";
    }



    @Autowired //注入 容器启动的时候注入
    private DeptService deptService;
    /**
     * 跳转用户添加页面
     * @return
     */
    @RequestMapping(value = "/toAdd" ,name = "跳转用户添加页面")
    public String toAdd(){
        //查询部门数据  deptService 空. 热部署 项目还在运行 直接更新到代码中
        List<Dept> deptList = deptService.findAll(companyId);
        //将list存入域中
        request.setAttribute("deptList" , deptList);
        //跳转页面
        return "system/user/user-add";
    }

    @Autowired
    private RabbitMQProducer rabbitMQProducer;
    /**
     * 添加和修改的代码
     * 修改会携带id  添加不会携带id
     * @return
     */
    @RequestMapping(value = "/edit" ,name = "添加或者修改用户")
    public String edit(User user){//, String action  表示当天调用的方法
        //暂时写死
        user.setCompanyId(companyId);
        user.setCompanyName(companyName);

        if(StringUtils.isEmpty(user.getId())){//增加
            try {
                String password = user.getPassword();//明文
                String md5Password = Encrypt.md5(password, user.getEmail());//加密
                user.setPassword(md5Password);//重新赋值

                //此行代码表示新增用户  为了保证数据库不再增加记录
                //userService.save(user);
                //新增用户以后发送一封邮件
                String to = user.getEmail();// 收件人 必须是动态的
                String subject="注册成功";//主题
                System.out.println(password);
                System.out.println(md5Password);
                String content = "恭喜您,加入到saas-export平台,您的密码是:"+password+",md5密码:"+md5Password;//内容  此处必须是明文的密码
                //MailUtil.sendMsg(to , subject  , content );
                Map<String , String> map = new HashMap<>();
                map.put("to" ,to );
                map.put("subject" ,subject );
                map.put("content" ,content );
                //发送消息给中间件
                rabbitMQProducer.sendData("mail.send" , map);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{//修改
            userService.update(user);
        }
        return "redirect:/system/user/list.do";
    }

    /**
     * 查询用户数据跳转页面
     * 1.查询用户对象
     * 2.查询部门列表数据
     * @return
     */
    @RequestMapping(value = "/toUpdate" , name = "查询用户数据跳转页面")
    public String toUpdate(String id){

        //String companyId = "1";
        //查询对象
        User user = userService.findById(id);
        //存入域
        request.setAttribute("user" , user);

        //部门数据
        List<Dept> deptList = deptService.findAll(companyId);
        request.setAttribute("deptList" , deptList);
        return "system/user/user-update";
    }


    /**
     * 删除用户
     * @return
     */
    @RequestMapping(value = "/delete" , name = "删除用户")
    public String delete(String id){
        userService.delete(id);

        return "redirect:/system/user/list.do";
    }



    //----------------------------------------------------------------------------------------------------
    @Autowired
    private RoleService roleService;
    /**
     * 跳转角色分配页面
     *  //1.查询当前的用户信息数据
     *  User user = userService.findById(id);
     *  //2.查询出所有的角色数据
     *  List<Role> roleList = roleService.findAll(companyId);
     *  //3.根据用户id 查询角色的信息
     *  #根据用户id 查询角色的信息
     * SELECT * FROM pe_role WHERE role_id IN (
     * 	SELECT role_id FROM pe_role_user
     * 	WHERE user_id = '002108e2-9a10-4510-9683-8d8fd1d374ef'
     *  )
     * @return
     */
    @RequestMapping(value = "/roleList" , name = "跳转角色分配页面")
    public String roleList(String id){//id为用户id
        //1.查询当前的用户信息数据
        User user = userService.findById(id);
        request.setAttribute("user" , user);
        //2.查询出所有的角色数据
        List<Role> roleList = roleService.findAll(companyId);
        request.setAttribute("roleList" , roleList);
        //System.out.println(roleList.size());
        //3.当前用户具有的角色信息
        List<Role> userRoleList = roleService.findByUid(id);
        //System.out.println(userRoleList.size());
        //拼接成一个字符串id 在页面使用包含的语法判断
        String userRoleStr = "";
        for (Role role : userRoleList) {
            userRoleStr+=role.getId()+",";
        }
        //System.out.println(userRoleStr);
        request.setAttribute("userRoleStr" , userRoleStr);
        //角色分配页面
        return "system/user/user-role";
    }


    /**
     * 修改用户的权限
     * 1.删除当前用户原本的角色数据
     * 2.添加新的角色
     * userid: 002108e2-9a10-4510-9683-8d8fd1d374ef
     * roleIds: 4028a1c34ec2e5c8014ec2ebf8430001
     * roleIds: 4028a1c34ec2e5c8014ec2ec38cc0002
     * roleIds: 4028a1cd4ee2d9d6014ee2df4c6a0000
     * roleIds: 4028a1cd4ee2d9d6014ee2df4c6a0001
     * roleIds: 4028a1cd4ee2d9d6014ee2df4c6a0002
     * @return
     */
    @RequestMapping(value = "/changeRole" , name = "修改用户的权限")
    public String changeRole(String userid , String roleIds){
        //修改完后跳转查询用户页面
        //controller没有事务
        //1.删除当前用户原本的角色数据
        //2.添加新的角色
        //必须在service层中编写逻辑代码
        roleService.changeRole(userid , roleIds);

        return "redirect:/system/user/list.do";
    }
}
