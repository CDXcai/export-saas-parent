package com.itheima.dao.stat;

import java.util.List;
import java.util.Map;

public interface StatDao {
    //查询厂家销售统计
    List<Map> findFactory(String companyId);

    //产品的销售排行
    List<Map> findSell(String companyId);

    //系统访问压力图
    List<Map> findOnline(String companyId);
}
