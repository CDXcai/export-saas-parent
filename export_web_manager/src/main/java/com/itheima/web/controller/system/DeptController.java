package com.itheima.web.controller.system;

import com.github.pagehelper.PageInfo;
import com.itheima.domain.system.Dept;
import com.itheima.service.system.DeptService;
import com.itheima.web.controller.base.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/system/dept")
public class DeptController extends BaseController {
    @Autowired
    private DeptService deptService;
    /**
     * 部门查询 : 根据企业进行部门查询
     * @return
     */
    @RequestMapping(value = "/list" ,name = "部门查询")
    public String list(@RequestParam(defaultValue ="1") Integer page ,@RequestParam(defaultValue ="5") Integer size){
        //String companyId = "1"; //必须动态获得 根据不同的登录用户 获得企业id
        //1.需要service 调用分页方法
        PageInfo pageInfo = deptService.findByPage(page , size , companyId);
        //2.将返回的分页对象 放入request域
        request.setAttribute("page" , pageInfo);
        return "system/dept/dept-list";
    }


    /**
     * 跳转部门添加页面
     * @return
     */
    @RequestMapping(value = "/toAdd" ,name = "跳转部门添加页面")
    public String toAdd(){
        //String companyId = "1";
        //查询上级部门的名称  查询所有
        List<Dept> deptList = deptService.findAll(companyId);
        //存入页面
        request.setAttribute("deptList" ,deptList );
        //跳转页面
        return "system/dept/dept-add";
    }

    /**
     * 添加和修改的代码
     * @return
     */
    @RequestMapping(value = "/edit" ,name = "添加或者修改部门")
    public String edit(Dept dept){
        //暂时写死
        dept.setCompanyId(companyId);
        dept.setCompanyName(companyName);

        if(StringUtils.isEmpty(dept.getId())){//增加
            deptService.save(dept);
        }else{//修改
            deptService.update(dept);
        }
        return "redirect:/system/dept/list.do";
    }

    /**
     * 查询部门数据跳转页面
     * 1.查询部门对象
     * 2.查询所有的上级部门对象
     * @return
     */
    @RequestMapping(value = "/toUpdate" , name = "查询部门数据跳转页面")
    public String toUpdate(String id){

        //String companyId = "1";
        //查询对象
        Dept dept = deptService.findById(id);
        //存入域
        request.setAttribute("dept" , dept);

        //查询上级部门的名称  查询所有
        List<Dept> deptList = deptService.findAll(companyId);
        //存入页面
        request.setAttribute("deptList" ,deptList );
        return "system/dept/dept-update";
    }


    /**
     * 删除部门
     * @return
     */
    @RequestMapping(value = "/delete" , name = "删除部门")
    public String delete(String id){
        deptService.delete(id);

        return "redirect:/system/dept/list.do";
    }
}
