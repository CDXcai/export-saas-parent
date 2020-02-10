package com.itheima.web.controller.cargo;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.itheima.domain.cargo.Contract;
import com.itheima.domain.cargo.ContractExample;
import com.itheima.domain.system.User;
import com.itheima.service.cargo.ContractService;
import com.itheima.web.controller.base.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 购销合同
 */
@Controller
@RequestMapping("/cargo/contract")
public class ContractController extends BaseController {

    @Reference
    private ContractService contractService;
    @RequestMapping(value = "/list" )
    public String list(@RequestParam(defaultValue = "1") Integer page , @RequestParam(defaultValue = "5") Integer size){
        //1.创建example 对象
        ContractExample example = new ContractExample();
        //排序
        example.setOrderByClause("create_time desc");

        //2.创建条件封装对象
        ContractExample.Criteria criteria = example.createCriteria();
        criteria.andCompanyIdEqualTo( super.companyId ); //-企业管理员

        /**
         *
         *  0作为内部控制，租户企业不能使用
         *      0-saas管理员
         *      1-企业管理员
         *      2-管理所有下属部门和人员
         *      3-管理本部门
         *      4-普通员工
         *
         *private Integer degree;
         */
        //判断用户的身份
        User loginUser =(User) session.getAttribute("loginUser");
        if(loginUser.getDegree() == 4){//普通员工
            criteria.andCreateByEqualTo(loginUser.getId());
        }else if(loginUser.getDegree() == 3){//部门管理员
            criteria.andCreateDeptEqualTo( loginUser.getDeptId() );
        }else if(loginUser.getDegree() == 2){//区域管理员 100 100 %
            criteria.andCreateDeptLike( loginUser.getDeptId() + "%" );
        }


        PageInfo pageInfo = contractService.findAll(example, page, size);
        request.setAttribute("page" , pageInfo);

        return "cargo/contract/contract-list";
    }




    /**
     * 跳转添加页面
     */
    @RequestMapping(value = "/toAdd" )
    public String toAdd(){
        return "cargo/contract/contract-add";
    }

    /**
     */
    @RequestMapping(value = "/edit")
    public String edit(Contract contract){

        contract.setCompanyId(super.companyId);
        contract.setCompanyName(super.companyName);
        if(StringUtils.isEmpty(contract.getId())){
            User loginUser =(User) session.getAttribute("loginUser");
            contract.setCreateBy(loginUser.getId());//创建人
            contract.setCreateDept(loginUser.getDeptId()); // 创建人所在的部门
            contractService.save(contract);
        }else{
            contractService.update(contract);
        }


        return "redirect:/cargo/contract/list.do";
    }


    /**
     * 跳转修改页面
     */
    @RequestMapping(value = "/toUpdate" )
    public String toUpdate( String id){
        Contract contract = contractService.findById(id);
        request.setAttribute("contract" , contract);
        return "cargo/contract/contract-update";
    }

    /**
     * 删除
     * @return
     */
    @RequestMapping(value = "/delete" )
    public String delete(String id){
        contractService.delete(id);
        //跳转查询
        return "redirect:/cargo/contract/list.do";
    }
    /**
     *提交 修改合同状态变为1
     * @return
     */
    @RequestMapping(value = "/submit" )
    public String submit(String id){

        //1.new对象
        Contract contract = new Contract();
        contract.setId(id);
        contract.setState(1);//提交状态
        contractService.update(contract);

        //跳转查询
        return "redirect:/cargo/contract/list.do";
    }
   /**
     * 取消 修改合同状态变为0
     * @return
     */
    @RequestMapping(value = "/cancel" )
    public String cancel(String id){

        //1.new对象
        Contract contract = new Contract();
        contract.setId(id);
        contract.setState(0);//草稿状态
        contractService.update(contract);

        //跳转查询
        return "redirect:/cargo/contract/list.do";
    }

}
