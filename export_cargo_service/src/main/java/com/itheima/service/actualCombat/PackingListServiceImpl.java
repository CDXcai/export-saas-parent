package com.itheima.service.actualCombat;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.dao.actualCombat.PackingListDao;
import com.itheima.dao.export.ExportDao;
import com.itheima.domain.actualCombat.PackingList;
import com.itheima.domain.actualCombat.PackingListExample;
import com.itheima.domain.export.Export;
import com.itheima.domain.export.ExportExample;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class PackingListServiceImpl implements PackingListService {

    @Autowired
    private PackingListDao packingListDao;

    @Autowired
    private ExportDao exportDao;
    /**
     * 保存装箱单信息  装箱单的信息从报运表中获得
     * 1.报运表
     * 1.1 查询出报运表的信息
     * 1.2 修改报运表数据的状态(状态修改为3 . 让装箱的时候 已经装箱的数据 不能再次查询出来的)
     * 2.装箱单的表 执行新增请求
     * 组装装箱单信息 添加到数据库即可
     *
     * @param packingList
     */
    @Override
    public void save(PackingList packingList) {
        //1.报运表
        String exportIds = packingList.getExportIds();
        String exportNos= "";
        if(!StringUtils.isBlank(exportIds)){//id非空
            //切割id
            String[] splitExportIds = exportIds.split(",");
            for (String splitExportId : splitExportIds) {
                //查询对象
                //1.1 查询出报运表的信息
                Export export = exportDao.selectByPrimaryKey(splitExportId);
                //1.2 修改报运表数据的状态(状态修改为3 . 让装箱的时候 已经装箱的数据 不能再次查询出来的)
                //修改状态
                export.setState(3);
                exportNos+=export.getCustomerContract();//拼接合同号
                exportDao.updateByPrimaryKeySelective(export);
            }
        }


        //2.装箱单的表 执行新增请求
        //组装装箱单信息 添加到数据库即可
        //Field 'packing_list_id' doesn't have a default value; nested exception is java.sql.SQLException: Field 'packing_list_id' doesn't have a default value
        packingList.setPackingListId(UUID.randomUUID().toString());
        packingList.setExportNos(exportNos);
        packingList.setState(0L);//草稿状态
        packingListDao.insertSelective(packingList);
    }

    @Override
    public void update(PackingList packingList) {
        packingListDao.updateByPrimaryKeySelective(packingList);
    }

    /**
     * 删除装箱单
     * 1.报运表数据的修改
     * 2.删除装箱单信息
     * @param id
     */
    @Override
    public void delete(String id) {
        //报运表数据的修改\
        //先查询出装箱单数据
        PackingList packingList = packingListDao.selectByPrimaryKey(id);
        //获得报运单的id
        String exportIds = packingList.getExportIds();
        if(!StringUtils.isBlank(exportIds)){
            //数组数据
            String[] splitIds = exportIds.split(",");
            //1.查询到报运表数据
            ExportExample exportExample = new ExportExample();
            ExportExample.Criteria criteria = exportExample.createCriteria();
            criteria.andIdIn(Arrays.asList(splitIds));
            List<Export> exports = exportDao.selectByExample(exportExample);
            //遍历数据 修改数据
            for (Export export : exports) {
                export.setState(2);
                exportDao.updateByPrimaryKeySelective(export);//修改状态
            }
        }


        //2.循环对象 修改状态
        //删除装箱单
        packingListDao.deleteByPrimaryKey(id);
    }

    @Override
    public PackingList findById(String id) {
        PackingList packingList = packingListDao.selectByPrimaryKey(id);
        return packingList;
    }

    @Override
    public PageInfo findAll(PackingListExample example, int page, int size) {
        PageHelper.startPage(page , size);
        List<PackingList> packingLists = packingListDao.selectByExample(example);
        return new PageInfo(packingLists);
    }
}
