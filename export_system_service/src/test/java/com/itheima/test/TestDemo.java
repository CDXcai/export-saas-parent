package com.itheima.test;

import com.itheima.domain.company.Company;
import com.itheima.service.company.CompanyService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
/*classpath:spring/applicationContext-tx.xml 只加载了service的配置文件 没有dao*/
/*运行时期 两个工程 一个工程不能够直接加载另一个工程的配置文件 需要再加一个*号 */
/**
 *    classpath*: 加载当前工程及其子工程的配置文件
 *    spring/applicationContext-*.xml  通配符
 *    创建工程 创建目录 创建配置文件 都需要有一定的规律
 */
@ContextConfiguration(locations = "classpath*:spring/applicationContext-*.xml")
public class TestDemo {
    @Autowired
    private CompanyService companyService;
    @Test
    public void test(){
        List<Company> companyList = companyService.findAll();
        System.out.println(companyList);
    }
}
