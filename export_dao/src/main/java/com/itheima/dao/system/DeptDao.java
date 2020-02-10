package com.itheima.dao.system;

import com.itheima.domain.system.Dept;

import java.util.List;

/**
 * <Description>
 *
 * @author hzb@itcast.cn
 * @version 1.0
 * @taskId: <br>
 * @createDate 2020/01/05 11:36
 * @see com.itheima.dao.system
 */
public interface DeptDao {
    //分页查询数据
    List<Dept> findAll(String companyId);

    //查询部门对象
    Dept findById(String id);

    //增
    void save(Dept dept);
    //删
    void delete(String id);
    //改
    void update(Dept dept);

}
