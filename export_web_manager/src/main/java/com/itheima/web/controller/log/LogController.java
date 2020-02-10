package com.itheima.web.controller.log;

import com.github.pagehelper.PageInfo;
import com.itheima.domain.log.SysLog;
import com.itheima.service.log.LogService;
import com.itheima.web.controller.base.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/system/log")
public class LogController extends BaseController {

    @Autowired
    private LogService logService;
    @RequestMapping("/list")
    public String list(@RequestParam(defaultValue = "1") Integer page , @RequestParam(defaultValue = "10") Integer size){
        PageInfo<SysLog> pageInfo = logService.findAll(page, size, companyId);
        request.setAttribute("page" , pageInfo);
        return "system/log/log-list";
    }
}
