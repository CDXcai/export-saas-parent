package com.itheima.web.controller.system;

import com.github.pagehelper.PageInfo;
import com.itheima.domain.system.Module;
import com.itheima.service.system.ModuleService;
import com.itheima.web.controller.base.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/system/module")
public class ModuleController extends BaseController {

    @Autowired
    private ModuleService moduleService;

    /**
     * 模块查询
     * @param page
     * @param size
     * @return
     */
    //Requires 必须  Permissions权限 如果注解开发 必须重启服务器
    //注解方式如果没有权限 直接抛出异常 必须还需要自己处理这个异常
    //@RequiresPermissions("模块管理")
    @RequestMapping(value = "/list" ,name = "模块查询")
    public String list(@RequestParam(defaultValue ="1") Integer page , @RequestParam(defaultValue ="5") Integer size){
        PageInfo pageInfo = moduleService.findByPage(page, size);
        request.setAttribute("page" , pageInfo);
        return "system/module/module-list";
    }

    /**
     * 跳转模块新增
     * @return
     */
    @RequestMapping(value = "/toAdd",name = "跳转模块新增")
    public String toAdd(){
        //查询所有的菜单选项 : 为了上级菜单的下拉框做准备
        List<Module> moduleList = moduleService.findAll();
        request.setAttribute("menus" , moduleList);
        return "system/module/module-add";
    }

    /**
     * 保存或者修改模块数据
     * @return
     */
    @RequestMapping(value = "/edit",name = "保存或者修改模块数据")
    public String edit(Module module){
        if(StringUtils.isEmpty(module.getId())){
            moduleService.save(module);
        }else{
            moduleService.update(module);
        }
        return "redirect:/system/module/list.do";
    }

    /**
     * 跳转修改页面
     * @param id
     * @return
     */
    @RequestMapping(value = "/toUpdate" , name = "跳转模块修改页面")
    public String toUpdate(String id){
        //1.查询原来的数据
        Module module = moduleService.findById(id);
        //2.查询上级模块的数据
        List<Module> moduleList = moduleService.findAll();
        request.setAttribute("module" , module);
        request.setAttribute("menus" , moduleList);
        return "system/module/module-update";
    }

    /**
     * 删除模块数据
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete" , name = "删除模块数据")
    public String delete(String id){
        moduleService.delete(id);
        return "redirect:/system/module/list.do";
    }


    /**
     * 修改上级模块的数据
     * @return
     */
    @RequestMapping(value = "/changeModule" , name = "修改上级模块的数据")
    @ResponseBody
    public List<Module> changeModule( String ctype){
        //System.out.println(ctype);

        return moduleService.findModuleByCtype(ctype);
    }
}
