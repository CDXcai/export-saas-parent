package com.itheima.service.system;

import com.github.pagehelper.PageInfo;
import com.itheima.domain.system.Role;

import java.util.List;


public interface RoleService {
    PageInfo findByPage(Integer page, Integer size, String companyId);

    //保存对象
    void save(Role role);

    //根据id查询对象
    Role findById(String id);

    //修改角色数据
    void update(Role role);

    //删除角色数据
    void delete(String id);

    //查询所有的角色
    List<Role> findAll(String companyId);

    //根据当前的用户查询角色的信息
    List<Role> findByUid(String id);

    //修改当前用户的权限数据
    void changeRole(String userid, String roleIds);
}
