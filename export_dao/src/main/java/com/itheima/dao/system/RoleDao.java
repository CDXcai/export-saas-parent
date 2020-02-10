package com.itheima.dao.system;

import com.itheima.domain.system.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface RoleDao {

    //根据id查询
    Role findById(String id);

    //查询全部用户
    List<Role> findAll(String companyId);

	//根据id删除
    int delete(String id);

	//添加
    int save(Role role);

	//更新
    int update(Role role);

    //根据当前的用户查询角色的信息
    List<Role> findByUid(String id);

    //根据用户id删除用户所有的角色信息
    void deleteByUid(String userid);

    //循环添加 用户的角色数据
    void saveUserRole(@Param("userId") String userid,@Param("roleId") String roleId);
}