package com.itheima.dao.company;

import com.itheima.domain.company.Company;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CompanyDao {
    public List<Company> findAll();

    //添加企业数据
    void save(Company company);

    //根据id查询数据
    Company findById(String id);

    //修改数据
    void update(Company company);

    //删除数据
    void delete(String id);

    //查询总记录数
    long findTotal();

    //分页查询数据
    List<Company> findByPage(@Param("startIndex") Integer startIndex,@Param("pageSize") Integer pageSize);
}
