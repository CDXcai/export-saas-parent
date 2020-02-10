package com.itheima.service.system.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.dao.system.DeptDao;
import com.itheima.domain.system.Dept;
import com.itheima.service.system.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
public class DeptServiceImpl implements DeptService {

/*    @Autowired  因为直接注入dao即可 除非代码有逻辑
    private UserService userService;*/
    @Autowired
    private DeptDao deptDao;
    /**
     * 分页查询部门数据
     * @param page
     * @param size
     * @return
     */
    @Override
    public PageInfo findByPage(Integer page, Integer size , String companyId) {
        //1.调用分页插件
        PageHelper.startPage(page , size);
        //2.查询所有
        List<Dept> list = deptDao.findAll(companyId);
        return new PageInfo(list);
    }

    @Override
    public void save(Dept dept) {
        dept.setId(UUID.randomUUID().toString());
        deptDao.save(dept);
    }

    @Override
    public Dept findById(String id) {
        return deptDao.findById(id);
    }

    @Override
    public void update(Dept dept) {
        deptDao.update(dept);
    }

    @Override
    public void delete(String id) {
        deptDao.delete(id);
    }

    @Override
    public List<Dept> findAll(String companyId) {
        //查询所有
        List<Dept> list = deptDao.findAll(companyId);
        return list;
    }
}
