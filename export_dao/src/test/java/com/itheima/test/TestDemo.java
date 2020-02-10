package com.itheima.test;

import com.itheima.dao.cargo.FactoryDao;
import com.itheima.domain.cargo.Factory;
import com.itheima.domain.cargo.FactoryExample;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/applicationContext-dao.xml")
public class TestDemo {
    /*@Test
    public void testMyBatis() throws Exception {
        InputStream is = Resources.getResourceAsStream("SqlSessionConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        CompanyDao companyDao = sqlSession.getMapper(CompanyDao.class);
        List<Company> companyList = companyDao.findAll();
        for (Company company : companyList) {
            System.out.println(company);
        }
    }*/

    @Autowired
    private FactoryDao factoryDao;

    /**
     * updateByPrimaryKey 全字段全改
     * updateByPrimaryKeySelective 有值就修改 没值就不修改
     * @throws Exception
     */
    @Test
    public void testSpringMyBatis() throws Exception {
        Factory factory = new Factory();
        factory.setId("1111");
        factory.setFactoryName("xxxxx");
        factoryDao.updateByPrimaryKeySelective(factory);
    }


    /**
     * Example 是特殊的类 , 条件封装类
     *
     * 2.example封装了 特殊的对象 Criteria  此对象是所有条件的集合对象
     * example封装了 还可以直接操作排序情况
     * select * from co_factory WHERE ( ctype = ? ) order by create_time desc
     *
     *
     *  select * from co_factory   -->  factoryDao.selectByExample(null); 查询所有
     *  select * from co_factory WHERE ( ctype = ? )
     * @throws Exception
     * mybatis逆向工程 只能生成最基本的增删改查 不能够生成多表(还是需要自己写)
     *
     */
    @Test
    public void testSelectByExample() throws Exception {
        //1.创建example对象
        FactoryExample example = new FactoryExample();

        example.setOrderByClause("create_time desc");



        //2.example封装了 特殊的对象 Criteria  此对象是所有条件的集合对象
        FactoryExample.Criteria exampleCriteria = example.createCriteria();

        exampleCriteria.andCtypeEqualTo("货物");

        List<Factory> factoryList = factoryDao.selectByExample(example);

        System.out.println(factoryList);

    }
}
