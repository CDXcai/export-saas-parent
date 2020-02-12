package com.itheima.service.system.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.common.utils.Encrypt;
import com.itheima.common.utils.UtilFuns;
import com.itheima.dao.system.UserDao;
import com.itheima.domain.system.User;
import com.itheima.service.system.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    /**
     * 分页查询用户数据
     * @param page
     * @param size
     * @return
     */
    @Override
    public PageInfo findByPage(Integer page, Integer size , String companyId) {
        //1.调用分页插件
        PageHelper.startPage(page , size);
        //2.查询所有  采用了动态代理的方式 底层 为我们的sql语句动态拼接分页条件
        List<User> list = userDao.findAll(companyId);
        return new PageInfo(list);
    }

    @Override
    public void save(User user) {
        user.setId(UUID.randomUUID().toString());
        userDao.save(user);
    }

    @Override
    public User findById(String id) {
        return userDao.findById(id);
    }

    @Override
    public void update(User user) {
        userDao.update(user);
    }

    @Override
    public void delete(String id) {
        userDao.delete(id);
    }

    @Override
    public List<User> findAll(String companyId) {
        //查询所有
        List<User> list = userDao.findAll(companyId);
        return list;
    }

    /**
     * 根据邮箱获得用户数据
     * @param email
     * @return
     */
    @Override
    public User findByEmail(String email) {
        return userDao.findByEmail(email);
    }

    /**
     * 修改密码
     * @param email
     * @param password
     */
    @Override
    public void changePassword(String email, String password) {
        //密码加密
        String md5Password = Encrypt.md5(password, email);
        userDao.changePassword(email, md5Password);
    }

    @Override
    public List<User> findByDegree(String degree) {
        return userDao.findByDegree(degree);
    }
}
