package com.itheima.service.export;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.dao.cargo.ContractDao;
import com.itheima.dao.cargo.ContractProductDao;
import com.itheima.dao.cargo.ExtCproductDao;
import com.itheima.dao.export.ExportDao;
import com.itheima.dao.export.ExportProductDao;
import com.itheima.dao.export.ExtEproductDao;
import com.itheima.domain.cargo.*;
import com.itheima.domain.export.Export;
import com.itheima.domain.export.ExportExample;
import com.itheima.domain.export.ExportProduct;
import com.itheima.domain.export.ExtEproduct;
import com.itheima.domain.vo.ExportProductResult;
import com.itheima.domain.vo.ExportResult;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * <Description>
 *
 * @author hzb@itcast.cn
 * @version 1.0
 * @taskId: <br>
 * @createDate 2020/01/15 11:21
 * @see com.itheima.service.export
 */
@Service
public class ExportServiceImpl implements ExportService {

    @Autowired
    private ExportDao exportDao;

    @Autowired
    private ContractDao contractDao;

    @Autowired
    private ContractProductDao contractProductDao;
    @Autowired
    private ExportProductDao exportProductDao;
    @Override
    public Export findById(String id) {
        return exportDao.selectByPrimaryKey(id);
    }


    @Autowired
    private ExtCproductDao extCproductDao;
    @Autowired
    private ExtEproductDao extEproductDao;
    /**
     * 新增出口报运单
     * 1.查询购销合同数据(Contract) 转换成 报运单数据 (Export)
     *  只执行一次查询
     *  ContractExample contractExample = new ContractExample();
     *  ContractExample.Criteria contractExampleCriteria = contractExample.createCriteria();
     *  //Arrays.asList 将数组转换成集合   Arrays.toString() 将数组转换成字符串
     *  contractExampleCriteria.andIdIn( Arrays.asList(splitIdsArray) );
     *  contractDao.selectByExample(contractExample);
     *
     * 2.查询购销合同下货物数据(ContractProduct) 转换成 报运单下货物数据(ExportProduct)
     * 3.查询购销合同下附件数据(ExtCProduct) 转换成 报运单下附件数据(ExtEProduct)
     * 4.将已经上报的购销合同数据状态值修改为2
     * @param export
     */
    @Override
    public void save(Export export) {
        //1.查询购销合同数据(Contract) 转换成 报运单数据 (Export)
        //将购销合同的id字符串转换成数组
        String[] splitIdsArray = export.getContractIds().split(",");
        //1.1 查询购销合同
        String customerContract = "";
        for (String contractId : splitIdsArray) {
            //获得每一个购销合同 执行多次查询
            Contract contract = contractDao.selectByPrimaryKey(contractId);
            customerContract+= contract.getContractNo() + " ";//需要分隔符

            contract.setState(2);//已报运
            contractDao.updateByPrimaryKeySelective(contract);
        }
        //1.2 设置export 购销合同中涉及的数据
        export.setCustomerContract(customerContract);//合同号

        export.setId(UUID.randomUUID().toString()); //必须提前设置
        export.setInputDate(new Date());//制单日期
        export.setState(0);//状态为草稿



        //2.查询购销合同下货物数据(ContractProduct) 转换成 报运单下货物数据(ExportProduct)
        //2.1 查询到购销合同下货物的数据
        ContractProductExample contractProductExample = new ContractProductExample();
        ContractProductExample.Criteria contractProductExampleCriteria = contractProductExample.createCriteria();
        contractProductExampleCriteria.andContractIdIn(Arrays.asList( splitIdsArray ));
        //购销合同下货物数据
        List<ContractProduct> contractProducts = contractProductDao.selectByExample(contractProductExample);

        //此map就是用来描述 id的关系
        Map<String , String> idMap =new HashMap<String , String>();
        //2.2 转换成报运单下货物数据
        for (ContractProduct contractProduct : contractProducts) {
            //创建报运单下货物对象
            ExportProduct exportProduct = new ExportProduct();

            //2.3 保存即可  copyProperties : 将参数1的数据赋值给参数2的数据( 字段名称一致 )
            BeanUtils.copyProperties( contractProduct , exportProduct );//基本数据赋值了
            exportProduct.setId( UUID.randomUUID().toString() );//必须放在copyProperties后面

            //将原来的货物id和报运货物id建立map关系
            idMap.put( contractProduct.getId() , exportProduct.getId() );
            //建立报运货物和报运单的关系
            exportProduct.setExportId( export.getId() );

            //保存报运单下货物对象
            exportProductDao.insertSelective(exportProduct);
        }


        //3.查询购销合同下附件数据(ExtCProduct) 转换成 报运单下附件数据(ExtEProduct)
        //3.1 查询附件数据
        ExtCproductExample extCproductExample = new ExtCproductExample();
        ExtCproductExample.Criteria extCproductExampleCriteria = extCproductExample.createCriteria();
        extCproductExampleCriteria.andContractIdIn( Arrays.asList(splitIdsArray));
        List<ExtCproduct> extCproducts = extCproductDao.selectByExample(extCproductExample);
        //3.2 转换附件数据
        for (ExtCproduct oldCproduct : extCproducts) {
            //创建报运单下附件对象
            ExtEproduct newEProduct = new ExtEproduct();
            //赋值数据
            BeanUtils.copyProperties(oldCproduct ,newEProduct );
            //设置基本数据 id..
            newEProduct.setId(UUID.randomUUID().toString());

            //获得原来的货物id
            String oldContractProductId = oldCproduct.getContractProductId();
            String exportProductId = idMap.get(oldContractProductId);
            //建立附件和报运货物的关系
            newEProduct.setExportProductId(exportProductId);
            //建立附件和报运单的关系
            newEProduct.setExportId( export.getId() );

            //3.3 保存报运附件数据
            extEproductDao.insertSelective(newEProduct);
        }


        export.setExtNum(extCproducts.size()); //所有附件数量
        export.setProNum(contractProducts.size());//所有货物数量
        //保存出口报运对象
        exportDao.insertSelective(export);
    }

