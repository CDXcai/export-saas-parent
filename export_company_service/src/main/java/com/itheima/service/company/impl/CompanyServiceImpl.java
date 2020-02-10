package com.itheima.service.company.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.common.entity.PageResult;
import com.itheima.dao.company.CompanyDao;
import com.itheima.domain.company.Company;
import com.itheima.service.company.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;


@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyDao companyDao;
    @Override
    public List<Company> findAll() {
        return companyDao.findAll();
    }

    /**
     * 添加企业数据
     * @param company
     */
    @Override
    public void save(Company company) {
        company.setId(UUID.randomUUID().toString());//赋值uuid
        companyDao.save(company);//company没有id
    }

    /**
     * 根据id查询对象
     * @param id
     * @return
     */
    @Override
    public Company findById(String id) {
        return companyDao.findById(id);
    }

    /**
     * 修改企业数据
     * @param company
     */
    @Override
    public void update(Company company) {
        companyDao.update(company);
    }

    @Override
    public void delete(String id) {
        companyDao.delete(id);
    }

    /**
     * 分页查询
     * @param pageNum  当前页
     * @param pageSize 每页显示个数
     * @return
     */
    @Override
    public PageResult findByPage(Integer pageNum , Integer pageSize) {
        //1.创建对象

        //PageResult pageResult = new PageResult();
        //4.查询数据
        //4.1 总记录数
        long total = companyDao.findTotal();
        //4.2 显示的数据
        List<Company> list = companyDao.findByPage( (pageNum-1)*pageSize , pageSize );

        //3.赋值数据
        //pageResult.setTotal(total);//总记录数
        // pageResult.setList(list);//显示的数据

        //2.返回对象
        return new PageResult(total , list , pageNum , pageSize);
    }

    /**
     * mybatis分页插件
     * @param page
     * @param size
     * @return
     */
    @Override
    public PageInfo findByPageMybatis(Integer page, Integer size) {
        //1.分页
        PageHelper.startPage(page , size);
        //2.调用自己的findAll方法
        List<Company> list = companyDao.findAll();
        //3.构建PageInfo 对象
        return new PageInfo(list);
    }


}
