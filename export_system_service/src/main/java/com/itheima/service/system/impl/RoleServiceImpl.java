package com.itheima.service.system.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.dao.system.RoleDao;
import com.itheima.domain.system.Role;
import com.itheima.service.system.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.UUID;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleDao roleDao;
    @Override
    public PageInfo findByPage(Integer page, Integer size, String companyId) {
        PageHelper.startPage(page , size);
        List<Role> list = roleDao.findAll(companyId);
        return new PageInfo(list);
    }

    @Override
    public void save(Role role) {
        role.setId(UUID.randomUUID().toString());
        //如果希望排序 加上日期
        //role.set
        roleDao.save(role);
    }

    @Override
    public Role findById(String id) {
        return roleDao.findById(id);
    }

    @Override
    public void update(Role role) {
        roleDao.update(role);
    }

    @Override
    public void delete(String id) {
        roleDao.delete(id);
    }

    @Override
    public List<Role> findAll(String companyId) {
        return roleDao.findAll(companyId);
    }

    /**
     * 根据当前的用户查询角色的信息
     * @param id
     * @return
     */
    @Override
    public List<Role> findByUid(String id) {

        return roleDao.findByUid(id);
    }

    /**
     * 修改当前用户的角色数据
     * 1.删除当前用户原本的角色数据
     * 2.添加新的角色
     * userid: 002108e2-9a10-4510-9683-8d8fd1d374ef
     * roleIds: 4028a1c34ec2e5c8014ec2ebf8430001
     * roleIds: 4028a1c34ec2e5c8014ec2ec38cc0002
     * roleIds: 4028a1cd4ee2d9d6014ee2df4c6a0000
     * roleIds: 4028a1cd4ee2d9d6014ee2df4c6a0001
     * roleIds: 4028a1cd4ee2d9d6014ee2df4c6a0002
     * @param userid
     * @param roleIds
     */
    @Override
    public void changeRole(String userid, String roleIds) {
        //1.根据用户id删除用户所有的角色信息
        roleDao.deleteByUid(userid);
        //2.循环添加 用户的角色数据  roleIds="" 空串  roleIds=null
        if(!StringUtils.isEmpty(roleIds) && roleIds.length()>0){
            String[] splitArr = roleIds.split(",");
            for (String roleId : splitArr) {
                roleDao.saveUserRole(userid , roleId);
            }
        }

    }
}