    /**
     * 更新出口报运单: 实际上将货物长宽高体积重量维护到数据库
     * @param export
     */
    @Override
    public void update(Export export) {
        //1.维护报运单表
        exportDao.updateByPrimaryKeySelective(export);
        //2.维护报运单下货物表  能不使用.size>0  就不使用
        if(export.getExportProducts()!=null && !export.getExportProducts().isEmpty() ){
            //遍历对象
            for (ExportProduct exportProduct : export.getExportProducts()) {
                //更新报运单下货物数据
                exportProductDao.updateByPrimaryKeySelective(exportProduct);
            }
        }
    }

    @Override
    public void delete(String id) {

    }

    @Override
    public PageInfo findAll(ExportExample example, int page, int size) {
        PageHelper.startPage(page , size);
        List<Export> list = exportDao.selectByExample(example);
        return new PageInfo(list);
    }

    @Override
    public List<Export> findAll(ExportExample example) {
        return exportDao.selectByExample(example);
    }

    /**
     * 报运成功后 修改自己数据库的数据
     * ExportResult{exportId='fb5ec491-00d1-46df-b9c9-62c59ee47c31', state=2, remark='报运成功',
     * products=[ExportProductResult{exportProductId='a664d2ee-cce5-41dd-9cef-15065acdfdc2', tax=11.2}, ExportProductResult{exportProductId='45ade244-5970-4889-b5ab-526747da0729', tax=10.8}, ExportProductResult{exportProductId='0a098950-87d0-43fc-864a-66ef6e49b045', tax=10.4}]
     * }
     * @param result
     * 1.修改报运单对象
     * 2.修改报运单下货物对象
     */
    @Override
    public void exportE(ExportResult result) {
        //1.修改报运单对象
        Export export = new Export();
        export.setId(result.getExportId());// id
        export.setState(result.getState()); //状态
        export.setRemark(result.getRemark());//备注
        exportDao.updateByPrimaryKeySelective(export);
        //2.修改报运单下货物数据
        for (ExportProductResult exportProductResult : result.getProducts()) {
            ExportProduct exportProduct = new ExportProduct();
            exportProduct.setId(exportProductResult.getExportProductId());//id
            exportProduct.setTax( exportProductResult.getTax() ); //税后金额
            exportProductDao.updateByPrimaryKeySelective(exportProduct);
        }
    }
}
