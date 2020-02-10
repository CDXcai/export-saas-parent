package com.itheima.service.log.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.dao.log.SysLogDao;
import com.itheima.domain.log.SysLog;
import com.itheima.service.log.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * <Description>
 *
 * @author hzb@itcast.cn
 * @version 1.0
 * @taskId: <br>
 * @createDate 2020/01/08 14:41
 * @see com.itheima.service.log.impl
 */
@Service
public class LogServiceImpl  implements LogService {
    @Autowired
    private SysLogDao sysLogDao;
    @Override
    public PageInfo<SysLog>  findAll(Integer pageNum , Integer pageSize , String companyId) {
        PageHelper.startPage(pageNum , pageSize);
        List<SysLog> list = sysLogDao.findAll(companyId);
        return new PageInfo<>(list);
    }

    @Override
    public int save(SysLog log) {
        log.setId(UUID.randomUUID().toString());
        int count = sysLogDao.save(log);
        return count;
    }
}
