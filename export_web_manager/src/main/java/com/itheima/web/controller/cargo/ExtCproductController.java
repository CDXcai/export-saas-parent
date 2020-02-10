package com.itheima.web.controller.cargo;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.itheima.domain.cargo.ExtCproduct;
import com.itheima.domain.cargo.ExtCproductExample;
import com.itheima.domain.cargo.Factory;
import com.itheima.domain.cargo.FactoryExample;
import com.itheima.service.cargo.ExtCproductService;
import com.itheima.service.cargo.FactoryService;
import com.itheima.web.controller.base.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/cargo/extCproduct")
public class ExtCproductController extends BaseController {

    @Reference
    private ExtCproductService extCproductService;

    @Reference
    private FactoryService factoryService;
    /**
     * 附件查询
     * 1.查询当前货物下附件的数据
     * 2.查询生产厂家(查询附件的厂家)
     * 3.bug 保存附件的时候 需要传入指定的合同id和货物id
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value = "/list" )
    public String list(String contractId , String contractProductId,@RequestParam(defaultValue = "1") Integer page , @RequestParam(defaultValue = "5") Integer size){
        //1.查询货物下的附件数据
        ExtCproductExample extCproductExample = new ExtCproductExample();
        ExtCproductExample.Criteria extCproductExampleCriteria = extCproductExample.createCriteria();
        extCproductExampleCriteria.andContractProductIdEqualTo(contractProductId);
        PageInfo pageInfo = extCproductService.findAll(extCproductExample, page, size);
        request.setAttribute("page" , pageInfo);

        //2.查询厂家数据
        FactoryExample factoryExample= new FactoryExample();
        FactoryExample.Criteria exampleCriteria = factoryExample.createCriteria();
        exampleCriteria.andCtypeEqualTo("附件");
        List<Factory> factoryList = factoryService.findAll(factoryExample);
        request.setAttribute("factoryList" , factoryList);

        //3.赋值合同id和货物id
        request.setAttribute("contractId" , contractId);
        request.setAttribute("contractProductId" , contractProductId);

        return "cargo/extc/extc-list";
    }


    /**
     * 添加或修改方法
     * @return
     */
    @RequestMapping("/edit")
    public String edit(ExtCproduct extCproduct){
        extCproduct.setCompanyId(super.companyId);
        extCproduct.setCompanyName(super.companyName);
        if(StringUtils.isEmpty(extCproduct.getId())){
            extCproductService.save(extCproduct);
        }else{
            extCproductService.update(extCproduct);
        }
        return "redirect:/cargo/extCproduct/list.do?contractId="+extCproduct.getContractId()+"&contractProductId="+extCproduct.getContractProductId();
    }


    /**
     * 跳转修改页面
     * @return
     */
    @RequestMapping("/toUpdate")
    public String toUpdate(String id , String contractId , String contractProductId){
        //1.根据id查询当前的附件信息
        ExtCproduct extCproduct = extCproductService.findById(id);
        request.setAttribute("extCproduct" , extCproduct);
        //2.附件厂家数据
        FactoryExample factoryExample= new FactoryExample();
        FactoryExample.Criteria exampleCriteria = factoryExample.createCriteria();
        exampleCriteria.andCtypeEqualTo("附件");
        List<Factory> factoryList = factoryService.findAll(factoryExample);
        request.setAttribute("factoryList" , factoryList);

        return "cargo/extc/extc-update";
    }

    /**
     * 删除
     * @return
     */
    @RequestMapping("/delete")
    public String delete(String id , String contractId , String contractProductId){
        //根据id删除
        extCproductService.delete(id);
        return "redirect:/cargo/extCproduct/list.do?contractId="+contractId+"&contractProductId="+contractProductId;
    }

}
