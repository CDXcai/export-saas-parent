package com.itheima.web.controller.stat;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.service.stat.StatService;
import com.itheima.web.controller.base.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * 图形报表
 */
@Controller
@RequestMapping("/stat")
public class StatController extends BaseController {
    @Reference
    private StatService statService;
    /**
     * 跳转页面
     * @param chartsType
     * @return
     */
    @RequestMapping("/toCharts")
    public String toCharts(String chartsType){
        return "stat/stat-"+chartsType;
    }

    /**
     * 厂家销售情况统计
     *  [
     *      {value: 335, name: '直接访问'},
     *      {value: 310, name: '邮件营销'},
     *      {value: 234, name: '联盟广告'},
     *      {value: 135, name: '视频广告'},
     *      {value: 1548, name: '搜索引擎'}
     *  ]
     *  List<Map>  List<对象>
     * @return
     */
    @RequestMapping("/findFactory")
    @ResponseBody
    public List<Map> findFactory(){
        return statService.findFactory(super.companyId);
    }


    @RequestMapping("/findSell")
    @ResponseBody
    public List<Map> findSell(){
        return statService.findSell(super.companyId);
    }
    @RequestMapping("/findOnline")
    @ResponseBody
    public List<Map> findOnline(){

        return statService.findOnline(super.companyId);
    }

    public static void main(String[] args) {

        //这是一行注释
        System.out.println("xxxx");
    }
}
