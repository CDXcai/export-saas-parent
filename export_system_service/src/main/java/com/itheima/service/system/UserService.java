package com.itheima.service.system;

import com.github.pagehelper.PageInfo;
import com.itheima.domain.system.User;

import java.util.List;


public interface UserService {
    PageInfo findByPage(Integer page, Integer size, String companyId);

    //保存对象
    void save(User user);

    //根据id查询对象
    User findById(String id);

    //修改用户数据
    void update(User user);

    //删除用户数据
    void delete(String id);

    //查询所有的用户
    List<User> findAll(String companyId);

    //根据邮箱获得用户对象
    User findByEmail(String email);

    //修改密码
    void changePassword(String email, String password);
}
