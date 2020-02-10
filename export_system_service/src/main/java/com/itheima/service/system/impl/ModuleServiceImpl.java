package com.itheima.service.system.impl;

import com.alibaba.druid.util.StringUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.dao.system.ModuleDao;
import com.itheima.domain.system.Module;
import com.itheima.domain.system.User;
import com.itheima.service.system.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ModuleServiceImpl implements ModuleService {
    @Autowired
    private ModuleDao moduleDao;
    @Override
    public PageInfo findByPage(Integer page, Integer size) {
        PageHelper.startPage(page , size);
        List<Module> list = moduleDao.findAll();
        return new PageInfo(list);
    }

    @Override
    public void save(Module module) {
        module.setId(UUID.randomUUID().toString());
        moduleDao.save(module);
    }

    @Override
    public Module findById(String id) {
        return moduleDao.findById(id);
    }

    @Override
    public void update(Module module) {
        moduleDao.update(module);
    }

    @Override
    public void delete(String id) {
        moduleDao.delete(id);
    }

    @Override
    public List<Module> findAll() {
        return moduleDao.findAll();
    }

    /**
     * 修改上级模块的数据
     * @param ctype
     * @return
     */
    @Override
    public List<Module> findModuleByCtype(String ctype) {
        List<Module> list = null ;

        if(StringUtils.equals(ctype,"1")){//当前选择的为二级菜单 应该显示一级菜单的数据
            list = moduleDao.findModuleByCtype("0");
        }else if(StringUtils.equals(ctype,"2")){//当前选择的为按钮 应该显示二级菜单的数据
            list = moduleDao.findModuleByCtype("1");
        }
        return list;
    }

    /**
     * 根据角色的id查询出模块的数据
     * @param roleId
     * @return
     */
    @Override
    public List<Module> findByRoleId(String roleId) {
        return moduleDao.findByRoleId(roleId);
    }

    /**
     * 修改角色权限数据
     * @param roleid
     * @param moduleIds
     */
    @Override
    public void updateRoleModule(String roleid, String moduleIds) {
        //1.删除原本的数据
        moduleDao.deleteRoleModuleByRoleId(roleid);
        //2.新增数据
        if(moduleIds!= null && moduleIds.length()>0){
            //获得所有的模块id
            String[] moduleIdsSplit = moduleIds.split(",");

            for (String moduleId : moduleIdsSplit) {
                moduleDao.insertRoleModule(roleid , moduleId);
            }
        }
    }

    /**
     * 根据用户查询不同的模块数据
     * 1.根据身份判断
     * user表中有degree 字段 表示的含义如下
     * 0作为内部控制，租户企业不能使用
     *     0-saas管理员
     *     1-企业管理员
     *     2-管理所有下属部门和人员
     *     3-管理本部门
     *     4-普通员工
     *
     *  模块表中有belong属性 表示的含义如下
     *  从属关系
     *   0：sass系统内部菜单
     *   1：租用企业菜单
     * @param user
     * @return
     */
    @Override
    public List<Module> findByUser(User user) {

        List<Module> moduleList = null;
        //根据用户的身份 查询用户可以操作用户的权限
        if(user.getDegree() == 0 ){//saas管理员
            moduleList = moduleDao.findByBelong("0");
        }else if(user.getDegree() == 1){//企业管理员
            moduleList = moduleDao.findByBelong("1");
        }else{//普通员工
            moduleList=moduleDao.findByUid(user.getId());
        }
        return moduleList;
    }


}
