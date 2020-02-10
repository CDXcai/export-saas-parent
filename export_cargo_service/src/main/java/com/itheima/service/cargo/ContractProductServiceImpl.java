package com.itheima.service.cargo;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.dao.cargo.ContractDao;
import com.itheima.dao.cargo.ContractProductDao;
import com.itheima.dao.cargo.ExtCproductDao;
import com.itheima.domain.cargo.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

@Service
public class ContractProductServiceImpl implements ContractProductService {

    @Autowired
    private ContractProductDao contractProductDao;


    @Autowired
    private ContractDao contractDao;
    /**
     * 新增货物
     * 1.动货物表 -新增
     * 1.1 赋值id
     * 1.2 拿到用户输入的金额和数量
     * 1.3 计算当前货物的总金额
     * 1.4 正式保存货物对象
     * 2.动合同表 -修改
     * 2.1 获得合同对象
     * 2.2 计算总金额
     * 2.3 计算货物的种类数量
     * 2.4 修改合同表数据
     * @param contractProduct
     */
    @Override
    public void save(ContractProduct contractProduct) {
        //1.动货物表 -新增
        //1.1 赋值id
        contractProduct.setId(UUID.randomUUID().toString());
        Double amount = 0d; //初始化总金额
        //1.2 拿到用户输入的金额和数量
        //1.3 计算当前货物的总金额
        if(contractProduct.getCnumber()!=null && contractProduct.getPrice()!=null){
            amount=contractProduct.getCnumber()*contractProduct.getPrice();
        }

        contractProduct.setAmount( amount );
        //1.4 正式保存货物对象
        contractProductDao.insertSelective(contractProduct);

        //2.动合同表 -修改
        //2.1 获得合同对象
        String contractId = contractProduct.getContractId();
        Contract contract = contractDao.selectByPrimaryKey(contractId);
        //2.2 计算总金额  总金额 = 原来的总金额+新增货物的总金额
       /* System.out.println(contractId);
        System.out.println(contract);
        System.out.println(amount);
        System.out.println(contract.getTotalAmount() );*/
        contract.setTotalAmount( contract.getTotalAmount() +  amount);
        //2.3 计算货物的种类数量  种类只加1  ,不考虑原本已经有的情况 (已经有的数据 可以通过修改数量)
        contract.setProNum( contract.getProNum() + 1 );
        //2.4 修改合同表数据
        contractDao.updateByPrimaryKeySelective(contract);
    }

    /**
     * 更新货物
     * 3.修改货物表之前 需要先查询原来货物的总金额
     * 1.货物表
     * 1.1 获得当前货物修改完的总金额-计算得到
     * 1.2 修改数据库中的货物表中总金额
     * 2.合同表
     * 2.1 拿到合同的对象
     * 2.2 赋值总金额-计算
     * 2.3 修改合同对象
     */
    @Override
    public void update(ContractProduct contractProduct) {
        //3.修改货物表之前 需要先查询原来货物的总金额
        ContractProduct oldContractProduct = contractProductDao.selectByPrimaryKey(contractProduct.getId());

        //1.货物表
        Double amount = 0d;
        //1.1 获得当前货物修改完的总金额-计算得到
        if(contractProduct.getCnumber() != null && contractProduct.getPrice() !=null){
            amount= contractProduct.getCnumber()*contractProduct.getPrice();
        }
        //1.2 修改数据库中的货物表中总金额
        contractProduct.setAmount( amount );
        contractProductDao.updateByPrimaryKeySelective(contractProduct);

        //2.合同表
        //2.1 拿到合同的对象
        String contractId = contractProduct.getContractId();
        Contract contract = contractDao.selectByPrimaryKey(contractId);
        //2.2 赋值总金额-计算  总金额 = 总金额-原来货物的金额 + 新维护的总金额
        contract.setTotalAmount( contract.getTotalAmount() - oldContractProduct.getAmount() + amount );
        //2.3 修改合同对象
        contractDao.updateByPrimaryKeySelective(contract);
    }

    @Autowired
    private ExtCproductDao extCproductDao;
    /**
     * 删除货物
     * 1.货物表-删除操作
     * 1.1 先将这个货物表中的对象查询出来
     * 1.2 正常删除即可
     * 2.附件表-删除操作
     * 2.1 根据货物表对象查询到当前附件数据
     * 2.2 删除附件数据即可
     * 3.合同表-修改操作
     * 3.1 根据货物对象查询到合同对象
     * 3.2 修改总金额
     * 3.3 修改货物种类数量 和 附件种类数量
     * 3.4 保存到数据库中即可
     * @param id
     */
    @Override
    public void delete(String id) {
        //1.货物表-删除操作
        //1.1 先将这个货物表中的对象查询出来
        ContractProduct contractProduct = contractProductDao.selectByPrimaryKey(id);

        //获得当前需要删除货物的总金额
        Double amount = contractProduct.getAmount();

        //2.附件表-删除操作
        //2.1 根据货物表对象查询到当前附件数据  根据货物表的id查询 附件表的数据
        ExtCproductExample extCproductExample = new ExtCproductExample();
        ExtCproductExample.Criteria extCproductExampleCriteria = extCproductExample.createCriteria();
        extCproductExampleCriteria.andContractProductIdEqualTo(id);
        List<ExtCproduct> extCproducts = extCproductDao.selectByExample(extCproductExample);
        //2.2 删除附件数据即可
        //获得每一个附件的对象
        if(extCproducts!= null && extCproducts.size()>0 ){
            for (ExtCproduct extCproduct : extCproducts) {
                //计算总金额  amount= 删除的货物金额  + 每一次删除附件的金额  = 此次操作中删除的所有货物和附件的金额
                amount += extCproduct.getAmount();
                //删除
                extCproductDao.deleteByPrimaryKey(extCproduct.getId());
            }
        }

        //1.2 正常删除货物对象即可
        contractProductDao.deleteByPrimaryKey(id);

        //3.合同表-修改操作
        //3.1 根据货物对象查询到合同对象
        Contract contract = contractDao.selectByPrimaryKey(contractProduct.getContractId());
        //3.2 修改总金额
        contract.setTotalAmount( contract.getTotalAmount() - amount );
        //3.3 修改货物种类数量 和 附件种类数量
        contract.setProNum( contract.getProNum()-1 );//货物种类数量
        contract.setExtNum( contract.getExtNum() - extCproducts.size() );//附件种类数量
        //3.4 保存到数据库中即可
        contractDao.updateByPrimaryKeySelective(contract);
    }

    @Override
    public ContractProduct findById(String id) {
        return contractProductDao.selectByPrimaryKey(id);
    }

    @Override
    public PageInfo findAll(ContractProductExample example, int page, int size) {
        PageHelper.startPage(page, size);
        List<ContractProduct> list = contractProductDao.selectByExample(example);
        return new PageInfo(list);
    }

    /**
     * 批量上传货物数据
     * @param list
     */
    @Override
    public void saveList(List<ContractProduct> list) {
        for (ContractProduct contractProduct : list) {
            //一定调用save的方法  ( save有业务逻辑 )
            this.save(contractProduct);
        }
    }
}
