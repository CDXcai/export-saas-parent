package com.itheima.service.cargo;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.dao.cargo.ContractDao;
import com.itheima.domain.cargo.Contract;
import com.itheima.domain.cargo.ContractExample;
import com.itheima.domain.vo.ContractProductVo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class ContractServiceImpl implements  ContractService{
    @Autowired
    private ContractDao contractDao;
    @Override
    public Contract findById(String id) {
        return contractDao.selectByPrimaryKey(id);
    }

    @Override
    public void save(Contract contract) {
        contract.setId(UUID.randomUUID().toString());
        //提供一些默认值 用户不输入的值
        contract.setTotalAmount(0d);//总金额
        contract.setState(0);//草稿状态
        contract.setProNum(0);//货物种类数量
        contract.setExtNum(0);//附件种类数量
        contract.setCreateTime(new Date());//创建时间
        contractDao.insertSelective(contract);
    }

    @Override
    public void update(Contract contract) {
        contractDao.updateByPrimaryKeySelective(contract);
    }

    /**
     * 这样写不对
     * 作业:
     * 购销合同删除
     * 1.删除购销合同表数据
     * 2.删除货物表数据
     * 3.删除附件表数据
     * @param id
     */
    @Override
    public void delete(String id) {
        contractDao.deleteByPrimaryKey(id);
    }

    @Override
    public PageInfo findAll(ContractExample example, int page, int size) {
        PageHelper.startPage(page,size);
        List<Contract> list = contractDao.selectByExample(example);
        return new PageInfo(list);
    }

    /**
     * 出货表数据查询
     * @param inputDate
     * @param companyId
     * @return
     */
    @Override
    public List<ContractProductVo> findByShipTime(String inputDate, String companyId) {
        return contractDao.findByShipTime(inputDate ,companyId );
    }
}
