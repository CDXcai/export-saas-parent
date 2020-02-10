package com.itheima.test;


import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestMail {
    public static void main(String[] args) {
        //启动容器
        ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
    }
}
