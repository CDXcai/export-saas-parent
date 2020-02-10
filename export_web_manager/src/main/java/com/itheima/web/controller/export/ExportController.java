package com.itheima.web.controller.export;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.itheima.domain.cargo.ContractExample;
import com.itheima.domain.export.Export;
import com.itheima.domain.export.ExportExample;
import com.itheima.domain.export.ExportProduct;
import com.itheima.domain.export.ExportProductExample;
import com.itheima.domain.vo.ExportProductVo;
import com.itheima.domain.vo.ExportResult;
import com.itheima.domain.vo.ExportVo;
import com.itheima.service.cargo.ContractService;
import com.itheima.service.export.ExportProductService;
import com.itheima.service.export.ExportService;
import com.itheima.web.controller.base.BaseController;
import org.apache.cxf.jaxrs.client.WebClient;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/cargo/export")
public class ExportController  extends BaseController {

    @Reference
    private ContractService contractService;
    /**
     * 合同管理: 查询已经上报的数据
     * @return
     */
    @RequestMapping("/contractList")
    public String contractList(@RequestParam(defaultValue = "1") Integer page , @RequestParam(defaultValue = "5") Integer size){
        ContractExample contractExample = new ContractExample();
        ContractExample.Criteria criteria = contractExample.createCriteria();
        criteria.andCompanyIdEqualTo( super.companyId );
        criteria.andStateEqualTo(1);
        PageInfo pageInfo = contractService.findAll(contractExample, page, size);
        request.setAttribute("page",pageInfo);

        return "cargo/export/export-contractList";
    }

    /**
     * 跳转报运
     * 1.基本的报运信息
     * 2.将购销合同数据转换成报运单数据
     * 3.将购销合同下货物数据转换成报运单下货物数据
     * 4.将购销合同下附件数据转换成报运单下附件数据
     * @return
     */
    @RequestMapping("/toExport")
    public String toExport(String id ){ //接收了多个购销合同id 以逗号方式拼接
        System.out.println(id);
        //传入 购销合同的id 方便一会报运使用
        // <input type="text" name="contractIds" value="${id}"> 页面使用 存入隐藏域中
        request.setAttribute("id" , id);
        return "cargo/export/export-toExport";
    }


    @Reference
    private ExportService exportService;
    /**
     * 查询出口报运数据
     * @param page
     * @param size
     * @return
     */
    @RequestMapping("/list")
    public String list(@RequestParam(defaultValue = "1") Integer page , @RequestParam(defaultValue = "5") Integer size){
        ExportExample exportExample = new ExportExample();
        ExportExample.Criteria criteria = exportExample.createCriteria();
        criteria.andCompanyIdEqualTo(super.companyId);
        //1.查询出口报运单的数据
        PageInfo pageInfo = exportService.findAll(exportExample, page, size);
        request.setAttribute("page" , pageInfo);
        return "cargo/export/export-list";
    }

    /**
     * 新增或修改报运单
     * @return
     */
    @RequestMapping("/edit")
    public String edit(Export export){
        export.setCompanyId(super.companyId);
        export.setCompanyName(super.companyName);
        if(StringUtils.isEmpty(export.getId())){
            exportService.save(export);
        }else{
            //提交两种数据
            //1.报运单的修改数据
            //2.报运单下货物的修改数据
            //System.out.println(export);
            exportService.update(export);
        }
        return "redirect:/cargo/export/list.do";
    }

    /**
     * 跳转报运单修改页面
     * @return
     */
    @RequestMapping("/toUpdate")
    public String toUpdate(String id){
        //根据id查询报运单信息
        Export export = exportService.findById(id);
        request.setAttribute("export" ,export);
        //System.out.println(export);
        return "cargo/export/export-update";
    }


    @Reference
    private ExportProductService exportProductService;
    /**
     * 电子报运
     * id为报运单的id
     * 1.查询到当前报运单的信息 (Export)
     * 2.查询到当前报运单下货物的信息(ExportProduct)
     * 3.组装海关需要的报运单对象信息(ExportVo)
     * 4.组装海关需要的报运单下货物对象信息)_ (ExportProductVo)
     * 5.发送请求
     * 6.接收结果-再次发送请求
     * 7.修改自己的数据库信息
     * @return
     */
    @RequestMapping("/exportE")
    public String exportE(String id ){
        //1.查询到当前报运单的信息 (Export)
        Export export = exportService.findById(id);
        //2.查询到当前报运单下货物的信息(ExportProduct)
        ExportProductExample exportProductExample = new ExportProductExample();
        ExportProductExample.Criteria exportProductExampleCriteria = exportProductExample.createCriteria();
        exportProductExampleCriteria.andExportIdEqualTo( id );
        List<ExportProduct> exportProductList = exportProductService.findAll(exportProductExample);

        //3.组装海关需要的报运单对象信息(ExportVo)
        ExportVo exportVo = new ExportVo();
        //3.1 只能封装基本数据 一样的数据
        BeanUtils.copyProperties(export ,exportVo );
        //exportVo.setId(); 给不给无所谓  最终海关会自己生成一个id
        exportVo.setExportId( export.getId() ); //将自己的id 交给海关管理 以后需要通过此id进行查询


        List<ExportProductVo> exportProductVoList = new ArrayList<ExportProductVo>();
        //4.组装海关需要的报运单下货物对象信息 (ExportProductVo)
        //4.1 遍历
        for (ExportProduct exportProduct : exportProductList) {
            //vo对象创建
            ExportProductVo exportProductVo = new ExportProductVo();
            //赋值数据
            BeanUtils.copyProperties(exportProduct , exportProductVo);
            //特殊数据
            //交给海关保存货物的id    id属性海关会自己产生
            exportProductVo.setExportProductId( exportProduct.getId() );
            //建立vo之间的关系
            exportProductVo.setExportId(export.getId()); //一个就够了  建立数据库表和表之间的关系  java中也需要建立关系
            exportProductVo.setEid(export.getId());
            exportProductVoList.add(exportProductVo);//将vo装入list
        }

        //将报运货物的vo对象 给 报运单vo对象
        exportVo.setProducts(exportProductVoList);// java中也需要建立关系


        //System.out.println("发送海关平台方法");
        //5.发送请求
        WebClient webClient = WebClient.create("http://localhost:9096/ws/export/user");
        webClient.post(exportVo);
        //System.out.println("结束发送方法");
        //6.接收结果-再次发送请求
        webClient = WebClient.create("http://localhost:9096/ws/export/user/"+export.getId());//传入的是报运单的id
        ExportResult exportResult = webClient.get(ExportResult.class);
        //System.out.println(exportResult);
        //7.修改自己的数据库信息
        exportService.exportE(exportResult);
        return "redirect:/cargo/export/list.do";
    }

    /**
     * 提交 cancel
     * @return
     */
    @RequestMapping("/submit")
    public String submit(String id ){
        Export export = new Export();
        export.setId(id);
        export.setState(1);
        exportService.update(export);
        return "redirect:/cargo/export/list.do";
    }

    /**
     * 取消 cancel
     * @return
     */
    @RequestMapping("/cancel")
    public String cancel(String id ){
        Export export = new Export();
        export.setId(id);
        export.setState(0);
        exportService.update(export);
        return "redirect:/cargo/export/list.do";
    }
}

