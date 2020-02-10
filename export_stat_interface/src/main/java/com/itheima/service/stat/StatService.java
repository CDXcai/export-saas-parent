package com.itheima.service.stat;

import java.util.List;
import java.util.Map;

public interface StatService {
    List<Map> findFactory(String companyId);

    List<Map> findSell(String companyId);

    List<Map> findOnline(String companyId);
}
