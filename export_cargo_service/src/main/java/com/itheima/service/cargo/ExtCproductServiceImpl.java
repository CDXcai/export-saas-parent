package com.itheima.service.cargo;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.dao.cargo.ContractDao;
import com.itheima.dao.cargo.ExtCproductDao;
import com.itheima.domain.cargo.Contract;
import com.itheima.domain.cargo.ExtCproduct;
import com.itheima.domain.cargo.ExtCproductExample;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

@Service
public class ExtCproductServiceImpl implements ExtCproductService {
    @Autowired
    private ExtCproductDao extCproductDao;


    @Autowired
    private ContractDao contractDao;
    /**
     * 添加附件对象
     * 1.附件表
     * 1.1 附件的数量和单价 计算总金额
     * 1.2 赋值随机的id
     * 1.3 正常保存附件表即可
     * 2.合同表
     * 2.1 根据附件对象获得合同对象
     * 2.2 修改合同的总金额
     * 2.3 修改合同中附件的种类数量
     * 2.4 保存合同
     * @param extCproduct
     */
    @Override
    public void save(ExtCproduct extCproduct) {
        //1.附件表
        //1.1 附件的数量和单价 计算总金额
        Double amount = 0d;
        if(extCproduct.getPrice()!=null && extCproduct.getCnumber()!=null){
            amount = extCproduct.getPrice()*extCproduct.getCnumber();
        }
        extCproduct.setAmount(amount);
        //1.2 赋值随机的id
        extCproduct.setId(UUID.randomUUID().toString());
        //1.3 正常保存附件表即可
        extCproductDao.insertSelective(extCproduct);
        //2.合同表
        //2.1 根据附件对象获得合同对象
        String contractId = extCproduct.getContractId();
        Contract contract = contractDao.selectByPrimaryKey(contractId);
        //2.2 修改合同的总金额
        contract.setTotalAmount( contract.getTotalAmount() + amount );
        //2.3 修改合同中附件的种类数量
        contract.setExtNum( contract.getExtNum() + 1);
        //2.4 保存合同
        contractDao.updateByPrimaryKeySelective(contract);
    }

    /**
     * 修改附件
     * 3.查询到原来的附件对象
     * 1.附件表
     * 1.1 计算修改之后的总金额
     * 1.2 重新赋值回去
     * 1.3 直接修改即可
     * 2.合同表
     * 2.1 根据附件得到合同对象
     * 2.2 修改合同的总金额= 合同总金额 - 原来的附件金额 + 新增加的附件金额
     * 2.3 修改合同即可
     * @param extCproduct
     */
    @Override
    public void update(ExtCproduct extCproduct) {
        //3.查询到原来的附件对象
        ExtCproduct oldExtCProduct = extCproductDao.selectByPrimaryKey(extCproduct.getId());
        //1.附件表
        Double amount = 0d;
        //1.1 计算修改之后的总金额
        if(extCproduct.getPrice() != null && extCproduct.getCnumber() != null){
            amount = extCproduct.getPrice()*extCproduct.getCnumber();
        }
        //1.2 重新赋值回去
        extCproduct.setAmount( amount );
        //1.3 直接修改即可
        extCproductDao.updateByPrimaryKeySelective(extCproduct);
        //2.合同表
        //2.1 根据附件得到合同对象
        Contract contract = contractDao.selectByPrimaryKey(oldExtCProduct.getContractId());
        //2.2 修改合同的总金额= 合同总金额 - 原来的附件金额 + 新增加的附件金额
        contract.setTotalAmount( contract.getTotalAmount() - oldExtCProduct.getAmount() +  amount);
        //2.3 修改合同即可
        contractDao.updateByPrimaryKeySelective(contract);
    }

    /**
     * 删除附件
     * 1.附件表
     * 1.1 获得附件即可
     * 1.2 删除附件
     * 2.合同表
     * 2.1 获得合同对象
     * 2.2 修改合同对象的总金额
     * 2.3 修改合同对象附件数量
     * 2.4 修改合同
     * @param id
     */
    @Override
    public void delete(String id) {
        //1.附件表
        //1.1 获得附件即可
        ExtCproduct extCproduct = extCproductDao.selectByPrimaryKey(id);
        //1.2 删除附件
        extCproductDao.deleteByPrimaryKey(id);
        //2.合同表
        //2.1 获得合同对象
        Contract contract = contractDao.selectByPrimaryKey(extCproduct.getContractId());
        //2.2 修改合同对象的总金额
        contract.setTotalAmount( contract.getTotalAmount() -  extCproduct.getAmount() );
        //2.3 修改合同对象附件数量
        contract.setExtNum( contract.getExtNum() -1 );
        //2.4 修改合同
        contractDao.updateByPrimaryKeySelective(contract);

    }

    @Override
    public ExtCproduct findById(String id) {
        return extCproductDao.selectByPrimaryKey(id);
    }

    @Override
    public PageInfo findAll(ExtCproductExample example, int page, int size) {
        PageHelper.startPage(page, size);
        List<ExtCproduct> list = extCproductDao.selectByExample(example);
        return new PageInfo(list);
    }
}
