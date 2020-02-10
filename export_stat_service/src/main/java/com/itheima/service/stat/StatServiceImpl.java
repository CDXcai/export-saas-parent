package com.itheima.service.stat;


import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.stat.StatDao;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

@Service
public class StatServiceImpl implements StatService {
    @Autowired
    private StatDao statDao;
    @Override
    public List<Map> findFactory(String companyId) {
        return statDao.findFactory(companyId);
    }

    @Override
    public List<Map> findSell(String companyId) {
        return statDao.findSell(companyId);
    }

    @Override
    public List<Map> findOnline(String companyId) {
        return statDao.findOnline(companyId);
    }
}
