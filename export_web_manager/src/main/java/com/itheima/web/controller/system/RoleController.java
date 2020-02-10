package com.itheima.web.controller.system;

import com.github.pagehelper.PageInfo;
import com.itheima.domain.system.Module;
import com.itheima.domain.system.Role;
import com.itheima.service.system.ModuleService;
import com.itheima.service.system.RoleService;
import com.itheima.web.controller.base.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/system/role")
public class RoleController extends BaseController {

    @Autowired
    private RoleService roleService;

    /**
     * 角色查询
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value = "/list"  , name = "角色查询")
    public String list(@RequestParam(defaultValue = "1") Integer page ,@RequestParam(defaultValue = "5") Integer size){
        PageInfo pageInfo = roleService.findByPage(page, size, companyId);
        request.setAttribute("page" , pageInfo);
        return "system/role/role-list";
    }


    /**
     * 角色跳转添加页面
     * @return
     */
    @RequestMapping(value = "/toAdd"  , name = "角色跳转添加页面")
    public String toAdd(){

        return "system/role/role-add";
    }

    /**
     * 添加或者修改角色数据
     * @param role
     * @return
     */
    @RequestMapping(value = "/edit"  , name = "添加或者修改角色数据")
    public String edit(Role role){
        role.setCompanyId(super.companyId);
        role.setCompanyName(super.companyName);

        if(StringUtils.isEmpty(role.getId())){
            roleService.save(role);
        }else{
            roleService.update(role);
        }
        return "redirect:/system/role/list.do";
    }


    /**
     * 跳转修改页面
     * @return
     */
    @RequestMapping(value = "/toUpdate"  , name = "跳转修改页面")
    public String toUpdate(String id){
       //查询角色数据
        Role role = roleService.findById(id);
        request.setAttribute("role" , role);
        return "system/role/role-update";
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete"  , name = "删除角色")
    public String delete(String id){
        roleService.delete(id);
        return "redirect:/system/role/list.do";
    }

    /**
     * 角色模块页面跳转
     * 查询出当前角色的信息
     * @return
     */
    @RequestMapping(value = "/roleModule"  , name = "角色模块页面跳转")
    public String roleModule(String roleid){
        //查询出当前角色的信息
        Role role = roleService.findById(roleid);
        request.setAttribute("role" , role);
        //查询模块信息.... 不能在此处编写 此处的代码是请求转发 而我们需要的是json数据
        return "system/role/role-module";
    }

    @Autowired
    private ModuleService moduleService;
    /**
     * 初始化树的controller代码 -> 目的拿到json数据
     * var zNodes =[
     *             { id:11, pId:1, name:"随意勾选 1-1", open:true , checked:true},
     *             { id:111, pId:11, name:"随意勾选 1-1-1"}
     *   ]
     *   []  List array数组  {}  是map  或者 对象 username password age
     *   使用Map的原因是: 我们可以自己构建数据
     * @return
     */
    @RequestMapping(value = "/initTree" ,name = "构建树形菜单")
    @ResponseBody
    public List<Map> initTree(String roleId){

        //查询原来角色的模块信息
        //System.out.println(roleId);//配置文件中 resultMap="BaseResultMap"
        List<Module> roleModuleList = moduleService.findByRoleId(roleId);
        //System.out.println(roleModuleList);
        //--------------------------------------------
        //1.查询所有的module数据
        List<Module> moduleList = moduleService.findAll();
        //2.将 List<Module> 转换成  List<Map>
        List<Map> mapList = new ArrayList<>();

        //3.转换
        for (Module module : moduleList) {
            //创建map
            Map map = new HashMap();
            //构建数据
            map.put("id" , module.getId());
            map.put("pId" , module.getParentId());//pId 必须大写
            map.put("name" , module.getName());

            //根据原来的角色模块信息 进行比对 如果有 加上checked 为true
            /*for (Module childModule : roleModuleList) {
                if(module.getId().equals(childModule.getId())){
                    map.put("checked" , true);
                }
            }*/
            //集合中是否包含module信息  roleModuleList子模块的集合  必须实现equals方法
            if(roleModuleList.contains(module)){
                map.put("checked" , true);
            }
            mapList.add(map);
        }

        return mapList;
    }


    /**
     * 修改角色权限数据
     * @return
     */
    @RequestMapping(value = "/updateRoleModule" , name = "修改角色权限数据")
    public String updateRoleModule(String roleid , String moduleIds){
        //删除原本的角色模块数据  新增角色模块数据
        moduleService.updateRoleModule(roleid , moduleIds);
        return "redirect:/system/role/list.do";
    }
}
