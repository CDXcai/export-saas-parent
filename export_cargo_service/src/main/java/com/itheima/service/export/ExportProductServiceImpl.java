package com.itheima.service.export;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.export.ExportProductDao;
import com.itheima.domain.export.ExportProduct;
import com.itheima.domain.export.ExportProductExample;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class ExportProductServiceImpl implements ExportProductService {
    @Autowired
    private ExportProductDao exportProductDao;
    @Override
    public List<ExportProduct> findAll(ExportProductExample example) {
        return exportProductDao.selectByExample(example);
    }
}
