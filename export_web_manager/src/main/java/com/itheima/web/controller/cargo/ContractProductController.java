package com.itheima.web.controller.cargo;


import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.itheima.domain.cargo.ContractProduct;
import com.itheima.domain.cargo.ContractProductExample;
import com.itheima.domain.cargo.Factory;
import com.itheima.domain.cargo.FactoryExample;
import com.itheima.service.cargo.ContractProductService;
import com.itheima.service.cargo.FactoryService;
import com.itheima.web.controller.base.BaseController;
import com.itheima.web.controller.utils.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/cargo/contractProduct")
public class ContractProductController  extends BaseController {

    @Reference
    private ContractProductService contractProductService;

    @Reference
    private FactoryService factoryService;
    /**
     * 页面由新增和查询列表页面组成
     * @param contractId 合同id
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value = "/list" )
    public String list(String contractId,@RequestParam(defaultValue = "1") Integer page , @RequestParam(defaultValue = "5") Integer size){
        //1.查询购销合同下的货物列表数据
        ContractProductExample example = new ContractProductExample();
        ContractProductExample.Criteria criteria = example.createCriteria();
        criteria.andContractIdEqualTo(contractId);

        PageInfo pageInfo = contractProductService.findAll(example, page, size);
        request.setAttribute("page" , pageInfo);

        //2.准备新增页面中-> 新增货物的厂家下拉列表数据
        FactoryExample factoryExample = new FactoryExample();
        FactoryExample.Criteria exampleCriteria = factoryExample.createCriteria();
        exampleCriteria.andCtypeEqualTo("货物");
        List<Factory> factoryList = factoryService.findAll(factoryExample);
        request.setAttribute("factoryList" , factoryList);

        //3.有bug 传入购销合同的id -> 为了新增使用
        request.setAttribute("contractId" , contractId);


        return "cargo/product/product-list";
    }

    @Autowired
    private FileUploadUtil uploadUtil;
    /**
     * 添加货物的时候 如果数据为空
     * 表单为多部分表单形式提交 而我们没有进行springmvc配置 上传文件的组件 解析不了数据
     * 1.接收到图片
     * 2.上传到七牛云(资料有改造好的工具类)
     *
     * @return
     */
    @RequestMapping(value = "/edit")
    public String edit(ContractProduct contractProduct , MultipartFile productPhoto){
        try {
            //System.out.println(productPhoto);
            //System.out.println(contractProduct);
            contractProduct.setCompanyId(super.companyId);
            contractProduct.setCompanyName(super.companyName);
            if(StringUtils.isEmpty(contractProduct.getId())){
                //上传图片
                if(productPhoto != null ){
                    //返回的是图片的路径
                    String filePath = uploadUtil.upload(productPhoto);
                    //将图片赋值给数据库属性
                    contractProduct.setProductImage(filePath);
                }

                contractProductService.save(contractProduct);
            }else{
                contractProductService.update(contractProduct);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/cargo/contractProduct/list.do?contractId="+contractProduct.getContractId();
    }


    /**
     * 跳转修改货物的页面
     * @param id
     * @return
     */
    @RequestMapping(value = "/toUpdate")
    public String toUpdate(String id ){//货物的id
        //1.查询生产厂家
        FactoryExample factoryExample = new FactoryExample();
        FactoryExample.Criteria exampleCriteria = factoryExample.createCriteria();
        exampleCriteria.andCtypeEqualTo("货物");
        List<Factory> factoryList = factoryService.findAll(factoryExample);
        request.setAttribute("factoryList" , factoryList);

        //2.查询货物的信息数据
        ContractProduct contractProduct = contractProductService.findById(id);
        request.setAttribute("contractProduct" , contractProduct);

        return "/cargo/product/product-update";
    }

    /**
     * 删除货物
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    public String delete(String id , String contractId){//第二个合同id为了页面跳转使用的
        contractProductService.delete(id);
        return "redirect:/cargo/contractProduct/list.do?contractId="+contractId;
    }
}
