package com.itheima.service.company;

import com.github.pagehelper.PageInfo;
import com.itheima.common.entity.PageResult;
import com.itheima.domain.company.Company;

import java.util.List;

public interface CompanyService {
    //查询所有数据
    public List<Company> findAll();

    //保存对象
    void save(Company company);

    //根据id查询对象
    Company findById(String id);

    //修改企业数据
    void update(Company company);

    //删除企业数据
    void delete(String id);

    //分页查询
    PageResult findByPage(Integer pageNum , Integer pageSize);


    //分页插件查询数据
    PageInfo findByPageMybatis(Integer page, Integer size);
}
