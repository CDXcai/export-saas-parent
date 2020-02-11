package com.itheima.service.actualCombat;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.dao.actualCombat.PackingListDao;
import com.itheima.dao.actualCombat.ShippingDao;
import com.itheima.dao.export.ExportDao;
import com.itheima.domain.actualCombat.PackingList;
import com.itheima.domain.actualCombat.Shipping;
import com.itheima.domain.actualCombat.ShippingExample;
import com.itheima.domain.export.Export;
import com.itheima.service.export.ExportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;

/**
 * @author: guojy
 * @date: 2020/2/11 8:56
 * @Description: ${TODO}
 * @version:
 */
@Service
public class ShippingServiceImpl implements ShippingService {
    @Autowired
    private ShippingDao shippingDao;
    @Autowired
    private PackingListDao packingListDao;
    @Autowired
    private ExportDao exportDao;

    @Override
    public void save(Shipping shipping) {

        // 修改装箱单的状态为已委托 state = 2
        String id = shipping.getShippingOrderId();
        PackingList packingList = packingListDao.selectByPrimaryKey(id);
        packingList.setPackingListId(id);
        packingList.setState(2L);
        packingListDao.updateByPrimaryKeySelective(packingList);

        // 循环修改报运单的状态为 已委托 state = 4 ，此处的状态值，数据库中的备注和页面的不一致，最后以页面的为准
        /**
         * 数据库 0-草稿 1-已上报 2-装箱 3-委托 4-发票 5-财务
         * 页面： <c:if test="${o.state==0}">草稿</c:if>
         *        <c:if test="${o.state==1}"><font color="green">已上报</font></c:if>
         *        <c:if test="${o.state==2}"><font color="red">已报运</font></c:if>
         *        <c:if test="${o.state==3}"><font color="red">已装箱</font></c:if>
         *        <c:if test="${o.state==4}"><font color="red">已委托</font></c:if>
         */
        String exportIds = packingList.getExportIds();
        System.out.println("exportIds = " + exportIds);
        if (!StringUtils.isEmpty(exportIds)) {
            String[] exportIdArr = exportIds.split(",");
          //  System.out.println("id集合"+Arrays.toString(exportIdArr));
            for (String exportId : exportIdArr) {
               // System.out.println("exportId = " + exportId);
                Export export = exportDao.selectByPrimaryKey(exportId);
               //stem.out.println("装箱单的export = " + export);
                export.setState(4);
                exportDao.updateByPrimaryKeySelective(export);
            }
        }

        // 设置委托单初始化状态
        shipping.setState(0);
        // 保存
        shippingDao.insertSelective(shipping);
    }

    @Override
    public void update(Shipping shipping) {
        shippingDao.updateByPrimaryKeySelective(shipping);
    }

    @Override
    public void delete(String id) {
        // 查询委托单
        //Shipping shipping = shippingDao.selectByPrimaryKey(id);
        // 删除委托单
        shippingDao.deleteByPrimaryKey(id);
        //修改 装箱单状态  已委托-》 已提交   state = 1
        // 修改装箱单的状态为已委托 state = 2
        // 委托单的id和装箱单的id一致，直接使用
        PackingList packingList = packingListDao.selectByPrimaryKey(id);
        packingList.setPackingListId(id);
        packingList.setState(1L);
        packingListDao.updateByPrimaryKeySelective(packingList);

        // 修改保运单状态：已装箱 state = 3
        String exportIds = packingList.getExportIds();
        System.out.println("exportIds = " + exportIds);
        if (!StringUtils.isEmpty(exportIds)) {
            String[] exportIdArr = exportIds.split(",");
            //System.out.println("id集合"+Arrays.toString(exportIdArr));
            for (String exportId : exportIdArr) {
                //System.out.println("exportId = " + exportId);
                Export export = exportDao.selectByPrimaryKey(exportId);
                //System.out.println("装箱单的export = " + export);
                export.setState(3);
                exportDao.updateByPrimaryKeySelective(export);
            }
        }

    }

    @Override
    public Shipping findById(String id) {
        return shippingDao.selectByPrimaryKey(id);
    }

    @Override
    public PageInfo findAll(ShippingExample example, int page, int size) {
        PageHelper.startPage(page, size);
        List<Shipping> list = shippingDao.selectByExample(example);
        return new PageInfo(list);
    }

    @Override
    public void deleteByContractID(String ContractID) {

    }
}
