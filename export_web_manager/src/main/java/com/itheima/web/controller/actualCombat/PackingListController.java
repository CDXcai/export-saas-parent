package com.itheima.web.controller.actualCombat;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.itheima.domain.actualCombat.PackingList;
import com.itheima.domain.actualCombat.PackingListExample;
import com.itheima.domain.cargo.Contract;
import com.itheima.domain.cargo.ContractExample;
import com.itheima.domain.export.Export;
import com.itheima.domain.export.ExportExample;
import com.itheima.domain.system.User;
import com.itheima.service.actualCombat.PackingListService;
import com.itheima.service.cargo.ContractService;
import com.itheima.service.export.ExportService;
import com.itheima.web.controller.base.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

/**
 * 实战的装箱单
 */
@Controller
@RequestMapping("/actualCombat/packingList")
public class PackingListController extends BaseController {

    @Reference
    private PackingListService packingListService;
    /**
     * 访问装箱管理
     * 查询装箱单信息
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value = "/list" )
    public String list(@RequestParam(defaultValue = "1") Integer page , @RequestParam(defaultValue = "5") Integer size){
        PackingListExample example = new PackingListExample();
        PackingListExample.Criteria criteria = example.createCriteria();
        criteria.andCompanyIdEqualTo(companyId);

        PageInfo pageInfo = packingListService.findAll(example, page, size);

        request.setAttribute("page" , pageInfo);
        return "actualCombat/packingList/packingList-list";
    }


    @Reference
    private ExportService exportService;
    /**
     * 跳转添加页面
     */
    @RequestMapping(value = "/toAdd" )
    public String toAdd(){
        ExportExample exportExample = new ExportExample();
        ExportExample.Criteria criteria = exportExample.createCriteria();
        criteria.andCompanyIdEqualTo(super.companyId);
        criteria.andStateEqualTo(2L); //0d
        //查询报运的数据
        List<Export> exports = exportService.findAll(exportExample);
        request.setAttribute("exports" ,exports);
        return "actualCombat/packingList/packingList-add";
    }

    /**
     */
    @RequestMapping(value = "/edit")
    public String edit(PackingList packingList){
        packingList.setCompanyId(super.companyId);
        packingList.setCompanyName(super.companyName);
        if(StringUtils.isEmpty(packingList.getPackingListId())){
            packingListService.save(packingList);
        }else{
            System.out.println(packingList);
        }
        return "redirect:/actualCombat/packingList/list.do";
    }


    /**
     * 跳转修改页面
     */
    @RequestMapping(value = "/toUpdate" )
    public String toUpdate( String id){
        //1.查询装箱单信息
        PackingList packingList = packingListService.findById(id);
        request.setAttribute("packingList" , packingList);
        //2.查询报运单信息
        ExportExample exportExample = new ExportExample();
        ExportExample.Criteria criteria = exportExample.createCriteria();
        criteria.andCompanyIdEqualTo(super.companyId);
        //and state =2 and state = 3
        //criteria.andStateEqualTo(2L); //0d
        //criteria.andStateEqualTo(3L); //已经装箱的数据也需要查询出来
        List<Long> idList = new ArrayList<>();
        idList.add(2L);
        idList.add(3L);
        criteria.andStateIn(idList);
        //查询报运的数据
        List<Export> exports = exportService.findAll(exportExample);
        request.setAttribute("exports" ,exports);
        return "actualCombat/packingList/packingList-update";
    }

    /**
     * 删除
     * @return
     */
    @RequestMapping(value = "/delete" )
    public String delete(String id){
        packingListService.delete(id);
        return "redirect:/actualCombat/packingList/list.do";
    }


    /**
     *提交 修改装箱单状态变为1
     * @return
     */
    @RequestMapping(value = "/submit" )
    public String submit(String id){
        PackingList packingList = new PackingList();

        packingList.setPackingListId(id);
        packingList.setState(1L);
        packingListService.update(packingList);
        //跳转查询
        return "redirect:/actualCombat/packingList/list.do";
    }
   /**
     * 取消 修改装箱单状态变为0
     * @return
     */
    @RequestMapping(value = "/cancel" )
    public String cancel(String id){

        PackingList packingList = new PackingList();

        packingList.setPackingListId(id);
        packingList.setState(0L);
        packingListService.update(packingList);
        //跳转查询
        return "redirect:/actualCombat/packingList/list.do";
    }

}
