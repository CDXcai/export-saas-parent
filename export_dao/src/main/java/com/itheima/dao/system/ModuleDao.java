package com.itheima.dao.system;


import com.itheima.domain.system.Module;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 */
public interface ModuleDao {

    //根据id查询
    Module findById(String moduleId);

    //根据id删除
    int delete(String moduleId);

    //添加用户
    int save(Module module);

    //更新用户
    int update(Module module);

    //查询全部
    List<Module> findAll();

    //修改上级模块的数据
    List<Module> findModuleByCtype(String ctype);

    //根据角色的id查询出模块的数据
    List<Module> findByRoleId(String roleId);

    //根据角色的id删除角色的模块数据
    void deleteRoleModuleByRoleId(String roleid);

    //新增角色的模块数据
    void insertRoleModule(@Param("roleId") String roleid,@Param("moduleId") String moduleId);

    //根据belong字段查询出模块信息
    List<Module> findByBelong(String belong);

    //根据用户的id查询用户所具有的权限信息
    List<Module> findByUid(String uid);
}