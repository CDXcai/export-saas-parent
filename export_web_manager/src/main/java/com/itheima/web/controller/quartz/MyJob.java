package com.itheima.web.controller.quartz;


import java.util.Date;

public class MyJob {

    public void run(){
        //1.定时发送邮件
        //2.海关系统(需求有一定的问题 , 发送完报运后 立马查询了是否报运成功(需要人工审核))
        //定时任务方法 -> 查询别的平台....... 没有业务代码 模拟的打印
        System.out.println(new Date().toLocaleString());
    }
}
