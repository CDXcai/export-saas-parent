package com.itheima.service.system;

import com.github.pagehelper.PageInfo;
import com.itheima.domain.system.Module;
import com.itheima.domain.system.User;

import java.util.List;

public interface ModuleService {
    PageInfo findByPage(Integer page, Integer size);

    //保存对象
    void save(Module module);

    //根据id查询对象
    Module findById(String id);

    //修改模块数据
    void update(Module module);

    //删除模块数据
    void delete(String id);

    //查询所有的模块
    List<Module> findAll();

    //修改上级模块的数据
    List<Module> findModuleByCtype(String ctype);

    //根据角色的id查询出模块的数据
    List<Module> findByRoleId(String roleId);

    //修改角色权限数据
    void updateRoleModule(String roleid, String moduleIds);

    //根据用户查询不同的模块数据
    List<Module> findByUser(User user);
}
