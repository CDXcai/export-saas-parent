package com.itheima.test;


import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

public class CompanyProvider {
    public static void main(String[] args) throws IOException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath*:spring/applicationContext-*.xml");
        context.start();
        System.out.println("CompanyProvider启动成功");
        System.in.read(); // 按任意键退出
    }
}
