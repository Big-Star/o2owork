package com.otowork.o2o;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ApplicationTest {
    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:spring/spring-dao.xml","classpath:spring/spring-service.xml");
        Object o = ctx.getBean("areaServiceImpl");
        System.out.println(o);
    }
}
