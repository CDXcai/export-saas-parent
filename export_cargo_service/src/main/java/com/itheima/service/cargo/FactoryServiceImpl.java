package com.itheima.service.cargo;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.cargo.FactoryDao;
import com.itheima.domain.cargo.Factory;
import com.itheima.domain.cargo.FactoryExample;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
@Service
public class FactoryServiceImpl implements FactoryService {

    @Autowired
    private FactoryDao factoryDao;
    @Override
    public void save(Factory factory) {

    }

    @Override
    public void update(Factory factory) {

    }

    @Override
    public void delete(String id) {

    }

    @Override
    public Factory findById(String id) {
        return factoryDao.selectByPrimaryKey(id);
    }

    @Override
    public List<Factory> findAll(FactoryExample example) {
        List<Factory> list = factoryDao.selectByExample(example);
        return list;
    }
}
