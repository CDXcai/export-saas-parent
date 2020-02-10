package com.itheima.service.log;

import com.github.pagehelper.PageInfo;
import com.itheima.domain.log.SysLog;

/**
 * <Description>
 *
 * @author hzb@itcast.cn
 * @version 1.0
 * @taskId: <br>
 * @createDate 2020/01/08 14:41
 * @see com.itheima.service.log
 */
public interface LogService {
    //查询全部
    PageInfo<SysLog> findAll(Integer pageNum , Integer pageSize  ,String companyId);

    //添加
    int save(SysLog log);
}
