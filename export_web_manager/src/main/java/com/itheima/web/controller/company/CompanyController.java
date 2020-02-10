package com.itheima.web.controller.company;


import com.github.pagehelper.PageInfo;
import com.itheima.domain.company.Company;
import com.itheima.service.company.CompanyService;
import com.itheima.web.controller.base.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/company")
public class CompanyController extends BaseController {

    @Autowired
    private CompanyService companyService;


    /**
     * 查询企业列表
     * @RequestParam(defaultValue = "1") Integer pageNum  如果页面没有传入页码 默认第一页
     */
    @RequestMapping(value = "/list" , name = "企业列表查询")
    public String list(@RequestParam(defaultValue = "1") Integer page ,@RequestParam(defaultValue = "2") Integer size){
        //List<Company> companyList = companyService.findAll();
        //PageResult pageResult = companyService.findByPage(page , size);
        PageInfo pageInfo = companyService.findByPageMybatis(page , size);

        //System.out.println(pageResult);
        //存入域
        //request.setAttribute("companyList" , companyList);
        request.setAttribute("page" , pageInfo);


        //找视图解析器匹配数据
        return "company/company-list";
    }

    /**
     * 跳转添加页面
     * name为了标注此方法的作用 , 第五天会使用
     * @return
     */
    @RequestMapping(value = "/toAdd" , name = "企业跳转添加页面")
    public String toAdd(){
        return "company/company-add";
    }

    /**
     * 企业添加页面
     * edit 既是添加也是修改
     *  save(user)
     *  update(user)
     *  save和update的区别只差一个id
     * @return
     */
    @RequestMapping(value = "/edit" , name = "企业添加或修改")
    public String edit(Company company){

        if(StringUtils.isEmpty(company.getId())){//id为空 添加
            companyService.save(company);
        }else{//id不为空,为修改
            companyService.update(company);
        }

        return "redirect:/company/list.do";
    }


    /**
     * 跳转修改页面
     * @param id
     * @return
     */
    @RequestMapping(value = "/toUpdate" , name = "跳转企业编辑页面")
    public String toUpdate( String id){
        //查询数据
        Company company = companyService.findById(id);
        request.setAttribute("company" , company);
        return "company/company-update";
    }

    /**
     * 删除
     * @return
     */
    @RequestMapping(value = "/delete" , name = "删除企业数据")
    public String delete(String id){
        companyService.delete(id);
        //跳转查询
        return "redirect:/company/list.do";
    }
}
