package com.itheima.service.system;

import com.github.pagehelper.PageInfo;
import com.itheima.domain.system.Dept;

import java.util.List;


public interface DeptService {
    PageInfo findByPage(Integer page, Integer size,String companyId);

    //保存对象
    void save(Dept dept);

    //根据id查询对象
    Dept findById(String id);

    //修改部门数据
    void update(Dept dept);

    //删除部门数据
    void delete(String id);

    //查询所有的部门
    List<Dept> findAll(String companyId);

}
